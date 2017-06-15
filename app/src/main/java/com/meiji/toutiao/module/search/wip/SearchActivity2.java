package com.meiji.toutiao.module.search.wip;

import android.app.SearchManager;
import android.app.SearchableInfo;
import android.content.ComponentName;
import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.view.MenuItemCompat;
import android.support.v4.view.ViewPager;
import android.support.v7.widget.SearchView;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

import com.jakewharton.rxbinding2.support.v7.widget.RxSearchView;
import com.jakewharton.rxbinding2.support.v7.widget.SearchViewQueryTextEvent;
import com.meiji.toutiao.R;
import com.meiji.toutiao.RetrofitFactory;
import com.meiji.toutiao.adapter.base.BasePagerAdapter;
import com.meiji.toutiao.api.IMobileSearchApi;
import com.meiji.toutiao.bean.search.SearchSuggestionBean;
import com.meiji.toutiao.module.base.BaseActivity;
import com.meiji.toutiao.utils.SettingsUtil;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;

import io.reactivex.ObservableSource;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.annotations.NonNull;
import io.reactivex.functions.Consumer;
import io.reactivex.functions.Function;
import io.reactivex.schedulers.Schedulers;

/**
 * Created by Meiji on 2017/6/13.
 */

public class SearchActivity2 extends BaseActivity {

