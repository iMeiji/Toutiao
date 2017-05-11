package com.meiji.toutiao.module.video.article;

import com.meiji.toutiao.bean.video.VideoArticleBean;
import com.meiji.toutiao.module.base.IBasePresenter;
import com.meiji.toutiao.module.base.IBaseView;

import java.util.List;

/**
 * Created by Meiji on 2017/3/29.
 */

public interface IVideoArticle {

    interface View extends IBaseView<Presenter> {

        /**
         * 请求数据
         */
        void onLoadData();

        /**
         * 设置适配器
         */
        void onSetAdapter(List<VideoArticleBean.DataBean> list);
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
        void doSetAdapter(List<VideoArticleBean.DataBean> dataBeen);

        /**
         * 点击事件跳转
         */
        void doOnClickItem(int position);
    }
}
