package com.meiji.toutiao.news.article;

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
import com.meiji.toutiao.adapter.news.NewsPagerAdapter;
import com.meiji.toutiao.utils.ColorUtil;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Meiji on 2016/12/12.
 */

public class ArticleTabLayout extends Fragment {

    private static ArticleTabLayout instance = null;
    private TabLayout tab_layout;
    private ViewPager view_pager;
    private List<Fragment> list = new ArrayList<>();

    public static ArticleTabLayout getInstance() {
        if (instance == null) {
            instance = new ArticleTabLayout();
        }
        return instance;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.news_article_adapter, container, false);
        initView(view);
        initData();
        return view;
    }

    private void initView(View view) {
        tab_layout = (TabLayout) view.findViewById(R.id.tab_layout);
        view_pager = (ViewPager) view.findViewById(R.id.view_pager);

        tab_layout.setBackgroundColor(ColorUtil.getColor());
        tab_layout.setupWithViewPager(view_pager);
        tab_layout.setTabMode(TabLayout.MODE_SCROLLABLE);
        view_pager.setOffscreenPageLimit(10);
    }

    /**
     * 初始化 ArticleView 数据
     */
    private void initData() {
        String categoryId[] = InitApp.AppContext.getResources().getStringArray(R.array.id);
        String categoryName[] = InitApp.AppContext.getResources().getStringArray(R.array.name);
        for (int i = 0; i < categoryId.length; i++) {
            Fragment fragment = ArticleView.newInstance(categoryId[i]);
            list.add(fragment);
        }
        NewsPagerAdapter adapter = new NewsPagerAdapter(getFragmentManager(), list, categoryName);
        view_pager.setAdapter(adapter);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        instance = null;
    }
}
