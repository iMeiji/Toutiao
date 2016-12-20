package com.meiji.toutiao;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v4.app.Fragment;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.widget.Toast;

import com.meiji.toutiao.news.article.ArticleTabLayout;
import com.meiji.toutiao.utils.ColorUtil;

public class MainActivity extends BaseActivity {

    private Toolbar toolbar;
    private BottomNavigationView bottom_navigation;
    private long exitTime;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initView();
        replaceFragment(ArticleTabLayout.getInstance(), getResources().getColor(R.color.colorPrimary));
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
                        replaceFragment(ArticleTabLayout.getInstance(), getResources().getColor(R.color.colorPrimary));
                        break;
                    case R.id.action_schedules:
//                        replaceFragment(ArticleView.newInstance("__all__"), getResources().getColor(R.color.blue));
                        break;
                    case R.id.action_music:
                        break;
                }
                return true;
            }
        });
    }

    private void replaceFragment(Fragment fragment, int color) {
        ColorUtil.setColor(color);
        bottom_navigation.setBackgroundColor(ColorUtil.getColor());
        getSupportFragmentManager().beginTransaction().replace(R.id.content_main, fragment).commit();
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
