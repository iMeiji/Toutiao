package com.meiji.toutiao.module.wenda.content;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.Snackbar;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.util.DiffUtil;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;

import com.meiji.toutiao.R;
import com.meiji.toutiao.adapter.DiffCallback;
import com.meiji.toutiao.adapter.wenda.WendaContentAdapter;
import com.meiji.toutiao.bean.wenda.WendaContentBean;
import com.meiji.toutiao.interfaces.IOnItemClickListener;
import com.meiji.toutiao.module.base.BaseFragment;
import com.meiji.toutiao.utils.SettingsUtil;

import java.util.List;

/**
 * Created by Meiji on 2017/5/22.
 */

public class WendaContentFragment extends BaseFragment<IWendaContent.Presenter> implements IWendaContent.View, SwipeRefreshLayout.OnRefreshListener {

    private static final String TAG = "WendaContentFragment";
    private RecyclerView recycler_view;
    private SwipeRefreshLayout refresh_layout;
    private WendaContentAdapter adapter;
    private boolean canLoading = false;
    private String qid;
    private String shareTitle;
    private String shareUrl;

    public static WendaContentFragment newInstance(String qid) {
        Bundle args = new Bundle();
        args.putString("qid", qid);
        WendaContentFragment fragment = new WendaContentFragment();
        fragment.setArguments(args);
        return fragment;
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
                        presenter.doLoadMoreData();
                    }
                }).show();
    }

    @Override
    public void setPresenter(IWendaContent.Presenter presenter) {
        if (null == presenter) {
            this.presenter = new WendaContentPresenter(this);
        }
    }

    @Override
    public void onSetAdapter(List<?> list) {
        List<WendaContentBean.AnsListBean> oldList = adapter.getList();
        DiffUtil.DiffResult result = DiffUtil.calculateDiff(new DiffCallback(oldList, list, DiffCallback.WENDA_CONTENT), true);
        result.dispatchUpdatesTo(adapter);
        adapter.setList((List<WendaContentBean.AnsListBean>) list);

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
    protected int attachLayoutId() {
        return R.layout.fragment_wenda_content;
    }

    @Override
    protected void initViews(View view) {
        Toolbar toolbar = (Toolbar) view.findViewById(R.id.toolbar);
        initToolBar(toolbar, true, "问答");
        recycler_view = (RecyclerView) view.findViewById(R.id.recycler_view);
        recycler_view.setHasFixedSize(true);
        recycler_view.setLayoutManager(new LinearLayoutManager(getActivity()));
        toolbar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                recycler_view.smoothScrollToPosition(0);
            }
        });
        refresh_layout = (SwipeRefreshLayout) view.findViewById(R.id.refresh_layout);
        refresh_layout.setColorSchemeColors(SettingsUtil.getInstance().getColor());
        refresh_layout.setOnRefreshListener(this);

        adapter = new WendaContentAdapter(getActivity());
        recycler_view.setAdapter(adapter);
        adapter.setOnItemClickListener(new IOnItemClickListener() {
            @Override
            public void onClick(View view, int position) {
                presenter.doOnClickItem(position);
            }
        });

        setHasOptionsMenu(true);
    }

    @Override
    public void onResume() {
        super.onResume();
        refresh_layout.setColorSchemeColors(SettingsUtil.getInstance().getColor());
    }

    @Override
    protected void initData() {
        this.qid = getArguments().getString("qid");
        onLoadData();
    }

    @Override
    public void onLoadData() {
        onShowLoading();
        presenter.doLoadData(qid);
    }

    @Override
    public void onRefresh() {
        recycler_view.smoothScrollToPosition(0);
        presenter.doRefresh();
    }

    @Override
    public void onSetHeader(WendaContentBean.QuestionBean questionBean) {
        this.shareTitle = questionBean.getShare_data().getTitle();
        this.shareUrl = questionBean.getShare_data().getShare_url();
        adapter.setQuestionBean(questionBean);
    }

    @Override
    public void onShowNoMore() {
        Snackbar.make(refresh_layout, R.string.no_more, Snackbar.LENGTH_INDEFINITE).show();
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        super.onCreateOptionsMenu(menu, inflater);
        inflater.inflate(R.menu.menu_wenda_content, menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == R.id.action_wenda_share) {
            Intent shareIntent = new Intent()
                    .setAction(Intent.ACTION_SEND)
                    .setType("text/plain")
                    .putExtra(Intent.EXTRA_TEXT, shareTitle + "\n" + shareUrl);
            startActivity(Intent.createChooser(shareIntent, getString(R.string.share_to)));
        }
        return super.onOptionsItemSelected(item);
    }
}
