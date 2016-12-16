package com.meiji.toutiao.news;

import com.meiji.toutiao.bean.NewsBean;

import java.util.List;

/**
 * Created by Meiji on 2016/12/15.
 */

interface INews {

    interface View {
        void onRequestData();

        void onSetAdapter(List<NewsBean.DataBean> list);

        void onShowRefreshing();

        void onHideRefreshing();

        void onFail();
    }

    interface Presenter {
        void doGetUrl(String parameter);

        void doRequestData(String url);

        void doSetAdapter();

        void doRefresh();

        void onFail();
    }

    interface Model {
        boolean requestData(String url);

        List<NewsBean.DataBean> getDataList();

        int getmax_behot_time();
    }
}
