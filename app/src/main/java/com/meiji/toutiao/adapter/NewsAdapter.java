package com.meiji.toutiao.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.meiji.toutiao.R;
import com.meiji.toutiao.bean.NewsBean;
import com.meiji.toutiao.interfaces.IOnItemClickListener;
import com.meiji.toutiao.utils.TimeUtil;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Meiji on 2016/12/13.
 */

public class NewsAdapter extends RecyclerView.Adapter<NewsAdapter.NewsViewHolder> {

    private List<NewsBean.DataBean> list = new ArrayList<>();
    private IOnItemClickListener onItemClickListener;
    private Context context;

    public NewsAdapter(Context context, List<NewsBean.DataBean> list) {
        this.list = list;
        this.context = context;
    }

    public void setOnItemClickListener(IOnItemClickListener listener) {
        this.onItemClickListener = listener;
    }

    @Override
    public NewsViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.news_tab_item, parent, false);
        return new NewsViewHolder(view, onItemClickListener);
    }

    @Override
    public void onBindViewHolder(NewsViewHolder holder, int position) {
        NewsBean.DataBean bean = list.get(position);
        List<NewsBean.DataBean.ImageListBean> image_list = bean.getImage_list();
        if (image_list.size() != 0) {
            String url = image_list.get(0).getUrl();
            Glide.with(context).load(url).asBitmap().centerCrop().into(holder.iv_image_url);
        } else {
            holder.iv_image_url.setBackgroundResource(R.color.white);
        }

        String tv_title = bean.getTitle();
        String tv_abstract = bean.getAbstractX();
        String tv_source = bean.getSource();
        String tv_comment_count = bean.getComment_count() + "评论";
        String tv_datetime = bean.getDatetime();
        // 处理下时间
        tv_datetime = TimeUtil.timeAgo(TimeUtil.stringConvertDate(tv_datetime));

        holder.tv_title.setText(tv_title);
        holder.tv_abstract.setText(tv_abstract);
        holder.tv_source.setText(tv_source);
        holder.tv_comment_count.setText(tv_comment_count);
        holder.tv_datetime.setText(tv_datetime);
    }

    @Override
    public int getItemCount() {
        return list != null ? list.size() : 0;
    }

    public class NewsViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        private ImageView iv_image_url;
        private TextView tv_title;
        private TextView tv_abstract;
        private TextView tv_source;
        private TextView tv_comment_count;
        private TextView tv_datetime;
        private IOnItemClickListener onItemClickListener;

        public NewsViewHolder(View itemView, IOnItemClickListener onItemClickListener) {
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
}
