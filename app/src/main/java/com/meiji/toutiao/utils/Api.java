package com.meiji.toutiao.utils;

/**
 * Created by Meiji on 2016/12/13.
 */

public class Api {


//    private static final String NEWS_ARTICLE_URL =
//            "http://toutiao.com/api/article/recent/?source=2&category=类型&as=A1C5D7A9962A7C9&count=20";

    private static final String NEWS_ARTICLE_URL =
            "http://toutiao.com/api/article/recent/?source=2&category=类型&as=A105177907376A5&cp=5797C7865AD54E1&_=时间&count=20";

    private static final String NEWS_INFO_URL = "http://m.toutiao.com/item_seo_url值/info/";

    private static final String NEWS_COMMENT_URL =
            "http://www.toutiao.com/api/comment/list/?group_id=头条号&item_id=文章号&offset=偏移量&count=数量";

    /**
     * http://toutiao.com/api/article/recent/?source=2&category=news_hot&as=A105177907376A5&cp=5797C7865AD54E1&count=20&_=1481986412
     */
    public static String getNewsArticleUrl(String category, String max_behot_time) {
        return NEWS_ARTICLE_URL
                .replace("类型", category)
                .replace("时间", max_behot_time);
    }

    /**
     * /item/6365771196654420481/
     * /i6365733620811825665/
     * /item/6365802820284727809/?_as_=1482159839
     */
    public static String getNewsInfoUrl(String item_seo_url) {

        if (item_seo_url.contains("/item/")) {
            item_seo_url = item_seo_url.replace("item/", "i");
        }
        if (item_seo_url.contains("?")) {
            int i = item_seo_url.indexOf("?");
            item_seo_url = item_seo_url.substring(0, i);
        }
        return NEWS_INFO_URL.replace("/item_seo_url值/", item_seo_url);
    }

    /**
     * http://www.toutiao.com/api/comment/list/?group_id=6364965628189327618&item_id=6364969235889783298&offset=0&count=10
     */
    public static String getNewsCommentUrl(String group_id, String item_id, int offset, int count) {

        return NEWS_COMMENT_URL
                .replace("头条号", group_id)
                .replace("文章号", item_id)
                .replace("偏移量", offset + "")
                .replace("数量", count + "");
    }

}
