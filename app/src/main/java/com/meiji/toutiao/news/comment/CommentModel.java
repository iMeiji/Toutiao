package com.meiji.toutiao.news.comment;

import com.google.gson.Gson;
import com.google.gson.JsonSyntaxException;
import com.meiji.toutiao.InitApp;
import com.meiji.toutiao.bean.news.NewsCommentBean;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import okhttp3.Request;
import okhttp3.Response;

/**
 * Created by Meiji on 2016/12/20.
 */

public class CommentModel implements IComment.Model {

    private Gson gson = new Gson();
    private List<NewsCommentBean> commentBeanList = new ArrayList<>();
    private List<NewsCommentBean.DataBean> dataBeanList = new ArrayList<>();
    private List<NewsCommentBean.DataBean.CommentsBean> commentsBeanList = new ArrayList<>();
    private List<NewsCommentBean.DataBean.CommentsBean.UserBean> userBeanList = new ArrayList<>();

    public CommentModel() {
    }

    @Override
    public boolean requestData(String url) {
        // 清除旧数据
        if (commentBeanList.size() != 0) {
            commentBeanList.clear();
        }

        System.out.println("newsCommentApi -- " + url);
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
                //String result = ChineseUtil.UnicodeToChs(responseJson);
                System.out.println(responseJson);
                NewsCommentBean bean = gson.fromJson(responseJson, NewsCommentBean.class);
                commentBeanList.add(bean);
            }
        } catch (IOException | JsonSyntaxException e) {
            e.printStackTrace();
        }
        return flag;
    }

    @Override
    public List<NewsCommentBean.DataBean.CommentsBean> getDataList() {

        for (NewsCommentBean newsCommentBean : commentBeanList) {
            for (NewsCommentBean.DataBean.CommentsBean commentsBean : newsCommentBean.getData().getComments()) {
                commentsBeanList.add(commentsBean);
            }
        }

        return commentsBeanList;
    }
}
