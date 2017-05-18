package com.meiji.toutiao.module.photo.content;

import android.Manifest;
import android.annotation.SuppressLint;
import android.annotation.TargetApi;
import android.content.Intent;
import android.content.pm.PackageManager;
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
import android.widget.Toast;

import com.meiji.toutiao.R;
import com.meiji.toutiao.adapter.photo.PhotoContentAdapter;
import com.meiji.toutiao.api.INewsApi;
import com.meiji.toutiao.bean.photo.PhotoArticleBean;
import com.meiji.toutiao.bean.photo.PhotoGalleryBean;
import com.meiji.toutiao.module.base.BaseFragment;
import com.meiji.toutiao.module.media.MediaAddActivity;
import com.meiji.toutiao.module.photo.comment.PhotoCommentFragment;
import com.meiji.toutiao.utils.SettingsUtil;
import com.meiji.toutiao.widget.ViewPagerFixed;

/**
 * Created by Meiji on 2017/3/1.
 */

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
        shareUrl = INewsApi.HOST + dataBean.getSource_url();
        shareTitle = dataBean.getTitle();
        ((AppCompatActivity) getActivity()).getSupportActionBar().setTitle(shareTitle);
        groupId = dataBean.getGroup_id() + "";
        itemId = dataBean.getGroup_id() + "";
        mediaUrl = dataBean.getMedia_url();
        presenter.doLoadData(shareUrl);
    }

    @Override
    protected void initViews(View view) {
        Toolbar toolbar = (Toolbar) view.findViewById(R.id.toolbar);
        initToolBar(toolbar, true, "");
        tv_hint = (TextView) view.findViewById(R.id.tv_hint);
        tv_save = (TextView) view.findViewById(R.id.tv_save);
        tv_save.setOnClickListener(this);
        viewPager = (ViewPagerFixed) view.findViewById(R.id.viewPager);
        photoView = (RelativeLayout) view.findViewById(R.id.photoView);
        webView = (WebView) view.findViewById(R.id.webview_content);
        scrollView = (NestedScrollView) view.findViewById(R.id.scrollView);
        progressBar = (ProgressBar) view.findViewById(R.id.pb_progress);
        int color = SettingsUtil.getInstance().getColor();
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

    @TargetApi(Build.VERSION_CODES.M)
    @Override
    public void onClick(View view) {
        int id = view.getId();
        if (id == R.id.tv_save) {
            // 运行时权限处理
            if (getActivity().checkSelfPermission(Manifest.permission.WRITE_EXTERNAL_STORAGE)
                    != PackageManager.PERMISSION_GRANTED) {

                requestPermissions(new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE}, 1);
            } else {
                presenter.doSaveImage();
            }
        }
    }

    @Override
    public void onShowSaveSuccess() {
        Toast.makeText(getActivity(), R.string.saved, Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {

        if (requestCode == 1) {
            if (grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                presenter.doSaveImage();
            } else {
                // Permission Denied
                Toast.makeText(getActivity(), R.string.permission_denied, Toast.LENGTH_SHORT).show();
            }
            return;
        }
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
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
                getActivity().getSupportFragmentManager().beginTransaction()
                        .add(R.id.container, PhotoCommentFragment.newInstance(groupId, itemId), PhotoCommentFragment.class.getName())
                        .addToBackStack(PhotoCommentFragment.class.getName())
                        .hide(this)
                        .commit();
                break;

            case R.id.action_follow_media:
                MediaAddActivity.launch(mediaUrl, "photo");
                break;

            case R.id.action_open_in_browser:
                startActivity(new Intent(Intent.ACTION_VIEW).setData(Uri.parse(shareUrl)));
                break;

            case R.id.action_share:
                Intent shareIntent = new Intent()
                        .setAction(Intent.ACTION_SEND)
                        .setType("text/plain")
                        .putExtra(Intent.EXTRA_TEXT, shareTitle + "\n" + shareUrl);
                startActivity(Intent.createChooser(shareIntent, getString(R.string.share_to)));
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
}
