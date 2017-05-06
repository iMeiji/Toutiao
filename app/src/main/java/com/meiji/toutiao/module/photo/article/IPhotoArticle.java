package com.meiji.toutiao.module.photo.article;

import com.meiji.toutiao.bean.photo.PhotoArticleBean;

import java.util.List;

interface IPhotoArticle {

    interface View {
        void onRequestData();

        void onSetAdapter(List<PhotoArticleBean.DataBean> list);

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

        void doOnClickItem(int position);
    }

    interface Model {
        boolean requestData(String url);

        List<PhotoArticleBean.DataBean> getDataList();

        int getmax_behot_time();
    }
}
