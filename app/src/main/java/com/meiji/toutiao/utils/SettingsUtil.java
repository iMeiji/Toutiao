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

    public boolean getPhotoSwitch() {
        SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(InitApp.AppContext);
        return prefs.getBoolean("photo_switch", false);
    }

    private static final class SettingsUtilInstance {
        private static final SettingsUtil instance = new SettingsUtil();
    }
}
