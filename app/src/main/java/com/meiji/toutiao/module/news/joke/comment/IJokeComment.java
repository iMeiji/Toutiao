package com.meiji.toutiao.module.news.joke.comment;

import com.meiji.toutiao.bean.news.joke.JokeCommentBean;

import java.util.List;

/**
 * Created by Meiji on 2017/1/1.
 */

interface IJokeComment {

    interface View {

        /**
         * 设置适配器
         */
        void onSetAdapter(List<JokeCommentBean.DataBean.RecentCommentsBean> list);

        /**
         * 请求数据
         */
        void onLoadData();

        /**
         * 显示加载动画
         */
        void onShowLoading();

        /**
         * 隐藏加载
         */
        void onHideLoading();

        /**
         * 显示网络错误
         */
        void onShowNetError();

        /**
         * 加载完毕
         */
        void onShowNoMore();

    }

    interface Presenter {

        /**
         * 请求数据
         */
        void doLoadData(String... jokeId_Count);

        /**
         * 再起请求数据
         */
        void doLoadMoreData();

        /**
         * 设置适配器
         */
        void doSetAdapter(List<JokeCommentBean.DataBean.RecentCommentsBean> commentsBeanList);

        /**
         * 刷新
         */
        void doRefresh();

        /**
         * 显示网络错误
         */
        void doShowNetError();

        /**
         * 加载完毕
         */
        void doShowNoMore();

    }

    @Deprecated
    interface Model {
        boolean requestData(String url);

        List<JokeCommentBean.DataBean.RecentCommentsBean> getDataList();

    }
}
