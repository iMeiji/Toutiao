package com.meiji.toutiao.adapter.news;

import android.content.Context;
import android.graphics.PorterDuff;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.support.v4.graphics.drawable.DrawableCompat;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.meiji.toutiao.R;
import com.meiji.toutiao.bean.news.MultiNewsArticleDataBean;
import com.meiji.toutiao.interfaces.IOnItemClickListener;
import com.meiji.toutiao.utils.ImageLoader;
import com.meiji.toutiao.utils.SettingsUtil;
import com.meiji.toutiao.utils.TimeUtil;
import com.meiji.toutiao.widget.CircleImageView;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Meiji on 2017/5/18.
 */
@Deprecated
public class MultiNewsArticleAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private static final String TAG = "MultiNewsArticleAdapter";
    private static final int TYPE_NORMAL = 0;
    private static final int TYPE_FOOTER = 1;
    private static final int TYPE_NOIMAGE = 2;
    private static final int TYPE_VIDEO = 3;
    private List<MultiNewsArticleDataBean> list;
    private IOnItemClickListener onItemClickListener;
    private Context context;

    public MultiNewsArticleAdapter(Context context) {
        this.context = context;
    }

    public List<MultiNewsArticleDataBean> getList() {
        return list;
    }

    public void setList(List<MultiNewsArticleDataBean> list) {
        this.list = new ArrayList<>(list);
    }

    public void setOnItemClickListener(IOnItemClickListener listener) {
        this.onItemClickListener = listener;
    }

    @Override
    public int getItemViewType(int position) {
        if (position == list.size()) {
            return TYPE_FOOTER;
        }
        if (list.get(position).isHas_video()) {
            return TYPE_VIDEO;
        }
        if (list.get(position).getImage_list().size() < 1) {
            return TYPE_NOIMAGE;
        }
        return TYPE_NORMAL;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        if (viewType == TYPE_NORMAL) {
            View view = LayoutInflater.from(context).inflate(R.layout.item_news_article, parent, false);
            return new NormalViewHolder(view, onItemClickListener);
        }
        if (viewType == TYPE_FOOTER) {
            View view = LayoutInflater.from(context).inflate(R.layout.item_footer, parent, false);
            return new FooterViewHolder(view);
        }
        if (viewType == TYPE_NOIMAGE) {
            View view = LayoutInflater.from(context).inflate(R.layout.item_news_article_no_pic, parent, false);
            return new NoImageViewHolder(view, onItemClickListener);
        }
        if (viewType == TYPE_VIDEO) {
            View view = LayoutInflater.from(context).inflate(R.layout.item_news_article_has_video, parent, false);
            return new VideoViewHolder(view, onItemClickListener);
        }
        return null;
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {

        try {
            if (holder instanceof NormalViewHolder) {
                NormalViewHolder viewHolder = (NormalViewHolder) holder;

                MultiNewsArticleDataBean bean = list.get(position);

                List<MultiNewsArticleDataBean.ImageListBean> image_list = bean.getImage_list();
                if (image_list != null && image_list.size() != 0) {
                    String url = image_list.get(0).getUrl();
                    ImageLoader.loadCenterCrop(context, url, viewHolder.iv_image, R.color.viewBackground);
                }
                String avatar_url = bean.getUser_info().getAvatar_url();
                if (!TextUtils.isEmpty(avatar_url)) {
                    ImageLoader.loadCenterCrop(context, avatar_url, viewHolder.iv_media, R.color.viewBackground);
                }

                String tv_title = bean.getTitle();
                String tv_abstract = bean.getAbstractX();
                String tv_source = bean.getSource();
                String tv_comment_count = bean.getComment_count() + "评论";
                String tv_datetime = bean.getBehot_time() + "";
                if (!TextUtils.isEmpty(tv_datetime)) {
                    tv_datetime = TimeUtil.getTimeStampAgo(tv_datetime);
                }

                viewHolder.tv_title.setText(tv_title);
                viewHolder.tv_abstract.setText(tv_abstract);
                viewHolder.tv_extra.setText(tv_source + " - " + tv_comment_count + " - " + tv_datetime);
            }

            if (holder instanceof NoImageViewHolder) {
                NoImageViewHolder viewHolder = (NoImageViewHolder) holder;
                MultiNewsArticleDataBean bean = list.get(position);

                String avatar_url = bean.getUser_info().getAvatar_url();
                if (!TextUtils.isEmpty(avatar_url)) {
                    ImageLoader.loadCenterCrop(context, avatar_url, viewHolder.iv_media, R.color.viewBackground);
                }

                String tv_title = bean.getTitle();
                String tv_abstract = bean.getAbstractX();
                String tv_source = bean.getSource();
                String tv_comment_count = bean.getComment_count() + "评论";
                String tv_datetime = bean.getBehot_time() + "";
                if (!TextUtils.isEmpty(tv_datetime)) {
                    tv_datetime = TimeUtil.getTimeStampAgo(tv_datetime);
                }

                viewHolder.tv_title.setText(tv_title);
                viewHolder.tv_abstract.setText(tv_abstract);
                viewHolder.tv_extra.setText(tv_source + " - " + tv_comment_count + " - " + tv_datetime);
            }

            if (holder instanceof VideoViewHolder) {
                VideoViewHolder viewHolder = (VideoViewHolder) holder;
                MultiNewsArticleDataBean bean = list.get(position);

                String image = bean.getVideo_detail_info().getDetail_video_large_image().getUrl();
                if (!TextUtils.isEmpty(image)) {
                    ImageLoader.loadCenterCrop(context, image, viewHolder.iv_video_image, R.color.viewBackground);
                }
                String avatar_url = bean.getUser_info().getAvatar_url();
                if (!TextUtils.isEmpty(avatar_url)) {
                    ImageLoader.loadCenterCrop(context, image, viewHolder.iv_media, R.color.viewBackground);
                }

                String tv_title = bean.getTitle();
                String tv_source = bean.getSource();
                String tv_comment_count = bean.getComment_count() + "评论";
                String tv_datetime = bean.getBehot_time() + "";
                if (!TextUtils.isEmpty(tv_datetime)) {
                    tv_datetime = TimeUtil.getTimeStampAgo(tv_datetime);
                }
                int video_duration = bean.getVideo_duration();
                String min = String.valueOf(video_duration / 60);
                String second = String.valueOf(video_duration % 10);
                if (Integer.parseInt(second) < 10) {
                    second = "0" + second;
                }
                String tv_video_time = min + ":" + second;

                viewHolder.tv_title.setText(tv_title);
                viewHolder.tv_extra.setText(tv_source + " - " + tv_comment_count + " - " + tv_datetime);
                viewHolder.tv_video_time.setText(tv_video_time);
            }
        } catch (NullPointerException e) {

        }
    }


    @Override
    public int getItemCount() {
        return list != null ? list.size() + 1 : 0;
    }

    private class NormalViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        private CircleImageView iv_media;
        private ImageView iv_image;
        private TextView tv_title;
        private TextView tv_abstract;
        private TextView tv_extra;
        private IOnItemClickListener onItemClickListener;

        NormalViewHolder(View itemView, IOnItemClickListener onItemClickListener) {
            super(itemView);
            this.iv_media = (CircleImageView) itemView.findViewById(R.id.iv_media);
            this.iv_image = (ImageView) itemView.findViewById(R.id.iv_image);
            this.tv_title = (TextView) itemView.findViewById(R.id.tv_title);
            this.tv_abstract = (TextView) itemView.findViewById(R.id.tv_abstract);
            this.tv_extra = (TextView) itemView.findViewById(R.id.tv_extra);
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

        private ProgressBar progressBar;

        FooterViewHolder(View itemView) {
            super(itemView);
            this.progressBar = (ProgressBar) itemView.findViewById(R.id.progress_footer);
            int color = SettingsUtil.getInstance().getColor();
            if (Build.VERSION.SDK_INT < Build.VERSION_CODES.LOLLIPOP) {
                Drawable wrapDrawable = DrawableCompat.wrap(progressBar.getIndeterminateDrawable());
                DrawableCompat.setTint(wrapDrawable, color);
                this.progressBar.setIndeterminateDrawable(DrawableCompat.unwrap(wrapDrawable));
            } else {
                this.progressBar.getIndeterminateDrawable().setColorFilter(color, PorterDuff.Mode.SRC_IN);
            }
        }
    }

    private class NoImageViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        private CircleImageView iv_media;
        private TextView tv_extra;
        private TextView tv_title;
        private TextView tv_abstract;
        private IOnItemClickListener onItemClickListener;

        NoImageViewHolder(View itemView, IOnItemClickListener onItemClickListener) {
            super(itemView);
            this.iv_media = (CircleImageView) itemView.findViewById(R.id.iv_media);
            this.tv_extra = (TextView) itemView.findViewById(R.id.tv_extra);
            this.tv_title = (TextView) itemView.findViewById(R.id.tv_title);
            this.tv_abstract = (TextView) itemView.findViewById(R.id.tv_abstract);
            this.onItemClickListener = onItemClickListener;
            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {
            if (onItemClickListener != null) {
                onItemClickListener.onClick(v, getLayoutPosition());
            }
        }
    }

    private class VideoViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        private CircleImageView iv_media;
        private TextView tv_extra;
        private TextView tv_title;
        private ImageView iv_video_image;
        private TextView tv_video_time;
        private IOnItemClickListener onItemClickListener;

        VideoViewHolder(View itemView, IOnItemClickListener onItemClickListener) {
            super(itemView);
            this.iv_media = (CircleImageView) itemView.findViewById(R.id.iv_media);
            this.tv_extra = (TextView) itemView.findViewById(R.id.tv_extra);
            this.tv_title = (TextView) itemView.findViewById(R.id.tv_title);
            this.iv_video_image = (ImageView) itemView.findViewById(R.id.iv_video_image);
            this.tv_video_time = (TextView) itemView.findViewById(R.id.tv_video_time);
            this.onItemClickListener = onItemClickListener;
            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {
            try {
                if (onItemClickListener != null) {
                    onItemClickListener.onClick(v, getLayoutPosition());
                }
            } catch (IndexOutOfBoundsException e) {

            }
        }
    }
}
