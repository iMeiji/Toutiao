package com.meiji.toutiao.module.photo.content;

import android.content.Context;

import com.meiji.toutiao.bean.photo.PhotoArticleBean;
import com.meiji.toutiao.bean.photo.PhotoGalleryBean;
import com.meiji.toutiao.module.base.IBasePresenter;
import com.meiji.toutiao.module.base.IBaseView;

/**
 * Created by Meiji on 2017/2/16.
 */

interface IPhotoContent {

    interface View extends IBaseView {

        void onSetImageBrowser(PhotoGalleryBean bean, int position);

        void onSaveImageSuccess();
    }

    interface Presenter extends IBasePresenter {

        void doSetImageBrowser();

        void doRequestData(PhotoArticleBean.DataBean dataBean);

        int doGetImageCount();

        void doSetPosition(int position);

        void doSaveImage();

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

        boolean SaveImage(String url, Context context);
    }

}
