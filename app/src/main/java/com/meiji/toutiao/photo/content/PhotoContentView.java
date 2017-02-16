package com.meiji.toutiao.photo.content;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.view.ViewPager;
import android.support.v7.app.ActionBar;
import android.support.v7.widget.Toolbar;
import android.widget.TextView;

import com.meiji.toutiao.BaseActivity;
import com.meiji.toutiao.R;
import com.meiji.toutiao.adapter.photo.PhotoContentAdapter;
import com.meiji.toutiao.bean.photo.PhotoArticleBean;
import com.meiji.toutiao.bean.photo.PhotoGalleryBean;
import com.meiji.toutiao.view.ViewPagerFixed;

/**
 * Created by Meiji on 2017/2/16.
 */

public class PhotoContentView extends BaseActivity implements IPhotoContent.View, ViewPager.OnPageChangeListener {

    public static final String TAG = "PhotoContentView";
    private Toolbar toolbar;
    private TextView tv_hint;
    private TextView tv_save;
    private ViewPagerFixed viewPager;
    private IPhotoContent.Presenter presenter;
    private ActionBar actionBar;
    private String shareUrl;
    private String shareTitle;
    private PhotoContentAdapter adapter;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.photo_content_main);
        presenter = new PhotoContentPresenter(this);
        initView();
        initData();
    }

    private void initData() {
        Intent intent = getIntent();
        PhotoArticleBean.DataBean dataBean = intent.getParcelableExtra(TAG);
        presenter.doRequestData(dataBean);
        shareUrl = dataBean.getDisplay_url();
        shareTitle = dataBean.getTitle();
        actionBar.setTitle(dataBean.getMedia_name());
    }

    private void initView() {
        toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        actionBar = getSupportActionBar();
        if (actionBar != null) {
            actionBar.setDisplayHomeAsUpEnabled(true);
        }
        tv_hint = (TextView) findViewById(R.id.tv_hint);
        tv_save = (TextView) findViewById(R.id.tv_save);
        viewPager = (ViewPagerFixed) findViewById(R.id.viewPager);
    }

    @Override
    public void onSetImageBrwoser(PhotoGalleryBean bean, int position) {
        if (adapter == null) {
            adapter = new PhotoContentAdapter(this, bean);
            viewPager.setAdapter(adapter);
            viewPager.setCurrentItem(position);
            viewPager.addOnPageChangeListener(this);
            tv_hint.setText(position + 1 + "/" + bean.getCount());
        }
    }

    @Override
    public void onFail() {

    }

    @Override
    public void onShowRefreshing() {

    }

    @Override
    public void onHideRefreshing() {

    }

    @Override
    public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

    }

    @Override
    public void onPageSelected(int position) {
        tv_hint.setText(position + 1 + "/" + presenter.getImageCount());
    }

    @Override
    public void onPageScrollStateChanged(int state) {

    }
}
