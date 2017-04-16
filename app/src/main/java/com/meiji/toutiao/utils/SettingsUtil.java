package com.meiji.toutiao.utils;

import android.content.SharedPreferences;
import android.preference.PreferenceManager;

import com.meiji.toutiao.InitApp;

/**
 * Created by Meiji on 2017/2/20.
 */

public class SettingsUtil {

    private SharedPreferences settings = PreferenceManager.getDefaultSharedPreferences(InitApp.AppContext);

    public static SettingsUtil getInstance() {
        return SettingsUtilInstance.instance;
    }

    public boolean getIsNoPhotoMode() {
        return settings.getBoolean("switch_noPhotoMode", false);
    }

    public int getColor() {
        int defaultColor = -14776091;
        return settings.getInt("color", defaultColor);
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

    private static final class SettingsUtilInstance {
        private static final SettingsUtil instance = new SettingsUtil();
    }
}
