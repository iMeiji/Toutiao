package com.meiji.toutiao.module.wenda.detail;

import android.annotation.SuppressLint;
import android.graphics.PorterDuff;
import android.os.Bundle;
import android.os.Parcelable;
import android.support.design.widget.Snackbar;
import android.support.v4.widget.ContentLoadingProgressBar;
import android.support.v4.widget.NestedScrollView;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.RecyclerView;
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
import android.widget.LinearLayout;
import android.widget.TextView;

import com.meiji.toutiao.IntentAction;
import com.meiji.toutiao.R;
import com.meiji.toutiao.Register;
import com.meiji.toutiao.bean.LoadingBean;
import com.meiji.toutiao.bean.LoadingEndBean;
import com.meiji.toutiao.bean.wenda.WendaContentBean;
import com.meiji.toutiao.module.base.BaseFragment;
import com.meiji.toutiao.module.wenda.content.WendaContentActivity;
import com.meiji.toutiao.util.DiffCallback;
import com.meiji.toutiao.util.ImageLoader;
import com.meiji.toutiao.util.SettingUtil;
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
    private ContentLoadingProgressBar progressBar;
    private TextView tv_title;
    private CircleImageView iv_user_avatar;
    private TextView tv_user_name;
    private RecyclerView recyclerView;
    private LinearLayout header_layout;
    private SwipeRefreshLayout swipeRefreshLayout;

    private MultiTypeAdapter adapter;
    private boolean canLoadMore;
    private Items oldItems = new Items();

    public static WendaDetailFragment newInstance(Parcelable bean) {
        Bundle args = new Bundle();
        args.putParcelable(TAG, bean);
        WendaDetailFragment fragment = new WendaDetailFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    protected void initData() {
        bean = getArguments().getParcelable(TAG);
        if (null == this.bean) {
            onShowNetError();
            return;
        }
        url = bean.getShare_data().getShare_url();
        onLoadData();

        ImageLoader.loadCenterCrop(getActivity(), bean.getUser().getAvatar_url(), iv_user_avatar, R.color.viewBackground);
        tv_title.setText(bean.getTitle());
        tv_user_name.setText(bean.getUser().getUname());
        shareTitle = bean.getShare_data().getTitle();
        header_layout.setOnClickListener(v -> WendaContentActivity.launch(bean.getQid()));
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
        newItems.add(new LoadingBean());
        DiffCallback.create(oldItems, newItems, adapter);
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
    protected void initView(View view) {
        Toolbar toolbar = view.findViewById(R.id.toolbar);
        initToolBar(toolbar, true, getString(R.string.title_wenda_detail));
        toolbar.setOnClickListener(view12 -> scrollView.smoothScrollTo(0, 0));

        webView = view.findViewById(R.id.webview);
        initWebClient();

        header_layout = view.findViewById(R.id.header_layout);
        header_layout.setBackgroundColor(SettingUtil.getInstance().getColor());

        tv_title = view.findViewById(R.id.tv_title);
        iv_user_avatar = view.findViewById(R.id.iv_user_avatar);
        tv_user_name = view.findViewById(R.id.tv_user_name);

        scrollView = view.findViewById(R.id.scrollView);
        scrollView.setOnScrollChangeListener((NestedScrollView.OnScrollChangeListener) (v, scrollX, scrollY, oldScrollX, oldScrollY) -> onHideLoading());
        scrollView.getViewTreeObserver().addOnScrollChangedListener(() -> {
            View view1 = scrollView.getChildAt(scrollView.getChildCount() - 1);
            int diff = (view1.getBottom() - (scrollView.getHeight() + scrollView.getScrollY()));
            if (diff == 0) {
                canLoadMore = false;
                presenter.doLoadMoreComment();
            }
        });

        swipeRefreshLayout = view.findViewById(R.id.refresh_layout);
        swipeRefreshLayout.setColorSchemeColors(SettingUtil.getInstance().getColor());
        swipeRefreshLayout.setOnRefreshListener(() -> {
            swipeRefreshLayout.post(() -> swipeRefreshLayout.setRefreshing(true));
            presenter.doLoadData(url);
        });

        progressBar = view.findViewById(R.id.pb_progress);
        int color = SettingUtil.getInstance().getColor();
        progressBar.getIndeterminateDrawable().setColorFilter(color, PorterDuff.Mode.MULTIPLY);
        progressBar.show();

        recyclerView = view.findViewById(R.id.recycler_view);
        recyclerView.setHasFixedSize(true);
        // 禁止嵌套滚动
        recyclerView.setNestedScrollingEnabled(false);
        adapter = new MultiTypeAdapter(oldItems);
        Register.registerNewsCommentItem(adapter);
        recyclerView.setAdapter(adapter);

        setHasOptionsMenu(true);
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

        webView.setOnKeyListener((view, i, keyEvent) -> {
            if ((keyEvent.getKeyCode() == KeyEvent.KEYCODE_BACK) && webView.canGoBack()) {
                webView.goBack();
                return true;
            }
            return false;
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
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        super.onCreateOptionsMenu(menu, inflater);
        inflater.inflate(R.menu.menu_wenda_detail, menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == R.id.action_wenda_share) {
            IntentAction.send(getActivity(), shareTitle + "\n" + url);
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onShowNoMore() {
        getActivity().runOnUiThread(() -> {
            if (oldItems.size() > 0) {
                Items newItems = new Items(oldItems);
                newItems.remove(newItems.size() - 1);
                newItems.add(new LoadingEndBean());
                adapter.setItems(newItems);
                adapter.notifyDataSetChanged();
            } else if (oldItems.size() == 0) {
                oldItems.add(new LoadingEndBean());
                adapter.setItems(oldItems);
                adapter.notifyDataSetChanged();
            }
            canLoadMore = false;
        });
    }

    @Override
    public void onShowLoading() {
        progressBar.show();
    }

    @Override
    public void onHideLoading() {
        progressBar.hide();
        swipeRefreshLayout.post(() -> swipeRefreshLayout.setRefreshing(false));
    }

    @Override
    public void onShowNetError() {
        Snackbar.make(scrollView, R.string.network_error, Snackbar.LENGTH_SHORT).show();
        getActivity().runOnUiThread(() -> {
            adapter.setItems(new Items());
            adapter.notifyDataSetChanged();
            canLoadMore = false;
        });
    }
}
