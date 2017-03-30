package com.meiji.toutiao.video.content;

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
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.meiji.toutiao.BaseActivity;
import com.meiji.toutiao.InitApp;
import com.meiji.toutiao.R;
import com.meiji.toutiao.adapter.video.VideoContentAdapter;
import com.meiji.toutiao.bean.news.NewsCommentBean;
import com.meiji.toutiao.bean.video.VideoArticleBean;
import com.meiji.toutiao.interfaces.IOnItemClickListener;
import com.meiji.toutiao.utils.SettingsUtil;
import com.meiji.toutiao.view.CircleImageView;

import java.util.List;

/**
 * Created by Meiji on 2017/3/30.
 */

public class VideoContentActivity extends BaseActivity implements View.OnClickListener, IVideoContent.View {

    public static final String TAG = "VideoContentActivity";
    private String groupId;
    private String itemId;

    private ImageView iv_image_url;
    private FloatingActionButton fab_play;
    private RecyclerView recycler_view;
    private View rv_header;
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
        onRequestData();
    }

    private void initData() {
        Intent intent = getIntent();
        VideoArticleBean.DataBean bean = intent.getParcelableExtra(TAG);

        // init header
        rv_header = LayoutInflater.from(this).inflate(R.layout.item_video_content_header, recycler_view, false);
        final TextView tv_title = (TextView) rv_header.findViewById(R.id.tv_title);
        final TextView tv_tv_video_duration_str = (TextView) rv_header.findViewById(R.id.tv_tv_video_duration_str);
        final TextView tv_abstract = (TextView) rv_header.findViewById(R.id.tv_abstract);
        final TextView tv_source = (TextView) rv_header.findViewById(R.id.tv_source);
        final CircleImageView iv_media_avatar_url = (CircleImageView) rv_header.findViewById(R.id.iv_media_avatar_url);
        tv_title.setText(bean.getTitle());
        tv_tv_video_duration_str.setText("时长 " + bean.getVideo_duration_str() + " | " + bean.getComments_count() + "评论");
        tv_abstract.setText(bean.getAbstractX());
        tv_source.setText(bean.getSource());

        if (!SettingsUtil.getInstance().getIsNoPhotoMode()) {
            //String image_url = bean.getImage_url();
            try {
                String url = intent.getStringExtra("url");
                if (!TextUtils.isEmpty(url)) {
                    Glide.with(this).load(url).crossFade().centerCrop().error(R.mipmap.error_image).into(iv_image_url);
                }
            } catch (NullPointerException e) {
                e.printStackTrace();
            }

            String media_avatar_url = bean.getMedia_avatar_url();
            if (!TextUtils.isEmpty(media_avatar_url)) {
                Glide.with(this).load(media_avatar_url).crossFade().centerCrop().error(R.mipmap.error_image).into(iv_media_avatar_url);
            }
        }

        this.groupId = bean.getGroup_id() + "";
        this.itemId = bean.getItem_id() + "";

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

                break;
        }
    }

    @Override
    public void onRequestData() {
        presenter.doGetUrl(groupId, itemId);
    }

    @Override
    public void onSetAdapter(final List<NewsCommentBean.DataBean.CommentsBean> list) {
        if (adapter == null) {
            adapter = new VideoContentAdapter(list, this, rv_header);
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
    public void onShowRefreshing() {

    }

    @Override
    public void onHideRefreshing() {

    }

    @Override
    public void onFail() {

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
