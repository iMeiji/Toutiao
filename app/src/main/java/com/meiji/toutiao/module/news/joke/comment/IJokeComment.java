package com.meiji.toutiao.module.news.joke.comment;

import com.meiji.toutiao.bean.news.joke.JokeCommentBean;

import java.util.List;

/**
 * Created by Meiji on 2017/1/1.
 */

interface IJokeComment {

    interface View {
        void onRequestData();

        void onSetAdapter(List<JokeCommentBean.DataBean.RecentCommentsBean> list);

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

        List<JokeCommentBean.DataBean.RecentCommentsBean> getDataList();

    }
}
