package com.meiji.toutiao.utils;

import android.content.SharedPreferences;
import android.preference.PreferenceManager;

import com.meiji.toutiao.InitApp;

import static android.content.Context.MODE_PRIVATE;

/**
 * Created by Meiji on 2017/2/20.
 */

public class SettingsUtil {

    SharedPreferences prefs = InitApp.AppContext.getSharedPreferences(InitApp.AppContext.getPackageName(), MODE_PRIVATE);

    private SettingsUtil() {

    }

    public static SettingsUtil getInstance() {
        return SettingsUtilInstance.instance;
    }

    public boolean getNoPhotoMode() {
        return PreferenceManager.getDefaultSharedPreferences(InitApp.AppContext).getBoolean("switch_no_photo_mode", false);
    }

    public int getColor() {
        int defaultColor = -14776091;
        return prefs.getInt("color", defaultColor);
    }

    public void setColor(int color) {
        prefs.edit().putInt("color", color).apply();
    }

    public boolean getIsNightMode() {
        return prefs.getBoolean("switch_night_mode", false);
    }

    public void setIsNightMode(boolean flag) {
        prefs.edit().putBoolean("switch_night_mode", flag).apply();
    }

    private static final class SettingsUtilInstance {
        private static final SettingsUtil instance = new SettingsUtil();
    }
}
