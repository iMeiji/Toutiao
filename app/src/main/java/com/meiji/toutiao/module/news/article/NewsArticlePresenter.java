package com.meiji.toutiao.module.news.article;

import android.util.Log;

import com.meiji.toutiao.RetrofitFactory;
import com.meiji.toutiao.api.INewsApi;
import com.meiji.toutiao.bean.news.NewsArticleBean;
import com.meiji.toutiao.module.news.content.NewsContentActivity;

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
 * Created by Meiji on 2016/12/15.
 */

class NewsArticlePresenter implements INewsArticle.Presenter {

    private static final String TAG = "NewsArticlePresenter";
    private INewsArticle.View view;
    private List<NewsArticleBean.DataBean> dataList = new ArrayList<>();
    private String category;
    private int time;

    NewsArticlePresenter(INewsArticle.View view) {
        this.view = view;
    }

//    private static int getRandom() {
//        Random random = new Random();
//        StringBuilder result = new StringBuilder();
//        for (int i = 0; i < 6; i++) {
//            result.append(random.nextInt(10));
//        }
//        return Integer.parseInt(result.toString());
//    }

    @Override
    public void doLoadData(String... category) {

        try {
            if (this.category == null) {
                this.category = category[0];
            }
        } catch (ArrayIndexOutOfBoundsException e) {
            e.printStackTrace();
        }

        // 因为删除了部分新闻 故使用2个请求
        Observable<NewsArticleBean> ob1 = RetrofitFactory.getRetrofit().create(INewsApi.class).getNewsArticle1(this.category, time);
        Observable<NewsArticleBean> ob2 = RetrofitFactory.getRetrofit().create(INewsApi.class).getNewsArticle2(this.category);

        Observable.merge(ob1, ob2)
                .subscribeOn(Schedulers.io())
                .observeOn(Schedulers.io())
                .flatMap(new Function<NewsArticleBean, Observable<NewsArticleBean.DataBean>>() {
                    @Override
                    public Observable<NewsArticleBean.DataBean> apply(@NonNull NewsArticleBean newsArticleBean) throws Exception {
                        List<NewsArticleBean.DataBean> data = newsArticleBean.getData();
                        // 移除最后一项 数据有重复
                        if (data.size() > 0)
                            data.remove(data.size() - 1);
                        time = newsArticleBean.getNext().getMax_behot_time();
                        return Observable.fromIterable(data);
                    }
                })
                .filter(new Predicate<NewsArticleBean.DataBean>() {
                    @Override
                    public boolean test(@NonNull NewsArticleBean.DataBean dataBean) throws Exception {
                        // 删除广告等等
                        if (dataBean.getSource().contains("头条问答")
                                || dataBean.getTag().contains("ad")
                                || dataBean.isHas_video()
                                || dataBean.getSource().contains("话题")) {
                            return false;
                        }
                        // 去除重复新闻
                        for (NewsArticleBean.DataBean bean : dataList) {
                            if (dataBean.getTitle().equals(bean.getTitle())) {
                                return false;
                            }
                        }
                        return true;
                    }
                })
                .toList()
                .observeOn(AndroidSchedulers.mainThread())
                .compose(view.<List<NewsArticleBean.DataBean>>bindToLife())
                .subscribe(new SingleObserver<List<NewsArticleBean.DataBean>>() {
                    @Override
                    public void onSubscribe(@NonNull Disposable d) {

                    }

                    @Override
                    public void onSuccess(@NonNull List<NewsArticleBean.DataBean> dataBeen) {
                        doSetAdapter(dataBeen);
                    }

                    @Override
                    public void onError(@NonNull Throwable e) {
                        doShowNetError();
                    }
                });
    }

    @Override
    public void doLoadMoreData() {
        doLoadData();
    }

    @Override
    public void doSetAdapter(List<NewsArticleBean.DataBean> dataBeen) {
        dataList.addAll(dataBeen);
        view.onSetAdapter(dataList);
        view.onHideLoading();
        // 释放内存
        if (dataList.size() > 100) {
            dataList.clear();
        }
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
    public void doOnClickItem(int position) {
        NewsArticleBean.DataBean bean = dataList.get(position);
        NewsContentActivity.launch(bean);
        // 打印下点击的标题和链接
        Log.d(TAG, "doOnClickItem: " + "点击的标题和链接---" + bean.getTitle() + "  " + bean.getDisplay_url());
    }
}
