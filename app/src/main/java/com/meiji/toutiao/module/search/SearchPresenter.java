package com.meiji.toutiao.module.search;

import android.util.Log;

import com.meiji.toutiao.RetrofitFactory;
import com.meiji.toutiao.api.ISearchApi;
import com.meiji.toutiao.bean.news.NewsArticleBean;
import com.meiji.toutiao.module.news.content.NewsContentActivity;

import java.util.ArrayList;
import java.util.List;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.annotations.NonNull;
import io.reactivex.functions.Consumer;
import io.reactivex.functions.Function;
import io.reactivex.schedulers.Schedulers;

/**
 * Created by Meiji on 2017/2/7.
 */

class SearchPresenter implements ISearch.Presenter {

    private static final String TAG = "SearchPresenter";
    private int offset = 0;
    private String query;
    private ISearch.View view;
    private List<NewsArticleBean.DataBean> list = new ArrayList<>();

    SearchPresenter(ISearch.View view) {
        this.view = view;
    }

    public void doLoadData(String... parameter) {

        try {
            if (null == this.query)
                this.query = parameter[0];
        } catch (ArrayIndexOutOfBoundsException e) {
            e.printStackTrace();
        }

        RetrofitFactory.getRetrofit().create(ISearchApi.class)
                .getSearch(this.query, offset)
                .subscribeOn(Schedulers.io())
                .observeOn(Schedulers.io())
                .map(new Function<NewsArticleBean, List<NewsArticleBean.DataBean>>() {
                    @Override
                    public List<NewsArticleBean.DataBean> apply(@NonNull NewsArticleBean newsArticleBean) throws Exception {
                        return newsArticleBean.getData();
                    }
                })
                .observeOn(AndroidSchedulers.mainThread())
                .compose(view.<List<NewsArticleBean.DataBean>>bindToLife())
                .subscribe(new Consumer<List<NewsArticleBean.DataBean>>() {
                    @Override
                    public void accept(@NonNull List<NewsArticleBean.DataBean> dataBeen) throws Exception {
                        doSetAdapter(dataBeen);
                    }
                }, new Consumer<Throwable>() {
                    @Override
                    public void accept(@NonNull Throwable throwable) throws Exception {
                        throwable.printStackTrace();
                        doShowNetError();
                    }
                });
    }

    @Override
    public void doLoadMoreData() {
        offset += 20;
        doLoadData();
    }

    @Override
    public void doSetAdapter(List<NewsArticleBean.DataBean> dataBeen) {
        list.addAll(dataBeen);
        view.onSetAdapter(list);
        view.onHideLoading();
        // 释放内存
        if (list.size() > 100) {
            list.clear();
        }
    }

    @Override
    public void doRefresh() {
        if (list.size() != 0) {
            list.clear();
            offset = 0;
        }
        doLoadData();
    }

    @Override
    public void doShowNetError() {
        view.onHideLoading();
        view.onShowNetError();
    }

    @Override
    public void doOnClickItem(int position) {
        NewsArticleBean.DataBean bean = list.get(position);
        NewsContentActivity.launch(bean);
        // 打印下点击的标题和链接
        Log.d(TAG, "doOnClickItem: " + "点击的标题和链接---" + bean.getTitle() + "  " + bean.getDisplay_url());
    }
}
