package com.meiji.toutiao.other.funny.comment;

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
import android.view.View;
import android.widget.Toast;

import com.afollestad.materialdialogs.DialogAction;
import com.afollestad.materialdialogs.MaterialDialog;
import com.meiji.toutiao.BaseActivity;
import com.meiji.toutiao.R;
import com.meiji.toutiao.adapter.other.funny.FunnyCommentAdapter;
import com.meiji.toutiao.bean.other.funny.FunnyCommentBean;
import com.meiji.toutiao.interfaces.IOnItemClickListener;

import java.util.List;

/**
 * Created by Meiji on 2017/1/25.
 */

public class FunnyCommentView extends BaseActivity implements SwipeRefreshLayout.OnRefreshListener, IFunnyComment.View {

    public static final String GROUP_ID = "group_id";
    public static final String ITEM_ID = "item_id";
    private static final String TAG = "NewsCommentView";
    private String groupId;
    private String itemId;


    private FunnyCommentAdapter adapter;
    private RecyclerView recycler_view;
    private SwipeRefreshLayout refresh_layout;
    private Toolbar toolbar;


    private IFunnyComment.Presenter presenter;
    private boolean canLoading;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.other_funny_comment_main);
        presenter = new FunnyCommentPresenter(this);
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
        recycler_view = (RecyclerView) findViewById(R.id.recycler_view_photo);
        recycler_view.setHasFixedSize(true);
        recycler_view.setLayoutManager(new LinearLayoutManager(this));

        refresh_layout = (SwipeRefreshLayout) findViewById(R.id.refresh_layout_photo);
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
    public void onRefresh() {
        presenter.doRefresh();
    }

    @Override
    public void onRequestData() {
        presenter.doGetUrl(groupId, itemId);
    }

    @Override
    public void onSetAdapter(final List<FunnyCommentBean.DataBean.CommentsBean> list) {
        if (adapter == null) {
            adapter = new FunnyCommentAdapter(list, this);
            recycler_view.setAdapter(adapter);
            adapter.setOnItemClickListener(new IOnItemClickListener() {
                @Override
                public void onClick(View view, int position) {
                    showCopyDialog(position, list.get(position).getText());
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
        });
    }

    private void showCopyDialog(int position, final String content) {
        final MaterialDialog dialog = new MaterialDialog.Builder(FunnyCommentView.this)
                .title("是否复制评论?")
                .content(content).build();
        dialog.setActionButton(DialogAction.NEGATIVE, "取消");
        dialog.getActionButton(DialogAction.NEGATIVE).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialog.dismiss();
            }
        });

        dialog.setActionButton(DialogAction.POSITIVE, "确定");
        dialog.getActionButton(DialogAction.POSITIVE).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ClipboardManager copy = (ClipboardManager) getSystemService(Context.CLIPBOARD_SERVICE);
                ClipData clipData = ClipData.newPlainText("text", content);
                copy.setPrimaryClip(clipData);
                Toast.makeText(FunnyCommentView.this, "已复制", Toast.LENGTH_SHORT).show();
                dialog.dismiss();
            }
        });

        dialog.show();
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
