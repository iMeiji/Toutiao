package com.meiji.toutiao.module.media.home.tab;

import android.text.TextUtils;

import com.meiji.toutiao.Constant;
import com.meiji.toutiao.ErrorAction;
import com.meiji.toutiao.api.IMobileMediaApi;
import com.meiji.toutiao.bean.media.MediaWendaBean;
import com.meiji.toutiao.bean.media.MultiMediaArticleBean;
import com.meiji.toutiao.util.RetrofitFactory;
import com.meiji.toutiao.util.TimeUtil;
import com.meiji.toutiao.util.ToutiaoUtil;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;

/**
 * Created by Meiji on 2017/7/1.
 */

public class MediaTabPresenter implements IMediaProfile.Presenter {

    static final int TYPE_ARTICLE = 0;
    static final int TYPE_VIDEO = 1;
    static final int TYPE_WENDA = 2;
    private static final String TAG = "MediaTabPresenter";
    private IMediaProfile.View view;
    private String mediaId;
    private String articleTime;
    private String videoTime;
    private int wendatotal;
    private String wendaCursor;
    private List<MultiMediaArticleBean.DataBean> articleList = new ArrayList<>();
    private List<MultiMediaArticleBean.DataBean> videoList = new ArrayList<>();
    private List<MediaWendaBean.AnswerQuestionBean> wendaList = new ArrayList<>();

    MediaTabPresenter(IMediaProfile.View view) {
        this.view = view;
        this.articleTime = TimeUtil.getCurrentTimeStamp();
        this.videoTime = TimeUtil.getCurrentTimeStamp();
    }

    @Override
    public void doRefresh() {

    }

    @Override
    public void doRefresh(int type) {
        switch (type) {
            case TYPE_ARTICLE:
                if (articleList.size() > 0) {
                    articleList.clear();
                    articleTime = TimeUtil.getCurrentTimeStamp();
                }
                doLoadArticle();
                break;
            case TYPE_VIDEO:
                if (videoList.size() > 0) {
                    videoList.clear();
                    videoTime = TimeUtil.getCurrentTimeStamp();
                }
                doLoadVideo();
                break;
            case TYPE_WENDA:
                if (wendaList.size() > 0) {
                    wendaList.clear();
                }
                doLoadWenda();
                break;
        }
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
        Map<String, String> map = ToutiaoUtil.getAsCp();

        RetrofitFactory.getRetrofit().create(IMobileMediaApi.class)
                .getMediaArticle(this.mediaId, this.articleTime, map.get(Constant.AS), map.get(Constant.CP))
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .as(view.bindAutoDispose())
                .subscribe(bean -> {
                    articleTime = bean.getNext().getMax_behot_time();
                    List<MultiMediaArticleBean.DataBean> list = bean.getData();
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
    public void doLoadVideo(String... mediaId) {
        try {
            if (TextUtils.isEmpty(this.mediaId)) {
                this.mediaId = mediaId[0];
            }
        } catch (Exception e) {
            ErrorAction.print(e);
        }
        Map<String, String> map = ToutiaoUtil.getAsCp();

        RetrofitFactory.getRetrofit().create(IMobileMediaApi.class)
                .getMediaVideo(this.mediaId, this.videoTime, map.get(Constant.AS), map.get(Constant.CP))
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .as(view.bindAutoDispose())
                .subscribe(bean -> {
                    videoTime = bean.getNext().getMax_behot_time();
                    List<MultiMediaArticleBean.DataBean> list = bean.getData();
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
    public void doLoadWenda(String... mediaId) {
        try {
            if (TextUtils.isEmpty(this.mediaId)) {
                this.mediaId = mediaId[0];
            }
        } catch (Exception e) {
            ErrorAction.print(e);
        }
        RetrofitFactory.getRetrofit().create(IMobileMediaApi.class)
                .getMediaWenda(this.mediaId)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .as(view.bindAutoDispose())
                .subscribe(bean -> {
                    wendatotal = bean.getTotal();
                    wendaCursor = bean.getCursor();
                    List<MediaWendaBean.AnswerQuestionBean> list = bean.getAnswer_question();
                    if (null != list && list.size() > 0) {
                        doSetWendaAdapter(list);
                    } else {
                        doShowNoMore();
                    }
                }, throwable -> {
                    doShowNetError();
                    ErrorAction.print(throwable);
                });
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
                if (wendaList.size() < wendatotal) {
                    RetrofitFactory.getRetrofit().create(IMobileMediaApi.class)
                            .getMediaWendaLoadMore(this.mediaId, this.wendaCursor)
                            .subscribeOn(Schedulers.io())
                            .observeOn(AndroidSchedulers.mainThread())
                            .as(view.bindAutoDispose())
                            .subscribe(bean -> {
                                List<MediaWendaBean.AnswerQuestionBean> list = bean.getAnswer_question();
                                if (null != list && list.size() > 0) {
                                    doSetWendaAdapter(list);
                                } else {
                                    doShowNoMore();
                                }
                            }, throwable -> {
                                view.onShowNoMore();
                                ErrorAction.print(throwable);
                            });
                } else {
                    new Thread(this::doShowNoMore).start();
                }
                break;
        }
    }

    @Override
    public void doSetAdapter(List<MultiMediaArticleBean.DataBean> list) {
        articleList.addAll(list);
        view.onSetAdapter(articleList);
        view.onHideLoading();
    }

    @Override
    public void doSetWendaAdapter(List<MediaWendaBean.AnswerQuestionBean> list) {
        wendaList.addAll(list);
        view.onSetAdapter(wendaList);
        view.onHideLoading();
    }

    @Override
    public void doShowNoMore() {
        view.onHideLoading();
        view.onShowNoMore();
    }
}
