package com.meiji.toutiao.module.news.comment;

import com.meiji.toutiao.RetrofitFactory;
import com.meiji.toutiao.api.IMobileNewsApi;
import com.meiji.toutiao.bean.news.NewsCommentMobileBean;

import java.util.ArrayList;
import java.util.List;

import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.annotations.NonNull;
import io.reactivex.disposables.Disposable;
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
    private List<NewsCommentMobileBean.DataBean.CommentBean> commentsBeanList = new ArrayList<>();

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
        } catch (ArrayIndexOutOfBoundsException e) {
            e.printStackTrace();
        }

        RetrofitFactory.getRetrofit().create(IMobileNewsApi.class)
                .getNewsComment(groupId, offset)
                .subscribeOn(Schedulers.io())
                .observeOn(Schedulers.io())
                .map(new Function<NewsCommentMobileBean, List<NewsCommentMobileBean.DataBean.CommentBean>>() {
                    @Override
                    public List<NewsCommentMobileBean.DataBean.CommentBean> apply(@NonNull NewsCommentMobileBean newsCommentMobileBean) throws Exception {
                        List<NewsCommentMobileBean.DataBean.CommentBean> data = new ArrayList<>();
                        for (NewsCommentMobileBean.DataBean bean : newsCommentMobileBean.getData()) {
                            data.add(bean.getComment());
                        }
                        return data;
                    }
                })
                .observeOn(AndroidSchedulers.mainThread())
                .compose(view.<List<NewsCommentMobileBean.DataBean.CommentBean>>bindToLife())
                .subscribe(new Observer<List<NewsCommentMobileBean.DataBean.CommentBean>>() {
                    @Override
                    public void onSubscribe(@NonNull Disposable d) {

                    }

                    @Override
                    public void onNext(@NonNull List<NewsCommentMobileBean.DataBean.CommentBean> commentBeen) {
                        if (commentBeen.size() > 0) {
                            doSetAdapter(commentBeen);
                        } else {
                            doShowNoMore();
                        }
                    }

                    @Override
                    public void onError(@NonNull Throwable e) {
                        e.printStackTrace();
                        doShowNetError();
                    }

                    @Override
                    public void onComplete() {

                    }
                });
    }

    @Override
    public void doLoadMoreData() {
        offset += 10;
        doLoadData();
    }

    @Override
    public void doSetAdapter(List<NewsCommentMobileBean.DataBean.CommentBean> list) {
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

    @Override
    public String doGetCopyContent(int position) {
        return commentsBeanList.get(position).getText();
    }
}
