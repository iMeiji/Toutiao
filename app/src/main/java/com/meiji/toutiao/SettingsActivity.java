package com.meiji.toutiao;


import android.content.Intent;
import android.content.pm.PackageManager;
import android.content.res.Configuration;
import android.net.Uri;
import android.os.Bundle;
import android.support.design.widget.Snackbar;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.app.AppCompatDelegate;
import android.support.v7.preference.Preference;
import android.support.v7.preference.PreferenceFragmentCompat;
import android.view.MenuItem;

import com.bumptech.glide.Glide;
import com.meiji.toutiao.utils.SettingsUtil;

import de.psdev.licensesdialog.LicensesDialog;
import de.psdev.licensesdialog.licenses.ApacheSoftwareLicense20;
import de.psdev.licensesdialog.licenses.BSD3ClauseLicense;
import de.psdev.licensesdialog.licenses.MITLicense;
import de.psdev.licensesdialog.model.Notice;
import de.psdev.licensesdialog.model.Notices;

public class SettingsActivity extends BaseActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.container);
//        setupActionBar();
        getSupportFragmentManager().beginTransaction()
                .add(R.id.container, GeneralPreferenceFragment.newInstance())
                .commit();
    }

//    private void setupActionBar() {
//        ActionBar actionBar = getSupportActionBar();
//        if (actionBar != null) {
//            actionBar.setDisplayHomeAsUpEnabled(true);
//        }
//    }
//
//    @Override
//    public boolean onOptionsItemSelected(MenuItem item) {
//        int itemId = item.getItemId();
//        if (itemId == android.R.id.home) {
//            onBackPressed();
//        }
//        return super.onOptionsItemSelected(item);
//    }

    public static class GeneralPreferenceFragment extends PreferenceFragmentCompat {

        public static GeneralPreferenceFragment newInstance() {
            return new GeneralPreferenceFragment();
        }

        @Override
        public void onCreate(Bundle savedInstanceState) {
            super.onCreate(savedInstanceState);
            AppCompatActivity activity = (AppCompatActivity) getActivity();
            ActionBar actionBar = activity.getSupportActionBar();
            if (actionBar != null) {
                actionBar.setDisplayHomeAsUpEnabled(true);
            }
        }

        @Override
        public boolean onOptionsItemSelected(MenuItem item) {
            int itemId = item.getItemId();
            if (itemId == android.R.id.home) {
                getActivity().onBackPressed();
            }
            return super.onOptionsItemSelected(item);
        }

        @Override
        public void onCreatePreferences(Bundle savedInstanceState, String rootKey) {
            setPreferencesFromResource(R.xml.pref_general, rootKey);
            setHasOptionsMenu(true);

            findPreference("switch_night_mode").setOnPreferenceClickListener(new Preference.OnPreferenceClickListener() {
                @Override
                public boolean onPreferenceClick(Preference preference) {
                    int mode = getResources().getConfiguration().uiMode & Configuration.UI_MODE_NIGHT_MASK;
                    if (mode == Configuration.UI_MODE_NIGHT_YES) {
                        SettingsUtil.getInstance().setIsNightMode(true);
                        AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO);
                    } else {
                        SettingsUtil.getInstance().setIsNightMode(false);
                        AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES);
                    }
                    getActivity().getWindow().setWindowAnimations(R.style.WindowAnimationFadeInOut);
                    getActivity().recreate();
                    return false;
                }
            });

            findPreference("clear_image_cache").setOnPreferenceClickListener(new Preference.OnPreferenceClickListener() {
                @Override
                public boolean onPreferenceClick(Preference preference) {
                    new Thread(new Runnable() {
                        @Override
                        public void run() {
                            Glide.get(getActivity()).clearDiskCache();
                        }
                    }).start();
                    Glide.get(getActivity()).clearMemory();
                    Snackbar.make(getView(), R.string.clear_image_cache_successfully, Snackbar.LENGTH_SHORT).show();
                    return false;
                }
            });

            findPreference("about").setOnPreferenceClickListener(new Preference.OnPreferenceClickListener() {
                @Override
                public boolean onPreferenceClick(Preference preference) {
                    getActivity().getSupportFragmentManager().beginTransaction()
                            .add(R.id.container, AboutFragment.newInstance())
                            .addToBackStack(AboutFragment.class.getName())
                            .hide(GeneralPreferenceFragment.this)
                            .commit();
                    return false;
                }
            });
        }
    }

    public static class AboutFragment extends PreferenceFragmentCompat {

        public static AboutFragment newInstance() {
            return new AboutFragment();
        }

        @Override
        public void onCreate(Bundle savedInstanceState) {
            super.onCreate(savedInstanceState);
            AppCompatActivity activity = (AppCompatActivity) getActivity();
            ActionBar actionBar = activity.getSupportActionBar();
            if (actionBar != null) {
                actionBar.setDisplayHomeAsUpEnabled(true);
            }
        }

        @Override
        public boolean onOptionsItemSelected(MenuItem item) {
            int itemId = item.getItemId();
            if (itemId == android.R.id.home) {
                getActivity().onBackPressed();
            }
            return super.onOptionsItemSelected(item);
        }

        @Override
        public void onCreatePreferences(Bundle savedInstanceState, String rootKey) {
            setPreferencesFromResource(R.xml.pref_about, rootKey);
            setHasOptionsMenu(true);

            try {
                String version = getActivity().getPackageManager().getPackageInfo(getActivity().getPackageName(), 0).versionName;
                findPreference("version").setTitle(getResources().getString(R.string.version) + " V" + version);
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

            findPreference("developers").setOnPreferenceClickListener(new Preference.OnPreferenceClickListener() {
                @Override
                public boolean onPreferenceClick(Preference preference) {
                    getActivity().startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse(getString(R.string.about_developers))));
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
    }

//     * This fragment shows notification preferences only. It is used when the
//     * activity is showing a two-pane settings UI.
//     */
//    @TargetApi(Build.VERSION_CODES.HONEYCOMB)
//    public static class NotificationPreferenceFragment extends PreferenceFragment {
//        @Override
//        public void onCreate(Bundle savedInstanceState) {
//            super.onCreate(savedInstanceState);
//            addPreferencesFromResource(R.xml.pref_notification);
//            setHasOptionsMenu(true);
//
//            // Bind the summaries of EditText/List/Dialog/Ringtone preferences
//            // to their values. When their values change, their summaries are
//            // updated to reflect the new value, per the Android Design
//            // guidelines.
//            bindPreferenceSummaryToValue(findPreference("notifications_new_message_ringtone"));
//        }
//
//        @Override
//        public boolean onOptionsItemSelected(MenuItem item) {
//            int id = item.getItemId();
//            if (id == android.R.id.home) {
//                startActivity(new Intent(getActivity(), SettingsActivity.class));
//                return true;
//            }
//            return super.onOptionsItemSelected(item);
//        }
//    }
}
