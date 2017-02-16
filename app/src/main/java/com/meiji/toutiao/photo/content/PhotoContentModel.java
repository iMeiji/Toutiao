package com.meiji.toutiao.photo.content;

import android.util.Log;

import com.google.gson.Gson;
import com.google.gson.JsonSyntaxException;
import com.google.gson.stream.JsonReader;
import com.meiji.toutiao.bean.photo.PhotoGalleryBean;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.IOException;
import java.io.StringReader;

/**
 * Created by Meiji on 2017/2/16.
 */

class PhotoContentModel implements IPhotoContent.Model {

    private static final String TAG = "PhotoContentModel";
    private Gson gson = new Gson();
    private PhotoGalleryBean bean;


    @Override
    public boolean getRequestData(String url) {
        boolean flag = false;
        Log.d(TAG, "getRequestData: " + url);
        try {
            Document doc = Jsoup
                    .connect(url)
                    .userAgent("Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/56.0.2924.87 Safari/537.36")
                    .get();
            // 取得所有的script tag
            Elements scripts = doc.getElementsByTag("script");
            for (Element e : scripts) {
                // 过滤字符串
                String script = e.toString();
                if (script.contains("var gallery = {")) {
                    // 只取得script的內容
                    script = e.childNode(0).toString();
                    // 取得JS变量数组
                    String[] vars = script.split("var ");
                    // 取得单个JS变量
                    for (String var : vars) {
                        // 取到满足条件的JS变量
                        if (var.contains("gallery = ")) {
                            int start = var.indexOf("=");
                            int end = var.lastIndexOf(";");
                            String json = var.substring(start + 1, end + 1);
                            JsonReader reader = new JsonReader(new StringReader(json));
                            reader.setLenient(true);
                            Log.d(TAG, "getRequestData: " + reader);
                            bean = gson.fromJson(reader, PhotoGalleryBean.class);
                            flag = true;
                        }
                    }
                }
            }
        } catch (IOException | JsonSyntaxException e) {
            e.printStackTrace();
        }

        return flag;
    }

    @Override
    public PhotoGalleryBean getData() {
        return bean;
    }
}
