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
import com.meiji.toutiao.Register;
import com.meiji.toutiao.bean.media.MediaChannelBean;
import com.meiji.toutiao.database.dao.MediaChannelDao;
import com.meiji.toutiao.util.SettingsUtil;

import java.util.List;

import me.drakeet.multitype.MultiTypeAdapter;

/**
 * Created by Meiji on 2016/12/24.
 */

public class MediaChannelView extends Fragment implements SwipeRefreshLayout.OnRefreshListener {

    private static final String TAG = "MediaChannelView";
    private static MediaChannelView instance = null;
    private RecyclerView recyclerView;
    private SwipeRefreshLayout swipeRefreshLayout;
    private MultiTypeAdapter adapter;
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
        initData();
        return view;
    }

    @Override
    public void onResume() {
        super.onResume();
        swipeRefreshLayout.setColorSchemeColors(SettingsUtil.getInstance().getColor());
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
        adapter = new MultiTypeAdapter(list);
        Register.registerMediaChannelItem(adapter);
        recyclerView.setAdapter(adapter);
        if (list.size() == 0) {
            tv_desc.setVisibility(View.VISIBLE);
        } else {
            tv_desc.setVisibility(View.GONE);
        }
    }

    private void initView(View view) {
        recyclerView = (RecyclerView) view.findViewById(R.id.recycler_view);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));

        swipeRefreshLayout = (SwipeRefreshLayout) view.findViewById(R.id.refresh_layout);
        swipeRefreshLayout.setColorSchemeColors(SettingsUtil.getInstance().getColor());
        swipeRefreshLayout.setOnRefreshListener(this);
        tv_desc = (TextView) view.findViewById(R.id.tv_desc);
    }

    @Override
    public void onRefresh() {
        swipeRefreshLayout.setRefreshing(true);
        initData();
        swipeRefreshLayout.setRefreshing(false);
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        if (instance != null) {
            instance = null;
        }
    }
}
