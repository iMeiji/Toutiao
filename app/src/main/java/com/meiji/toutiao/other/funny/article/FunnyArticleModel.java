package com.meiji.toutiao.other.funny.article;

import android.util.Log;

import com.google.gson.Gson;
import com.google.gson.JsonSyntaxException;
import com.meiji.toutiao.InitApp;
import com.meiji.toutiao.bean.other.funny.FunnyArticleBean;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import okhttp3.Request;
import okhttp3.Response;

/**
 * Created by Meiji on 2017/1/2.
 */

class FunnyArticleModel implements IFunnyArticle.Model {

    private static final String TAG = "FunnyArticleModel";
    private Gson gson = new Gson();
    private List<FunnyArticleBean> funnyList = new ArrayList<>();
    private List<FunnyArticleBean.DataBean> dataList = new ArrayList<>();

    @Override
    public boolean requestData(String url) {
        if (funnyList.size() != 0) {
            funnyList.clear();
        }
        Log.d(TAG, "requestData: " + url);
        boolean flag = false;
        Request request = new Request.Builder()
                .url(url)
                .get()
                .build();
        try {
            Response response = InitApp.getOkHttpClient().newCall(request).execute();
            if (response.isSuccessful()) {
                flag = true;
                String responseJson = response.body().string();
                Log.d(TAG, "requestData: " + responseJson);
                FunnyArticleBean bean = gson.fromJson(responseJson, FunnyArticleBean.class);
                funnyList.add(bean);
            }
        } catch (IOException | JsonSyntaxException e) {
            e.printStackTrace();
        }
        return flag;
    }

    @Override
    public List<FunnyArticleBean.DataBean> getDataList() {
        for (FunnyArticleBean funnyArticleBean : funnyList) {
            for (FunnyArticleBean.DataBean dataBean : funnyArticleBean.getData()) {
                dataList.add(dataBean);
            }
        }
        // 移除最后一项
        dataList.remove(dataList.size() - 1);
        return dataList;
    }

    @Override
    public int getmax_behot_time() {
        return 0;
    }
}
