package com.meiji.toutiao.module.news.article;

import android.os.Bundle;
import android.support.design.widget.Snackbar;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.util.DiffUtil;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.meiji.toutiao.R;
import com.meiji.toutiao.adapter.DiffCallback;
import com.meiji.toutiao.adapter.news.NewsArticleAdapter;
import com.meiji.toutiao.bean.news.NewsArticleBean;
import com.meiji.toutiao.interfaces.IOnItemClickListener;
import com.meiji.toutiao.module.base.LazyLoadFragment;

import java.util.List;

/**
 * Created by Meiji on 2016/12/12.
 */

public class NewsArticleView extends LazyLoadFragment<INewsArticle.Presenter> implements SwipeRefreshLayout.OnRefreshListener, INewsArticle.View {

    private static final String TAG = "NewsArticleView";
    private RecyclerView recycler_view;
    private SwipeRefreshLayout refresh_layout;
    private NewsArticleAdapter adapter;
    private String categoryId;
    private boolean canLoading = false;

    public static NewsArticleView newInstance(String categoryId) {
        Bundle bundle = new Bundle();
        bundle.putString("categoryId", categoryId);
        NewsArticleView newsArticleView = new NewsArticleView();
        newsArticleView.setArguments(bundle);
        return newsArticleView;
    }

    @Override
    protected int attachLayoutId() {
        return R.layout.fragment_base_main;
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
        // 设置下拉刷新的按钮的颜色
        refresh_layout.setColorSchemeResources(R.color.colorPrimary);
        refresh_layout.setOnRefreshListener(this);

        adapter = new NewsArticleAdapter(getActivity());
        recycler_view.setAdapter(adapter);
        adapter.setOnItemClickListener(new IOnItemClickListener() {
            @Override
            public void onClick(View view, int position) {
                presenter.doOnClickItem(position);
            }
        });
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
    public void onSetAdapter(final List<NewsArticleBean.DataBean> list) {
        List<NewsArticleBean.DataBean> oldList = adapter.getList();
        DiffUtil.DiffResult result = DiffUtil.calculateDiff(new DiffCallback(oldList, list, DiffCallback.NEWs), true);
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
//                LinearLayoutManager lm = (LinearLayoutManager) recyclerView.getLayoutManager();
//                int totalItemCount = recyclerView.getAdapter().getItemCount();
//                int lastVisibleItemPosition = lm.findLastVisibleItemPosition();
//                int visibleItemCount = recyclerView.getChildCount();
////                 添加预加载 滚动快到底部时候自动加载
//                if (lastVisibleItemPosition + 4 >= totalItemCount - 1 && visibleItemCount > 0) {
//                    if (canLoading) {
//                        presenter.doLoadMoreData();
//                        canLoading = false;
//                    }
//                }
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
                        presenter.doLoadData(categoryId);
                    }
                }).show();
    }

    @Override
    public void setPresenter(INewsArticle.Presenter presenter) {
        if (null == presenter) {
            this.presenter = new NewsArticlePresenter(this);
        }
    }
}
