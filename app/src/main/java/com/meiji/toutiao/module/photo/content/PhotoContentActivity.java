package com.meiji.toutiao.module.photo.content;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;

import com.meiji.toutiao.InitApp;
import com.meiji.toutiao.R;
import com.meiji.toutiao.bean.photo.PhotoArticleBean;
import com.meiji.toutiao.module.base.BaseActivity;

/**
 * Created by Meiji on 2017/3/1.
 */

public class PhotoContentActivity extends BaseActivity {

    private static final String TAG = "PhotoContentActivity";

    public static void launch(PhotoArticleBean.DataBean bean) {
        InitApp.AppContext.startActivity(new Intent(InitApp.AppContext, PhotoContentActivity.class)
                .putExtra(PhotoContentActivity.TAG, bean)
                .addFlags(Intent.FLAG_ACTIVITY_NEW_TASK));
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.container);
        PhotoContentFragment photoContentFragment =
                PhotoContentFragment.newInstance(getIntent().getParcelableExtra(TAG));
        getSupportFragmentManager().beginTransaction()
                .replace(R.id.container, photoContentFragment)
                .commit();
    }
}
