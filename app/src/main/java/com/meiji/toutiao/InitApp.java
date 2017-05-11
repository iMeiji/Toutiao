package com.meiji.toutiao;

import android.app.Application;
import android.content.Context;
import android.support.v7.app.AppCompatDelegate;

import com.meiji.toutiao.utils.SettingsUtil;

/**
 * Created by Meiji on 2016/12/12.
 */

public class InitApp extends Application {

    public static Context AppContext;

    @Override
    public void onCreate() {
        super.onCreate();
        AppContext = getApplicationContext();
        if (SettingsUtil.getInstance().getIsNightMode()) {
            AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES);
        } else {
            AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO);
        }
        if (BuildConfig.DEBUG) {
            SdkManager.initStetho(AppContext);
        }
    }
}
