package com.meiji.toutiao.other.joke.comment;

import android.os.Handler;
import android.os.Message;

import com.meiji.toutiao.api.JokeApi;
import com.meiji.toutiao.bean.other.joke.JokeCommentBean;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Meiji on 2017/1/1.
 */

class JokeCommentPresenter implements IJokeComment.Presenter {

    private IJokeComment.View view;
    private IJokeComment.Model model;
    private String jokeId;
    private String jokeCommentCount;
    private int offset = 0;
    private List<JokeCommentBean.DataBean.RecentCommentsBean> commentsList = new ArrayList<>();
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

    JokeCommentPresenter(IJokeComment.View view) {
        this.view = view;
        this.model = new JokeCommentModel();
    }

    @Override
    public void doGetUrl(String jokeId, String jokeCommentCount) {
        view.onShowRefreshing();
        this.jokeId = jokeId;
        this.jokeCommentCount = jokeCommentCount;
        String url = JokeApi.getJokeCommentUrl(jokeId, 20, 0);
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
        if (commentsList.size() != 0) {
            commentsList.clear();
        }
        commentsList.addAll(model.getDataList());
        view.onSetAdapter(commentsList);
        view.onHideRefreshing();
    }

    @Override
    public void doRefresh() {
        if (offset < Integer.parseInt(jokeCommentCount)) {
            offset += 20;
            String url = JokeApi.getJokeCommentUrl(jokeId, 20, offset);
            doRequestData(url);
        } else {
            view.onFinish();
            view.onHideRefreshing();
        }
    }

    @Override
    public void onFail() {
        view.onHideRefreshing();
        view.onFail();
    }
}
