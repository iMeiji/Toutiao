package com.meiji.toutiao.module.media.home;

import android.content.Intent;
import android.graphics.PorterDuff;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.Snackbar;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.support.v4.widget.ContentLoadingProgressBar;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;

import com.meiji.toutiao.ErrorAction;
import com.meiji.toutiao.InitApp;
import com.meiji.toutiao.R;
import com.meiji.toutiao.adapter.base.BasePagerAdapter;
import com.meiji.toutiao.api.IMobileMediaApi;
import com.meiji.toutiao.bean.media.MediaProfileBean;
import com.meiji.toutiao.module.base.BaseActivity;
import com.meiji.toutiao.module.media.home.tab.MediaArticleFragment;
import com.meiji.toutiao.module.media.home.tab.MediaVideoFragment;
import com.meiji.toutiao.module.media.home.tab.MediaWendaFragment;
import com.meiji.toutiao.util.RetrofitFactory;
import com.meiji.toutiao.util.SettingUtil;

import java.util.ArrayList;
import java.util.List;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;

/**
 * Created by Meiji on 2017/6/29.
 */

public class MediaHomeActivity extends BaseActivity {

    private static final String ARG_MEDIAID = "mediaId";
    private String mediaId = null;
    private Toolbar toolbar;
    private TabLayout tabLayout;
    private ViewPager viewPager;
    private ContentLoadingProgressBar progressBar;

    public static void launch(String MediaId) {
        InitApp.AppContext.startActivity(new Intent(InitApp.AppContext, MediaHomeActivity.class)
                .putExtra(ARG_MEDIAID, MediaId)
                .addFlags(Intent.FLAG_ACTIVITY_NEW_TASK));
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_media_home);
        initView();
        initData();
    }

    private void initView() {
        toolbar = findViewById(R.id.toolbar);
        toolbar.setBackgroundColor(SettingUtil.getInstance().getColor());

        tabLayout = findViewById(R.id.tab_layout);
        viewPager = findViewById(R.id.view_pager);
        tabLayout.setBackgroundColor(SettingUtil.getInstance().getColor());
        tabLayout.setupWithViewPager(viewPager);
        tabLayout.setTabMode(TabLayout.MODE_SCROLLABLE);

        progressBar = findViewById(R.id.pb_progress);
        int color = SettingUtil.getInstance().getColor();
        progressBar.getIndeterminateDrawable().setColorFilter(color, PorterDuff.Mode.MULTIPLY);
        progressBar.show();

        viewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {
                if (position == 0) {
                    if (slidrInterface != null) {
                        slidrInterface.unlock();
                    }
                } else {
                    if (slidrInterface != null) {
                        slidrInterface.lock();
                    }
                }
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });
    }

    private void initData() {
        Intent intent = getIntent();
        this.mediaId = intent.getStringExtra(ARG_MEDIAID);
        if (TextUtils.isEmpty(mediaId)) {
            onError();
            return;
        }

        RetrofitFactory.getRetrofit().create(IMobileMediaApi.class)
                .getMediaProfile(mediaId)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .as(this.bindAutoDispose())
                .subscribe(bean -> {
                    String name = bean.getData().getName();
                    initToolBar(toolbar, true, name);
                    List<MediaProfileBean.DataBean.TopTabBean> topTab = bean.getData().getTop_tab();
                    if (null != topTab && topTab.size() < 0) {
                        onError();
                        return;
                    }
                    initTabLayout(bean.getData());
                }, throwable -> {
                    onError();
                    ErrorAction.print(throwable);
                });
    }

    private void initTabLayout(MediaProfileBean.DataBean dataBean) {
        List<Fragment> fragmentList = new ArrayList<>();
        List<String> titleList = new ArrayList<>();
        List<MediaProfileBean.DataBean.TopTabBean> topTab = dataBean.getTop_tab();
        for (MediaProfileBean.DataBean.TopTabBean bean : topTab) {
            if (bean.getType().equals("all")) {
                fragmentList.add(MediaArticleFragment.newInstance(dataBean));
                titleList.add(bean.getShow_name());
            }
            if (bean.getType().equals("video")) {
                fragmentList.add(MediaVideoFragment.newInstance(mediaId));
                titleList.add(bean.getShow_name());
            }
            if (bean.getType().equals("wenda")) {
                fragmentList.add(MediaWendaFragment.newInstance(dataBean.getUser_id() + ""));
                titleList.add(bean.getShow_name());
            }
        }
        BasePagerAdapter pagerAdapter = new BasePagerAdapter(getSupportFragmentManager(), fragmentList, titleList);
        viewPager.setAdapter(pagerAdapter);
        viewPager.setOffscreenPageLimit(topTab.size());
        progressBar.hide();
    }

    private void onError() {
        progressBar.hide();
        Snackbar.make(progressBar, getString(R.string.error), Snackbar.LENGTH_INDEFINITE).show();
    }
}
