package com.meiji.toutiao.news.channel;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;

import com.meiji.toutiao.R;

/**
 * Created by Meiji on 2017/3/10.
 */

public class NewsChannelActivity extends AppCompatActivity {

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.container);
        getSupportFragmentManager().beginTransaction()
                .replace(R.id.container, NewsChannelFragment.newInstance())
                .commit();
    }
}




