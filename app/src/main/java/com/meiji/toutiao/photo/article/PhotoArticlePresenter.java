package com.meiji.toutiao.photo.article;

import android.os.Handler;
import android.os.Message;

import com.meiji.toutiao.api.PhotoApi;
import com.meiji.toutiao.bean.photo.PhotoArticleBean;
import com.meiji.toutiao.photo.content.PhotoContentActivity;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Meiji on 2017/2/16.
 */

class PhotoArticlePresenter implements IPhotoArticle.Presenter {

    private static final String TAG = "PhotoArticlePresenter";
    private IPhotoArticle.View view;
    private IPhotoArticle.Model model;
    private List<PhotoArticleBean.DataBean> dataList = new ArrayList<>();
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
    private String category;
    private int offset = 0;

    PhotoArticlePresenter(IPhotoArticle.View view) {
        this.view = view;
        this.model = new PhotoArticleModel();
    }

    @Override
    public void doGetUrl(String parameter) {
        view.onShowRefreshing();
        this.category = parameter;
        String url = PhotoApi.getPhotoArticleUrl(category, 0, model.getmax_behot_time() + "");
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
        dataList.addAll(model.getDataList());
        view.onSetAdapter(dataList);
        view.onHideRefreshing();
    }

    @Override
    public void doRefresh() {
        offset += 20;
        String url = PhotoApi.getPhotoArticleUrl(category, offset, model.getmax_behot_time() + "");
        doRequestData(url);
    }

    @Override
    public void onFail() {
        view.onHideRefreshing();
        view.onFail();
    }

    @Override
    public void doOnClickItem(int position) {
        PhotoContentActivity.startActivity(dataList.get(position));
    }
}
