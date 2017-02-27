package com.meiji.toutiao.other;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.meiji.toutiao.InitApp;
import com.meiji.toutiao.R;
import com.meiji.toutiao.adapter.base.BasePagerAdapter;
import com.meiji.toutiao.other.funny.article.FunnyArticleView;
import com.meiji.toutiao.other.joke.content.JokeContentView;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Meiji on 2016/12/28.
 */

public class OtherTabLayout extends Fragment {

    private static OtherTabLayout instance = null;
    private static int pageSize = InitApp.AppContext.getResources().getStringArray(R.array.other_id).length;
    private String categoryId[] = InitApp.AppContext.getResources().getStringArray(R.array.other_id);
    private String categoryName[] = InitApp.AppContext.getResources().getStringArray(R.array.other_name);
    private TabLayout tab_layout;
    private ViewPager view_pager;
    private List<Fragment> list = new ArrayList<>();
    private BasePagerAdapter adapter;

    public static OtherTabLayout getInstance() {
        if (instance == null) {
            instance = new OtherTabLayout();
        }
        return instance;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.other_adapter, container, false);
        initView(view);
        initData();
        return view;
    }

    private void initView(View view) {
        tab_layout = (TabLayout) view.findViewById(R.id.tab_layout_other);
        view_pager = (ViewPager) view.findViewById(R.id.view_pager_other);

        tab_layout.setupWithViewPager(view_pager);
        tab_layout.setTabMode(TabLayout.MODE_SCROLLABLE);
        view_pager.setOffscreenPageLimit(pageSize);
    }

    private void initData() {
        Fragment jokeContentView = JokeContentView.newInstance(categoryId[0]);
        Fragment funnyArticleView = FunnyArticleView.newInstance(categoryId[1]);
        list.add(jokeContentView);
        list.add(funnyArticleView);

        adapter = new BasePagerAdapter(getFragmentManager(), list, categoryName);
        view_pager.setAdapter(adapter);
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        if (instance != null) {
            instance = null;
        }
        if (adapter != null) {
            adapter = null;
        }
    }
}
