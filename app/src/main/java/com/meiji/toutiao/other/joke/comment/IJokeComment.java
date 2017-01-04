package com.meiji.toutiao.other.joke.comment;

import com.meiji.toutiao.bean.other.joke.JokeCommentBean;

import java.util.List;

/**
 * Created by Meiji on 2017/1/1.
 */

interface IJokeComment {

    interface View {
        void onRequestData();

        void onSetAdapter(List<JokeCommentBean.DataBean.CommentsBean> list);

        void onShowRefreshing();

        void onHideRefreshing();

        void onFail();

        void onFinish();
    }

    interface Presenter {

        void doGetUrl(String jokeId, String jokeCommentCount);

        void doRequestData(String url);

        void doSetAdapter();

        void doRefresh();

        void onFail();
    }

    interface Model {
        boolean requestData(String url);

        List<JokeCommentBean.DataBean.CommentsBean> getDataList();

    }
}
