package com.meiji.toutiao.module.news.content;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.graphics.PorterDuff;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Parcelable;
import android.support.design.widget.Snackbar;
import android.support.v4.graphics.drawable.DrawableCompat;
import android.support.v4.widget.NestedScrollView;
import android.support.v7.widget.Toolbar;
import android.view.KeyEvent;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.ProgressBar;

import com.meiji.toutiao.ErrorAction;
import com.meiji.toutiao.R;
import com.meiji.toutiao.bean.news.MultiNewsArticleDataBean;
import com.meiji.toutiao.module.base.BaseActivity;
import com.meiji.toutiao.module.base.BaseFragment;
import com.meiji.toutiao.module.media.MediaAddActivity;
import com.meiji.toutiao.module.media.wip.MediaHomeActivity;
import com.meiji.toutiao.utils.SettingsUtil;

import java.util.List;

/**
 * Created by Meiji on 2017/2/28.
 */

public class NewsContentFragment extends BaseFragment<INewsContent.Presenter> implements INewsContent.View {

    private static final String TAG = "NewsContentFragment";
    // 新闻链接 标题 头条号 文章号 媒体名
    private String shareUrl;
    private String shareTitle;
    private String mediaUrl;
    private String mediaId;

    private WebView webView;
    private NestedScrollView scrollView;
    private INewsContent.Presenter presenter;
    private ProgressBar progressBar;

    public static NewsContentFragment newInstance(Parcelable dataBean) {
        NewsContentFragment instance = new NewsContentFragment();
        Bundle bundle = new Bundle();
        bundle.putParcelable(TAG, dataBean);
        instance.setArguments(bundle);
        return instance;
    }

    @Override
    protected int attachLayoutId() {
        return R.layout.fragment_news_content;
    }

    @Override
    protected void initData() {
        Bundle bundle = getArguments();
//        NewsArticleBean.DataBean dataBean = bundle.getParcelable(TAG);
//        shareUrl = dataBean.getDisplay_url();
//        shareTitle = dataBean.getTitle();
//        ((BaseActivity) getActivity()).getSupportActionBar().setTitle(dataBean.getMedia_name());
//        mediaUrl = dataBean.getMedia_url();
//        presenter.doLoadData(dataBean);
        try {
            MultiNewsArticleDataBean bean = bundle.getParcelable(TAG);
            presenter.doLoadData(bean);
            shareUrl = bean.getDisplay_url();
            shareTitle = bean.getTitle();
            ((BaseActivity) getActivity()).getSupportActionBar().setTitle(bean.getMedia_name());
            mediaUrl = "http://toutiao.com/m" + bean.getMedia_info().getMedia_id();
            mediaId = bean.getMedia_info().getMedia_id();
        } catch (Exception e) {
            ErrorAction.print(e);
        }
    }

    @Override
    protected void initViews(View view) {
        Toolbar toolbar = (Toolbar) view.findViewById(R.id.toolbar);
        initToolBar(toolbar, true, "");
        webView = (WebView) view.findViewById(R.id.webview_content);
        scrollView = (NestedScrollView) view.findViewById(R.id.scrollView);
        scrollView.setOnScrollChangeListener(new NestedScrollView.OnScrollChangeListener() {
            @Override
            public void onScrollChange(NestedScrollView v, int scrollX, int scrollY, int oldScrollX, int oldScrollY) {
                progressBar.setVisibility(View.GONE);
            }
        });
        toolbar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                scrollView.smoothScrollTo(0, 0);
            }
        });
        progressBar = (ProgressBar) view.findViewById(R.id.pb_progress);
        int color = SettingsUtil.getInstance().getColor();
        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.LOLLIPOP) {
            Drawable wrapDrawable = DrawableCompat.wrap(progressBar.getIndeterminateDrawable());
            DrawableCompat.setTint(wrapDrawable, color);
            this.progressBar.setIndeterminateDrawable(DrawableCompat.unwrap(wrapDrawable));
        } else {
            this.progressBar.getIndeterminateDrawable().setColorFilter(color, PorterDuff.Mode.SRC_IN);
        }
        progressBar.setVisibility(View.VISIBLE);
        setHasOptionsMenu(true);
        initWebClient();
    }

    @SuppressLint("SetJavaScriptEnabled")
    private void initWebClient() {
        WebSettings settings = webView.getSettings();
        settings.setJavaScriptEnabled(true);
        // 缩放,设置为不能缩放可以防止页面上出现放大和缩小的图标
        settings.setBuiltInZoomControls(false);
        // 缓存
        settings.setCacheMode(WebSettings.LOAD_DEFAULT);
        // 开启DOM storage API功能
        settings.setDomStorageEnabled(true);
        // 开启application Cache功能
        settings.setAppCacheEnabled(false);
        // 判断是否为无图模式
        settings.setBlockNetworkImage(SettingsUtil.getInstance().getIsNoPhotoMode());
        // 不调用第三方浏览器即可进行页面反应
        webView.setWebViewClient(new WebViewClient() {
            @Override
            public boolean shouldOverrideUrlLoading(WebView view, String url) {
                view.loadUrl(url);
                return true;
            }

            @Override
            public void onPageFinished(WebView view, String url) {
                onHideLoading();
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
    public void onShowNetError() {
        Snackbar.make(scrollView, R.string.network_error, Snackbar.LENGTH_SHORT).show();
    }

    @Override
    public void setPresenter(INewsContent.Presenter presenter) {
        if (null == presenter) {
            this.presenter = new NewsContentPresenter(this);
        }
    }

    @Override
    public void onSetAdapter(List<?> list) {

    }

    @Override
    public void onShowLoading() {
        progressBar.setVisibility(View.VISIBLE);
    }

    @Override
    public void onHideLoading() {
        progressBar.setVisibility(View.GONE);
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        inflater.inflate(R.menu.menu_browser, menu);
        super.onCreateOptionsMenu(menu, inflater);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        switch (id) {
            case R.id.action_open_comment:
                presenter.doShowComment(getActivity(), this);
                break;

            case R.id.action_follow_media:
                MediaAddActivity.launch(mediaUrl, "news");
                break;

            case R.id.action_share:
                Intent shareIntent = new Intent()
                        .setAction(Intent.ACTION_SEND)
                        .setType("text/plain")
                        .putExtra(Intent.EXTRA_TEXT, shareTitle + "\n" + shareUrl);
                startActivity(Intent.createChooser(shareIntent, getString(R.string.share_to)));
                break;

            case R.id.action_open_in_browser:
                startActivity(new Intent(Intent.ACTION_VIEW).setData(Uri.parse(shareUrl)));
                break;

            case android.R.id.home:
                getActivity().onBackPressed();
                break;
            case R.id.action_open_media_info:
                MediaHomeActivity.launch(mediaId);
                break;
        }
        return super.onOptionsItemSelected(item);
    }
}
