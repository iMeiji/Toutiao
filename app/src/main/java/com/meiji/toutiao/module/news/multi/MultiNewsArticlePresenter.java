package com.meiji.toutiao.module.news.multi;

import android.util.Log;

import com.google.gson.Gson;
import com.meiji.toutiao.InitApp;
import com.meiji.toutiao.RetrofitFactory;
import com.meiji.toutiao.api.IMobileNewsApi;
import com.meiji.toutiao.bean.news.MultiNewsArticleBean;
import com.meiji.toutiao.bean.news.MultiNewsArticleDataBean;
import com.meiji.toutiao.bean.news.NewsArticleBean;
import com.meiji.toutiao.bean.video.VideoArticleBean;
import com.meiji.toutiao.module.news.content.NewsContentActivity;
import com.meiji.toutiao.module.video.content.VideoContentActivity;
import com.meiji.toutiao.utils.NetWorkUtil;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import io.reactivex.Observable;
import io.reactivex.SingleObserver;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.annotations.NonNull;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Function;
import io.reactivex.functions.Predicate;
import io.reactivex.schedulers.Schedulers;

/**
 * Created by Meiji on 2017/5/18.
 */

public class MultiNewsArticlePresenter implements IMultiNewsArticle.Presenter {

    private static final String TAG = "NewsArticlePresenter";
    private IMultiNewsArticle.View view;
    private List<MultiNewsArticleDataBean> dataList = new ArrayList<>();
    private String category;
    private int time;
    private Gson gson = new Gson();
    private int oldSize;

    MultiNewsArticlePresenter(IMultiNewsArticle.View view) {
        this.view = view;
    }

    private int getRandom() {
        if (this.time != 0) {
            Random random = new Random();
            StringBuilder result = new StringBuilder();
            for (int i = 0; i < 6; i++) {
                result.append(random.nextInt(10));
            }
            return this.time - Integer.parseInt(result.toString());
        }
        return 0;
    }

    @Override
    public void doLoadData(String... category) {

        try {
            if (this.category == null) {
                this.category = category[0];
            }
        } catch (ArrayIndexOutOfBoundsException e) {
            e.printStackTrace();
        }

        // 释放内存
        if (dataList.size() > 100) {
            dataList.clear();
        }

        Observable<MultiNewsArticleBean> ob1 = RetrofitFactory.getRetrofit().create(IMobileNewsApi.class)
                .getNewsArticle(this.category);
        Observable<MultiNewsArticleBean> ob2 = RetrofitFactory.getRetrofit().create(IMobileNewsApi.class)
                .getNewsArticle2(this.category);

        Observable.merge(ob1, ob2)
                .subscribeOn(Schedulers.io())
                .observeOn(Schedulers.io())
                .flatMap(new Function<MultiNewsArticleBean, Observable<MultiNewsArticleDataBean>>() {
                    @Override
                    public Observable<MultiNewsArticleDataBean> apply(@NonNull MultiNewsArticleBean multiNewsArticleBean) throws Exception {
                        List<MultiNewsArticleDataBean> dataList = new ArrayList<>();
                        for (MultiNewsArticleBean.DataBean dataBean : multiNewsArticleBean.getData()) {
                            dataList.add(gson.fromJson(dataBean.getContent(), MultiNewsArticleDataBean.class));
                        }
                        return Observable.fromIterable(dataList);
                    }
                })
                .filter(new Predicate<MultiNewsArticleDataBean>() {
                    @Override
                    public boolean test(@NonNull MultiNewsArticleDataBean multiNewsArticleDataBean) throws Exception {
                        if (multiNewsArticleDataBean.getSource().contains("头条问答")
                                || multiNewsArticleDataBean.getTag().contains("ad")
                                || multiNewsArticleDataBean.getSource().contains("话题")) {
                            return false;
                        }
                        for (MultiNewsArticleDataBean bean : dataList) {
                            if (bean.getTitle().equals(multiNewsArticleDataBean.getTitle())) {
                                return false;
                            }
                        }
                        return true;
                    }
                })
                .toList()
                .compose(view.<List<MultiNewsArticleDataBean>>bindToLife())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new SingleObserver<List<MultiNewsArticleDataBean>>() {
                    @Override
                    public void onSubscribe(@NonNull Disposable d) {

                    }

                    @Override
                    public void onSuccess(@NonNull List<MultiNewsArticleDataBean> testNewsArticleDataBeen) {
                        doSetAdapter(testNewsArticleDataBeen);
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
    public void doSetAdapter(List<MultiNewsArticleDataBean> dataBeen) {
        dataList.addAll(dataBeen);
        view.onSetAdapter(dataList);
        view.onHideLoading();
        Log.d(TAG, "doSetAdapter: " + dataList.size());
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
        try {
            MultiNewsArticleDataBean bean = dataList.get(position);
            if (bean.isHas_video()) {
                VideoArticleBean.DataBean dataBean = new VideoArticleBean.DataBean();
                dataBean.setTitle(bean.getTitle());
                dataBean.setGroup_id(bean.getGroup_id());
                dataBean.setItem_id(bean.getGroup_id());
                dataBean.setVideo_id(bean.getVideo_id());
                dataBean.setAbstractX(bean.getAbstractX());
                dataBean.setSource(bean.getSource());
                dataBean.setVideo_duration_str(bean.getVideo_duration() / 60 + "");
                String url = bean.getVideo_detail_info().getDetail_video_large_image().getUrl();
                VideoContentActivity.launch(dataBean, url);
            } else {
                NewsArticleBean.DataBean dataBean = new NewsArticleBean.DataBean();
                dataBean.setDisplay_url(bean.getDisplay_url());
                dataBean.setTitle(bean.getTitle());
                dataBean.setMedia_name(bean.getMedia_name());
                dataBean.setMedia_url("http://toutiao.com/m" + bean.getMedia_info().getMedia_id());
                dataBean.setGroup_id(bean.getGroup_id());
                dataBean.setItem_id(bean.getGroup_id());
                NewsContentActivity.launch(dataBean);
            }
            Log.d(TAG, "doOnClickItem: " + "点击的标题和链接---" + bean.getTitle() + "  " + bean.getDisplay_url());
        } catch (NullPointerException e) {
            e.printStackTrace();
        }
    }
}
