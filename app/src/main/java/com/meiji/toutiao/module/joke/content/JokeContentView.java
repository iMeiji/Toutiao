package com.meiji.toutiao.module.joke.content;

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
    protected void initView(View view) {
        super.initView(view);
        adapter = new MultiTypeAdapter(oldItems);
        Register.registerJokeContentItem(adapter);
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
    public void fetchData() {
        onLoadData();
    }

    @Override
    public void onLoadData() {
        onShowLoading();
        presenter.doLoadData();
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
    public void setPresenter(IJokeContent.Presenter presenter) {
        if (null == presenter) {
            this.presenter = new JokeContentPresenter(this);
        }
    }
}
