package com.meiji.toutiao.binder.video;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.support.v7.widget.PopupMenu;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.meiji.toutiao.R;
import com.meiji.toutiao.bean.video.VideoArticleBean;
import com.meiji.toutiao.module.video.content.VideoContentActivity;
import com.meiji.toutiao.utils.ImageLoader;
import com.meiji.toutiao.utils.TimeUtil;
import com.meiji.toutiao.widget.CircleImageView;

import me.drakeet.multitype.ItemViewBinder;

/**
 * Created by Meiji on 2017/6/10.
 */

public class VideoArticleViewBinder extends ItemViewBinder<VideoArticleBean.DataBean, VideoArticleViewBinder.ViewHolder> {

    @NonNull
    @Override
    protected VideoArticleViewBinder.ViewHolder onCreateViewHolder(@NonNull LayoutInflater inflater, @NonNull ViewGroup parent) {
        View view = inflater.inflate(R.layout.item_video_article, parent, false);
        return new ViewHolder(view);
    }

    @Override
    protected void onBindViewHolder(@NonNull final ViewHolder holder, @NonNull final VideoArticleBean.DataBean item) {
        try {
            String url = item.getVideo_detail_info().getVideo_detail_info().getDetail_video_large_image().getUrl();
            if (!TextUtils.isEmpty(url)) {
                ImageLoader.loadCenterCrop(holder.itemView.getContext(), url, holder.iv_image_url, R.color.viewBackground);
            }

            String media_avatar_url = item.getMedia_avatar_url();
            if (!TextUtils.isEmpty(media_avatar_url)) {
                ImageLoader.loadCenterCrop(holder.itemView.getContext(), media_avatar_url, holder.iv_media_avatar_url, R.color.viewBackground);
            }

            String title = item.getTitle();
            String source = item.getSource();
            String video_duration_str = item.getVideo_duration_str();
            String external_visit_count = item.getExternal_visit_count() + "次观看";
            String tv_datetime = item.getBehot_time() + "";
            if (!TextUtils.isEmpty(tv_datetime)) {
                tv_datetime = TimeUtil.getTimeStampAgo(tv_datetime);
            }
            String tv_description = source + " - " + external_visit_count + " - " + tv_datetime;

            holder.tv_title.setText(title);
            holder.tv_description.setText(tv_description);
            holder.tv_video_duration_str.setText(video_duration_str);
            holder.iv_menu.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    final Context context = holder.itemView.getContext();
                    PopupMenu popupMenu = new PopupMenu(context, v);
                    popupMenu.inflate(R.menu.menu_video);
                    popupMenu.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
                        @Override
                        public boolean onMenuItemClick(MenuItem menuItem) {
                            String shareTitle = item.getTitle();
                            String shareUrl = item.getDisplay_url();
                            switch (menuItem.getItemId()) {
                                case R.id.action_follow_media:
                                    break;

                                case R.id.action_share:
                                    Intent shareIntent = new Intent()
                                            .setAction(Intent.ACTION_SEND)
                                            .setType("text/plain")
                                            .putExtra(Intent.EXTRA_TEXT, shareTitle + "\n" + shareUrl);
                                    context.startActivity(Intent.createChooser(shareIntent, context.getString(R.string.share_to)));
                                    break;

                                case R.id.action_open_in_browser:
                                    context.startActivity(new Intent(Intent.ACTION_VIEW).setData(Uri.parse(shareUrl)));
                                    break;
                            }
                            return false;
                        }
                    });
                    popupMenu.show();
                }
            });
            holder.itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    String url = item.getVideo_detail_info().getVideo_detail_info().getDetail_video_large_image().getUrl();
                    VideoContentActivity.launch(item, url);
                }
            });
        } catch (Exception e) {

        }
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        private ImageView iv_image_url;
        private CircleImageView iv_media_avatar_url;
        private TextView tv_video_duration_str;
        private TextView tv_title;
        private TextView tv_description;
        private ImageView iv_menu;

        public ViewHolder(View itemView) {
            super(itemView);
            this.iv_image_url = (ImageView) itemView.findViewById(R.id.iv_image_url);
            this.iv_menu = (ImageView) itemView.findViewById(R.id.iv_menu);
            this.tv_video_duration_str = (TextView) itemView.findViewById(R.id.tv_video_duration_str);
            this.iv_media_avatar_url = (CircleImageView) itemView.findViewById(R.id.iv_media_avatar_url);
            this.tv_title = (TextView) itemView.findViewById(R.id.tv_title);
            this.tv_description = (TextView) itemView.findViewById(R.id.tv_description);
        }
    }
}
