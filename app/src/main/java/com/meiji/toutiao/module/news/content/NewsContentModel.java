package com.meiji.toutiao.module.news.content;

import android.util.Log;

import com.google.gson.Gson;
import com.google.gson.JsonSyntaxException;
import com.meiji.toutiao.Constant;
import com.meiji.toutiao.InitApp;
import com.meiji.toutiao.bean.news.NewsContentBean;
import com.meiji.toutiao.utils.SettingsUtil;

import java.io.IOException;

import okhttp3.HttpUrl;
import okhttp3.Request;
import okhttp3.Response;

/**
 * Created by Meiji on 2016/12/17.
 */

@Deprecated
class NewsContentModel implements INewsContent.Model {

    private static final String TAG = "NewsContentModel";
    private Gson gson = new Gson();
    private String url;
    private NewsContentBean newsContentBean;

    NewsContentModel() {
    }

    @Override
    public boolean getRequestData(String url) {

        boolean flag = false;
        Log.d(TAG, "getRequestData: " + url);

        try {
            Request request = new Request.Builder()
                    .get()
                    .url(url)
                    .addHeader("User-Agent", Constant.USER_AGENT_MOBILE)
                    .build();
            Response response = InitApp.getOkHttpClient().newCall(request).execute();
            if (response.isSuccessful()) {
                // 获取重定向后的链接
                HttpUrl redirectUrl = response.request().url();

                // 拼凑API再次请求
                String realUrl = redirectUrl + "info";
                Log.d(TAG, "getRequestData: realUrl " + realUrl);
                Request realRequest = new Request.Builder()
                        .get()
                        .url(realUrl)
                        .build();
                Response realResponse = InitApp.getOkHttpClient().newCall(realRequest).execute();
                if (realResponse.isSuccessful()) {
                    // 得到API返回数据
                    String responseJson = realResponse.body().string();
                    Log.d(TAG, "getRequestData: " + responseJson);
                    newsContentBean = gson.fromJson(responseJson, NewsContentBean.class);
                    flag = true;
                }
            }
        } catch (IOException | JsonSyntaxException e) {
            e.printStackTrace();
            flag = false;
        }

        return flag;
    }

    @Override
    public String getHtml() {

        NewsContentBean.DataBean dataBean = newsContentBean.getData();
        String title = dataBean.getTitle();
        String content = dataBean.getContent();
        if (content != null) {

            String css = "<link rel=\"stylesheet\" href=\"file:///android_asset/toutiao_light.css\" type=\"text/css\">";
            if (SettingsUtil.getInstance().getIsNightMode()) {
                css = css.replace("toutiao_light", "toutiao_dark");
            }

            String html = "<!DOCTYPE html>\n" +
                    "<html lang=\"en\">\n" +
                    "<head>\n" +
                    "    <meta charset=\"UTF-8\">" +
                    css +
                    "<body>\n" +
                    "<article class=\"article-container\">\n" +
                    "    <div class=\"article__content article-content\">" +
                    "<h1 class=\"article-title\">" +
                    title +
                    "</h1>" +
                    content +
                    "    </div>\n" +
                    "</article>\n" +
                    "</body>\n" +
                    "</html>";

            return html;
        } else {
            return null;
        }
    }
}


