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
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

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

public class JokeCommentActivity extends BaseActivity {

    public static final String TAG = "NewsCommentView";

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.container);
        getSupportFragmentManager().beginTransaction()
                .replace(R.id.container, new JokeCommentFragment())
                .commit();
    }

    public static class JokeCommentFragment extends Fragment implements IJokeComment.View, SwipeRefreshLayout.OnRefreshListener {

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

        @Nullable
        @Override
        public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
            View view = inflater.inflate(R.layout.fragment_other_joke_comment, container, false);
            presenter = new JokeCommentPresenter(this);
            initView(view);
            initData();
            onRequestData();
            setHasOptionsMenu(true);
            return view;
        }

        private void initView(View view) {
            tv_content = (TextView) view.findViewById(R.id.tv_content);
            toolbar = (Toolbar) view.findViewById(R.id.toolbar);
            recycler_view = (RecyclerView) view.findViewById(R.id.recycler_view);
            refresh_layout = (SwipeRefreshLayout) view.findViewById(R.id.refresh_layout);

            AppCompatActivity activity = (AppCompatActivity) getActivity();
            activity.setSupportActionBar(toolbar);
            if (activity.getSupportActionBar() != null) {
                activity.getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            }

            recycler_view.setHasFixedSize(true);
            recycler_view.setLayoutManager(new LinearLayoutManager(activity));

            refresh_layout = (SwipeRefreshLayout) view.findViewById(R.id.refresh_layout);
            // 设置下拉刷新的按钮的颜色
            refresh_layout.setColorSchemeResources(R.color.colorPrimary);
            // 设置手指在屏幕上下拉多少距离开始刷新
            refresh_layout.setDistanceToTriggerSync(300);
            // 设置下拉刷新按钮的背景颜色
            refresh_layout.setProgressBackgroundColorSchemeColor(Color.WHITE);
            // 设置下拉刷新按钮的大小
            refresh_layout.setSize(SwipeRefreshLayout.DEFAULT);
            refresh_layout.setOnRefreshListener(this);
            toolbar.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    recycler_view.smoothScrollToPosition(0);
                }
            });
        }

        private void initData() {
            Intent intent = getActivity().getIntent();
            JokeContentBean.DataBean.GroupBean bean = intent.getParcelableExtra(TAG);
            jokeId = bean.getId() + "";
            jokeCommentCount = bean.getComment_count() + "";
            jokeText = bean.getText();
            tv_content.setText(jokeText);
        }

        @Override
        public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
            inflater.inflate(R.menu.other_joke_comment, menu);
            super.onCreateOptionsMenu(menu, inflater);
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
                case R.id.other_joke_comment_copy:
                    ClipboardManager copy = (ClipboardManager) getActivity().getSystemService(Context.CLIPBOARD_SERVICE);
                    ClipData clipData = ClipData.newPlainText("text", jokeText);
                    copy.setPrimaryClip(clipData);
                    Snackbar.make(refresh_layout, R.string.copied_to_clipboard, Snackbar.LENGTH_SHORT).show();
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
            presenter.doGetUrl(jokeId, jokeCommentCount);
        }

        @Override
        public void onSetAdapter(final List<JokeCommentBean.DataBean.RecentCommentsBean> list) {
            if (adapter == null) {
                adapter = new JokeCommentAdapter(list, getActivity());
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
        public void onShowRefreshing() {
            refresh_layout.post(new Runnable() {
                @Override
                public void run() {
                    refresh_layout.setRefreshing(true);
                }
            });
        }

        @Override
        public void onHideRefreshing() {
            refresh_layout.post(new Runnable() {
                @Override
                public void run() {
                    refresh_layout.setRefreshing(false);
                }
            });
        }

        @Override
        public void onFail() {
            Snackbar.make(refresh_layout, R.string.network_error, Snackbar.LENGTH_SHORT).show();
        }

        @Override
        public void onFinish() {
            Snackbar.make(refresh_layout, R.string.no_more, Snackbar.LENGTH_SHORT).show();
        }
    }
}
