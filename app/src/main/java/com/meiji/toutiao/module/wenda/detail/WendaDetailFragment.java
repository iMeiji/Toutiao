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
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.KeyEvent;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewTreeObserver;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.meiji.toutiao.R;
import com.meiji.toutiao.Register;
import com.meiji.toutiao.adapter.DiffCallback;
import com.meiji.toutiao.bean.FooterBean;
import com.meiji.toutiao.bean.wenda.WendaContentBean;
import com.meiji.toutiao.module.base.BaseFragment;
import com.meiji.toutiao.module.wenda.content.WendaContentActivity;
import com.meiji.toutiao.util.ImageLoader;
import com.meiji.toutiao.util.SettingsUtil;
import com.meiji.toutiao.widget.CircleImageView;

import java.util.List;

import me.drakeet.multitype.Items;
import me.drakeet.multitype.MultiTypeAdapter;

/**
 * Created by Meiji on 2017/5/23.
 */

public class WendaDetailFragment extends BaseFragment<IWendaDetail.Presenter> implements IWendaDetail.View {

    private static final String TAG = "WendaDetailFragment";
    private WendaContentBean.AnsListBean bean = null;
    private String url;
    private String title;
    private String shareTitle;

    private WebView webView;
    private NestedScrollView scrollView;
    private ProgressBar progressBar;
    private TextView tv_title;
    private CircleImageView iv_user_avatar;
    private TextView tv_user_name;
    private RecyclerView recyclerView;
    private MultiTypeAdapter adapter;
    private boolean canLoadMore;
    private Items oldItems = new Items();
    private LinearLayout header_layout;

    public static WendaDetailFragment newInstance(Parcelable bean) {
        Bundle args = new Bundle();
        args.putParcelable(TAG, bean);
        WendaDetailFragment fragment = new WendaDetailFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    protected void initData() {
        this.bean = getArguments().getParcelable(TAG);
        if (null == this.bean) {
            onShowNetError();
            return;
        }
        this.url = bean.getShare_data().getShare_url();

        ImageLoader.loadCenterCrop(getActivity(), bean.getUser().getAvatar_url(), iv_user_avatar, R.color.viewBackground);
        this.tv_title.setText(bean.getTitle());
        this.tv_user_name.setText(bean.getUser().getUname());
        this.shareTitle = bean.getShare_data().getTitle();
        onLoadData();

        header_layout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                WendaContentActivity.launch(bean.getQid());
            }
        });
    }

    @Override
    public void onLoadData() {
        presenter.doLoadData(url);
    }

    @Override
    public void setPresenter(IWendaDetail.Presenter presenter) {
        if (null == presenter) {
            this.presenter = new WendaDetailPresenter(this);
        }
    }

    @Override
    public void onSetAdapter(List<?> list) {
        Items newItems = new Items(list);
        newItems.add(new FooterBean());
        DiffCallback.notifyDataSetChanged(oldItems, newItems, DiffCallback.NEWS_COMMENT, adapter);
        oldItems.clear();
        oldItems.addAll(newItems);
        canLoadMore = true;
        recyclerView.setLayoutParams(new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.MATCH_PARENT));
    }

    @Override
    protected int attachLayoutId() {
        return R.layout.fragment_wenda_detail;
    }

    @Override
    protected void initViews(View view) {
        Toolbar toolbar = (Toolbar) view.findViewById(R.id.toolbar);
        webView = (WebView) view.findViewById(R.id.webview_content);
        scrollView = (NestedScrollView) view.findViewById(R.id.scrollView);
        progressBar = (ProgressBar) view.findViewById(R.id.pb_progress);
        header_layout = (LinearLayout) view.findViewById(R.id.header_layout);
        tv_title = (TextView) view.findViewById(R.id.tv_title);
        iv_user_avatar = (CircleImageView) view.findViewById(R.id.iv_user_avatar);
        tv_user_name = (TextView) view.findViewById(R.id.tv_user_name);
        recyclerView = (RecyclerView) view.findViewById(R.id.recycler_view);

        initToolBar(toolbar, true, getString(R.string.title_wenda_detail));
        toolbar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                scrollView.smoothScrollTo(0, 0);
            }
        });

        header_layout.setBackgroundColor(SettingsUtil.getInstance().getColor());

        scrollView.setOnScrollChangeListener(new NestedScrollView.OnScrollChangeListener() {
            @Override
            public void onScrollChange(NestedScrollView v, int scrollX, int scrollY, int oldScrollX, int oldScrollY) {
                progressBar.setVisibility(View.GONE);
            }
        });

        scrollView.getViewTreeObserver().addOnScrollChangedListener(new ViewTreeObserver.OnScrollChangedListener() {
            @Override
            public void onScrollChanged() {
                View view = scrollView.getChildAt(scrollView.getChildCount() - 1);

                int diff = (view.getBottom() - (scrollView.getHeight() + scrollView
                        .getScrollY()));

                if (diff == 0) {
                    canLoadMore = false;
                    presenter.doLoadMoreComment();
                }
            }
        });

        int color = SettingsUtil.getInstance().getColor();
        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.LOLLIPOP) {
            Drawable wrapDrawable = DrawableCompat.wrap(progressBar.getIndeterminateDrawable());
            DrawableCompat.setTint(wrapDrawable, color);
            this.progressBar.setIndeterminateDrawable(DrawableCompat.unwrap(wrapDrawable));
        } else {
            this.progressBar.getIndeterminateDrawable().setColorFilter(color, PorterDuff.Mode.SRC_IN);
        }
        progressBar.setVisibility(View.VISIBLE);

        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        // 禁止嵌套滚动
        recyclerView.setNestedScrollingEnabled(false);

        adapter = new MultiTypeAdapter(oldItems);
        Register.registerNewsCommentItem(adapter);
        recyclerView.setAdapter(adapter);

        setHasOptionsMenu(true);
        initWebClient();
    }

    @Override
    public void onSetWebView(String url, boolean flag) {
        // 是否解析网页成功
        if (flag) {
            webView.loadDataWithBaseURL(null, url, "text/html", "utf-8", null);
            presenter.doLoadComment(bean.getAnsid());
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

    @Override
    public void onShowNoMore() {
        Snackbar.make(scrollView, R.string.no_more_comment, Snackbar.LENGTH_SHORT).show();
        getActivity().runOnUiThread(new Runnable() {
            @Override
            public void run() {
                if (oldItems.size() > 0) {
                    Items newItems = new Items(oldItems);
                    newItems.remove(newItems.size() - 1);
                    adapter.setItems(newItems);
                    adapter.notifyDataSetChanged();
                }
                canLoadMore = false;
            }
        });
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
        getActivity().runOnUiThread(new Runnable() {
            @Override
            public void run() {
                adapter.setItems(new Items());
                adapter.notifyDataSetChanged();
                canLoadMore = false;
            }
        });
    }
}
