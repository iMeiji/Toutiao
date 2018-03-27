package com.meiji.toutiao.binder.news;

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

import com.jakewharton.rxbinding2.view.RxView;
import com.meiji.toutiao.ErrorAction;
import com.meiji.toutiao.IntentAction;
import com.meiji.toutiao.R;
import com.meiji.toutiao.bean.news.MultiNewsArticleDataBean;
import com.meiji.toutiao.module.news.content.NewsContentActivity;
import com.meiji.toutiao.util.ImageLoader;
import com.meiji.toutiao.util.SettingUtil;
import com.meiji.toutiao.util.TimeUtil;
import com.meiji.toutiao.widget.CircleImageView;

import java.util.List;
import java.util.concurrent.TimeUnit;

import me.drakeet.multitype.ItemViewBinder;

/**
 * Created by Meiji on 2017/6/8.
 * 带图片的 item
 */

public class NewsArticleImgViewBinder extends ItemViewBinder<MultiNewsArticleDataBean, NewsArticleImgViewBinder.ViewHolder> {

    @NonNull
    @Override
    protected NewsArticleImgViewBinder.ViewHolder onCreateViewHolder(@NonNull LayoutInflater inflater, @NonNull ViewGroup parent) {
        View view = inflater.inflate(R.layout.item_news_article_img, parent, false);
        return new ViewHolder(view);
    }

    @Override
    protected void onBindViewHolder(@NonNull final NewsArticleImgViewBinder.ViewHolder holder, @NonNull final MultiNewsArticleDataBean item) {

        final Context context = holder.itemView.getContext();

        try {
            String imgUrl = "http://p3.pstatp.com/";
            List<MultiNewsArticleDataBean.ImageListBean> image_list = item.getImage_list();
            if (image_list != null && image_list.size() != 0) {
                String url = image_list.get(0).getUrl();
                ImageLoader.loadCenterCrop(context, url, holder.iv_image, R.color.viewBackground);
                if (!TextUtils.isEmpty(image_list.get(0).getUri())) {
                    imgUrl += image_list.get(0).getUri().replace("list", "large");
                }
            }

            if (null != item.getUser_info()) {
                String avatar_url = item.getUser_info().getAvatar_url();
                if (!TextUtils.isEmpty(avatar_url)) {
                    ImageLoader.loadCenterCrop(context, avatar_url, holder.iv_media, R.color.viewBackground);
                }
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
            holder.tv_title.setTextSize(SettingUtil.getInstance().getTextSize());
            holder.tv_abstract.setText(tv_abstract);
            holder.tv_extra.setText(tv_source + " - " + tv_comment_count + " - " + tv_datetime);
            holder.iv_dots.setOnClickListener(view -> {
                PopupMenu popupMenu = new PopupMenu(context,
                        holder.iv_dots, Gravity.END, 0, R.style.MyPopupMenu);
                popupMenu.inflate(R.menu.menu_share);
                popupMenu.setOnMenuItemClickListener(menu -> {
                    int itemId = menu.getItemId();
                    if (itemId == R.id.action_share) {
                        IntentAction.send(context, item.getTitle() + "\n" + item.getShare_url());
                    }
                    return false;
                });
                popupMenu.show();
            });

            final String finalImgUrl = imgUrl;
            RxView.clicks(holder.itemView)
                    .throttleFirst(1, TimeUnit.SECONDS)
                    .subscribe(o -> NewsContentActivity.launch(item, finalImgUrl));
        } catch (Exception e) {
            ErrorAction.print(e);
        }
    }

    class ViewHolder extends RecyclerView.ViewHolder {

        private CircleImageView iv_media;
        private ImageView iv_image;
        private TextView tv_title;
        private TextView tv_abstract;
        private TextView tv_extra;
        private ImageView iv_dots;

        ViewHolder(View itemView) {
            super(itemView);
            this.iv_media = itemView.findViewById(R.id.iv_media);
            this.iv_image = itemView.findViewById(R.id.iv_image);
            this.tv_title = itemView.findViewById(R.id.tv_title);
            this.tv_abstract = itemView.findViewById(R.id.tv_abstract);
            this.tv_extra = itemView.findViewById(R.id.tv_extra);
            this.iv_dots = itemView.findViewById(R.id.iv_dots);
        }
    }
}
