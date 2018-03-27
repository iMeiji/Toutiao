package com.meiji.toutiao.binder.news;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.PopupMenu;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.jakewharton.rxbinding2.view.RxView;
import com.meiji.toutiao.ErrorAction;
import com.meiji.toutiao.IntentAction;
import com.meiji.toutiao.R;
import com.meiji.toutiao.bean.news.MultiNewsArticleDataBean;
import com.meiji.toutiao.module.video.content.VideoContentActivity;
import com.meiji.toutiao.util.ImageLoader;
import com.meiji.toutiao.util.SettingUtil;
import com.meiji.toutiao.util.TimeUtil;
import com.meiji.toutiao.widget.CircleImageView;

import java.util.concurrent.TimeUnit;

import me.drakeet.multitype.ItemViewBinder;

/**
 * Created by Meiji on 2017/6/8.
 */

public class NewsArticleVideoViewBinder extends ItemViewBinder<MultiNewsArticleDataBean, NewsArticleVideoViewBinder.ViewHolder> {

    private static final String TAG = "NewsArticleHasVideoView";

    @NonNull
    @Override
    protected NewsArticleVideoViewBinder.ViewHolder onCreateViewHolder(@NonNull LayoutInflater inflater, @NonNull ViewGroup parent) {
        View view = inflater.inflate(R.layout.item_news_article_video, parent, false);
        return new ViewHolder(view);
    }

    @Override
    protected void onBindViewHolder(@NonNull final NewsArticleVideoViewBinder.ViewHolder holder, @NonNull final MultiNewsArticleDataBean item) {

        final Context context = holder.itemView.getContext();

        try {
            if (null != item.getVideo_detail_info()) {
                if (null != item.getVideo_detail_info().getDetail_video_large_image()) {
                    String image = item.getVideo_detail_info().getDetail_video_large_image().getUrl();
                    if (!TextUtils.isEmpty(image)) {
                        ImageLoader.loadCenterCrop(context, image, holder.iv_video_image, R.color.viewBackground, R.mipmap.error_image);
                    }
                }
            } else {
                holder.iv_video_image.setImageResource(R.mipmap.error_image);
            }

            if (null != item.getUser_info()) {
                String avatar_url = item.getUser_info().getAvatar_url();
                if (!TextUtils.isEmpty(avatar_url)) {
                    ImageLoader.loadCenterCrop(context, avatar_url, holder.iv_media, R.color.viewBackground);
                }
            }

            String tv_title = item.getTitle();
            holder.tv_title.setTextSize(SettingUtil.getInstance().getTextSize());
            String tv_source = item.getSource();
            String tv_comment_count = item.getComment_count() + "评论";
            String tv_datetime = item.getBehot_time() + "";
            if (!TextUtils.isEmpty(tv_datetime)) {
                tv_datetime = TimeUtil.getTimeStampAgo(tv_datetime);
            }
            int video_duration = item.getVideo_duration();
            String min = String.valueOf(video_duration / 60);
            String second = String.valueOf(video_duration % 10);
            if (Integer.parseInt(second) < 10) {
                second = "0" + second;
            }
            String tv_video_time = min + ":" + second;

            holder.tv_title.setText(tv_title);
            holder.tv_extra.setText(tv_source + " - " + tv_comment_count + " - " + tv_datetime);
            holder.tv_video_time.setText(tv_video_time);
            holder.iv_dots.setOnClickListener(view -> {
                PopupMenu popupMenu = new PopupMenu(context,
                        holder.iv_dots, Gravity.END, 0, R.style.MyPopupMenu);
                popupMenu.inflate(R.menu.menu_share);
                popupMenu.setOnMenuItemClickListener(menu -> {
                    int itemId = menu.getItemId();
                    if (itemId == R.id.action_share) {
                        IntentAction.send(context, item.getTitle() + "\n" + item.getShare_url());
                    }
                    return false;
                });
                popupMenu.show();
            });

            RxView.clicks(holder.itemView)
                    .throttleFirst(1, TimeUnit.SECONDS)
                    .subscribe(o -> VideoContentActivity.launch(item));
        } catch (Exception e) {
            ErrorAction.print(e);
        }
    }

    class ViewHolder extends RecyclerView.ViewHolder {

        private CircleImageView iv_media;
        private TextView tv_extra;
        private TextView tv_title;
        private ImageView iv_video_image;
        private TextView tv_video_time;
        private ImageView iv_dots;

        ViewHolder(View itemView) {
            super(itemView);
            this.iv_media = itemView.findViewById(R.id.iv_media);
            this.tv_extra = itemView.findViewById(R.id.tv_extra);
            this.tv_title = itemView.findViewById(R.id.tv_title);
            this.iv_video_image = itemView.findViewById(R.id.iv_video_image);
            this.tv_video_time = itemView.findViewById(R.id.tv_video_time);
            this.iv_dots = itemView.findViewById(R.id.iv_dots);
        }
    }
}
