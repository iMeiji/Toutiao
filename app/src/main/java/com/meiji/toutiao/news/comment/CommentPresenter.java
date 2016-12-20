package com.meiji.toutiao.news.comment;

import android.os.Handler;
import android.os.Message;

import com.meiji.toutiao.bean.news.NewsCommentBean;
import com.meiji.toutiao.utils.Api;

import java.util.List;

/**
 * Created by Meiji on 2016/12/20.
 */

public class CommentPresenter implements IComment.Presenter {

    private IComment.View view;
    private IComment.Model model;
    private Handler handler = new Handler(new Handler.Callback() {
        @Override
        public boolean handleMessage(Message message) {
            if (message.what == 1) {
                doSetAdapter();
            }
            if (message.what == 0) {
                onFail();
            }
            return false;
        }
    });


    public CommentPresenter(IComment.View view) {
        this.view = view;
        this.model = new CommentModel();
    }

    @Override
    public void doRequestData(String group_id, String item_id) {
        final String url = Api.getNewsCommentUrl(group_id, item_id, 0, 50);
        new Thread(new Runnable() {
            @Override
            public void run() {
                boolean result = model.requestData(url);
                if (result) {
                    Message message = handler.obtainMessage(1);
                    message.sendToTarget();
                } else {
                    Message message = handler.obtainMessage(0);
                    message.sendToTarget();
                }
            }
        }).start();
    }

    @Override
    public void doSetAdapter() {
        List<NewsCommentBean.DataBean.CommentsBean> commentsBeanList = model.getDataList();
        view.onSetAdapter(commentsBeanList);
    }

    @Override
    public void doRefresh() {

    }

    @Override
    public void onFail() {

    }
}