    private static final String TAG = "SearchActivity2";
    private TabLayout tab_layout;
    private ViewPager view_pager;
    private BasePagerAdapter adapter;
    private List<Fragment> fragmentList = new ArrayList<>();
    private String[] titles = new String[]{"综合", "视频", "图集", "用户", "问答"};
    private SearchView searchView;
    private LinearLayout search_layout;
    private ListView listview;
    private MyAdapter myAdapter;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search);
        initView();
    }

    private void initView() {
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        initToolBar(toolbar, true, "");
        tab_layout = (TabLayout) findViewById(R.id.tab_layout);
        view_pager = (ViewPager) findViewById(R.id.view_pager);

        tab_layout.setBackgroundColor(SettingsUtil.getInstance().getColor());
        tab_layout.setupWithViewPager(view_pager);
        tab_layout.setTabMode(TabLayout.MODE_SCROLLABLE);
        search_layout = (LinearLayout) findViewById(R.id.search_layout);
        listview = (ListView) findViewById(R.id.listview);

        myAdapter = new MyAdapter(this, -1);
        listview.setAdapter(myAdapter);
    }

    private void initData(String query) {
        search_layout.setVisibility(View.VISIBLE);
        listview.setVisibility(View.GONE);
        if (fragmentList.size() > 0) {
            fragmentList.clear();
        }
        for (int i = 1; i < titles.length + 1; i++) {
            fragmentList.add(SearchFragment2.newInstance(query, i + ""));
        }
        Log.d(TAG, "initData: " + query);
        adapter = new BasePagerAdapter(getSupportFragmentManager(), fragmentList, titles);
        view_pager.setAdapter(adapter);
        view_pager.setOffscreenPageLimit(5);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_search, menu);
        MenuItem item = menu.findItem(R.id.action_search);
        searchView = (SearchView) MenuItemCompat.getActionView(item);
        // 关联检索配置与 SearchActivity
        SearchManager searchManager = (SearchManager) getSystemService(Context.SEARCH_SERVICE);
        SearchableInfo searchableInfo = searchManager.getSearchableInfo(
                new ComponentName(getApplicationContext(), SearchActivity2.class));
        searchView.setSearchableInfo(searchableInfo);
        searchView.onActionViewExpanded();


        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                Log.d(TAG, "onQueryTextSubmit: " + query);
                initData(query);
                adapter.notifyDataSetChanged();
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
//                getSearchSuggest();
                if (!TextUtils.isEmpty(newText)) {
                    getSearchSuggest2(newText);
                } else {
                    if (search_layout.getVisibility() != View.GONE) {
                        search_layout.setVisibility(View.GONE);
                    }
                    if (listview.getVisibility() != View.VISIBLE) {
                        listview.setVisibility(View.VISIBLE);
                    }
                }
                return false;
            }
        });

        return super.onCreateOptionsMenu(menu);
    }

    private void getSearchSuggest2(String text) {
        RetrofitFactory.getRetrofit().create(IMobileSearchApi.class)
                .getSearchSuggestion(text.trim())
                .subscribeOn(Schedulers.io())
                .map(new Function<SearchSuggestionBean, List<SearchSuggestionBean.DataBean>>() {
                    @Override
                    public List<SearchSuggestionBean.DataBean> apply(@NonNull SearchSuggestionBean searchSuggestionBean) throws Exception {
                        return searchSuggestionBean.getData();
                    }
                })
                .observeOn(AndroidSchedulers.mainThread())
                .compose(this.<List<SearchSuggestionBean.DataBean>>bindToLifecycle())
                .subscribe(new Consumer<List<SearchSuggestionBean.DataBean>>() {
                    @Override
                    public void accept(@NonNull List<SearchSuggestionBean.DataBean> dataBeen) throws Exception {
                        myAdapter.updateDataSource(dataBeen);
                    }
                }, new Consumer<Throwable>() {
                    @Override
                    public void accept(@NonNull Throwable throwable) throws Exception {
                        throwable.printStackTrace();
                    }
                });
    }

    private void getSearchSuggest() {
        RxSearchView.queryTextChangeEvents(searchView)
                .subscribeOn(AndroidSchedulers.mainThread())
                .debounce(500, TimeUnit.MILLISECONDS, AndroidSchedulers.mainThread())
                .observeOn(Schedulers.io())
                .switchMap(new Function<SearchViewQueryTextEvent, ObservableSource<SearchSuggestionBean>>() {
                    @Override
                    public ObservableSource<SearchSuggestionBean> apply(@NonNull SearchViewQueryTextEvent searchViewQueryTextEvent) throws Exception {
                        return RetrofitFactory.getRetrofit().create(IMobileSearchApi.class)
                                .getSearchSuggestion(searchViewQueryTextEvent.queryText().toString().trim());
                    }
                })
                .map(new Function<SearchSuggestionBean, List<SearchSuggestionBean.DataBean>>() {
                    @Override
                    public List<SearchSuggestionBean.DataBean> apply(@NonNull SearchSuggestionBean searchSuggestionBean) throws Exception {
                        return searchSuggestionBean.getData();
                    }
                })
                .observeOn(AndroidSchedulers.mainThread())
                .compose(this.<List<SearchSuggestionBean.DataBean>>bindToLifecycle())
                .subscribe(new Consumer<List<SearchSuggestionBean.DataBean>>() {
                    @Override
                    public void accept(@NonNull List<SearchSuggestionBean.DataBean> dataBeen) throws Exception {
                        myAdapter.updateDataSource(dataBeen);
                    }
                }, new Consumer<Throwable>() {
                    @Override
                    public void accept(@NonNull Throwable throwable) throws Exception {
                        throwable.printStackTrace();
                    }
                });
    }

    private class MyAdapter extends ArrayAdapter<SearchSuggestionBean.DataBean> {

        private LayoutInflater inflater;
        private List<SearchSuggestionBean.DataBean> data;

        public MyAdapter(Context context, int resource) {
            super(context, resource);
            inflater = LayoutInflater.from(context);
            data = new ArrayList<>();
        }

        // 更新数据并notifyDataSetChanged
        public void updateDataSource(List<SearchSuggestionBean.DataBean> data) {
            this.data = data;
            this.notifyDataSetChanged();
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            if (convertView == null)
                convertView = inflater.inflate(android.R.layout.simple_list_item_1, null);

            TextView text = (TextView) convertView.findViewById(android.R.id.text1);
            text.setText(getItem(position).getKeyword());

            return convertView;
        }

        @Override
        public SearchSuggestionBean.DataBean getItem(int pos) {
            return data.get(pos);
        }

        @Override
        public int getCount() {
            return data.size();
        }
    }
}
