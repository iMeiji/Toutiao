package com.meiji.toutiao;

import android.app.Application;
import android.content.Context;

import com.franmontiel.persistentcookiejar.ClearableCookieJar;
import com.franmontiel.persistentcookiejar.PersistentCookieJar;
import com.franmontiel.persistentcookiejar.cache.SetCookieCache;
import com.franmontiel.persistentcookiejar.persistence.SharedPrefsCookiePersistor;

import okhttp3.OkHttpClient;

/**
 * Created by Meiji on 2016/12/12.
 */

public class InitApp extends Application {

    public static Context AppContext;

    public static OkHttpClient okHttpClient = null;

    public static OkHttpClient getOkHttpClient() {
        if (okHttpClient == null) {
            ClearableCookieJar cookieJar =
                    new PersistentCookieJar(new SetCookieCache(), new SharedPrefsCookiePersistor(AppContext));
            okHttpClient = new OkHttpClient.Builder()
                    .cookieJar(cookieJar)
                    .build();
        }
        return okHttpClient;
    }

    @Override
    public void onCreate() {
        super.onCreate();
        AppContext = getApplicationContext();
    }
}
