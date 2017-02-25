package com.meiji.toutiao;

import android.app.SearchManager;
import android.app.SearchableInfo;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.graphics.drawable.ColorDrawable;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.view.MenuItemCompat;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.Toast;

import com.afollestad.materialdialogs.color.CircleView;
import com.meiji.toutiao.news.NewsTabLayout;
import com.meiji.toutiao.other.OtherTabLayout;
import com.meiji.toutiao.photo.PhotoTabLayout;
import com.meiji.toutiao.search.SearchView;
import com.meiji.toutiao.utils.ColorUtil;

public class MainActivity extends BaseActivity {

    private static final String TAG = "MainActivity";
    private static final String POSITION = "position";
    private static final int FRAGMENT_NEWS = 0;
    private static final int FRAGMENT_OTHER = 1;
    private static final int FRAGMENT_MEDIA = 2;

    private NewsTabLayout newsTabLayout;
    private OtherTabLayout otherTabLayout;
    private PhotoTabLayout photoTabLayout;

    private Toolbar toolbar;
    private BottomNavigationView bottom_navigation;
    private long exitTime;
    private int position;
    private FragmentManager fragmentManager;
    private FrameLayout content_main;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initView();

//        if (savedInstanceState != null) {

//            newsTabLayout = (NewsTabLayout) fragmentManager.getFragment(savedInstanceState, "newsTabLayout");
//            otherTabLayout = (OtherTabLayout) fragmentManager.getFragment(savedInstanceState, "otherTabLayout");
//            photoTabLayout = (PhotoTabLayout) fragmentManager.getFragment(savedInstanceState, "photoTabLayout");
//            Log.d(TAG, "onCreate: ");
//        }
//        else {
//            newsTabLayout = NewsTabLayout.getInstance();
//            otherTabLayout = OtherTabLayout.getInstance();
//            photoTabLayout = PhotoTabLayout.getInstance();
//        }

//        if (!newsTabLayout.isAdded()) {
//            fragmentManager.beginTransaction()
//                    .add(R.id.content_main, newsTabLayout, "newsTabLayout")
//                    .commit();
//        }
//
//        if (!otherTabLayout.isAdded()) {
//            getSupportFragmentManager().beginTransaction()
//                    .add(R.id.content_main, otherTabLayout, "otherTabLayout")
//                    .commit();
//        }
//
//        if (!photoTabLayout.isAdded()) {
//            getSupportFragmentManager().beginTransaction()
//                    .add(R.id.content_main, photoTabLayout, "photoTabLayout")
//                    .commit();
//        }

    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        // 屏幕旋转时记录位置
        outState.putInt(POSITION, position);
//        if (newsTabLayout != null && newsTabLayout.isAdded()) {
//            fragmentManager.putFragment(outState, "newsTabLayout", newsTabLayout);
//        }
//        if (otherTabLayout != null && otherTabLayout.isAdded()) {
//            fragmentManager.putFragment(outState, "otherTabLayout", otherTabLayout);
//        }
//        if (photoTabLayout != null) {
//            getSupportFragmentManager().putFragment(outState, "photoTabLayout", photoTabLayout);
//        }
    }

    @Override
    protected void onRestoreInstanceState(Bundle savedInstanceState) {
        super.onRestoreInstanceState(savedInstanceState);
//        newsTabLayout = (NewsTabLayout) fragmentManager.getFragment(savedInstanceState, "newsTabLayout");
//        otherTabLayout = (OtherTabLayout) fragmentManager.getFragment(savedInstanceState, "otherTabLayout");
//        photoTabLayout = (PhotoTabLayout) getSupportFragmentManager().getFragment(savedInstanceState, "photoTabLayout");
        // 屏幕恢复时取出位置
        showFragment(savedInstanceState.getInt(POSITION));
    }

    private void initView() {
        toolbar = (Toolbar) findViewById(R.id.toolbar);
        bottom_navigation = (BottomNavigationView) findViewById(R.id.bottom_navigation);
        setSupportActionBar(toolbar);
        bottom_navigation.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                switch (item.getItemId()) {
                    case R.id.action_news:
                        showFragment(FRAGMENT_NEWS);
                        break;
                    case R.id.action_other:
                        showFragment(FRAGMENT_OTHER);
                        break;
                    case R.id.action_photo:
                        showFragment(FRAGMENT_MEDIA);
                        break;
                }
                return true;
            }
        });

        showFragment(FRAGMENT_NEWS);
        content_main = (FrameLayout) findViewById(R.id.content_main);
    }

    private void showFragment(int index) {
        FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
        hideFragment(ft);
        position = index;
        switch (index) {
            case FRAGMENT_NEWS:
                toolbar.setTitle(getResources().getString(R.string.text_news));
                /**
                 * 如果Fragment为空，就新建一个实例
                 * 如果不为空，就将它从栈中显示出来
                 */
                if (newsTabLayout == null) {
                    newsTabLayout = NewsTabLayout.getInstance();
                    ft.add(R.id.content_main, newsTabLayout);
                } else {
                    ft.show(newsTabLayout);
                }
                setColor(getResources().getColor(R.color.colorPrimary));
                break;

            case FRAGMENT_OTHER:
                toolbar.setTitle(getResources().getString(R.string.text_other));
                if (otherTabLayout == null) {
                    otherTabLayout = OtherTabLayout.getInstance();
                    ft.add(R.id.content_main, otherTabLayout);
                } else {
                    ft.show(otherTabLayout);
                }
                setColor(getResources().getColor(R.color.colorPrimary));
                break;

            case FRAGMENT_MEDIA:
                toolbar.setTitle(getResources().getString(R.string.text_photo));
                if (photoTabLayout == null) {
                    photoTabLayout = PhotoTabLayout.getInstance();
                    ft.add(R.id.content_main, photoTabLayout);
                } else {
                    ft.show(photoTabLayout);
                }
                setColor(getResources().getColor(R.color.colorPrimary));
                break;
        }

        ft.commit();
    }

    private void hideFragment(FragmentTransaction ft) {
        // 如果不为空，就先隐藏起来
        if (newsTabLayout != null) {
            ft.hide(newsTabLayout);
        }
        if (otherTabLayout != null) {
            ft.hide(otherTabLayout);
        }
        if (photoTabLayout != null) {
            ft.hide(photoTabLayout);
        }
    }

    private void setColor(int color) {
        ColorUtil.setColor(color);
        //bottom_navigation.setBackgroundColor(color);
        if (getSupportActionBar() != null)
            getSupportActionBar().setBackgroundDrawable(new ColorDrawable(color));
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            getWindow().setStatusBarColor(CircleView.shiftColorDown(color));
            getWindow().setNavigationBarColor(CircleView.shiftColorDown(color));
        }
    }

    private void replaceFragment(Fragment fragment, int color, String tag) {
        ColorUtil.setColor(color);
        //bottom_navigation.setBackgroundColor(ColorUtil.getColor());
        getSupportFragmentManager().beginTransaction().replace(R.id.content_main, fragment).addToBackStack(null).commit();
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
        getMenuInflater().inflate(R.menu.activity_main, menu);
        setSearchView(menu);

        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int itemId = item.getItemId();
        switch (itemId) {
            case R.id.aciton_setting:
                startActivity(new Intent(this, SettingsActivity.class));
                break;
            /*case R.id.action_switch_mode:
                int mode = getResources().getConfiguration().uiMode & Configuration.UI_MODE_NIGHT_MASK;
                if (mode == Configuration.UI_MODE_NIGHT_YES) {
                    AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO);
                } else {
                    AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES);
                }
                recreate();*/
        }
        return super.onOptionsItemSelected(item);
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
