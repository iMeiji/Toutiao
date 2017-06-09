package com.meiji.toutiao.module.news.comment;

import android.os.Bundle;
import android.support.design.widget.Snackbar;
import android.support.v7.util.DiffUtil;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;

import com.meiji.toutiao.R;
import com.meiji.toutiao.Register;
import com.meiji.toutiao.adapter.DiffCallback;
import com.meiji.toutiao.bean.FooterBean;
import com.meiji.toutiao.module.base.BaseListFragment;
import com.meiji.toutiao.utils.OnLoadMoreListener;
import com.meiji.toutiao.utils.SettingsUtil;

import java.util.List;

import me.drakeet.multitype.Items;
import me.drakeet.multitype.MultiTypeAdapter;

/**
 * Created by Meiji on 2017/2/28.
 */

public class NewsCommentFragment extends BaseListFragment<INewsComment.Presenter> implements INewsComment.View {

    private static final String GROUP_ID = "groupId";
    private static final String ITEM_ID = "itemId";
    private static final String TAG = "NewsCommentFragment";
    private String groupId;
    private String itemId;

    public static NewsCommentFragment newInstance(String groupId, String itemId) {
        NewsCommentFragment instance = new NewsCommentFragment();
        Bundle bundle = new Bundle();
        bundle.putString(GROUP_ID, groupId);
        bundle.putString(ITEM_ID, itemId);
        instance.setArguments(bundle);
        return instance;
    }

    @Override
    protected int attachLayoutId() {
        return R.layout.fragment_list_toolbar;
    }

    @Override
    protected void initData() {
        Bundle arguments = getArguments();
        groupId = arguments.getString(GROUP_ID);
        itemId = arguments.getString(ITEM_ID);
        onLoadData();
    }

    @Override
    protected void initViews(View view) {
        super.initViews(view);
        Toolbar toolbar = (Toolbar) view.findViewById(R.id.toolbar);
        initToolBar(toolbar, true, getString(R.string.title_comment));
        toolbar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                recyclerView.smoothScrollToPosition(0);
            }
        });
        toolbar.setBackgroundColor(SettingsUtil.getInstance().getColor());

        adapter = new MultiTypeAdapter(oldItems);
        Register.registerNewsCommentItem(adapter);
        recyclerView.setAdapter(adapter);
        recyclerView.addOnScrollListener(new OnLoadMoreListener() {
            @Override
            public void onLoadMore() {
                canLoadMore = false;
                presenter.doLoadMoreData();
            }
        });
        setHasOptionsMenu(true);
    }

    @Override
    public void onRefresh() {
        presenter.doRefresh();
    }

    @Override
    public void onLoadData() {
        presenter.doLoadData(groupId, itemId);
    }

    @Override
    public void onSetAdapter(final List<?> list) {
        Items newItems = new Items(list);
        newItems.add(new FooterBean());
        DiffCallback diffCallback = new DiffCallback(oldItems, newItems, DiffCallback.NEWS_COMMENT);
        DiffUtil.DiffResult result = DiffUtil.calculateDiff(diffCallback, true);
        result.dispatchUpdatesTo(adapter);
        oldItems.clear();
        oldItems.addAll(newItems);
        canLoadMore = true;
    }

    @Override
    public void setPresenter(INewsComment.Presenter presenter) {
        if (null == presenter) {
            this.presenter = new NewsCommentPresenter(this);
        }
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if (id == android.R.id.home) {
            getActivity().onBackPressed();
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onShowNoMore() {
        Snackbar.make(swipeRefreshLayout, R.string.no_more_comment, Snackbar.LENGTH_INDEFINITE).show();
    }

    @Override
    public void fetchData() {

    }
}
