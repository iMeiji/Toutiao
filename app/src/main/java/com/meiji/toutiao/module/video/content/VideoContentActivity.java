package com.meiji.toutiao.module.video.content;

import android.content.ClipData;
import android.content.ClipboardManager;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.BottomSheetDialog;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.View;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.meiji.toutiao.InitApp;
import com.meiji.toutiao.R;
import com.meiji.toutiao.adapter.video.VideoContentAdapter;
import com.meiji.toutiao.bean.news.NewsCommentBean;
import com.meiji.toutiao.bean.video.VideoArticleBean;
import com.meiji.toutiao.interfaces.IOnItemClickListener;
import com.meiji.toutiao.module.base.BaseActivity;
import com.meiji.toutiao.utils.SettingsUtil;

import java.util.List;

import fm.jiecao.jcvideoplayer_lib.JCVideoPlayer;
import fm.jiecao.jcvideoplayer_lib.JCVideoPlayerStandard;

/**
 * Created by Meiji on 2017/3/30.
 */

public class VideoContentActivity extends BaseActivity implements View.OnClickListener, IVideoContent.View {

    public static final String TAG = "VideoContentActivity";
    private String groupId;
    private String itemId;
    private String videoId;
    private String videoUrls;
    private String videoTitle;
    private VideoArticleBean.DataBean articleBean;

    private ImageView iv_image_url;
    private FloatingActionButton fab_play;
    private RecyclerView recycler_view;
    private IVideoContent.Presenter presenter;
    private VideoContentAdapter adapter;
    private boolean canLoading;

    public static void startActivity(VideoArticleBean.DataBean bean, String url) {
        InitApp.AppContext.startActivity(new Intent(InitApp.AppContext, VideoContentActivity.class)
                .putExtra(VideoContentActivity.TAG, bean)
                .putExtra("url", url)
                .addFlags(Intent.FLAG_ACTIVITY_NEW_TASK));
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.fragment_video_content);
        presenter = new VideoContentPresenter(this);
        initView();
        initData();
        onLoadData();
    }

    private void initData() {
        Intent intent = getIntent();
        articleBean = intent.getParcelableExtra(TAG);

        if (!SettingsUtil.getInstance().getIsNoPhotoMode()) {
            try {
                String url = intent.getStringExtra("url");
                if (!TextUtils.isEmpty(url)) {
                    Glide.with(this).load(url).crossFade().centerCrop().error(R.mipmap.error_image).into(iv_image_url);
                }
            } catch (NullPointerException e) {
                e.printStackTrace();
            }
        }

        this.groupId = articleBean.getGroup_id() + "";
        this.itemId = articleBean.getItem_id() + "";
        this.videoId = articleBean.getVideo_id();
        this.videoTitle = articleBean.getTitle();
    }

    private void initView() {
        iv_image_url = (ImageView) findViewById(R.id.iv_image_url);
        fab_play = (FloatingActionButton) findViewById(R.id.fab_play);
        recycler_view = (RecyclerView) findViewById(R.id.recycler_view);
        recycler_view.setHasFixedSize(true);
        recycler_view.setLayoutManager(new LinearLayoutManager(this));
        fab_play.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.fab_play:
                JCVideoPlayerStandard.startFullscreen(this, JCVideoPlayerStandard.class, videoUrls, videoTitle);
                break;
        }
    }

    @Override
    public void onLoadData() {
        presenter.doLoadData(groupId, itemId);
        presenter.doRequestVideoData(videoId);
    }

    @Override
    public void onSetAdapter(final List<NewsCommentBean.DataBean.CommentsBean> list) {
        if (adapter == null) {
            adapter = new VideoContentAdapter(list, this, articleBean);
            recycler_view.setAdapter(adapter);
            adapter.setOnItemClickListener(new IOnItemClickListener() {
                @Override
                public void onClick(View view, int position) {
                    showCopyDialog(list.get(position).getText());
                }
            });
        } else {
            adapter.notifyItemInserted(list.size());
        }

        canLoading = true;

        recycler_view.addOnScrollListener(new RecyclerView.OnScrollListener() {

            @Override
            public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);
            }

            @Override
            public void onScrollStateChanged(RecyclerView recyclerView, int newState) {
                super.onScrollStateChanged(recyclerView, newState);
                if (newState == RecyclerView.SCROLL_STATE_IDLE) {
                    if (!recyclerView.canScrollVertically(1)) {
                        if (canLoading) {
                            presenter.doRefresh();
                            canLoading = false;
                        }
                    }
                }
            }
        });
    }

    @Override
    public void onShowLoading() {

    }

    @Override
    public void onHideLoading() {

    }

    @Override
    public void onShowNetError() {

    }

    @Override
    public void onShowNoMore() {

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
            return;
        }
        super.onBackPressed();
    }

    private void showCopyDialog(final String content) {

        final BottomSheetDialog dialog = new BottomSheetDialog(this);
        View view = getLayoutInflater().inflate(R.layout.item_comment_action_sheet, null);
        view.findViewById(R.id.layout_copy_text).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ClipboardManager copy = (ClipboardManager) getSystemService(Context.CLIPBOARD_SERVICE);
                ClipData clipData = ClipData.newPlainText("text", content);
                copy.setPrimaryClip(clipData);
                Snackbar.make(recycler_view, R.string.copied_to_clipboard, Snackbar.LENGTH_SHORT).show();
                dialog.dismiss();
            }
        });
        view.findViewById(R.id.layout_share_text).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent shareIntent = new Intent()
                        .setAction(Intent.ACTION_SEND)
                        .setType("text/plain")
                        .putExtra(Intent.EXTRA_TEXT, content);
                startActivity(Intent.createChooser(shareIntent, getString(R.string.share_to)));
                dialog.dismiss();
            }
        });
        dialog.setContentView(view);
        dialog.show();
    }
}
