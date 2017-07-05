package com.meiji.toutiao.module.wenda.content;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;

import com.meiji.toutiao.R;
import com.meiji.toutiao.Register;
import com.meiji.toutiao.adapter.DiffCallback;
import com.meiji.toutiao.bean.FooterBean;
import com.meiji.toutiao.bean.wenda.WendaContentBean;
import com.meiji.toutiao.module.base.BaseListFragment;
import com.meiji.toutiao.util.OnLoadMoreListener;
import com.meiji.toutiao.util.SettingsUtil;

import java.util.List;

import me.drakeet.multitype.Items;
import me.drakeet.multitype.MultiTypeAdapter;

/**
 * Created by Meiji on 2017/5/22.
 */

public class WendaContentFragment extends BaseListFragment<IWendaContent.Presenter> implements IWendaContent.View {

    private static final String TAG = "WendaContentFragment";
    private String qid;
    private String shareTitle;
    private String shareUrl;
    private WendaContentBean.QuestionBean WendaContentHeaderBean;

    public static WendaContentFragment newInstance(String qid) {
        Bundle args = new Bundle();
        args.putString("qid", qid);
        WendaContentFragment fragment = new WendaContentFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void setPresenter(IWendaContent.Presenter presenter) {
        if (null == presenter) {
            this.presenter = new WendaContentPresenter(this);
        }
    }

    @Override
    public void onSetAdapter(List<?> list) {
        Items newItems = new Items();
        newItems.add(WendaContentHeaderBean);
        newItems.addAll(list);
        newItems.add(new FooterBean());
        DiffCallback.notifyDataSetChanged(oldItems, newItems, DiffCallback.WENDA_CONTENT, adapter);
        oldItems.clear();
        oldItems.addAll(newItems);
        canLoadMore = true;
    }

    @Override
    protected int attachLayoutId() {
        return R.layout.fragment_list_toolbar;
    }

    @Override
    protected void initViews(View view) {
        super.initViews(view);
        Toolbar toolbar = (Toolbar) view.findViewById(R.id.toolbar);
        initToolBar(toolbar, true, getString(R.string.title_wenda));
        toolbar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                recyclerView.smoothScrollToPosition(0);
            }
        });
        toolbar.setBackgroundColor(SettingsUtil.getInstance().getColor());

        adapter = new MultiTypeAdapter(oldItems);
        Register.registerWendaContentItem(adapter);
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
        recyclerView.smoothScrollToPosition(0);
        presenter.doRefresh();
    }

    @Override
    public void onSetHeader(WendaContentBean.QuestionBean questionBean) {
        this.shareTitle = questionBean.getShare_data().getTitle();
        this.shareUrl = questionBean.getShare_data().getShare_url();
        this.WendaContentHeaderBean = questionBean;
    }

//    @Override
//    public void onShowNoMore() {
//        Snackbar.make(swipeRefreshLayout, R.string.no_more_comment, Snackbar.LENGTH_SHORT).show();
//        getActivity().runOnUiThread(new Runnable() {
//            @Override
//            public void run() {
//                if (oldItems.size() > 0) {
//                    Items newItems = new Items(oldItems);
//                    newItems.remove(newItems.size() - 1);
//                    adapter.setItems(newItems);
//                    adapter.notifyDataSetChanged();
//                }
//                canLoadMore = false;
//            }
//        });
//    }

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

    @Override
    public void fetchData() {

    }
}
