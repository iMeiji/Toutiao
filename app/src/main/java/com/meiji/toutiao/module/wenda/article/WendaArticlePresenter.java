package com.meiji.toutiao.module.wenda.article;

import android.text.TextUtils;
import android.util.Log;

import com.google.gson.Gson;
import com.meiji.toutiao.InitApp;
import com.meiji.toutiao.RetrofitFactory;
import com.meiji.toutiao.api.IMobileWendaApi;
import com.meiji.toutiao.bean.wenda.WendaArticleBean;
import com.meiji.toutiao.bean.wenda.WendaArticleDataBean;
import com.meiji.toutiao.utils.NetWorkUtil;

import java.util.ArrayList;
import java.util.List;

import io.reactivex.Observable;
import io.reactivex.SingleObserver;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.annotations.NonNull;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Function;
import io.reactivex.functions.Predicate;
import io.reactivex.schedulers.Schedulers;

/**
 * Created by Meiji on 2017/5/20.
 */

public class WendaArticlePresenter implements IWendaArticle.Presenter {

    private static final String TAG = "WendaArticlePresenter";
    private IWendaArticle.View view;
    private int time;
    private Gson gson = new Gson();
    private List<WendaArticleDataBean> dataList = new ArrayList<>();


    public WendaArticlePresenter(IWendaArticle.View view) {
        this.view = view;
    }

    @Override
    public void doRefresh() {
        if (dataList.size() != 0) {
            dataList.clear();
            time = 0;
        }
        doLoadData();
    }

    @Override
    public void doShowNetError() {
        view.onHideLoading();
        view.onShowNetError();
    }

    @Override
    public void doLoadData() {
        RetrofitFactory.getRetrofit().create(IMobileWendaApi.class)
                .getWendaArticle(time)
                .subscribeOn(Schedulers.io())
                .observeOn(Schedulers.io())
                .flatMap(new Function<WendaArticleBean, Observable<WendaArticleDataBean>>() {
                    @Override
                    public Observable<WendaArticleDataBean> apply(@NonNull WendaArticleBean wendaArticleBean) throws Exception {
                        List<WendaArticleDataBean> list = new ArrayList<>();
                        for (WendaArticleBean.DataBean bean : wendaArticleBean.getData()) {
                            WendaArticleDataBean contentBean = gson.fromJson(bean.getContent(), WendaArticleDataBean.class);
                            list.add(contentBean);
                        }
                        Log.d(TAG, "apply2: " + list.size());
                        return Observable.fromIterable(list);
                    }
                })
                .filter(new Predicate<WendaArticleDataBean>() {
                    @Override
                    public boolean test(@NonNull WendaArticleDataBean wendaArticleDataBean) throws Exception {
                        return !TextUtils.isEmpty(wendaArticleDataBean.getQuestion());
                    }
                })
                .toList()
                .map(new Function<List<WendaArticleDataBean>, List<WendaArticleDataBean>>() {
                    @Override
                    public List<WendaArticleDataBean> apply(@NonNull List<WendaArticleDataBean> wendaArticleContentBeen) throws Exception {

                        List<WendaArticleDataBean> dataList = new ArrayList<>();

                        for (WendaArticleDataBean bean : wendaArticleContentBeen) {
                            WendaArticleDataBean dataBean = new WendaArticleDataBean();

                            WendaArticleDataBean.ExtraBean extraBean = gson.fromJson(bean.getExtra(), WendaArticleDataBean.ExtraBean.class);
                            dataBean.setExtraBean(extraBean);

                            WendaArticleDataBean.QuestionBean questionBean = gson.fromJson(bean.getQuestion(), WendaArticleDataBean.QuestionBean.class);
                            dataBean.setQuestionBean(questionBean);
                            dataList.add(dataBean);
                            time = bean.getBehot_time();
                        }
                        Log.d(TAG, "apply: " + dataList.size());
                        return dataList;
                    }
                })
                .compose(view.<List<WendaArticleDataBean>>bindToLife())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new SingleObserver<List<WendaArticleDataBean>>() {
                    @Override
                    public void onSubscribe(@NonNull Disposable d) {

                    }

                    @Override
                    public void onSuccess(@NonNull List<WendaArticleDataBean> wendaArticleContentBeen) {
                        doSetAdapter(wendaArticleContentBeen);
                    }

                    @Override
                    public void onError(@NonNull Throwable e) {
                        e.printStackTrace();
                        if (NetWorkUtil.isNetworkConnected(InitApp.AppContext)) {
                            view.onRefresh();
                        } else {
                            doShowNetError();
                        }
                    }
                });
    }

    @Override
    public void doLoadMoreData() {
        doLoadData();
    }

    @Override
    public void doSetAdapter(List<WendaArticleDataBean> list) {
        dataList.addAll(list);
        view.onSetAdapter(dataList);
        view.onHideLoading();
    }

    @Override
    public void doOnClickItem(int position) {

    }
}
