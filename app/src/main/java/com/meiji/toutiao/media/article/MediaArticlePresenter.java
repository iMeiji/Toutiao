package com.meiji.toutiao.media.article;

import android.os.Handler;
import android.os.Message;

import com.meiji.toutiao.api.MediaApi;
import com.meiji.toutiao.bean.media.MediaArticleBean;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Meiji on 2017/4/11.
 */

class MediaArticlePresenter implements IMediaArticle.Presenter {

    private IMediaArticle.Model model;
    private IMediaArticle.View view;
    private List<MediaArticleBean.DataBean> mediaList = new ArrayList<>();
    private String mediaId;
    private List<MediaArticleBean.DataBean> dataList = new ArrayList<>();
    private Handler handler = new Handler(new Handler.Callback() {
        @Override
        public boolean handleMessage(Message msg) {
            if (msg.what == 1) {
                doSetAdapter();
            }
            if (msg.what == 0) {
                onFail();
            }
            return false;
        }
    });

    MediaArticlePresenter(IMediaArticle.View view) {
        this.view = view;
        this.model = new MediaArticleModel();
    }

    @Override
    public void doGetUrl(String mediaId) {
        view.onShowRefreshing();
        this.mediaId = mediaId;
        String url = MediaApi.getMediaArticleUrl(mediaId, model.getMaxBehotTime() + "");
        doRequestData(url);
    }

    @Override
    public void doRequestData(final String url) {
        new Thread(new Runnable() {
            @Override
            public void run() {
                boolean result = model.requestData(url);
                if (result) {
                    handler.obtainMessage(1).sendToTarget();
                } else {
                    handler.obtainMessage(0).sendToTarget();
                }
            }
        }).start();
    }

    @Override
    public void doSetAdapter() {
        if (model.getDataList() != null) {
            dataList.addAll(model.getDataList());
            view.onSetAdapter(dataList);
            view.onHideRefreshing();
        } else {
            onFail();
        }
    }

    @Override
    public void doRefresh() {
        String url = MediaApi.getMediaArticleUrl(mediaId, model.getMaxBehotTime() + "");
        doRequestData(url);
    }

    @Override
    public void onFail() {
        view.onHideRefreshing();
        view.onFail();
    }

    @Override
    public void doOnClickItem(int position) {

    }
}
