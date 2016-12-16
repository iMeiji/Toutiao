package com.meiji.toutiao.utils;

/**
 * Created by Meiji on 2016/12/13.
 */

public class Api {

    /**
     * 类型
     * '推荐': '__all__',
     * '热点': 'news_hot',
     * '社会': 'news_society',
     * '娱乐': 'news_entertainment',
     * '科技': 'news_tech',
     * '军事': 'news_military',
     * '体育': 'news_sports'
     * '汽车': 'news_car',
     * '财经': 'news_finance',
     * '国际': 'news_world',
     * '时尚': 'news_fashion',
     * '旅游': 'news_travel',
     * '探索': 'news_discovery',
     * '育儿': 'news_baby',
     * '养生': 'news_regimen',
     * '故事': 'news_story',
     * '美文': 'news_essay',
     * '游戏': 'news_game',
     * '历史': 'news_history',
     * '美食': 'news_food',
     */

    private static final String NEWS_URL =
            "http://toutiao.com/api/article/recent/?source=2&category=类型&as=A1C5D7A9962A7C9";
    private static final String NEWS_URL_REFRESH =
            "http://toutiao.com/api/article/recent/?source=2&category=类型&as=A105177907376A5&cp=5797C7865AD54E1&_=时间";

    public static String getNewsUrl(String category) {
        return NEWS_URL.replace("类型", category);
    }

    public static String getNewsRefreshUrl(String category, String max_behot_time) {
        return NEWS_URL_REFRESH
                .replace("类型", category)
                .replace("时间", max_behot_time);
    }
}
