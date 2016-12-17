package com.meiji.toutiao.news.content;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.support.v7.widget.Toolbar;
import android.view.KeyEvent;
import android.view.MenuItem;
import android.view.View;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;

import com.meiji.toutiao.BaseActivity;
import com.meiji.toutiao.R;

/**
 * Created by Meiji on 2016/12/17.
 */

public class ContentView extends BaseActivity implements IContent.View {

    // 新闻链接 标题 头条号 文章号
    public static final String DISPLAY_URL = "display_url";
    public static final String TITLR = "title";
    public static final String GROUP_ID = "group_id";
    public static final String ITEM_ID = "item_id";

    private String display_url;
    private String title;
    private Toolbar toolbar;
    private WebView webView;
    private IContent.Presenter presenter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.news_content_main);
        presenter = new ContentPresenter(this);
        initView();
        initData();
        initWebClient();
    }

    private void initData() {
        Intent intent = getIntent();
        display_url = intent.getStringExtra(DISPLAY_URL);
        title = intent.getStringExtra(TITLR);
        presenter.doRequestData(display_url);
    }

    private void initView() {
        toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            actionBar.setTitle(title);
            actionBar.setDisplayHomeAsUpEnabled(true);
        }
        webView = (WebView) findViewById(R.id.webview_content);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if (id == android.R.id.home) {
            onBackPressed();
        }
        return super.onOptionsItemSelected(item);
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
    }

    @Override
    public void onSetWebView(String url, boolean flag) {
        // 是否为头条的网站
        if (flag) {
            webView.loadDataWithBaseURL(null, url, "text/html", "utf-8", null);
        } else {
            webView.loadUrl(url);
        }
    }

    @Override
    public void onFail() {

    }

}
