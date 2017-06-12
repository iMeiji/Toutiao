package com.meiji.toutiao.binder.wenda;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.meiji.toutiao.R;
import com.meiji.toutiao.bean.wenda.WendaContentBean;
import com.meiji.toutiao.utils.ImageLoader;

import me.drakeet.multitype.ItemViewBinder;

/**
 * Created by Meiji on 2017/6/11.
 */

public class WendaContentHeaderViewBinder extends ItemViewBinder<WendaContentBean.QuestionBean, WendaContentHeaderViewBinder.ViewHolder> {

    @NonNull
    @Override
    protected WendaContentHeaderViewBinder.ViewHolder onCreateViewHolder(@NonNull LayoutInflater inflater, @NonNull ViewGroup parent) {
        View view = inflater.inflate(R.layout.item_wenda_content_header, parent, false);
        return new ViewHolder(view);
    }

    @Override
    protected void onBindViewHolder(@NonNull ViewHolder holder, @NonNull final WendaContentBean.QuestionBean item) {
        try {
            String tv_title = item.getTitle();
            String tv_abstract = item.getContent().getText();

            if (item.getContent().getThumb_image_list().size() > 0) {
                holder.iv_image.setVisibility(View.VISIBLE);
                String url = item.getContent().getThumb_image_list().get(0).getUrl();
                ImageLoader.loadCenterCrop(holder.itemView.getContext(), url, holder.iv_image, R.color.viewBackground);
            }

            String tv_answer_count = item.getNormal_ans_count() + " 回答";
            String tv_follow_count = item.getFollow_count() + " 关注";
            holder.tv_title.setText(tv_title);
            holder.tv_abstract.setText(tv_abstract);
            holder.tv_answer_count.setText(tv_answer_count);
            holder.tv_follow_count.setText(tv_follow_count);
        } catch (Exception e) {

        }
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        private TextView tv_title;
        private TextView tv_abstract;
        private ImageView iv_image;
        private TextView tv_answer_count;
        private TextView tv_follow_count;

        public ViewHolder(View itemView) {
            super(itemView);
            this.tv_title = (TextView) itemView.findViewById(R.id.tv_title);
            this.tv_abstract = (TextView) itemView.findViewById(R.id.tv_abstract);
            this.iv_image = (ImageView) itemView.findViewById(R.id.iv_image);
            this.tv_answer_count = (TextView) itemView.findViewById(R.id.tv_answer_count);
            this.tv_follow_count = (TextView) itemView.findViewById(R.id.tv_follow_count);
        }
    }
}
