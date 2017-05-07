package com.meiji.toutiao.module.news.joke.content;

import com.meiji.toutiao.bean.news.joke.JokeContentBean;

import java.util.List;

/**
 * Created by Meiji on 2016/12/28.
 */

interface IJokeContent {

    interface View {

        /**
         * 设置适配器
         */
        void onSetAdapter(List<JokeContentBean.DataBean.GroupBean> list);

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

    }

    interface Presenter {

        /**
         * 请求数据
         */
        void doLoadData();

        /**
         * 再起请求数据
         */
        void doLoadMoreData();

        /**
         * 设置适配器
         */
        void doSetAdapter();

        /**
         * 刷新数据
         */
        void doRefresh();

        /**
         * 显示网络错误
         */
        void doShowNetError();

        /**
         * 点击事件跳转
         */
        void doOnClickItem(int position);
    }

    @Deprecated
    interface Model {
        boolean requestData(String url);

        List<JokeContentBean.DataBean.GroupBean> getDataList();

        int getmax_behot_time();
    }
}
