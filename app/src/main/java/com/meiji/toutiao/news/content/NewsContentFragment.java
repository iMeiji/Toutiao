package com.meiji.toutiao.news.content;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.os.Parcelable;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.widget.NestedScrollView;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;

import com.afollestad.materialdialogs.MaterialDialog;
import com.meiji.toutiao.R;
import com.meiji.toutiao.bean.news.NewsArticleBean;
import com.meiji.toutiao.news.comment.NewsCommentFragment;
import com.meiji.toutiao.utils.SettingsUtil;

/**
 * Created by Meiji on 2017/2/28.
 */

public class NewsContentFragment extends Fragment implements INewsContent.View {

    private static final String TAG = "NewsContentFragment";
    private static NewsContentFragment instance;
    // 新闻链接 标题 头条号 文章号 媒体名
    private String shareUrl;
    private String shareTitle;
    private String group_id;
    private String item_id;

    private Toolbar toolbar;
    private WebView webView;
    private ActionBar actionBar;
    private MaterialDialog dialog;
    private NestedScrollView scrollView;
    private INewsContent.Presenter presenter;

    public NewsContentFragment() {
    }

    public static NewsContentFragment newInstance(Parcelable dataBean) {
        instance = new NewsContentFragment();
        Bundle bundle = new Bundle();
        bundle.putParcelable(TAG, dataBean);
        instance.setArguments(bundle);
        return instance;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.news_content_main, container, false);
        presenter = new NewsContentPresenter(this);
        initView(view);
        initWebClient();
        onShowRefreshing();
        initData();
        setHasOptionsMenu(true);
        return view;
    }

    private void initData() {
        Bundle bundle = getArguments();
        NewsArticleBean.DataBean dataBean = bundle.getParcelable(TAG);
        presenter.doRequestData(dataBean);
        shareUrl = dataBean.getDisplay_url();
        shareTitle = dataBean.getTitle();
        actionBar.setTitle(dataBean.getMedia_name());
        group_id = dataBean.getGroup_id() + "";
        item_id = dataBean.getItem_id() + "";
    }

    private void initView(View view) {
        toolbar = (Toolbar) view.findViewById(R.id.toolbar);
        AppCompatActivity activity = (AppCompatActivity) getActivity();
        activity.setSupportActionBar(toolbar);
        activity.setSupportActionBar(toolbar);
        actionBar = activity.getSupportActionBar();
        if (actionBar != null) {
            actionBar.setDisplayHomeAsUpEnabled(true);
        }
        webView = (WebView) view.findViewById(R.id.webview_content);
        dialog = new MaterialDialog.Builder(activity)
                .progress(true, 100)
                .content(R.string.md_loading)
                .cancelable(true)
                .build();
        scrollView = (NestedScrollView) view.findViewById(R.id.scrollView);
        toolbar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                scrollView.smoothScrollTo(0, 0);
            }
        });
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
        // 判断是否为无图模式
        settings.setBlockNetworkImage(SettingsUtil.getInstance().getNoPhotoMode());
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
    public void onSetWebView(String url, boolean flag) {
        // 是否为头条的网站
        if (flag) {
            webView.loadDataWithBaseURL(null, url, "text/html", "utf-8", null);
        } else {
            webView.loadUrl(shareUrl);
        }
    }

    @Override
    public void onFail() {

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
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        inflater.inflate(R.menu.news_content_main, menu);
        super.onCreateOptionsMenu(menu, inflater);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        switch (id) {
            case R.id.news_content_comment:
//                presenter.doGetComment();
                getActivity().getSupportFragmentManager().beginTransaction()
                        .add(R.id.content_main, NewsCommentFragment.newInstance(group_id, item_id), NewsCommentFragment.class.getName())
                        .addToBackStack(NewsCommentFragment.class.getName())
                        .commit();
                break;

            case R.id.news_content_follow:
                break;

            case R.id.news_content_share:
                Intent shareIntent = new Intent()
                        .setAction(Intent.ACTION_SEND)
                        .setType("text/plain")
                        .putExtra(Intent.EXTRA_TEXT, shareTitle + "\n" + shareUrl);
                startActivity(Intent.createChooser(shareIntent, getString(R.string.share_to)));
                break;
        }
        return super.onOptionsItemSelected(item);
    }
}
