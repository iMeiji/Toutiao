package com.meiji.toutiao.module.search;

import com.meiji.toutiao.bean.news.NewsArticleBean;
import com.meiji.toutiao.module.base.IBasePresenter;
import com.meiji.toutiao.module.base.IBaseView;

import java.util.List;

/**
 * Created by Meiji on 2017/2/7.
 */
@Deprecated
interface ISearch {

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
        void doLoadData(String... parameter);

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
}
