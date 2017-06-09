package com.meiji.toutiao.binder.news;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.meiji.toutiao.R;
import com.meiji.toutiao.bean.news.MultiNewsArticleDataBean;
import com.meiji.toutiao.bean.video.VideoArticleBean;
import com.meiji.toutiao.module.video.content.VideoContentActivity;
import com.meiji.toutiao.utils.ImageLoader;
import com.meiji.toutiao.utils.TimeUtil;
import com.meiji.toutiao.widget.CircleImageView;

import me.drakeet.multitype.ItemViewBinder;

/**
 * Created by Meiji on 2017/6/8.
 */

public class NewsArticleHasVideoViewBinder extends ItemViewBinder<MultiNewsArticleDataBean, NewsArticleHasVideoViewBinder.ViewHolder> {

    private static final String TAG = "NewsArticleHasVideoView";

    @NonNull
    @Override
    protected NewsArticleHasVideoViewBinder.ViewHolder onCreateViewHolder(@NonNull LayoutInflater inflater, @NonNull ViewGroup parent) {
        View view = inflater.inflate(R.layout.item_news_article_has_video, parent, false);
        return new ViewHolder(view);
    }

    @Override
    protected void onBindViewHolder(@NonNull NewsArticleHasVideoViewBinder.ViewHolder holder, @NonNull final MultiNewsArticleDataBean item) {
        try {

            String image = item.getVideo_detail_info().getDetail_video_large_image().getUrl();
            if (!TextUtils.isEmpty(image)) {
                ImageLoader.loadCenterCrop(holder.itemView.getContext(), image, holder.iv_video_image, R.color.viewBackground);
            }
            String avatar_url = item.getUser_info().getAvatar_url();
            if (!TextUtils.isEmpty(avatar_url)) {
                ImageLoader.loadCenterCrop(holder.itemView.getContext(), image, holder.iv_media, R.color.viewBackground);
            }

            String tv_title = item.getTitle();
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
            holder.itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    VideoArticleBean.DataBean dataBean = new VideoArticleBean.DataBean();
                    dataBean.setTitle(item.getTitle());
                    dataBean.setGroup_id(item.getGroup_id());
                    dataBean.setItem_id(item.getGroup_id());
                    dataBean.setVideo_id(item.getVideo_id());
                    dataBean.setAbstractX(item.getAbstractX());
                    dataBean.setSource(item.getSource());
                    dataBean.setVideo_duration_str(item.getVideo_duration() / 60 + "");
                    String url = item.getVideo_detail_info().getDetail_video_large_image().getUrl();
                    VideoContentActivity.launch(dataBean, url);
                    Log.d(TAG, "doOnClickItem: " + url);
                }
            });
        } catch (Exception e) {
        }
    }

    class ViewHolder extends RecyclerView.ViewHolder {

        private CircleImageView iv_media;
        private TextView tv_extra;
        private TextView tv_title;
        private ImageView iv_video_image;
        private TextView tv_video_time;

        ViewHolder(View itemView) {
            super(itemView);
            this.iv_media = (CircleImageView) itemView.findViewById(R.id.iv_media);
            this.tv_extra = (TextView) itemView.findViewById(R.id.tv_extra);
            this.tv_title = (TextView) itemView.findViewById(R.id.tv_title);
            this.iv_video_image = (ImageView) itemView.findViewById(R.id.iv_video_image);
            this.tv_video_time = (TextView) itemView.findViewById(R.id.tv_video_time);
        }
    }
}
