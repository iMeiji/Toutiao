package com.meiji.toutiao.other.funny.content;

import android.util.Log;

import com.google.gson.Gson;
import com.google.gson.JsonSyntaxException;
import com.meiji.toutiao.InitApp;
import com.meiji.toutiao.bean.other.funny.FunnyContentBean;
import com.meiji.toutiao.utils.SettingsUtil;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;

import java.io.IOException;

import okhttp3.Request;
import okhttp3.Response;

/**
 * Created by Meiji on 2017/1/3.
 */

class FunnyContentModel implements IFunnyContent.Model {

    private static final String TAG = "FunnyContentModel";
    private Gson gson = new Gson();
    private FunnyContentBean bean;

    @Override
    public boolean requestData(String url) {

        boolean flag = false;
        try {
            Document document = Jsoup
                    .connect(url)
                    .userAgent("Mozilla/5.0 (Linux; Android 5.1.1; Nexus 6 Build/LYZ28E) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/55.0.2883.87 Mobile Safari/537.36")
                    .get();
            Elements select = document.select("link[href~=^.*http.*://www.toutiao.com/(.*)$]");
            // http://www.toutiao.com/i6371213132714476034
            url = select.attr("href");
            // http://m.toutiao.com/i6371213132714476034/info/
            url = url.replace("www.toutiao.com", "m.toutiao.com") + "/info";

            Log.d(TAG, "requestData: " + url);
            Request request = new Request.Builder()
                    .url(url)
                    .get()
                    .build();

            Response response = InitApp.getOkHttpClient().newCall(request).execute();
            if (response.isSuccessful()) {
                flag = true;
                String responseJson = response.body().string();
                Log.d(TAG, "requestData: " + responseJson);
                bean = gson.fromJson(responseJson, FunnyContentBean.class);
            }
        } catch (IOException | JsonSyntaxException e) {
            e.printStackTrace();
        }
        return flag;
    }

    @Override
    public String getHtml() {

        FunnyContentBean.DataBean data = bean.getData();
        String title = data.getTitle();
        String content = data.getContent();

        if (content != null) {

            String css = "<link rel=\"stylesheet\" href=\"file:///android_asset/toutiao_light.css\" type=\"text/css\">";
            if (!SettingsUtil.getInstance().getIsNightMode()) {
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
