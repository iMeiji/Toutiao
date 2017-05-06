package com.meiji.toutiao.module.news.comment;

import com.meiji.toutiao.bean.news.NewsCommentBean;

import java.util.List;

/**
 * Created by Meiji on 2016/12/20.
 */

public interface INewsComment {

    interface View {
        void onRequestData();

        void onSetAdapter(List<NewsCommentBean.DataBean.CommentsBean> list);

        void onShowRefreshing();

        void onHideRefreshing();

        void onFail();
    }

    interface Presenter {

        void doGetUrl(String group_id, String item_id);

        void doRequestData(String url);

        void doSetAdapter();

        void doRefresh();

        void onFail();
    }

    interface Model {
        boolean requestData(String url);

        List<NewsCommentBean.DataBean.CommentsBean> getDataList();
    }
}
