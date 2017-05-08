package com.meiji.toutiao.module.news.comment;

import com.meiji.toutiao.bean.news.NewsCommentBean;
import com.meiji.toutiao.module.base.IBasePresenter;
import com.meiji.toutiao.module.base.IBaseView;

import java.util.List;

/**
 * Created by Meiji on 2016/12/20.
 */

public interface INewsComment {

    interface View extends IBaseView {

        /**
         * 请求数据
         */
        void onLoadData();

        /**
         * 设置适配器
         */
        void onSetAdapter(List<NewsCommentBean.DataBean.CommentsBean> list);

        /**
         * 加载完毕
         */
        void onShowNoMore();
    }

    interface Presenter extends IBasePresenter {

        /**
         * 请求数据
         */
        void doLoadData(String... groupId_ItemId);

        /**
         * 再起请求数据
         */
        void doLoadMoreData();

        /**
         * 设置适配器
         */
        void doSetAdapter(List<NewsCommentBean.DataBean.CommentsBean> commentsBeen);

        /**
         * 加载完毕
         */
        void doShowNoMore();
    }

    @Deprecated
    interface Model {
        boolean requestData(String url);

        List<NewsCommentBean.DataBean.CommentsBean> getDataList();
    }
}
