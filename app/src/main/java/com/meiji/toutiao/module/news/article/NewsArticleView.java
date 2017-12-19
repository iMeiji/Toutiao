package com.meiji.toutiao.module.news.article;

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
 * Created by Meiji on 2017/5/18.
 */

public class NewsArticleView extends BaseListFragment<INewsArticle.Presenter> implements INewsArticle.View {

    private static final String TAG = "NewsArticleView";
    private String categoryId;

    public static NewsArticleView newInstance(String categoryId) {
        Bundle bundle = new Bundle();
        bundle.putString(TAG, categoryId);
        NewsArticleView view = new NewsArticleView();
        view.setArguments(bundle);
        return view;
    }

    @Override
    protected void initData() {
        categoryId = getArguments().getString(TAG);
    }

    @Override
    protected void initView(View view) {
        super.initView(view);
        adapter = new MultiTypeAdapter(oldItems);
        Register.registerNewsArticleItem(adapter);
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
        super.fetchData();
        onLoadData();
    }

    @Override
    public void onLoadData() {
        onShowLoading();
        presenter.doLoadData(categoryId);
    }

    @Override
    public void onSetAdapter(final List<?> list) {
        Items newItems = new Items(list);
        newItems.add(new LoadingBean());
        DiffCallback.create(oldItems, newItems, adapter);
        oldItems.clear();
        oldItems.addAll(newItems);
        canLoadMore = true;
        /**
         * https://medium.com/@hanru.yeh/recyclerview-and-appbarlayout-behavior-changed-in-v26-0-x-d9eb4de78fc0
         * support libraries v26 增加了 RV 惯性滑动，当 root layout 使用了 AppBarLayout Behavior 就会自动生效
         * 因此需要手动停止滑动
         */
        recyclerView.stopScroll();
    }

    @Override
    public void setPresenter(INewsArticle.Presenter presenter) {
        if (null == presenter) {
            this.presenter = new NewsArticlePresenter(this);
        }
    }
}
