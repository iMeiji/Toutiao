package com.meiji.toutiao.module.news.content;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;

import com.meiji.toutiao.bean.news.NewsArticleBean;

/**
 * Created by Meiji on 2016/12/17.
 */

interface INewsContent {

    interface View {

        /**
         * 加载网页
         */
        void onSetWebView(String url, boolean flag);

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

        void doLoadData(NewsArticleBean.DataBean dataBean);

        void doShowComment(FragmentActivity context, Fragment fragment);
    }

    @Deprecated
    interface Model {

        /**
         * 请求数据
         */
        boolean getRequestData(String url);

        /**
         * 返回内容
         */
        String getHtml();
    }
}
