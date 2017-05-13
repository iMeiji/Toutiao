package com.meiji.toutiao.adapter.news;

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
import com.meiji.toutiao.bean.news.NewsArticleBean;
import com.meiji.toutiao.interfaces.IOnItemClickListener;
import com.meiji.toutiao.utils.SettingsUtil;
import com.meiji.toutiao.utils.TimeUtil;
import com.meiji.toutiao.widget.CircleImageView;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Meiji on 2016/12/13.
 */

public class NewsArticleAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private static final int TYPE_NORMAL = 0;
    private static final int TYPE_FOOTER = 1;
    private static final int TYPE_NOIMAGE = 2;
    private List<NewsArticleBean.DataBean> list;
    private IOnItemClickListener onItemClickListener;
    private Context context;

    public NewsArticleAdapter(Context context) {
        this.context = context;
    }

    public List<NewsArticleBean.DataBean> getList() {
        return list;
    }

    public void setList(List<NewsArticleBean.DataBean> list) {
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
        if (list.get(position).getImage_list().size() < 1) {
            return TYPE_NOIMAGE;
        }
        return TYPE_NORMAL;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        if (viewType == TYPE_NORMAL) {
            View view = LayoutInflater.from(context).inflate(R.layout.item_news_article, parent, false);
            return new NewsArticleViewHolder(view, onItemClickListener);
        }
        if (viewType == TYPE_FOOTER) {
            View view = LayoutInflater.from(context).inflate(R.layout.list_footer, parent, false);
            return new FooterViewHolder(view);
        }
        if (viewType == TYPE_NOIMAGE) {
            View view = LayoutInflater.from(context).inflate(R.layout.item_news_article_no_image, parent, false);
            return new NewsArticleNoImageViewHolder(view, onItemClickListener);
        }
        return null;
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        if (holder instanceof NewsArticleViewHolder) {
            NewsArticleViewHolder newsHolder = (NewsArticleViewHolder) holder;

            NewsArticleBean.DataBean bean = list.get(position);

            if (!SettingsUtil.getInstance().getIsNoPhotoMode()) {
                List<NewsArticleBean.DataBean.ImageListBean> image_list = bean.getImage_list();
                if (image_list != null && image_list.size() != 0) {
                    String url = image_list.get(0).getUrl();
                    Glide.with(context).load(url).crossFade().centerCrop().error(R.mipmap.error_image).into(newsHolder.iv_image);
                }
                if (!TextUtils.isEmpty(bean.getMedia_avatar_url())) {
                    Glide.with(context).load(bean.getMedia_avatar_url()).crossFade().centerCrop().into(newsHolder.iv_media);
                }
            }

            String tv_title = bean.getTitle();
            String tv_abstract = bean.getAbstractX();
            String tv_source = bean.getSource();
            String tv_comment_count = bean.getComment_count() + "评论";
            String tv_datetime = bean.getBehot_time() + "";
            if (!TextUtils.isEmpty(tv_datetime)) {
                tv_datetime = TimeUtil.getTimeStampAgo(tv_datetime);
            }

            newsHolder.tv_title.setText(tv_title);
            newsHolder.tv_abstract.setText(tv_abstract);
            newsHolder.tv_extra.setText(tv_source + " - " + tv_comment_count + " - " + tv_datetime);
        }

        if (holder instanceof NewsArticleNoImageViewHolder) {
            NewsArticleNoImageViewHolder viewHolder = (NewsArticleNoImageViewHolder) holder;
            NewsArticleBean.DataBean bean = list.get(position);

            if (!SettingsUtil.getInstance().getIsNoPhotoMode()) {
                if (!TextUtils.isEmpty(bean.getMedia_avatar_url())) {
                    Glide.with(context).load(bean.getMedia_avatar_url()).crossFade().centerCrop().into(viewHolder.iv_media);
                }
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
    }

    @Override
    public int getItemCount() {
        return list != null ? list.size() + 1 : 0;
    }

    private class NewsArticleViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        private CircleImageView iv_media;
        private ImageView iv_image;
        private TextView tv_title;
        private TextView tv_abstract;
        private TextView tv_extra;
        private IOnItemClickListener onItemClickListener;

        NewsArticleViewHolder(View itemView, IOnItemClickListener onItemClickListener) {
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

        FooterViewHolder(View itemView) {
            super(itemView);
        }
    }

    private class NewsArticleNoImageViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        private CircleImageView iv_media;
        private TextView tv_extra;
        private TextView tv_title;
        private TextView tv_abstract;
        private IOnItemClickListener onItemClickListener;

        NewsArticleNoImageViewHolder(View itemView, IOnItemClickListener onItemClickListener) {
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
}
