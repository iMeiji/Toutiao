package com.meiji.toutiao.settings;

import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.meiji.toutiao.BaseActivity;
import com.meiji.toutiao.R;

import de.psdev.licensesdialog.LicensesDialog;
import de.psdev.licensesdialog.licenses.ApacheSoftwareLicense20;
import de.psdev.licensesdialog.licenses.BSD3ClauseLicense;
import de.psdev.licensesdialog.licenses.MITLicense;
import de.psdev.licensesdialog.model.Notice;
import de.psdev.licensesdialog.model.Notices;

import static com.meiji.toutiao.R.id.changelogView;

/**
 * Created by Meiji on 2017/3/23.
 */

public class AboutActivity extends BaseActivity {

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.container);
        getSupportFragmentManager().beginTransaction()
                .add(R.id.container, AboutFragment.newInstance())
                .commit();
    }

    public static class AboutFragment extends Fragment implements View.OnClickListener {

        public static AboutFragment newInstance() {
            return new AboutFragment();
        }

        @Nullable
        @Override
        public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
            View view = inflater.inflate(R.layout.fragment_about, container, false);
            initView(view);
            setHasOptionsMenu(true);
            return view;
        }

        private void initView(View view) {
            TextView tv_version = (TextView) view.findViewById(R.id.tv_version);
            LinearLayout changelogView = (LinearLayout) view.findViewById(R.id.changelogView);
            LinearLayout developersView = (LinearLayout) view.findViewById(R.id.developersView);
            LinearLayout licensesView = (LinearLayout) view.findViewById(R.id.licensesView);
            LinearLayout sourceCodeView = (LinearLayout) view.findViewById(R.id.sourceCodeView);
            LinearLayout copyRightView = (LinearLayout) view.findViewById(R.id.copyRightView);
            Toolbar toolbar = (Toolbar) view.findViewById(R.id.toolbar);
            AppCompatActivity activity = (AppCompatActivity) getActivity();
            activity.setSupportActionBar(toolbar);
            ActionBar actionBar = activity.getSupportActionBar();
            if (actionBar != null) {
                actionBar.setDisplayHomeAsUpEnabled(true);
                try {
                    String version = activity.getPackageManager().getPackageInfo(activity.getPackageName(), 0).versionName;
                    tv_version.setText(version);
                } catch (PackageManager.NameNotFoundException e) {
                    e.printStackTrace();
                }
            }
            changelogView.setOnClickListener(this);
            developersView.setOnClickListener(this);
            licensesView.setOnClickListener(this);
            sourceCodeView.setOnClickListener(this);
            copyRightView.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {
            switch (v.getId()) {
                case changelogView:
                    getActivity().startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse(getString(R.string.changelog_url))));
                    break;

                case R.id.developersView:
                    getActivity().startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse(getString(R.string.about_developers))));
                    break;

                case R.id.licensesView:
                    createLicenseDialog();
                    break;

                case R.id.sourceCodeView:
                    getActivity().startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse(getString(R.string.source_code_url))));
                    break;

                case R.id.copyRightView:
                    new AlertDialog.Builder(getActivity())
                            .setTitle(R.string.copyright)
                            .setMessage(R.string.copyright_content)
                            .setCancelable(true)
                            .show();
                    break;
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
        public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
            inflater.inflate(R.menu.menu_about, menu);
            super.onCreateOptionsMenu(menu, inflater);
        }

        @Override
        public boolean onOptionsItemSelected(MenuItem item) {
            int itemId = item.getItemId();
            if (itemId == android.R.id.home) {
                getActivity().onBackPressed();
            }
            if (itemId == R.id.share) {
                Intent shareIntent = new Intent()
                        .setAction(Intent.ACTION_SEND)
                        .setType("text/plain")
                        .putExtra(Intent.EXTRA_TEXT, getString(R.string.share_app_text) + getString(R.string.source_code_url));
                startActivity(Intent.createChooser(shareIntent, getString(R.string.share_to)));
            }
            return super.onOptionsItemSelected(item);
        }
    }
}
