package com.meiji.toutiao.news;

import android.os.Handler;
import android.os.Message;

import com.meiji.toutiao.bean.NewsBean;
import com.meiji.toutiao.utils.Api;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Meiji on 2016/12/15.
 */

public class NewsPresenter implements INews.Presenter {

    private INews.View view;
    private INews.Model model;
    private List<NewsBean.DataBean> list = new ArrayList<>();
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
    private String url;
    private String category;

    public NewsPresenter(INews.View view) {
        this.view = view;
        this.model = new NewsModel(this);
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
        list.addAll(model.getDataList());
        view.onSetAdapter(list);
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
}
