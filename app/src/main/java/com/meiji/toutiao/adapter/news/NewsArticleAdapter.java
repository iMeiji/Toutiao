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

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Meiji on 2016/12/13.
 */

public class NewsArticleAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private static final int TYPE_NORMAL = 0;
    private static final int TYPE_FOOTER = 1;
    private List<NewsArticleBean.DataBean> list = new ArrayList<>();
    private IOnItemClickListener onItemClickListener;
    private Context context;

    public NewsArticleAdapter(Context context, List<NewsArticleBean.DataBean> list) {
        this.list = list;
        this.context = context;
    }

    public void setOnItemClickListener(IOnItemClickListener listener) {
        this.onItemClickListener = listener;
    }

    @Override
    public int getItemViewType(int position) {
        if (position == list.size()) {
            return TYPE_FOOTER;
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
        return null;
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        if (holder instanceof NewsArticleViewHolder) {
            NewsArticleViewHolder newsHolder = (NewsArticleViewHolder) holder;

            NewsArticleBean.DataBean bean = list.get(position);

            if (!SettingsUtil.getInstance().getNoPhotoMode()) {
                List<NewsArticleBean.DataBean.ImageListBean> image_list = bean.getImage_list();
                if (image_list != null && image_list.size() != 0) {
                    String url = image_list.get(0).getUrl();
                    Glide.with(context).load(url).crossFade().centerCrop().error(R.mipmap.error_image).into(newsHolder.iv_image_url);
                }
            }

            String tv_title = bean.getTitle();
            String tv_abstract = bean.getAbstractX();
            String tv_source = bean.getSource();
            String tv_comment_count = bean.getComment_count() + "评论";
//            String tv_datetime = bean.getDatetime();
////             处理下时间
//            if (tv_datetime != null) {
//                tv_datetime = TimeUtil.timeAgo(TimeUtil.stringConvertDate(tv_datetime));
//            }
            String tv_datetime = bean.getBehot_time() + "";
            if (!TextUtils.isEmpty(tv_datetime)) {
                tv_datetime = TimeUtil.getTimeStampAgo(tv_datetime);
            }

            newsHolder.tv_title.setText(tv_title);
            newsHolder.tv_abstract.setText(tv_abstract);
            newsHolder.tv_source.setText(tv_source);
            newsHolder.tv_comment_count.setText(tv_comment_count);
            newsHolder.tv_datetime.setText(tv_datetime);
        }
    }

    @Override
    public int getItemCount() {
        return list != null ? list.size() + 1 : 0;
    }

    private class NewsArticleViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        private ImageView iv_image_url;
        private TextView tv_title;
        private TextView tv_abstract;
        private TextView tv_source;
        private TextView tv_comment_count;
        private TextView tv_datetime;
        private IOnItemClickListener onItemClickListener;

        NewsArticleViewHolder(View itemView, IOnItemClickListener onItemClickListener) {
            super(itemView);
            this.iv_image_url = (ImageView) itemView.findViewById(R.id.iv_image_url);
            this.tv_title = (TextView) itemView.findViewById(R.id.tv_title);
            this.tv_abstract = (TextView) itemView.findViewById(R.id.tv_abstract);
            this.tv_source = (TextView) itemView.findViewById(R.id.tv_source);
            this.tv_comment_count = (TextView) itemView.findViewById(R.id.tv_comment_count);
            this.tv_datetime = (TextView) itemView.findViewById(R.id.tv_datetime);
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
}
