package com.meiji.toutiao.news.info;

import com.meiji.toutiao.bean.news.NewsArticleBean;

/**
 * Created by Meiji on 2016/12/17.
 */

public interface IInfo {

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

        void doGetComment();
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
