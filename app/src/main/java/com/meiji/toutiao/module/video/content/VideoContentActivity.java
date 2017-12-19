package com.meiji.toutiao.module.video.content;

import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.content.res.ColorStateList;
import android.graphics.PorterDuff;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v4.widget.ContentLoadingProgressBar;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.MotionEvent;
import android.view.View;
import android.view.WindowManager;

import com.meiji.toutiao.ErrorAction;
import com.meiji.toutiao.InitApp;
import com.meiji.toutiao.IntentAction;
import com.meiji.toutiao.R;
import com.meiji.toutiao.Register;
import com.meiji.toutiao.bean.LoadingBean;
import com.meiji.toutiao.bean.LoadingEndBean;
import com.meiji.toutiao.bean.news.MultiNewsArticleDataBean;
import com.meiji.toutiao.module.base.BaseActivity;
import com.meiji.toutiao.module.news.comment.INewsComment;
import com.meiji.toutiao.util.DiffCallback;
import com.meiji.toutiao.util.ImageLoader;
import com.meiji.toutiao.util.OnLoadMoreListener;
import com.meiji.toutiao.util.SettingUtil;
import com.meiji.toutiao.widget.helper.MyJCVideoPlayerStandard;
import com.trello.rxlifecycle2.LifecycleTransformer;
import com.trello.rxlifecycle2.android.ActivityEvent;

import java.util.List;

import fm.jiecao.jcvideoplayer_lib.JCUserAction;
import fm.jiecao.jcvideoplayer_lib.JCUserActionStandard;
import fm.jiecao.jcvideoplayer_lib.JCVideoPlayer;
import fm.jiecao.jcvideoplayer_lib.JCVideoPlayerStandard;
import me.drakeet.multitype.Items;
import me.drakeet.multitype.MultiTypeAdapter;

/**
 * Created by Meiji on 2017/3/30.
 */

public class VideoContentActivity extends BaseActivity implements IVideoContent.View {

    public static final String TAG = "VideoContentActivity";
    protected boolean canLoadMore = false;
    protected MultiTypeAdapter adapter;
    private String groupId;
    private String itemId;
    private String videoId;
    private String videoTitle;
    private String shareUrl;
    private MultiNewsArticleDataBean dataBean;
    private Items oldItems = new Items();

    private RecyclerView recyclerView;
    private ContentLoadingProgressBar progressBar;
    private FloatingActionButton fab;
    private MyJCVideoPlayerStandard jcVideo;
    private IVideoContent.Presenter presenter;
    private int currentAction;
    private SwipeRefreshLayout swipeRefreshLayout;

