package com.meiji.toutiao.module.news.multi;

import android.os.Bundle;
import android.support.v7.util.DiffUtil;
import android.view.View;

import com.meiji.toutiao.Register;
import com.meiji.toutiao.adapter.DiffCallback;
import com.meiji.toutiao.bean.FooterBean;
import com.meiji.toutiao.module.base.BaseListFragment;
import com.meiji.toutiao.utils.OnLoadMoreListener;

import java.util.List;

import me.drakeet.multitype.Items;
import me.drakeet.multitype.MultiTypeAdapter;

/**
 * Created by Meiji on 2017/5/18.
 */

public class MultiNewsArticleView extends BaseListFragment<IMultiNewsArticle.Presenter> implements IMultiNewsArticle.View {

    private static final String TAG = "NewsArticleView";
    private String categoryId;

    public static MultiNewsArticleView newInstance(String categoryId) {
        Bundle bundle = new Bundle();
        bundle.putString("categoryId", categoryId);
        MultiNewsArticleView view = new MultiNewsArticleView();
        view.setArguments(bundle);
        return view;
    }

    @Override
    protected void initData() {
        categoryId = getArguments().getString("categoryId");
    }

    @Override
    protected void initViews(View view) {
        super.initViews(view);
        adapter = new MultiTypeAdapter(oldItems);
        Register.registerNewsItem(adapter);
        recyclerView.setAdapter(adapter);
        recyclerView.addOnScrollListener(new OnLoadMoreListener() {
            @Override
            public void onLoadMore() {
                canLoadMore = false;
                presenter.doLoadMoreData();
            }
        });
    }

    @Override
    public void fetchData() {
        onLoadData();
    }

    @Override
    public void onRefresh() {
        recyclerView.smoothScrollToPosition(0);
        presenter.doRefresh();
    }

    @Override
    public void onLoadData() {
        onShowLoading();
        presenter.doLoadData(categoryId);
    }

    @Override
    public void onSetAdapter(final List<?> list) {
        Items newItems = new Items(list);
        newItems.add(new FooterBean());
        DiffCallback diffCallback = new DiffCallback(oldItems, newItems, DiffCallback.MUlTI_NEWS);
        DiffUtil.DiffResult result = DiffUtil.calculateDiff(diffCallback, true);
        result.dispatchUpdatesTo(adapter);
        oldItems.clear();
        oldItems.addAll(newItems);
        canLoadMore = true;
    }

    @Override
    public void setPresenter(IMultiNewsArticle.Presenter presenter) {
        if (null == presenter) {
            this.presenter = new MultiNewsArticlePresenter(this);
        }
    }
}
