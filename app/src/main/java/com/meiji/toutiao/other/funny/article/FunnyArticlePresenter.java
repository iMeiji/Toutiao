package com.meiji.toutiao.other.funny.article;

import android.content.Intent;
import android.os.Handler;
import android.os.Message;

import com.meiji.toutiao.InitApp;
import com.meiji.toutiao.bean.other.funny.FunnyArticleBean;
import com.meiji.toutiao.other.funny.content.FunnyContentView;
import com.meiji.toutiao.utils.Api;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Meiji on 2017/1/2.
 */

class FunnyArticlePresenter implements IFunnyArticle.Presenter {

    private IFunnyArticle.View view;
    private IFunnyArticle.Model model;
    private String categoryId;
    private List<FunnyArticleBean.DataBean> dataList = new ArrayList<>();
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

    FunnyArticlePresenter(IFunnyArticle.View view) {
        this.view = view;
        this.model = new FunnyArticleModel();
    }

    public void doGetUrl(String parameter) {
        view.onShowRefreshing();
        this.categoryId = parameter;
        String url = Api.getOtherUrl(categoryId);
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
        if (dataList.size() != 0) {
            dataList.clear();
        }
        dataList.addAll(model.getDataList());
        view.onSetAdapter(model.getDataList());
        view.onHideRefreshing();
    }

    @Override
    public void doRefresh() {
        view.onShowRefreshing();
        String url = Api.getOtherUrl(categoryId);
        doRequestData(url);
    }

    @Override
    public void onFail() {
        view.onHideRefreshing();
        view.onFail();
    }

    @Override
    public void doOnClickItem(int position) {
        FunnyArticleBean.DataBean bean = dataList.get(position);
        Intent intent = new Intent(InitApp.AppContext, FunnyContentView.class);
        intent.putExtra(FunnyContentView.TAG, bean);
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        InitApp.AppContext.startActivity(intent);
    }
}
