package com.meiji.toutiao;

import android.annotation.TargetApi;
import android.graphics.drawable.ColorDrawable;
import android.os.Build;
import android.support.v7.app.AppCompatActivity;
import android.view.MenuItem;

import okhttp3.Call;

/**
 * Created by Meiji on 2016/12/12.
 */

public class BaseActivity extends AppCompatActivity {

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
        switch (item.getItemId()) {
            case android.R.id.home:
                onBackPressed();
                break;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    protected void onDestroy() {
        for (Call call : InitApp.getOkHttpClient().dispatcher().queuedCalls()) {
            call.cancel();
        }
        for (Call call : InitApp.getOkHttpClient().dispatcher().runningCalls()) {
            call.cancel();
        }
        super.onDestroy();
    }

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
