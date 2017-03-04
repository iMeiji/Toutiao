package com.meiji.toutiao.other.joke.content;

import android.content.Intent;
import android.os.Handler;
import android.os.Message;

import com.meiji.toutiao.InitApp;
import com.meiji.toutiao.api.JokeApi;
import com.meiji.toutiao.bean.other.joke.JokeContentBean;
import com.meiji.toutiao.other.joke.comment.JokeCommentView;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Meiji on 2016/12/28.
 */

class JokeContentPresenter implements IJokeContent.Presenter {

    private IJokeContent.View view;
    private IJokeContent.Model model;
    private String category;
    private int offset = 0;
    private List<JokeContentBean.DataBean.GroupBean> groupList = new ArrayList<>();
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

    public JokeContentPresenter(IJokeContent.View view) {
        this.view = view;
        model = new JokeContentModel();
    }

    @Override
    public void doGetUrl(String parameter) {
        view.onShowRefreshing();
        this.category = parameter;
        String url = JokeApi.getJokeContentUrl();
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
        if (groupList.size() != 0) {
            groupList.clear();
        }
        groupList.addAll(model.getDataList());
        view.onSetAdapter(groupList);
        view.onHideRefreshing();
    }

    @Override
    public void doRefresh() {
        view.onShowRefreshing();
        //offset += 20;
        String url = JokeApi.getJokeContentUrl();
        doRequestData(url);
    }

    @Override
    public void onFail() {
        view.onHideRefreshing();
        view.onFail();
    }

    @Override
    public void doOnClickItem(int position) {
        JokeContentBean.DataBean.GroupBean bean = groupList.get(position);
        // 转跳到评论页面
        Intent intent = new Intent(InitApp.AppContext, JokeCommentView.class);
        intent.putExtra(JokeCommentView.TAG, bean);
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        InitApp.AppContext.startActivity(intent);
    }
}
