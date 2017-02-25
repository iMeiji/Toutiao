package com.meiji.toutiao.adapter.other.joke;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.meiji.toutiao.R;
import com.meiji.toutiao.bean.other.joke.JokeContentBean;
import com.meiji.toutiao.interfaces.IOnItemClickListener;
import com.meiji.toutiao.utils.SettingsUtil;
import com.meiji.toutiao.view.CircleImageView;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Meiji on 2016/12/28.
 */

public class JokeContentAdapter extends RecyclerView.Adapter<JokeContentAdapter.JokeContentViewHolder> {

    private List<JokeContentBean.DataBean.GroupBean> list = new ArrayList();
    private Context context;
    private IOnItemClickListener onItemClickListener;

    public JokeContentAdapter(List list, Context context) {
        this.list = list;
        this.context = context;
    }

    public void setOnItemClickListener(IOnItemClickListener onItemClickListener) {
        this.onItemClickListener = onItemClickListener;
    }

    @Override
    public JokeContentViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.other_joke_item, parent, false);
        return new JokeContentViewHolder(view, onItemClickListener);
    }

    @Override
    public void onBindViewHolder(JokeContentViewHolder holder, int position) {
        JokeContentBean.DataBean.GroupBean bean = list.get(position);

        String avatar_url = bean.getUser().getAvatar_url();
        String name = bean.getUser().getName();
        String text = bean.getText();
        String digg_count = bean.getDigg_count() + "";
        String bury_count = bean.getBury_count() + "";
        String comment_count = bean.getComment_count() + "评论";

        if (!SettingsUtil.getInstance().getNoPhotoMode()) {
            Glide.with(context).load(avatar_url).crossFade().centerCrop().into(holder.iv_avatar);
        }
        holder.tv_username.setText(name);
        holder.tv_text.setText(text);
        holder.tv_digg_count.setText(digg_count);
        holder.tv_bury_count.setText(bury_count);
        holder.tv_comment_count.setText(comment_count);
    }

    @Override
    public int getItemCount() {
        return list != null ? list.size() : 0;
    }

    public class JokeContentViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        private CircleImageView iv_avatar;
        private TextView tv_username;
        private TextView tv_text;
        private TextView tv_digg_count;
        private TextView tv_bury_count;
        private TextView tv_comment_count;
        private IOnItemClickListener onItemClickListener;

        public JokeContentViewHolder(View itemView, IOnItemClickListener onItemClickListener) {
            super(itemView);
            this.iv_avatar = (CircleImageView) itemView.findViewById(R.id.iv_avatar);
            this.tv_username = (TextView) itemView.findViewById(R.id.tv_username);
            this.tv_text = (TextView) itemView.findViewById(R.id.tv_text);
            this.tv_digg_count = (TextView) itemView.findViewById(R.id.tv_digg_count);
            this.tv_bury_count = (TextView) itemView.findViewById(R.id.tv_bury_count);
            this.tv_comment_count = (TextView) itemView.findViewById(R.id.tv_comment_count);
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
