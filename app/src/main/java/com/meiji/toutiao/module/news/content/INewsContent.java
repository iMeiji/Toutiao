package com.meiji.toutiao.module.news.content;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;

import com.meiji.toutiao.bean.news.NewsArticleBean;
import com.meiji.toutiao.module.base.IBasePresenter;
import com.meiji.toutiao.module.base.IBaseView;

/**
 * Created by Meiji on 2016/12/17.
 */

interface INewsContent {

    interface View extends IBaseView<Presenter> {

        /**
         * 加载网页
         */
        void onSetWebView(String url, boolean flag);
    }

    interface Presenter extends IBasePresenter {

        /**
         * 请求数据
         */
        void doLoadData(NewsArticleBean.DataBean dataBean);

        /**
         * 查看评论
         */
        void doShowComment(FragmentActivity context, Fragment fragment);
    }
}
