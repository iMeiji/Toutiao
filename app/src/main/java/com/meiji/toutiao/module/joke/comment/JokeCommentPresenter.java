package com.meiji.toutiao.module.joke.comment;

import com.meiji.toutiao.ErrorAction;
import com.meiji.toutiao.api.IJokeApi;
import com.meiji.toutiao.bean.joke.JokeCommentBean;
import com.meiji.toutiao.util.RetrofitFactory;

import java.util.ArrayList;
import java.util.List;

import io.reactivex.annotations.NonNull;
import io.reactivex.functions.Consumer;
import io.reactivex.functions.Function;
import io.reactivex.schedulers.Schedulers;

/**
 * Created by Meiji on 2017/1/1.
 */

class JokeCommentPresenter implements IJokeComment.Presenter {

    private IJokeComment.View view;
    private String jokeId;
    private int count = -1;
    private int offset = 0;
    private List<JokeCommentBean.DataBean.RecentCommentsBean> commentsList = new ArrayList<>();

    JokeCommentPresenter(IJokeComment.View view) {
        this.view = view;
    }

    @Override
    public void doLoadData(String... jokeId_Count) {

        try {
            if (null == this.jokeId) {
                this.jokeId = jokeId_Count[0];
            }
            if (-1 == this.count) {
                this.count = Integer.parseInt(jokeId_Count[1]);
            }
        } catch (Exception e) {
            ErrorAction.print(e);
        }

        RetrofitFactory.getRetrofit().create(IJokeApi.class).getJokeComment(jokeId, offset)
                .subscribeOn(Schedulers.io())
                .observeOn(Schedulers.io())
                .map(new Function<JokeCommentBean, List<JokeCommentBean.DataBean.RecentCommentsBean>>() {
                    @Override
                    public List<JokeCommentBean.DataBean.RecentCommentsBean> apply(@NonNull JokeCommentBean jokeCommentBean) throws Exception {
                        return jokeCommentBean.getData().getRecent_comments();
                    }
                })
                .compose(view.<List<JokeCommentBean.DataBean.RecentCommentsBean>>bindToLife())
                .subscribe(new Consumer<List<JokeCommentBean.DataBean.RecentCommentsBean>>() {
                    @Override
                    public void accept(@NonNull List<JokeCommentBean.DataBean.RecentCommentsBean> recentCommentsBeen) throws Exception {
                        if (recentCommentsBeen.size() > 0) {
                            doSetAdapter(recentCommentsBeen);
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
        offset += 10;
        doLoadData();
    }

    @Override
    public void doSetAdapter(List<JokeCommentBean.DataBean.RecentCommentsBean> commentsBeanList) {
        commentsList.addAll(commentsBeanList);
        view.onSetAdapter(commentsList);
        view.onHideLoading();
    }

    @Override
    public void doRefresh() {
        if (commentsList.size() != 0) {
            commentsList.clear();
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
        if (commentsList.size() > 0) {
            view.onShowNoMore();
        }
    }
}
