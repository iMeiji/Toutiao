package com.meiji.toutiao.utils;

import android.content.SharedPreferences;

import com.meiji.toutiao.InitApp;

import static android.content.Context.MODE_PRIVATE;

/**
 * Created by Meiji on 2016/12/12.
 */
public class ColorUtil {

    public static int getColor() {
        SharedPreferences prefs = InitApp.AppContext.getSharedPreferences(InitApp.AppContext.getPackageName(), MODE_PRIVATE);
        int defaultColor = -14776091;
        return prefs.getInt("color", defaultColor);
    }

    public static void setColor(int color) {
        SharedPreferences prefs = InitApp.AppContext.getSharedPreferences(InitApp.AppContext.getPackageName(), MODE_PRIVATE);
        prefs.edit().putInt("color", color).apply();
    }
}
