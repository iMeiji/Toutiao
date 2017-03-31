package com.meiji.toutiao.video.content;

import com.meiji.toutiao.bean.news.NewsCommentBean;
import com.meiji.toutiao.news.comment.INewsComment;

import java.util.List;

/**
 * Created by Meiji on 2017/3/30.
 */

public interface IVideoContent {

    interface View extends INewsComment.View {
        void onRequestData();

        void onSetAdapter(List<NewsCommentBean.DataBean.CommentsBean> list);

        void onFail();

        void onSetVideoPlay(String url);
    }

    interface Presenter extends INewsComment.Presenter {

        void doGetUrl(String group_id, String item_id);

        void doRequestData(String url);

        void doSetAdapter();

        void doRefresh();

        void onFail();

        void doRequestVideoData(String videoid);

        void doSetVideoPlay();
    }

    interface Model extends INewsComment.Model {
        boolean requestData(String url);

        List<NewsCommentBean.DataBean.CommentsBean> getDataList();

        boolean requestVideoData(String videoid);

        String getVideoUrl();
    }
}
