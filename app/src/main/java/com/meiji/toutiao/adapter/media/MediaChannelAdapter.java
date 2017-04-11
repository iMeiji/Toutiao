package com.meiji.toutiao.adapter.media;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.meiji.toutiao.R;
import com.meiji.toutiao.bean.media.MediaChannelBean;
import com.meiji.toutiao.interfaces.IOnItemClickListener;
import com.meiji.toutiao.utils.SettingsUtil;
import com.meiji.toutiao.view.CircleImageView;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Meiji on 2017/4/9.
 */

public class MediaChannelAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private List<MediaChannelBean> list = new ArrayList<>();
    private Context context;
    private IOnItemClickListener onItemClickListener;

    public MediaChannelAdapter(List<MediaChannelBean> list, Context context) {
        this.list = list;
        this.context = context;
    }

    public void setOnItemClickListener(IOnItemClickListener onItemClickListener) {
        this.onItemClickListener = onItemClickListener;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_media_channel, parent, false);
        return new MediaChannelViewHolder(view, onItemClickListener);
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        MediaChannelViewHolder viewHolder = (MediaChannelViewHolder) holder;
        MediaChannelBean bean = list.get(position);
        if (!SettingsUtil.getInstance().getIsNoPhotoMode()) {
            String url = bean.getAvatar();
            Glide.with(context).load(url).crossFade().centerCrop().error(R.mipmap.error_image).into(viewHolder.cv_avatar);
        }
        viewHolder.tv_mediaName.setText(bean.getName());
        viewHolder.tv_descText.setText(bean.getDescText());
    }

    @Override
    public int getItemCount() {
        return list != null ? list.size() : 0;
    }

    private class MediaChannelViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        private CircleImageView cv_avatar;
        private TextView tv_mediaName;
        private TextView tv_followCount;
        private TextView tv_descText;
        private IOnItemClickListener onItemClickListener;

        MediaChannelViewHolder(View itemView, IOnItemClickListener onItemClickListener) {
            super(itemView);
            this.cv_avatar = (CircleImageView) itemView.findViewById(R.id.cv_avatar);
            this.tv_mediaName = (TextView) itemView.findViewById(R.id.tv_mediaName);
            this.tv_followCount = (TextView) itemView.findViewById(R.id.tv_followCount);
            this.tv_descText = (TextView) itemView.findViewById(R.id.tv_descText);
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
}
