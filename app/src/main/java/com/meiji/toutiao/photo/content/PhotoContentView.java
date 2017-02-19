package com.meiji.toutiao.photo.content;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.Snackbar;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v4.view.ViewPager;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.ActionBar;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.meiji.toutiao.BaseActivity;
import com.meiji.toutiao.R;
import com.meiji.toutiao.adapter.photo.PhotoContentAdapter;
import com.meiji.toutiao.bean.photo.PhotoArticleBean;
import com.meiji.toutiao.bean.photo.PhotoGalleryBean;
import com.meiji.toutiao.view.ViewPagerFixed;

/**
 * Created by Meiji on 2017/2/16.
 */

public class PhotoContentView extends BaseActivity implements IPhotoContent.View, ViewPager.OnPageChangeListener, View.OnClickListener {

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
    private SwipeRefreshLayout refresh_layout;

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
        tv_save.setOnClickListener(this);
        viewPager = (ViewPagerFixed) findViewById(R.id.viewPager);

        refresh_layout = (SwipeRefreshLayout) findViewById(R.id.refresh_layout);
    }

    @Override
    public void onSetImageBrowser(PhotoGalleryBean bean, int position) {
        if (adapter == null) {
            adapter = new PhotoContentAdapter(this, bean, this);
            viewPager.setAdapter(adapter);
            viewPager.setCurrentItem(position);
            viewPager.addOnPageChangeListener(this);
            tv_hint.setText(position + 1 + "/" + bean.getCount());
        }
    }

    @Override
    public void onFail() {
        Snackbar.make(getCurrentFocus(), R.string.network_error, Snackbar.LENGTH_SHORT).show();
    }

    @Override
    public void onShowRefreshing() {
        refresh_layout.setRefreshing(true);
        refresh_layout.setEnabled(true);
    }

    @Override
    public void onHideRefreshing() {
        refresh_layout.setRefreshing(false);
        refresh_layout.setEnabled(false);
    }

    @Override
    public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

    }

    @Override
    public void onPageSelected(int position) {
        presenter.doSetPosition(position);
        tv_hint.setText(position + 1 + "/" + presenter.doGetImageCount());
    }

    @Override
    public void onPageScrollStateChanged(int state) {

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.photo_content_main, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        switch (id) {
            case R.id.photo_content_comment:
                presenter.doGetComment();
                break;

            case R.id.photo_content_follow:
                break;

            case R.id.photo_content_share:
                Intent shareIntent = new Intent()
                        .setAction(Intent.ACTION_SEND)
                        .setType("text/plain")
                        .putExtra(Intent.EXTRA_TEXT, shareTitle + "\n" + shareUrl);
                startActivity(Intent.createChooser(shareIntent, getString(R.string.share_to)));
                break;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onClick(View view) {
        int id = view.getId();
        switch (id) {
            case R.id.tv_save:
                // 运行时权限处理
                if (ContextCompat.checkSelfPermission(this, Manifest.permission.WRITE_EXTERNAL_STORAGE)
                        != PackageManager.PERMISSION_GRANTED) {

                    ActivityCompat.requestPermissions(this,
                            new String[]{"需要存储权限保存图片"}
                            , 1);
                } else {
                    presenter.doSaveImage();
                }
        }
    }


    @Override
    public void onSaveImageSuccess() {
        Toast.makeText(this, "保存成功", Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {

        if (requestCode == 1) {
            if (grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                presenter.doSaveImage();
            } else {
                // Permission Denied
                Toast.makeText(this, "没有权限", Toast.LENGTH_SHORT).show();
            }
            return;
        }
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
    }
}
