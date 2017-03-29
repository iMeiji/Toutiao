package com.meiji.toutiao.video.article;

import android.os.Handler;
import android.os.Message;

import com.meiji.toutiao.api.VideoApi;
import com.meiji.toutiao.bean.video.VideoArticleBean;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Meiji on 2017/3/29.
 */

public class VideoArticlePresenter implements IVideoArticle.Presenter {

    private IVideoArticle.View view;
    private IVideoArticle.Model model;
    private String category;
    private List<VideoArticleBean.DataBean> dataList = new ArrayList<>();
    private Handler handler = new Handler(new Handler.Callback() {
        @Override
        public boolean handleMessage(Message message) {
            if (message.what == 1) {
                doSetAdapter();
            }
            if (message.what == 0) {
                onFail();
            }
            return false;
        }
    });

    VideoArticlePresenter(IVideoArticle.View view) {
        this.view = view;
        this.model = new VideoArticleModel();
    }

    @Override
    public void doGetUrl(String parameter) {
        view.onShowRefreshing();
        this.category = parameter;
        String url = VideoApi.getVideoArticleUrl(category);
        doRequestData(url);
    }

    @Override
    public void doRequestData(final String url) {
        new Thread(new Runnable() {
            @Override
            public void run() {
                boolean result = model.requestData(url);
                if (result) {
                    Message message = handler.obtainMessage(1);
                    message.sendToTarget();
                } else {
                    Message message = handler.obtainMessage(0);
                    message.sendToTarget();
                }
            }
        }).start();
    }

    @Override
    public void doSetAdapter() {
        dataList.addAll(model.getDataList());
        view.onSetAdapter(dataList);
        view.onHideRefreshing();
    }

    @Override
    public void doRefresh() {
        String url = VideoApi.getVideoArticleUrl(category);
        doRequestData(url);
    }

    @Override
    public void onFail() {
        view.onHideRefreshing();
        view.onFail();
    }

    @Override
    public void doOnClickItem(int position) {
//        NewsArticleBean.DataBean bean = dataList.get(position);
//        NewsContentActivity.startActivity(bean);
//        // 打印下点击的标题和链接
//        Log.d(TAG, "doOnClickItem: " + "点击的标题和链接---" + bean.getTitle() + "  " + bean.getDisplay_url());
    }
}
