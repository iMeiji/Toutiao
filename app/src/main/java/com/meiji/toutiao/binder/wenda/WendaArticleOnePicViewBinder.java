package com.meiji.toutiao.binder.wenda;

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
import com.meiji.toutiao.bean.wenda.WendaArticleDataBean;
import com.meiji.toutiao.module.wenda.content.WendaContentActivity;
import com.meiji.toutiao.utils.ImageLoader;
import com.meiji.toutiao.utils.TimeUtil;

import me.drakeet.multitype.ItemViewBinder;

/**
 * Created by Meiji on 2017/6/11.
 */

public class WendaArticleOnePicViewBinder extends ItemViewBinder<WendaArticleDataBean, WendaArticleOnePicViewBinder.ViewHolder> {

    @NonNull
    @Override
    protected WendaArticleOnePicViewBinder.ViewHolder onCreateViewHolder(@NonNull LayoutInflater inflater, @NonNull ViewGroup parent) {
        View view = inflater.inflate(R.layout.item_wenda_article_one_pic, parent, false);
        return new ViewHolder(view);
    }

    @Override
    protected void onBindViewHolder(@NonNull ViewHolder holder, @NonNull final WendaArticleDataBean item) {
        try {
            String url = item.getExtraBean().getWenda_image().getLarge_image_list().get(0).getUrl();
            ImageLoader.loadCenterCrop(holder.itemView.getContext(), url, holder.iv_image_big, R.color.viewBackground);

            String tv_title = item.getQuestionBean().getTitle();
            String tv_answer_count = item.getQuestionBean().getNormal_ans_count() + "回答";
            String tv_datetime = item.getQuestionBean().getCreate_time() + "";
            if (!TextUtils.isEmpty(tv_datetime)) {
                tv_datetime = TimeUtil.getTimeStampAgo(tv_datetime);
            }
            holder.tv_title.setText(tv_title);
            holder.tv_answer_count.setText(tv_answer_count);
            holder.tv_time.setText(tv_datetime);
            holder.itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    WendaContentActivity.launch(item.getQuestionBean().getQid() + "");
                }
            });
        } catch (Exception e) {
            ErrorAction.print(e);
        }
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        private TextView tv_title;
        private ImageView iv_image_big;
        private TextView tv_answer_count;
        private TextView tv_time;

        public ViewHolder(View itemView) {
            super(itemView);
            this.tv_title = (TextView) itemView.findViewById(R.id.tv_title);
            this.iv_image_big = (ImageView) itemView.findViewById(R.id.iv_image_big);
            this.tv_answer_count = (TextView) itemView.findViewById(R.id.tv_answer_count);
            this.tv_time = (TextView) itemView.findViewById(R.id.tv_time);
        }
    }
}
