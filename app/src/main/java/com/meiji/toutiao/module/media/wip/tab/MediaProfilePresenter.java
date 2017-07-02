package com.meiji.toutiao.module.media.wip.tab;

import android.support.v4.util.ArrayMap;

import com.meiji.toutiao.RetrofitFactory;
import com.meiji.toutiao.api.IMobileMediaApi;
import com.meiji.toutiao.bean.news.MultiNewsArticleDataBean;
import com.meiji.toutiao.utils.TimeUtil;

import java.util.List;

/**
 * Created by Meiji on 2017/7/1.
 */

public class MediaProfilePresenter implements IMediaProfile.Presenter {

    static final String KEY_MEDIAID = "mediaId";
    private IMediaProfile.View view;
    private String mediaid;
    private String time;

    MediaProfilePresenter(IMediaProfile.View view) {
        this.view = view;
        this.time = TimeUtil.getTimeStamp();
    }

    public void doRefresh() {

    }

    @Override
    public void doShowNetError() {

    }

    @Override
    public void doLoadData(ArrayMap<?, ?> map) {
        this.mediaid = (String) map.get(KEY_MEDIAID);
        RetrofitFactory.getRetrofit().create(IMobileMediaApi.class)
                .getMediaArticle(this.mediaid, this.time);
    }

    @Override
    public void doLoadMoreData() {

    }

    @Override
    public void doSetAdapter(List<MultiNewsArticleDataBean> dataBeen) {

    }
}
