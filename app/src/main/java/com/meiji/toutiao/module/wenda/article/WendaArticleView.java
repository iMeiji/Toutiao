package com.meiji.toutiao.module.wenda.article;

import android.support.design.widget.Snackbar;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.util.DiffUtil;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.meiji.toutiao.R;
import com.meiji.toutiao.adapter.DiffCallback;
import com.meiji.toutiao.adapter.wenda.WendaArticleAdapter;
import com.meiji.toutiao.bean.wenda.WendaArticleDataBean;
import com.meiji.toutiao.interfaces.IOnItemClickListener;
import com.meiji.toutiao.module.base.LazyLoadFragment;
import com.meiji.toutiao.utils.SettingsUtil;

import java.util.List;

/**
 * Created by Meiji on 2017/5/20.
 */

public class WendaArticleView extends LazyLoadFragment<IWendaArticle.Presenter> implements IWendaArticle.View, SwipeRefreshLayout.OnRefreshListener {

    private RecyclerView recycler_view;
    private SwipeRefreshLayout refresh_layout;
    private WendaArticleAdapter adapter;
    private boolean canLoading = false;

    public static WendaArticleView newInstance() {
        return new WendaArticleView();
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
                        presenter.doLoadData();
                    }
                }).show();
    }

    @Override
    public void setPresenter(IWendaArticle.Presenter presenter) {
        if (null == presenter) {
            this.presenter = new WendaArticlePresenter(this);
        }
    }

    @Override
    public void onSetAdapter(List<?> list) {
        List<WendaArticleDataBean> oldList = adapter.getList();
        DiffUtil.DiffResult result = DiffUtil.calculateDiff(new DiffCallback(oldList, list, DiffCallback.WENDA_ARTICLE), true);
        result.dispatchUpdatesTo(adapter);
        adapter.setList((List<WendaArticleDataBean>) list);

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
    public void onLoadData() {
        onShowLoading();
        presenter.doLoadData();
    }

    @Override
    protected int attachLayoutId() {
        return R.layout.fragment_base_main;
    }

    @Override
    protected void initViews(View view) {
        recycler_view = (RecyclerView) view.findViewById(R.id.recycler_view);
        recycler_view.setHasFixedSize(true);
        recycler_view.setLayoutManager(new LinearLayoutManager(getActivity()));

        refresh_layout = (SwipeRefreshLayout) view.findViewById(R.id.refresh_layout);
        refresh_layout.setColorSchemeColors(SettingsUtil.getInstance().getColor());
        refresh_layout.setOnRefreshListener(this);

        adapter = new WendaArticleAdapter(getActivity());
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
    protected void initData() {

    }

    @Override
    public void fetchData() {
        onLoadData();
    }

    @Override
    public void onRefresh() {
        recycler_view.smoothScrollToPosition(0);
        presenter.doRefresh();
    }
}
