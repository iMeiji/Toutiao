package com.meiji.toutiao;

import android.support.annotation.NonNull;

import com.meiji.toutiao.bean.FooterBean;
import com.meiji.toutiao.bean.joke.JokeCommentBean;
import com.meiji.toutiao.bean.joke.JokeContentBean;
import com.meiji.toutiao.bean.media.MediaChannelBean;
import com.meiji.toutiao.bean.news.MultiNewsArticleDataBean;
import com.meiji.toutiao.bean.news.NewsCommentMobileBean;
import com.meiji.toutiao.bean.photo.PhotoArticleBean;
import com.meiji.toutiao.bean.video.VideoArticleBean;
import com.meiji.toutiao.bean.wenda.WendaArticleDataBean;
import com.meiji.toutiao.bean.wenda.WendaContentBean;
import com.meiji.toutiao.binder.FooterViewBinder;
import com.meiji.toutiao.binder.joke.JokeCommentHeaderViewBinder;
import com.meiji.toutiao.binder.joke.JokeCommentViewBinder;
import com.meiji.toutiao.binder.joke.JokeContentViewBinder;
import com.meiji.toutiao.binder.media.MediaChannelViewBinder;
import com.meiji.toutiao.binder.news.NewsArticleHasVideoViewBinder;
import com.meiji.toutiao.binder.news.NewsArticleNoPicViewBinder;
import com.meiji.toutiao.binder.news.NewsArticleViewBinder;
import com.meiji.toutiao.binder.news.NewsCommentViewBinder;
import com.meiji.toutiao.binder.photo.PhotoArticleViewBinder;
import com.meiji.toutiao.binder.video.VideoArticleViewBinder;
import com.meiji.toutiao.binder.video.VideoContentHeaderViewBinder;
import com.meiji.toutiao.binder.wenda.WendaArticleNoPicViewBinder;
import com.meiji.toutiao.binder.wenda.WendaArticleOnePicViewBinder;
import com.meiji.toutiao.binder.wenda.WendaArticleThreePicViewBinder;
import com.meiji.toutiao.binder.wenda.WendaContentHeaderViewBinder;
import com.meiji.toutiao.binder.wenda.WendaContentViewBinder;

import me.drakeet.multitype.ClassLinker;
import me.drakeet.multitype.ItemViewBinder;
import me.drakeet.multitype.MultiTypeAdapter;

/**
 * Created by Meiji on 2017/6/9.
 */

public class Register {

    public static void registerNewsArticleItem(MultiTypeAdapter adapter) {
        // 一个类型对应多个 ItemViewBinder
        adapter.register(MultiNewsArticleDataBean.class)
                .to(new NewsArticleViewBinder(),
                        new NewsArticleHasVideoViewBinder(),
                        new NewsArticleNoPicViewBinder())
                .withClassLinker(new ClassLinker<MultiNewsArticleDataBean>() {
                    @NonNull
                    @Override
                    public Class<? extends ItemViewBinder<MultiNewsArticleDataBean, ?>> index(@NonNull MultiNewsArticleDataBean item) {
                        if (item.isHas_video()) {
                            return NewsArticleHasVideoViewBinder.class;
                        }
                        if (item.getImage_list().size() > 0) {
                            return NewsArticleViewBinder.class;
                        }
                        return NewsArticleNoPicViewBinder.class;
                    }
                });
        adapter.register(FooterBean.class, new FooterViewBinder());
    }

    public static void registerNewsCommentItem(MultiTypeAdapter adapter) {
        adapter.register(NewsCommentMobileBean.DataBean.CommentBean.class, new NewsCommentViewBinder());
        adapter.register(FooterBean.class, new FooterViewBinder());
    }

    public static void registerVideoContentItem(MultiTypeAdapter adapter) {
        adapter.register(VideoArticleBean.DataBean.class, new VideoContentHeaderViewBinder());
        adapter.register(NewsCommentMobileBean.DataBean.CommentBean.class, new NewsCommentViewBinder());
        adapter.register(FooterBean.class, new FooterViewBinder());
    }

    public static void registerVideoArticleItem(MultiTypeAdapter adapter) {
        adapter.register(VideoArticleBean.DataBean.class, new VideoArticleViewBinder());
        adapter.register(FooterBean.class, new FooterViewBinder());
    }

    public static void registerJokeContentItem(MultiTypeAdapter adapter) {
        adapter.register(JokeContentBean.DataBean.GroupBean.class, new JokeContentViewBinder());
        adapter.register(FooterBean.class, new FooterViewBinder());
    }

    public static void registerJokeCommentItem(MultiTypeAdapter adapter) {
        adapter.register(JokeContentBean.DataBean.GroupBean.class, new JokeCommentHeaderViewBinder());
        adapter.register(JokeCommentBean.DataBean.RecentCommentsBean.class, new JokeCommentViewBinder());
        adapter.register(FooterBean.class, new FooterViewBinder());
    }

    public static void registerPhotoArticleItem(MultiTypeAdapter adapter) {
        adapter.register(PhotoArticleBean.DataBean.class, new PhotoArticleViewBinder());
        adapter.register(FooterBean.class, new FooterViewBinder());
    }

    public static void registerWendaArticleItem(MultiTypeAdapter adapter) {
        // 一个类型对应多个 ItemViewBinder
        adapter.register(WendaArticleDataBean.class)
                .to(new WendaArticleNoPicViewBinder(),
                        new WendaArticleOnePicViewBinder(),
                        new WendaArticleThreePicViewBinder())
                .withClassLinker(new ClassLinker<WendaArticleDataBean>() {
                    @NonNull
                    @Override
                    public Class<? extends ItemViewBinder<WendaArticleDataBean, ?>> index(@NonNull WendaArticleDataBean item) {
                        if (item.getExtraBean().getWenda_image().getThree_image_list().size() > 0) {
                            return WendaArticleThreePicViewBinder.class;
                        }
                        if (item.getExtraBean().getWenda_image().getLarge_image_list().size() > 0) {
                            return WendaArticleOnePicViewBinder.class;
                        }
                        return WendaArticleNoPicViewBinder.class;
                    }
                });
        adapter.register(FooterBean.class, new FooterViewBinder());
    }

    public static void registerWendaContentItem(MultiTypeAdapter adapter) {
        adapter.register(WendaContentBean.QuestionBean.class, new WendaContentHeaderViewBinder());
        adapter.register(WendaContentBean.AnsListBean.class, new WendaContentViewBinder());
        adapter.register(FooterBean.class, new FooterViewBinder());
    }

    public static void registerMediaChannelItem(MultiTypeAdapter adapter) {
        adapter.register(MediaChannelBean.class, new MediaChannelViewBinder());
    }
}
