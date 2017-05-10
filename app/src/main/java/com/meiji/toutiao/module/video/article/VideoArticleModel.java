package com.meiji.toutiao.module.video.article;

import android.util.Log;

import com.google.gson.Gson;
import com.google.gson.JsonSyntaxException;
import com.meiji.toutiao.InitApp;
import com.meiji.toutiao.bean.video.VideoArticleBean;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import okhttp3.Request;
import okhttp3.Response;

/**
 * Created by Meiji on 2017/3/29.
 */
@Deprecated
public class VideoArticleModel implements IVideoArticle.Model {

    private static final String TAG = "VideoArticleModel";
    private Gson gson = new Gson();
    private List<VideoArticleBean> newsList = new ArrayList<>();
    private List<VideoArticleBean.DataBean> dataList = new ArrayList<>();

    @Override
    public boolean requestData(String url) {
        Log.d(TAG, "requestData: " + url);

        if (newsList.size() != 0) {
            newsList.clear();
        }

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
                VideoArticleBean bean = gson.fromJson(responseJson, VideoArticleBean.class);
                newsList.add(bean);
            }
        } catch (IOException | JsonSyntaxException e) {
            e.printStackTrace();
        }
        return flag;
    }

    @Override
    public List<VideoArticleBean.DataBean> getDataList() {
        for (VideoArticleBean bean : newsList) {
            dataList = bean.getData();
            // 移除最后一项 数据有重复
            if (dataList.size() != 0) {
                dataList.remove(dataList.size() - 1);
            }
        }
        return dataList;
    }

}
