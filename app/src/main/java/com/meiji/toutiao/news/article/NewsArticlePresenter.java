package com.meiji.toutiao.news.article;

import android.os.Handler;
import android.os.Message;
import android.util.Log;

import com.meiji.toutiao.api.NewsApi;
import com.meiji.toutiao.bean.news.NewsArticleBean;
import com.meiji.toutiao.news.content.NewsContentActivity;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Meiji on 2016/12/15.
 */

class NewsArticlePresenter implements INewsArticle.Presenter {

    private static final String TAG = "NewsArticlePresenter";
    private INewsArticle.View view;
    private INewsArticle.Model model;
    private List<NewsArticleBean.DataBean> dataList = new ArrayList<>();
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
    private String category;

    NewsArticlePresenter(INewsArticle.View view) {
        this.view = view;
        this.model = new NewsArticleModel();
    }

    @Override
    public void doGetUrl(String parameter) {
        view.onShowRefreshing();
        this.category = parameter;
        String url = NewsApi.getNewsArticle_PCUrl(category, model.getMaxBehotTime() + "");
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
        Log.d(TAG, "doSetAdapter: " + "刷新新闻数量 " + model.getDataList().size());
        dataList.addAll(model.getDataList());
        view.onSetAdapter(dataList);
        view.onHideRefreshing();
    }

    @Override
    public void doRefresh() {
        String url = NewsApi.getNewsArticle_PCUrl(category, model.getMaxBehotTime() + "");
        doRequestData(url);
    }

    @Override
    public void onFail() {
        view.onHideRefreshing();
        view.onFail();
    }

    @Override
    public void doOnClickItem(int position) {
        NewsArticleBean.DataBean bean = dataList.get(position);
        NewsContentActivity.startActivity(bean);
        // 打印下点击的标题和链接
        Log.d(TAG, "doOnClickItem: " + "点击的标题和链接---" + bean.getTitle() + "  " + bean.getDisplay_url());
    }
}
