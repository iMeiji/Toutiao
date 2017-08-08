package com.meiji.toutiao.module.photo.content;

import android.Manifest;
import android.annotation.SuppressLint;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.PorterDuff;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Parcelable;
import android.support.annotation.NonNull;
import android.support.design.widget.Snackbar;
import android.support.v4.graphics.drawable.DrawableCompat;
import android.support.v4.view.ViewPager;
import android.support.v4.widget.NestedScrollView;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
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
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.meiji.toutiao.IntentAction;
import com.meiji.toutiao.R;
import com.meiji.toutiao.adapter.photo.PhotoContentAdapter;
import com.meiji.toutiao.bean.photo.PhotoArticleBean;
import com.meiji.toutiao.bean.photo.PhotoGalleryBean;
import com.meiji.toutiao.module.base.BaseFragment;
import com.meiji.toutiao.module.news.comment.NewsCommentActivity;
import com.meiji.toutiao.util.SettingUtil;
import com.meiji.toutiao.widget.ViewPagerFixed;

import permissions.dispatcher.NeedsPermission;
import permissions.dispatcher.OnNeverAskAgain;
import permissions.dispatcher.OnPermissionDenied;
import permissions.dispatcher.OnShowRationale;
import permissions.dispatcher.PermissionRequest;
import permissions.dispatcher.RuntimePermissions;

/**
 * Created by Meiji on 2017/3/1.
 */
@RuntimePermissions
public class PhotoContentFragment extends BaseFragment<IPhotoContent.Presenter> implements IPhotoContent.View, ViewPager.OnPageChangeListener, View.OnClickListener {

    public static final String TAG = "PhotoContentFragment";
    private TextView tv_hint;
    private TextView tv_save;
    private ViewPagerFixed viewPager;
    private RelativeLayout photoView;
    private WebView webView;
    private NestedScrollView scrollView;
    private ProgressBar progressBar;
    private String shareUrl;
    private String shareTitle;
    private String groupId;
    private String itemId;
    private PhotoContentAdapter adapter;
    private String mediaUrl;
    private String mediaId;

    public static PhotoContentFragment newInstance(Parcelable dataBean) {
        PhotoContentFragment instance = new PhotoContentFragment();
        Bundle bundle = new Bundle();
        bundle.putParcelable(TAG, dataBean);
        instance.setArguments(bundle);
        return instance;
    }

    @Override
    protected int attachLayoutId() {
        return R.layout.fragment_photo_content;
    }

    @Override
    protected void initData() {
        Bundle bundle = getArguments();
        PhotoArticleBean.DataBean dataBean = bundle.getParcelable(TAG);
        shareUrl = dataBean.getSource_url();
        shareTitle = dataBean.getTitle();
        ((AppCompatActivity) getActivity()).getSupportActionBar().setTitle(shareTitle);
        groupId = dataBean.getGroup_id() + "";
        itemId = dataBean.getGroup_id() + "";
        mediaUrl = dataBean.getMedia_url();
        presenter.doLoadData(shareUrl);
        if (!shareUrl.contains("toutiao")) {
            shareUrl = "http://toutiao.com" + shareUrl;
        }
    }

