package com.meiji.toutiao.binder.media;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.meiji.toutiao.ErrorAction;
import com.meiji.toutiao.R;
import com.meiji.toutiao.bean.media.MultiMediaArticleBean;
import com.meiji.toutiao.utils.ImageLoader;
import com.meiji.toutiao.utils.TimeUtil;

import java.util.List;

import me.drakeet.multitype.ItemViewBinder;

/**
 * Created by Meiji on 2017/6/8.
 */

public class MediaArticleHasVideoViewBinder extends ItemViewBinder<MultiMediaArticleBean.DataBean, MediaArticleHasVideoViewBinder.ViewHolder> {

    private static final String TAG = "NewsArticleHasVideoView";

    @NonNull
    @Override
    protected MediaArticleHasVideoViewBinder.ViewHolder onCreateViewHolder(@NonNull LayoutInflater inflater, @NonNull ViewGroup parent) {
        View view = inflater.inflate(R.layout.item_media_article_new_has_video, parent, false);
        return new ViewHolder(view);
    }

    @Override
    protected void onBindViewHolder(@NonNull ViewHolder holder, @NonNull final MultiMediaArticleBean.DataBean item) {
        try {
            List<MultiMediaArticleBean.DataBean.ImageListBean> imageList = item.getImage_list();
            if (imageList != null && imageList.size() > 0) {
                String url = imageList.get(0).getUrl();
                ImageLoader.loadCenterCrop(holder.itemView.getContext(), url, holder.iv_video_image, R.color.viewBackground);
            }

            String title = item.getTitle();
            String readCount = item.getTotal_read_count() + "阅读量";
            String commentCount = item.getComment_count() + "评论";
            String datetime = item.getBehot_time() + "";
            if (!TextUtils.isEmpty(datetime)) {
                datetime = TimeUtil.getTimeStampAgo(datetime);
            }
            String video_time = item.getVideo_duration_str();

            holder.tv_title.setText(title);
            holder.tv_extra.setText(readCount + " - " + commentCount + " - " + datetime);
            holder.tv_video_time.setText(video_time);
            holder.itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
//                    VideoArticleBean.DataBean dataBean = new VideoArticleBean.DataBean();
//                    dataBean.setTitle(item.getTitle());
//                    dataBean.setGroup_id(item.getGroup_id());
//                    dataBean.setItem_id(item.getGroup_id());
//                    dataBean.setVideo_id(item.getVideo_id());
//                    dataBean.setAbstractX(item.getAbstractX());
//                    dataBean.setSource(item.getSource());
//                    dataBean.setVideo_duration_str(item.getVideo_duration() / 60 + "");
//                    String url = item.getVideo_detail_info().getDetail_video_large_image().getUrl();
//                    VideoContentActivity.launch(dataBean, url);
//                    Log.d(TAG, "doOnClickItem: " + url);
                }
            });
        } catch (Exception e) {
            ErrorAction.print(e);
        }
    }

    class ViewHolder extends RecyclerView.ViewHolder {

        private TextView tv_extra;
        private TextView tv_title;
        private ImageView iv_video_image;
        private TextView tv_video_time;

        ViewHolder(View itemView) {
            super(itemView);
            this.tv_extra = (TextView) itemView.findViewById(R.id.tv_extra);
            this.tv_title = (TextView) itemView.findViewById(R.id.tv_title);
            this.iv_video_image = (ImageView) itemView.findViewById(R.id.iv_video_image);
            this.tv_video_time = (TextView) itemView.findViewById(R.id.tv_video_time);
        }
    }
}
