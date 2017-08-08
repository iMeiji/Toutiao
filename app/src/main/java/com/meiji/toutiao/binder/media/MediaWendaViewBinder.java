package com.meiji.toutiao.binder.media;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.PopupMenu;
import android.support.v7.widget.RecyclerView;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.meiji.toutiao.ErrorAction;
import com.meiji.toutiao.IntentAction;
import com.meiji.toutiao.R;
import com.meiji.toutiao.bean.media.MediaWendaBean;
import com.meiji.toutiao.bean.wenda.WendaContentBean;
import com.meiji.toutiao.module.wenda.detail.WendaDetailActivity;
import com.meiji.toutiao.util.ImageLoader;

import java.util.List;

import me.drakeet.multitype.ItemViewBinder;

/**
 * Created by Meiji on 2017/6/8.
 * 带图片的 item
 */

public class MediaWendaViewBinder extends ItemViewBinder<MediaWendaBean.AnswerQuestionBean, MediaWendaViewBinder.ViewHolder> {

    @NonNull
    @Override
    protected MediaWendaViewBinder.ViewHolder onCreateViewHolder(@NonNull LayoutInflater inflater, @NonNull ViewGroup parent) {
        View view = inflater.inflate(R.layout.item_media_article_wenda, parent, false);
        return new ViewHolder(view);
    }

    @Override
    protected void onBindViewHolder(@NonNull final MediaWendaViewBinder.ViewHolder holder, @NonNull final MediaWendaBean.AnswerQuestionBean item) {

        final Context context = holder.itemView.getContext();

        try {
            MediaWendaBean.AnswerQuestionBean.AnswerBean answerBean = item.getAnswer();
            MediaWendaBean.AnswerQuestionBean.QuestionBean questionBean = item.getQuestion();
            List<MediaWendaBean.AnswerQuestionBean.AnswerBean.ContentAbstractBean.ThumbImageListBean> imageList = answerBean.getContent_abstract().getThumb_image_list();
            if (imageList != null && imageList.size() > 0) {
                String url = imageList.get(0).getUrl();
                ImageLoader.loadCenterCrop(context, url, holder.iv_image, R.color.viewBackground);
            } else {
                holder.iv_image.setVisibility(View.GONE);
            }

            final String title = questionBean.getTitle();
            String abstractX = answerBean.getContent_abstract().getText();
            String readCount = answerBean.getBrow_count() + "个回答";
            String time = answerBean.getShow_time();

            holder.tv_title.setText(title);
            holder.tv_abstract.setText(abstractX);
            holder.tv_extra.setText(readCount + "  -  " + time);
            holder.itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    WendaContentBean.AnsListBean ansBean = new WendaContentBean.AnsListBean();
                    WendaContentBean.AnsListBean.ShareDataBeanX shareBean = new WendaContentBean.AnsListBean.ShareDataBeanX();
                    WendaContentBean.AnsListBean.UserBeanX userBean = new WendaContentBean.AnsListBean.UserBeanX();
                    ansBean.setTitle(title);
                    ansBean.setQid(item.getQuestion().getQid());
                    ansBean.setAnsid(item.getQuestion().getQid());
                    shareBean.setShare_url(item.getAnswer().getWap_url());
                    userBean.setUname(item.getAnswer().getUser().getUname());
                    userBean.setAvatar_url(item.getAnswer().getUser().getAvatar_url());
                    ansBean.setShare_data(shareBean);
                    ansBean.setUser(userBean);
                    WendaDetailActivity.launch(ansBean);
                }
            });
            holder.iv_dots.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    PopupMenu popupMenu = new PopupMenu(context,
                            holder.iv_dots, Gravity.END, 0, R.style.MyPopupMenu);
                    popupMenu.inflate(R.menu.menu_share);
                    popupMenu.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
                        @Override
                        public boolean onMenuItemClick(MenuItem menu) {
                            int itemId = menu.getItemId();
                            if (itemId == R.id.action_share) {
                                IntentAction.send(context, title + "\n" + item.getAnswer().getWap_url());
                            }
                            return false;
                        }
                    });
                    popupMenu.show();
                }
            });
        } catch (Exception e) {
            ErrorAction.print(e);
        }
    }

    class ViewHolder extends RecyclerView.ViewHolder {

        private ImageView iv_image;
        private TextView tv_title;
        private TextView tv_abstract;
        private TextView tv_extra;
        private ImageView iv_dots;

        ViewHolder(View itemView) {
            super(itemView);
            this.iv_image = itemView.findViewById(R.id.iv_image);
            this.tv_title = itemView.findViewById(R.id.tv_title);
            this.tv_abstract = itemView.findViewById(R.id.tv_abstract);
            this.tv_extra = itemView.findViewById(R.id.tv_extra);
            this.iv_dots = itemView.findViewById(R.id.iv_dots);
        }
    }
}
