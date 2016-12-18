package com.meiji.toutiao.news.tab;

import com.google.gson.Gson;
import com.google.gson.JsonSyntaxException;
import com.meiji.toutiao.InitApp;
import com.meiji.toutiao.bean.NewsBean;
import com.meiji.toutiao.utils.ChineseUtil;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import okhttp3.Request;
import okhttp3.Response;

/**
 * Created by Meiji on 2016/12/15.
 */

public class NewsModel implements INews.Model {

    private static Gson gson = new Gson();
    private List<NewsBean> newsList = new ArrayList<>();
    private List<NewsBean.DataBean> dataList = new ArrayList<>();
    private List<NewsBean.NextBean> nextList = new ArrayList<>();

    public NewsModel() {
    }

    @Override
    public boolean requestData(String url) {
        System.out.println("newsApi -- " + url);
        boolean flag = false;
        Request request = new Request.Builder()
                .url(url)
                .get()
                .build();
        try {
            Response response = InitApp.okHttpClient.newCall(request).execute();
            if (response.isSuccessful()) {
                flag = true;
                String responseJson = response.body().string();
                String result = ChineseUtil.UnicodeToChs(responseJson);
                System.out.println(result);
                NewsBean bean = gson.fromJson(result, NewsBean.class);
                newsList.add(bean);
            }
        } catch (IOException | JsonSyntaxException e) {
            e.printStackTrace();
        }
        return flag;
    }

    @Override
    public List<NewsBean.DataBean> getDataList() {
        for (NewsBean bean : newsList) {
            dataList = bean.getData();
            try {
                // 移除最后一项 数据有重复
                if (dataList.size() != 0) {
                    dataList.remove(dataList.size() - 1);
                    dataList.remove(0);
                }
                // 移除无图片的 Item 和 source 为 "头条问答"
                for (int i = 0; i < dataList.size(); i++) {
                    NewsBean.DataBean dataBean = dataList.get(i);
                    if (!dataBean.isHas_image() || dataBean.getSource().contains("头条问答") || dataBean.getTag().contains("ad")) {
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
        for (NewsBean bean : newsList) {
            max_behot_time = bean.getNext().getMax_behot_time();
        }
        return max_behot_time;
    }
}
