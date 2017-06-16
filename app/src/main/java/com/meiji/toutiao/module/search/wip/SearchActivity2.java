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
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

import com.meiji.toutiao.R;
import com.meiji.toutiao.RetrofitFactory;
import com.meiji.toutiao.adapter.base.BasePagerAdapter;
import com.meiji.toutiao.api.IMobileSearchApi;
import com.meiji.toutiao.bean.search.SearchSuggestionBean;
import com.meiji.toutiao.module.base.BaseActivity;
import com.meiji.toutiao.utils.SettingsUtil;

import java.util.ArrayList;
import java.util.List;

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
    private TabLayout tabLayout;
    private ViewPager viewPager;
    private BasePagerAdapter pagerAdapter;
    private List<Fragment> fragmentList = new ArrayList<>();
    private String[] titles = new String[]{"综合", "视频", "图集", "用户", "问答"};
    private SearchView searchView;
    private LinearLayout searchLayout;
    private ListView listView;
    private SearchAdapter searchAdapter;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search);
        initView();
    }

    private void initView() {
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        initToolBar(toolbar, true, "");
        tabLayout = (TabLayout) findViewById(R.id.tab_layout);
        viewPager = (ViewPager) findViewById(R.id.view_pager);

        tabLayout.setBackgroundColor(SettingsUtil.getInstance().getColor());
        tabLayout.setupWithViewPager(viewPager);
        tabLayout.setTabMode(TabLayout.MODE_SCROLLABLE);
        searchLayout = (LinearLayout) findViewById(R.id.search_layout);
        listView = (ListView) findViewById(R.id.listview);

        searchAdapter = new SearchAdapter(this, -1);
        listView.setAdapter(searchAdapter);
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                String keyWord = searchAdapter.getItem(position).getKeyword();
                Log.d(TAG, "onItemClick: " + keyWord);
                initData(keyWord);
                pagerAdapter.notifyDataSetChanged();
                searchView.clearFocus();
                searchView.setQuery(keyWord, true);
            }
        });
    }

    private void initData(String query) {
        searchLayout.setVisibility(View.VISIBLE);
        listView.setVisibility(View.GONE);
        if (fragmentList.size() > 0) {
            fragmentList.clear();
        }
        for (int i = 1; i < titles.length + 1; i++) {
            fragmentList.add(SearchFragment2.newInstance(query, i + ""));
        }
        Log.d(TAG, "initData: " + query);
        pagerAdapter = new BasePagerAdapter(getSupportFragmentManager(), fragmentList, titles);
        viewPager.setAdapter(pagerAdapter);
        viewPager.setOffscreenPageLimit(5);
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
            public boolean onQueryTextSubmit(String keyWord) {
                Log.d(TAG, "onQueryTextSubmit: " + keyWord);
                initData(keyWord);
                pagerAdapter.notifyDataSetChanged();
                return false;
            }

            @Override
            public boolean onQueryTextChange(String keyWord) {
                if (!TextUtils.isEmpty(keyWord)) {
                    getSearchSuggest(keyWord);
                } else {
                    if (searchLayout.getVisibility() != View.GONE) {
                        searchLayout.setVisibility(View.GONE);
                    }
                    if (listView.getVisibility() != View.VISIBLE) {
                        listView.setVisibility(View.VISIBLE);
                    }
                }
                return false;
            }
        });

        return super.onCreateOptionsMenu(menu);
    }

    private void getSearchSuggest(String keyWord) {
        RetrofitFactory.getRetrofit().create(IMobileSearchApi.class)
                .getSearchSuggestion(keyWord.trim())
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
                        searchAdapter.updateDataSource(dataBeen);
                    }
                }, new Consumer<Throwable>() {
                    @Override
                    public void accept(@NonNull Throwable throwable) throws Exception {
                        throwable.printStackTrace();
                    }
                });
    }

    @Override
    protected void onRestart() {
        super.onRestart();
        searchView.clearFocus();
    }

    private class SearchAdapter extends ArrayAdapter<SearchSuggestionBean.DataBean> {

        private LayoutInflater inflater;
        private List<SearchSuggestionBean.DataBean> data;

        public SearchAdapter(Context context, int resource) {
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
