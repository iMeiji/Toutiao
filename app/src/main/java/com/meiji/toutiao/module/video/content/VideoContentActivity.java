package com.meiji.toutiao.module.video.content;

import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.graphics.Color;
import android.graphics.PorterDuff;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.Snackbar;
import android.support.v4.widget.ContentLoadingProgressBar;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.MotionEvent;
import android.view.View;

import com.meiji.toutiao.ErrorAction;
import com.meiji.toutiao.InitApp;
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
import com.meiji.toutiao.widget.helper.MyJZVideoPlayerStandard;

import java.util.List;

import cn.jzvd.JZUserAction;
import cn.jzvd.JZUserActionStandard;
import cn.jzvd.JZVideoPlayer;
import cn.jzvd.JZVideoPlayerStandard;
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
    private MyJZVideoPlayerStandard jcVideo;
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
        setContentView(R.layout.fragment_video_content_new);
        presenter = new VideoContentPresenter(this);
        initView();
        initData();
        onLoadData();
    }

    @Override
    protected void onResume() {
        super.onResume();
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            getWindow().setStatusBarColor(Color.BLACK);
        }
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

    @SuppressWarnings("all")
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

        MyJZVideoPlayerStandard.setOnClickFullScreenListener(new MyJZVideoPlayerStandard.onClickFullScreenListener() {
            @Override
            public void onClickFullScreen() {
                if (currentAction == JZUserAction.ON_ENTER_FULLSCREEN && SettingUtil.getInstance().getIsVideoForceLandscape()) {
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

        jcVideo = findViewById(R.id.jc_video);
        jcVideo.thumbImageView.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View view, MotionEvent motionEvent) {
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
        jcVideo.setUp(urls, JZVideoPlayerStandard.SCREEN_WINDOW_NORMAL, videoTitle);
        if (SettingUtil.getInstance().getIsVideoAutoPlay()) {
            jcVideo.startButton.performClick();
        }

        // 设置监听事件 判断是否进入全屏
        JZVideoPlayer.setJzUserAction(new JZUserAction() {
            @Override
            public void onEvent(int type, Object url, int screen, Object... objects) {
                if (type == JZUserActionStandard.ON_CLICK_START_THUMB ||
                        type == JZUserAction.ON_CLICK_START_ICON ||
                        type == JZUserAction.ON_CLICK_RESUME ||
                        type == JZUserAction.ON_CLICK_START_AUTO_COMPLETE) {
                }

                if (type == JZUserAction.ON_CLICK_PAUSE || type == JZUserAction.ON_AUTO_COMPLETE) {
                }

                if (type == JZUserAction.ON_ENTER_FULLSCREEN) {
                    currentAction = JZUserAction.ON_ENTER_FULLSCREEN;

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

                if (type == JZUserAction.ON_QUIT_FULLSCREEN) {
                    currentAction = JZUserAction.ON_QUIT_FULLSCREEN;

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
        JZVideoPlayer.setJzUserAction(null);
        JZVideoPlayer.releaseAllVideos();
        MyJZVideoPlayerStandard.setOnClickFullScreenListener(null);
        MyJZVideoPlayerStandard.releaseAllVideos();
    }

    @Override
    public void onBackPressed() {
        if (JZVideoPlayer.backPress()) {
            return;
        }
        super.onBackPressed();
    }
}
