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
import com.meiji.toutiao.bean.joke.JokeContentBean;
import com.meiji.toutiao.interfaces.IOnItemClickListener;
import com.meiji.toutiao.utils.ImageLoader;
import com.meiji.toutiao.utils.SettingsUtil;
import com.meiji.toutiao.widget.CircleImageView;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Meiji on 2016/12/28.
 */
@Deprecated
public class JokeContentAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private static final int TYPE_NORMAL = 0;
    private static final int TYPE_FOOTER = 1;
    private List<JokeContentBean.DataBean.GroupBean> list;
    private Context context;
    private IOnItemClickListener onItemClickListener;

    public JokeContentAdapter(Context context) {
        this.context = context;
    }

    public List<JokeContentBean.DataBean.GroupBean> getList() {
        return list;
    }

    public void setList(List<JokeContentBean.DataBean.GroupBean> list) {
        this.list = new ArrayList<>(list);
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
            View view = LayoutInflater.from(context).inflate(R.layout.item_news_joke_content, parent, false);
            return new JokeContentViewHolder(view, onItemClickListener);
        }
        if (viewType == TYPE_FOOTER) {
            View view = LayoutInflater.from(context).inflate(R.layout.item_footer, parent, false);
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

            ImageLoader.loadCenterCrop(context, avatar_url, jokeViewHolder.iv_avatar, R.color.viewBackground);
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
}
