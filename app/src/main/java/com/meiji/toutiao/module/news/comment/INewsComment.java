package com.meiji.toutiao.module.news.comment;

import com.meiji.toutiao.bean.news.NewsCommentMobileBean;
import com.meiji.toutiao.module.base.IBasePresenter;
import com.meiji.toutiao.module.base.IBaseView;

import java.util.List;

/**
 * Created by Meiji on 2016/12/20.
 */

public interface INewsComment {

    interface View extends IBaseView<Presenter> {

        /**
         * 请求数据
         */
        void onLoadData();

        /**
         * 设置适配器
         */
        void onSetAdapter(List<NewsCommentMobileBean.DataBean.CommentBean> list);

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
        void doSetAdapter(List<NewsCommentMobileBean.DataBean.CommentBean> list);

        /**
         * 加载完毕
         */
        void doShowNoMore();

        /**
         * 获取复制内容
         */
        String doGetCopyContent(int position);
    }
}
