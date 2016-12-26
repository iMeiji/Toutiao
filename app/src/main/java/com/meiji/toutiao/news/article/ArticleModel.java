package com.meiji.toutiao.news.article;

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

public class ArticleModel implements IArticle.Model {

    private Gson gson = new Gson();
    private List<NewsArticleBean> newsList = new ArrayList<>();
    private List<NewsArticleBean.DataBean> dataList = new ArrayList<>();
    private List<NewsArticleBean.NextBean> nextList = new ArrayList<>();

    public ArticleModel() {
    }

    @Override
    public boolean requestData(String url) {
        System.out.println("newsArticleApi -- " + url);
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
//                    dataList.remove(0);
                }
                // 移除无图片的 Item 和 source 为 "ad"
                for (int i = 0; i < dataList.size(); i++) {
                    NewsArticleBean.DataBean dataBean = dataList.get(i);
                    if (!dataBean.isHas_image() || dataBean.getTag().contains("ad")) {
                        dataList.remove(i);
                    }
                }
            } catch (IndexOutOfBoundsException e) {
                e.printStackTrace();
            }
        }
        return dataList;
    }

    @Override
    public int getmax_behot_time() {
        int max_behot_time = 0;
        for (NewsArticleBean bean : newsList) {
            max_behot_time = bean.getNext().getMax_behot_time();
        }
        return max_behot_time;
    }
}