    @Override
    protected void initView(View view) {
        Toolbar toolbar = view.findViewById(R.id.toolbar);
        initToolBar(toolbar, true, "");
        tv_hint = view.findViewById(R.id.tv_hint);
        tv_save = view.findViewById(R.id.tv_save);
        tv_save.setOnClickListener(this);
        viewPager = view.findViewById(R.id.viewPager);
        photoView = view.findViewById(R.id.photoView);
        webView = view.findViewById(R.id.webview_content);
        scrollView = view.findViewById(R.id.scrollView);
        progressBar = view.findViewById(R.id.pb_progress);
        int color = SettingUtil.getInstance().getColor();
        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.LOLLIPOP) {
            Drawable wrapDrawable = DrawableCompat.wrap(progressBar.getIndeterminateDrawable());
            DrawableCompat.setTint(wrapDrawable, color);
            this.progressBar.setIndeterminateDrawable(DrawableCompat.unwrap(wrapDrawable));
        } else {
            this.progressBar.getIndeterminateDrawable().setColorFilter(color, PorterDuff.Mode.SRC_IN);
        }
        toolbar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                scrollView.smoothScrollTo(0, 0);
            }
        });
        setHasOptionsMenu(true);
    }

    @Override
    public void onSetImageBrowser(PhotoGalleryBean bean, int position) {
        if (adapter == null) {
            adapter = new PhotoContentAdapter(getActivity(), bean);
            viewPager.setAdapter(adapter);
            viewPager.setCurrentItem(position);
            viewPager.addOnPageChangeListener(this);
            tv_hint.setText(position + 1 + "/" + bean.getCount());
        }
    }

    @Override
    public void onSetWebView(String url, boolean flag) {
        initWebClient();
        photoView.setVisibility(View.GONE);
        scrollView.setVisibility(View.VISIBLE);

        // 是否为头条的网站
        if (flag) {
            webView.loadDataWithBaseURL(null, url, "text/html", "utf-8", null);
        } else {
            webView.loadUrl(shareUrl);
        }
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
        Snackbar.make(viewPager, R.string.network_error, Snackbar.LENGTH_SHORT).show();
    }

    @Override
    public void setPresenter(IPhotoContent.Presenter presenter) {
        if (null == presenter) {
            this.presenter = new PhotoContentPresenter(this);
        }
    }

    @Override
    public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

    }

    @Override
    public void onPageSelected(int position) {
        presenter.doSetPosition(position);
        tv_hint.setText(position + 1 + "/" + presenter.doGetImageCount());
    }

    @Override
    public void onPageScrollStateChanged(int state) {

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
                NewsCommentActivity.launch(groupId, itemId);
                break;

            case R.id.action_open_media_home:
                presenter.doGoMediaHome(mediaUrl);
                break;

            case R.id.action_open_in_browser:
                startActivity(new Intent(Intent.ACTION_VIEW).setData(Uri.parse(shareUrl)));
                break;

            case R.id.action_share:
                IntentAction.send(getActivity(), shareTitle + "\n" + shareUrl);
                break;
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
        settings.setCacheMode(WebSettings.LOAD_DEFAULT);
        // 开启DOM storage API功能
        settings.setDomStorageEnabled(true);
        // 开启application Cache功能
        settings.setAppCacheEnabled(false);
        // 判断是否为无图模式
        settings.setBlockNetworkImage(SettingUtil.getInstance().getIsNoPhotoMode());
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
    public void onShowSaveSuccess() {
        Snackbar.make(photoView, R.string.saved, Snackbar.LENGTH_SHORT).show();
    }

    @Override
    public void onClick(View v) {
        if (v.getId() == R.id.tv_save) {
            PhotoContentFragmentPermissionsDispatcher.onSaveImageWithCheck(this);
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        PhotoContentFragmentPermissionsDispatcher.onRequestPermissionsResult(this, requestCode, grantResults);
    }

    @NeedsPermission(Manifest.permission.WRITE_EXTERNAL_STORAGE)
    void onSaveImage() {
        presenter.doSaveImage();
    }

    @OnShowRationale(Manifest.permission.WRITE_EXTERNAL_STORAGE)
    void showRationale(final PermissionRequest request) {
        new AlertDialog.Builder(getActivity())
                .setMessage(R.string.permission_write_rationale)
                .setPositiveButton(R.string.button_allow, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        request.proceed();
                    }
                })
                .setNegativeButton(R.string.button_deny, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        request.cancel();
                    }
                })
                .show();
    }

    @OnPermissionDenied(Manifest.permission.WRITE_EXTERNAL_STORAGE)
    void showDenied() {
        Snackbar.make(photoView, R.string.permission_write_denied, Snackbar.LENGTH_SHORT).show();
    }

    @OnNeverAskAgain(Manifest.permission.WRITE_EXTERNAL_STORAGE)
    void showNeverAsk() {
        Snackbar.make(photoView, R.string.permission_write_never_ask, Snackbar.LENGTH_SHORT).show();
    }
}
