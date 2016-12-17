package com.meiji.toutiao.news.content;

/**
 * Created by Meiji on 2016/12/17.
 */

public interface IContent {

    interface View {

        /**
         * 加载网页
         */
        void onSetWebView(String url, boolean flag);

        /**
         * 请求数据失败
         */
        void onFail();
    }

    interface Presenter {
        /**
         * 请求数据
         */
        void doRequestData(String url);

        /**
         * 设置浏览器
         */
        void doSetWebView(boolean flag);

        /**
         * 请求数据失败
         */
        void onFail();
    }

    interface Model {

        /**
         * 请求数据
         */
        boolean getRequestData(String url);

        /**
         * 返回内容
         */
        String getHtml(boolean flag);
    }


}
