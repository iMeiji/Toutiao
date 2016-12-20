package com.meiji.toutiao.news.comment;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;

import com.meiji.toutiao.BaseActivity;
import com.meiji.toutiao.R;
import com.meiji.toutiao.adapter.news.NewsCommentsAdapter;
import com.meiji.toutiao.bean.news.NewsCommentBean;

import java.util.List;

/**
 * Created by Meiji on 2016/12/20.
 */

public class CommentView extends BaseActivity implements SwipeRefreshLayout.OnRefreshListener, IComment.View {

    public static final String GROUP_ID = "group_id";
    public static final String ITEM_ID = "item_id";
    private String groupId;
    private String itemId;


    private NewsCommentsAdapter adapter;
    private RecyclerView recycler_view;
    private SwipeRefreshLayout refresh_layout;
    private Toolbar toolbar;


    private IComment.Presenter presenter;

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

    }

    @Override
    public void onRequestData() {
        presenter.doRequestData(groupId, itemId);
    }

    @Override
    public void onSetAdapter(List<NewsCommentBean.DataBean.CommentsBean> list) {
        if (adapter == null) {
            adapter = new NewsCommentsAdapter(list, this);
            recycler_view.setAdapter(adapter);
        } else {
            adapter.notifyItemInserted(list.size());
        }
    }

    @Override
    public void onShowRefreshing() {

    }

    @Override
    public void onHideRefreshing() {

    }

    @Override
    public void onFail() {

    }
}
