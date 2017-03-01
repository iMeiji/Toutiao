package com.meiji.toutiao.other.funny.content;

import android.os.Bundle;
import android.support.annotation.Nullable;

import com.meiji.toutiao.BaseActivity;
import com.meiji.toutiao.R;

/**
 * Created by Meiji on 2017/3/1.
 */

public class FunnyContentActivity extends BaseActivity {

    public static final String TAG = "FunnyContentActivity";

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
