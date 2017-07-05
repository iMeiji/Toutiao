package com.meiji.toutiao.util;

import android.content.SharedPreferences;
import android.graphics.Color;
import android.preference.PreferenceManager;

import com.meiji.toutiao.InitApp;
import com.meiji.toutiao.R;

/**
 * Created by Meiji on 2017/2/20.
 */

public class SettingsUtil {

    private SharedPreferences settings = PreferenceManager.getDefaultSharedPreferences(InitApp.AppContext);

    public static SettingsUtil getInstance() {
        return SettingsUtilInstance.instance;
    }

    public boolean getIsNoPhotoMode() {
        return settings.getBoolean("switch_noPhotoMode", false) && NetWorkUtil.isMobileConnected(InitApp.AppContext);
    }

    public int getColor() {
        int defaultColor = InitApp.AppContext.getResources().getColor(R.color.colorPrimary);
        int color = settings.getInt("color", defaultColor);
        if ((color != 0) && Color.alpha(color) != 255) {
            return defaultColor;
        }
        return color;
    }

    public void setColor(int color) {
        settings.edit().putInt("color", color).apply();
    }

    public boolean getIsNightMode() {
        return settings.getBoolean("switch_nightMode", false);
    }

    public void setIsNightMode(boolean flag) {
        settings.edit().putBoolean("switch_nightMode", flag).apply();
    }

    public boolean getNavBar() {
        return settings.getBoolean("nav_bar", false);
    }

    public boolean getVideoOrientation() {
        return settings.getBoolean("video_force_landscape", false);
    }

    private static final class SettingsUtilInstance {
        private static final SettingsUtil instance = new SettingsUtil();
    }
}
