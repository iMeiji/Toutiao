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

public class CommentModel implements IComment.Model {

    private static final String TAG = "CommentModel";
    private Gson gson = new Gson();
    private List<JokeCommentBean> jokeCommentList = new ArrayList<>();
    private List<JokeCommentBean.DataBean> dataList = new ArrayList<>();
    private List<JokeCommentBean.DataBean.CommentsBean> commentsList = new ArrayList<>();

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
    public List<JokeCommentBean.DataBean.CommentsBean> getDataList() {

        for (JokeCommentBean jokeCommentBean : jokeCommentList) {
            for (JokeCommentBean.DataBean.CommentsBean commentsBean : jokeCommentBean.getData().getComments()) {
                commentsList.add(commentsBean);
            }
        }

        return commentsList;
    }
}
