package com.meiji.toutiao.other.joke.comment;

import android.util.Log;

import com.google.gson.Gson;
import com.google.gson.JsonSyntaxException;
import com.meiji.toutiao.InitApp;
import com.meiji.toutiao.bean.other.joke.JokeCommentBean;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import okhttp3.Request;
import okhttp3.Response;

/**
 * Created by Meiji on 2017/1/1.
 */

public class JokeCommentModel implements IJokeComment.Model {

    private static final String TAG = "JokeCommentModel";
    private Gson gson = new Gson();
    private List<JokeCommentBean> jokeCommentList = new ArrayList<>();
    private List<JokeCommentBean.DataBean.RecentCommentsBean> dataList = new ArrayList<>();
    private boolean isFirstTime = true;

    @Override
    public boolean requestData(String url) {
        if (jokeCommentList.size() != 0) {
            jokeCommentList.clear();
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
                JokeCommentBean bean = gson.fromJson(responseJson, JokeCommentBean.class);
                jokeCommentList.add(bean);
            }
        } catch (IOException | JsonSyntaxException e) {
            e.printStackTrace();
        }
        return flag;
    }

    @Override
    public List<JokeCommentBean.DataBean.RecentCommentsBean> getDataList() {

        for (JokeCommentBean jokeCommentBean : jokeCommentList) {
            if (isFirstTime) {
                for (JokeCommentBean.DataBean.TopCommentsBean commentsBean : jokeCommentBean.getData().getTop_comments()) {
                    JokeCommentBean.DataBean.RecentCommentsBean bean = new JokeCommentBean.DataBean.RecentCommentsBean();
                    bean.setUser_profile_image_url(commentsBean.getUser_profile_image_url());
                    bean.setUser_name(commentsBean.getUser_name());
                    bean.setText(commentsBean.getText());
                    bean.setDigg_count(commentsBean.getDigg_count());
                    dataList.add(bean);
                }
                isFirstTime = false;
            }
            for (JokeCommentBean.DataBean.RecentCommentsBean commentsBean : jokeCommentBean.getData().getRecent_comments()) {
                dataList.add(commentsBean);
            }
        }

        return dataList;
    }
}
