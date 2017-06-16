package com.meiji.toutiao.module.search;

import android.app.SearchManager;
import android.content.Intent;
import android.os.Bundle;

import com.meiji.toutiao.R;
import com.meiji.toutiao.module.base.BaseActivity;

@Deprecated
public class SearchActivity extends BaseActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.container);
        Intent intent = getIntent();
        if (Intent.ACTION_SEARCH.equals(intent.getAction())) {
            String query = intent.getStringExtra(SearchManager.QUERY);
            getSupportFragmentManager().beginTransaction()
                    .replace(R.id.container, SearchFragment.newInstance(query))
                    .commit();
        }
    }
}
