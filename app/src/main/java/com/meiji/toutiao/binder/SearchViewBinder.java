package com.meiji.toutiao.binder;

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
import com.meiji.toutiao.bean.news.MultiNewsArticleDataBean;
import com.meiji.toutiao.bean.search.SearchBean;
import com.meiji.toutiao.module.news.content.NewsContentActivity;
import com.meiji.toutiao.utils.ImageLoader;
import com.meiji.toutiao.utils.TimeUtil;
import com.meiji.toutiao.widget.CircleImageView;

import me.drakeet.multitype.ItemViewBinder;

/**
 * Created by Meiji on 2017/6/13.
 */

public class SearchViewBinder extends ItemViewBinder<SearchBean.DataBeanX, SearchViewBinder.ViewHolder> {


    @NonNull
    @Override
    protected SearchViewBinder.ViewHolder onCreateViewHolder(@NonNull LayoutInflater inflater, @NonNull ViewGroup parent) {
        View view = inflater.inflate(R.layout.item_news_article, parent, false);
        return new ViewHolder(view);
    }

    @Override
    protected void onBindViewHolder(@NonNull SearchViewBinder.ViewHolder holder, @NonNull final SearchBean.DataBeanX item) {
        try {

            if (item.getImage_list() != null && item.getImage_list().size() > 0) {
                String url = item.getImage_list().get(0).getUrl();
                ImageLoader.loadCenterCrop(holder.itemView.getContext(), url, holder.iv_image, R.color.viewBackground);
            }

            String tv_title = item.getTitle();
            String tv_abstract = item.getAbstractX();
            String tv_source = item.getSource();
            String tv_comment_count = item.getComment_count() + "评论";
            String tv_datetime = item.getBehot_time() + "";
            if (!TextUtils.isEmpty(tv_datetime)) {
                tv_datetime = TimeUtil.getTimeStampAgo(tv_datetime);
            }

            holder.tv_title.setText(tv_title);
            holder.tv_abstract.setText(tv_abstract);
            holder.tv_extra.setText(tv_source + " - " + tv_comment_count + " - " + tv_datetime);
            holder.itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
//                    NewsArticleBean.DataBean dataBean = new NewsArticleBean.DataBean();
//                    dataBean.setDisplay_url(item.getDisplay_url());
//                    dataBean.setTitle(item.getTitle());
//                    dataBean.setMedia_name(item.getMedia_name());
//                    dataBean.setMedia_url(item.getMedia_url());
//                    dataBean.setGroup_id(item.getGroup_id());
//                    dataBean.setItem_id(item.getGroup_id());
//                    NewsContentActivity.launch(dataBean);
                    MultiNewsArticleDataBean bean = new MultiNewsArticleDataBean();
                    bean.setTitle(item.getTitle());
                    bean.setDisplay_url(item.getDisplay_url());
                    bean.setMedia_name(item.getMedia_name());
                    MultiNewsArticleDataBean.MediaInfoBean mediaInfo = new MultiNewsArticleDataBean.MediaInfoBean();
                    mediaInfo.setMedia_id(item.getMedia_url());
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

    public class ViewHolder extends RecyclerView.ViewHolder {

        private CircleImageView iv_media;
        private ImageView iv_image;
        private TextView tv_title;
        private TextView tv_abstract;
        private TextView tv_extra;

        ViewHolder(View itemView) {
            super(itemView);
            this.iv_media = (CircleImageView) itemView.findViewById(R.id.iv_media);
            this.iv_image = (ImageView) itemView.findViewById(R.id.iv_image);
            this.tv_title = (TextView) itemView.findViewById(R.id.tv_title);
            this.tv_abstract = (TextView) itemView.findViewById(R.id.tv_abstract);
            this.tv_extra = (TextView) itemView.findViewById(R.id.tv_extra);
        }
    }
}
