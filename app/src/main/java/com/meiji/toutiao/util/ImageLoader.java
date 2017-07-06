package com.meiji.toutiao.util;

import android.content.Context;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestListener;

/**
 * Created by Meiji on 2017/5/31.
 */

public class ImageLoader {

    public static void loadCenterCrop(Context context, String url, ImageView view, int defaultResId) {
        if (SettingUtil.getInstance().getIsNoPhotoMode() && NetWorkUtil.isMobileConnected(context)) {
            view.setImageResource(defaultResId);
        } else {
            Glide.with(context).load(url).crossFade().centerCrop().into(view);
        }
    }

    /**
     * 带加载异常图片
     */
    public static void loadCenterCrop(Context context, String url, ImageView view, int defaultResId, int errorResId) {
        if (SettingUtil.getInstance().getIsNoPhotoMode() && NetWorkUtil.isMobileConnected(context)) {
            view.setImageResource(defaultResId);
        } else {
            Glide.with(context).load(url).crossFade().centerCrop().error(errorResId).into(view);
        }
    }

    /**
     * 带监听处理
     */
    public static void loadCenterCrop(Context context, String url, ImageView view, RequestListener listener) {
        Glide.with(context).load(url).crossFade().centerCrop().listener(listener).into(view);
    }
}
