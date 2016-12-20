package com.meiji.toutiao.news.article;

import android.content.Intent;
import android.os.Handler;
import android.os.Message;

import com.meiji.toutiao.InitApp;
import com.meiji.toutiao.bean.news.NewsArticleBean;
import com.meiji.toutiao.news.info.InfoView;
import com.meiji.toutiao.utils.Api;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Meiji on 2016/12/15.
 */

public class ArticlePresenter implements IArticle.Presenter {

    private IArticle.View view;
    private IArticle.Model model;
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

    public ArticlePresenter(IArticle.View view) {
        this.view = view;
        this.model = new ArticleModel();
    }

    @Override
    public void doGetUrl(String parameter) {
        view.onShowRefreshing();
        this.category = parameter;
//        doRequestData(Api.getNewsUrl(parameter));
        String url = Api.getNewsArticleUrl(category, model.getmax_behot_time() + "");
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
        System.out.println("刷新新闻数量 " + model.getDataList().size());
        dataList.addAll(model.getDataList());
        view.onSetAdapter(dataList);
        view.onHideRefreshing();
    }

    @Override
    public void doRefresh() {
        view.onShowRefreshing();
        String url = Api.getNewsArticleUrl(category, model.getmax_behot_time() + "");
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
        Intent intent = new Intent(InitApp.AppContext, InfoView.class);
        intent.putExtra(InfoView.TAG, bean);
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        InitApp.AppContext.startActivity(intent);
        // 打印下点击的标题和链接
        System.out.println("点击的标题和链接---" + bean.getTitle() + "  " + bean.getDisplay_url());
    }
}
