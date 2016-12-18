package com.meiji.toutiao.news.content;

import com.meiji.toutiao.InitApp;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;

import java.io.IOException;

import okhttp3.Request;
import okhttp3.Response;

/**
 * Created by Meiji on 2016/12/17.
 */

public class ContentModel implements IContent.Model {

    private String html;
    private String url;

    public ContentModel() {
    }

    @Override
    public boolean getRequestData(String url) {

        boolean flag = false;

        if (url != null && url.contains("://toutiao")) {
            // 判断网址是否转跳 转跳则不处理 HTML (待写)
            this.url = url.replace("toutiao", "m.toutiao");
            System.out.println("新闻内容链接 " + this.url);

            try {
                Request request = new Request.Builder().get().url(this.url).build();
                Response response = InitApp.okHttpClient.newCall(request).execute();
                if (response.isSuccessful() && !response.isRedirect()) {
                    html = response.body().string();
                    flag = true;
//                    System.out.println(html);
                }
            } catch (IOException e) {
                e.printStackTrace();
            }

        } else {
            // 非头条的网站 不处理 HTML
            this.url = url;
            flag = false;
        }

        return flag;
    }

    @Override
    public String getHtml() {
        String result = parseHtml(html);
        if (result != null) {
            return result;
        }
        return null;
    }

    /**
     * news_image 类型的另外处理
     */
    private String parseHtml(String response) {
        String content = null;
        try {
            Document document = Jsoup.parse(response);
            Element main = document.getElementById("article-main");
            main.getElementsByClass("footnote").remove();
            main.getElementsByClass("article-actions").remove();
            content = main.toString();

        } catch (NullPointerException e) {
            e.printStackTrace();
            return null;
        }

        // 如果切割后的内容不为空
        if (content != null) {
//            System.out.println("切割后的HTML " + content);
            String css = "<link rel=\"stylesheet\" href=\"file:///android_asset/wap.css\" type=\"text/css\">";

            String html = "<!DOCTYPE html>\n" +
                    "<html lang=\"en\">\n" +
                    "<head>\n" +
                    "    <meta charset=\"UTF-8\">" +
                    css +
                    "<body>\n" +
                    "<article class=\"article-container\">\n" +
                    "    <div class=\"article__content article-content\">" +
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


