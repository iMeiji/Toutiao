package com.meiji.toutiao.module.media.article;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;

import com.meiji.toutiao.InitApp;
import com.meiji.toutiao.R;
import com.meiji.toutiao.bean.media.MediaChannelBean;
import com.meiji.toutiao.module.base.BaseActivity;

/**
 * Created by Meiji on 2017/4/10.
 */

public class MediaArticleActivity extends BaseActivity {

    private static final String TAG = "MediaArticleActivity";

    public static void startActivity(MediaChannelBean bean) {
        InitApp.AppContext.startActivity(new Intent(InitApp.AppContext, MediaArticleActivity.class)
                .putExtra(TAG, bean)
                .addFlags(Intent.FLAG_ACTIVITY_NEW_TASK));
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.container);
        MediaArticleFragment fragment = MediaArticleFragment.newInstance(getIntent().getParcelableExtra(TAG));
        getSupportFragmentManager().beginTransaction()
                .replace(R.id.container, fragment)
                .commit();
    }
}
