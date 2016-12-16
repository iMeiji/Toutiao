package com.meiji.toutiao;

import android.graphics.drawable.ColorDrawable;
import android.os.Build;
import android.support.v7.app.AppCompatActivity;

import com.meiji.toutiao.utils.ColorUtil;

/**
 * Created by Meiji on 2016/12/12.
 */

public class BaseActivity extends AppCompatActivity {

    @Override
    protected void onResume() {
        super.onResume();
        int color = ColorUtil.getColor();
        if (getSupportActionBar() != null)
            getSupportActionBar().setBackgroundDrawable(new ColorDrawable(color));
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            getWindow().setStatusBarColor(color);
            getWindow().setNavigationBarColor(color);
        }
    }
}
