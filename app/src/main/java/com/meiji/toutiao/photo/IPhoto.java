package com.meiji.toutiao.photo;

import com.meiji.toutiao.bean.photo.PhotoViewBean;

import java.util.List;

interface IPhoto {

    interface View {
        void onRequestData();

        void onSetAdapter(List<PhotoViewBean.DataBean> list);

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

        List<PhotoViewBean.DataBean> getDataList();

        int getmax_behot_time();
    }
}
