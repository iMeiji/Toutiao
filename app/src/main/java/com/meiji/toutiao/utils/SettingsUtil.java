package com.meiji.toutiao.utils;

import android.content.SharedPreferences;
import android.preference.PreferenceManager;

import com.meiji.toutiao.InitApp;

/**
 * Created by Meiji on 2017/2/20.
 */

public class SettingsUtil {

    private SettingsUtil() {

    }

    public static SettingsUtil getInstance() {
        return SettingsUtilInstance.instance;
    }

    public boolean getNoPhotoMode() {
        SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(InitApp.AppContext);
        return prefs.getBoolean("switch_no_photo_mode", false);
    }

    private static final class SettingsUtilInstance {
        private static final SettingsUtil instance = new SettingsUtil();
    }
}
