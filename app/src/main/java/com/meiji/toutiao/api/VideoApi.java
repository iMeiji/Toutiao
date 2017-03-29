package com.meiji.toutiao.api;

import java.util.Random;
import java.util.zip.CRC32;

/**
 * Created by Meiji on 2017/3/24.
 */

public class VideoApi {

    private static final String videoArticleUrl =
//            "http://www.toutiao.com/api/pc/feed/?category=类型&utm_source=toutiao&widen=1&tadrequire=true&as=A165F8CD147CFC5&cp=58D45C0FAC256E1";
            "http://toutiao.com/api/article/recent/?source=2&category=类型&as=A105177907376A5&cp=5797C7865AD54E1&count=20";

    private static final String videoContentUrl =
            "http://ib.365yg.com/video/urls/v/1/toutiao/mp4/视频ID?r=17位随机数&s=加密结果";

    public static String getVideoArticleUrl(String category) {
        return videoArticleUrl.replace("类型", category);
    }

    public static String getVideoContentUrl(String videoid) {
        String VIDEO_HOST = "http://ib.365yg.com";
        String VIDEO_URL = "/video/urls/v/1/toutiao/mp4/%s?r=%s";
        String r = getRandom();
        String s = String.format(VIDEO_URL, videoid, r);
        // 将/video/urls/v/1/toutiao/mp4/{videoid}?r={Math.random()} 进行crc32加密
        CRC32 crc32 = new CRC32();
        crc32.update(s.getBytes());
        String crcString = crc32.getValue() + "";
        String url = VIDEO_HOST + s + "&s=" + crcString;
        return url;
    }

    private static String getRandom() {
        Random random = new Random();
        StringBuilder result = new StringBuilder();
        for (int i = 0; i < 16; i++) {
            result.append(random.nextInt(10));
        }
        return result.toString();
    }
}
