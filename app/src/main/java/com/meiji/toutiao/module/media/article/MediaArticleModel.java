package com.meiji.toutiao.module.media.article;

import android.util.Log;

import com.google.gson.Gson;
import com.google.gson.JsonSyntaxException;
import com.meiji.toutiao.Constant;
import com.meiji.toutiao.InitApp;
import com.meiji.toutiao.bean.media.MediaArticleBean;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import okhttp3.Request;
import okhttp3.Response;

/**
 * Created by Meiji on 2017/4/11.
 */

class MediaArticleModel implements IMediaArticle.Model {

    private static final String TAG = "MediaArticleModel";
    private Gson gson = new Gson();
    private List<MediaArticleBean> mediaChannelList = new ArrayList<>();
    private long groupId;
    private long itemId;

    @Override
    public long getGroupId() {
        return groupId;
    }

    @Override
    public long getItemId() {
        return itemId;
    }

    @Override
    public boolean requestData(String url) {

        if (mediaChannelList.size() != 0) {
            mediaChannelList.clear();
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
//                Log.d(TAG, "requestData: " + responseJson);
                MediaArticleBean bean = gson.fromJson(responseJson, MediaArticleBean.class);
                mediaChannelList.add(bean);
            }
        } catch (IOException | JsonSyntaxException e) {
            e.printStackTrace();
        }
        return flag;
    }

    @Override
    public List<MediaArticleBean.DataBean> getDataList() {
        for (MediaArticleBean mediaArticleBean : mediaChannelList) {
            return mediaArticleBean.getData();
        }
        return null;
    }

    @Override
    public int getMaxBehotTime() {
        int max_behot_time = 0;
        for (MediaArticleBean bean : mediaChannelList) {
            max_behot_time = bean.getNext().getMax_behot_time();
        }
        return max_behot_time;
    }

    @Override
    public void getCommentRequestData(String articleUrl) {
        Log.d(TAG, "getCommentRequestData: " + articleUrl);
        try {
            Document doc = Jsoup
                    .connect(articleUrl)
                    .userAgent(Constant.USER_AGENT_PHONE)
                    .get();
            // 取得所有的script tag
            Elements scripts = doc.getElementsByTag("script");
            for (Element e : scripts) {
                // 过滤字符串
                String script = e.toString();
                if (script.contains("var group_id =")) {
                    // 只取得script的內容
                    script = e.childNode(0).toString();
                    // 取得JS变量数组
                    String[] vars = script.split("var ");
                    for (String var : vars) {
                        // 取到满足条件的JS变量
                        if (var.contains("group_id")) {
                            int start = var.indexOf("\"");
                            int end = var.lastIndexOf("\"");
                            groupId = Long.parseLong(var.substring(start + 1, end));
                            Log.d(TAG, "getCommentRequestData: groupId" + groupId);
                        }
                        if (var.contains("item_id")) {
                            int start = var.indexOf("\"");
                            int end = var.lastIndexOf("\"");
                            itemId = Long.parseLong(var.substring(start + 1, end));
                            Log.d(TAG, "getCommentRequestData: itemId" + itemId);
                        }
                    }
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
