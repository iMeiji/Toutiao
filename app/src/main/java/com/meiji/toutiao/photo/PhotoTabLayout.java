package com.meiji.toutiao.photo;

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
import com.meiji.toutiao.photo.article.PhotoArticleView;
import com.meiji.toutiao.utils.ColorUtil;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Meiji on 2017/2/16.
 */

public class PhotoTabLayout extends Fragment {

    private static PhotoTabLayout instance = null;
    private static int pageSize = InitApp.AppContext.getResources().getStringArray(R.array.photo_id).length;
    private TabLayout tab_layout;
    private ViewPager view_pager;
    private List<Fragment> list = new ArrayList<>();

    public static PhotoTabLayout getInstance() {
        if (instance == null) {
            instance = new PhotoTabLayout();
        }
        return instance;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.photo_adapter, container, false);
        initView(view);
        initData();
        return view;
    }

    private void initView(View view) {
        tab_layout = (TabLayout) view.findViewById(R.id.tab_layout_photo);
        view_pager = (ViewPager) view.findViewById(R.id.view_pager_photo);

        tab_layout.setBackgroundColor(ColorUtil.getColor(getActivity()));
        tab_layout.setupWithViewPager(view_pager);
        tab_layout.setTabMode(TabLayout.MODE_SCROLLABLE);
        view_pager.setOffscreenPageLimit(pageSize);
    }

    private void initData() {
        String categoryId[] = InitApp.AppContext.getResources().getStringArray(R.array.photo_id);
        String categoryName[] = InitApp.AppContext.getResources().getStringArray(R.array.photo_name);
        for (int i = 0; i < categoryId.length; i++) {
            Fragment fragment = PhotoArticleView.newInstance(categoryId[i]);
            list.add(fragment);
        }

        BasePagerAdapter adapter = new BasePagerAdapter(getFragmentManager(), list, categoryName);
        view_pager.setAdapter(adapter);
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        if (instance != null) {
            instance = null;
        }
    }
}
