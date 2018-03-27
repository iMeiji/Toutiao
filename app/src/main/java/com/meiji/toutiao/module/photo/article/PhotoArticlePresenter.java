package com.meiji.toutiao.module.photo.article;

import com.meiji.toutiao.ErrorAction;
import com.meiji.toutiao.api.IPhotoApi;
import com.meiji.toutiao.bean.photo.PhotoArticleBean;
import com.meiji.toutiao.util.RetrofitFactory;
import com.meiji.toutiao.util.TimeUtil;

import java.util.ArrayList;
import java.util.List;

import io.reactivex.Observable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.functions.Function;
import io.reactivex.schedulers.Schedulers;

/**
 * Created by Meiji on 2017/2/16.
 */

class PhotoArticlePresenter implements IPhotoArticle.Presenter {

    private static final String TAG = "PhotoArticlePresenter";
    private IPhotoArticle.View view;
    private List<PhotoArticleBean.DataBean> dataList = new ArrayList<>();
    private String category;
    private String time;

    PhotoArticlePresenter(IPhotoArticle.View view) {
        this.view = view;
        this.time = TimeUtil.getCurrentTimeStamp();
    }

    @Override
    public void doLoadData(String... category) {

        try {
            if (null == this.category) {
                this.category = category[0];
            }
        } catch (Exception e) {
            ErrorAction.print(e);
        }

        // 释放内存
        if (dataList.size() > 100) {
            dataList.clear();
        }

        RetrofitFactory.getRetrofit().create(IPhotoApi.class).getPhotoArticle(this.category, time)
                .subscribeOn(Schedulers.io())
                .switchMap((Function<PhotoArticleBean, Observable<PhotoArticleBean.DataBean>>) photoArticleBean -> {
                    List<PhotoArticleBean.DataBean> data = photoArticleBean.getData();
                    // 移除最后一项 数据有重复
                    if (data.size() > 0)
                        data.remove(data.size() - 1);
                    time = photoArticleBean.getNext().getMax_behot_time();
                    return Observable.fromIterable(data);
                })
                .filter(dataBean -> {
                    // 去除重复新闻
                    for (PhotoArticleBean.DataBean bean : dataList) {
                        if (dataBean.getTitle().equals(bean.getTitle())) {
                            return false;
                        }
                    }
                    return true;
                })
                .toList()
                .observeOn(AndroidSchedulers.mainThread())
                .as(view.bindAutoDispose())
                .subscribe(list -> {
                    if (null != list && list.size() > 0) {
                        doSetAdapter(list);
                    } else {
                        doShowNoMore();
                    }
                }, throwable -> {
                    doShowNetError();
                    ErrorAction.print(throwable);
                });
    }

    @Override
    public void doLoadMoreData() {
        doLoadData();
    }

    @Override
    public void doSetAdapter(List<PhotoArticleBean.DataBean> dataBeen) {
        dataList.addAll(dataBeen);
        view.onSetAdapter(dataList);
        view.onHideLoading();
    }

    @Override
    public void doRefresh() {
        if (dataList.size() != 0) {
            dataList.clear();
            time = TimeUtil.getCurrentTimeStamp();
        }
        view.onShowLoading();
        doLoadData();
    }

    @Override
    public void doShowNetError() {
        view.onHideLoading();
        view.onShowNetError();
    }

    @Override
    public void doShowNoMore() {
        view.onHideLoading();
        view.onShowNoMore();
    }
}
