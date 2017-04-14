package com.meiji.toutiao;

import android.app.SearchManager;
import android.app.SearchableInfo;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.res.Configuration;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.view.MenuItemCompat;
import android.support.v7.app.AppCompatDelegate;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.Toast;

import com.meiji.toutiao.media.channel.MediaChannelView;
import com.meiji.toutiao.news.NewsTabLayout;
import com.meiji.toutiao.photo.PhotoTabLayout;
import com.meiji.toutiao.search.SearchView;
import com.meiji.toutiao.settings.SettingsActivity;
import com.meiji.toutiao.utils.SettingsUtil;
import com.meiji.toutiao.video.VideoTabLayout;
import com.meiji.toutiao.view.BottomNavigationViewHelper;

public class MainActivity extends BaseActivity {

    private static final String TAG = "MainActivity";
    private static final String POSITION = "position";
    private static final int FRAGMENT_NEWS = 0;
    private static final int FRAGMENT_PHOTO = 1;
    private static final int FRAGMENT_VIDEO = 2;
    private static final int FRAGMENT_MEDIA = 3;
    private final int REQUEST_CODE = 1;
    private NewsTabLayout newsTabLayout;
    private PhotoTabLayout photoTabLayout;
    private VideoTabLayout videoTabLayout;
    private MediaChannelView mediaChannelView;
    private Toolbar toolbar;
    private BottomNavigationView bottom_navigation;
    private long exitTime;
    private int position;
    private FrameLayout content_main;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initView();

