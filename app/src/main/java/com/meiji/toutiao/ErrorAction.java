package com.meiji.toutiao;

import android.support.annotation.NonNull;

import io.reactivex.functions.Consumer;

/**
 * Created by Meiji on 2017/6/18.
 */

public class ErrorAction {

    @NonNull
    public static Consumer<Throwable> error() {
        return throwable -> {
            if (BuildConfig.DEBUG) {
                throwable.printStackTrace();
            }
        };
    }

    public static void print(@NonNull Throwable throwable) {
        if (BuildConfig.DEBUG) {
            throwable.printStackTrace();
        }
    }
}
