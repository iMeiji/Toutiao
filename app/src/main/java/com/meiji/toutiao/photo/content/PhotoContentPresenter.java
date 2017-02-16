package com.meiji.toutiao.photo.content;

import android.os.Handler;
import android.os.Message;
import android.util.Log;

import com.meiji.toutiao.bean.photo.PhotoArticleBean;
import com.meiji.toutiao.bean.photo.PhotoGalleryBean;

/**
 * Created by Meiji on 2017/2/16.
 */

class PhotoContentPresenter implements IPhotoContent.Presenter {

    private static final String TAG = "PhotoContentPresenter";
    private IPhotoContent.View view;
    private IPhotoContent.Model model;
    private PhotoGalleryBean bean;
    private Handler handler = new Handler(new Handler.Callback() {
        @Override
        public boolean handleMessage(Message message) {
            if (message.what == 1) {
                doSetImageBrwoser();
            }
            if (message.what == 0) {
//                System.out.println("设置未处理的url --");
//                view.onSetWebView(null, false);
            }
            return false;
        }
    });
    private int position;

    public PhotoContentPresenter(IPhotoContent.View view) {
        this.view = view;
        this.model = new PhotoContentModel();
    }

    @Override
    public void doSetImageBrwoser() {
        bean = model.getData();
        view.onSetImageBrwoser(bean, 0);
    }

    @Override
    public void onFail() {

    }

    @Override
    public void doRequestData(PhotoArticleBean.DataBean dataBean) {
        final String url = "http://www.toutiao.com/a" + dataBean.getGroup_id();
        Log.d(TAG, "doRequestData: " + url);
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
    public int getImageCount() {
        return bean.getCount();
    }

    @Override
    public void doGetComment() {

    }
}
