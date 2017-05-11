package com.meiji.toutiao.module.video.content;

import com.meiji.toutiao.module.news.comment.INewsComment;

/**
 * Created by Meiji on 2017/3/30.
 */

public interface IVideoContent {

    interface View extends INewsComment.View {

        /**
         * 设置播放器
         */
        void onSetVideoPlay(String url);
    }

    interface Presenter extends INewsComment.Presenter {

        /**
         * 请求数据
         */
        void doLoadVideoData(String videoid);
    }
}
