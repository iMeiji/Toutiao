package com.meiji.toutiao.binder.wenda;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.jakewharton.rxbinding2.view.RxView;
import com.meiji.toutiao.ErrorAction;
import com.meiji.toutiao.R;
import com.meiji.toutiao.bean.wenda.WendaArticleDataBean;
import com.meiji.toutiao.module.wenda.content.WendaContentActivity;
import com.meiji.toutiao.util.SettingUtil;
import com.meiji.toutiao.util.TimeUtil;

import java.util.concurrent.TimeUnit;

import me.drakeet.multitype.ItemViewBinder;

/**
 * Created by Meiji on 2017/6/11.
 */

public class WendaArticleTextViewBinder extends ItemViewBinder<WendaArticleDataBean, WendaArticleTextViewBinder.ViewHolder> {

    @NonNull
    @Override
    protected WendaArticleTextViewBinder.ViewHolder onCreateViewHolder(@NonNull LayoutInflater inflater, @NonNull ViewGroup parent) {
        View view = inflater.inflate(R.layout.item_wenda_article_text, parent, false);
        return new ViewHolder(view);
    }

    @Override
    protected void onBindViewHolder(@NonNull ViewHolder holder, @NonNull final WendaArticleDataBean item) {
        try {
            String tv_title = item.getQuestionBean().getTitle();
            String tv_answer_count = item.getQuestionBean().getNormal_ans_count() + item.getQuestionBean().getNice_ans_count() + "回答";
            String tv_datetime = item.getQuestionBean().getCreate_time() + "";
            if (!TextUtils.isEmpty(tv_datetime)) {
                tv_datetime = TimeUtil.getTimeStampAgo(tv_datetime);
            }
            String tv_content = item.getAnswerBean().getAbstractX();
            holder.tv_title.setText(tv_title);
            holder.tv_title.setTextSize(SettingUtil.getInstance().getTextSize());
            holder.tv_answer_count.setText(tv_answer_count);
            holder.tv_time.setText(tv_datetime);
            holder.tv_content.setText(tv_content);

            RxView.clicks(holder.itemView)
                    .throttleFirst(1, TimeUnit.SECONDS)
                    .subscribe(o -> WendaContentActivity.launch(item.getQuestionBean().getQid() + ""));
        } catch (Exception e) {
            ErrorAction.print(e);
        }
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        private TextView tv_title;
        private TextView tv_content;
        private TextView tv_answer_count;
        private TextView tv_time;

        public ViewHolder(View itemView) {
            super(itemView);
            this.tv_title = itemView.findViewById(R.id.tv_title);
            this.tv_content = itemView.findViewById(R.id.tv_content);
            this.tv_answer_count = itemView.findViewById(R.id.tv_answer_count);
            this.tv_time = itemView.findViewById(R.id.tv_time);
        }
    }
}
