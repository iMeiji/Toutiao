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

    private NewsContentFragment newsContentFragment;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.content_main);
        newsContentFragment = NewsContentFragment.newInstance(getIntent().getParcelableExtra(TAG));
        getSupportFragmentManager().beginTransaction()
                .replace(R.id.content_main, newsContentFragment, newsContentFragment.getClass().getName())
                .commit();
    }
}
