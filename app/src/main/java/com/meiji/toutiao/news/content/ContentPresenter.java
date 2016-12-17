package com.meiji.toutiao.news.content;

import android.os.Handler;
import android.os.Message;

/**
 * Created by Meiji on 2016/12/17.
 */

public class ContentPresenter implements IContent.Presenter {

    private IContent.View view;
    private IContent.Model model;
    private Handler handler = new Handler(new Handler.Callback() {
        @Override
        public boolean handleMessage(Message message) {
            if (message.what == 1) {
                doSetWebView(true);
            }
            if (message.what == 0) {
                doSetWebView(false);
            }
            return false;
        }
    });

    public ContentPresenter(IContent.View view) {
        this.view = view;
        model = new ContentModel();
    }

    @Override
    public void doRequestData(final String url) {
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
    public void doSetWebView(boolean flag) {
        String url = model.getHtml(flag);
        System.out.println("设置的URL --" + flag + "--" + url);
        view.onSetWebView(url, flag);
    }

    @Override
    public void onFail() {

    }
}
