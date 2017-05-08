package com.meiji.toutiao.module.photo.article;

import android.util.Log;

import com.google.gson.Gson;
import com.google.gson.JsonSyntaxException;
import com.meiji.toutiao.InitApp;
import com.meiji.toutiao.bean.photo.PhotoArticleBean;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import okhttp3.Request;
import okhttp3.Response;

/**
 * Created by Meiji on 2017/2/16.
 */
@Deprecated
public class PhotoArticleModel implements IPhotoArticle.Model {

    private static final String TAG = "NewsArticleModel";
    private Gson gson = new Gson();
    private List<PhotoArticleBean> newsList = new ArrayList<>();
    private List<PhotoArticleBean.DataBean> dataList = new ArrayList<>();
    private List<PhotoArticleBean.NextBean> nextList = new ArrayList<>();

    @Override
    public boolean requestData(String url) {
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
                //String result = ChineseUtil.UnicodeToChs(responseJson);
                Log.d(TAG, "requestData: " + responseJson);
                PhotoArticleBean bean = gson.fromJson(responseJson, PhotoArticleBean.class);
                newsList.add(bean);
            }
        } catch (IOException | JsonSyntaxException e) {
            e.printStackTrace();
        }
        return flag;
    }

    @Override
    public List<PhotoArticleBean.DataBean> getDataList() {
        for (PhotoArticleBean bean : newsList) {
            dataList = bean.getData();
            try {
                // 移除最后一项 数据有重复
                if (dataList.size() != 0) {
                    dataList.remove(dataList.size() - 1);
//                    dataList.remove(0);
                }
//                // 移除无图片的 Item 和 source 为 "ad"
//                for (int i = 0; i < dataList.size(); i++) {
//                    PhotoArticleBean.DataBean dataBean = dataList.get(i);
//                    if (!dataBean.isHas_image() || dataBean.getTag().contains("ad")) {
//                        dataList.remove(i);
//                    }
//                }
            } catch (IndexOutOfBoundsException e) {
                e.printStackTrace();
            }
        }
        return dataList;
    }

    @Override
    public int getmax_behot_time() {
        int max_behot_time = 0;
        for (PhotoArticleBean bean : newsList) {
            max_behot_time = bean.getNext().getMax_behot_time();
        }
        return max_behot_time;
    }
}
