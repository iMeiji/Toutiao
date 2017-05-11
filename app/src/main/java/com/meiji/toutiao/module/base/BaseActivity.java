package com.meiji.toutiao.module.base;

import android.annotation.TargetApi;
import android.graphics.drawable.ColorDrawable;
import android.os.Build;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;

import com.meiji.toutiao.R;
import com.trello.rxlifecycle2.components.support.RxAppCompatActivity;

/**
 * Created by Meiji on 2016/12/12.
 */

public abstract class BaseActivity extends RxAppCompatActivity {

    /**
     * 初始化 Toolbar
     */
    protected void initToolBar(Toolbar toolbar, boolean homeAsUpEnabled, String title) {
        toolbar.setTitle(title);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(homeAsUpEnabled);
    }

    @TargetApi(Build.VERSION_CODES.M)
    @Override
    protected void onResume() {
        super.onResume();
        int color = getResources().getColor(R.color.colorPrimary, getTheme());
        if (getSupportActionBar() != null)
            getSupportActionBar().setBackgroundDrawable(new ColorDrawable(color));
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            getWindow().setStatusBarColor(getResources().getColor(R.color.colorPrimaryDark, getTheme()));
            getWindow().setNavigationBarColor(getResources().getColor(R.color.colorPrimaryDark, getTheme()));
        }
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            onBackPressed();
        }
        return super.onOptionsItemSelected(item);
    }

//    @Override
//    protected void onDestroy() {
//        for (Call call : InitApp.getOkHttpClient().dispatcher().queuedCalls()) {
//            call.cancel();
//        }
//        for (Call call : InitApp.getOkHttpClient().dispatcher().runningCalls()) {
//            call.cancel();
//        }
//        super.onDestroy();
//    }

    @Override
    public void onBackPressed() {
        // Fragment 逐个出栈
        int count = getSupportFragmentManager().getBackStackEntryCount();
        if (count == 0) {
            super.onBackPressed();
        } else {
            getSupportFragmentManager().popBackStack();
        }
    }
}
