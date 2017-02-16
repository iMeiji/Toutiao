package com.meiji.toutiao.photo.content;

import com.meiji.toutiao.bean.photo.PhotoArticleBean;
import com.meiji.toutiao.bean.photo.PhotoGalleryBean;

/**
 * Created by Meiji on 2017/2/16.
 */

interface IPhotoContent {

    interface View {

        void onSetImageBrwoser(PhotoGalleryBean bean, int position);

        /**
         * 请求数据失败
         */
        void onFail();

        void onShowRefreshing();

        void onHideRefreshing();
    }

    interface Presenter {

        void doSetImageBrwoser();

        /**
         * 请求数据失败
         */
        void onFail();

        void doRequestData(PhotoArticleBean.DataBean dataBean);

        void doGetComment();

        int getImageCount();

    }

    interface Model {

        /**
         * 请求数据
         */
        boolean getRequestData(String url);

        /**
         * 返回内容
         */
        PhotoGalleryBean getData();
    }

}
