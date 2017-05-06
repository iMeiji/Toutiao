package com.meiji.toutiao.module.news.article;

import com.meiji.toutiao.bean.news.NewsArticleBean;

import java.util.List;

/**
 * Created by Meiji on 2016/12/15.
 */

public interface INewsArticle {

    interface View {

        /**
         * 请求数据
         */
        void onLoadData();

        /**
         * 设置适配器
         */
        void onSetAdapter(List<NewsArticleBean.DataBean> list);

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
        void doLoadData(String parameter);

        /**
         * 再起请求数据
         */
        void doLoadMoreData();

        /**
         * 设置适配器
         */
        void doSetAdapter(List<NewsArticleBean.DataBean> dataBeen);

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

        List<NewsArticleBean.DataBean> getDataList();

        int getMaxBehotTime();
    }
}
