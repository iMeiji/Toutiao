package com.meiji.toutiao.utils;

import android.content.Context;
import android.widget.ImageView;

import com.bumptech.glide.Glide;

/**
 * Created by Meiji on 2017/5/31.
 */

public class ImageLoader {

    public static void loadCenterCrop(Context context, String url, ImageView view, int defaultResId) {
        if (SettingsUtil.getInstance().getIsNoPhotoMode() && NetWorkUtil.isMobileConnected(context)) {
            view.setImageResource(defaultResId);
        } else {
            Glide.with(context).load(url).crossFade().centerCrop().into(view);
        }
    }
}
