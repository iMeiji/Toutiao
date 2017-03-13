package com.meiji.toutiao.news.channel;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.support.v7.widget.helper.ItemTouchHelper;
import android.view.View;
import android.widget.Toast;

import com.meiji.toutiao.BaseActivity;
import com.meiji.toutiao.R;
import com.meiji.toutiao.adapter.news.NewsChannelAdapter;
import com.meiji.toutiao.bean.news.NewsChannelBean;
import com.meiji.toutiao.database.dao.NewsChannelDao;
import com.meiji.toutiao.view.ItemDragHelperCallback;

import java.util.List;

/**
 * Created by Meiji on 2017/3/10.
 */

public class NewsChannelActivity extends BaseActivity {

    private static final String TAG = "NewsChannelActivity";
    private RecyclerView recycler_view;
    private NewsChannelAdapter adapter;
    private NewsChannelDao dao = new NewsChannelDao();
    private List<NewsChannelBean> items = dao.query(1);

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.news_channel_main);
        initView();
        initData();
    }

    private void initData() {
        final List<NewsChannelBean> items = dao.query(1);
        final List<NewsChannelBean> otherItems = dao.query(0);

        GridLayoutManager manager = new GridLayoutManager(this, 4);
        recycler_view.setLayoutManager(manager);

        ItemDragHelperCallback callback = new ItemDragHelperCallback();
        final ItemTouchHelper helper = new ItemTouchHelper(callback);
        helper.attachToRecyclerView(recycler_view);

        adapter = new NewsChannelAdapter(this, helper, items, otherItems);
        manager.setSpanSizeLookup(new GridLayoutManager.SpanSizeLookup() {
            @Override
            public int getSpanSize(int position) {
                int viewType = adapter.getItemViewType(position);
                return viewType == NewsChannelAdapter.TYPE_MY || viewType == NewsChannelAdapter.TYPE_OTHER ? 1 : 4;
            }
        });
        recycler_view.setAdapter(adapter);

        adapter.setOnMyChannelItemClickListener(new NewsChannelAdapter.OnMyChannelItemClickListener() {
            @Override
            public void onItemClick(View v, int position) {
                Toast.makeText(NewsChannelActivity.this, items.get(position).getChannelName() + position, Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void initView() {
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        recycler_view = (RecyclerView) findViewById(R.id.recycler_view);
        setSupportActionBar(toolbar);
        if (getSupportActionBar() != null) {
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        }
    }

    @Override
    public void onBackPressed() {

        if (!compare(items, adapter.getmMyChannelItems())) {
            List<NewsChannelBean> items = adapter.getmMyChannelItems();
            List<NewsChannelBean> otherItems = adapter.getmOtherChannelItems();
            dao.removeAll();
            for (int i = 0; i < items.size(); i++) {
                NewsChannelBean bean = items.get(i);
                dao.add(bean.getChannelId(), bean.getChannelName(), 1, i);
            }
            for (int i = 0; i < otherItems.size(); i++) {
                NewsChannelBean bean = otherItems.get(i);
                dao.add(bean.getChannelId(), bean.getChannelName(), 0, i);
            }
            setResult(RESULT_OK);
        }
        super.onBackPressed();
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
