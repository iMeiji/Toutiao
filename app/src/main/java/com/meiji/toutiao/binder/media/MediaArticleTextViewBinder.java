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

import com.jakewharton.rxbinding2.view.RxView;
import com.meiji.toutiao.ErrorAction;
import com.meiji.toutiao.IntentAction;
import com.meiji.toutiao.R;
import com.meiji.toutiao.bean.media.MultiMediaArticleBean;
import com.meiji.toutiao.bean.news.MultiNewsArticleDataBean;
import com.meiji.toutiao.module.news.content.NewsContentActivity;
import com.meiji.toutiao.util.SettingUtil;
import com.meiji.toutiao.util.TimeUtil;

import java.util.concurrent.TimeUnit;

import me.drakeet.multitype.ItemViewBinder;

/**
 * Created by Meiji on 2017/6/8.
 * 不带图片的 item
 */

public class MediaArticleTextViewBinder extends ItemViewBinder<MultiMediaArticleBean.DataBean, MediaArticleTextViewBinder.ViewHolder> {

    @NonNull
    @Override
    protected MediaArticleTextViewBinder.ViewHolder onCreateViewHolder(@NonNull LayoutInflater inflater, @NonNull ViewGroup parent) {
        View view = inflater.inflate(R.layout.item_media_article_text, parent, false);
        return new ViewHolder(view);
    }

    @Override
    protected void onBindViewHolder(@NonNull final MediaArticleTextViewBinder.ViewHolder holder, @NonNull final MultiMediaArticleBean.DataBean item) {

        final Context context = holder.itemView.getContext();

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
            holder.tv_title.setTextSize(SettingUtil.getInstance().getTextSize());
            holder.tv_abstract.setText(abstractX);
            holder.tv_extra.setText(readCount + " - " + countmmentCount + " - " + time);

            RxView.clicks(holder.itemView)
                    .throttleFirst(1, TimeUnit.SECONDS)
                    .subscribe(o -> {
                        MultiNewsArticleDataBean bean = new MultiNewsArticleDataBean();
                        bean.setTitle(item.getTitle());
                        bean.setDisplay_url(item.getDisplay_url());
                        bean.setShare_url(item.getDisplay_url());
                        bean.setMedia_name(item.getSource());
                        MultiNewsArticleDataBean.MediaInfoBean mediaInfo = new MultiNewsArticleDataBean.MediaInfoBean();
                        mediaInfo.setMedia_id(item.getMedia_id() + "");
                        bean.setMedia_info(mediaInfo);
                        bean.setGroup_id(item.getGroup_id());
                        bean.setItem_id(item.getItem_id());
                        NewsContentActivity.launch(bean);
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
        private TextView tv_abstract;
        private ImageView iv_dots;

        ViewHolder(View itemView) {
            super(itemView);
            this.tv_extra = itemView.findViewById(R.id.tv_extra);
            this.tv_title = itemView.findViewById(R.id.tv_title);
            this.tv_abstract = itemView.findViewById(R.id.tv_abstract);
            this.iv_dots = itemView.findViewById(R.id.iv_dots);
        }
    }
}
