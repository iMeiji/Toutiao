package com.meiji.toutiao.adapter.base;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.PagerAdapter;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * Created by Meiji on 2017/2/16.
 */

public class BasePagerAdapter extends FragmentPagerAdapter {

    private List<Fragment> fragments;
    private List<String> titles;

    public BasePagerAdapter(FragmentManager fm, List<Fragment> list, String[] title) {
        super(fm);
        this.fragments = list;
        this.titles = new ArrayList<>(Arrays.asList(title));
    }

    @Override
    public Fragment getItem(int position) {
        return fragments.get(position);
    }

    @Override
    public int getCount() {
        return fragments.size();
    }

    @Override
    public CharSequence getPageTitle(int position) {
        return titles.get(position);
    }

    @Override
    public int getItemPosition(Object object) {
        return PagerAdapter.POSITION_NONE;
    }

    public void recreateItems(List<Fragment> list, String[] title) {
        this.fragments.clear();
        this.titles.clear();

        this.fragments = list;
        this.titles = new ArrayList<>(Arrays.asList(title));
        notifyDataSetChanged();
    }
}
