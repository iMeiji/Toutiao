package com.meiji.toutiao.module.news.comment;

import android.content.ClipData;
import android.content.ClipboardManager;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.BottomSheetDialog;
import android.support.design.widget.Snackbar;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.util.DiffUtil;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;

import com.meiji.toutiao.R;
import com.meiji.toutiao.adapter.DiffCallback;
import com.meiji.toutiao.adapter.news.NewsCommentAdapter;
import com.meiji.toutiao.bean.news.NewsCommentBean;
import com.meiji.toutiao.interfaces.IOnItemClickListener;
import com.meiji.toutiao.module.base.BaseFragment;
import com.meiji.toutiao.utils.SettingsUtil;

import java.util.List;

/**
 * Created by Meiji on 2017/2/28.
 */

public class NewsCommentFragment extends BaseFragment<INewsComment.Presenter> implements SwipeRefreshLayout.OnRefreshListener, INewsComment.View {

    private static final String GROUP_ID = "groupId";
    private static final String ITEM_ID = "itemId";
    private static final String TAG = "NewsCommentFragment";
    private String groupId;
    private String itemId;
    private NewsCommentAdapter adapter;
    private RecyclerView recycler_view;
    private SwipeRefreshLayout refresh_layout;
    private Toolbar toolbar;
    private INewsComment.Presenter presenter;
    private boolean canLoading;

    public static NewsCommentFragment newInstance(String groupId, String itemId) {
        NewsCommentFragment instance = new NewsCommentFragment();
        Bundle bundle = new Bundle();
        bundle.putString(GROUP_ID, groupId);
        bundle.putString(ITEM_ID, itemId);
        instance.setArguments(bundle);
        return instance;
    }

    @Override
    protected int attachLayoutId() {
        return R.layout.fragment_news_comment;
    }

    @Override
    protected void initData() {
        Bundle arguments = getArguments();
        groupId = arguments.getString(GROUP_ID);
        itemId = arguments.getString(ITEM_ID);
        onLoadData();
    }

    @Override
    protected void initViews(View view) {
        recycler_view = (RecyclerView) view.findViewById(R.id.recycler_view_comment);
        recycler_view.setHasFixedSize(true);
        recycler_view.setLayoutManager(new LinearLayoutManager(getActivity()));

        refresh_layout = (SwipeRefreshLayout) view.findViewById(R.id.refresh_layout_comment);
        // 设置下拉刷新的按钮的颜色
        refresh_layout.setColorSchemeColors(SettingsUtil.getInstance().getColor());
        refresh_layout.setOnRefreshListener(this);

        toolbar = (Toolbar) view.findViewById(R.id.toolbar);
        initToolBar(toolbar, true, getString(R.string.title_comment));
        toolbar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                recycler_view.smoothScrollToPosition(0);
            }
        });
        toolbar.setBackgroundColor(SettingsUtil.getInstance().getColor());

        adapter = new NewsCommentAdapter(getActivity());
        recycler_view.setAdapter(adapter);
        adapter.setOnItemClickListener(new IOnItemClickListener() {
            @Override
            public void onClick(View view, int position) {
                showCopyDialog(position);
            }
        });

        setHasOptionsMenu(true);
    }

    @Override
    public void onRefresh() {
        presenter.doRefresh();
    }

    @Override
    public void onLoadData() {
        presenter.doLoadData(groupId, itemId);
    }

    @Override
    public void onSetAdapter(final List<NewsCommentBean.DataBean.CommentsBean> list) {
        List<NewsCommentBean.DataBean.CommentsBean> oldList = adapter.getList();
        DiffUtil.DiffResult result = DiffUtil.calculateDiff(new DiffCallback(oldList, list, DiffCallback.NEWS_COMMENT), true);
        result.dispatchUpdatesTo(adapter);
        adapter.setList(list);

        canLoading = true;

        recycler_view.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(RecyclerView recyclerView, int newState) {
                super.onScrollStateChanged(recyclerView, newState);
                if (newState == RecyclerView.SCROLL_STATE_IDLE) {
                    if (!recyclerView.canScrollVertically(1)) {
                        if (canLoading) {
                            presenter.doLoadMoreData();
                            canLoading = false;
                        }
                    }
                }
            }
        });
    }

    private void showCopyDialog(final int position) {
        final String content = presenter.doGetCopyContent(position);

        final BottomSheetDialog dialog = new BottomSheetDialog(getActivity());
        View view = getActivity().getLayoutInflater().inflate(R.layout.item_comment_action_sheet, null);
        view.findViewById(R.id.layout_copy_text).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ClipboardManager copy = (ClipboardManager) getActivity().getSystemService(Context.CLIPBOARD_SERVICE);
                ClipData clipData = ClipData.newPlainText("text", content);
                copy.setPrimaryClip(clipData);
                Snackbar.make(refresh_layout, R.string.copied_to_clipboard, Snackbar.LENGTH_SHORT).show();
                dialog.dismiss();
            }
        });
        view.findViewById(R.id.layout_share_text).setOnClickListener(new View.OnClickListener() {
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
        dialog.setContentView(view);
        dialog.show();
    }

    @Override
    public void onShowLoading() {
        refresh_layout.post(new Runnable() {
            @Override
            public void run() {
                refresh_layout.setRefreshing(true);
            }
        });
    }

    @Override
    public void onHideLoading() {
        refresh_layout.post(new Runnable() {
            @Override
            public void run() {
                refresh_layout.setRefreshing(false);
            }
        });
    }

    @Override
    public void onShowNetError() {
        Snackbar.make(refresh_layout, R.string.network_error, Snackbar.LENGTH_SHORT).show();
    }

    @Override
    public void setPresenter(INewsComment.Presenter presenter) {
        if (null == presenter) {
            this.presenter = new NewsCommentPresenter(this);
        }
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if (id == android.R.id.home) {
            getActivity().onBackPressed();
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onShowNoMore() {
        Snackbar.make(refresh_layout, R.string.no_more, Snackbar.LENGTH_SHORT).show();
    }
}
