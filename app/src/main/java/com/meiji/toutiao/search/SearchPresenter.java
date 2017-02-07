package com.meiji.toutiao.search;

import android.content.Intent;
import android.os.Handler;
import android.os.Message;
import android.util.Log;

import com.meiji.toutiao.InitApp;
import com.meiji.toutiao.bean.news.NewsArticleBean;
import com.meiji.toutiao.news.content.NewsContetnView;
import com.meiji.toutiao.utils.Api;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Meiji on 2017/2/7.
 */

class SearchPresenter implements ISearch.Presenter {

    private static final String TAG = "SearchPresenter";
    private int offset = 0;
    private String query;
    private ISearch.View view;
    private ISearch.Model model;
    private List<NewsArticleBean.DataBean> list = new ArrayList<>();
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

    SearchPresenter(ISearch.View view) {
        this.view = view;
        this.model = new SearchModel();
    }

    public void doGetUrl(String parameter) {
        view.onShowRefreshing();
        this.query = parameter;
        String url = Api.getSearchUrl(parameter, 0);
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
        list.addAll(model.getDataList());
        for (NewsArticleBean.DataBean dataBean : list) {
            dataBean.getTitle();
        }

        view.onSetAdapter(list);
        view.onHideRefreshing();
    }

    @Override
    public void doRefresh() {
        view.onShowRefreshing();
        offset += 20;
        String url = Api.getSearchUrl(query, offset);
        doRequestData(url);
    }

    @Override
    public void onFail() {
        view.onFail();
    }

    @Override
    public void doOnClickItem(int position) {
        NewsArticleBean.DataBean bean = list.get(position);
        Intent intent = new Intent(InitApp.AppContext, NewsContetnView.class);
        intent.putExtra(NewsContetnView.TAG, bean);
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        InitApp.AppContext.startActivity(intent);
        // 打印下点击的标题和链接
        Log.d(TAG, "doOnClickItem: " + "点击的标题和链接---" + bean.getTitle() + "  " + bean.getDisplay_url());
    }
}
