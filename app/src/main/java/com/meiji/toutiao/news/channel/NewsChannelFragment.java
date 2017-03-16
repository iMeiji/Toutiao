package com.meiji.toutiao.news.channel;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.support.v7.widget.helper.ItemTouchHelper;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.meiji.toutiao.R;
import com.meiji.toutiao.adapter.news.NewsChannelAdapter;
import com.meiji.toutiao.bean.news.NewsChannelBean;
import com.meiji.toutiao.database.dao.NewsChannelDao;
import com.meiji.toutiao.view.ItemDragHelperCallback;

import java.util.List;

import static android.app.Activity.RESULT_OK;

/**
 * Created by Meiji on 2017/3/15.
 */

public class NewsChannelFragment extends Fragment {

    private RecyclerView recycler_view;
    private NewsChannelAdapter adapter;
    private NewsChannelDao dao = new NewsChannelDao();
    private List<NewsChannelBean> items = dao.query(1);

    public static NewsChannelFragment newInstance() {
        return new NewsChannelFragment();
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_news_channel, container, false);
        initView(view);
        initData();
        setHasOptionsMenu(true);
        return view;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if (id == android.R.id.home) {
            if (onSaveData()) {
                getActivity().setResult(RESULT_OK);
            }
            getActivity().onBackPressed();
        }
        return super.onOptionsItemSelected(item);
    }

    private void initView(View view) {
        Toolbar toolbar = (Toolbar) view.findViewById(R.id.toolbar);
        recycler_view = (RecyclerView) view.findViewById(R.id.recycler_view);
        AppCompatActivity activity = (AppCompatActivity) getActivity();
        activity.setSupportActionBar(toolbar);
        if (activity.getSupportActionBar() != null) {
            activity.getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        }
    }

    private void initData() {
        final List<NewsChannelBean> items = dao.query(1);
        final List<NewsChannelBean> otherItems = dao.query(0);

        GridLayoutManager manager = new GridLayoutManager(getActivity(), 4);
        recycler_view.setLayoutManager(manager);

        ItemDragHelperCallback callback = new ItemDragHelperCallback();
        final ItemTouchHelper helper = new ItemTouchHelper(callback);
        helper.attachToRecyclerView(recycler_view);

        adapter = new NewsChannelAdapter(getActivity(), helper, items, otherItems);
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
                Toast.makeText(getActivity(), items.get(position).getChannelName() + position, Toast.LENGTH_SHORT).show();
            }
        });
    }

    public boolean onSaveData() {
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
            return true;
        }
        return false;
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