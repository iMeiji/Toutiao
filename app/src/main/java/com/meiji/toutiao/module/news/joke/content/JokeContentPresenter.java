package com.meiji.toutiao.module.news.joke.content;

import com.meiji.toutiao.RetrofitFactory;
import com.meiji.toutiao.api.IJokeApi;
import com.meiji.toutiao.bean.news.joke.JokeContentBean;
import com.meiji.toutiao.module.news.joke.comment.JokeCommentActivity;

import java.util.ArrayList;
import java.util.List;

import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.annotations.NonNull;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Function;
import io.reactivex.schedulers.Schedulers;

/**
 * Created by Meiji on 2016/12/28.
 */

class JokeContentPresenter implements IJokeContent.Presenter {

    private IJokeContent.View view;
    private List<JokeContentBean.DataBean.GroupBean> groupList = new ArrayList<>();

    JokeContentPresenter(IJokeContent.View view) {
        this.view = view;
    }

    @Override
    public void doLoadData() {
        RetrofitFactory.getRetrofit().create(IJokeApi.class).getJokeContent()
                .subscribeOn(Schedulers.io())
                .observeOn(Schedulers.io())
                .map(new Function<JokeContentBean, List<JokeContentBean.DataBean.GroupBean>>() {
                    @Override
                    public List<JokeContentBean.DataBean.GroupBean> apply(@NonNull JokeContentBean jokeContentBean) throws Exception {
                        List<JokeContentBean.DataBean> data = jokeContentBean.getData();
                        for (JokeContentBean.DataBean dataBean : data) {
                            groupList.add(dataBean.getGroup());
                        }
                        return groupList;
                    }
                })
                .observeOn(AndroidSchedulers.mainThread())
                .compose(view.<List<JokeContentBean.DataBean.GroupBean>>bindToLife())
                .subscribe(new Observer<List<JokeContentBean.DataBean.GroupBean>>() {
                    @Override
                    public void onSubscribe(@NonNull Disposable d) {
                    }

                    @Override
                    public void onNext(@NonNull List<JokeContentBean.DataBean.GroupBean> o) {
                        doSetAdapter();
                    }

                    @Override
                    public void onError(@NonNull Throwable e) {
                        doShowNetError();
                    }

                    @Override
                    public void onComplete() {

                    }
                });
    }

    @Override
    public void doLoadMoreData() {
        doLoadData();
    }

    @Override
    public void doSetAdapter() {
        view.onSetAdapter(groupList);
        view.onHideLoading();
        // 释放内存
        if (groupList.size() > 100) {
            groupList.clear();
        }
    }

    @Override
    public void doRefresh() {
        if (groupList.size() != 0) {
            groupList.clear();
        }
        doLoadData();
    }

    @Override
    public void doShowNetError() {
        view.onHideLoading();
        view.onShowNetError();
    }

    @Override
    public void doOnClickItem(int position) {
        JokeCommentActivity.launch(groupList.get(position));
    }
}
