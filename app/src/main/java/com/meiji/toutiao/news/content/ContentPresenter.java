package com.meiji.toutiao.news.content;

import android.os.Handler;
import android.os.Message;

/**
 * Created by Meiji on 2016/12/17.
 */

public class ContentPresenter implements IContent.Presenter {

    private IContent.View view;
    private IContent.Model model;
    private String url;
    private Handler handler = new Handler(new Handler.Callback() {
        @Override
        public boolean handleMessage(Message message) {
            if (message.what == 1) {
                doSetWebView();
            }
            if (message.what == 0) {
                System.out.println("设置未处理的url --" + url);
                view.onSetWebView(url, false);
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
        this.url = url;
        // 如果是头条的网址 则处理 HTML
        if (url != null && url.contains("://toutiao")) {
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
        } else {
            System.out.println("设置未处理的url --" + this.url);
            view.onSetWebView(this.url, false);
        }
    }

    @Override
    public void doSetWebView() {
        String html = model.getHtml();
        if (html != null) {
            System.out.println("设置的URL --" + "--" + html);
            view.onSetWebView(html, true);
        } else {
            System.out.println("设置未处理的url --" + this.url);
            view.onSetWebView(this.url, false);
        }
    }

    @Override
    public void onFail() {

    }
}
