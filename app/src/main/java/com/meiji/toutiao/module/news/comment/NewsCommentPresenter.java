package com.meiji.toutiao.module.news.comment;

import com.meiji.toutiao.ErrorAction;
import com.meiji.toutiao.api.IMobileNewsApi;
import com.meiji.toutiao.bean.news.NewsCommentBean;
import com.meiji.toutiao.util.RetrofitFactory;

import java.util.ArrayList;
import java.util.List;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.annotations.NonNull;
import io.reactivex.functions.Consumer;
import io.reactivex.functions.Function;
import io.reactivex.schedulers.Schedulers;

/**
 * Created by Meiji on 2016/12/20.
 */

public class NewsCommentPresenter implements INewsComment.Presenter {

    private static final String TAG = "NewsCommentPresenter";
    private INewsComment.View view;
    private String groupId;
    private String itemId;
    private int offset = 0;
    private List<NewsCommentBean.DataBean.CommentBean> commentsBeanList = new ArrayList<>();

    public NewsCommentPresenter(INewsComment.View view) {
        this.view = view;
    }

    @Override
    public void doLoadData(String... groupId_ItemId) {

        try {
            if (null == this.groupId) {
                this.groupId = groupId_ItemId[0];
            }
            if (null == this.itemId) {
                this.itemId = groupId_ItemId[1];
            }
        } catch (Exception e) {
            ErrorAction.print(e);
        }

        RetrofitFactory.getRetrofit().create(IMobileNewsApi.class)
                .getNewsComment(groupId, offset)
                .subscribeOn(Schedulers.io())
                .map(new Function<NewsCommentBean, List<NewsCommentBean.DataBean.CommentBean>>() {
                    @Override
                    public List<NewsCommentBean.DataBean.CommentBean> apply(@NonNull NewsCommentBean newsCommentBean) throws Exception {
                        List<NewsCommentBean.DataBean.CommentBean> data = new ArrayList<>();
                        for (NewsCommentBean.DataBean bean : newsCommentBean.getData()) {
                            data.add(bean.getComment());
                        }
                        return data;
                    }
                })
                .compose(view.<List<NewsCommentBean.DataBean.CommentBean>>bindToLife())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Consumer<List<NewsCommentBean.DataBean.CommentBean>>() {
                    @Override
                    public void accept(@NonNull List<NewsCommentBean.DataBean.CommentBean> list) throws Exception {
                        if (null != list && list.size() > 0) {
                            doSetAdapter(list);
                        } else {
                            doShowNoMore();
                        }
                    }
                }, new Consumer<Throwable>() {
                    @Override
                    public void accept(@NonNull Throwable throwable) throws Exception {
                        doShowNetError();
                        ErrorAction.print(throwable);
                    }
                });
    }

    @Override
    public void doLoadMoreData() {
        offset += 20;
        doLoadData();
    }

    @Override
    public void doSetAdapter(List<NewsCommentBean.DataBean.CommentBean> list) {
        commentsBeanList.addAll(list);
        view.onSetAdapter(commentsBeanList);
        view.onHideLoading();
    }

    @Override
    public void doRefresh() {
        if (commentsBeanList.size() != 0) {
            commentsBeanList.clear();
            offset = 0;
        }
        doLoadData();
    }

    @Override
    public void doShowNetError() {
        view.onHideLoading();
        view.onShowNetError();
    }

    @Override
    public void doShowNoMore() {
        view.onHideLoading();
        view.onShowNoMore();
    }
}
