package com.meiji.toutiao.module.media.wip.tab;

import android.text.TextUtils;

import com.meiji.toutiao.ErrorAction;
import com.meiji.toutiao.RetrofitFactory;
import com.meiji.toutiao.api.IMobileMediaApi;
import com.meiji.toutiao.bean.media.MultiMediaArticleBean;
import com.meiji.toutiao.utils.TimeUtil;

import java.util.ArrayList;
import java.util.List;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.annotations.NonNull;
import io.reactivex.functions.Consumer;
import io.reactivex.schedulers.Schedulers;

/**
 * Created by Meiji on 2017/7/1.
 */

public class MediaTabPresenter implements IMediaProfile.Presenter {

    protected static final int TYPE_ARTICLE = 0;
    protected static final int TYPE_VIDEO = 1;
    protected static final int TYPE_WENDA = 2;
    private static final String TAG = "MediaTabPresenter";
    private IMediaProfile.View view;
    private String mediaId;
    private String time;
    private List<MultiMediaArticleBean.DataBean> dataList = new ArrayList<>();

    MediaTabPresenter(IMediaProfile.View view) {
        this.view = view;
        this.time = TimeUtil.getTimeStamp();
    }

    @Override
    public void doRefresh() {
        if (dataList.size() > 0) {
            dataList.clear();
            time = TimeUtil.getTimeStamp();
        }
        doLoadArticle();
    }

    @Override
    public void doShowNetError() {
        view.onHideLoading();
        view.onShowNetError();
    }

    @Override
    public void doLoadArticle(String... mediaId) {
        try {
            if (TextUtils.isEmpty(this.mediaId)) {
                this.mediaId = mediaId[0];
            }
        } catch (Exception e) {
            ErrorAction.print(e);
        }
        RetrofitFactory.getRetrofit().create(IMobileMediaApi.class)
                .getMediaArticle(this.mediaId, this.time)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .compose(view.<MultiMediaArticleBean>bindToLife())
                .subscribe(new Consumer<MultiMediaArticleBean>() {
                    @Override
                    public void accept(@NonNull MultiMediaArticleBean bean) throws Exception {
                        time = bean.getNext().getMax_behot_time();
                        doSetAdapter(bean.getData());
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
    public void doLoadVideo(String... mediaId) {
        try {
            if (TextUtils.isEmpty(this.mediaId)) {
                this.mediaId = mediaId[0];
            }
        } catch (Exception e) {
            ErrorAction.print(e);
        }
        RetrofitFactory.getRetrofit().create(IMobileMediaApi.class)
                .getMediaVideo(this.mediaId, this.time)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .compose(view.<MultiMediaArticleBean>bindToLife())
                .subscribe(new Consumer<MultiMediaArticleBean>() {
                    @Override
                    public void accept(@NonNull MultiMediaArticleBean bean) throws Exception {
                        time = bean.getNext().getMax_behot_time();
                        doSetAdapter(bean.getData());
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
    public void doLoadWenda(String... mediaId) {

    }

    @Override
    public void doLoadMoreData(int type) {
        switch (type) {
            case TYPE_ARTICLE:
                doLoadArticle();
                break;
            case TYPE_VIDEO:
                doLoadVideo();
                break;
            case TYPE_WENDA:
                doLoadWenda();
                break;
        }
    }

    @Override
    public void doSetAdapter(List<MultiMediaArticleBean.DataBean> list) {
        dataList.addAll(list);
        view.onSetAdapter(dataList);
        view.onHideLoading();
    }
}
