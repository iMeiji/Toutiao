package com.meiji.toutiao.news.content;

import android.os.Handler;
import android.os.Message;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;

import com.meiji.toutiao.R;
import com.meiji.toutiao.bean.news.NewsArticleBean;
import com.meiji.toutiao.news.comment.NewsCommentFragment;

/**
 * Created by Meiji on 2016/12/17.
 */

class NewsContentPresenter implements INewsContent.Presenter {

    private static final String TAG = "NewsContentPresenter";
    private static final int HTTP_REQUEST_FAIL = 0;
    private static final int HTTP_REQUEST_SUCCESS = 1;
    private INewsContent.View view;
    private INewsContent.Model model;
    private String group_id;
    private String item_id;
    private Handler handler = new Handler(new Handler.Callback() {
        @Override
        public boolean handleMessage(Message message) {
            if (message.what == HTTP_REQUEST_SUCCESS) {
                doSetWebView();
            }
            if (message.what == HTTP_REQUEST_FAIL) {
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
        final String url = dataBean.getDisplay_url();

        new Thread(new Runnable() {
            @Override
            public void run() {
                boolean result = model.getRequestData(url);
                if (result) {
                    Message message = handler.obtainMessage(HTTP_REQUEST_SUCCESS);
                    message.sendToTarget();
                } else {
                    Message message = handler.obtainMessage(HTTP_REQUEST_FAIL);
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
    public void doGetComment(FragmentActivity context, Fragment fragment) {
        context.getSupportFragmentManager().beginTransaction()
                .add(R.id.container, NewsCommentFragment.newInstance(group_id, item_id), NewsCommentFragment.class.getName())
                .addToBackStack(NewsCommentFragment.class.getName())
                .hide(fragment)
                .commit();
    }
}
