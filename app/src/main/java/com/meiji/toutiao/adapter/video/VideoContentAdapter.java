package com.meiji.toutiao.adapter.video;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.meiji.toutiao.R;
import com.meiji.toutiao.bean.news.NewsCommentBean;
import com.meiji.toutiao.bean.video.VideoArticleBean;
import com.meiji.toutiao.interfaces.IOnItemClickListener;
import com.meiji.toutiao.utils.SettingsUtil;
import com.meiji.toutiao.widget.CircleImageView;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Meiji on 2017/3/30.
 */

public class VideoContentAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private static final int TYPE_HEADER = 0;
    private static final int TYPE_NORMAL = 1;
    private static final int TYPE_FOOTER = 2;
    private List<NewsCommentBean.DataBean.CommentsBean> list = new ArrayList<>();
    private VideoArticleBean.DataBean articleBean;
    private Context context;
    private IOnItemClickListener onItemClickListener;

    public VideoContentAdapter(Context context, VideoArticleBean.DataBean articleBean) {
        this.context = context;
        this.articleBean = articleBean;
    }

    public List<NewsCommentBean.DataBean.CommentsBean> getList() {
        return list;
    }

    public void setList(List<NewsCommentBean.DataBean.CommentsBean> list) {
        this.list = new ArrayList<>(list);
    }

    public void setOnItemClickListener(IOnItemClickListener onItemClickListener) {
        this.onItemClickListener = onItemClickListener;
    }

    @Override
    public int getItemViewType(int position) {
        if (position == 0) {
            return TYPE_HEADER;
        }
//        if (position == list.size()) {
//            return TYPE_FOOTER;
//        }
        return TYPE_NORMAL;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        if (viewType == TYPE_HEADER) {
            View view = LayoutInflater.from(context).inflate(R.layout.item_video_content_header, parent, false);
            return new VideoDescHeader(view);
        }
        if (viewType == TYPE_NORMAL) {
            View view = LayoutInflater.from(context).inflate(R.layout.item_news_comment, parent, false);
            return new NewsCommentsViewHolder(view, onItemClickListener);
        }
//        if (viewType == TYPE_FOOTER) {
//            View view = LayoutInflater.from(context).inflate(R.layout.list_footer, parent, false);
//            return new FooterViewHolder(view);
//        }
        return null;
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        if (holder instanceof NewsCommentsViewHolder) {
            NewsCommentsViewHolder commentHolder = (NewsCommentsViewHolder) holder;
            NewsCommentBean.DataBean.CommentsBean commentsBean = list.get(position);

            String iv_avatar = commentsBean.getUser().getAvatar_url();
            String tv_username = commentsBean.getUser().getName();
            String tv_text = commentsBean.getText();
            int tv_likes = commentsBean.getDigg_count();

            if (!SettingsUtil.getInstance().getIsNoPhotoMode()) {
                Glide.with(context).load(iv_avatar).crossFade().centerCrop().into(commentHolder.iv_avatar);
            }
            commentHolder.tv_username.setText(tv_username);
            commentHolder.tv_text.setText(tv_text);
            commentHolder.tv_likes.setText(tv_likes + "赞");
        }

        if (holder instanceof VideoDescHeader) {
            VideoDescHeader videoDescHeader = (VideoDescHeader) holder;

            if (!SettingsUtil.getInstance().getIsNoPhotoMode()) {

                String media_avatar_url = articleBean.getMedia_avatar_url();
                if (!TextUtils.isEmpty(media_avatar_url)) {
                    Glide.with(context).load(media_avatar_url).crossFade().centerCrop().error(R.mipmap.error_image).into(videoDescHeader.iv_media_avatar_url);
                }
            }

            videoDescHeader.tv_title.setText(articleBean.getTitle());
            videoDescHeader.tv_tv_video_duration_str.setText("时长 " + articleBean.getVideo_duration_str() + " | " + articleBean.getComments_count() + "评论");
            videoDescHeader.tv_abstract.setText(articleBean.getAbstractX());
            videoDescHeader.tv_source.setText(articleBean.getSource());

        }
    }

    @Override
    public int getItemCount() {
//        if (list.size() > 0) {
//            int count = 1;
//            count = list.size() + 1;
//        }
        return list != null ? list.size() : 0;
    }

    private class NewsCommentsViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        private ImageView iv_avatar;
        private TextView tv_username;
        private TextView tv_text;
        private TextView tv_likes;
        private IOnItemClickListener onItemClickListener;

        NewsCommentsViewHolder(View itemView, IOnItemClickListener onItemClickListener) {
            super(itemView);
            this.iv_avatar = (ImageView) itemView.findViewById(R.id.iv_avatar);
            this.tv_username = (TextView) itemView.findViewById(R.id.tv_username);
            this.tv_text = (TextView) itemView.findViewById(R.id.tv_text);
            this.tv_likes = (TextView) itemView.findViewById(R.id.tv_likes);
            this.onItemClickListener = onItemClickListener;
            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View view) {
            if (onItemClickListener != null) {
                onItemClickListener.onClick(view, getLayoutPosition());
            }
        }
    }

    private class FooterViewHolder extends RecyclerView.ViewHolder {

        FooterViewHolder(View itemView) {
            super(itemView);
        }
    }

    private class SimpleViewHolder extends RecyclerView.ViewHolder {

        public SimpleViewHolder(View itemView) {
            super(itemView);
        }
    }

    private class VideoDescHeader extends RecyclerView.ViewHolder {

        private TextView tv_title;
        private TextView tv_tv_video_duration_str;
        private TextView tv_abstract;
        private TextView tv_source;
        private CircleImageView iv_media_avatar_url;

        public VideoDescHeader(View itemView) {
            super(itemView);
            this.tv_title = (TextView) itemView.findViewById(R.id.tv_title);
            this.tv_tv_video_duration_str = (TextView) itemView.findViewById(R.id.tv_tv_video_duration_str);
            this.tv_abstract = (TextView) itemView.findViewById(R.id.tv_abstract);
            this.tv_source = (TextView) itemView.findViewById(R.id.tv_source);
            this.iv_media_avatar_url = (CircleImageView) itemView.findViewById(R.id.iv_media_avatar_url);
        }
    }
}
