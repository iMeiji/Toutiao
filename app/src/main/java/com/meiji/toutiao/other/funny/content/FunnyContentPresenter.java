package com.meiji.toutiao.other.funny.content;

import android.os.Handler;
import android.os.Message;
import android.util.Log;

/**
 * Created by Meiji on 2017/1/3.
 */

class FunnyContentPresenter implements IFunnyContent.Presenter {

    private static final String TAG = "FunnyContentPresenter";
    private IFunnyContent.View view;
    private IFunnyContent.Model model;
    private String url;
    private Handler handler = new Handler(new Handler.Callback() {
        @Override
        public boolean handleMessage(Message message) {
            if (message.what == 1) {
                doSetWebView();
            }
            if (message.what == 0) {
                view.onSetWebView(null, false);
            }
            return false;
        }
    });

    FunnyContentPresenter(IFunnyContent.View view) {
        this.view = view;
        this.model = new FunnyContentModel();
    }

    public void doGetUrl(String group_id) {
        view.onShowRefreshing();
        url = "http://www.toutiao.com/a" + group_id;
        Log.d(TAG, "doGetUrl: ");
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
    public void doSetWebView() {
        String html = model.getHtml();
        if (html != null) {
            view.onSetWebView(html, true);
        } else {
            view.onSetWebView(null, false);
        }
    }

    @Override
    public void onFail() {

    }

    @Override
    public void doGetComment() {

    }
}
