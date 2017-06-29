package com.meiji.toutiao.module.media.wip;

import android.os.Bundle;

import com.meiji.toutiao.module.base.BaseListFragment;
import com.meiji.toutiao.module.base.IBasePresenter;

import java.util.List;

/**
 * Created by Meiji on 2017/6/29.
 */

public class MediaVideoFragment extends BaseListFragment<IBasePresenter> {

    private static final String ARG_MEDIAID = "mediaId";
    private String mediaId;

    public static MediaVideoFragment newInstance(String mediaId) {
        Bundle args = new Bundle();
        args.putString(ARG_MEDIAID, mediaId);
        MediaVideoFragment fragment = new MediaVideoFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onRefresh() {

    }

    @Override
    public void setPresenter(IBasePresenter presenter) {

    }

    @Override
    public void onSetAdapter(List<?> list) {

    }

    @Override
    protected void initData() {

    }

    @Override
    public void fetchData() {

    }
}
