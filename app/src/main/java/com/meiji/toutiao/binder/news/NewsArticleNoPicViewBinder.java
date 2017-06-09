package com.meiji.toutiao.binder.news;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.meiji.toutiao.R;
import com.meiji.toutiao.bean.news.MultiNewsArticleDataBean;
import com.meiji.toutiao.bean.news.NewsArticleBean;
import com.meiji.toutiao.module.news.content.NewsContentActivity;
import com.meiji.toutiao.utils.ImageLoader;
import com.meiji.toutiao.utils.TimeUtil;
import com.meiji.toutiao.widget.CircleImageView;

import me.drakeet.multitype.ItemViewBinder;

/**
 * Created by Meiji on 2017/6/8.
 * 不带图片的 item
 */

public class NewsArticleNoPicViewBinder extends ItemViewBinder<MultiNewsArticleDataBean, NewsArticleNoPicViewBinder.ViewHolder> {

    @NonNull
    @Override
    protected NewsArticleNoPicViewBinder.ViewHolder onCreateViewHolder(@NonNull LayoutInflater inflater, @NonNull ViewGroup parent) {
        View view = inflater.inflate(R.layout.item_news_article_no_pic, parent, false);
        return new ViewHolder(view);
    }

    @Override
    protected void onBindViewHolder(@NonNull NewsArticleNoPicViewBinder.ViewHolder holder, @NonNull final MultiNewsArticleDataBean item) {

        try {
            String avatar_url = item.getUser_info().getAvatar_url();
            if (!TextUtils.isEmpty(avatar_url)) {
                ImageLoader.loadCenterCrop(holder.itemView.getContext(), avatar_url, holder.iv_media, R.color.viewBackground);
            }

            String tv_title = item.getTitle();
            String tv_abstract = item.getAbstractX();
            String tv_source = item.getSource();
            String tv_comment_count = item.getComment_count() + "评论";
            String tv_datetime = item.getBehot_time() + "";
            if (!TextUtils.isEmpty(tv_datetime)) {
                tv_datetime = TimeUtil.getTimeStampAgo(tv_datetime);
            }

            holder.tv_title.setText(tv_title);
            holder.tv_abstract.setText(tv_abstract);
            holder.tv_extra.setText(tv_source + " - " + tv_comment_count + " - " + tv_datetime);
            holder.itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    NewsArticleBean.DataBean dataBean = new NewsArticleBean.DataBean();
                    dataBean.setDisplay_url(item.getDisplay_url());
                    dataBean.setTitle(item.getTitle());
                    dataBean.setMedia_name(item.getMedia_name());
                    if (item.getMedia_info() != null) {
                        dataBean.setMedia_url("http://toutiao.com/m" + item.getMedia_info().getMedia_id());
                    }
                    dataBean.setGroup_id(item.getGroup_id());
                    dataBean.setItem_id(item.getGroup_id());
                    NewsContentActivity.launch(dataBean);
                }
            });
        } catch (Exception e) {

        }
    }

    class ViewHolder extends RecyclerView.ViewHolder {

        private CircleImageView iv_media;
        private TextView tv_extra;
        private TextView tv_title;
        private TextView tv_abstract;

        ViewHolder(View itemView) {
            super(itemView);
            this.iv_media = (CircleImageView) itemView.findViewById(R.id.iv_media);
            this.tv_extra = (TextView) itemView.findViewById(R.id.tv_extra);
            this.tv_title = (TextView) itemView.findViewById(R.id.tv_title);
            this.tv_abstract = (TextView) itemView.findViewById(R.id.tv_abstract);
        }
    }
}
