package com.meiji.toutiao.adapter.other.joke;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.meiji.toutiao.R;
import com.meiji.toutiao.bean.other.joke.JokeCommentBean;
import com.meiji.toutiao.interfaces.IOnItemClickListener;
import com.meiji.toutiao.view.CircleImageView;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Meiji on 2017/1/2.
 */

public class JokeCommentAdapter extends RecyclerView.Adapter<JokeCommentAdapter.JokeCommentViewHolder> {

    private List<JokeCommentBean.DataBean.RecentCommentsBean> list = new ArrayList<>();
    private Context context;
    private IOnItemClickListener onItemClickListener;

    public JokeCommentAdapter(List list, Context context) {
        this.list = list;
        this.context = context;
    }

    public void setOnItemClickListener(IOnItemClickListener onItemClickListener) {
        this.onItemClickListener = onItemClickListener;
    }

    @Override
    public JokeCommentViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.other_joke_comment_item, parent, false);
        return new JokeCommentViewHolder(view, onItemClickListener);
    }

    @Override
    public void onBindViewHolder(JokeCommentViewHolder holder, int position) {
        JokeCommentBean.DataBean.RecentCommentsBean bean = list.get(position);
        String iv_avatar = bean.getUser_profile_image_url();
        String tv_username = bean.getUser_name();
        String tv_text = bean.getText();
        String tv_likes = bean.getDigg_count() + "赞";

        Glide.with(context).load(iv_avatar).crossFade().centerCrop().into(holder.iv_avatar);
        holder.tv_username.setText(tv_username);
        holder.tv_text.setText(tv_text);
        holder.tv_likes.setText(tv_likes);
    }

    @Override
    public int getItemCount() {
        return list != null ? list.size() : 0;
    }

    public class JokeCommentViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        private CircleImageView iv_avatar;
        private TextView tv_username;
        private TextView tv_text;
        private TextView tv_likes;
        private IOnItemClickListener onItemClickListener;

        public JokeCommentViewHolder(View itemView, IOnItemClickListener onItemClickListener) {
            super(itemView);
            this.iv_avatar = (CircleImageView) itemView.findViewById(R.id.iv_avatar);
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

}
