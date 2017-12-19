package com.meiji.toutiao.module.search.result;

import android.os.Bundle;
import android.view.View;

import com.meiji.toutiao.Register;
import com.meiji.toutiao.bean.LoadingBean;
import com.meiji.toutiao.module.base.BaseListFragment;
import com.meiji.toutiao.util.DiffCallback;
import com.meiji.toutiao.util.OnLoadMoreListener;

import java.util.List;

import me.drakeet.multitype.Items;
import me.drakeet.multitype.MultiTypeAdapter;

/**
 * Created by Meiji on 2017/6/13.
 */

public class SearchResultFragment extends BaseListFragment<ISearchResult.Presenter> implements ISearchResult.View {

    private String query;
    private String curTab;

    public static SearchResultFragment newInstance(String query, String curTab) {
        Bundle args = new Bundle();
        args.putString("queryAll", query);
        args.putString("curTab", curTab);
        SearchResultFragment fragment = new SearchResultFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    protected void initData() {
        query = getArguments().getString("queryAll");
        curTab = getArguments().getString("curTab");
    }

    @Override
    public void onRefresh() {
        recyclerView.smoothScrollToPosition(0);
        presenter.doRefresh();
    }

    @Override
    public void setPresenter(ISearchResult.Presenter presenter) {
        if (presenter == null) {
            this.presenter = new SearchResultPresenter(this);
        }
    }

    @Override
    protected void initView(View view) {
        super.initView(view);
        adapter = new MultiTypeAdapter(oldItems);
        Register.registerSearchItem(adapter);
        recyclerView.setAdapter(adapter);
        recyclerView.addOnScrollListener(new OnLoadMoreListener() {
            @Override
            public void onLoadMore() {
                if (canLoadMore) {
                    canLoadMore = false;
                    presenter.doLoadMoreData();
                }
            }
        });
    }

    @Override
    public void onSetAdapter(List<?> list) {
        Items newItems = new Items(list);
        newItems.add(new LoadingBean());
        DiffCallback.create(oldItems, newItems, adapter);
        oldItems.clear();
        oldItems.addAll(newItems);
        canLoadMore = true;
        recyclerView.stopScroll();
    }

    @Override
    public void fetchData() {
        onLoadData();
    }

    @Override
    public void onLoadData() {
        onShowLoading();
        presenter.doLoadData(query, curTab);
    }
}
