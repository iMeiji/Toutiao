package com.meiji.toutiao.module.wenda.detail;

import com.meiji.toutiao.bean.news.NewsCommentBean;
import com.meiji.toutiao.module.base.IBaseListView;
import com.meiji.toutiao.module.base.IBasePresenter;

import java.util.List;

/**
 * Created by Meiji on 2017/5/23.
 */

public interface IWendaDetail {

    interface View extends IBaseListView<Presenter> {

        /**
         * 加载网页
         */
        void onSetWebView(String baseUrl, boolean flag);

        /**
         * 请求数据
         */
        void onLoadData();
    }

    interface Presenter extends IBasePresenter {

        /**
         * 请求数据
         */
        void doLoadData(String url);

        /**
         * 加载评论
         */
        void doLoadComment(String... ansId);

        /**
         * 加载更多评论
         */
        void doLoadMoreComment();

        /**
         * 设置适配器
         */
        void doSetAdapter(List<NewsCommentBean.DataBean.CommentBean> list);

        /**
         * 加载完毕
         */
        void doShowNoMore();
    }
}
