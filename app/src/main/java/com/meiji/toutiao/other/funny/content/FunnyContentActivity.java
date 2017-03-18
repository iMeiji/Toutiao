package com.meiji.toutiao.other.funny.content;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;

import com.meiji.toutiao.BaseActivity;
import com.meiji.toutiao.InitApp;
import com.meiji.toutiao.R;
import com.meiji.toutiao.bean.other.funny.FunnyArticleBean;

/**
 * Created by Meiji on 2017/3/1.
 */

public class FunnyContentActivity extends BaseActivity {

    private static final String TAG = "FunnyContentActivity";

    public static void startActivity(FunnyArticleBean.DataBean bean) {
        InitApp.AppContext.startActivity(new Intent(InitApp.AppContext, FunnyContentActivity.class)
                .putExtra(FunnyContentActivity.TAG, bean)
                .addFlags(Intent.FLAG_ACTIVITY_NEW_TASK));
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.container);
        FunnyContentFragment funnyContentFragment = FunnyContentFragment.newInstance(getIntent().getParcelableExtra(TAG));
        getSupportFragmentManager().beginTransaction()
                .replace(R.id.container, funnyContentFragment, funnyContentFragment.getClass().getName())
                .commit();
    }
}
