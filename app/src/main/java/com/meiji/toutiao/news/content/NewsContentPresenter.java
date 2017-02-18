package com.meiji.toutiao.news.content;

import android.content.Intent;
import android.os.Handler;
import android.os.Message;
import android.util.Log;

import com.meiji.toutiao.InitApp;
import com.meiji.toutiao.bean.news.NewsArticleBean;
import com.meiji.toutiao.news.comment.NewsCommentView;
import com.meiji.toutiao.utils.Api;

/**
 * Created by Meiji on 2016/12/17.
 */

class NewsContentPresenter implements INewsContent.Presenter {

    private static final String TAG = "NewsContentPresenter";
    private INewsContent.View view;
    private INewsContent.Model model;
    private String group_id;
    private String item_id;
    private Handler handler = new Handler(new Handler.Callback() {
        @Override
        public boolean handleMessage(Message message) {
            if (message.what == 1) {
                doSetWebView();
            }
            if (message.what == 0) {
                System.out.println("设置未处理的url --");
                view.onSetWebView(null, false);
            }
            return false;
        }
    });

    NewsContentPresenter(INewsContent.View view) {
        this.view = view;
        model = new NewsContentModel();
    }

    @Override
    public void doRequestData(NewsArticleBean.DataBean dataBean) {
        group_id = dataBean.getGroup_id() + "";
        item_id = dataBean.getItem_id() + "";

        String item_seo_url = dataBean.getItem_seo_url();
        final String url = Api.getNewsInfoUrl(item_seo_url);
        new Thread(new Runnable() {
            @Override
            public void run() {
                boolean result = model.getRequestData(url);
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
    public void doSetWebView() {
        String html = model.getHtml();
        view.onSetWebView(html, true);
    }

    @Override
    public void onFail() {

    }

    @Override
    public void doGetComment() {
        Intent intent = new Intent(InitApp.AppContext, NewsCommentView.class);
        intent.putExtra(NewsCommentView.GROUP_ID, group_id);
        intent.putExtra(NewsCommentView.ITEM_ID, item_id);
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        InitApp.AppContext.startActivity(intent);
        // 打印下点击的标题和链接
        Log.d(TAG, "doGetComment: " + group_id + "  " + item_id);
    }
}
