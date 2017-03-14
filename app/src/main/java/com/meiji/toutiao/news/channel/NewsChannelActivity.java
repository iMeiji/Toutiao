package com.meiji.toutiao.news.channel;

import android.os.Bundle;
import android.support.annotation.Nullable;

import com.meiji.toutiao.BaseActivity;
import com.meiji.toutiao.R;

/**
 * Created by Meiji on 2017/3/10.
 */

public class NewsChannelActivity extends BaseActivity {

    private NewsChannelFragment newsChannelFragment;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.container);
        newsChannelFragment = NewsChannelFragment.newInstance();
        getSupportFragmentManager().beginTransaction()
                .replace(R.id.container, newsChannelFragment, newsChannelFragment.getClass().getName())
                .commit();
    }

    @Override
    public void onBackPressed() {
        if (newsChannelFragment.onSaveData()) {
            setResult(RESULT_OK);
        }
        super.onBackPressed();
    }
}




