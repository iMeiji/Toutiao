package com.meiji.toutiao.adapter;

import android.support.v7.util.DiffUtil;

import com.meiji.toutiao.bean.media.MediaArticleBean;
import com.meiji.toutiao.bean.news.NewsArticleBean;
import com.meiji.toutiao.bean.news.NewsCommentMobileBean;
import com.meiji.toutiao.bean.news.joke.JokeCommentBean;
import com.meiji.toutiao.bean.news.joke.JokeContentBean;
import com.meiji.toutiao.bean.photo.PhotoArticleBean;
import com.meiji.toutiao.bean.video.VideoArticleBean;

import java.util.List;

/**
 * Created by Meiji on 2017/4/18.
 */

public class DiffCallback extends DiffUtil.Callback {

    public static final int NEWS = 0;
    public static final int JOKE = 1;
    public static final int PHOTO = 2;
    public static final int VIDEO = 3;
    public static final int MEDIA = 4;
    public static final int NEWS_COMMENT = 5;
    public static final int JOKE_COMMENT = 6;
    private List oldList, newList;
    private int type;

    public DiffCallback(List oldList, List newList, int type) {
        this.oldList = oldList;
        this.newList = newList;
        this.type = type;
    }

    @Override
    public int getOldListSize() {
        return oldList != null ? oldList.size() : 0;
    }

    @Override
    public int getNewListSize() {
        return newList != null ? newList.size() : 0;
    }

    @Override
    public boolean areItemsTheSame(int oldItemPosition, int newItemPosition) {
        try {
            switch (type) {
                case NEWS:
                    return ((NewsArticleBean.DataBean) oldList.get(oldItemPosition)).getTitle().equals(
                            ((NewsArticleBean.DataBean) newList.get(newItemPosition)).getTitle());
                case JOKE:
                    return ((JokeContentBean.DataBean.GroupBean) oldList.get(oldItemPosition)).getContent().equals(
                            ((JokeContentBean.DataBean.GroupBean) newList.get(newItemPosition)).getContent());
                case PHOTO:
                    return ((PhotoArticleBean.DataBean) oldList.get(oldItemPosition)).getTitle().equals(
                            ((PhotoArticleBean.DataBean) newList.get(newItemPosition)).getTitle());
                case VIDEO:
                    return ((VideoArticleBean.DataBean) oldList.get(oldItemPosition)).getTitle().equals(
                            ((VideoArticleBean.DataBean) newList.get(newItemPosition)).getTitle());
                case MEDIA:
                    return ((MediaArticleBean.DataBean) oldList.get(oldItemPosition)).getTitle().equals(
                            ((MediaArticleBean.DataBean) newList.get(newItemPosition)).getTitle());
                case NEWS_COMMENT:
                    return ((NewsCommentMobileBean.DataBean.CommentBean) oldList.get(oldItemPosition)).getText().equals(
                            ((NewsCommentMobileBean.DataBean.CommentBean) newList.get(newItemPosition)).getText());
                case JOKE_COMMENT:
                    return ((JokeCommentBean.DataBean.RecentCommentsBean) oldList.get(oldItemPosition)).getText().equals(
                            ((JokeCommentBean.DataBean.RecentCommentsBean) newList.get(newItemPosition)).getText());
            }
        } catch (NullPointerException e) {
            e.printStackTrace();
        }
        return false;
    }

    @Override
    public boolean areContentsTheSame(int oldItemPosition, int newItemPosition) {
        try {
            switch (type) {
                case NEWS:
                    return ((NewsArticleBean.DataBean) oldList.get(oldItemPosition)).getShare_url().equals(
                            ((NewsArticleBean.DataBean) newList.get(newItemPosition)).getShare_url());
                case JOKE:
                    return ((JokeContentBean.DataBean.GroupBean) oldList.get(oldItemPosition)).getShare_url().equals(
                            ((JokeContentBean.DataBean.GroupBean) newList.get(newItemPosition)).getShare_url());
                case PHOTO:
                    return ((PhotoArticleBean.DataBean) oldList.get(oldItemPosition)).getSource_url().equals(
                            ((PhotoArticleBean.DataBean) newList.get(newItemPosition)).getSource_url());
                case VIDEO:
                    return ((VideoArticleBean.DataBean) oldList.get(oldItemPosition)).getShare_url().equals(
                            ((VideoArticleBean.DataBean) newList.get(newItemPosition)).getShare_url());
                case MEDIA:
                    return ((MediaArticleBean.DataBean) oldList.get(oldItemPosition)).getSource_url().equals(
                            ((MediaArticleBean.DataBean) newList.get(newItemPosition)).getSource_url());
                case NEWS_COMMENT:
                    return ((NewsCommentMobileBean.DataBean.CommentBean) oldList.get(oldItemPosition)).getId() ==
                            ((NewsCommentMobileBean.DataBean.CommentBean) newList.get(newItemPosition)).getId();
                case JOKE_COMMENT:
                    return ((JokeCommentBean.DataBean.RecentCommentsBean) oldList.get(oldItemPosition)).getId() ==
                            ((JokeCommentBean.DataBean.RecentCommentsBean) newList.get(newItemPosition)).getId();
            }
        } catch (NullPointerException e) {
            e.printStackTrace();
        }
        return false;
    }
}
