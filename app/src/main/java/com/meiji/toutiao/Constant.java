package com.meiji.toutiao;

import android.graphics.Color;

/**
 * Created by Meiji on 2017/4/13.
 */

public class Constant {

    public static final String USER_AGENT_MOBILE = "Mozilla/5.0 (Linux; Android 5.1.1; Nexus 6 Build/LYZ28E) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/57.0.2987.133 Mobile Safari/537.36";

    public static final String USER_AGENT_PC = "Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/56.0.2924.87 Safari/537.36";

    public static final int[] TAG_COLORS = new int[]{
            Color.parseColor("#90C5F0"),
            Color.parseColor("#91CED5"),
            Color.parseColor("#F88F55"),
            Color.parseColor("#C0AFD0"),
            Color.parseColor("#E78F8F"),
            Color.parseColor("#67CCB7"),
            Color.parseColor("#F6BC7E")
    };

    public static final int[] ICONS_DRAWABLES = new int[]{
            R.mipmap.ic_launcher_circle,
            R.mipmap.ic_launcher_rect,
            R.mipmap.ic_launcher_square};

    public static final String[] ICONS_TYPE = new String[]{"circle", "rect", "square"};

    public static final int SLIDABLE_DISABLE = 0;
    public static final int SLIDABLE_EDGE = 1;
    public static final int SLIDABLE_FULL = 2;

    public static final String AS = "as";
    public static final String CP = "cp";

    public static final int NEWS_CHANNEL_ENABLE = 1;
    public static final int NEWS_CHANNEL_DISABLE = 0;

    /**
     * 这段js函数的功能就是，遍历所有的img几点，并添加onclick函数，函数的功能是在图片点击的时候调用本地java接口并传递url过去
     * {@link com.meiji.toutiao.module.news.content.NewsContentPresenter#openImage(String)}
     */
    public static final String JS_INJECT_IMG = "javascript:(function(){" +
            "var objs = document.getElementsByTagName(\"img\"); " +
            "for(var i=0;i<objs.length;i++)  " +
            "{"
            + "    objs[i].onclick=function()  " +
            "    {  "
            + "        window.imageListener.openImage(this.src);  " +
            "    }  " +
            "}" +
            "})()";
}
