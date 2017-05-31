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

import com.meiji.toutiao.R;
import com.meiji.toutiao.adapter.base.BasePagerAdapter;
import com.meiji.toutiao.bean.news.NewsChannelBean;
import com.meiji.toutiao.database.dao.NewsChannelDao;
import com.meiji.toutiao.module.joke.content.JokeContentView;
import com.meiji.toutiao.module.news.channel.NewsChannelActivity;
import com.meiji.toutiao.module.news.multi.MultiNewsArticleView;
import com.meiji.toutiao.module.wenda.article.WendaArticleView;
import com.meiji.toutiao.utils.SettingsUtil;

import java.util.ArrayList;
import java.util.List;

import static android.app.Activity.RESULT_OK;

/**
 * Created by Meiji on 2016/12/12.
 */

public class NewsTabLayout extends Fragment {

    private static final String TAG = "NewsTabLayout";
    private static NewsTabLayout instance = null;
    private final int REQUEST_CODE = 1;
    private ViewPager view_pager;
    private BasePagerAdapter adapter;
    private LinearLayout header_layout;
    private NewsChannelDao dao = new NewsChannelDao();

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
        header_layout.setBackgroundColor(SettingsUtil.getInstance().getColor());
    }

    private void initView(View view) {
        TabLayout tab_layout = (TabLayout) view.findViewById(R.id.tab_layout_news);
        view_pager = (ViewPager) view.findViewById(R.id.view_pager_news);

        tab_layout.setupWithViewPager(view_pager);
        tab_layout.setTabMode(TabLayout.MODE_SCROLLABLE);
        ImageView add_channel_iv = (ImageView) view.findViewById(R.id.add_channel_iv);
        add_channel_iv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(), NewsChannelActivity.class);
                startActivityForResult(intent, REQUEST_CODE);
            }
        });
        header_layout = (LinearLayout) view.findViewById(R.id.header_layout);
        header_layout.setBackgroundColor(SettingsUtil.getInstance().getColor());
    }

    /**
     * 初始化 NewsArticleView 数据
     */
    private void initData() {
        List<NewsChannelBean> channelList = dao.query(1);
        List<Fragment> fragmentList = new ArrayList<>();
        if (channelList.size() == 0) {
            dao.addInitData();
            channelList = dao.query(1);
        }
        String[] categoryName = new String[channelList.size()];
        for (int i = 0; i < channelList.size(); i++) {
            if (channelList.get(i).getChannelId().equals("essay_joke")) {
                Fragment jokeContentView = JokeContentView.newInstance();
                fragmentList.add(jokeContentView);
            } else if (channelList.get(i).getChannelId().equals("question_and_answer")) {
                WendaArticleView wendaArticleView = WendaArticleView.newInstance();
                fragmentList.add(wendaArticleView);
            } else {
                Fragment fragment = MultiNewsArticleView.newInstance(channelList.get(i).getChannelId());
                fragmentList.add(fragment);
            }
            categoryName[i] = channelList.get(i).getChannelName();
        }
        adapter = new BasePagerAdapter(getChildFragmentManager(), fragmentList, categoryName);
        view_pager.setAdapter(adapter);
        view_pager.setOffscreenPageLimit(15);
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
//                getActivity().getWindow().setWindowAnimations(R.style.WindowAnimationFadeInOut);
//                getActivity().recreate();
                List<NewsChannelBean> channelList = dao.query(1);
                List<Fragment> fragmentList = new ArrayList<>();
                if (channelList.size() == 0) {
                    dao.addInitData();
                    channelList = dao.query(1);
                }
                String[] categoryName = new String[channelList.size()];
                for (int i = 0; i < channelList.size(); i++) {
                    if (channelList.get(i).getChannelId().equals("essay_joke")) {
                        Fragment jokeContentView = JokeContentView.newInstance();
                        fragmentList.add(jokeContentView);
                    } else if (channelList.get(i).getChannelId().equals("question_and_answer")) {
                        WendaArticleView wendaArticleView = WendaArticleView.newInstance();
                        fragmentList.add(wendaArticleView);
                    } else {
                        Fragment fragment = MultiNewsArticleView.newInstance(channelList.get(i).getChannelId());
                        fragmentList.add(fragment);
                    }
                    categoryName[i] = channelList.get(i).getChannelName();
                }
                adapter.recreateItems(fragmentList, categoryName);
            }
        }
    }
}
