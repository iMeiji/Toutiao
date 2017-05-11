package com.meiji.toutiao.module.news.joke.content;

import com.meiji.toutiao.bean.news.joke.JokeContentBean;
import com.meiji.toutiao.module.base.IBasePresenter;
import com.meiji.toutiao.module.base.IBaseView;

import java.util.List;

/**
 * Created by Meiji on 2016/12/28.
 */

interface IJokeContent {

    interface View extends IBaseView<Presenter> {

        /**
         * 设置适配器
         */
        void onSetAdapter(List<JokeContentBean.DataBean.GroupBean> list);

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
