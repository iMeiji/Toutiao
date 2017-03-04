package com.meiji.toutiao.api;

/**
 * Created by Meiji on 2017/3/1.
 */

public class PhotoApi {

    /**
     * 获取图片标题等信息
     */
    private static final String photoArticleUrl =
            "http://www.toutiao.com/api/article/feed/?category=类型&as=A115C8457F69B85&cp=585F294B8845EE1&_=时间&count=30";

    /**
     * 获取图片内容HTML内容
     * 抓取 url 较复杂
     * 详情查看 PhotoContentModel
     */
    private static final String photoContentUrl = "";

    /**
     * 获取图片内容的评论
     */
    private static final String photoCommentUrl =
            "http://www.toutiao.com/api/comment/list/?group_id=头条号&item_id=文章号&offset=偏移量&count=数量";

    public static String getPhotoArticleUrl(String category, int offset, String max_behot_time) {
        return photoArticleUrl.replace("类型", category)
                .replace("偏移量", offset + "")
                .replace("时间", max_behot_time);
    }

    public static String getPhotoCommentUrl(String jokeId, int count, int offset) {
        return photoCommentUrl
                .replace("编号", jokeId)
                .replace("数量", count + "")
                .replace("偏移量", offset + "");
    }
}
