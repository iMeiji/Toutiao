package com.meiji.toutiao.setting;


import android.app.ActivityManager;
import android.app.Fragment;
import android.app.FragmentTransaction;
import android.content.Intent;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.ColorInt;
import android.support.annotation.NonNull;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.view.Menu;
import android.view.MenuItem;

import com.afollestad.materialdialogs.color.CircleView;
import com.afollestad.materialdialogs.color.ColorChooserDialog;
import com.meiji.toutiao.IntentAction;
import com.meiji.toutiao.R;
import com.meiji.toutiao.module.base.BaseActivity;
import com.meiji.toutiao.util.SettingUtil;

public class SettingActivity extends BaseActivity implements ColorChooserDialog.ColorCallback {

    public static final String EXTRA_SHOW_FRAGMENT = "show_fragment";
    public static final String EXTRA_SHOW_FRAGMENT_ARGUMENTS = "show_fragment_args";
    public static final String EXTRA_SHOW_FRAGMENT_TITLE = "show_fragment_title";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings);

        String initFragment = getIntent().getStringExtra(EXTRA_SHOW_FRAGMENT);
        Bundle initArguments = getIntent().getBundleExtra(EXTRA_SHOW_FRAGMENT_ARGUMENTS);
        String initTitle = getIntent().getStringExtra(EXTRA_SHOW_FRAGMENT_TITLE);

        if (TextUtils.isEmpty(initFragment)) {
            setupFragment(GeneralPreferenceFragment.class.getName(), initArguments);
        } else {
            setupFragment(initFragment, initArguments);
        }

        Toolbar toolbar = findViewById(R.id.toolbar);
        initToolBar(toolbar, true, TextUtils.isEmpty(initTitle) ? getString(R.string.title_settings) : initTitle);
    }

    private void setupFragment(String fragmentName, Bundle args) {
        Fragment fragment = Fragment.instantiate(this, fragmentName, args);
        FragmentTransaction transaction = getFragmentManager().beginTransaction();
        transaction.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_FADE);
        transaction.replace(R.id.container, fragment);
        transaction.commitAllowingStateLoss();
    }

    public Intent onBuildStartFragmentIntent(String fragmentName, Bundle args, String title) {
        Intent intent = new Intent(Intent.ACTION_MAIN);
        intent.setClass(this, getClass());
        intent.putExtra(EXTRA_SHOW_FRAGMENT, fragmentName);
        intent.putExtra(EXTRA_SHOW_FRAGMENT_ARGUMENTS, args);
        intent.putExtra(EXTRA_SHOW_FRAGMENT_TITLE, title);
        return intent;
    }

    public void startWithFragment(String fragmentName, Bundle args,
                                  Fragment resultTo, int resultRequestCode, String title) {
        Intent intent = onBuildStartFragmentIntent(fragmentName, args, title);
        if (resultTo == null) {
            startActivity(intent);
        } else {
            resultTo.startActivityForResult(intent, resultRequestCode);
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_about, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int itemId = item.getItemId();
        if (itemId == R.id.action_share) {
            IntentAction.send(SettingActivity.this, getString(R.string.share_app_text) + getString(R.string.source_code_url));
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onColorSelection(@NonNull ColorChooserDialog dialog, @ColorInt int selectedColor) {
        if (getSupportActionBar() != null)
            getSupportActionBar().setBackgroundDrawable(new ColorDrawable(selectedColor));
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            // 状态栏上色
            getWindow().setStatusBarColor(CircleView.shiftColorDown(selectedColor));
            // 最近任务栏上色
            ActivityManager.TaskDescription tDesc = new ActivityManager.TaskDescription(
                    getString(R.string.app_name),
                    BitmapFactory.decodeResource(getResources(), R.mipmap.ic_launcher_rect),
                    selectedColor);
            setTaskDescription(tDesc);
            // 导航栏上色
            if (SettingUtil.getInstance().getNavBar()) {
                getWindow().setNavigationBarColor(CircleView.shiftColorDown(selectedColor));
            } else {
                getWindow().setNavigationBarColor(Color.BLACK);
            }
        }
        if (!dialog.isAccentMode()) {
            SettingUtil.getInstance().setColor(selectedColor);
        }
    }

    @Override
    public void onColorChooserDismissed(@NonNull ColorChooserDialog dialog) {

    }
}
