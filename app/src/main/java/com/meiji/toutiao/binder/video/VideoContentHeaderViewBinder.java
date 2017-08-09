package com.meiji.toutiao.binder.video;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.jakewharton.rxbinding2.view.RxView;
import com.meiji.toutiao.ErrorAction;
import com.meiji.toutiao.R;
import com.meiji.toutiao.bean.news.MultiNewsArticleDataBean;
import com.meiji.toutiao.module.media.home.MediaHomeActivity;
import com.meiji.toutiao.util.ImageLoader;
import com.meiji.toutiao.widget.CircleImageView;

import java.util.concurrent.TimeUnit;

import io.reactivex.functions.Consumer;
import me.drakeet.multitype.ItemViewBinder;

/**
 * Created by Meiji on 2017/6/9.
 */

public class VideoContentHeaderViewBinder extends ItemViewBinder<MultiNewsArticleDataBean, VideoContentHeaderViewBinder.ViewHolder> {

    @NonNull
    @Override
    protected VideoContentHeaderViewBinder.ViewHolder onCreateViewHolder(@NonNull LayoutInflater inflater, @NonNull ViewGroup parent) {
        View view = inflater.inflate(R.layout.item_video_content_header, parent, false);
        return new ViewHolder(view);
    }

    @Override
    protected void onBindViewHolder(@NonNull ViewHolder holder, @NonNull MultiNewsArticleDataBean item) {
        try {
            String media_avatar_url = item.getMedia_info().getAvatar_url();
            if (!TextUtils.isEmpty(media_avatar_url)) {
                ImageLoader.loadCenterCrop(holder.itemView.getContext(), media_avatar_url, holder.iv_media_avatar_url, R.color.viewBackground);
            }

            String title = item.getTitle();
            String abstractX = item.getAbstractX();
            String source = item.getSource();

            int video_duration = item.getVideo_duration();
            String min = String.valueOf(video_duration / 60);
            String second = String.valueOf(video_duration % 10);
            if (Integer.parseInt(second) < 10) {
                second = "0" + second;
            }
            String tv_video_time = min + ":" + second;
            String tv_comment_count = item.getComment_count() + "";
            final String media_id = item.getMedia_info().getMedia_id();

            holder.tv_title.setText(title);
            holder.tv_tv_video_duration_str.setText("时长 " + tv_video_time + " | " + tv_comment_count + "评论");
            holder.tv_abstract.setText(abstractX);
            holder.tv_source.setText(source);

            RxView.clicks(holder.itemView)
                    .throttleFirst(1, TimeUnit.SECONDS)
                    .subscribe(new Consumer<Object>() {
                        @Override
                        public void accept(@io.reactivex.annotations.NonNull Object o) throws Exception {
                            MediaHomeActivity.launch(media_id);
                        }
                    });
        } catch (Exception e) {
            ErrorAction.print(e);
        }
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        private TextView tv_title;
        private TextView tv_tv_video_duration_str;
        private TextView tv_abstract;
        private TextView tv_source;
        private CircleImageView iv_media_avatar_url;
        private LinearLayout media_layout;

        public ViewHolder(View itemView) {
            super(itemView);
            this.tv_title = itemView.findViewById(R.id.tv_title);
            this.tv_tv_video_duration_str = itemView.findViewById(R.id.tv_tv_video_duration_str);
            this.tv_abstract = itemView.findViewById(R.id.tv_abstract);
            this.tv_source = itemView.findViewById(R.id.tv_extra);
            this.iv_media_avatar_url = itemView.findViewById(R.id.iv_media_avatar_url);
            this.media_layout = itemView.findViewById(R.id.media_layout);
        }
    }
}
