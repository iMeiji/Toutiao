package com.meiji.toutiao.module.video.article;

import android.text.TextUtils;

import com.meiji.toutiao.ErrorAction;
import com.meiji.toutiao.RetrofitFactory;
import com.meiji.toutiao.api.IVideoApi;
import com.meiji.toutiao.bean.video.VideoArticleBean;
import com.meiji.toutiao.util.TimeUtil;

import java.util.ArrayList;
import java.util.List;

import io.reactivex.Observable;
import io.reactivex.annotations.NonNull;
import io.reactivex.functions.Consumer;
import io.reactivex.functions.Function;
import io.reactivex.functions.Predicate;
import io.reactivex.schedulers.Schedulers;

/**
 * Created by Meiji on 2017/3/29.
 */

public class VideoArticlePresenter implements IVideoArticle.Presenter {

    private static final String TAG = "VideoArticlePresenter";
    private IVideoArticle.View view;
    private String category;
    private String time;
    private List<VideoArticleBean.DataBean> dataList = new ArrayList<>();

    VideoArticlePresenter(IVideoArticle.View view) {
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

        RetrofitFactory.getRetrofit().create(IVideoApi.class).getVideoArticle(this.category, time)
                .subscribeOn(Schedulers.io())
                .observeOn(Schedulers.io())
                .switchMap(new Function<VideoArticleBean, Observable<VideoArticleBean.DataBean>>() {
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
                        if (TextUtils.isEmpty(dataBean.getVideo_id())) {
                            return false;
                        }
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
                .compose(view.<List<VideoArticleBean.DataBean>>bindToLife())
                .subscribe(new Consumer<List<VideoArticleBean.DataBean>>() {
                    @Override
                    public void accept(@NonNull List<VideoArticleBean.DataBean> list) throws Exception {
                        doSetAdapter(list);
                    }
                }, new Consumer<Throwable>() {
                    @Override
                    public void accept(@NonNull Throwable throwable) throws Exception {
                        doShowNetError();
                        ErrorAction.print(throwable);
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
    }

    @Override
    public void doRefresh() {
        if (dataList.size() != 0) {
            dataList.clear();
            time = TimeUtil.getCurrentTimeStamp();
        }
        doLoadData();
    }

    @Override
    public void doShowNetError() {
        view.onHideLoading();
        view.onShowNetError();
    }
}
