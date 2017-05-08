package com.meiji.toutiao.module.news.article;

import com.meiji.toutiao.bean.news.NewsArticleBean;
import com.meiji.toutiao.module.base.IBasePresenter;
import com.meiji.toutiao.module.base.IBaseView;

import java.util.List;

/**
 * Created by Meiji on 2016/12/15.
 */

public interface INewsArticle {

    interface View extends IBaseView {

        /**
         * 请求数据
         */
        void onLoadData();

        /**
         * 设置适配器
         */
        void onSetAdapter(List<NewsArticleBean.DataBean> list);
    }

    interface Presenter extends IBasePresenter {

        /**
         * 请求数据
         */
        void doLoadData(String... category);

        /**
         * 再起请求数据
         */
        void doLoadMoreData();

        /**
         * 设置适配器
         */
        void doSetAdapter(List<NewsArticleBean.DataBean> dataBeen);

        /**
         * 点击事件跳转
         */
        void doOnClickItem(int position);
    }

    @Deprecated
    interface Model {
        boolean requestData(String url);

        List<NewsArticleBean.DataBean> getDataList();

        int getMaxBehotTime();
    }
}
