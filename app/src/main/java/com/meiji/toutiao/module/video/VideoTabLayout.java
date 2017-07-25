package com.meiji.toutiao.module.video;

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
import com.meiji.toutiao.module.base.BaseListFragment;
import com.meiji.toutiao.module.video.article.VideoArticleView;
import com.meiji.toutiao.util.SettingUtil;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Meiji on 2017/3/28.
 */

public class VideoTabLayout extends Fragment {

    private static VideoTabLayout instance = null;
    private static int pageSize = InitApp.AppContext.getResources().getStringArray(R.array.mobile_video_id).length;
    private String categoryId[] = InitApp.AppContext.getResources().getStringArray(R.array.mobile_video_id);
    private String categoryName[] = InitApp.AppContext.getResources().getStringArray(R.array.mobile_video_name);
    private TabLayout tabLayout;
    private ViewPager viewPager;
    private List<Fragment> fragmentList = new ArrayList<>();
    private BasePagerAdapter adapter;

    public static VideoTabLayout getInstance() {
        if (instance == null) {
            instance = new VideoTabLayout();
        }
        return instance;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_video_tab, container, false);
        initView(view);
        initData();
        return view;
    }

    @Override
    public void onResume() {
        super.onResume();
        tabLayout.setBackgroundColor(SettingUtil.getInstance().getColor());
    }

    private void initView(View view) {
        tabLayout = view.findViewById(R.id.tab_layout_video);
        viewPager = view.findViewById(R.id.view_pager_video);

        tabLayout.setupWithViewPager(viewPager);
        tabLayout.setTabMode(TabLayout.MODE_SCROLLABLE);
        tabLayout.setBackgroundColor(SettingUtil.getInstance().getColor());
        viewPager.setOffscreenPageLimit(pageSize);
    }

    private void initData() {
        for (int i = 0; i < categoryId.length; i++) {
            Fragment fragment = VideoArticleView.newInstance(categoryId[i]);
            fragmentList.add(fragment);
        }
        adapter = new BasePagerAdapter(getChildFragmentManager(), fragmentList, categoryName);
        viewPager.setAdapter(adapter);
    }

    public void onDoubleClick() {
        if (fragmentList != null && fragmentList.size() > 0) {
            int item = viewPager.getCurrentItem();
            ((BaseListFragment) fragmentList.get(item)).onRefresh();
        }
    }

    @Override
    public void onDestroy() {
        if (instance != null) {
            instance = null;
        }
        super.onDestroy();
    }
}
