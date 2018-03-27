package com.meiji.toutiao.module.wenda.content;

import android.util.Log;

import com.meiji.toutiao.ErrorAction;
import com.meiji.toutiao.api.IMobileWendaApi;
import com.meiji.toutiao.bean.wenda.WendaContentBean;
import com.meiji.toutiao.util.RetrofitFactory;

import java.util.ArrayList;
import java.util.List;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;

/**
 * Created by Meiji on 2017/5/22.
 */

class WendaContentPresenter implements IWendaContent.Presenter {

    private static final String TAG = "WendaContentPresenter";
    private IWendaContent.View view;
    private String qid;
    private int niceOffset = 0;
    private int normalOffset = 0;
    private int niceAnsCount = 0;
    private int normalAnsCount = 0;
    private List<WendaContentBean.AnsListBean> ansList = new ArrayList<>();
    private String title;

    WendaContentPresenter(IWendaContent.View view) {
        this.view = view;
    }

    public void doRefresh() {
        if (ansList.size() != 0) {
            ansList.clear();
            niceOffset = 0;
            normalOffset = 0;
        }
        doLoadData(this.qid);
    }

    @Override
    public void doShowNetError() {
        view.onHideLoading();
        view.onShowNetError();
    }

    @Override
    public void doLoadData(String qid) {
        this.qid = qid;
        Log.d(TAG, "doLoadArticle: " + qid);

        RetrofitFactory.getRetrofit().create(IMobileWendaApi.class).getWendaNiceContent(qid)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .as(view.bindAutoDispose())
                .subscribe(wendaContentBean -> {
                    doSetHeader(wendaContentBean.getQuestion());
                    doSetAdapter(wendaContentBean.getAns_list());
                    niceOffset += 10;
                }, throwable -> {
                    doShowNetError();
                    ErrorAction.print(throwable);
                });
    }

    @Override
    public void doLoadMoreData() {

        if (niceOffset < niceAnsCount) {
            RetrofitFactory.getRetrofit().create(IMobileWendaApi.class)
                    .getWendaNiceContentLoadMore(qid, niceOffset)
                    .subscribeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread())
                    .as(view.bindAutoDispose())
                    .subscribe(wendaContentBean -> {
                        doSetAdapter(wendaContentBean.getAns_list());
                        niceOffset += 10;
                    }, throwable -> {
                        doShowNetError();
                        ErrorAction.print(throwable);
                    });
        } else if (normalOffset < normalAnsCount) {
            RetrofitFactory.getRetrofit().create(IMobileWendaApi.class)
                    .getWendaNormalContentLoadMore(qid, normalOffset)
                    .subscribeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread())
                    .as(view.bindAutoDispose())
                    .subscribe(wendaContentBean -> {
                        doSetAdapter(wendaContentBean.getAns_list());
                        normalOffset += 10;
                    }, throwable -> {
                        doShowNetError();
                        ErrorAction.print(throwable);
                    });
        } else {
            doShowNoMore();
        }
    }

    @Override
    public void doSetAdapter(List<WendaContentBean.AnsListBean> list) {
        for (WendaContentBean.AnsListBean bean : list) {
            bean.setTitle(this.title);
            bean.setQid(this.qid);
        }
        ansList.addAll(list);
        view.onSetAdapter(ansList);
        view.onHideLoading();
    }

    @Override
    public void doSetHeader(WendaContentBean.QuestionBean questionBean) {
        this.niceAnsCount = questionBean.getNice_ans_count();
        this.normalAnsCount = questionBean.getNormal_ans_count();
        this.title = questionBean.getTitle();
        view.onSetHeader(questionBean);
    }

    @Override
    public void doShowNoMore() {
        view.onHideLoading();
        if (ansList.size() > 0) {
            view.onShowNoMore();
        }
    }
}
