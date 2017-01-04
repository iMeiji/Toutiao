package com.meiji.toutiao.other.funny.content;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.ActionBar;
import android.support.v7.widget.Toolbar;
import android.view.KeyEvent;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;

import com.afollestad.materialdialogs.MaterialDialog;
import com.meiji.toutiao.BaseActivity;
import com.meiji.toutiao.R;
import com.meiji.toutiao.bean.other.funny.FunnyArticleBean;

/**
 * Created by Meiji on 2017/1/3.
 */

public class FunnyContentView extends BaseActivity implements IFunnyContent.View {

    public static final String TAG = "FunnyContentView";
    private String group_id;
    private String source_url;
    private String shareTitle;
    private IFunnyContent.Presenter presenter;
    private Toolbar toolbar;
    private WebView webView;
    private ActionBar actionBar;
    private MaterialDialog dialog;

    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.other_funny_content_main);
        presenter = new FunnyContentPresenter(this);
        initView();
        initWebClient();
        initData();
        onRequestData();
    }

    private void initView() {
        toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        actionBar = getSupportActionBar();
        if (actionBar != null) {
            actionBar.setDisplayHomeAsUpEnabled(true);
        }
        webView = (WebView) findViewById(R.id.webview_content);
        dialog = new MaterialDialog.Builder(this)
                .progress(true, 100)
                .content(R.string.md_loading)
                .cancelable(true)
                .build();
    }

    private void initData() {
        Intent intent = getIntent();
        FunnyArticleBean.DataBean dataBean = intent.getParcelableExtra(TAG);
        group_id = dataBean.getGroup_id();
        // http://www.toutiao.com/group/6369021708875383042/
        source_url = "http://www.toutiao.com" + dataBean.getSource_url();
        shareTitle = dataBean.getTitle();
        actionBar.setTitle(dataBean.getSource());
    }

    @SuppressLint("SetJavaScriptEnabled")
    private void initWebClient() {
        WebSettings settings = webView.getSettings();
        settings.setJavaScriptEnabled(true);
        // 缩放,设置为不能缩放可以防止页面上出现放大和缩小的图标
        settings.setBuiltInZoomControls(false);
        // 缓存
        settings.setCacheMode(WebSettings.LOAD_NO_CACHE);
        // 开启DOM storage API功能
        settings.setDomStorageEnabled(true);
        // 开启application Cache功能
        settings.setAppCacheEnabled(false);
        // 不调用第三方浏览器即可进行页面反应
        webView.setWebViewClient(new WebViewClient() {
            @Override
            public boolean shouldOverrideUrlLoading(WebView view, String url) {
                view.loadUrl(url);
                return true;
            }

            @Override
            public void onPageFinished(WebView view, String url) {
                onHideRefreshing();
                super.onPageFinished(view, url);
            }
        });

        webView.setOnKeyListener(new View.OnKeyListener() {
            @Override
            public boolean onKey(View view, int i, KeyEvent keyEvent) {
                if ((keyEvent.getKeyCode() == KeyEvent.KEYCODE_BACK) && webView.canGoBack()) {
                    webView.goBack();
                    return true;
                }
                return false;
            }
        });

//        webView.setWebChromeClient(new WebChromeClient() {
//            @Override
//            public void onProgressChanged(WebView view, int newProgress) {
//                if (newProgress < 80) {
//                    dialog.show();
//                } else {
//                    dialog.dismiss();
//                }
//                super.onProgressChanged(view, newProgress);
//            }
//        });
    }

    @Override
    public void onRequestData() {
        presenter.doGetUrl(group_id);
    }

    @Override
    public void onSetWebView(String url, boolean flag) {
        // 是否为头条的网站
        if (flag) {
            webView.loadDataWithBaseURL(null, url, "text/html", "utf-8", null);
        } else {
            webView.loadUrl(source_url);
        }
    }

    @Override
    public void onShowRefreshing() {
        dialog.show();
    }

    @Override
    public void onHideRefreshing() {
        dialog.dismiss();
    }

    @Override
    public void onFail() {

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.other_funny_content_main, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int itemId = item.getItemId();
        switch (itemId) {
            case android.R.id.home:
                onBackPressed();
                break;

            case R.id.other_funny_content_comment:
                presenter.doGetComment();
                break;

            case R.id.other_funny_content_follow:

                break;

            case R.id.other_funny_content_share:
                Intent shareIntent = new Intent()
                        .setAction(Intent.ACTION_SEND)
                        .setType("text/plain")
                        .putExtra(Intent.EXTRA_TEXT, shareTitle + "\n" + source_url);
                startActivity(Intent.createChooser(shareIntent, getString(R.string.share_to)));
                break;
        }
        return super.onOptionsItemSelected(item);
    }
}
