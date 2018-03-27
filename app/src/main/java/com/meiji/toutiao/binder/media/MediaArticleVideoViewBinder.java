package com.meiji.toutiao.binder.media;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.PopupMenu;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.jakewharton.rxbinding2.view.RxView;
import com.meiji.toutiao.ErrorAction;
import com.meiji.toutiao.IntentAction;
import com.meiji.toutiao.R;
import com.meiji.toutiao.bean.media.MultiMediaArticleBean;
import com.meiji.toutiao.bean.news.MultiNewsArticleDataBean;
import com.meiji.toutiao.module.video.content.VideoContentActivity;
import com.meiji.toutiao.util.ImageLoader;
import com.meiji.toutiao.util.NetWorkUtil;
import com.meiji.toutiao.util.SettingUtil;
import com.meiji.toutiao.util.TimeUtil;

import java.util.List;
import java.util.concurrent.TimeUnit;

import io.reactivex.schedulers.Schedulers;
import me.drakeet.multitype.ItemViewBinder;

/**
 * Created by Meiji on 2017/6/8.
 */

public class MediaArticleVideoViewBinder extends ItemViewBinder<MultiMediaArticleBean.DataBean, MediaArticleVideoViewBinder.ViewHolder> {

    private static final String TAG = "NewsArticleHasVideoView";

    @NonNull
    @Override
    protected MediaArticleVideoViewBinder.ViewHolder onCreateViewHolder(@NonNull LayoutInflater inflater, @NonNull ViewGroup parent) {
        View view = inflater.inflate(R.layout.item_media_article_video, parent, false);
        return new ViewHolder(view);
    }

    @Override
    protected void onBindViewHolder(@NonNull final ViewHolder holder, @NonNull final MultiMediaArticleBean.DataBean item) {

        final Context context = holder.itemView.getContext();

        try {
            List<MultiMediaArticleBean.DataBean.ImageListBean> imageList = item.getImage_list();
            String url = null;
            if (imageList != null && imageList.size() > 0) {
                url = imageList.get(0).getUrl();
                if (!TextUtils.isEmpty(url)) {
                    if (NetWorkUtil.isWifiConnected(context)) {
                        // 加载高清图
                        url = url.replace("list", "large");
                    }
                    ImageLoader.loadCenterCrop(context, url, holder.iv_video_image, R.color.viewBackground);
                }
            }

            final String title = item.getTitle();
            String readCount = item.getTotal_read_count() + "阅读量";
            String commentCount = item.getComment_count() + "评论";
            String datetime = item.getBehot_time() + "";
            if (!TextUtils.isEmpty(datetime)) {
                datetime = TimeUtil.getTimeStampAgo(datetime);
            }
            String video_time = item.getVideo_duration_str();

            holder.tv_title.setText(title);
            holder.tv_title.setTextSize(SettingUtil.getInstance().getTextSize());
            holder.tv_extra.setText(readCount + " - " + commentCount + " - " + datetime);
            holder.tv_video_time.setText(video_time);

            final String finalUrl = url;
            RxView.clicks(holder.itemView)
                    .throttleFirst(1, TimeUnit.SECONDS)
                    .observeOn(Schedulers.io())
                    .subscribe(o -> {
                        MultiNewsArticleDataBean bean = new MultiNewsArticleDataBean();
                        bean.setTitle(title);
                        bean.setGroup_id(item.getGroup_id());
                        bean.setItem_id(item.getItem_id());
                        bean.setVideo_id(item.getVideo_infos().get(0).getVid());
                        bean.setAbstractX(item.getAbstractX());
                        bean.setSource(item.getSource());

                        String s = item.getVideo_duration_str();
                        int time = 0;
                        if (s.contains(":")) {
                            String[] split = s.split(":");
                            for (int i = 0; i < split.length; i++) {
                                if (i == 0) {
                                    time = Integer.parseInt(split[i]) * 60;
                                }
                                time += Integer.parseInt(split[i]);
                            }
                        }
                        bean.setVideo_duration(time);

                        MultiNewsArticleDataBean.MediaInfoBean mediaInfoBean = new MultiNewsArticleDataBean.MediaInfoBean();
                        mediaInfoBean.setMedia_id(item.getMedia_id() + "");
                        bean.setMedia_info(mediaInfoBean);

                        MultiNewsArticleDataBean.VideoDetailInfoBean.DetailVideoLargeImageBean videobean = new MultiNewsArticleDataBean.VideoDetailInfoBean.DetailVideoLargeImageBean();
                        MultiNewsArticleDataBean.VideoDetailInfoBean videoDetail = new MultiNewsArticleDataBean.VideoDetailInfoBean();
                        videobean.setUrl(finalUrl);
                        videoDetail.setDetail_video_large_image(videobean);
                        bean.setVideo_detail_info(videoDetail);

                        VideoContentActivity.launch(bean);
                    }, throwable -> {
                        Toast.makeText(context, context.getString(R.string.error), Toast.LENGTH_SHORT).show();
                        ErrorAction.print(throwable);
                    });
            holder.iv_dots.setOnClickListener(view -> {
                PopupMenu popupMenu = new PopupMenu(context,
                        holder.iv_dots, Gravity.END, 0, R.style.MyPopupMenu);
                popupMenu.inflate(R.menu.menu_share);
                popupMenu.setOnMenuItemClickListener(menu -> {
                    int itemId = menu.getItemId();
                    if (itemId == R.id.action_share) {
                        IntentAction.send(context, item.getTitle() + "\n" + item.getDisplay_url());
                    }
                    return false;
                });
                popupMenu.show();
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
        private ImageView iv_dots;


        ViewHolder(View itemView) {
            super(itemView);
            this.tv_extra = itemView.findViewById(R.id.tv_extra);
            this.tv_title = itemView.findViewById(R.id.tv_title);
            this.iv_video_image = itemView.findViewById(R.id.iv_video_image);
            this.tv_video_time = itemView.findViewById(R.id.tv_video_time);
            this.iv_dots = itemView.findViewById(R.id.iv_dots);
        }
    }
}
