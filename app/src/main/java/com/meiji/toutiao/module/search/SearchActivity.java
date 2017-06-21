package com.meiji.toutiao.module.search;

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
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.LinearLayout;
import android.widget.ListView;

import com.meiji.toutiao.ErrorAction;
import com.meiji.toutiao.R;
import com.meiji.toutiao.RetrofitFactory;
import com.meiji.toutiao.adapter.base.BasePagerAdapter;
import com.meiji.toutiao.adapter.search.SearchHistoryAdapter;
import com.meiji.toutiao.api.IMobileSearchApi;
import com.meiji.toutiao.bean.search.SearchHistoryBean;
import com.meiji.toutiao.bean.search.SearchSuggestionBean;
import com.meiji.toutiao.database.dao.SearchHistoryDao;
import com.meiji.toutiao.module.base.BaseActivity;
import com.meiji.toutiao.module.search.result.SearchResultFragment;
import com.meiji.toutiao.utils.SettingsUtil;
import com.meiji.toutiao.utils.TimeUtil;

import java.util.ArrayList;
import java.util.List;

import io.reactivex.Observable;
import io.reactivex.ObservableEmitter;
import io.reactivex.ObservableOnSubscribe;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.annotations.NonNull;
import io.reactivex.functions.Consumer;
import io.reactivex.functions.Function;
import io.reactivex.schedulers.Schedulers;

/**
 * Created by Meiji on 2017/6/13.
 */

public class SearchActivity extends BaseActivity {

    private static final String TAG = "SearchActivity";
    private TabLayout tabLayout;
    private ViewPager viewPager;
    private BasePagerAdapter pagerAdapter;
    private String[] titles = new String[]{"综合", "视频", "图集", "用户", "问答"};
    private SearchView searchView;
    private LinearLayout searchLayout;
    private ListView listView;
    private SearchHistoryAdapter adapter;
    private SearchHistoryDao dao = new SearchHistoryDao();

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search);
        initView();
        getSearchHistory();
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

        adapter = new SearchHistoryAdapter(this, -1);
        listView.setAdapter(adapter);
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                String keyWord = adapter.getItem(position).getKeyWord();
                Log.d(TAG, "onItemClick: " + keyWord);
                initData(keyWord);
                searchView.clearFocus();
                searchView.setQuery(keyWord, true);
            }
        });
    }

    private void initData(String query) {
        searchLayout.setVisibility(View.VISIBLE);
        listView.setVisibility(View.GONE);
        List<Fragment> fragmentList = new ArrayList<>();
        for (int i = 1; i < titles.length + 1; i++) {
            fragmentList.add(SearchResultFragment.newInstance(query, i + ""));
        }
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
                new ComponentName(getApplicationContext(), SearchActivity.class));
        searchView.setSearchableInfo(searchableInfo);
        searchView.onActionViewExpanded();

        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(final String keyWord) {
                Log.d(TAG, "onQueryTextSubmit: " + keyWord);
                initData(keyWord);
                new Thread(new Runnable() {
                    @Override
                    public void run() {
                        if (dao.queryisExist(keyWord)) {
                            dao.update(keyWord);
                        } else {
                            dao.add(keyWord);
                        }
                    }
                }).start();
                return false;
            }

            @Override
            public boolean onQueryTextChange(String keyWord) {
                if (!TextUtils.isEmpty(keyWord)) {
                    getSearchSuggest(keyWord);
                } else {
                    getSearchHistory();
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

    private void getSearchHistory() {
        Observable
                .create(new ObservableOnSubscribe<List<SearchHistoryBean>>() {
                    @Override
                    public void subscribe(@NonNull ObservableEmitter<List<SearchHistoryBean>> e) throws Exception {
                        List<SearchHistoryBean> list = dao.queryAll();
                        e.onNext(list);
                    }
                })
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .compose(this.<List<SearchHistoryBean>>bindToLifecycle())
                .subscribe(new Consumer<List<SearchHistoryBean>>() {
                    @Override
                    public void accept(@NonNull final List<SearchHistoryBean> list) throws Exception {
                        adapter.updateDataSource(list);
                    }
                }, ErrorAction.error());
    }

    private void getSearchSuggest(String keyWord) {
        RetrofitFactory.getRetrofit().create(IMobileSearchApi.class)
                .getSearchSuggestion(keyWord.trim())
                .subscribeOn(Schedulers.io())
                .map(new Function<SearchSuggestionBean, List<SearchHistoryBean>>() {
                    @Override
                    public List<SearchHistoryBean> apply(@NonNull SearchSuggestionBean searchSuggestionBean) throws Exception {
                        List<SearchHistoryBean> list = new ArrayList<>();
                        for (SearchSuggestionBean.DataBean bean : searchSuggestionBean.getData()) {
                            SearchHistoryBean searchHistoryBean = new SearchHistoryBean();
                            searchHistoryBean.setKeyWord(bean.getKeyword());
                            searchHistoryBean.setType(SearchHistoryAdapter.TYPE_SUG);
                            searchHistoryBean.setTime(TimeUtil.getTimeStamp());
                            list.add(searchHistoryBean);
                        }
                        return list;
                    }
                })
                .observeOn(AndroidSchedulers.mainThread())
                .compose(this.<List<SearchHistoryBean>>bindToLifecycle())
                .subscribe(new Consumer<List<SearchHistoryBean>>() {
                    @Override
                    public void accept(@NonNull List<SearchHistoryBean> searchHistoryBeen) throws Exception {
                        adapter.updateDataSource(searchHistoryBeen);
                    }
                }, ErrorAction.error());
    }

    @Override
    protected void onRestart() {
        super.onRestart();
        searchView.clearFocus();
    }
}
