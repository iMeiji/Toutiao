package com.meiji.toutiao.api;

/**
 * Created by Meiji on 2017/4/7.
 */

public class MediaApi {

    private static final String mediaArticleUrl =
            "http://www.toutiao.com/pgc/ma/?media_id=头条号&page_type=1&count=20&version=2&platform=pc&as=479BB4B7254C150&cp=585DB1871ED64E1&max_behot_time=时间";

    public static String getMediaArticleUrl(String mediaId, String max_behot_time) {
        return mediaArticleUrl
                .replace("头条号", mediaId)
                .replace("时间", max_behot_time);
    }
}
