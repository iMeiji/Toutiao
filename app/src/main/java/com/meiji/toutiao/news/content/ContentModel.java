package com.meiji.toutiao.news.content;

import com.meiji.toutiao.InitApp;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;

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

        if (url != null && url.contains("toutiao")) {
            // 判断网址是否转跳 转跳则不处理 HTML (待写)
            this.url = url.replace("toutiao", "m.toutiao");
            System.out.println("新闻内容链接 " + this.url);

            try {
                Request request = new Request.Builder().get().url(this.url).build();
                Response response = InitApp.okHttpClient.newCall(request).execute();
                if (response.isSuccessful()) {
                    html = response.body().string();
                    flag = true;
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
    public String getHtml(boolean flag) {
        if (flag) {
            Document document = Jsoup.parse(html);
            Elements content = document.getElementsByClass("article-content");
            if (html != null) {
                html = content.toString();
                System.out.println("切割后的HTML " + html);

                String result = "<!DOCTYPE html>\n" +
                        "<html lang=\"en\">\n" +
                        "<head>\n" +
                        "    <meta charset=\"UTF-8\">\n" +
                        "    <title>Title</title>\n" +
                        "    <!--<link rel=\"stylesheet\" type=\"text/css\" href=\"ed.css.css\">-->\n" +
                        "\n" +
                        "    <style type=\"text/css\">\n" +
                        "        .article-content {\n" +
                        "            font-size: 16px;\n" +
                        "        }\n" +
                        "\n" +
                        "        img {\n" +
                        "            max-width: 300px;\n" +
                        "        }\n" +
                        "    </style>\n" +
                        "</head>\n" +
                        "\n" +
                        "<body>" +
                        html +
                        "</body>\n" +
                        "</html>";

                return result;
            } else {
                return this.url;
            }
        } else {
            return this.url;
        }
    }


}
