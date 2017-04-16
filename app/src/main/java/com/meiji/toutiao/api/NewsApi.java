package com.meiji.toutiao.api;

/**
 * Created by Meiji on 2017/3/1.
 */

public class NewsApi {

    /**
     * 获取新闻标题等信息
     */
    private static final String newsArticleUrl_PC =
//            "http://www.toutiao.com/api/article/feed/?category=类型&as=A115C8457F69B85&cp=585F294B8845EE1&_=时间&count=30";
//            "http://www.toutiao.com/api/pc/feed/?category=类型&utm_source=toutiao&widen=1&max_behot_time=时间&max_behot_time_tmp=时间&tadrequire=true&as=A1C598BB87BE7DA&cp=58B72ED7AD3A0E1";
            "http://toutiao.com/api/article/recent/?source=2&category=类型&as=A105177907376A5&cp=5797C7865AD54E1&count=30";

    private static final String newsArticleUrl_Mobile =
            "http://m.toutiao.com/list/?tag=类型&ac=wap&count=20&format=json_raw&as=A195086BB952360&cp=58B942C3D6804E1&min_behot_time=时间";

    /**
     * 获取新闻HTML内容
     */
    private static final String newsContentUrl = "http://m.toutiao.com/item_seo_url值/info/";

    /**
     * 获取新闻评论
     */
    private static final String newsCommentUrl =
            "http://www.toutiao.com/api/comment/list/?group_id=头条号&item_id=文章号&offset=偏移量&count=数量";

    public static String getNewsArticle_PCUrl(String category) {
        return newsArticleUrl_PC
                .replace("类型", category);
    }

    public static String getNewsArticleUrl_Mobile(String category, String max_behot_time) {
        return newsArticleUrl_Mobile
                .replace("类型", category)
                .replace("时间", max_behot_time);
    }

    public static String getNewsContentUrl(String item_seo_url) {

        if (item_seo_url.contains("/item/")) {
            item_seo_url = item_seo_url.replace("item/", "i");
        }
        if (item_seo_url.contains("?")) {
            int i = item_seo_url.indexOf("?");
            item_seo_url = item_seo_url.substring(0, i);
        }
        return newsContentUrl.replace("/item_seo_url值/", item_seo_url);
    }

    public static String getNewsCommentUrl(String group_id, String item_id, int offset, int count) {
        return newsCommentUrl
                .replace("头条号", group_id)
                .replace("文章号", item_id)
                .replace("偏移量", offset + "")
                .replace("数量", count + "");
    }
}
