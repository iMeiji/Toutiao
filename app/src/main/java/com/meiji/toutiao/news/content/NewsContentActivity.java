package com.meiji.toutiao.news.content;

import android.os.Bundle;
import android.support.annotation.Nullable;

import com.meiji.toutiao.BaseActivity;
import com.meiji.toutiao.R;

/**
 * Created by Meiji on 2017/2/28.
 */

public class NewsContentActivity extends BaseActivity {

    public static final String TAG = "NewsContentActivity";

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.container);
        NewsContentFragment newsContentFragment = NewsContentFragment.newInstance(getIntent().getParcelableExtra(TAG));
        getSupportFragmentManager().beginTransaction()
                .replace(R.id.container, newsContentFragment, newsContentFragment.getClass().getName())
                .commit();
    }
}