        if (savedInstanceState != null) {
            newsTabLayout = (NewsTabLayout) getSupportFragmentManager().findFragmentByTag(NewsTabLayout.class.getName());
            photoTabLayout = (PhotoTabLayout) getSupportFragmentManager().findFragmentByTag(PhotoTabLayout.class.getName());
            videoTabLayout = (VideoTabLayout) getSupportFragmentManager().findFragmentByTag(VideoTabLayout.class.getName());
            mediaChannelView = (MediaChannelView) getSupportFragmentManager().findFragmentByTag(MediaChannelView.class.getName());
            // 屏幕恢复时取出位置
            showFragment(savedInstanceState.getInt(POSITION));
        } else {
            showFragment(FRAGMENT_NEWS);
        }
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        // 屏幕旋转时记录位置
        outState.putInt(POSITION, position);
    }

    @Override
    protected void onResume() {
        super.onResume();
        bottom_navigation.setBackgroundColor(getResources().getColor(R.color.colorPrimary));
    }

    private void initView() {
        toolbar = (Toolbar) findViewById(R.id.toolbar);
        bottom_navigation = (BottomNavigationView) findViewById(R.id.bottom_navigation);
        BottomNavigationViewHelper.disableShiftMode(bottom_navigation);
        setSupportActionBar(toolbar);
        bottom_navigation.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                switch (item.getItemId()) {
                    case R.id.action_news:
                        showFragment(FRAGMENT_NEWS);
                        break;
                    case R.id.action_photo:
                        showFragment(FRAGMENT_PHOTO);
                        break;
                    case R.id.action_video:
                        showFragment(FRAGMENT_VIDEO);
                        break;
                    case R.id.action_media:
                        showFragment(FRAGMENT_MEDIA);
                        break;
                }
                return true;
            }
        });

        content_main = (FrameLayout) findViewById(R.id.container);
    }

    private void showFragment(int index) {
        FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
        hideFragment(ft);
        position = index;
        switch (index) {
            case FRAGMENT_NEWS:
                toolbar.setTitle(R.string.title_news);
                /**
                 * 如果Fragment为空，就新建一个实例
                 * 如果不为空，就将它从栈中显示出来
                 */
                if (newsTabLayout == null) {
                    newsTabLayout = NewsTabLayout.getInstance();
                    ft.add(R.id.container, newsTabLayout, NewsTabLayout.class.getName());
                } else {
                    ft.show(newsTabLayout);
                }
                break;

            case FRAGMENT_PHOTO:
                toolbar.setTitle(R.string.title_photo);
                if (photoTabLayout == null) {
                    photoTabLayout = PhotoTabLayout.getInstance();
                    ft.add(R.id.container, photoTabLayout, PhotoTabLayout.class.getName());
                } else {
                    ft.show(photoTabLayout);
                }
                break;

            case FRAGMENT_VIDEO:
                toolbar.setTitle(getString(R.string.title_video));
                if (videoTabLayout == null) {
                    videoTabLayout = VideoTabLayout.getInstance();
                    ft.add(R.id.container, videoTabLayout, VideoTabLayout.class.getName());
                } else {
                    ft.show(videoTabLayout);
                }
                break;

            case FRAGMENT_MEDIA:
                toolbar.setTitle(getString(R.string.title_media));
                if (mediaChannelView == null) {
                    mediaChannelView = MediaChannelView.getInstance();
                    ft.add(R.id.container, mediaChannelView, MediaChannelView.class.getName());
                } else {
                    ft.show(mediaChannelView);
                }
        }

        ft.commit();
    }

    private void hideFragment(FragmentTransaction ft) {
        // 如果不为空，就先隐藏起来
        if (newsTabLayout != null) {
            ft.hide(newsTabLayout);
        }
        if (photoTabLayout != null) {
            ft.hide(photoTabLayout);
        }
        if (videoTabLayout != null) {
            ft.hide(videoTabLayout);
        }
        if (mediaChannelView != null) {
            ft.hide(mediaChannelView);
        }
    }

    @Override
    public void onBackPressed() {
        long currentTime = System.currentTimeMillis();
        if ((currentTime - exitTime) < 2000) {
            super.onBackPressed();
        } else {
            Toast.makeText(this, R.string.double_click_exit, Toast.LENGTH_SHORT).show();
            exitTime = currentTime;
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_activity_main, menu);
        setSearchView(menu);

        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int itemId = item.getItemId();
        switch (itemId) {
            case R.id.aciton_setting:
                startActivityForResult(new Intent(this, SettingsActivity.class), REQUEST_CODE);
                break;
            case R.id.action_switch_night_mode:
                int mode = getResources().getConfiguration().uiMode & Configuration.UI_MODE_NIGHT_MASK;
                if (mode == Configuration.UI_MODE_NIGHT_YES) {
                    SettingsUtil.getInstance().setIsNightMode(false);
                    AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO);
                } else {
                    SettingsUtil.getInstance().setIsNightMode(true);
                    AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES);
                }
                getWindow().setWindowAnimations(R.style.WindowAnimationFadeInOut);
                recreate();
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == REQUEST_CODE) {
            if (resultCode == RESULT_OK) {
                getWindow().setWindowAnimations(R.style.WindowAnimationFadeInOut);
                recreate();
            }
        }
    }

    private void setSearchView(Menu menu) {
        final MenuItem searchItem = menu.findItem(R.id.action_search);
        // 关联检索配置与 SearchView
        SearchManager searchManager = (SearchManager) getSystemService(Context.SEARCH_SERVICE);
        android.support.v7.widget.SearchView searchView = (android.support.v7.widget.SearchView) menu.findItem(R.id.action_search).getActionView();
        SearchableInfo searchableInfo = searchManager.getSearchableInfo(
                new ComponentName(getApplicationContext(), SearchView.class));
        searchView.setSearchableInfo(searchableInfo);
        searchView.setQueryHint(getString(R.string.search_hint));
        //searchItem.setActionView(searchView);

        // 按一次返回键关闭searchView
        searchView.setOnQueryTextFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View view, boolean b) {
                if (!b) {
                    searchItem.collapseActionView();
                }
            }
        });

        // 设置监听 当SearchView折叠和扩展时的响应事件
        MenuItemCompat.setOnActionExpandListener(searchItem, new MenuItemCompat.OnActionExpandListener() {
            @Override
            public boolean onMenuItemActionCollapse(MenuItem item) {
                // Do something when action item collapses
                content_main.setVisibility(View.VISIBLE);
                return true;     //Return true to collapse action view
            }

            @Override
            public boolean onMenuItemActionExpand(MenuItem item) {
                // Do something when expanded
                content_main.setVisibility(View.GONE);
                return true;      // Return true to expand action view
            }
        });
    }
}
