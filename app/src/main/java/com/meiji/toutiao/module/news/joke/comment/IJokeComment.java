package com.meiji.toutiao.module.news.joke.comment;

import com.meiji.toutiao.bean.news.joke.JokeCommentBean;
import com.meiji.toutiao.module.base.IBasePresenter;
import com.meiji.toutiao.module.base.IBaseView;

import java.util.List;

/**
 * Created by Meiji on 2017/1/1.
 */

interface IJokeComment {

    interface View extends IBaseView {

        /**
         * 设置适配器
         */
        void onSetAdapter(List<JokeCommentBean.DataBean.RecentCommentsBean> list);

        /**
         * 请求数据
         */
        void onLoadData();

        /**
         * 加载完毕
         */
        void onShowNoMore();

    }

    interface Presenter extends IBasePresenter {

        /**
         * 请求数据
         */
        void doLoadData(String... jokeId_Count);

        /**
         * 再起请求数据
         */
        void doLoadMoreData();

        /**
         * 设置适配器
         */
        void doSetAdapter(List<JokeCommentBean.DataBean.RecentCommentsBean> commentsBeanList);

        /**
         * 加载完毕
         */
        void doShowNoMore();
    }

    @Deprecated
    interface Model {
        boolean requestData(String url);

        List<JokeCommentBean.DataBean.RecentCommentsBean> getDataList();

    }
}
