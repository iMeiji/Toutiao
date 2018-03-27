package com.meiji.toutiao.binder.news;

import android.content.ClipData;
import android.content.ClipboardManager;
import android.content.Context;
import android.support.annotation.NonNull;
import android.support.design.widget.Snackbar;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.meiji.toutiao.ErrorAction;
import com.meiji.toutiao.IntentAction;
import com.meiji.toutiao.R;
import com.meiji.toutiao.bean.news.NewsCommentBean;
import com.meiji.toutiao.module.base.BaseActivity;
import com.meiji.toutiao.util.ImageLoader;
import com.meiji.toutiao.widget.BottomSheetDialogFixed;

import me.drakeet.multitype.ItemViewBinder;

/**
 * Created by Meiji on 2017/6/9.
 */

public class NewsCommentViewBinder extends ItemViewBinder<NewsCommentBean.DataBean.CommentBean, NewsCommentViewBinder.ViewHolder> {

    @NonNull
    @Override
    protected NewsCommentViewBinder.ViewHolder onCreateViewHolder(@NonNull LayoutInflater inflater, @NonNull ViewGroup parent) {
        View view = inflater.inflate(R.layout.item_news_comment, parent, false);
        return new ViewHolder(view);
    }

    @Override
    protected void onBindViewHolder(@NonNull final ViewHolder holder, @NonNull final NewsCommentBean.DataBean.CommentBean item) {

        final Context context = holder.itemView.getContext();

        try {
            String iv_avatar = item.getUser_profile_image_url();
            String tv_username = item.getUser_name();
            String tv_text = item.getText();
            int tv_likes = item.getDigg_count();

            ImageLoader.loadCenterCrop(context, iv_avatar, holder.iv_avatar, R.color.viewBackground);
            holder.tv_username.setText(tv_username);
            holder.tv_text.setText(tv_text);
            holder.tv_likes.setText(tv_likes + "赞");
            holder.itemView.setOnClickListener(v -> {
                final String content = item.getText();
                final BottomSheetDialogFixed dialog = new BottomSheetDialogFixed(context);
                dialog.setOwnerActivity((BaseActivity) context);
                View view = ((BaseActivity) context).getLayoutInflater().inflate(R.layout.item_comment_action_sheet, null);
                view.findViewById(R.id.layout_copy_text).setOnClickListener(view12 -> {
                    ClipboardManager copy = (ClipboardManager) context.getSystemService(Context.CLIPBOARD_SERVICE);
                    ClipData clipData = ClipData.newPlainText("text", content);
                    copy.setPrimaryClip(clipData);
                    Snackbar.make(holder.itemView, R.string.copied_to_clipboard, Snackbar.LENGTH_SHORT).show();
                    dialog.dismiss();
                });
                view.findViewById(R.id.layout_share_text).setOnClickListener(view1 -> {
                    IntentAction.send(context, content);
                    dialog.dismiss();
                });
                dialog.setContentView(view);
                dialog.show();
            });
        } catch (Exception e) {
            ErrorAction.print(e);
        }
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        private ImageView iv_avatar;
        private TextView tv_username;
        private TextView tv_text;
        private TextView tv_likes;

        public ViewHolder(View itemView) {
            super(itemView);
            this.iv_avatar = itemView.findViewById(R.id.iv_avatar);
            this.tv_username = itemView.findViewById(R.id.tv_username);
            this.tv_text = itemView.findViewById(R.id.tv_text);
            this.tv_likes = itemView.findViewById(R.id.tv_likes);
        }
    }
}
