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

public class JokeContentAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private static final int TYPE_NORMAL = 0;
    private static final int TYPE_FOOTER = 1;
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
    public int getItemViewType(int position) {
        if (position == list.size()) {
            return TYPE_FOOTER;
        }
        return TYPE_NORMAL;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        if (viewType == TYPE_NORMAL) {
            View view = LayoutInflater.from(context).inflate(R.layout.item_other_joke, parent, false);
            return new JokeContentViewHolder(view, onItemClickListener);
        }
        if (viewType == TYPE_FOOTER) {
            View view = LayoutInflater.from(context).inflate(R.layout.list_footer, parent, false);
            return new FooterViewHolder(view);
        }
        return null;
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {

        if (holder instanceof JokeContentViewHolder) {
            JokeContentViewHolder jokeViewHolder = (JokeContentViewHolder) holder;

            JokeContentBean.DataBean.GroupBean bean = list.get(position);

            String avatar_url = bean.getUser().getAvatar_url();
            String name = bean.getUser().getName();
            String text = bean.getText();
            String digg_count = bean.getDigg_count() + "";
            String bury_count = bean.getBury_count() + "";
            String comment_count = bean.getComment_count() + "评论";

            if (!SettingsUtil.getInstance().getIsNoPhotoMode()) {
                Glide.with(context).load(avatar_url).crossFade().centerCrop().into(jokeViewHolder.iv_avatar);
            }
            jokeViewHolder.tv_username.setText(name);
            jokeViewHolder.tv_text.setText(text);
            jokeViewHolder.tv_digg_count.setText(digg_count);
            jokeViewHolder.tv_bury_count.setText(bury_count);
            jokeViewHolder.tv_comment_count.setText(comment_count);
        }
    }

    @Override
    public int getItemCount() {
        return list != null ? list.size() + 1 : 0;
    }

    private class JokeContentViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        private CircleImageView iv_avatar;
        private TextView tv_username;
        private TextView tv_text;
        private TextView tv_digg_count;
        private TextView tv_bury_count;
        private TextView tv_comment_count;
        private IOnItemClickListener onItemClickListener;

        JokeContentViewHolder(View itemView, IOnItemClickListener onItemClickListener) {
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

    private class FooterViewHolder extends RecyclerView.ViewHolder {

        FooterViewHolder(View itemView) {
            super(itemView);
        }
    }
}
