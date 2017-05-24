package com.meiji.toutiao.module.wenda.detail;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.graphics.PorterDuff;
import android.graphics.drawable.Drawable;
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
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.meiji.toutiao.R;
import com.meiji.toutiao.bean.wenda.WendaContentBean;
import com.meiji.toutiao.module.base.BaseFragment;
import com.meiji.toutiao.utils.SettingsUtil;
import com.meiji.toutiao.widget.CircleImageView;

import java.util.List;

/**
 * Created by Meiji on 2017/5/23.
 */

public class WendaDetailFragment extends BaseFragment<IWendaDetail.Presenter> implements IWendaDetail.View {

    private WendaContentBean.AnsListBean bean;
    private String url;
    private String title;
    private String shareTitle;

    private WebView webView;
    private NestedScrollView scrollView;
    private ProgressBar progressBar;
    private TextView tv_title;
    private CircleImageView iv_user_avatar;
    private TextView tv_user_name;
    private TextView tv_like_count;

    public static WendaDetailFragment newInstance(Parcelable bean) {
        Bundle args = new Bundle();
        args.putParcelable("bean", bean);
        WendaDetailFragment fragment = new WendaDetailFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    protected void initData() {
        this.bean = getArguments().getParcelable("bean");
        this.url = bean.getShare_data().getShare_url();
        presenter.doLoadData(url);

        if (!SettingsUtil.getInstance().getIsNoPhotoMode()) {
            Glide.with(getActivity()).load(bean.getUser().getAvatar_url()).crossFade().centerCrop().error(R.mipmap.error_image).into(iv_user_avatar);
        }
        this.tv_title.setText(bean.getTitle());
        this.tv_user_name.setText(bean.getUser().getUname());
        this.tv_like_count.setText(bean.getDigg_count() + "");
        this.shareTitle = bean.getShare_data().getTitle();
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
    public void onShowNetError() {
        Snackbar.make(scrollView, R.string.network_error, Snackbar.LENGTH_SHORT).show();
    }

    @Override
    public void setPresenter(IWendaDetail.Presenter presenter) {
        if (null == presenter) {
            this.presenter = new WendaDetailPresenter(this);
        }
    }

    @Override
    public void onSetAdapter(List<?> list) {

    }

    @Override
    protected int attachLayoutId() {
        return R.layout.fragment_wenda_detail;
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

        tv_title = (TextView) view.findViewById(R.id.tv_title);
        iv_user_avatar = (CircleImageView) view.findViewById(R.id.iv_user_avatar);
        tv_user_name = (TextView) view.findViewById(R.id.tv_user_name);
        tv_like_count = (TextView) view.findViewById(R.id.tv_like_count);

        setHasOptionsMenu(true);
        initWebClient();
    }

    @Override
    public void onSetWebView(String url, boolean flag) {
        // 是否解析网页成功
        if (flag) {
            webView.loadDataWithBaseURL(null, url, "text/html", "utf-8", null);
        } else {
            webView.loadUrl(url);
        }
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
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        super.onCreateOptionsMenu(menu, inflater);
        inflater.inflate(R.menu.menu_wenda_detail, menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == R.id.action_wenda_share) {
            Intent shareIntent = new Intent()
                    .setAction(Intent.ACTION_SEND)
                    .setType("text/plain")
                    .putExtra(Intent.EXTRA_TEXT, shareTitle + "\n" + url);
            startActivity(Intent.createChooser(shareIntent, getString(R.string.share_to)));
        }
        return super.onOptionsItemSelected(item);
    }
}
