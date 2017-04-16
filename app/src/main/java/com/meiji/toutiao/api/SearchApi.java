package com.meiji.toutiao.api;

/**
 * Created by Meiji on 2017/3/1.
 */

public class SearchApi {

    /**
     * 获取搜索文章标题等信息
     */
    private static final String searchUrl =
            "http://www.toutiao.com/search_content/?format=json&keyword=关键词&autoload=true&count=20&cur_tab=1&offset=偏移量";

    public static String getSearchUrl(String keywork, int offset) {
        return searchUrl
                .replace("关键词", keywork)
                .replace("偏移量", offset + "");
    }
}
