package com.meiji.toutiao.module.news;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.meiji.toutiao.Constant;
import com.meiji.toutiao.R;
import com.meiji.toutiao.adapter.base.BasePagerAdapter;
import com.meiji.toutiao.bean.news.NewsChannelBean;
import com.meiji.toutiao.database.dao.NewsChannelDao;
import com.meiji.toutiao.module.base.BaseListFragment;
import com.meiji.toutiao.module.joke.content.JokeContentView;
import com.meiji.toutiao.module.news.article.NewsArticleView;
import com.meiji.toutiao.module.news.channel.NewsChannelActivity;
import com.meiji.toutiao.module.wenda.article.WendaArticleView;
import com.meiji.toutiao.util.RxBus;
import com.meiji.toutiao.util.SettingUtil;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import io.reactivex.Observable;

/**
 * Created by Meiji on 2016/12/12.
 */

public class NewsTabLayout extends Fragment {

    public static final String TAG = "NewsTabLayout";
    private static NewsTabLayout instance = null;
    private ViewPager viewPager;
    private BasePagerAdapter adapter;
    private LinearLayout linearLayout;
    private NewsChannelDao dao = new NewsChannelDao();
    private List<Fragment> fragmentList;
    private List<String> titleList;
    private Observable<Boolean> observable;
    private Map<String, Fragment> map = new HashMap<>();

    public static NewsTabLayout getInstance() {
        if (instance == null) {
            instance = new NewsTabLayout();
        }
        return instance;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_news_tab, container, false);
        initView(view);
        initData();
        return view;
    }

    @Override
    public void onResume() {
        super.onResume();
        linearLayout.setBackgroundColor(SettingUtil.getInstance().getColor());
    }

    private void initView(View view) {
        TabLayout tab_layout = view.findViewById(R.id.tab_layout_news);
        viewPager = view.findViewById(R.id.view_pager_news);

        tab_layout.setupWithViewPager(viewPager);
        tab_layout.setTabMode(TabLayout.MODE_SCROLLABLE);
        ImageView add_channel_iv = view.findViewById(R.id.add_channel_iv);
        add_channel_iv.setOnClickListener(v -> startActivity(new Intent(getActivity(), NewsChannelActivity.class)));
        linearLayout = view.findViewById(R.id.header_layout);
        linearLayout.setBackgroundColor(SettingUtil.getInstance().getColor());
    }

    private void initData() {
        initTabs();
        adapter = new BasePagerAdapter(getChildFragmentManager(), fragmentList, titleList);
        viewPager.setAdapter(adapter);
        viewPager.setOffscreenPageLimit(15);

        observable = RxBus.getInstance().register(NewsTabLayout.TAG);
        observable.subscribe(isRefresh -> {
            if (isRefresh) {
                initTabs();
                adapter.recreateItems(fragmentList, titleList);
            }
        });
    }

    private void initTabs() {
        List<NewsChannelBean> channelList = dao.query(1);
        fragmentList = new ArrayList<>();
        titleList = new ArrayList<>();
        if (channelList.size() == 0) {
            dao.addInitData();
            channelList = dao.query(Constant.NEWS_CHANNEL_ENABLE);
        }

        for (NewsChannelBean bean : channelList) {

            Fragment fragment = null;
            String channelId = bean.getChannelId();

            switch (channelId) {
                case "essay_joke":
                    if (map.containsKey(channelId)) {
                        fragmentList.add(map.get(channelId));
                    } else {
                        fragment = JokeContentView.newInstance();
                        fragmentList.add(fragment);
                    }

                    break;
                case "question_and_answer":
                    if (map.containsKey(channelId)) {
                        fragmentList.add(map.get(channelId));
                    } else {
                        fragment = WendaArticleView.newInstance();
                        fragmentList.add(fragment);
                    }

                    break;
                default:
                    if (map.containsKey(channelId)) {
                        fragmentList.add(map.get(channelId));
                    } else {
                        fragment = NewsArticleView.newInstance(channelId);
                        fragmentList.add(fragment);
                    }
                    break;
            }

            titleList.add(bean.getChannelName());

            if (fragment != null) {
                map.put(channelId, fragment);
            }
        }
    }

    public void onDoubleClick() {
        if (titleList != null && titleList.size() > 0 && fragmentList != null && fragmentList.size() > 0) {
            int item = viewPager.getCurrentItem();
            ((BaseListFragment) fragmentList.get(item)).onRefresh();
        }
    }

    @Override
    public void onDestroy() {
        RxBus.getInstance().unregister(NewsTabLayout.TAG, observable);
        if (instance != null) {
            instance = null;
        }
        super.onDestroy();
    }
}
