package com.meiji.toutiao.adapter;

import android.support.v7.util.DiffUtil;
import android.support.v7.widget.RecyclerView;

import com.meiji.toutiao.bean.joke.JokeCommentBean;
import com.meiji.toutiao.bean.joke.JokeContentBean;
import com.meiji.toutiao.bean.media.MediaArticleBean;
import com.meiji.toutiao.bean.media.MultiMediaArticleBean;
import com.meiji.toutiao.bean.news.MultiNewsArticleDataBean;
import com.meiji.toutiao.bean.news.NewsCommentMobileBean;
import com.meiji.toutiao.bean.photo.PhotoArticleBean;
import com.meiji.toutiao.bean.search.SearchResultBean;
import com.meiji.toutiao.bean.video.VideoArticleBean;
import com.meiji.toutiao.bean.wenda.WendaArticleDataBean;
import com.meiji.toutiao.bean.wenda.WendaContentBean;

import java.util.List;

/**
 * Created by Meiji on 2017/4/18.
 */

public class DiffCallback extends DiffUtil.Callback {

    public static final int JOKE = 1;
    public static final int PHOTO = 2;
    public static final int VIDEO = 3;
    public static final int MEDIA = 4;
    public static final int NEWS_COMMENT = 5;
    public static final int JOKE_COMMENT = 6;
    public static final int MUlTI_NEWS = 7;
    public static final int WENDA_ARTICLE = 8;
    public static final int WENDA_CONTENT = 9;
    public static final int SEARCH = 10;
    public static final int MUlTI_MEDIA = 11;
    private List oldList, newList;
    private int type;

    public DiffCallback(List oldList, List newList, int type) {
        this.oldList = oldList;
        this.newList = newList;
        this.type = type;
    }

    public static void notifyDataSetChanged(List oldList, List newList, int type, RecyclerView.Adapter adapter) {
        DiffCallback diffCallback = new DiffCallback(oldList, newList, type);
        DiffUtil.DiffResult result = DiffUtil.calculateDiff(diffCallback, true);
        result.dispatchUpdatesTo(adapter);
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
                case MUlTI_NEWS:
                    return ((MultiNewsArticleDataBean) oldList.get(oldItemPosition)).getTitle().equals(
                            ((MultiNewsArticleDataBean) newList.get(newItemPosition)).getTitle());
                case WENDA_ARTICLE:
                    return ((WendaArticleDataBean) oldList.get(oldItemPosition)).getQuestionBean().getTitle().equals(
                            ((WendaArticleDataBean) newList.get(newItemPosition)).getQuestionBean().getTitle());
                case WENDA_CONTENT:
                    return ((WendaContentBean.AnsListBean) oldList.get(oldItemPosition)).getAnsid().equals(
                            ((WendaContentBean.AnsListBean) newList.get(newItemPosition)).getAnsid());
                case SEARCH:
                    return ((SearchResultBean.DataBeanX) oldList.get(oldItemPosition)).getTitle().equals(
                            ((SearchResultBean.DataBeanX) newList.get(newItemPosition)).getTitle());
                case MUlTI_MEDIA:
                    return ((MultiMediaArticleBean.DataBean) oldList.get(oldItemPosition)).getTitle().equals(
                            ((MultiMediaArticleBean.DataBean) newList.get(newItemPosition)).getTitle());
            }
        } catch (Exception e) {
//            ErrorAction.print(e);
        }
        return false;
    }

    @Override
    public boolean areContentsTheSame(int oldItemPosition, int newItemPosition) {
        try {
            switch (type) {
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
                    return ((NewsCommentMobileBean.DataBean.CommentBean) oldList.get(oldItemPosition)).getUser_name().equals(
                            ((NewsCommentMobileBean.DataBean.CommentBean) newList.get(newItemPosition)).getUser_name());
                case JOKE_COMMENT:
                    return ((JokeCommentBean.DataBean.RecentCommentsBean) oldList.get(oldItemPosition)).getId() ==
                            ((JokeCommentBean.DataBean.RecentCommentsBean) newList.get(newItemPosition)).getId();
                case MUlTI_NEWS:
                    return ((MultiNewsArticleDataBean) oldList.get(oldItemPosition)).getItem_id() ==
                            ((MultiNewsArticleDataBean) newList.get(newItemPosition)).getItem_id();
                case WENDA_ARTICLE:
                    return ((WendaArticleDataBean) oldList.get(oldItemPosition)).getQuestionBean().getContent().equals(
                            ((WendaArticleDataBean) newList.get(newItemPosition)).getQuestionBean().getContent());
                case SEARCH:
                    return ((SearchResultBean.DataBeanX) oldList.get(oldItemPosition)).getAbstractX().equals(
                            ((SearchResultBean.DataBeanX) newList.get(newItemPosition)).getAbstractX());
                case MUlTI_MEDIA:
                    return ((MultiMediaArticleBean.DataBean) oldList.get(oldItemPosition)).getAbstractX().equals(
                            ((MultiMediaArticleBean.DataBean) newList.get(newItemPosition)).getAbstractX());
            }
        } catch (Exception e) {
//            ErrorAction.print(e);
        }
        return false;
    }
}
