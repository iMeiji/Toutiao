package com.meiji.toutiao.other.joke.comment;

import android.content.ClipData;
import android.content.ClipboardManager;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.BottomSheetDialog;
import android.support.design.widget.Snackbar;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.meiji.toutiao.BaseActivity;
import com.meiji.toutiao.R;
import com.meiji.toutiao.adapter.other.joke.JokeCommentAdapter;
import com.meiji.toutiao.bean.other.joke.JokeCommentBean;
import com.meiji.toutiao.bean.other.joke.JokeContentBean;
import com.meiji.toutiao.interfaces.IOnItemClickListener;

import java.util.List;

/**
 * Created by Meiji on 2017/1/1.
 */

public class JokeCommentView extends BaseActivity implements IJokeComment.View, SwipeRefreshLayout.OnRefreshListener {

    public static final String TAG = "NewsCommentView";
    private String jokeId;
    private String jokeCommentCount;
    private String jokeText;
    private boolean canLoading;

    private TextView tv_content;
    private Toolbar toolbar;
    private RecyclerView recycler_view;
    private SwipeRefreshLayout refresh_layout;
    private JokeCommentAdapter adapter;

    private IJokeComment.Presenter presenter;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.other_joke_comment_main);
        presenter = new JokeCommentPresenter(this);
        initView();
        initData();
        onRequestData();
    }

    private void initView() {
        tv_content = (TextView) findViewById(R.id.tv_content);
        toolbar = (Toolbar) findViewById(R.id.toolbar);
        recycler_view = (RecyclerView) findViewById(R.id.recycler_view_photo);
        refresh_layout = (SwipeRefreshLayout) findViewById(R.id.refresh_layout_photo);

        setSupportActionBar(toolbar);
        if (getSupportActionBar() != null) {
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        }

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
    }

    private void initData() {
        Intent intent = getIntent();
        JokeContentBean.DataBean.GroupBean bean = intent.getParcelableExtra(TAG);
        jokeId = bean.getId() + "";
        jokeCommentCount = bean.getComment_count() + "";
        jokeText = bean.getText();
        tv_content.setText(jokeText);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.other_joke_comment, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.other_joke_comment_share:
                Intent shareIntent = new Intent()
                        .setAction(Intent.ACTION_SEND)
                        .setType("text/plain")
                        .putExtra(Intent.EXTRA_TEXT, jokeText);
                startActivity(Intent.createChooser(shareIntent, getString(R.string.share_to)));
                break;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onRequestData() {
        presenter.doGetUrl(jokeId, jokeCommentCount);
    }

    @Override
    public void onSetAdapter(final List<JokeCommentBean.DataBean.RecentCommentsBean> list) {
        if (adapter == null) {
            adapter = new JokeCommentAdapter(list, this);
            recycler_view.setAdapter(adapter);
            adapter.setOnItemClickListener(new IOnItemClickListener() {
                @Override
                public void onClick(View view, int position) {
                    //JokeCommentBean.DataBean.RecentCommentsBean bean = (JokeCommentBean.DataBean.RecentCommentsBean) list.get(position);
                    showCopyDialog(list.get(position).getText());
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

    private void showCopyDialog(final String content) {

        final BottomSheetDialog dialog = new BottomSheetDialog(this);
        View view = getLayoutInflater().inflate(R.layout.bottom_sheet, null);
        dialog.setContentView(view);
        TextView tv_cpoy = (TextView) view.findViewById(R.id.tv_cpoy);
        TextView tv_share = (TextView) view.findViewById(R.id.tv_share);
        tv_cpoy.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ClipboardManager copy = (ClipboardManager) getSystemService(Context.CLIPBOARD_SERVICE);
                ClipData clipData = ClipData.newPlainText("text", content);
                copy.setPrimaryClip(clipData);
                Toast.makeText(JokeCommentView.this, "已复制", Toast.LENGTH_SHORT).show();
                dialog.dismiss();
            }
        });
        tv_share.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent shareIntent = new Intent()
                        .setAction(Intent.ACTION_SEND)
                        .setType("text/plain")
                        .putExtra(Intent.EXTRA_TEXT, content);
                startActivity(Intent.createChooser(shareIntent, getString(R.string.share_to)));
                dialog.dismiss();
            }
        });
        dialog.show();


//        final MaterialDialog dialog = new MaterialDialog.Builder(this)
//                .title("是否复制评论?")
//                .content(content).build();
//        dialog.setActionButton(DialogAction.NEGATIVE, "取消");
//        dialog.getActionButton(DialogAction.NEGATIVE).setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                dialog.dismiss();
//            }
//        });
//
//        dialog.setActionButton(DialogAction.POSITIVE, "确定");
//        dialog.getActionButton(DialogAction.POSITIVE).setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                ClipboardManager copy = (ClipboardManager) getSystemService(Context.CLIPBOARD_SERVICE);
//                ClipData clipData = ClipData.newPlainText("text", content);
//                copy.setPrimaryClip(clipData);
//                Toast.makeText(JokeCommentView.this, "已复制", Toast.LENGTH_SHORT).show();
//                dialog.dismiss();
//            }
//        });
//
//        dialog.show();
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

    @Override
    public void onRefresh() {
        presenter.doRefresh();
    }

    @Override
    public void onFinish() {
        Snackbar.make(refresh_layout, R.string.no_more, Snackbar.LENGTH_SHORT).show();
    }
}
