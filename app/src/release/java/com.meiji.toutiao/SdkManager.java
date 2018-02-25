package com.meiji.toutiao;

import android.app.Application;
import android.content.Context;

import okhttp3.OkHttpClient;

/**
 * Created by Meiji on 2017/5/6.
 */

public class SdkManager {
    public static void initStetho(Context context) {
    }

    public static OkHttpClient.Builder initInterceptor(OkHttpClient.Builder builder) {
        return builder;
    }

    public static void initLeakCanary(Application app) {
    }
}
