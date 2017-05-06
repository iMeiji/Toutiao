package com.meiji.toutiao.module.news.joke.content;

import com.meiji.toutiao.bean.news.joke.JokeContentBean;

import java.util.List;

/**
 * Created by Meiji on 2016/12/28.
 */

interface IJokeContent {

    interface View {
        void onRequestData();

        void onSetAdapter(List<JokeContentBean.DataBean.GroupBean> list);

        void onShowRefreshing();

        void onHideRefreshing();

        void onFail();
    }

    interface Presenter {
        void doGetUrl(String parameter);

        void doRequestData(final String url);

        void doSetAdapter();

        void doRefresh();

        void onFail();

        void doOnClickItem(int position);
    }

    interface Model {
        boolean requestData(String url);

        List<JokeContentBean.DataBean.GroupBean> getDataList();

        int getmax_behot_time();
    }
}
