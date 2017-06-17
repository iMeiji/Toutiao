package com.meiji.toutiao.module.search.result;

import android.text.TextUtils;

import com.meiji.toutiao.RetrofitFactory;
import com.meiji.toutiao.api.IMobileSearchApi;
import com.meiji.toutiao.bean.search.SearchBean;

import java.util.ArrayList;
import java.util.List;

import io.reactivex.Observable;
import io.reactivex.ObservableSource;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.annotations.NonNull;
import io.reactivex.functions.Consumer;
import io.reactivex.functions.Function;
import io.reactivex.functions.Predicate;
import io.reactivex.schedulers.Schedulers;

/**
 * Created by Meiji on 2017/2/7.
 */

class SearchResultPresenter implements ISearchResult.Presenter {

    private static final String TAG = "SearchResultPresenter";
    private int offset = 0;
    private String query;
    private String curTab;
    private ISearchResult.View view;
    private List<SearchBean.DataBeanX> list = new ArrayList<>();

    SearchResultPresenter(ISearchResult.View view) {
        this.view = view;
    }

    public void doLoadData(String... parameter) {

        try {
            if (null == this.query) {
                this.query = parameter[0];
                this.curTab = parameter[1];
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        RetrofitFactory.getRetrofit().create(IMobileSearchApi.class)
                .getSearchArticle(this.query, curTab, offset)
                .subscribeOn(Schedulers.io())
                .observeOn(Schedulers.io())
                .flatMap(new Function<SearchBean, ObservableSource<SearchBean.DataBeanX>>() {
                    @Override
                    public ObservableSource<SearchBean.DataBeanX> apply(@NonNull SearchBean searchBean) throws Exception {
                        return Observable.fromIterable(searchBean.getData());
                    }
                })
                .filter(new Predicate<SearchBean.DataBeanX>() {
                    @Override
                    public boolean test(@NonNull SearchBean.DataBeanX dataBeanX) throws Exception {
                        return !TextUtils.isEmpty(dataBeanX.getTitle());
                    }
                })
                .toList()
                .observeOn(AndroidSchedulers.mainThread())
                // OkHttp: <-- HTTP FAILED: java.io.IOException: Canceled
                // Fragment 生命周期的祸
//                .compose(view.<List<SearchBean.DataBean>>bindToLife())
                .subscribe(new Consumer<List<SearchBean.DataBeanX>>() {
                    @Override
                    public void accept(@NonNull List<SearchBean.DataBeanX> dataBeen) throws Exception {
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
    public void doSetAdapter(List<SearchBean.DataBeanX> dataBeen) {
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

//    @Override
//    public void doOnClickItem(int position) {
//        NewsArticleBean.DataBean bean = list.get(position);
//        NewsContentActivity.launch(bean);
//        // 打印下点击的标题和链接
//        Log.d(TAG, "doOnClickItem: " + "点击的标题和链接---" + bean.getTitle() + "  " + bean.getDisplay_url());
//    }
}
