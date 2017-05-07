package com.meiji.toutiao.module.video.content;

import android.os.Handler;
import android.os.Message;

import com.meiji.toutiao.module.news.comment.NewsCommentPresenter;

/**
 * Created by Meiji on 2017/3/30.
 */

public class VideoContentPresenter extends NewsCommentPresenter implements IVideoContent.Presenter {

    private IVideoContent.View view;
    private IVideoContent.Model model;
    private Handler vHandler = new Handler(new Handler.Callback() {
        @Override
        public boolean handleMessage(Message msg) {
            if (msg.what == 3) {
                doSetVideoPlay();
            }
            return false;
        }
    });

    public VideoContentPresenter(IVideoContent.View view) {
        super(view);
        this.view = view;
        this.model = new VideoContentModel();
    }

    @Override
    public void doLoadData(String group_id, String item_id) {

    }

    @Override
    public void doRequestData(String url) {

    }

    @Override
    public void doSetAdapter() {

    }

    @Override
    public void doRequestVideoData(final String videoid) {
        new Thread(new Runnable() {
            @Override
            public void run() {
                boolean vResult = model.requestVideoData(videoid);
                if (vResult) {
                    Message message = vHandler.obtainMessage(3);
                    message.sendToTarget();
                } else {
                    Message message = vHandler.obtainMessage(4);
                    message.sendToTarget();
                }
            }
        }).start();
    }

    @Override
    public void doSetVideoPlay() {
        view.onSetVideoPlay(model.getVideoUrl());
    }
}
