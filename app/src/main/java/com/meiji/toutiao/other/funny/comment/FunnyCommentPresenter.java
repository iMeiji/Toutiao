package com.meiji.toutiao.other.funny.comment;

import android.os.Handler;
import android.os.Message;

import com.meiji.toutiao.bean.other.funny.FunnyCommentBean;
import com.meiji.toutiao.utils.Api;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Meiji on 2017/1/25.
 */

class FunnyCommentPresenter implements IFunnyComment.Presenter {

    private IFunnyComment.View view;
    private IFunnyComment.Model model;
    private String group_id;
    private String item_id;
    private int offset = 0;
    private List<FunnyCommentBean.DataBean.CommentsBean> commentsBeanList = new ArrayList<>();
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

    FunnyCommentPresenter(IFunnyComment.View view) {
        this.view = view;
        this.model = new FunnyCommentModel();
    }

    @Override
    public void doGetUrl(String group_id, String item_id) {
        view.onShowRefreshing();
        this.group_id = group_id;
        this.item_id = item_id;
        String url = Api.getNewsCommentUrl(group_id, item_id, 0, 20);
        doRequestData(url);
    }

    @Override
    public void doRequestData(final String url) {
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
        if (commentsBeanList.size() != 0) {
            commentsBeanList.clear();
        }
        commentsBeanList.addAll(model.getDataList());
        view.onSetAdapter(commentsBeanList);
        view.onHideRefreshing();
    }

    @Override
    public void doRefresh() {
        view.onShowRefreshing();
        offset += 20;
        String url = Api.getNewsCommentUrl(group_id, item_id, offset, 20);
        doRequestData(url);
    }

    @Override
    public void onFail() {
        view.onHideRefreshing();
        view.onFail();
    }
}
