package com.meiji.toutiao.video.article;

import com.meiji.toutiao.bean.video.VideoArticleBean;

import java.util.List;

/**
 * Created by Meiji on 2017/3/29.
 */

public interface IVideoArticle {

    interface View {
        void onRequestData();

        void onSetAdapter(List<VideoArticleBean.DataBean> list);

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

        List<VideoArticleBean.DataBean> getDataList();
    }
}
