package com.meiji.toutiao.adapter.joke;

import android.content.Context;
import android.graphics.PorterDuff;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.support.v4.graphics.drawable.DrawableCompat;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.meiji.toutiao.R;
import com.meiji.toutiao.bean.joke.JokeCommentBean;
import com.meiji.toutiao.bean.joke.JokeContentBean;
import com.meiji.toutiao.interfaces.IOnItemClickListener;
import com.meiji.toutiao.utils.ImageLoader;
import com.meiji.toutiao.utils.SettingsUtil;
import com.meiji.toutiao.widget.CircleImageView;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Meiji on 2017/1/2.
 */

public class JokeCommentAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private static final int TYPE_HEADER = 0;
    private static final int TYPE_NORMAL = 1;
    private static final int TYPE_FOOTER = 2;

    private List<JokeCommentBean.DataBean.RecentCommentsBean> list;
    private JokeContentBean.DataBean.GroupBean bean;
    private Context context;
    private IOnItemClickListener onItemClickListener;

    public JokeCommentAdapter(Context context) {
        this.context = context;
    }

    public List<JokeCommentBean.DataBean.RecentCommentsBean> getList() {
        return list;
    }

    public void setList(List<JokeCommentBean.DataBean.RecentCommentsBean> list) {
        this.list = new ArrayList<>(list);
    }

    public void setOnItemClickListener(IOnItemClickListener onItemClickListener) {
        this.onItemClickListener = onItemClickListener;
    }

    public void setBean(JokeContentBean.DataBean.GroupBean bean) {
        this.bean = bean;
    }

    @Override
    public int getItemViewType(int position) {
        if (position == 0) {
            return TYPE_HEADER;
        }
        if (position == list.size()) {
            return TYPE_FOOTER;
        }
        return TYPE_NORMAL;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        if (viewType == TYPE_HEADER) {
            View view = LayoutInflater.from(context).inflate(R.layout.item_news_joke_content, parent, false);
            return new JokeHeaderViewHolder(view, onItemClickListener);
        }
        if (viewType == TYPE_NORMAL) {
            View view = LayoutInflater.from(context).inflate(R.layout.item_news_joke_comment, parent, false);
            return new JokeCommentViewHolder(view, onItemClickListener);
        }
        if (viewType == TYPE_FOOTER) {
            View view = LayoutInflater.from(context).inflate(R.layout.item_footer, parent, false);
            return new FooterViewHolder(view);
        }
        return null;
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {

        if (holder instanceof JokeCommentViewHolder) {

            JokeCommentViewHolder jokeViewHolder = (JokeCommentViewHolder) holder;

            JokeCommentBean.DataBean.RecentCommentsBean bean = list.get(position);
            String iv_avatar = bean.getUser_profile_image_url();
            String tv_username = bean.getUser_name();
            String tv_text = bean.getText();
            String tv_likes = bean.getDigg_count() + "赞";

            ImageLoader.loadCenterCrop(context, iv_avatar, jokeViewHolder.iv_avatar, R.color.viewBackground);
            jokeViewHolder.tv_username.setText(tv_username);
            jokeViewHolder.tv_text.setText(tv_text);
            jokeViewHolder.tv_likes.setText(tv_likes);
        }

        if (holder instanceof JokeHeaderViewHolder) {
            JokeHeaderViewHolder viewHolder = (JokeHeaderViewHolder) holder;


            String avatar_url = bean.getUser().getAvatar_url();
            String name = bean.getUser().getName();
            String text = bean.getText();
            String digg_count = bean.getDigg_count() + "";
            String bury_count = bean.getBury_count() + "";
            String comment_count = bean.getComment_count() + "评论";

            ImageLoader.loadCenterCrop(context, avatar_url, viewHolder.iv_avatar, R.color.viewBackground);
            viewHolder.tv_username.setText(name);
            viewHolder.tv_text.setText(text);
            viewHolder.tv_digg_count.setText(digg_count);
            viewHolder.tv_bury_count.setText(bury_count);
            viewHolder.tv_comment_count.setText(comment_count);

        }
    }

    @Override
    public int getItemCount() {
        return list != null ? list.size() + 1 : 0;
    }

    private class JokeCommentViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        private CircleImageView iv_avatar;
        private TextView tv_username;
        private TextView tv_text;
        private TextView tv_likes;
        private IOnItemClickListener onItemClickListener;

        JokeCommentViewHolder(View itemView, IOnItemClickListener onItemClickListener) {
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

    private class JokeHeaderViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        private CircleImageView iv_avatar;
        private TextView tv_username;
        private TextView tv_text;
        private TextView tv_digg_count;
        private TextView tv_bury_count;
        private TextView tv_comment_count;
        private IOnItemClickListener onItemClickListener;

        JokeHeaderViewHolder(View itemView, IOnItemClickListener onItemClickListener) {
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
