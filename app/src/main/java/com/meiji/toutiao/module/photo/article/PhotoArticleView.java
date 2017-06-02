package com.meiji.toutiao.module.photo.article;

import android.os.Bundle;
import android.support.design.widget.Snackbar;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.util.DiffUtil;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.meiji.toutiao.R;
import com.meiji.toutiao.adapter.DiffCallback;
import com.meiji.toutiao.adapter.photo.PhotoArticleAdapter;
import com.meiji.toutiao.bean.photo.PhotoArticleBean;
import com.meiji.toutiao.interfaces.IOnItemClickListener;
import com.meiji.toutiao.module.base.LazyLoadFragment;
import com.meiji.toutiao.utils.SettingsUtil;

import java.util.List;

public class PhotoArticleView extends LazyLoadFragment<IPhotoArticle.Presenter> implements IPhotoArticle.View, SwipeRefreshLayout.OnRefreshListener {

    private static final String TAG = "PhotoArticleView";
    private RecyclerView recycler_view;
    private SwipeRefreshLayout refresh_layout;
    private PhotoArticleAdapter adapter;
    private String categoryId;
    private boolean canLoading = false;

    public static PhotoArticleView newInstance(String categoryId) {
        Bundle bundle = new Bundle();
        bundle.putString("categoryId", categoryId);
        PhotoArticleView instance = new PhotoArticleView();
        instance.setArguments(bundle);
        return instance;
    }

    @Override
    protected int attachLayoutId() {
        return R.layout.fragment_list;
    }

    @Override
    protected void initData() {
        categoryId = getArguments().getString("categoryId");
    }

    @Override
    protected void initViews(View view) {
        recycler_view = (RecyclerView) view.findViewById(R.id.recycler_view);
        recycler_view.setHasFixedSize(true);
        recycler_view.setLayoutManager(new LinearLayoutManager(getActivity()));

        refresh_layout = (SwipeRefreshLayout) view.findViewById(R.id.refresh_layout);
        refresh_layout.setColorSchemeColors(SettingsUtil.getInstance().getColor());
        refresh_layout.setOnRefreshListener(this);

        adapter = new PhotoArticleAdapter(getActivity());
        recycler_view.setAdapter(adapter);
        adapter.setOnItemClickListener(new IOnItemClickListener() {
            @Override
            public void onClick(View view, int position) {
                presenter.doOnClickItem(position);
            }
        });
    }

    @Override
    public void onResume() {
        super.onResume();
        refresh_layout.setColorSchemeColors(SettingsUtil.getInstance().getColor());
    }

    @Override
    public void fetchData() {
        onLoadData();
    }

    @Override
    public void onRefresh() {
        presenter.doRefresh();
    }

    @Override
    public void onLoadData() {
        onShowLoading();
        presenter.doLoadData(categoryId);
    }

    @Override
    public void onSetAdapter(final List<?> list) {
        List<PhotoArticleBean.DataBean> oldList = adapter.getList();
        DiffUtil.DiffResult result = DiffUtil.calculateDiff(new DiffCallback(oldList, list, DiffCallback.PHOTO), true);
        result.dispatchUpdatesTo(adapter);
        adapter.setList((List<PhotoArticleBean.DataBean>) list);

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
        Snackbar.make(refresh_layout, R.string.network_error, Snackbar.LENGTH_SHORT).show();
    }

    @Override
    public void setPresenter(IPhotoArticle.Presenter presenter) {
        if (null == presenter) {
            this.presenter = new PhotoArticlePresenter(this);
        }
    }
}
