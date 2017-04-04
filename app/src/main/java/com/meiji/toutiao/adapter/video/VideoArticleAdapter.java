package com.meiji.toutiao.adapter.video;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.support.v7.widget.PopupMenu;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.meiji.toutiao.R;
import com.meiji.toutiao.bean.video.VideoArticleBean;
import com.meiji.toutiao.interfaces.IOnItemClickListener;
import com.meiji.toutiao.utils.SettingsUtil;
import com.meiji.toutiao.utils.TimeUtil;
import com.meiji.toutiao.view.CircleImageView;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Meiji on 2017/3/29.
 */

public class VideoArticleAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private static final int TYPE_NORMAL = 0;
    private static final int TYPE_FOOTER = 1;
    private List<VideoArticleBean.DataBean> list = new ArrayList<>();
    private IOnItemClickListener onItemClickListener;
    private Context context;

    public VideoArticleAdapter(Context context, List<VideoArticleBean.DataBean> list) {
        this.list = list;
        this.context = context;
    }

    public void setOnItemClickListener(IOnItemClickListener listener) {
        this.onItemClickListener = listener;
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
            View view = LayoutInflater.from(context).inflate(R.layout.item_video_article, parent, false);
            return new VideoArticleViewHolder(view, onItemClickListener);
        }
        if (viewType == TYPE_FOOTER) {
            View view = LayoutInflater.from(context).inflate(R.layout.list_footer, parent, false);
            return new FooterViewHolder(view);
        }
        return null;
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {

        if (holder instanceof VideoArticleViewHolder) {

            VideoArticleViewHolder viewHolder = (VideoArticleViewHolder) holder;
            final VideoArticleBean.DataBean bean = list.get(position);

            if (!SettingsUtil.getInstance().getIsNoPhotoMode()) {
                //String image_url = bean.getImage_url();
                try {
                    String url = bean.getVideo_detail_info().getVideo_detail_info().getDetail_video_large_image().getUrl();
                    if (!TextUtils.isEmpty(url)) {
                        Glide.with(context).load(url).crossFade().centerCrop().error(R.mipmap.error_image).into(viewHolder.iv_image_url);
                    }
                } catch (NullPointerException e) {
                    e.printStackTrace();
                }

                String media_avatar_url = bean.getMedia_avatar_url();
                if (!TextUtils.isEmpty(media_avatar_url)) {
                    Glide.with(context).load(media_avatar_url).crossFade().centerCrop().error(R.mipmap.error_image).into(viewHolder.iv_media_avatar_url);
                }
            }

            String title = bean.getTitle();
            String source = bean.getSource();
            String video_duration_str = bean.getVideo_duration_str();
            String external_visit_count = bean.getExternal_visit_count() + "次观看";
            String tv_datetime = bean.getBehot_time() + "";
            if (!TextUtils.isEmpty(tv_datetime)) {
                tv_datetime = TimeUtil.getTimeStampAgo(tv_datetime);
            }
            String tv_description = source + " - " + external_visit_count + " - " + tv_datetime;

            viewHolder.tv_title.setText(title);
            viewHolder.tv_description.setText(tv_description);
            viewHolder.tv_video_duration_str.setText(video_duration_str);
            viewHolder.iv_menu.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    PopupMenu popupMenu = new PopupMenu(context, v);
                    popupMenu.inflate(R.menu.menu_video);
                    popupMenu.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
                        @Override
                        public boolean onMenuItemClick(MenuItem item) {
                            String shareTitle = bean.getTitle();
                            String shareUrl = bean.getDisplay_url();
                            switch (item.getItemId()) {
                                case R.id.action_follow_media:
                                    break;

                                case R.id.action_share:
                                    Intent shareIntent = new Intent()
                                            .setAction(Intent.ACTION_SEND)
                                            .setType("text/plain")
                                            .putExtra(Intent.EXTRA_TEXT, shareTitle + "\n" + shareUrl);
                                    context.startActivity(Intent.createChooser(shareIntent, context.getString(R.string.share_to)));
                                    break;

                                case R.id.action_open_in_browser:
                                    context.startActivity(new Intent(Intent.ACTION_VIEW).setData(Uri.parse(shareUrl)));
                                    break;
                            }
                            return false;
                        }
                    });
                    popupMenu.show();
                }
            });
        }
    }

    @Override
    public int getItemCount() {
        return list != null ? list.size() + 1 : 0;
    }

    private class VideoArticleViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        private ImageView iv_image_url;
        private CircleImageView iv_media_avatar_url;
        private TextView tv_video_duration_str;
        private TextView tv_title;
        private TextView tv_description;
        private ImageView iv_menu;
        private IOnItemClickListener onItemClickListener;

        private VideoArticleViewHolder(View view, IOnItemClickListener onItemClickListener) {
            super(view);
            this.iv_image_url = (ImageView) view.findViewById(R.id.iv_image_url);
            this.iv_menu = (ImageView) view.findViewById(R.id.iv_menu);
            this.tv_video_duration_str = (TextView) view.findViewById(R.id.tv_video_duration_str);
            this.iv_media_avatar_url = (CircleImageView) view.findViewById(R.id.iv_media_avatar_url);
            this.tv_title = (TextView) view.findViewById(R.id.tv_title);
            this.tv_description = (TextView) view.findViewById(R.id.tv_description);
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

        FooterViewHolder(View itemView) {
            super(itemView);
        }
    }
}
