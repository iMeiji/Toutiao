package com.meiji.toutiao.api;

/**
 * Created by Meiji on 2017/3/1.
 */

public class JokeApi {

    /**
     * 获取段子正文内容
     */
    private static final String jokeContentUrl =
            "http://www.toutiao.com/api/article/feed/?category=essay_joke&as=A115C8457F69B85&cp=585F294B8845EE1";

    /**
     * 获取段子评论
     */
    private static final String jokeCommentUrl =
            "http://m.neihanshequ.com/api/get_essay_comments/?group_id=编号&count=数量&offset=偏移量";

    public static String getJokeContentUrl() {
        return jokeContentUrl;
    }

    public static String getJokeCommentUrl(String jokeId, int count, int offset) {
        return jokeCommentUrl
                .replace("编号", jokeId)
                .replace("数量", count + "")
                .replace("偏移量", offset + "");
    }
}