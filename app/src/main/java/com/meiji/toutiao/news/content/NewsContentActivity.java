package com.meiji.toutiao.news.content;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;

import com.meiji.toutiao.R;

/**
 * Created by Meiji on 2017/2/28.
 */

public class NewsContentActivity extends AppCompatActivity {

    public static final String TAG = "NewsContentActivity";

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.container);
        NewsContentFragment newsContentFragment = NewsContentFragment.newInstance(getIntent().getParcelableExtra(TAG));
        getSupportFragmentManager().beginTransaction()
                .replace(R.id.container, newsContentFragment)
                .commit();
    }
}
