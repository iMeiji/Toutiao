package com.meiji.toutiao.search;

import com.meiji.toutiao.bean.news.NewsArticleBean;

import java.util.List;

/**
 * Created by Meiji on 2017/2/7.
 */

public class ISearch {

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
    }
}
