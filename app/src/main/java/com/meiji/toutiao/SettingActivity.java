package com.meiji.toutiao;


import android.app.ActivityManager;
import android.app.Fragment;
import android.app.TimePickerDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.preference.Preference;
import android.preference.PreferenceFragment;
import android.preference.PreferenceManager;
import android.support.annotation.ColorInt;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.AppCompatCheckBox;
import android.support.v7.widget.AppCompatTextView;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import android.widget.TimePicker;

import com.afollestad.materialdialogs.color.CircleView;
import com.afollestad.materialdialogs.color.ColorChooserDialog;
import com.meiji.toutiao.module.base.BaseActivity;
import com.meiji.toutiao.util.CacheDataManager;
import com.meiji.toutiao.util.SettingUtil;
import com.meiji.toutiao.widget.IconPreference;

import de.psdev.licensesdialog.LicensesDialog;
import de.psdev.licensesdialog.licenses.ApacheSoftwareLicense20;
import de.psdev.licensesdialog.licenses.BSD3ClauseLicense;
import de.psdev.licensesdialog.licenses.MITLicense;
import de.psdev.licensesdialog.model.Notice;
import de.psdev.licensesdialog.model.Notices;

public class SettingActivity extends BaseActivity implements ColorChooserDialog.ColorCallback {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings);
        initView();
        if (savedInstanceState == null) {
            getFragmentManager().beginTransaction()
                    .add(R.id.container, GeneralPreferenceFragment.newInstance())
                    .commit();
        }
    }

    private void initView() {
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        initToolBar(toolbar, true, getString(R.string.title_settings));
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
            Intent shareIntent = new Intent()
                    .setAction(Intent.ACTION_SEND)
                    .setType("text/plain")
                    .putExtra(Intent.EXTRA_TEXT, getString(R.string.share_app_text) + getString(R.string.source_code_url));
            startActivity(Intent.createChooser(shareIntent, getString(R.string.share_to)));
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

    public static class GeneralPreferenceFragment extends PreferenceFragment implements SharedPreferences.OnSharedPreferenceChangeListener {

        private IconPreference color;

        public static GeneralPreferenceFragment newInstance() {
            return new GeneralPreferenceFragment();
        }

        @Override
        public void onCreate(@Nullable Bundle savedInstanceState) {
            super.onCreate(savedInstanceState);
            addPreferencesFromResource(R.xml.pref_general);
            setHasOptionsMenu(true);
            setText();

            findPreference("auto_nightMode").setOnPreferenceClickListener(new Preference.OnPreferenceClickListener() {
                @Override
                public boolean onPreferenceClick(Preference preference) {
                    getActivity().getFragmentManager().beginTransaction()
                            .add(R.id.container, AutoNightModeFragment.newInstance())
                            .hide(GeneralPreferenceFragment.this)
                            .addToBackStack("")
                            .commit();
                    return true;
                }
            });

            findPreference("custom_icon").setOnPreferenceChangeListener(new Preference.OnPreferenceChangeListener() {
                @Override
                public boolean onPreferenceChange(Preference preference, Object newValue) {

                    int selectValue = Integer.parseInt((String) newValue);
                    int drawable = Constant.ICONS_DRAWABLES[selectValue];

                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                        ActivityManager.TaskDescription tDesc = new ActivityManager.TaskDescription(
                                getString(R.string.app_name),
                                BitmapFactory.decodeResource(getResources(), drawable),
                                SettingUtil.getInstance().getColor());
                        getActivity().setTaskDescription(tDesc);
                    }

                    return true;
                }
            });

            findPreference("color").setOnPreferenceClickListener(new Preference.OnPreferenceClickListener() {
                @Override
                public boolean onPreferenceClick(Preference preference) {
                    final SettingActivity context = (SettingActivity) getActivity();
                    new ColorChooserDialog.Builder(context, R.string.choose_theme_color)
                            .backButton(R.string.back)
                            .cancelButton(R.string.cancel)
                            .doneButton(R.string.done)
                            .customButton(R.string.custom)
                            .presetsButton(R.string.back)
                            .allowUserColorInputAlpha(false)
                            .show();
                    return false;
                }
            });

            color = (IconPreference) findPreference("color");

            findPreference("nav_bar").setOnPreferenceClickListener(new Preference.OnPreferenceClickListener() {
                @Override
                public boolean onPreferenceClick(Preference preference) {
                    int color = SettingUtil.getInstance().getColor();
                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                        if (SettingUtil.getInstance().getNavBar()) {
                            getActivity().getWindow().setNavigationBarColor(CircleView.shiftColorDown(CircleView.shiftColorDown(color)));
                        } else {
                            getActivity().getWindow().setNavigationBarColor(Color.BLACK);
                        }
                    }
                    return false;
                }
            });

            findPreference("clearCache").setOnPreferenceClickListener(new Preference.OnPreferenceClickListener() {
                @Override
                public boolean onPreferenceClick(Preference preference) {
                    CacheDataManager.clearAllCache(getActivity());
                    Snackbar.make(getView(), R.string.clear_cache_successfully, Snackbar.LENGTH_SHORT).show();
                    setText();
                    return false;
                }
            });

            try {
                String version = "当前版本 " + getActivity().getPackageManager().getPackageInfo(getActivity().getPackageName(), 0).versionName;
                findPreference("version").setSummary(version);
            } catch (PackageManager.NameNotFoundException e) {
                e.printStackTrace();
            }

            findPreference("changelog").setOnPreferenceClickListener(new Preference.OnPreferenceClickListener() {
                @Override
                public boolean onPreferenceClick(Preference preference) {
                    getActivity().startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse(getString(R.string.changelog_url))));
                    return false;
                }
            });

            findPreference("licenses").setOnPreferenceClickListener(new Preference.OnPreferenceClickListener() {
                @Override
                public boolean onPreferenceClick(Preference preference) {
                    createLicenseDialog();
                    return false;
                }
            });

            findPreference("sourceCode").setOnPreferenceClickListener(new Preference.OnPreferenceClickListener() {
                @Override
                public boolean onPreferenceClick(Preference preference) {
                    getActivity().startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse(getString(R.string.source_code_url))));
                    return false;
                }
            });

            findPreference("copyRight").setOnPreferenceClickListener(new Preference.OnPreferenceClickListener() {
                @Override
                public boolean onPreferenceClick(Preference preference) {
                    new AlertDialog.Builder(getActivity())
                            .setTitle(R.string.copyright)
                            .setMessage(R.string.copyright_content)
                            .setCancelable(true)
                            .show();
                    return false;
                }
            });
        }

        private void setText() {
            try {
                findPreference("clearCache").setSummary(CacheDataManager.getTotalCacheSize(getActivity()));
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

        private void createLicenseDialog() {
            Notices notices = new Notices();
            notices.addNotice(new Notice("PhotoView", "https://github.com/chrisbanes/PhotoView", "Copyright 2017 Chris Banes", new ApacheSoftwareLicense20()));
            notices.addNotice(new Notice("OkHttp", "https://github.com/square/okhttp", "Copyright 2016 Square, Inc.", new ApacheSoftwareLicense20()));
            notices.addNotice(new Notice("Gson", "https://github.com/google/gson", "Copyright 2008 Google Inc.", new ApacheSoftwareLicense20()));
            notices.addNotice(new Notice("Glide", "https://github.com/bumptech/glide", "Sam Judd - @sjudd on GitHub, @samajudd on Twitter", new ApacheSoftwareLicense20()));
            notices.addNotice(new Notice("Stetho", "https://github.com/facebook/stetho", "Copyright (c) 2015, Facebook, Inc. All rights reserved.", new BSD3ClauseLicense()));
            notices.addNotice(new Notice("PersistentCookieJar", "https://github.com/franmontiel/PersistentCookieJar", "Copyright 2016 Francisco José Montiel Navarro", new ApacheSoftwareLicense20()));
            notices.addNotice(new Notice("jsoup", "https://jsoup.org", "Copyright © 2009 - 2016 Jonathan Hedley (jonathan@hedley.net)", new MITLicense()));

            new LicensesDialog.Builder(getActivity())
                    .setNotices(notices)
                    .setIncludeOwnLicense(true)
                    .build()
                    .show();
        }

        @Override
        public void onResume() {
            super.onResume();
            getPreferenceScreen().getSharedPreferences().registerOnSharedPreferenceChangeListener(this);
        }

        @Override
        public void onPause() {
            super.onPause();
            getPreferenceScreen().getSharedPreferences().unregisterOnSharedPreferenceChangeListener(this);
        }

        @Override
        public void onSharedPreferenceChanged(SharedPreferences sharedPreferences, String key) {
            if (key.equals("color")) {
                color.setView();
            }
            if (key.equals("slidable")) {
                getActivity().recreate();
            }
        }
    }

    public static class AutoNightModeFragment extends Fragment implements SharedPreferences.OnSharedPreferenceChangeListener {

        private static final String TAG = "AutoNightModeFragment";
        private AppCompatCheckBox checkbox;
        private RelativeLayout checkboxLayout;
        private AppCompatTextView autoNightMode;

        private RelativeLayout nightStartLayout;
        private AppCompatTextView nightStartTitle;
        private AppCompatTextView nightStartSummary;

        private RelativeLayout dayStartLayout;
        private AppCompatTextView dayStartTitle;
        private AppCompatTextView dayStartSummary;

        private String nightStartHour;
        private String nightStartMinute;
        private String dayStartHour;
        private String dayStartMinute;
        private SettingUtil settingUtil = SettingUtil.getInstance();

        public static AutoNightModeFragment newInstance() {
            return new AutoNightModeFragment();
        }

        @Nullable
        @Override
        public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
            View view = inflater.inflate(R.layout.fragment_setting_auto_night, container, false);
            initView(view);
            return view;
        }

        private void initView(View view) {
            this.autoNightMode = view.findViewById(R.id.tv_auto_night_mode);
            this.checkboxLayout = view.findViewById(R.id.checkbox_layout);
            this.nightStartTitle = view.findViewById(R.id.tv_night_start_title);
            this.nightStartSummary = view.findViewById(R.id.tv_night_start_summary);
            this.nightStartLayout = view.findViewById(R.id.night_start_layout);
            this.dayStartTitle = view.findViewById(R.id.tv_day_start_title);
            this.dayStartSummary = view.findViewById(R.id.tv_day_start_summary);
            this.dayStartLayout = view.findViewById(R.id.day_start_layout);
            this.checkbox = view.findViewById(R.id.checkbox);

            checkbox.setChecked(settingUtil.getIsAutoNightMode());

            checkboxLayout.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    checkbox.setChecked(!checkbox.isChecked());
                    settingUtil.setIsAutoNightMode(checkbox.isChecked());
                }
            });

            setText();

            nightStartLayout.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    TimePickerDialog dialog = new TimePickerDialog(getActivity(),
                            new TimePickerDialog.OnTimeSetListener() {
                                @Override
                                public void onTimeSet(TimePicker timePicker, int hour, int minute) {
                                    settingUtil.setNightStartHour(hour > 9 ? "" + hour : "0" + hour);
                                    settingUtil.setNightStartMinute(minute > 9 ? "" + minute : "0" + minute);
                                }
                            }, Integer.parseInt(nightStartHour), Integer.parseInt(nightStartMinute), true);
                    dialog.show();
                    dialog.getButton(DialogInterface.BUTTON_POSITIVE).setText(R.string.done);
                    dialog.getButton(DialogInterface.BUTTON_NEGATIVE).setText(R.string.cancel);
                }
            });

            dayStartLayout.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    TimePickerDialog dialog = new TimePickerDialog(getActivity(),
                            new TimePickerDialog.OnTimeSetListener() {
                                @Override
                                public void onTimeSet(TimePicker timePicker, int hour, int minute) {
                                    settingUtil.setDayStartHour(hour > 9 ? "" + hour : "0" + hour);
                                    settingUtil.setDayStartMinute(minute > 9 ? "" + minute : "0" + minute);
                                }
                            }, Integer.parseInt(dayStartHour), Integer.parseInt(dayStartMinute), true);
                    dialog.show();
                    dialog.getButton(DialogInterface.BUTTON_POSITIVE).setText(R.string.done);
                    dialog.getButton(DialogInterface.BUTTON_NEGATIVE).setText(R.string.cancel);
                }
            });
        }

        private void setText() {

            nightStartHour = settingUtil.getNightStartHour();
            nightStartMinute = settingUtil.getNightStartMinute();
            dayStartHour = settingUtil.getDayStartHour();
            dayStartMinute = settingUtil.getDayStartMinute();

            nightStartSummary.setText(nightStartHour + ":" + nightStartMinute);
            dayStartSummary.setText(dayStartHour + ":" + dayStartMinute);

            if (checkbox.isChecked()) {
                nightStartLayout.setEnabled(true);
                dayStartLayout.setEnabled(true);
                int color = autoNightMode.getCurrentTextColor();
                nightStartTitle.setTextColor(color);
                nightStartSummary.setTextColor(color);
                dayStartTitle.setTextColor(color);
                dayStartSummary.setTextColor(color);
            } else {
                nightStartLayout.setEnabled(false);
                dayStartLayout.setEnabled(false);
                nightStartTitle.setTextColor(getResources().getColor(R.color.Grey500));
                nightStartSummary.setTextColor(getResources().getColor(R.color.Grey500));
                dayStartTitle.setTextColor(getResources().getColor(R.color.Grey500));
                dayStartSummary.setTextColor(getResources().getColor(R.color.Grey500));
            }
        }

        @Override
        public void onResume() {
            super.onResume();
            PreferenceManager.getDefaultSharedPreferences(getActivity()).registerOnSharedPreferenceChangeListener(this);
        }

        @Override
        public void onPause() {
            super.onPause();
            PreferenceManager.getDefaultSharedPreferences(getActivity()).unregisterOnSharedPreferenceChangeListener(this);
        }

        @Override
        public void onSharedPreferenceChanged(SharedPreferences sharedPreferences, String s) {
            setText();
        }
    }
}
