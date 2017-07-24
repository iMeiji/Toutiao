package com.meiji.toutiao;

import android.app.Application;
import android.content.Context;
import android.support.v7.app.AppCompatDelegate;

import com.meiji.toutiao.util.SettingUtil;

import java.util.Calendar;

/**
 * Created by Meiji on 2016/12/12.
 */

public class InitApp extends Application {

    public static Context AppContext;

    @Override
    public void onCreate() {
        super.onCreate();
        AppContext = getApplicationContext();
        // 获取是否开启 "自动切换夜间模式"
        if (SettingUtil.getInstance().getIsAutoNightMode()) {
            Calendar calendar = Calendar.getInstance();
            int hour = calendar.get(Calendar.HOUR_OF_DAY);
            if (hour >= 22 || hour < 6) {
                AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES);
            } else {
                AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO);
            }
        } else {
            // 获取当前主题
            if (SettingUtil.getInstance().getIsNightMode()) {
                AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES);
            } else {
                AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO);
            }
        }
        if (BuildConfig.DEBUG) {
            SdkManager.initStetho(AppContext);
        }
    }
}
