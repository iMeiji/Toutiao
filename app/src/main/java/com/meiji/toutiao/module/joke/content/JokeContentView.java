package com.meiji.toutiao.module.joke.content;

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
 * Created by Meiji on 2016/12/28.
 */

public class JokeContentView extends BaseListFragment<IJokeContent.Presenter> implements IJokeContent.View {

    public static JokeContentView newInstance() {
        return new JokeContentView();
    }

    @Override
    protected void initData() {

    }

    @Override
    protected void initViews(View view) {
        super.initViews(view);
        adapter = new MultiTypeAdapter(oldItems);
        Register.registerJokeContentItem(adapter);
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
        presenter.doRefresh();
    }

    @Override
    public void onLoadData() {
        onShowLoading();
        presenter.doLoadData();
    }

    @Override
    public void onSetAdapter(List<?> list) {
        Items newItems = new Items(list);
        newItems.add(new FooterBean());
        DiffCallback diffCallback = new DiffCallback(oldItems, newItems, DiffCallback.JOKE);
        DiffUtil.DiffResult result = DiffUtil.calculateDiff(diffCallback, true);
        result.dispatchUpdatesTo(adapter);
        oldItems.clear();
        oldItems.addAll(newItems);
        canLoadMore = true;
    }

    @Override
    public void setPresenter(IJokeContent.Presenter presenter) {
        if (null == presenter) {
            this.presenter = new JokeContentPresenter(this);
        }
    }
}
