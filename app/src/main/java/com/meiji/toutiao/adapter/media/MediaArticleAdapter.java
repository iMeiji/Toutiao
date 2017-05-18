package com.meiji.toutiao.adapter.media;

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
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.meiji.toutiao.R;
import com.meiji.toutiao.bean.media.MediaArticleBean;
import com.meiji.toutiao.interfaces.IOnItemClickListener;
import com.meiji.toutiao.utils.SettingsUtil;
import com.meiji.toutiao.utils.TimeUtil;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Meiji on 2017/4/11.
 */

public class MediaArticleAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private static final int TYPE_NORMAL = 0;
    private static final int TYPE_FOOTER = 1;
    private Context context;
    private List<MediaArticleBean.DataBean> list;
    private IOnItemClickListener onItemClickListener;

    public MediaArticleAdapter(Context context) {
        this.context = context;
    }

    public List<MediaArticleBean.DataBean> getList() {
        return list;
    }

    public void setList(List<MediaArticleBean.DataBean> list) {
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
            View view = LayoutInflater.from(context).inflate(R.layout.item_media_article, parent, false);
            return new MediaArticleViewHolder(view, onItemClickListener);
        }
        if (viewType == TYPE_FOOTER) {
            View view = LayoutInflater.from(context).inflate(R.layout.list_footer, parent, false);
            return new FooterViewHolder(view);
        }
        return null;
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        if (holder instanceof MediaArticleViewHolder) {
            MediaArticleViewHolder viewHolder = (MediaArticleViewHolder) holder;
            MediaArticleBean.DataBean bean = list.get(position);
            if (!SettingsUtil.getInstance().getIsNoPhotoMode()) {

                if (bean.getImage_list() != null) {
                    List<MediaArticleBean.DataBean.ImageListBean> image_list = bean.getImage_list();

                    if (image_list.size() == 1) {
                        String url = image_list.get(0).getPc_url();
                        viewHolder.iv_imageBig.setVisibility(View.VISIBLE);
                        viewHolder.view_image.setVisibility(View.GONE);
                        Glide.with(context).load(url).crossFade().centerCrop().error(R.color.viewBackground).into(viewHolder.iv_imageBig);
                    } else {
                        int size = image_list.size();
                        String[] ivs = new String[size];
                        for (int i = 0; i < size; i++) {
                            ivs[i] = image_list.get(i).getUrl();
                        }
                        switch (ivs.length) {
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

                }
            }
            String tv_title = bean.getTitle();
            String tv_abstract = bean.getAbstractX();
            String tv_datetime = bean.getDatetime();
            String tv_comments_count = bean.getComments_count() + "评论";
            if (!TextUtils.isEmpty(tv_datetime)) {
                tv_datetime = TimeUtil.timeAgo(TimeUtil.stringConvertDate(tv_datetime));
            }
            viewHolder.tv_title.setText(tv_title);
            if (!TextUtils.isEmpty(tv_abstract)) {
                viewHolder.tv_abstract.setText(tv_abstract);
            } else {
                viewHolder.tv_abstract.setVisibility(View.GONE);
            }
            viewHolder.tv_dataTime.setText(tv_datetime);
            viewHolder.tv_comments_count.setText(tv_comments_count);
        }
    }

    @Override
    public int getItemCount() {
        return list != null ? list.size() + 1 : 0;
    }

    private class MediaArticleViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        private TextView tv_title;
        private ImageView iv_imageBig;
        private ImageView iv_0;
        private ImageView iv_1;
        private ImageView iv_2;
        private LinearLayout view_image;
        private TextView tv_abstract;
        private TextView tv_dataTime;
        private TextView tv_comments_count;
        private IOnItemClickListener onItemClickListener;

        MediaArticleViewHolder(View itemView, IOnItemClickListener onItemClickListener) {
            super(itemView);
            this.tv_title = (TextView) itemView.findViewById(R.id.tv_title);
            this.iv_imageBig = (ImageView) itemView.findViewById(R.id.iv_imageBig);
            this.iv_0 = (ImageView) itemView.findViewById(R.id.iv_0);
            this.iv_1 = (ImageView) itemView.findViewById(R.id.iv_1);
            this.iv_2 = (ImageView) itemView.findViewById(R.id.iv_2);
            this.view_image = (LinearLayout) itemView.findViewById(R.id.view_image);
            this.tv_abstract = (TextView) itemView.findViewById(R.id.tv_abstract);
            this.tv_dataTime = (TextView) itemView.findViewById(R.id.tv_dataTime);
            this.tv_comments_count = (TextView) itemView.findViewById(R.id.tv_comments_count);
            this.onItemClickListener = onItemClickListener;
            itemView.setOnClickListener(this);
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
}
