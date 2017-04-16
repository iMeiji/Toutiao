package com.meiji.toutiao.news;

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

import com.meiji.toutiao.R;
import com.meiji.toutiao.adapter.base.BasePagerAdapter;
import com.meiji.toutiao.bean.news.NewsChannelBean;
import com.meiji.toutiao.database.dao.NewsChannelDao;
import com.meiji.toutiao.news.article.NewsArticleView;
import com.meiji.toutiao.news.channel.NewsChannelActivity;
import com.meiji.toutiao.news.joke.content.JokeContentView;

import java.util.ArrayList;
import java.util.List;

import static android.app.Activity.RESULT_OK;

/**
 * Created by Meiji on 2016/12/12.
 */

public class NewsTabLayout extends Fragment {

    private static final String TAG = "NewsTabLayout";
    private static NewsTabLayout instance = null;
    //    private static int pageSize = InitApp.AppContext.getResources().getStringArray(R.array.news_id).length;
    private final int REQUEST_CODE = 1;
    private ViewPager view_pager;
    private TabLayout tab_layout;
    private ImageView add_channel_iv;
    private List<Fragment> list = new ArrayList<>();
    private BasePagerAdapter adapter;

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
        tab_layout.setBackgroundColor(getResources().getColor(R.color.colorPrimary));
        add_channel_iv.setBackgroundColor(getResources().getColor(R.color.colorPrimary));
    }

    private void initView(View view) {
        tab_layout = (TabLayout) view.findViewById(R.id.tab_layout_news);
        view_pager = (ViewPager) view.findViewById(R.id.view_pager_news);

        tab_layout.setupWithViewPager(view_pager);
        tab_layout.setTabMode(TabLayout.MODE_SCROLLABLE);
        add_channel_iv = (ImageView) view.findViewById(R.id.add_channel_iv);
        add_channel_iv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(), NewsChannelActivity.class);
                startActivityForResult(intent, REQUEST_CODE);
            }
        });
    }

    /**
     * 初始化 NewsArticleView 数据
     */
    private void initData() {
        NewsChannelDao dao = new NewsChannelDao();
        List<NewsChannelBean> tabList = dao.query(1);
        if (tabList.size() == 0) {
            dao.addInitData();
            tabList = dao.query(1);
        }
        String[] categoryName = new String[tabList.size()];
        for (int i = 0; i < tabList.size(); i++) {
            if (!tabList.get(i).getChannelId().equals("essay_joke")) {
                Fragment fragment = NewsArticleView.newInstance(tabList.get(i).getChannelId());
                list.add(fragment);
            } else {
                Fragment jokeContentView = JokeContentView.newInstance(tabList.get(i).getChannelId());
                list.add(jokeContentView);
            }
            categoryName[i] = tabList.get(i).getChannelName();
        }
        adapter = new BasePagerAdapter(getChildFragmentManager(), list, categoryName);
        view_pager.setAdapter(adapter);
        view_pager.setOffscreenPageLimit(tabList.size());
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        if (instance != null) {
            instance = null;
        }
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == REQUEST_CODE) {
            if (resultCode == RESULT_OK) {
                getActivity().getWindow().setWindowAnimations(R.style.WindowAnimationFadeInOut);
                getActivity().recreate();
            }
        }
    }
}
