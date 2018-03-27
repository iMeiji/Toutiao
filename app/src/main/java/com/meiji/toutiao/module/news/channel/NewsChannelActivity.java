package com.meiji.toutiao.module.news.channel;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.helper.ItemTouchHelper;
import android.widget.Toast;

import com.meiji.toutiao.Constant;
import com.meiji.toutiao.ErrorAction;
import com.meiji.toutiao.R;
import com.meiji.toutiao.adapter.news.NewsChannelAdapter;
import com.meiji.toutiao.bean.news.NewsChannelBean;
import com.meiji.toutiao.database.dao.NewsChannelDao;
import com.meiji.toutiao.module.base.BaseActivity;
import com.meiji.toutiao.module.news.NewsTabLayout;
import com.meiji.toutiao.util.RxBus;
import com.meiji.toutiao.widget.helper.ItemDragHelperCallback;

import java.util.List;

import io.reactivex.Observable;
import io.reactivex.ObservableOnSubscribe;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;

/**
 * Created by Meiji on 2017/3/10.
 */

public class NewsChannelActivity extends BaseActivity {

    private RecyclerView recyclerView;
    private NewsChannelAdapter adapter;
    private NewsChannelDao dao = new NewsChannelDao();

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_news_channel);
        initView();
        initData();
    }

    @Override
    protected void onStop() {
        super.onStop();
        onSaveData();
    }

    private void initView() {
        initToolBar(findViewById(R.id.toolbar), true, getString(R.string.title_item_drag));
        recyclerView = findViewById(R.id.recycler_view);
    }

    private void initData() {
        final List<NewsChannelBean> enableItems = dao.query(Constant.NEWS_CHANNEL_ENABLE);
        final List<NewsChannelBean> disableItems = dao.query(Constant.NEWS_CHANNEL_DISABLE);

        GridLayoutManager manager = new GridLayoutManager(this, 4);
        recyclerView.setLayoutManager(manager);

        ItemDragHelperCallback callback = new ItemDragHelperCallback();
        final ItemTouchHelper helper = new ItemTouchHelper(callback);
        helper.attachToRecyclerView(recyclerView);

        adapter = new NewsChannelAdapter(this, helper, enableItems, disableItems);
        manager.setSpanSizeLookup(new GridLayoutManager.SpanSizeLookup() {
            @Override
            public int getSpanSize(int position) {
                int viewType = adapter.getItemViewType(position);
                return viewType == NewsChannelAdapter.TYPE_MY || viewType == NewsChannelAdapter.TYPE_OTHER ? 1 : 4;
            }
        });
        recyclerView.setAdapter(adapter);

        adapter.setOnMyChannelItemClickListener((v, position) -> Toast.makeText(NewsChannelActivity.this, enableItems.get(position).getChannelName() + position, Toast.LENGTH_SHORT).show());
    }

    public void onSaveData() {

        Observable
                .create((ObservableOnSubscribe<Boolean>) e -> {
                    List<NewsChannelBean> oldItems = dao.query(Constant.NEWS_CHANNEL_ENABLE);
                    e.onNext(!compare(oldItems, adapter.getmMyChannelItems()));
                })
                .subscribeOn(Schedulers.io())
                .doOnNext(aBoolean -> {
                    if (aBoolean) {
                        List<NewsChannelBean> enableItems = adapter.getmMyChannelItems();
                        List<NewsChannelBean> disableItems = adapter.getmOtherChannelItems();
                        dao.removeAll();
                        for (int i = 0; i < enableItems.size(); i++) {
                            NewsChannelBean bean = enableItems.get(i);
                            dao.add(bean.getChannelId(), bean.getChannelName(), Constant.NEWS_CHANNEL_ENABLE, i);
                        }
                        for (int i = 0; i < disableItems.size(); i++) {
                            NewsChannelBean bean = disableItems.get(i);
                            dao.add(bean.getChannelId(), bean.getChannelName(), Constant.NEWS_CHANNEL_DISABLE, i);
                        }
                    }
                })
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(isRefresh -> RxBus.getInstance().post(NewsTabLayout.TAG, isRefresh), ErrorAction.error());
    }

    public synchronized <T extends Comparable<T>> boolean compare(List<T> a, List<T> b) {
        if (a.size() != b.size())
            return false;
//        Collections.sort(a);
//        Collections.sort(b);
        for (int i = 0; i < a.size(); i++) {
            if (!a.get(i).equals(b.get(i)))
                return false;
        }
        return true;
    }
}