    public static void launch(MultiNewsArticleDataBean bean) {
        InitApp.AppContext.startActivity(new Intent(InitApp.AppContext, VideoContentActivity.class)
                .putExtra(VideoContentActivity.TAG, bean)
                .addFlags(Intent.FLAG_ACTIVITY_NEW_TASK));
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            getWindow().setFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS, WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
        }
        setContentView(R.layout.fragment_video_content_new);
        presenter = new VideoContentPresenter(this);
        initView();
        initData();
        onLoadData();
    }

    private void initData() {
        Intent intent = getIntent();
        try {
            dataBean = intent.getParcelableExtra(TAG);
            if (null != dataBean.getVideo_detail_info()) {
                if (null != dataBean.getVideo_detail_info().getDetail_video_large_image()) {
                    String image = dataBean.getVideo_detail_info().getDetail_video_large_image().getUrl();
                    if (!TextUtils.isEmpty(image)) {
                        ImageLoader.loadCenterCrop(this, image, jcVideo.thumbImageView, R.color.viewBackground, R.mipmap.error_image);
                    }
                }
            }
            this.groupId = dataBean.getGroup_id() + "";
            this.itemId = dataBean.getItem_id() + "";
            this.videoId = dataBean.getVideo_id();
            this.videoTitle = dataBean.getTitle();
            this.shareUrl = dataBean.getDisplay_url();
            oldItems.add(dataBean);
        } catch (NullPointerException e) {
            ErrorAction.print(e);
        }

    }

    private void initView() {
        recyclerView = findViewById(R.id.recycler_view);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        adapter = new MultiTypeAdapter(oldItems);
        Register.registerVideoContentItem(adapter);
        recyclerView.setAdapter(adapter);
        recyclerView.addOnScrollListener(new OnLoadMoreListener() {
            @Override
            public void onLoadMore() {
                if (canLoadMore) {
                    canLoadMore = false;
                    presenter.doLoadMoreData();
                }
            }
        });

        MyJCVideoPlayerStandard.setOnClickFullScreenListener(new MyJCVideoPlayerStandard.onClickFullScreenListener() {
            @Override
            public void onClickFullScreen() {
                if (currentAction == JCUserAction.ON_ENTER_FULLSCREEN && SettingUtil.getInstance().getIsVideoForceLandscape()) {
                    setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE);
                }
            }
        });

        progressBar = findViewById(R.id.pb_progress);
        int color = SettingUtil.getInstance().getColor();
        progressBar.getIndeterminateDrawable().setColorFilter(color, PorterDuff.Mode.MULTIPLY);
        progressBar.show();

        swipeRefreshLayout = findViewById(R.id.refresh_layout);
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
                onLoadData();
            }
        });

        fab = findViewById(R.id.fab);
        fab.setBackgroundTintList(ColorStateList.valueOf(SettingUtil.getInstance().getColor()));
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                IntentAction.send(VideoContentActivity.this, videoTitle + "\n" + shareUrl);
            }
        });

        jcVideo = findViewById(R.id.jc_video);
        jcVideo.thumbImageView.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View view, MotionEvent motionEvent) {
                fab.setVisibility(View.GONE);
                return false;
            }
        });
    }

    @Override
    public void onLoadData() {
        presenter.doLoadData(groupId, itemId);
        presenter.doLoadVideoData(videoId);
    }

    @Override
    public void onSetAdapter(final List<?> list) {
        Items newItems = new Items();
        newItems.add(dataBean);
        newItems.addAll(list);
        newItems.add(new LoadingBean());
        DiffCallback.create(newItems, newItems, adapter);
        oldItems.clear();
        oldItems.addAll(newItems);
        canLoadMore = true;
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
        Snackbar.make(recyclerView, R.string.network_error, Snackbar.LENGTH_SHORT).show();
    }

    @Override
    public void setPresenter(INewsComment.Presenter presenter) {

    }

    @Override
    public <T> LifecycleTransformer<T> bindToLife() {
        return this.bindUntilEvent(ActivityEvent.DESTROY);
    }

    @Override
    public void onShowNoMore() {
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                if (oldItems.size() > 1) {
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
            }
        });
    }

    @Override
    public void onSetVideoPlay(String urls) {
        jcVideo.setUp(urls, JCVideoPlayerStandard.SCREEN_LAYOUT_NORMAL, videoTitle);
        if (SettingUtil.getInstance().getIsVideoAutoPlay()) {
            jcVideo.startButton.performClick();
            fab.setVisibility(View.GONE);
        }

        // 设置监听事件 判断是否进入全屏
        JCVideoPlayer.setJcUserAction(new JCUserAction() {
            @Override
            public void onEvent(int type, String url, int screen, Object... objects) {
                if (type == JCUserActionStandard.ON_CLICK_START_THUMB ||
                        type == JCUserAction.ON_CLICK_START_ICON ||
                        type == JCUserAction.ON_CLICK_RESUME ||
                        type == JCUserAction.ON_CLICK_START_AUTO_COMPLETE) {
                    fab.setVisibility(View.GONE);
                }

                if (type == JCUserAction.ON_CLICK_PAUSE || type == JCUserAction.ON_AUTO_COMPLETE) {
                    fab.setVisibility(View.VISIBLE);
                }

                if (type == JCUserAction.ON_ENTER_FULLSCREEN) {
                    currentAction = JCUserAction.ON_ENTER_FULLSCREEN;

                    View decorView = getWindow().getDecorView();
                    int uiOptions = 0;
                    if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.KITKAT) {
                        uiOptions = View.SYSTEM_UI_FLAG_HIDE_NAVIGATION
                                | View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY;
                    } else {
                        uiOptions = View.SYSTEM_UI_FLAG_HIDE_NAVIGATION;
                    }
                    decorView.setSystemUiVisibility(uiOptions);

                    if (slidrInterface != null) {
                        slidrInterface.lock();
                    }
                }

                if (type == JCUserAction.ON_QUIT_FULLSCREEN) {
                    currentAction = JCUserAction.ON_QUIT_FULLSCREEN;

                    View decorView = getWindow().getDecorView();
                    decorView.setSystemUiVisibility(0);

                    if (slidrInterface != null) {
                        slidrInterface.unlock();
                    }
                }
            }
        });
    }

    @Override
    protected void onPause() {
        super.onPause();
        JCVideoPlayer.releaseAllVideos();
    }

    @Override
    public void onBackPressed() {
        if (JCVideoPlayer.backPress()) {
            return;
        }
        super.onBackPressed();
    }
}
