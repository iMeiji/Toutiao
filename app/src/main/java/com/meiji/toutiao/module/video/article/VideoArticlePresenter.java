package com.meiji.toutiao.module.video.article;

import com.meiji.toutiao.RetrofitFactory;
import com.meiji.toutiao.api.IVideoApi;
import com.meiji.toutiao.bean.video.VideoArticleBean;
import com.meiji.toutiao.module.video.content.VideoContentActivity;

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
 * Created by Meiji on 2017/3/29.
 */

public class VideoArticlePresenter implements IVideoArticle.Presenter {

    private IVideoArticle.View view;
    private String category;
    private int time;
    private List<VideoArticleBean.DataBean> dataList = new ArrayList<>();

    VideoArticlePresenter(IVideoArticle.View view) {
        this.view = view;
    }

    @Override
    public void doLoadData(String... category) {
        try {
            if (null == this.category) {
                this.category = category[0];
            }
        } catch (ArrayIndexOutOfBoundsException e) {
            e.printStackTrace();
        }

        RetrofitFactory.getRetrofit().create(IVideoApi.class).getVideoArticle(this.category, time)
                .subscribeOn(Schedulers.io())
                .observeOn(Schedulers.io())
                .flatMap(new Function<VideoArticleBean, Observable<VideoArticleBean.DataBean>>() {
                    @Override
                    public Observable<VideoArticleBean.DataBean> apply(@NonNull VideoArticleBean photoArticleBean) throws Exception {
                        List<VideoArticleBean.DataBean> data = photoArticleBean.getData();
                        // 移除最后一项 数据有重复
                        if (data.size() > 0)
                            data.remove(data.size() - 1);
                        time = photoArticleBean.getNext().getMax_behot_time();
                        return Observable.fromIterable(data);
                    }
                })
                .filter(new Predicate<VideoArticleBean.DataBean>() {
                    @Override
                    public boolean test(@NonNull VideoArticleBean.DataBean dataBean) throws Exception {
                        // 去除重复新闻
                        for (VideoArticleBean.DataBean bean : dataList) {
                            if (dataBean.getTitle().equals(bean.getTitle())) {
                                return false;
                            }
                        }
                        return true;
                    }
                })
                .toList()
                .observeOn(AndroidSchedulers.mainThread())
                .compose(view.<List<VideoArticleBean.DataBean>>bindToLife())
                .subscribe(new SingleObserver<List<VideoArticleBean.DataBean>>() {
                    @Override
                    public void onSubscribe(@NonNull Disposable d) {

                    }

                    @Override
                    public void onSuccess(@NonNull List<VideoArticleBean.DataBean> dataBeen) {
                        doSetAdapter(dataBeen);
                    }

                    @Override
                    public void onError(@NonNull Throwable e) {
                        e.printStackTrace();
                        doShowNetError();
                    }
                });
    }

    @Override
    public void doLoadMoreData() {
        doLoadData();
    }

    @Override
    public void doSetAdapter(List<VideoArticleBean.DataBean> dataBeen) {
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
        VideoArticleBean.DataBean bean = dataList.get(position);
        String url = null;
        try {
            url = bean.getVideo_detail_info().getVideo_detail_info().getDetail_video_large_image().getUrl();
        } catch (NullPointerException e) {
            e.printStackTrace();
        }
        VideoContentActivity.launch(bean, url);
    }
}
