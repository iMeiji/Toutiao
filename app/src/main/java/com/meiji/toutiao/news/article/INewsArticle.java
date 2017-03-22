package com.meiji.toutiao.news.article;

import com.meiji.toutiao.bean.news.NewsArticleBean;

import java.util.List;

/**
 * Created by Meiji on 2016/12/15.
 */

interface INewsArticle {

    interface View {
        void onRequestData();

        void onSetAdapter(List<NewsArticleBean.DataBean> list);

        void onShowRefreshing();

        void onHideRefreshing();

        void onFail();
    }

    interface Presenter {
        void doGetUrl(String parameter);

        void doRequestData(String url);

        void doSetAdapter();

        void doRefresh();

        void onFail();

        void doOnClickItem(int position);
    }

    interface Model {
        boolean requestData(String url);

        List<NewsArticleBean.DataBean> getDataList();

        int getMaxBehotTime();
    }
}
