package com.meiji.toutiao.media.article;

import android.util.Log;

import com.google.gson.Gson;
import com.google.gson.JsonSyntaxException;
import com.meiji.toutiao.InitApp;
import com.meiji.toutiao.bean.media.MediaArticleBean;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import okhttp3.Request;
import okhttp3.Response;

/**
 * Created by Meiji on 2017/4/11.
 */

class MediaArticleModel implements IMediaArticle.Model {

    private static final String TAG = "MediaArticleModel";
    private Gson gson = new Gson();
    private List<MediaArticleBean> mediaChannelList = new ArrayList<>();

    @Override
    public boolean requestData(String url) {

        if (mediaChannelList.size() != 0) {
            mediaChannelList.clear();
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
//                Log.d(TAG, "requestData: " + responseJson);
                MediaArticleBean bean = gson.fromJson(responseJson, MediaArticleBean.class);
                mediaChannelList.add(bean);
            }
        } catch (IOException | JsonSyntaxException e) {
            e.printStackTrace();
        }
        return flag;
    }

    @Override
    public List<MediaArticleBean.DataBean> getDataList() {
        for (MediaArticleBean mediaArticleBean : mediaChannelList) {
            return mediaArticleBean.getData();
        }
        return null;
    }

    @Override
    public int getMaxBehotTime() {
        int max_behot_time = 0;
        for (MediaArticleBean bean : mediaChannelList) {
            max_behot_time = bean.getNext().getMax_behot_time();
        }
        return max_behot_time;
    }
}
