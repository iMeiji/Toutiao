package com.meiji.toutiao.module.video.content;

import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.content.res.ColorStateList;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.view.View;
import android.view.WindowManager;
import android.widget.ImageView;

import com.meiji.toutiao.ErrorAction;
import com.meiji.toutiao.InitApp;
import com.meiji.toutiao.R;
import com.meiji.toutiao.Register;
import com.meiji.toutiao.adapter.DiffCallback;
import com.meiji.toutiao.bean.LoadingBean;
import com.meiji.toutiao.bean.LoadingEndBean;
import com.meiji.toutiao.bean.news.MultiNewsArticleDataBean;
import com.meiji.toutiao.module.base.BaseActivity;
import com.meiji.toutiao.module.news.comment.INewsComment;
import com.meiji.toutiao.util.ImageLoader;
import com.meiji.toutiao.util.OnLoadMoreListener;
import com.meiji.toutiao.util.SettingUtil;
import com.meiji.toutiao.widget.helper.MyJCVideoPlayerStandard;
import com.trello.rxlifecycle2.LifecycleTransformer;

import java.util.List;

import fm.jiecao.jcvideoplayer_lib.JCVideoPlayer;
import me.drakeet.multitype.Items;
import me.drakeet.multitype.MultiTypeAdapter;

/**
 * Created by Meiji on 2017/3/30.
 */

public class VideoContentActivity extends BaseActivity implements View.OnClickListener, IVideoContent.View {

    public static final String TAG = "VideoContentActivity";
    protected MultiTypeAdapter adapter;
    protected boolean canLoadMore = false;
    private String groupId;
    private String itemId;
    private String videoId;
    private String videoUrls;
    private String videoTitle;
    private ImageView iv_image_url;
    private FloatingActionButton fabPlay;
    private RecyclerView recyclerView;
    private IVideoContent.Presenter presenter;
    private Items oldItems = new Items();
    private MultiNewsArticleDataBean dataBean;

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
        setContentView(R.layout.fragment_video_content);
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
                        ImageLoader.loadCenterCrop(this, image, iv_image_url, R.color.viewBackground, R.mipmap.error_image);
                    }
                }
            }
            this.groupId = dataBean.getGroup_id() + "";
            this.itemId = dataBean.getItem_id() + "";
            this.videoId = dataBean.getVideo_id();
            this.videoTitle = dataBean.getTitle();
            oldItems.add(dataBean);
        } catch (NullPointerException e) {
            ErrorAction.print(e);
        }
    }

    private void initView() {
        initToolBar((Toolbar) findViewById(R.id.toolbar), true, "");

        iv_image_url = (ImageView) findViewById(R.id.iv_image_url);
        iv_image_url.setOnClickListener(this);
        fabPlay = (FloatingActionButton) findViewById(R.id.fab_play);
        fabPlay.setOnClickListener(this);
        fabPlay.setBackgroundTintList(ColorStateList.valueOf(SettingUtil.getInstance().getColor()));

        recyclerView = (RecyclerView) findViewById(R.id.recycler_view);
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
    }

    @Override
    public void onClick(View v) {
        int id = v.getId();
        if (id == R.id.fab_play || id == R.id.iv_image_url) {
            MyJCVideoPlayerStandard.startFullscreen(this, MyJCVideoPlayerStandard.class, videoUrls, videoTitle);
            View decorView = getWindow().getDecorView();
            int uiOptions = 0;
            if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.KITKAT) {
                uiOptions = View.SYSTEM_UI_FLAG_HIDE_NAVIGATION
                        | View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY;
            } else {
                uiOptions = View.SYSTEM_UI_FLAG_HIDE_NAVIGATION;
            }
            decorView.setSystemUiVisibility(uiOptions);
            if (SettingUtil.getInstance().getVideoOrientation()) {
                setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE);
            }
        }
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
        DiffCallback.notifyDataSetChanged(newItems, newItems, DiffCallback.NEWS_COMMENT, adapter);
        oldItems.clear();
        oldItems.addAll(newItems);
        canLoadMore = true;
    }

    @Override
    public void onShowLoading() {

    }

    @Override
    public void onHideLoading() {

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
        return this.bindToLifecycle();
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
        videoUrls = urls;
    }

    @Override
    protected void onPause() {
        super.onPause();
        JCVideoPlayer.releaseAllVideos();
    }

    @Override
    public void onBackPressed() {
        if (JCVideoPlayer.backPress()) {
            View decorView = getWindow().getDecorView();
            decorView.setSystemUiVisibility(0);
//            getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
            return;
        }
        super.onBackPressed();
    }
}
