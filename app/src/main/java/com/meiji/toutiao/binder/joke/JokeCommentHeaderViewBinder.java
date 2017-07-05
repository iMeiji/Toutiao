package com.meiji.toutiao.binder.joke;

import android.content.ClipData;
import android.content.ClipboardManager;
import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomSheetDialog;
import android.support.design.widget.Snackbar;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.meiji.toutiao.ErrorAction;
import com.meiji.toutiao.R;
import com.meiji.toutiao.bean.joke.JokeContentBean;
import com.meiji.toutiao.module.base.BaseActivity;
import com.meiji.toutiao.util.ImageLoader;
import com.meiji.toutiao.widget.CircleImageView;

import me.drakeet.multitype.ItemViewBinder;

/**
 * Created by Meiji on 2017/6/10.
 */

public class JokeCommentHeaderViewBinder extends ItemViewBinder<JokeContentBean.DataBean.GroupBean, JokeCommentHeaderViewBinder.ViewHolder> {

    @NonNull
    @Override
    protected JokeCommentHeaderViewBinder.ViewHolder onCreateViewHolder(@NonNull LayoutInflater inflater, @NonNull ViewGroup parent) {
        View view = inflater.inflate(R.layout.item_news_joke_content, parent, false);
        return new ViewHolder(view);
    }

    @Override
    protected void onBindViewHolder(@NonNull final ViewHolder holder, @NonNull final JokeContentBean.DataBean.GroupBean item) {
        try {
            String avatar_url = item.getUser().getAvatar_url();
            String name = item.getUser().getName();
            String text = item.getText();
            String digg_count = item.getDigg_count() + "";
            String bury_count = item.getBury_count() + "";
            String comment_count = item.getComment_count() + "评论";

            ImageLoader.loadCenterCrop(holder.itemView.getContext(), avatar_url, holder.iv_avatar, R.color.viewBackground);
            holder.tv_username.setText(name);
            holder.tv_text.setText(text);
            holder.tv_digg_count.setText(digg_count);
            holder.tv_bury_count.setText(bury_count);
            holder.tv_comment_count.setText(comment_count);
            holder.itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    final BaseActivity context = (BaseActivity) holder.itemView.getContext();
                    final String content = item.getText();
                    final BottomSheetDialog dialog = new BottomSheetDialog(context);
                    View view = context.getLayoutInflater().inflate(R.layout.item_comment_action_sheet, null);
                    view.findViewById(R.id.layout_copy_text).setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            ClipboardManager copy = (ClipboardManager) context.getSystemService(Context.CLIPBOARD_SERVICE);
                            ClipData clipData = ClipData.newPlainText("text", content);
                            copy.setPrimaryClip(clipData);
                            Snackbar.make(holder.itemView, R.string.copied_to_clipboard, Snackbar.LENGTH_SHORT).show();
                            dialog.dismiss();
                        }
                    });
                    view.findViewById(R.id.layout_share_text).setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            Intent shareIntent = new Intent()
                                    .setAction(Intent.ACTION_SEND)
                                    .setType("text/plain")
                                    .putExtra(Intent.EXTRA_TEXT, content);
                            context.startActivity(Intent.createChooser(shareIntent, context.getString(R.string.share_to)));
                            dialog.dismiss();
                        }
                    });
                    dialog.setContentView(view);
                    dialog.show();
                }
            });
        } catch (Exception e) {
            ErrorAction.print(e);
        }
    }

    class ViewHolder extends RecyclerView.ViewHolder {

        private CircleImageView iv_avatar;
        private TextView tv_username;
        private TextView tv_text;
        private TextView tv_digg_count;
        private TextView tv_bury_count;
        private TextView tv_comment_count;

        ViewHolder(View itemView) {
            super(itemView);
            this.iv_avatar = (CircleImageView) itemView.findViewById(R.id.iv_avatar);
            this.tv_username = (TextView) itemView.findViewById(R.id.tv_username);
            this.tv_text = (TextView) itemView.findViewById(R.id.tv_text);
            this.tv_digg_count = (TextView) itemView.findViewById(R.id.tv_digg_count);
            this.tv_bury_count = (TextView) itemView.findViewById(R.id.tv_bury_count);
            this.tv_comment_count = (TextView) itemView.findViewById(R.id.tv_comment_count);
        }
    }
}
