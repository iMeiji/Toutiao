package com.meiji.toutiao.adapter.news;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.meiji.toutiao.R;
import com.meiji.toutiao.bean.news.NewsCommentBean;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Meiji on 2016/12/20.
 */

public class NewsCommentsAdapter extends RecyclerView.Adapter<NewsCommentsAdapter.NewsCommentsViewHolder> {

    private List<NewsCommentBean.DataBean.CommentsBean> commentsBeanList = new ArrayList<>();
    private Context context;

    public NewsCommentsAdapter(List<NewsCommentBean.DataBean.CommentsBean> commentsBeanList, Context context) {
        this.commentsBeanList = commentsBeanList;
        this.context = context;
    }

    @Override
    public NewsCommentsViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.news_comment_item, parent, false);
        return new NewsCommentsViewHolder(view);
    }

    @Override
    public void onBindViewHolder(NewsCommentsViewHolder holder, int position) {
        NewsCommentBean.DataBean.CommentsBean commentsBean = commentsBeanList.get(position);
        String iv_avatar = commentsBean.getUser().getAvatar_url();
        String tv_username = commentsBean.getUser().getName();
        String tv_text = commentsBean.getText();
        int tv_likes = commentsBean.getDigg_count();

        Glide.with(context).load(iv_avatar).asBitmap().centerCrop().into(holder.iv_avatar);
        holder.tv_username.setText(tv_username);
        holder.tv_text.setText(tv_text);
        holder.tv_likes.setText(tv_likes + "赞");
    }

    @Override
    public int getItemCount() {
        return commentsBeanList != null ? commentsBeanList.size() : 0;
    }

    public class NewsCommentsViewHolder extends RecyclerView.ViewHolder {
        private ImageView iv_avatar;
        private TextView tv_username;
        private TextView tv_text;
        private TextView tv_likes;

        public NewsCommentsViewHolder(View itemView) {
            super(itemView);
            this.iv_avatar = (ImageView) itemView.findViewById(R.id.iv_avatar);
            this.tv_username = (TextView) itemView.findViewById(R.id.tv_username);
            this.tv_text = (TextView) itemView.findViewById(R.id.tv_text);
            this.tv_likes = (TextView) itemView.findViewById(R.id.tv_likes);
        }
    }
}
