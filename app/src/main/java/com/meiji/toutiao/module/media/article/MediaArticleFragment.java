package com.meiji.toutiao.module.media.article;

import android.content.DialogInterface;
import android.os.Bundle;
import android.os.Parcelable;
import android.support.annotation.Nullable;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.util.DiffUtil;
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

import com.bumptech.glide.Glide;
import com.meiji.toutiao.R;
import com.meiji.toutiao.adapter.DiffCallback;
import com.meiji.toutiao.adapter.media.MediaArticleAdapter;
import com.meiji.toutiao.bean.media.MediaArticleBean;
import com.meiji.toutiao.bean.media.MediaChannelBean;
import com.meiji.toutiao.database.dao.MediaChannelDao;
import com.meiji.toutiao.interfaces.IOnItemClickListener;
import com.meiji.toutiao.widget.CircleImageView;

import java.util.List;

/**
 * Created by Meiji on 2017/4/11.
 */

public class MediaArticleFragment extends Fragment implements IMediaArticle.View, SwipeRefreshLayout.OnRefreshListener {

    private static final String TAG = "MediaArticleFragment";
    private RecyclerView recycler_view;
    private SwipeRefreshLayout refresh_layout;
    private MediaArticleAdapter adapter;
    private IMediaArticle.Presenter presenter;
    private MediaChannelBean bean;
    private boolean canLoading = false;
    private boolean canDelete = false;

    public static MediaArticleFragment newInstance(Parcelable parcelable) {
        Bundle args = new Bundle();
        MediaArticleFragment fragment = new MediaArticleFragment();
        args.putParcelable(TAG, parcelable);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Bundle arguments = getArguments();
        if (arguments != null) {
            bean = arguments.getParcelable(TAG);
        }
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_media_article, container, false);
        presenter = new MediaArticlePresenter(this);
        setHasOptionsMenu(true);
        initView(view);
        onLoadData();
        return view;
    }

    private void initView(View view) {
        recycler_view = (RecyclerView) view.findViewById(R.id.recycler_view);
        recycler_view.setHasFixedSize(true);
        recycler_view.setLayoutManager(new LinearLayoutManager(getActivity()));

        refresh_layout = (SwipeRefreshLayout) view.findViewById(R.id.refresh_layout);
        // 设置下拉刷新的按钮的颜色
        refresh_layout.setColorSchemeResources(R.color.colorPrimary);
        refresh_layout.setOnRefreshListener(this);

        Toolbar toolbar = (Toolbar) view.findViewById(R.id.toolbar);
        AppCompatActivity activity = (AppCompatActivity) getActivity();
        activity.setSupportActionBar(toolbar);
        ActionBar actionBar = activity.getSupportActionBar();
        if (actionBar != null) {
            actionBar.setDisplayHomeAsUpEnabled(true);
//            actionBar.setTitle(bean.getName());
        }
        toolbar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                recycler_view.smoothScrollToPosition(0);
            }
        });

        // header view
        CollapsingToolbarLayout collapsing_toolbar = (CollapsingToolbarLayout) view.findViewById(R.id.collapsing_toolbar);
        collapsing_toolbar.setTitle(bean.getName());
        collapsing_toolbar.setExpandedTitleColor(getResources().getColor(R.color.NULL));

        CircleImageView cv_avatar = (CircleImageView) view.findViewById(R.id.cv_avatar);
        Glide.with(getActivity()).load(bean.getAvatar()).crossFade().centerCrop().into(cv_avatar);

        TextView tv_title = (TextView) view.findViewById(R.id.tv_title);
        tv_title.setText(bean.getName());
        TextView tv_descText = (TextView) view.findViewById(R.id.tv_descText);
        tv_descText.setText(bean.getDescText());
    }

    @Override
    public void onLoadData() {
        presenter.doLoadData(bean.getId());
    }

    @Override
    public void onSetAdapter(final List<MediaArticleBean.DataBean> list) {
        if (adapter == null) {
            adapter = new MediaArticleAdapter(getActivity());
            adapter.setList(list);
            recycler_view.setAdapter(adapter);
            adapter.setOnItemClickListener(new IOnItemClickListener() {
                @Override
                public void onClick(View view, int position) {
                    presenter.doOnClickItem(list.get(position), bean);
                }
            });
        } else {
//            adapter.notifyItemInserted(list.size());
            List<MediaArticleBean.DataBean> oldList = adapter.getList();
            DiffUtil.DiffResult result = DiffUtil.calculateDiff(new DiffCallback(oldList, list, DiffCallback.MEDIA), true);
            result.dispatchUpdatesTo(adapter);
            adapter.setList(list);
        }

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
        Snackbar.make(refresh_layout, R.string.network_error, Snackbar.LENGTH_SHORT)
                .setAction(R.string.retry, new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        presenter.doLoadData(bean.getId());
                    }
                }).show();
    }

    @Override
    public void onRefresh() {
        presenter.doRefresh();
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        inflater.inflate(R.menu.menu_media, menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                getActivity().onBackPressed();
                break;
            case R.id.action_unfollow_media:
                AlertDialog.Builder dialog = new AlertDialog.Builder(getActivity());
                String mediaName = bean.getName();
                dialog.setMessage(getString(R.string.anymore_unfollow) + " \"" + mediaName + "\" " + getString(R.string.title_media) + "?");
                dialog.setPositiveButton(getString(R.string.anymore_unfollow), new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        canDelete = true;
                        Snackbar.make(recycler_view, getString(R.string.action_unfollow_media_success), Snackbar.LENGTH_LONG)
                                .setAction(getString(R.string.undo), new View.OnClickListener() {
                                    @Override
                                    public void onClick(View v) {
                                        canDelete = false;
                                    }
                                }).show();
                    }
                });
                dialog.setNegativeButton(getString(R.string.cancel), new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                    }
                });
                dialog.setCancelable(true);
                dialog.show();
                break;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onPause() {
        super.onPause();
        if (canDelete) {
            MediaChannelDao dao = new MediaChannelDao();
            dao.delete(bean.getId());
        }
    }

    @Override
    public void onShowNoMore() {
        Snackbar.make(refresh_layout, R.string.no_more, Snackbar.LENGTH_SHORT).show();
    }
}
