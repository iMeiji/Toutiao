package com.meiji.toutiao.media.article;

import com.meiji.toutiao.bean.media.MediaArticleBean;
import com.meiji.toutiao.bean.media.MediaChannelBean;

import java.util.List;

/**
 * Created by Meiji on 2017/4/11.
 */

public interface IMediaArticle {

    interface View {
        void onRequestData();

        void onSetAdapter(List<MediaArticleBean.DataBean> list);

        void onShowRefreshing();

        void onHideRefreshing();

        void onFail();

        void onFinish();
    }

    interface Presenter {
        void doGetUrl(String mediaId);

        void doRequestData(String url);

        void doSetAdapter();

        void doRefresh();

        void onFail();

        void doOnClickItem(MediaArticleBean.DataBean bean, MediaChannelBean mediaChannelBean);
    }

    interface Model {
        long getItemId();

        long getGroupId();

        boolean requestData(String url);

        List<MediaArticleBean.DataBean> getDataList();

        int getMaxBehotTime();

        void getCommentRequestData(String articleUrl);
    }
}
