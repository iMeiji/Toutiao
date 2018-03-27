package com.meiji.toutiao.module.media.channel;

import android.arch.lifecycle.Lifecycle;
import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AlertDialog;
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
import com.meiji.toutiao.interfaces.IOnItemLongClickListener;
import com.meiji.toutiao.util.SettingUtil;
import com.uber.autodispose.AutoDispose;
import com.uber.autodispose.android.lifecycle.AndroidLifecycleScopeProvider;

import java.util.List;

import io.reactivex.Observable;
import io.reactivex.ObservableOnSubscribe;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;
import me.drakeet.multitype.MultiTypeAdapter;

import static com.meiji.toutiao.R.id.recycler_view;

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
    private List<MediaChannelBean> list;

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
        swipeRefreshLayout.setColorSchemeColors(SettingUtil.getInstance().getColor());
        setAdapter();
    }

    private void initData() {
        SharedPreferences editor = getActivity().getSharedPreferences(TAG, Context.MODE_PRIVATE);
        boolean result = editor.getBoolean(isFirstTime, true);
        if (result) {
            dao.initData();
            editor.edit().putBoolean(isFirstTime, false).apply();
        }
        setAdapter();
    }

    private void setAdapter() {
        Observable
                .create((ObservableOnSubscribe<List<MediaChannelBean>>) e -> {
                    list = dao.queryAll();
                    e.onNext(list);
                })
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .as(AutoDispose.autoDisposable(AndroidLifecycleScopeProvider
                        .from(this, Lifecycle.Event.ON_DESTROY)))
                .subscribe(list -> {
                    adapter.setItems(list);
                    adapter.notifyDataSetChanged();
                    if (list.size() == 0) {
                        tv_desc.setVisibility(View.VISIBLE);
                    } else {
                        tv_desc.setVisibility(View.GONE);
                    }
                });
    }

    private void initView(View view) {
        recyclerView = view.findViewById(recycler_view);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));

        swipeRefreshLayout = view.findViewById(R.id.refresh_layout);
        swipeRefreshLayout.setColorSchemeColors(SettingUtil.getInstance().getColor());
        swipeRefreshLayout.setOnRefreshListener(this);
        tv_desc = view.findViewById(R.id.tv_desc);

        IOnItemLongClickListener listener = (view1, position) -> {
            final MediaChannelBean item = list.get(position);
            AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
            builder.setMessage("取消订阅\" " + item.getName() + " \"?");
            builder.setPositiveButton(R.string.button_enter, (dialog, which) -> {
                new Thread(() -> {
                    dao.delete(item.getId());
                    setAdapter();
                }).start();
                dialog.dismiss();
            });
            builder.setNegativeButton(R.string.button_cancel, (dialog, which) -> dialog.dismiss());
            builder.show();
        };
        adapter = new MultiTypeAdapter();
        Register.registerMediaChannelItem(adapter, listener);
        recyclerView.setAdapter(adapter);
    }

    @Override
    public void onRefresh() {
        swipeRefreshLayout.setRefreshing(true);
        setAdapter();
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
