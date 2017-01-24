package com.meiji.toutiao.adapter.other.funny;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.meiji.toutiao.R;
import com.meiji.toutiao.bean.other.funny.FunnyCommentBean;
import com.meiji.toutiao.interfaces.IOnItemClickListener;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Meiji on 2017/1/25.
 */

public class FunnyCommentAdapter extends RecyclerView.Adapter<FunnyCommentAdapter.FunnyCommentsViewHolder> {

    private List<FunnyCommentBean.DataBean.CommentsBean> commentsBeanList = new ArrayList<>();
    private Context context;
    private IOnItemClickListener onItemClickListener;

    public FunnyCommentAdapter(List<FunnyCommentBean.DataBean.CommentsBean> commentsBeanList, Context context) {
        this.commentsBeanList = commentsBeanList;
        this.context = context;
    }

    public void setOnItemClickListener(IOnItemClickListener onItemClickListener) {
        this.onItemClickListener = onItemClickListener;
    }

    @Override
    public FunnyCommentAdapter.FunnyCommentsViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.other_funny_comment_item, parent, false);
        return new FunnyCommentAdapter.FunnyCommentsViewHolder(view, onItemClickListener);
    }

    @Override
    public void onBindViewHolder(FunnyCommentAdapter.FunnyCommentsViewHolder holder, int position) {
        FunnyCommentBean.DataBean.CommentsBean commentsBean = commentsBeanList.get(position);
        String iv_avatar = commentsBean.getUser().getAvatar_url();
        String tv_username = commentsBean.getUser().getName();
        String tv_text = commentsBean.getText();
        int tv_likes = commentsBean.getDigg_count();

        Glide.with(context).load(iv_avatar).crossFade().centerCrop().into(holder.iv_avatar);
        holder.tv_username.setText(tv_username);
        holder.tv_text.setText(tv_text);
        holder.tv_likes.setText(tv_likes + "赞");
    }

    @Override
    public int getItemCount() {
        return commentsBeanList != null ? commentsBeanList.size() : 0;
    }

    public class FunnyCommentsViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        private ImageView iv_avatar;
        private TextView tv_username;
        private TextView tv_text;
        private TextView tv_likes;
        private IOnItemClickListener onItemClickListener;

        public FunnyCommentsViewHolder(View itemView, IOnItemClickListener onItemClickListener) {
            super(itemView);
            this.iv_avatar = (ImageView) itemView.findViewById(R.id.iv_avatar);
            this.tv_username = (TextView) itemView.findViewById(R.id.tv_username);
            this.tv_text = (TextView) itemView.findViewById(R.id.tv_text);
            this.tv_likes = (TextView) itemView.findViewById(R.id.tv_likes);
            this.onItemClickListener = onItemClickListener;
            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View view) {
            if (onItemClickListener != null) {
                onItemClickListener.onClick(view, getLayoutPosition());
            }
        }
    }
}
