package com.meiji.toutiao.module.photo.content;

import com.meiji.toutiao.bean.photo.PhotoGalleryBean;
import com.meiji.toutiao.module.base.IBasePresenter;
import com.meiji.toutiao.module.base.IBaseView;

/**
 * Created by Meiji on 2017/2/16.
 */

interface IPhotoContent {

    interface View extends IBaseView<Presenter> {

        /**
         * 设置图片浏览器
         */
        void onSetImageBrowser(PhotoGalleryBean bean, int position);

        /**
         * 解析 HTML 失败, 可以用 WebView 加载内容
         */
        void onSetWebView(String url, boolean flag);

        /**
         * 保存图片成功
         */
        void onShowSaveSuccess();
    }

    interface Presenter extends IBasePresenter {

        /**
         * 请求数据
         */
        void doLoadData(String... category);

        /**
         * 返回图片数量
         */
        int doGetImageCount();

        /**
         * 设置图片位置
         */
        void doSetPosition(int position);

        /**
         * 保存图片
         */
        void doSaveImage();

        void doGoMediaHome(String media_url);
    }
}
