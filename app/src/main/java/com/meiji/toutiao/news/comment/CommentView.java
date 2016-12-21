package com.meiji.toutiao.news.comment;

import android.content.ClipData;
import android.content.ClipboardManager;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.Snackbar;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import com.meiji.toutiao.BaseActivity;
import com.meiji.toutiao.R;
import com.meiji.toutiao.adapter.news.NewsCommentsAdapter;
import com.meiji.toutiao.bean.news.NewsCommentBean;
import com.meiji.toutiao.interfaces.IOnItemClickListener;

import java.util.List;

/**
 * Created by Meiji on 2016/12/20.
 */

public class CommentView extends BaseActivity implements SwipeRefreshLayout.OnRefreshListener, IComment.View {

    public static final String GROUP_ID = "group_id";
    public static final String ITEM_ID = "item_id";
    private static final String TAG = "CommentView";
    private String groupId;
    private String itemId;


    private NewsCommentsAdapter adapter;
    private RecyclerView recycler_view;
    private SwipeRefreshLayout refresh_layout;
    private Toolbar toolbar;


    private IComment.Presenter presenter;
    private boolean canLoading;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.news_comment_main);
        presenter = new CommentPresenter(this);
        initView();
        initData();
        onRequestData();
    }

    private void initData() {
        Intent intent = getIntent();
        groupId = intent.getStringExtra(GROUP_ID);
        itemId = intent.getStringExtra(ITEM_ID);
    }

    private void initView() {
        recycler_view = (RecyclerView) findViewById(R.id.recycler_view);
        recycler_view.setHasFixedSize(true);
        recycler_view.setLayoutManager(new LinearLayoutManager(this));

        refresh_layout = (SwipeRefreshLayout) findViewById(R.id.refresh_layout);
        // 设置下拉刷新的按钮的颜色
        refresh_layout.setColorSchemeResources(R.color.colorPrimary);
        // 设置手指在屏幕上下拉多少距离开始刷新
        refresh_layout.setDistanceToTriggerSync(300);
        // 设置下拉刷新按钮的背景颜色
        refresh_layout.setProgressBackgroundColorSchemeColor(Color.WHITE);
        // 设置下拉刷新按钮的大小
        refresh_layout.setSize(SwipeRefreshLayout.DEFAULT);
        refresh_layout.setOnRefreshListener(this);

        toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        if (getSupportActionBar() != null) {
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        }
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                onBackPressed();
                break;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onRefresh() {
        presenter.doRefresh();
    }

    @Override
    public void onRequestData() {
        presenter.doGetUrl(groupId, itemId);
    }

    @Override
    public void onSetAdapter(final List<NewsCommentBean.DataBean.CommentsBean> list) {
        if (adapter == null) {
            adapter = new NewsCommentsAdapter(list, this);
            recycler_view.setAdapter(adapter);
            adapter.setOnItemClickListener(new IOnItemClickListener() {
                @Override
                public void onClick(View view, int position) {
                    ClipboardManager copy = (ClipboardManager) getSystemService(Context.CLIPBOARD_SERVICE);
                    ClipData clipData = ClipData.newPlainText("text", list.get(position).getText());
                    copy.setPrimaryClip(clipData);
                    Log.d(TAG, "onClick: " + clipData);
                    Toast.makeText(CommentView.this, "已复制", Toast.LENGTH_SHORT).show();
                }
            });
        } else {
            adapter.notifyItemInserted(list.size());
        }

        canLoading = true;

        recycler_view.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(RecyclerView recyclerView, int newState) {
                super.onScrollStateChanged(recyclerView, newState);
                if (newState == RecyclerView.SCROLL_STATE_IDLE) {
                    if (!recyclerView.canScrollVertically(1)) {
                        if (canLoading) {
                            presenter.doRefresh();
                            canLoading = false;
                        }
                    }
                }
            }

            @Override
            public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);
            }
        });
    }

    @Override
    public void onShowRefreshing() {
        refresh_layout.setRefreshing(true);
    }

    @Override
    public void onHideRefreshing() {
        refresh_layout.setRefreshing(false);
    }

    @Override
    public void onFail() {
        Snackbar.make(refresh_layout, R.string.network_error, Snackbar.LENGTH_SHORT).show();
    }
}
