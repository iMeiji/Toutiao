package com.meiji.toutiao.news.content;

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
         * 请求数据失败
         */
        void onFail();

        void onShowRefreshing();

        void onHideRefreshing();
    }

    interface Presenter {

        /**
         * 设置浏览器
         */
        void doSetWebView();

        /**
         * 请求数据失败
         */
        void onFail();

        void doRequestData(NewsArticleBean.DataBean dataBean);

        void doGetComment(FragmentActivity context, Fragment fragment);
    }

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
