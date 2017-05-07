package com.meiji.toutiao.module.news.comment;

import com.meiji.toutiao.bean.news.NewsCommentBean;

import java.util.List;

/**
 * Created by Meiji on 2016/12/20.
 */

public interface INewsComment {

    interface View {

        /**
         * 请求数据
         */
        void onLoadData();

        /**
         * 设置适配器
         */
        void onSetAdapter(List<NewsCommentBean.DataBean.CommentsBean> list);

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
        void doLoadData(String... groupId_ItemId);

        /**
         * 再起请求数据
         */
        void doLoadMoreData();

        /**
         * 设置适配器
         */
        void doSetAdapter(List<NewsCommentBean.DataBean.CommentsBean> commentsBeen);

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

        List<NewsCommentBean.DataBean.CommentsBean> getDataList();
    }
}
