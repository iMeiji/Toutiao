package com.meiji.toutiao.video.content;

import com.meiji.toutiao.news.comment.INewsComment;
import com.meiji.toutiao.news.comment.NewsCommentModel;
import com.meiji.toutiao.news.comment.NewsCommentPresenter;

/**
 * Created by Meiji on 2017/3/30.
 */

public class VideoContentPresenter extends NewsCommentPresenter implements IVideoContent.Presenter {

    private IVideoContent.View view;
    private INewsComment.Model model;

    public VideoContentPresenter(IVideoContent.View view) {
        super(view);
        this.view = view;
        this.model = new NewsCommentModel();
    }
}
