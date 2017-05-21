package com.meiji.toutiao.module.joke.content;

import com.meiji.toutiao.module.base.IBasePresenter;
import com.meiji.toutiao.module.base.IBaseView;

/**
 * Created by Meiji on 2016/12/28.
 */

interface IJokeContent {

    interface View extends IBaseView<Presenter> {

        /**
         * 请求数据
         */
        void onLoadData();
    }

    interface Presenter extends IBasePresenter {

        /**
         * 请求数据
         */
        void doLoadData();

        /**
         * 再起请求数据
         */
        void doLoadMoreData();

        /**
         * 设置适配器
         */
        void doSetAdapter();

        /**
         * 点击事件跳转
         */
        void doOnClickItem(int position);
    }
}
