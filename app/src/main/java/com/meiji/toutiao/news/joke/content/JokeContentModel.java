package com.meiji.toutiao.news.joke.content;

import android.util.Log;

import com.google.gson.Gson;
import com.google.gson.JsonSyntaxException;
import com.meiji.toutiao.InitApp;
import com.meiji.toutiao.bean.news.joke.JokeContentBean;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import okhttp3.Request;
import okhttp3.Response;

/**
 * Created by Meiji on 2016/12/28.
 */

class JokeContentModel implements IJokeContent.Model {

    private static final String TAG = "JokeContentModel";
    private Gson gson = new Gson();
    private List<JokeContentBean> jokeList = new ArrayList<>();
    private List<JokeContentBean.DataBean> dataList = new ArrayList<>();
    private List<JokeContentBean.DataBean.GroupBean> groupList = new ArrayList<>();

    @Override
    public boolean requestData(String url) {
        // 清除旧数据
        if (jokeList.size() != 0) {
            jokeList.clear();
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
                JokeContentBean bean = gson.fromJson(responseJson, JokeContentBean.class);
                jokeList.add(bean);
            }
        } catch (IOException | JsonSyntaxException e) {
            e.printStackTrace();
        }
        return flag;
    }

    @Override
    public List<JokeContentBean.DataBean.GroupBean> getDataList() {

        for (JokeContentBean jokeContentBean : jokeList) {
            for (JokeContentBean.DataBean dataBean : jokeContentBean.getData()) {
                groupList.add(dataBean.getGroup());
            }
        }

        return groupList;
    }

    @Override
    public int getmax_behot_time() {
        return 0;
    }
}
