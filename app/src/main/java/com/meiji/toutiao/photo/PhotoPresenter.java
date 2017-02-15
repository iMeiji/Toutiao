package com.meiji.toutiao.photo;

import android.os.Handler;
import android.os.Message;

import com.meiji.toutiao.bean.photo.PhotoViewBean;
import com.meiji.toutiao.utils.Api;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Meiji on 2017/2/16.
 */

class PhotoPresenter implements IPhoto.Presenter {

    private IPhoto.View view;
    private IPhoto.Model model;
    private List<PhotoViewBean.DataBean> dataList = new ArrayList<>();
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

    PhotoPresenter(IPhoto.View view) {
        this.view = view;
        this.model = new PhotoModel();
    }

    @Override
    public void doGetUrl(String parameter) {
        view.onShowRefreshing();
        this.category = parameter;
//        doRequestData(Api.getNewsUrl(parameter));
        String url = Api.getNewsPhoto(category, 0, model.getmax_behot_time() + "");
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
        view.onShowRefreshing();
        offset += 20;
        String url = Api.getNewsPhoto(category, offset, model.getmax_behot_time() + "");
        doRequestData(url);
    }

    @Override
    public void onFail() {
        view.onHideRefreshing();
        view.onFail();
    }

    @Override
    public void doOnClickItem(int position) {
//        NewsArticleBean.DataBean bean = dataList.get(position);
//        Intent intent = new Intent(InitApp.AppContext, NewsContetnView.class);
//        intent.putExtra(NewsContetnView.TAG, bean);
//        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
//        InitApp.AppContext.startActivity(intent);
//        // 打印下点击的标题和链接
//        System.out.println("点击的标题和链接---" + bean.getTitle() + "  " + bean.getDisplay_url());
    }
}
