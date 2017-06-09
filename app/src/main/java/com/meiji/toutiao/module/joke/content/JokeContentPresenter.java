package com.meiji.toutiao.module.joke.content;

import com.meiji.toutiao.RetrofitFactory;
import com.meiji.toutiao.api.IJokeApi;
import com.meiji.toutiao.bean.joke.JokeContentBean;

import java.util.ArrayList;
import java.util.List;

import io.reactivex.annotations.NonNull;
import io.reactivex.functions.Consumer;
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
        // 释放内存
        if (groupList.size() > 100) {
            groupList.clear();
        }

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
                .compose(view.<List<JokeContentBean.DataBean.GroupBean>>bindToLife())
                .subscribe(new Consumer<List<JokeContentBean.DataBean.GroupBean>>() {
                    @Override
                    public void accept(@NonNull List<JokeContentBean.DataBean.GroupBean> groupBeen) throws Exception {
                        doSetAdapter();
                    }
                }, new Consumer<Throwable>() {
                    @Override
                    public void accept(@NonNull Throwable throwable) throws Exception {

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
}
