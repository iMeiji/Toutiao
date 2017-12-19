package com.meiji.toutiao.module.wenda.article;

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
 * Created by Meiji on 2017/5/20.
 */

public class WendaArticleView extends BaseListFragment<IWendaArticle.Presenter> implements IWendaArticle.View {

    public static WendaArticleView newInstance() {
        return new WendaArticleView();
    }

    @Override
    public void setPresenter(IWendaArticle.Presenter presenter) {
        if (null == presenter) {
            this.presenter = new WendaArticlePresenter(this);
        }
    }

    @Override
    protected void initData() {

    }

    @Override
    protected void initView(View view) {
        super.initView(view);
        adapter = new MultiTypeAdapter(oldItems);
        Register.registerWendaArticleItem(adapter);
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
    public void onLoadData() {
        onShowLoading();
        presenter.doLoadData();
    }

    @Override
    public void fetchData() {
        super.fetchData();
        onLoadData();
    }
}
