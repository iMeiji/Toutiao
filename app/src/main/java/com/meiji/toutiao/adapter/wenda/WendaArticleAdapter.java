package com.meiji.toutiao.adapter.wenda;

import android.content.Context;
import android.graphics.PorterDuff;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.support.v4.graphics.drawable.DrawableCompat;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.meiji.toutiao.R;
import com.meiji.toutiao.bean.wenda.WendaArticleDataBean;
import com.meiji.toutiao.interfaces.IOnItemClickListener;
import com.meiji.toutiao.utils.SettingsUtil;
import com.meiji.toutiao.utils.TimeUtil;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Meiji on 2017/5/21.
 */

public class WendaArticleAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private static final int TYPE_THREE_IMAGE = 0;
    private static final int TYPE_FOOTER = 1;
    private static final int TYPE_LARGE_IMAGE = 2;
    private static final int TYPE_NO_IMAGE = 3;
    private List<WendaArticleDataBean> list;
    private IOnItemClickListener onItemClickListener;
    private Context context;

    public WendaArticleAdapter(Context context) {
        this.context = context;
    }

    public List<WendaArticleDataBean> getList() {
        return list;
    }

    public void setList(List<WendaArticleDataBean> list) {
        this.list = new ArrayList<>(list);
    }

    public void setOnItemClickListener(IOnItemClickListener listener) {
        this.onItemClickListener = listener;
    }

    @Override
    public int getItemViewType(int position) {
        if (position == list.size()) {
            return TYPE_FOOTER;
        }
        if (list.get(position).getExtraBean().getWenda_image().getThree_image_list().size() > 0) {
            return TYPE_THREE_IMAGE;
        }
        if (list.get(position).getExtraBean().getWenda_image().getLarge_image_list().size() > 0) {
            return TYPE_LARGE_IMAGE;
        }
        return TYPE_NO_IMAGE;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        if (viewType == TYPE_THREE_IMAGE) {
            View view = LayoutInflater.from(context).inflate(R.layout.item_wenda_article_three_image, parent, false);
            return new ThreeImageViewHolder(view, onItemClickListener);
        }
        if (viewType == TYPE_FOOTER) {
            View view = LayoutInflater.from(context).inflate(R.layout.list_footer, parent, false);
            return new FooterViewHolder(view);
        }
        if (viewType == TYPE_LARGE_IMAGE) {
            View view = LayoutInflater.from(context).inflate(R.layout.item_wenda_article_large_image, parent, false);
            return new LargeImageViewHolder(view, onItemClickListener);
        }
        if (viewType == TYPE_NO_IMAGE) {
            View view = LayoutInflater.from(context).inflate(R.layout.item_wenda_article_no_image, parent, false);
            return new NoImageViewHolder(view, onItemClickListener);
        }
        return null;
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {

        try {

            if (holder instanceof ThreeImageViewHolder) {
                ThreeImageViewHolder viewHolder = (ThreeImageViewHolder) holder;
                WendaArticleDataBean bean = list.get(position);

                if (!SettingsUtil.getInstance().getIsNoPhotoMode()) {

                    int size = bean.getExtraBean().getWenda_image().getThree_image_list().size();
                    String[] ivs = new String[size];
                    for (int i = 0; i < size; i++) {
                        ivs[i] = bean.getExtraBean().getWenda_image().getThree_image_list().get(i).getUrl();
                    }
                    switch (ivs.length) {
                        case 1:
                            Glide.with(context).load(ivs[0]).crossFade().centerCrop().into(viewHolder.iv_0);
                            break;
                        case 2:
                            Glide.with(context).load(ivs[0]).crossFade().centerCrop().into(viewHolder.iv_0);
                            Glide.with(context).load(ivs[1]).crossFade().centerCrop().into(viewHolder.iv_1);
                            break;
                        case 3:
                            Glide.with(context).load(ivs[0]).crossFade().centerCrop().into(viewHolder.iv_0);
                            Glide.with(context).load(ivs[1]).crossFade().centerCrop().into(viewHolder.iv_1);
                            Glide.with(context).load(ivs[2]).crossFade().centerCrop().into(viewHolder.iv_2);
                            break;
                    }
                }

                String tv_title = bean.getQuestionBean().getTitle();
                String tv_answer_count = bean.getQuestionBean().getNormal_ans_count() + "回答";
                String tv_datetime = bean.getQuestionBean().getCreate_time() + "";
                if (!TextUtils.isEmpty(tv_datetime)) {
                    tv_datetime = TimeUtil.getTimeStampAgo(tv_datetime);
                }
                viewHolder.tv_title.setText(tv_title);
                viewHolder.tv_answer_count.setText(tv_answer_count);
                viewHolder.tv_time.setText(tv_datetime);
            }

            if (holder instanceof LargeImageViewHolder) {
                LargeImageViewHolder viewHolder = (LargeImageViewHolder) holder;
                WendaArticleDataBean bean = list.get(position);

                if (!SettingsUtil.getInstance().getIsNoPhotoMode()) {
                    String url = bean.getExtraBean().getWenda_image().getLarge_image_list().get(0).getUrl();
                    Glide.with(context).load(url).crossFade().centerCrop().into(viewHolder.iv_image_big);
                }

                String tv_title = bean.getQuestionBean().getTitle();
                String tv_answer_count = bean.getQuestionBean().getNormal_ans_count() + "回答";
                String tv_datetime = bean.getQuestionBean().getCreate_time() + "";
                if (!TextUtils.isEmpty(tv_datetime)) {
                    tv_datetime = TimeUtil.getTimeStampAgo(tv_datetime);
                }
                viewHolder.tv_title.setText(tv_title);
                viewHolder.tv_answer_count.setText(tv_answer_count);
                viewHolder.tv_time.setText(tv_datetime);
            }

            if (holder instanceof NoImageViewHolder) {
                NoImageViewHolder viewHolder = (NoImageViewHolder) holder;
                WendaArticleDataBean bean = list.get(position);

                String tv_title = bean.getQuestionBean().getTitle();
                String tv_answer_count = bean.getQuestionBean().getNormal_ans_count() + "回答";
                String tv_datetime = bean.getQuestionBean().getCreate_time() + "";
                if (!TextUtils.isEmpty(tv_datetime)) {
                    tv_datetime = TimeUtil.getTimeStampAgo(tv_datetime);
                }
                String tv_content = bean.getAnswerBean().getAbstractX();
                viewHolder.tv_title.setText(tv_title);
                viewHolder.tv_answer_count.setText(tv_answer_count);
                viewHolder.tv_time.setText(tv_datetime);
                viewHolder.tv_content.setText(tv_content);
            }
        } catch (Exception e) {

        }
    }

    @Override
    public int getItemCount() {
        return list != null ? list.size() + 1 : 0;
    }

    private class ThreeImageViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        private TextView tv_title;
        private ImageView iv_0;
        private ImageView iv_1;
        private ImageView iv_2;
        private TextView tv_answer_count;
        private TextView tv_time;
        private IOnItemClickListener onItemClickListener;

        private ThreeImageViewHolder(View view, IOnItemClickListener onItemClickListener) {
            super(view);
            this.tv_title = (TextView) view.findViewById(R.id.tv_title);
            this.iv_0 = (ImageView) view.findViewById(R.id.iv_0);
            this.iv_1 = (ImageView) view.findViewById(R.id.iv_1);
            this.iv_2 = (ImageView) view.findViewById(R.id.iv_2);
            this.tv_answer_count = (TextView) view.findViewById(R.id.tv_answer_count);
            this.tv_time = (TextView) view.findViewById(R.id.tv_time);
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

    private class LargeImageViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        private TextView tv_title;
        private ImageView iv_image_big;
        private TextView tv_answer_count;
        private TextView tv_time;
        private IOnItemClickListener onItemClickListener;

        private LargeImageViewHolder(View view, IOnItemClickListener onItemClickListener) {
            super(view);
            this.tv_title = (TextView) view.findViewById(R.id.tv_title);
            this.iv_image_big = (ImageView) view.findViewById(R.id.iv_image_big);
            this.tv_answer_count = (TextView) view.findViewById(R.id.tv_answer_count);
            this.tv_time = (TextView) view.findViewById(R.id.tv_time);
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

    private class NoImageViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        private TextView tv_title;
        private TextView tv_content;
        private TextView tv_answer_count;
        private TextView tv_time;
        private IOnItemClickListener onItemClickListener;

        private NoImageViewHolder(View view, IOnItemClickListener onItemClickListener) {
            super(view);
            this.tv_title = (TextView) view.findViewById(R.id.tv_title);
            this.tv_content = (TextView) view.findViewById(R.id.tv_content);
            this.tv_answer_count = (TextView) view.findViewById(R.id.tv_answer_count);
            this.tv_time = (TextView) view.findViewById(R.id.tv_time);
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
