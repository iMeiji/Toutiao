package com.meiji.toutiao.module.media.channel;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.meiji.toutiao.R;
import com.meiji.toutiao.adapter.media.MediaChannelAdapter;
import com.meiji.toutiao.bean.media.MediaChannelBean;
import com.meiji.toutiao.database.dao.MediaChannelDao;
import com.meiji.toutiao.interfaces.IOnItemClickListener;
import com.meiji.toutiao.module.media.article.MediaArticleActivity;

import java.util.List;

/**
 * Created by Meiji on 2016/12/24.
 */

public class MediaChannelView extends Fragment implements SwipeRefreshLayout.OnRefreshListener {

    private static final String TAG = "MediaChannelView";
    private static MediaChannelView instance = null;
    private RecyclerView recycler_view;
    private SwipeRefreshLayout refresh_layout;
    private MediaChannelAdapter adapter;
    private MediaChannelDao dao = new MediaChannelDao();
    private TextView tv_desc;
    private String isFirstTime = "isFirstTime";

    public static MediaChannelView getInstance() {
        if (instance == null) {
            instance = new MediaChannelView();
        }
        return instance;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_media, container, false);
        initView(view);
        return view;
    }

    @Override
    public void onResume() {
        super.onResume();
        initData();
    }

    private void initData() {
        SharedPreferences editor = getActivity().getSharedPreferences(TAG, Context.MODE_PRIVATE);
        boolean result = editor.getBoolean(isFirstTime, true);
        if (result) {
            dao.initData();
            editor.edit().putBoolean(isFirstTime, false).apply();
        }

        final List<MediaChannelBean> list = dao.queryAll();
        adapter = new MediaChannelAdapter(list, getActivity());
        recycler_view.setAdapter(adapter);
        adapter.setOnItemClickListener(new IOnItemClickListener() {
            @Override
            public void onClick(View view, int position) {
                MediaArticleActivity.startActivity(list.get(position));
            }
        });
        if (list.size() == 0) {
            tv_desc.setVisibility(View.VISIBLE);
        } else {
            tv_desc.setVisibility(View.GONE);
        }
    }

    private void initView(View view) {
        recycler_view = (RecyclerView) view.findViewById(R.id.recycler_view);
        recycler_view.setHasFixedSize(true);
        recycler_view.setLayoutManager(new LinearLayoutManager(getActivity()));

        refresh_layout = (SwipeRefreshLayout) view.findViewById(R.id.refresh_layout);
        // 设置下拉刷新的按钮的颜色
        refresh_layout.setColorSchemeResources(R.color.colorPrimary);
        refresh_layout.setOnRefreshListener(this);
        tv_desc = (TextView) view.findViewById(R.id.tv_desc);
    }

    @Override
    public void onRefresh() {
        refresh_layout.setRefreshing(true);
        initData();
        refresh_layout.setRefreshing(false);
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        if (instance != null) {
            instance = null;
        }
    }
}
