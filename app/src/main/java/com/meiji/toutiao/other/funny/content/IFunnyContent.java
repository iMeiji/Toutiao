package com.meiji.toutiao.other.funny.content;

/**
 * Created by Meiji on 2017/1/3.
 */

interface IFunnyContent {

    interface View {
        void onRequestData();

        void onSetWebView(String url, boolean flag);

        void onShowRefreshing();

        void onHideRefreshing();

        void onFail();
    }

    interface Presenter {

        void doGetUrl(String group_id);

        void doRequestData(String url);

        void doSetWebView();

        void onFail();

        void doGetComment();
    }

    interface Model {
        boolean requestData(String url);

        String getHtml();
    }
}
