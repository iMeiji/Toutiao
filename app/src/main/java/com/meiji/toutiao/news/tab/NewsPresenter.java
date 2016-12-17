package com.meiji.toutiao.news.tab;

import android.content.Intent;
import android.os.Handler;
import android.os.Message;

import com.meiji.toutiao.InitApp;
import com.meiji.toutiao.bean.NewsBean;
import com.meiji.toutiao.news.content.ContentView;
import com.meiji.toutiao.utils.Api;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Meiji on 2016/12/15.
 */

public class NewsPresenter implements INews.Presenter {

    private INews.View view;
    private INews.Model model;
    private List<NewsBean.DataBean> dataList = new ArrayList<>();
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

    public NewsPresenter(INews.View view) {
        this.view = view;
        this.model = new NewsModel();
    }

    @Override
    public void doGetUrl(String parameter) {
        view.onShowRefreshing();
        this.category = parameter;
        doRequestData(Api.getNewsUrl(parameter));
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
        String url = Api.getNewsRefreshUrl(category, model.getmax_behot_time() + "");
        doRequestData(url);
    }

    @Override
    public void onFail() {
        view.onHideRefreshing();
        view.onFail();
    }

    @Override
    public void doOnClickItem(int position) {
        NewsBean.DataBean bean = dataList.get(position);
        String title = bean.getTitle();
        String display_url = bean.getDisplay_url();
        Intent intent = new Intent(InitApp.AppContext, ContentView.class);
        intent.putExtra(ContentView.DISPLAY_URL, display_url);
        intent.putExtra(ContentView.TITLR, title);
        intent.putExtra(ContentView.TITLR, title);
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        InitApp.AppContext.startActivity(intent);
        // 打印下点击的标题和链接
        System.out.println("doOnClickItem---" + title + "  " + display_url);
    }
}
