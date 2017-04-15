package com.meiji.toutiao.news.channel;

import android.os.Bundle;
import android.support.annotation.Nullable;

import com.meiji.toutiao.BaseActivity;

/**
 * Created by Meiji on 2017/3/10.
 */

public class NewsChannelActivity extends BaseActivity {

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getSupportFragmentManager().beginTransaction()
                .replace(android.R.id.content, NewsChannelFragment.newInstance())
                .commit();
    }
}




