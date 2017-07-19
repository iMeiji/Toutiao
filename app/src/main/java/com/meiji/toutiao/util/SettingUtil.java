package com.meiji.toutiao.util;

import android.content.SharedPreferences;
import android.graphics.Color;
import android.preference.PreferenceManager;

import com.meiji.toutiao.InitApp;
import com.meiji.toutiao.R;

/**
 * Created by Meiji on 2017/2/20.
 */

public class SettingUtil {

    private SharedPreferences setting = PreferenceManager.getDefaultSharedPreferences(InitApp.AppContext);

    public static SettingUtil getInstance() {
        return SettingsUtilInstance.instance;
    }

    /**
     * 获取是否开启无图模式
     */
    public boolean getIsNoPhotoMode() {
        return setting.getBoolean("switch_noPhotoMode", false) && NetWorkUtil.isMobileConnected(InitApp.AppContext);
    }

    /**
     * 获取主题颜色
     */
    public int getColor() {
        int defaultColor = InitApp.AppContext.getResources().getColor(R.color.colorPrimary);
        int color = setting.getInt("color", defaultColor);
        if ((color != 0) && Color.alpha(color) != 255) {
            return defaultColor;
        }
        return color;
    }

    /**
     * 设置主题颜色
     */
    public void setColor(int color) {
        setting.edit().putInt("color", color).apply();
    }

    /**
     * 获取是否开启夜间模式
     */
    public boolean getIsNightMode() {
        return setting.getBoolean("switch_nightMode", false);
    }

    /**
     * 设置夜间模式
     */
    public void setIsNightMode(boolean flag) {
        setting.edit().putBoolean("switch_nightMode", flag).apply();
    }

    /**
     * 获取是否开启导航栏上色
     */
    public boolean getNavBar() {
        return setting.getBoolean("nav_bar", false);
    }

    /**
     * 获取是否开启视频强制横屏
     */
    public boolean getVideoOrientation() {
        return setting.getBoolean("video_force_landscape", false);
    }

    /**
     * 获取图标值
     */
    public int getCustomIconValue() {
        String s = setting.getString("custom_icon", "0");
        return Integer.parseInt(s);
    }

    /**
     * 获取滑动返回值
     */
    public int getSlidable() {
        String s = setting.getString("slidable", "1");
        return Integer.parseInt(s);
    }

    private static final class SettingsUtilInstance {
        private static final SettingUtil instance = new SettingUtil();
    }
}
