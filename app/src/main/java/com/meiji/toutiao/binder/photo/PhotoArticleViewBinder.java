package com.meiji.toutiao.binder.photo;

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
import com.meiji.toutiao.bean.photo.PhotoArticleBean;
import com.meiji.toutiao.module.photo.content.PhotoContentActivity;
import com.meiji.toutiao.util.ImageLoader;
import com.meiji.toutiao.util.SettingUtil;
import com.meiji.toutiao.util.TimeUtil;
import com.meiji.toutiao.widget.CircleImageView;

import java.util.concurrent.TimeUnit;

import me.drakeet.multitype.ItemViewBinder;

/**
 * Created by Meiji on 2017/6/11.
 */

public class PhotoArticleViewBinder extends ItemViewBinder<PhotoArticleBean.DataBean, PhotoArticleViewBinder.ViewHolder> {

    @NonNull
    @Override
    protected PhotoArticleViewBinder.ViewHolder onCreateViewHolder(@NonNull LayoutInflater inflater, @NonNull ViewGroup parent) {
        View view = inflater.inflate(R.layout.item_photo_article, parent, false);
        return new ViewHolder(view);
    }

    @Override
    protected void onBindViewHolder(@NonNull final ViewHolder holder, @NonNull final PhotoArticleBean.DataBean item) {

        final Context context = holder.itemView.getContext();

        try {
            String tv_title = item.getTitle();

            if (!TextUtils.isEmpty(item.getMedia_avatar_url())) {
                ImageLoader.loadCenterCrop(context, item.getMedia_avatar_url(), holder.iv_media, R.color.viewBackground);
            }

            if (item.getImage_list() != null) {
                int size = item.getImage_list().size();
                String[] ivs = new String[size];
                for (int i = 0; i < item.getImage_list().size(); i++) {
                    ivs[i] = "https:" + item.getImage_list().get(i).getUrl();
                }
                switch (ivs.length) {
                    case 1:
                        ImageLoader.loadCenterCrop(context, ivs[0], holder.iv_0, R.color.viewBackground);
                        break;
                    case 2:
                        ImageLoader.loadCenterCrop(context, ivs[0], holder.iv_0, R.color.viewBackground);
                        ImageLoader.loadCenterCrop(context, ivs[1], holder.iv_1, R.color.viewBackground);
                        break;
                    case 3:
                        ImageLoader.loadCenterCrop(context, ivs[0], holder.iv_0, R.color.viewBackground);
                        ImageLoader.loadCenterCrop(context, ivs[1], holder.iv_1, R.color.viewBackground);
                        ImageLoader.loadCenterCrop(context, ivs[2], holder.iv_2, R.color.viewBackground);
                        break;
                }
            }
            String tv_source = item.getSource();
            String tv_datetime = item.getBehot_time() + "";
            String comments_count = item.getComments_count() + "评论";
            if (!TextUtils.isEmpty(tv_datetime)) {
                tv_datetime = TimeUtil.getTimeStampAgo(tv_datetime);
            }
            holder.tv_title.setText(tv_title);
            holder.tv_title.setTextSize(SettingUtil.getInstance().getTextSize());
            holder.tv_extra.setText(tv_source + " - " + comments_count + " - " + tv_datetime);
            holder.iv_dots.setOnClickListener(view -> {
                PopupMenu popupMenu = new PopupMenu(context,
                        holder.iv_dots, Gravity.END, 0, R.style.MyPopupMenu);
                popupMenu.inflate(R.menu.menu_share);
                popupMenu.setOnMenuItemClickListener(menu -> {
                    int itemId = menu.getItemId();
                    if (itemId == R.id.action_share) {
                        String shareUrl = item.getSource_url();
                        if (!shareUrl.contains("toutiao")) {
                            shareUrl = "http://toutiao.com" + shareUrl;
                        }
                        IntentAction.send(context, item.getTitle() + "\n" + shareUrl);
                    }
                    return false;
                });
                popupMenu.show();
            });

            RxView.clicks(holder.itemView)
                    .throttleFirst(1, TimeUnit.SECONDS)
                    .subscribe(o -> PhotoContentActivity.launch(item));
        } catch (Exception e) {
            ErrorAction.print(e);
        }
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        private CircleImageView iv_media;
        private TextView tv_extra;
        private TextView tv_title;
        private ImageView iv_0;
        private ImageView iv_1;
        private ImageView iv_2;
        private ImageView iv_dots;

        public ViewHolder(View itemView) {
            super(itemView);
            this.iv_media = itemView.findViewById(R.id.iv_media);
            this.tv_extra = itemView.findViewById(R.id.tv_extra);
            this.tv_title = itemView.findViewById(R.id.tv_title);
            this.iv_0 = itemView.findViewById(R.id.iv_0);
            this.iv_1 = itemView.findViewById(R.id.iv_1);
            this.iv_2 = itemView.findViewById(R.id.iv_2);
            this.iv_dots = itemView.findViewById(R.id.iv_dots);
        }
    }
}
