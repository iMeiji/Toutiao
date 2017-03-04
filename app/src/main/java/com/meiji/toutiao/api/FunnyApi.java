package com.meiji.toutiao.api;

/**
 * Created by Meiji on 2017/3/1.
 */

public class FunnyApi {

    /**
     * 获取搞笑内容标题等信息
     */
    private static final String funnyArticleUrl =
//            "http://www.toutiao.com/api/article/feed/?category=funny&as=A175588BE62BBA2&cp=58B6EB9B7AE21E1";
            "http://toutiao.com/api/article/recent/?source=2&category=funny&as=A105177907376A5&cp=5797C7865AD54E1";


    /**
     * 获取搞笑内容HTML内容
     * 抓取 url 较复杂
     * 详情查看 FunnyContentModel
     */
    private static final String funnyContentUrl =
            "";

    /**
     * 获取搞笑内容的评论
     */
    private static final String funnyCommentUrl =
            "http://www.toutiao.com/api/comment/list/?group_id=头条号&item_id=文章号&offset=偏移量&count=数量";

    public static String getFunnyArticleUrl() {
        return funnyArticleUrl;
    }

    public static String getFunnyCommentUrl(String group_id, String item_id, int offset, int count) {
        return funnyCommentUrl
                .replace("头条号", group_id)
                .replace("文章号", item_id)
                .replace("偏移量", offset + "")
                .replace("数量", count + "");
    }
}
