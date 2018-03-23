package com.meiji.toutiao.module.photo.content;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.PorterDuff;
import android.net.Uri;
import android.os.Bundle;
import android.os.Parcelable;
import android.support.design.widget.Snackbar;
import android.support.v4.view.ViewPager;
import android.support.v4.widget.ContentLoadingProgressBar;
import android.support.v4.widget.NestedScrollView;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.view.KeyEvent;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.webkit.WebChromeClient;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.meiji.toutiao.ErrorAction;
import com.meiji.toutiao.IntentAction;
import com.meiji.toutiao.R;
import com.meiji.toutiao.adapter.photo.PhotoContentAdapter;
import com.meiji.toutiao.bean.photo.PhotoArticleBean;
import com.meiji.toutiao.bean.photo.PhotoGalleryBean;
import com.meiji.toutiao.module.base.BaseFragment;
import com.meiji.toutiao.module.news.comment.NewsCommentActivity;
import com.meiji.toutiao.util.SettingUtil;
import com.meiji.toutiao.widget.ViewPagerFixed;
import com.r0adkll.slidr.model.SlidrInterface;
import com.yanzhenjie.permission.Action;
import com.yanzhenjie.permission.AndPermission;
import com.yanzhenjie.permission.Permission;
import com.yanzhenjie.permission.Rationale;
import com.yanzhenjie.permission.RequestExecutor;
import com.yanzhenjie.permission.SettingService;

import java.util.List;

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
    private ContentLoadingProgressBar progressBar;
    private String shareUrl;
    private String shareTitle;
    private String groupId;
    private String itemId;
    private PhotoContentAdapter adapter;
    private String mediaUrl;
    private String mediaId;
    private SwipeRefreshLayout swipeRefreshLayout;

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
        try {
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
        } catch (Exception e) {
            ErrorAction.print(e);
        }
    }

    @Override
    protected void initView(View view) {
        Toolbar toolbar = view.findViewById(R.id.toolbar);
        initToolBar(toolbar, true, "");
        toolbar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                scrollView.smoothScrollTo(0, 0);
            }
        });

        tv_hint = view.findViewById(R.id.tv_hint);
        tv_save = view.findViewById(R.id.tv_save);
        tv_save.setOnClickListener(this);

        viewPager = view.findViewById(R.id.viewPager);
        photoView = view.findViewById(R.id.photoView);
        webView = view.findViewById(R.id.webview);
        scrollView = view.findViewById(R.id.scrollView);

        progressBar = view.findViewById(R.id.pb_progress);
        int color = SettingUtil.getInstance().getColor();
        progressBar.getIndeterminateDrawable().setColorFilter(color, PorterDuff.Mode.MULTIPLY);
        progressBar.show();

        swipeRefreshLayout = view.findViewById(R.id.refresh_layout);
        swipeRefreshLayout.setColorSchemeColors(SettingUtil.getInstance().getColor());
        swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                swipeRefreshLayout.post(new Runnable() {
                    @Override
                    public void run() {
                        swipeRefreshLayout.setRefreshing(true);
                    }
                });
                presenter.doLoadData(shareUrl);
            }
        });

        setHasOptionsMenu(true);
    }

    @Override
    public void onSetImageBrowser(PhotoGalleryBean bean, int position) {
        if (adapter == null) {
            adapter = new PhotoContentAdapter(mContext, bean);
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
        swipeRefreshLayout.setVisibility(View.VISIBLE);

        // 是否为头条的网站
        if (flag) {
            webView.loadDataWithBaseURL(null, url, "text/html", "utf-8", null);
        } else {
            webView.loadUrl(shareUrl);
        }

        SlidrInterface slidrInterface = ((PhotoContentActivity) getActivity()).getSlidrInterface();
        if (slidrInterface != null) {
            slidrInterface.unlock();
        }
    }

    @Override
    public void onShowLoading() {
        progressBar.show();
    }

    @Override
    public void onHideLoading() {
        progressBar.hide();
        swipeRefreshLayout.post(new Runnable() {
            @Override
            public void run() {
                swipeRefreshLayout.setRefreshing(false);
            }
        });
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

        SlidrInterface slidrInterface = ((PhotoContentActivity) getActivity()).getSlidrInterface();
        if (slidrInterface != null) {
            if (position == 0) {
                slidrInterface.unlock();
            } else {
                slidrInterface.lock();
            }
        }
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
        settings.setAppCacheEnabled(true);
        // 判断是否为无图模式
        settings.setBlockNetworkImage(SettingUtil.getInstance().getIsNoPhotoMode());
        // 不调用第三方浏览器即可进行页面反应
        webView.setWebViewClient(new WebViewClient() {
            @Override
            public boolean shouldOverrideUrlLoading(WebView view, String url) {
                if (!TextUtils.isEmpty(url)) {
                    view.loadUrl(url);
                }
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

        webView.setWebChromeClient(new WebChromeClient() {
            @Override
            public void onProgressChanged(WebView view, int newProgress) {
                super.onProgressChanged(view, newProgress);
                if (newProgress >= 90) {
                    onHideLoading();
                } else {
                    onShowLoading();
                }
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
            AndPermission.with(this)
                    .permission(Permission.WRITE_EXTERNAL_STORAGE)
                    .rationale(new Rationale() {
                        @Override
                        public void showRationale(Context context, List<String> permissions, final RequestExecutor executor) {
                            new AlertDialog.Builder(context)
                                    .setMessage(R.string.permission_write_rationale)
                                    .setPositiveButton(R.string.button_allow, new DialogInterface.OnClickListener() {
                                        @Override
                                        public void onClick(DialogInterface dialog, int which) {
                                            executor.execute();
                                        }
                                    })
                                    .setNegativeButton(R.string.button_deny, new DialogInterface.OnClickListener() {
                                        @Override
                                        public void onClick(DialogInterface dialog, int which) {
                                            executor.cancel();
                                        }
                                    })
                                    .show();
                        }
                    })
                    .onGranted(new Action() {
                        @Override
                        public void onAction(List<String> permissions) {
                            presenter.doSaveImage();
                        }
                    })
                    .onDenied(new Action() {
                        @Override
                        public void onAction(List<String> permissions) {
                            Snackbar.make(photoView, R.string.permission_write_denied, Snackbar.LENGTH_SHORT).show();
                            if (AndPermission.hasAlwaysDeniedPermission(PhotoContentFragment.this, permissions)) {
                                final SettingService settingService = AndPermission.permissionSetting(PhotoContentFragment.this);
                                new AlertDialog.Builder(mContext)
                                        .setMessage(R.string.permission_write_rationale)
                                        .setPositiveButton(R.string.button_allow, new DialogInterface.OnClickListener() {
                                            @Override
                                            public void onClick(DialogInterface dialog, int which) {
                                                settingService.execute();
                                            }
                                        })
                                        .setNegativeButton(R.string.button_deny, new DialogInterface.OnClickListener() {
                                            @Override
                                            public void onClick(DialogInterface dialog, int which) {
                                                settingService.cancel();
                                            }
                                        })
                                        .show();
                            }
                        }
                    })
                    .start();
        }
    }
}
