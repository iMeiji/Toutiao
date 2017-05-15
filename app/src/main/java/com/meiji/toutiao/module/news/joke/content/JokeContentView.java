package com.meiji.toutiao.module.news.joke.content;

import android.support.design.widget.Snackbar;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.util.DiffUtil;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.meiji.toutiao.R;
import com.meiji.toutiao.adapter.DiffCallback;
import com.meiji.toutiao.adapter.news.joke.JokeContentAdapter;
import com.meiji.toutiao.bean.news.joke.JokeContentBean;
import com.meiji.toutiao.interfaces.IOnItemClickListener;
import com.meiji.toutiao.module.base.LazyLoadFragment;
import com.meiji.toutiao.utils.SettingsUtil;

import java.util.List;

/**
 * Created by Meiji on 2016/12/28.
 */

public class JokeContentView extends LazyLoadFragment<IJokeContent.Presenter> implements SwipeRefreshLayout.OnRefreshListener, IJokeContent.View {

    private static final String TAG = "JokeContentView";
    private RecyclerView recycler_view;
    private SwipeRefreshLayout refresh_layout;
    private JokeContentAdapter adapter;
    private boolean canLoading;

    public static JokeContentView newInstance() {
        return new JokeContentView();
    }

    @Override
    protected int attachLayoutId() {
        return R.layout.fragment_base_main;
    }

    @Override
    protected void initData() {

    }

    @Override
    protected void initViews(View view) {
        recycler_view = (RecyclerView) view.findViewById(R.id.recycler_view);
        recycler_view.setHasFixedSize(true);
        recycler_view.setLayoutManager(new LinearLayoutManager(getActivity()));

        refresh_layout = (SwipeRefreshLayout) view.findViewById(R.id.refresh_layout);
        refresh_layout.setOnRefreshListener(this);

        adapter = new JokeContentAdapter(getActivity());
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
        // 设置下拉刷新的按钮的颜色
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
        presenter.doLoadData();
    }

    @Override
    public void onSetAdapter(List<JokeContentBean.DataBean.GroupBean> list) {
        List<JokeContentBean.DataBean.GroupBean> oldList = adapter.getList();
        DiffUtil.DiffResult result = DiffUtil.calculateDiff(new DiffCallback(oldList, list, DiffCallback.JOKE), true);
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
    public void setPresenter(IJokeContent.Presenter presenter) {
        if (null == presenter) {
            this.presenter = new JokeContentPresenter(this);
        }
    }
}
