package com.meiji.toutiao.module.news.comment;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;

import com.meiji.toutiao.InitApp;
import com.meiji.toutiao.R;
import com.meiji.toutiao.module.base.BaseActivity;

/**
 * Created by Meiji on 2017/2/28.
 */

public class NewsCommentActivity extends BaseActivity {

    private static final String TAG = "NewsCommentActivity";
    private static final String ARG_GROUPID = "groupId";
    private static final String ARG_ITEMID = "itemId";

    public static void launch(String groupId, String itemId) {
        InitApp.AppContext.startActivity(new Intent(InitApp.AppContext, NewsCommentActivity.class)
                .putExtra(ARG_GROUPID, groupId)
                .putExtra(ARG_ITEMID, itemId)
                .addFlags(Intent.FLAG_ACTIVITY_NEW_TASK));
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.container);
        Intent intent = getIntent();
        getSupportFragmentManager().beginTransaction()
                .replace(R.id.container,
                        NewsCommentFragment.newInstance(intent.getStringExtra(ARG_GROUPID), intent.getStringExtra(ARG_ITEMID)))
                .commit();
    }
}
