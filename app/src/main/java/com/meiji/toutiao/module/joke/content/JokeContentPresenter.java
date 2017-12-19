package com.meiji.toutiao.module.joke.content;

import com.meiji.toutiao.Constant;
import com.meiji.toutiao.ErrorAction;
import com.meiji.toutiao.api.IJokeApi;
import com.meiji.toutiao.bean.joke.JokeContentBean;
import com.meiji.toutiao.util.RetrofitFactory;
import com.meiji.toutiao.util.TimeUtil;
import com.meiji.toutiao.util.ToutiaoUtil;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import io.reactivex.android.schedulers.AndroidSchedulers;
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
    private String time;

    JokeContentPresenter(IJokeContent.View view) {
        this.view = view;
        this.time = TimeUtil.getCurrentTimeStamp();
    }

    @Override
    public void doLoadData() {

        Map<String, String> map = ToutiaoUtil.getAsCp();

        RetrofitFactory.getRetrofit().create(IJokeApi.class).getJokeContent(time, map.get(Constant.AS), map.get(Constant.CP))
                .subscribeOn(Schedulers.io())
                .map(new Function<JokeContentBean, List<JokeContentBean.DataBean.GroupBean>>() {
                    @Override
                    public List<JokeContentBean.DataBean.GroupBean> apply(@NonNull JokeContentBean jokeContentBean) throws Exception {
                        List<JokeContentBean.DataBean> data = jokeContentBean.getData();
                        for (JokeContentBean.DataBean dataBean : data) {
                            groupList.add(dataBean.getGroup());
                        }
                        time = jokeContentBean.getNext().getMax_behot_time() + "";
                        return groupList;
                    }
                })
                .compose(view.<List<JokeContentBean.DataBean.GroupBean>>bindToLife())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Consumer<List<JokeContentBean.DataBean.GroupBean>>() {
                    @Override
                    public void accept(@NonNull List<JokeContentBean.DataBean.GroupBean> groupBeen) throws Exception {
                        if (groupBeen.size() > 0) {
                            doSetAdapter();
                        } else {
                            doShowNoMore();
                        }
                    }
                }, ErrorAction.error());
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
        view.onShowLoading();
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
