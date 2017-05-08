package com.meiji.toutiao.module.photo.article;

import com.meiji.toutiao.bean.photo.PhotoArticleBean;
import com.meiji.toutiao.module.base.IBasePresenter;
import com.meiji.toutiao.module.base.IBaseView;

import java.util.List;

interface IPhotoArticle {

    interface View extends IBaseView {

        /**
         * 请求数据
         */
        void onLoadData();

        /**
         * 设置适配器
         */
        void onSetAdapter(List<PhotoArticleBean.DataBean> list);
    }

    interface Presenter extends IBasePresenter {

        /**
         * 请求数据
         */
        void doLoadData(String... category);

        /**
         * 再起请求数据
         */
        void doLoadMoreData();

        /**
         * 设置适配器
         */
        void doSetAdapter(List<PhotoArticleBean.DataBean> dataBeen);

        /**
         * 点击事件跳转
         */
        void doOnClickItem(int position);
    }

    @Deprecated
    interface Model {
        boolean requestData(String url);

        List<PhotoArticleBean.DataBean> getDataList();

        int getmax_behot_time();
    }
}
