package com.meiji.toutiao.module.news.content;

import com.meiji.toutiao.bean.news.MultiNewsArticleDataBean;
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
        void doLoadData(MultiNewsArticleDataBean dataBean);
    }
}
