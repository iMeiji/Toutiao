package com.meiji.toutiao.module.media.article;

import android.content.DialogInterface;
import android.os.Bundle;
import android.os.Parcelable;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AlertDialog;
import android.support.v7.util.DiffUtil;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;

import com.meiji.toutiao.R;
import com.meiji.toutiao.adapter.DiffCallback;
import com.meiji.toutiao.adapter.media.MediaArticleAdapter;
import com.meiji.toutiao.bean.media.MediaArticleBean;
import com.meiji.toutiao.bean.media.MediaChannelBean;
import com.meiji.toutiao.database.dao.MediaChannelDao;
import com.meiji.toutiao.interfaces.IOnItemClickListener;
import com.meiji.toutiao.module.base.BaseListFragment;
import com.meiji.toutiao.utils.ImageLoader;
import com.meiji.toutiao.utils.OnLoadMoreListener;
import com.meiji.toutiao.widget.CircleImageView;

import java.util.List;

/**
 * Created by Meiji on 2017/4/11.
 */

public class MediaArticleFragment extends BaseListFragment<IMediaArticle.Presenter> implements IMediaArticle.View {

    private static final String TAG = "MediaArticleFragment";
    private CollapsingToolbarLayout collapsing_toolbar;
    private TextView tv_title;
    private TextView tv_descText;
    private CircleImageView cv_avatar;
    private MediaArticleAdapter adapter;
    private MediaChannelBean bean;
    private boolean canDelete = false;

    public static MediaArticleFragment newInstance(Parcelable parcelable) {
        Bundle args = new Bundle();
        MediaArticleFragment fragment = new MediaArticleFragment();
        args.putParcelable(TAG, parcelable);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    protected int attachLayoutId() {
        return R.layout.fragment_media_article;
    }

    @Override
    protected void initData() {
        bean = getArguments().getParcelable(TAG);
        ImageLoader.loadCenterCrop(getContext(), bean.getAvatar(), cv_avatar, R.color.viewBackground);
        collapsing_toolbar.setTitle(bean.getName());
        tv_title.setText(bean.getName());
        tv_descText.setText(bean.getDescText());

        onLoadData();
    }

    @Override
    protected void initViews(View view) {
        super.initViews(view);
        Toolbar toolbar = (Toolbar) view.findViewById(R.id.toolbar);
        initToolBar(toolbar, true, "");
        toolbar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                recyclerView.smoothScrollToPosition(0);
            }
        });

        // header view
        collapsing_toolbar = (CollapsingToolbarLayout) view.findViewById(R.id.collapsing_toolbar);
        collapsing_toolbar.setExpandedTitleColor(getResources().getColor(R.color.NULL));
        cv_avatar = (CircleImageView) view.findViewById(R.id.cv_avatar);
        tv_title = (TextView) view.findViewById(R.id.tv_title);
        tv_descText = (TextView) view.findViewById(R.id.tv_descText);

        adapter = new MediaArticleAdapter(getActivity());
        recyclerView.setAdapter(adapter);
        adapter.setOnItemClickListener(new IOnItemClickListener() {
            @Override
            public void onClick(View view, int position) {
                presenter.doOnClickItem(position, bean);
            }
        });
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
    public void onLoadData() {
        presenter.doLoadData(bean.getId());
    }

    @Override
    public void onSetAdapter(final List<?> list) {
        List<MediaArticleBean.DataBean> oldList = adapter.getList();
        DiffCallback diffCallback = new DiffCallback(oldList, list, DiffCallback.MEDIA);
        DiffUtil.DiffResult result = DiffUtil.calculateDiff(diffCallback, true);
        result.dispatchUpdatesTo(adapter);
        adapter.setList((List<MediaArticleBean.DataBean>) list);
        canLoadMore = true;
    }

    @Override
    public void onShowNetError() {
        Snackbar.make(swipeRefreshLayout, R.string.network_error, Snackbar.LENGTH_LONG)
                .setAction(R.string.retry, new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        presenter.doLoadData(bean.getId());
                    }
                }).show();
    }

    @Override
    public void setPresenter(IMediaArticle.Presenter presenter) {
        if (null == presenter) {
            this.presenter = new MediaArticlePresenter(this);
        }
    }

    @Override
    public void onRefresh() {
        presenter.doRefresh();
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        inflater.inflate(R.menu.menu_media, menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                getActivity().onBackPressed();
                break;
            case R.id.action_unfollow_media:
                AlertDialog.Builder dialog = new AlertDialog.Builder(getActivity());
                String mediaName = bean.getName();
                dialog.setMessage(getString(R.string.anymore_unfollow) + " \"" + mediaName + "\" " + getString(R.string.title_media) + "?");
                dialog.setPositiveButton(getString(R.string.anymore_unfollow), new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        canDelete = true;
                        Snackbar.make(swipeRefreshLayout, getString(R.string.action_unfollow_media_success), Snackbar.LENGTH_LONG)
                                .setAction(getString(R.string.undo), new View.OnClickListener() {
                                    @Override
                                    public void onClick(View v) {
                                        canDelete = false;
                                    }
                                }).show();
                    }
                });
                dialog.setNegativeButton(getString(R.string.cancel), new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                    }
                });
                dialog.setCancelable(true);
                dialog.show();
                break;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onPause() {
        super.onPause();
        if (canDelete) {
            MediaChannelDao dao = new MediaChannelDao();
            dao.delete(bean.getId());
        }
    }

    @Override
    public void onShowNoMore() {
        Snackbar.make(swipeRefreshLayout, R.string.no_more_content, Snackbar.LENGTH_SHORT).show();
        getActivity().runOnUiThread(new Runnable() {
            @Override
            public void run() {
                if (oldItems.size() > 1) {
                    List<MediaArticleBean.DataBean> oldList = adapter.getList();
                    oldList.remove(oldList.size() - 2);
                    adapter.setList(oldList);
                    adapter.notifyDataSetChanged();
                }
                canLoadMore = false;
            }
        });
    }

    @Override
    public void fetchData() {

    }
}
