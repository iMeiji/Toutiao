package com.meiji.toutiao;

import android.graphics.drawable.ColorDrawable;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.widget.Toast;

import com.afollestad.materialdialogs.color.CircleView;
import com.meiji.toutiao.media.MediaView;
import com.meiji.toutiao.news.article.ArticleTabLayout;
import com.meiji.toutiao.utils.ColorUtil;

public class MainActivity extends BaseActivity {

    private static final String POSITION = "position";
    private static final int FRAGMENT_NEWS = 0;
    private static final int FRAGMENT_MEDIA = 1;
    private Toolbar toolbar;
    private BottomNavigationView bottom_navigation;
    private long exitTime;
    private FragmentManager fragmentManager;
    private ArticleTabLayout articleTabLayout;
    private MediaView mediaView;
    private int position;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initView();
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        // 屏幕旋转时记录位置
        outState.putInt(POSITION, position);
    }

    @Override
    protected void onRestoreInstanceState(Bundle savedInstanceState) {
        // 屏幕恢复时取出位置
        showFragment(savedInstanceState.getInt(POSITION));
        super.onRestoreInstanceState(savedInstanceState);
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
                    case R.id.action_schedules:
                        showFragment(FRAGMENT_MEDIA);
                        break;
                    case R.id.action_music:
                        break;
                }
                return true;
            }
        });

        fragmentManager = getSupportFragmentManager();
        showFragment(FRAGMENT_NEWS);
    }

    private void showFragment(int index) {
        FragmentTransaction ft = fragmentManager.beginTransaction();
        hideFragment(ft);
        position = index;
        switch (index) {
            case FRAGMENT_NEWS:
                toolbar.setTitle("NEWS");
                /**
                 * 如果Fragment为空，就新建一个实例
                 * 如果不为空，就将它从栈中显示出来
                 */
                if (articleTabLayout == null) {
                    articleTabLayout = ArticleTabLayout.getInstance();
                    ft.add(R.id.content_main, articleTabLayout);
                } else {
                    ft.show(articleTabLayout);
                }
                setColor(getResources().getColor(R.color.colorPrimary));
                break;

            case FRAGMENT_MEDIA:
                toolbar.setTitle("MEDIA");
                if (mediaView == null) {
                    mediaView = MediaView.getInstance();
                    ft.add(R.id.content_main, mediaView);
                } else {
                    ft.show(mediaView);
                }
                setColor(getResources().getColor(R.color.blue));
                break;

        }

        ft.commit();
    }

    private void hideFragment(FragmentTransaction ft) {
        // 如果不为空，就先隐藏起来
        if (articleTabLayout != null) {
            ft.hide(articleTabLayout);
        }
        if (mediaView != null) {
            ft.hide(mediaView);
        }
    }

    private void setColor(int color) {
        ColorUtil.setColor(this, color);
        bottom_navigation.setBackgroundColor(color);
        if (getSupportActionBar() != null)
            getSupportActionBar().setBackgroundDrawable(new ColorDrawable(color));
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            getWindow().setStatusBarColor(CircleView.shiftColorDown(color));
            getWindow().setNavigationBarColor(color);
        }
    }

    private void replaceFragment(Fragment fragment, int color, String tag) {
        ColorUtil.setColor(this, color);
        bottom_navigation.setBackgroundColor(ColorUtil.getColor(this));
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
}
