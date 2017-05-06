package com.meiji.toutiao.module.news.article;

import android.util.Log;

import com.google.gson.Gson;
import com.google.gson.JsonSyntaxException;
import com.meiji.toutiao.InitApp;
import com.meiji.toutiao.bean.news.NewsArticleBean;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import okhttp3.Request;
import okhttp3.Response;

/**
 * Created by Meiji on 2016/12/15.
 */

@Deprecated
public class NewsArticleModel implements INewsArticle.Model {

    private static final String TAG = "NewsArticleModel";
    private Gson gson = new Gson();
    private List<NewsArticleBean> newsList = new ArrayList<>();
    private List<NewsArticleBean.DataBean> dataList = new ArrayList<>();
    private List<NewsArticleBean.DataBean> resultList = new ArrayList<>();

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
                NewsArticleBean bean = gson.fromJson(responseJson, NewsArticleBean.class);
                newsList.add(bean);
            }
        } catch (IOException | JsonSyntaxException e) {
            e.printStackTrace();
        }
        return flag;
    }

    @Override
    public List<NewsArticleBean.DataBean> getDataList() {
        for (NewsArticleBean bean : newsList) {
            dataList = bean.getData();
            try {
                // 移除最后一项 数据有重复
                if (dataList.size() != 0) {
                    dataList.remove(dataList.size() - 1);
                }
                // 移除头条问答 和 广告
                for (int i = 0; i < dataList.size(); i++) {
                    NewsArticleBean.DataBean dataBean = dataList.get(i);
                    if (dataBean.getSource().contains("头条问答")
                            || dataBean.getTag().contains("ad")
                            || dataBean.isHas_video()
                            || dataBean.getSource().contains("话题")) {
                        dataList.remove(i);
                    }
                }
            } catch (IndexOutOfBoundsException e) {
                e.printStackTrace();
            }
        }
        resultList.addAll(dataList);
        return resultList;
    }

    @Override
    public int getMaxBehotTime() {
        int max_behot_time = 0;
        for (NewsArticleBean bean : newsList) {
            max_behot_time = bean.getNext().getMax_behot_time();
        }
        return max_behot_time;
    }
}
