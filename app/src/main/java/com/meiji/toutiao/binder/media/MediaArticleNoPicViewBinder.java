package com.meiji.toutiao.binder.media;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.meiji.toutiao.ErrorAction;
import com.meiji.toutiao.R;
import com.meiji.toutiao.bean.media.MultiMediaArticleBean;
import com.meiji.toutiao.bean.news.MultiNewsArticleDataBean;
import com.meiji.toutiao.module.news.content.NewsContentActivity;
import com.meiji.toutiao.util.TimeUtil;

import me.drakeet.multitype.ItemViewBinder;

/**
 * Created by Meiji on 2017/6/8.
 * 不带图片的 item
 */

public class MediaArticleNoPicViewBinder extends ItemViewBinder<MultiMediaArticleBean.DataBean, MediaArticleNoPicViewBinder.ViewHolder> {

    @NonNull
    @Override
    protected MediaArticleNoPicViewBinder.ViewHolder onCreateViewHolder(@NonNull LayoutInflater inflater, @NonNull ViewGroup parent) {
        View view = inflater.inflate(R.layout.item_media_article_new_no_pic, parent, false);
        return new ViewHolder(view);
    }

    @Override
    protected void onBindViewHolder(@NonNull MediaArticleNoPicViewBinder.ViewHolder holder, @NonNull final MultiMediaArticleBean.DataBean item) {

        try {
            String title = item.getTitle();
            String abstractX = item.getAbstractX();
            String readCount = item.getTotal_read_count() + "阅读量";
            String countmmentCount = item.getComment_count() + "评论";
            String time = item.getBehot_time() + "";
            if (!TextUtils.isEmpty(time)) {
                time = TimeUtil.getTimeStampAgo(time);
            }

            holder.tv_title.setText(title);
            holder.tv_abstract.setText(abstractX);
            holder.tv_extra.setText(readCount + " - " + countmmentCount + " - " + time);
            holder.itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    MultiNewsArticleDataBean bean = new MultiNewsArticleDataBean();
                    bean.setTitle(item.getTitle());
                    bean.setDisplay_url(item.getDisplay_url());
                    bean.setMedia_name(item.getSource());
                    MultiNewsArticleDataBean.MediaInfoBean mediaInfo = new MultiNewsArticleDataBean.MediaInfoBean();
                    mediaInfo.setMedia_id(item.getMedia_id() + "");
                    bean.setMedia_info(mediaInfo);
                    bean.setGroup_id(item.getGroup_id());
                    bean.setItem_id(item.getItem_id());
                    NewsContentActivity.launch(bean);
                }
            });
        } catch (Exception e) {
            ErrorAction.print(e);
        }
    }

    class ViewHolder extends RecyclerView.ViewHolder {

        private TextView tv_extra;
        private TextView tv_title;
        private TextView tv_abstract;

        ViewHolder(View itemView) {
            super(itemView);
            this.tv_extra = (TextView) itemView.findViewById(R.id.tv_extra);
            this.tv_title = (TextView) itemView.findViewById(R.id.tv_title);
            this.tv_abstract = (TextView) itemView.findViewById(R.id.tv_abstract);
        }
    }
}
