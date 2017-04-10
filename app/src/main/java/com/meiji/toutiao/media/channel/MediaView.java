package com.meiji.toutiao.media.channel;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.meiji.toutiao.R;
import com.meiji.toutiao.adapter.media.MediaChannelAdapter;
import com.meiji.toutiao.bean.media.MediaChannelBean;
import com.meiji.toutiao.database.dao.MediaChannelDao;

import java.util.List;

/**
 * Created by Meiji on 2016/12/24.
 */

public class MediaView extends Fragment implements SwipeRefreshLayout.OnRefreshListener {

    private static MediaView instance = null;
    private RecyclerView recycler_view;
    private SwipeRefreshLayout refresh_layout;
    private MediaChannelAdapter adapter;

    public static MediaView getInstance() {
        if (instance == null) {
            instance = new MediaView();
        }
        return instance;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_base_main, container, false);
        initView(view);
        initData();
        return view;
    }

    private void initData() {
        if (adapter == null) {
            MediaChannelDao dao = new MediaChannelDao();
            List<MediaChannelBean> list = dao.queryAll();
            adapter = new MediaChannelAdapter(list, getActivity());
            recycler_view.setAdapter(adapter);
        }
    }

    private void initView(View view) {
        recycler_view = (RecyclerView) view.findViewById(R.id.recycler_view);
        recycler_view.setHasFixedSize(true);
        recycler_view.setLayoutManager(new LinearLayoutManager(getActivity()));

        refresh_layout = (SwipeRefreshLayout) view.findViewById(R.id.refresh_layout);
        // 设置下拉刷新的按钮的颜色
        refresh_layout.setOnRefreshListener(this);
    }

    @Override
    public void onRefresh() {

    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        if (instance != null) {
            instance = null;
        }
    }
}
