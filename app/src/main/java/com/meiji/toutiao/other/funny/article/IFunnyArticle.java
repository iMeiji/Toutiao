package com.meiji.toutiao.other.funny.article;

import com.meiji.toutiao.bean.other.funny.FunnyArticleBean;

import java.util.List;

/**
 * Created by Meiji on 2017/1/2.
 */

interface IFunnyArticle {

    interface View {
        void onRequestData();

        void onSetAdapter(List<FunnyArticleBean.DataBean> list);

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

        List<FunnyArticleBean.DataBean> getDataList();

        int getmax_behot_time();
    }
}
