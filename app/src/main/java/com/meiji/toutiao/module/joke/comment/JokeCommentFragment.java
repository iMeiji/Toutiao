package com.meiji.toutiao.module.joke.comment;

import android.content.Intent;
import android.os.Bundle;
import android.os.Parcelable;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;

import com.meiji.toutiao.R;
import com.meiji.toutiao.Register;
import com.meiji.toutiao.bean.LoadingBean;
import com.meiji.toutiao.bean.joke.JokeContentBean;
import com.meiji.toutiao.module.base.BaseListFragment;
import com.meiji.toutiao.util.DiffCallback;
import com.meiji.toutiao.util.OnLoadMoreListener;

import java.util.List;

import me.drakeet.multitype.Items;
import me.drakeet.multitype.MultiTypeAdapter;

/**
 * Created by Meiji on 2017/5/11.
 */

public class JokeCommentFragment extends BaseListFragment<IJokeComment.Presenter> implements IJokeComment.View {

    public static final String TAG = "JokeCommentFragment";
    private String jokeId;
    private String jokeCommentCount;
    private String jokeText;
    private JokeContentBean.DataBean.GroupBean jokeCommentHeaderBean;

    public static JokeCommentFragment newInstance(Parcelable data) {
        Bundle args = new Bundle();
        args.putParcelable(TAG, data);
        JokeCommentFragment fragment = new JokeCommentFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    protected void initView(View view) {
        super.initView(view);
        Toolbar toolbar = view.findViewById(R.id.toolbar);
        initToolBar(toolbar, true, "");
        toolbar.setOnClickListener(view1 -> recyclerView.smoothScrollToPosition(0));

        adapter = new MultiTypeAdapter(oldItems);
        Register.registerJokeCommentItem(adapter);
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
        setHasOptionsMenu(true);
    }

    @Override
    protected int attachLayoutId() {
        return R.layout.fragment_list_toolbar;
    }

    @Override
    protected void initData() {
        Bundle bundle = getArguments();
        try {
            jokeCommentHeaderBean = bundle.getParcelable(TAG);
            jokeId = jokeCommentHeaderBean.getId() + "";
            jokeCommentCount = jokeCommentHeaderBean.getComment_count() + "";
            jokeText = jokeCommentHeaderBean.getText();
            oldItems.add(jokeCommentHeaderBean);
        } catch (Exception e) {

        }
        onLoadData();
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        inflater.inflate(R.menu.menu_joke_comment, menu);
        super.onCreateOptionsMenu(menu, inflater);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.action_comment_share:
                Intent shareIntent = new Intent()
                        .setAction(Intent.ACTION_SEND)
                        .setType("text/plain")
                        .putExtra(Intent.EXTRA_TEXT, jokeText);
                startActivity(Intent.createChooser(shareIntent, getString(R.string.share_to)));
                break;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onRefresh() {
        presenter.doRefresh();
    }

    @Override
    public void onLoadData() {
        presenter.doLoadData(jokeId, jokeCommentCount);
    }

    @Override
    public void onSetAdapter(final List<?> list) {
        Items newItems = new Items();
        newItems.add(jokeCommentHeaderBean);
        newItems.addAll(list);
        newItems.add(new LoadingBean());
        DiffCallback.create(oldItems, newItems, adapter);
        oldItems.clear();
        oldItems.addAll(newItems);
        canLoadMore = true;
        recyclerView.stopScroll();
    }

    @Override
    public void setPresenter(IJokeComment.Presenter presenter) {
        if (null == presenter) {
            this.presenter = new JokeCommentPresenter(this);
        }
    }

    @Override
    public void fetchData() {

    }
}