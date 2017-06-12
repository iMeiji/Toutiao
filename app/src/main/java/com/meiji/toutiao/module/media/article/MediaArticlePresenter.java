package com.meiji.toutiao.module.media.article;

import android.util.Log;

import com.meiji.toutiao.RetrofitFactory;
import com.meiji.toutiao.api.IMediaApi;
import com.meiji.toutiao.bean.media.MediaArticleBean;
import com.meiji.toutiao.bean.media.MediaChannelBean;
import com.meiji.toutiao.bean.news.NewsArticleBean;
import com.meiji.toutiao.module.news.content.NewsContentActivity;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.util.ArrayList;
import java.util.List;

import io.reactivex.Observable;
import io.reactivex.ObservableEmitter;
import io.reactivex.ObservableOnSubscribe;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.annotations.NonNull;
import io.reactivex.functions.Consumer;
import io.reactivex.functions.Function;
import io.reactivex.schedulers.Schedulers;
import okhttp3.ResponseBody;
import retrofit2.Response;

import static com.meiji.toutiao.module.video.content.VideoContentActivity.TAG;

/**
 * Created by Meiji on 2017/4/11.
 */

class MediaArticlePresenter implements IMediaArticle.Presenter {

    private IMediaArticle.View view;
    private String mediaId;
    private List<MediaArticleBean.DataBean> dataList = new ArrayList<>();
    private int time;

    MediaArticlePresenter(IMediaArticle.View view) {
        this.view = view;
    }

    @Override
    public void doLoadData(String... mediaId) {
        try {
            if (null == this.mediaId) {
                this.mediaId = mediaId[0];
            }
        } catch (ArrayIndexOutOfBoundsException e) {
            e.printStackTrace();
        }

        RetrofitFactory.getRetrofit().create(IMediaApi.class).getMediaArticle(this.mediaId, time)
                .subscribeOn(Schedulers.io())
                .observeOn(Schedulers.io())
                .map(new Function<MediaArticleBean, List<MediaArticleBean.DataBean>>() {
                    @Override
                    public List<MediaArticleBean.DataBean> apply(@NonNull MediaArticleBean mediaArticleBean) throws Exception {
                        time = mediaArticleBean.getNext().getMax_behot_time();
                        return mediaArticleBean.getData();
                    }
                })
                .compose(view.<List<MediaArticleBean.DataBean>>bindToLife())
                .subscribe(new Consumer<List<MediaArticleBean.DataBean>>() {
                    @Override
                    public void accept(@NonNull List<MediaArticleBean.DataBean> dataBeen) throws Exception {
                        if (dataBeen.size() > 0) {
                            doSetAdapter(dataBeen);
                        } else {
                            doShowNoMore();
                        }
                    }
                }, new Consumer<Throwable>() {
                    @Override
                    public void accept(@NonNull Throwable throwable) throws Exception {
                        doShowNetError();
                    }
                });
    }

    @Override
    public void doLoadMoreData() {
        doLoadData();
    }

    @Override
    public void doSetAdapter(List<MediaArticleBean.DataBean> dataBeen) {
        dataList.addAll(dataBeen);
        view.onSetAdapter(dataList);
        view.onHideLoading();
    }

    @Override
    public void doRefresh() {
        if (dataList.size() > 0) {
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
    public void doShowNoMore() {
        view.onHideLoading();
        view.onShowNoMore();
    }

    @Override
    public void doOnClickItem(final int position, final MediaChannelBean mediaChannelBean) {
        final MediaArticleBean.DataBean dataBean = dataList.get(position);
        final String url = dataBean.getSource_url();
        Observable
                .create(new ObservableOnSubscribe<String>() {
                    @Override
                    public void subscribe(@NonNull ObservableEmitter<String> e) throws Exception {
                        Response<ResponseBody> response = RetrofitFactory.getRetrofit().create(IMediaApi.class)
                                .getCommentParameter(url).execute();
                        if (response.isSuccessful()) {
                            e.onNext(response.body().string());
                        } else {
                            e.onComplete();
                        }
                    }
                })
                .subscribeOn(Schedulers.io())
                .observeOn(Schedulers.io())
                .map(new Function<String, long[]>() {
                    @Override
                    public long[] apply(@NonNull String s) throws Exception {
                        return parseCommentParameter(s);
                    }
                })
                .map(new Function<long[], NewsArticleBean.DataBean>() {
                    @Override
                    public NewsArticleBean.DataBean apply(@NonNull long[] longs) throws Exception {
                        NewsArticleBean.DataBean bean = new NewsArticleBean.DataBean();
                        bean.setTitle(dataBean.getTitle());
                        bean.setDisplay_url(dataBean.getSource_url());
                        bean.setMedia_name(mediaChannelBean.getName());
                        bean.setMedia_url(mediaChannelBean.getUrl());
                        bean.setGroup_id(longs[0]);
                        bean.setItem_id(longs[1]);
                        return bean;
                    }
                })
                .observeOn(AndroidSchedulers.mainThread())
                .compose(view.<NewsArticleBean.DataBean>bindToLife())
                .subscribe(new Consumer<NewsArticleBean.DataBean>() {
                    @Override
                    public void accept(@NonNull NewsArticleBean.DataBean bean) throws Exception {
                        // 这里要区分文章类型 新闻/图片/视频 (待写)
                        NewsContentActivity.launch(bean);
                    }
                }, new Consumer<Throwable>() {
                    @Override
                    public void accept(@NonNull Throwable throwable) throws Exception {
                        doShowNetError();
                    }
                });
    }

    private long[] parseCommentParameter(String HTML) {
        long[] longs = new long[2];
        Document doc = Jsoup.parse(HTML);
        // 取得所有的script tag
        Elements scripts = doc.getElementsByTag("script");
        for (Element e : scripts) {
            // 过滤字符串
            String script = e.toString();
            if (script.contains("var group_id =")) {
                // 只取得script的內容
                script = e.childNode(0).toString();
                // 取得JS变量数组
                String[] vars = script.split("var ");
                for (String var : vars) {
                    // 取到满足条件的JS变量
                    if (var.contains("group_id")) {
                        int start = var.indexOf("\"");
                        int end = var.lastIndexOf("\"");
                        longs[0] = Long.parseLong(var.substring(start + 1, end));
                        Log.d(TAG, "getCommentRequestData: groupId" + longs[0]);
                    }
                    if (var.contains("item_id")) {
                        int start = var.indexOf("\"");
                        int end = var.lastIndexOf("\"");
                        longs[1] = Long.parseLong(var.substring(start + 1, end));
                        Log.d(TAG, "getCommentRequestData: itemId" + longs[1]);
                    }
                }
            }
        }
        return longs;
    }
}
