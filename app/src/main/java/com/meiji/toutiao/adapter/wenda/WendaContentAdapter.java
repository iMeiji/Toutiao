package com.meiji.toutiao.adapter.wenda;

import android.content.Context;
import android.graphics.PorterDuff;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.support.v4.graphics.drawable.DrawableCompat;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.meiji.toutiao.R;
import com.meiji.toutiao.bean.wenda.WendaContentBean;
import com.meiji.toutiao.interfaces.IOnItemClickListener;
import com.meiji.toutiao.utils.SettingsUtil;
import com.meiji.toutiao.widget.CircleImageView;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Meiji on 2017/5/22.
 */

public class WendaContentAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private static final int TYPE_HEADER = 0;
    private static final int TYPE_FOOTER = 1;
    private static final int TYPE_NORMAL = 2;

    private List<WendaContentBean.AnsListBean> list;
    private WendaContentBean.QuestionBean questionBean;
    private IOnItemClickListener onItemClickListener;
    private Context context;

    public WendaContentAdapter(Context context) {
        this.context = context;
    }

    public void setHeader(WendaContentBean.QuestionBean questionBean) {
        this.questionBean = questionBean;
    }

    public List<WendaContentBean.AnsListBean> getList() {
        return list;
    }

    public void setList(List<WendaContentBean.AnsListBean> list) {
        this.list = new ArrayList<>(list);
    }

    public void setOnItemClickListener(IOnItemClickListener listener) {
        this.onItemClickListener = listener;
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
            View view = LayoutInflater.from(context).inflate(R.layout.item_wenda_content_header, parent, false);
            return new HeaderViewHolder(view);
        }
        if (viewType == TYPE_FOOTER) {
            View view = LayoutInflater.from(context).inflate(R.layout.list_footer, parent, false);
            return new FooterViewHolder(view);
        }
        if (viewType == TYPE_NORMAL) {
            View view = LayoutInflater.from(context).inflate(R.layout.item_wenda_content, parent, false);
            return new NormalViewHolder(view, onItemClickListener);
        }
        return null;
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {

        if (holder instanceof HeaderViewHolder) {
            HeaderViewHolder viewHolder = (HeaderViewHolder) holder;

            String tv_title = questionBean.getTitle();
            String tv_abstract = questionBean.getContent().getText();

            if (!SettingsUtil.getInstance().getIsNoPhotoMode()) {
                if (questionBean.getContent().getThumb_image_list().size() > 0) {
                    viewHolder.iv_image.setVisibility(View.VISIBLE);
                    String url = questionBean.getContent().getThumb_image_list().get(0).getUrl();
                    Glide.with(context).load(url).crossFade().centerCrop().into(viewHolder.iv_image);
                }
            }

            String tv_answer_count = questionBean.getNormal_ans_count() + " 回答";
            String tv_follow_count = questionBean.getFollow_count() + " 关注";
            viewHolder.tv_title.setText(tv_title);
            viewHolder.tv_abstract.setText(tv_abstract);
            viewHolder.tv_answer_count.setText(tv_answer_count);
            viewHolder.tv_follow_count.setText(tv_follow_count);
        }

        if (holder instanceof NormalViewHolder) {
            NormalViewHolder viewHolder = (NormalViewHolder) holder;
            WendaContentBean.AnsListBean bean = list.get(position);

            if (!SettingsUtil.getInstance().getIsNoPhotoMode()) {
                String iv_user_avatar = bean.getUser().getAvatar_url();
                Glide.with(context).load(iv_user_avatar).crossFade().centerCrop().into(viewHolder.iv_user_avatar);
            }
            String tv_user_name = bean.getUser().getUname();
            String tv_like_count = bean.getDigg_count() + "";
            String tv_abstract = bean.getContent_abstract().getText();
            viewHolder.tv_user_name.setText(tv_user_name);
            viewHolder.tv_like_count.setText(tv_like_count);
            viewHolder.tv_abstract.setText(tv_abstract);
        }
    }

    @Override
    public int getItemCount() {
        return list != null ? list.size() + 1 : 0;
    }

    private class HeaderViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        private TextView tv_title;
        private TextView tv_abstract;
        private ImageView iv_image;
        private TextView tv_answer_count;
        private TextView tv_follow_count;

        HeaderViewHolder(View view) {
            super(view);
            this.tv_title = (TextView) view.findViewById(R.id.tv_title);
            this.tv_abstract = (TextView) view.findViewById(R.id.tv_abstract);
            this.iv_image = (ImageView) view.findViewById(R.id.iv_image);
            this.tv_answer_count = (TextView) view.findViewById(R.id.tv_answer_count);
            this.tv_follow_count = (TextView) view.findViewById(R.id.tv_follow_count);
            view.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {

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

    private class NormalViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        private CircleImageView iv_user_avatar;
        private TextView tv_user_name;
        private TextView tv_like_count;
        private TextView tv_abstract;
        private IOnItemClickListener onItemClickListener;

        NormalViewHolder(View view, IOnItemClickListener onItemClickListener) {
            super(view);
            this.iv_user_avatar = (CircleImageView) view.findViewById(R.id.iv_user_avatar);
            this.tv_user_name = (TextView) view.findViewById(R.id.tv_user_name);
            this.tv_like_count = (TextView) view.findViewById(R.id.tv_like_count);
            this.tv_abstract = (TextView) view.findViewById(R.id.tv_abstract);
            this.onItemClickListener = onItemClickListener;
            view.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {
            if (onItemClickListener != null) {
                onItemClickListener.onClick(v, getLayoutPosition());
            }
        }
    }
}
