package com.meiji.toutiao.module.search;

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
 * Created by Meiji on 2017/2/7.
 */

class SearchModel implements ISearch.Model {

    private static final String TAG = "SearchModel";
    private List<NewsArticleBean> searchBeanList = new ArrayList<>();
    private List<NewsArticleBean.DataBean> dataBeanList = new ArrayList<>();
    private Gson gson = new Gson();

    @Override
    public boolean requestData(String url) {

        if (searchBeanList.size() != 0) {
            searchBeanList.clear();
        }

        boolean flag = false;
        Log.d(TAG, "requestData: " + url);
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
                searchBeanList.add(bean);
            }
        } catch (IOException | JsonSyntaxException e) {
            e.printStackTrace();
        }
        return flag;
    }

    @Override
    public List<NewsArticleBean.DataBean> getDataList() {

        for (NewsArticleBean searchBean : searchBeanList) {
            dataBeanList = searchBean.getData();
            try {
                // 移除无图片的 Item 和 source 为 "ad"
                for (int i = 0; i < dataBeanList.size(); i++) {
                    NewsArticleBean.DataBean dataBean = dataBeanList.get(i);
                    if (!dataBean.isHas_image() || dataBean.getTag().contains("ad")) {
                        dataBeanList.remove(i);
                    }
                }
            } catch (IndexOutOfBoundsException e) {
                e.printStackTrace();
            }
        }

        return dataBeanList;
    }
}
