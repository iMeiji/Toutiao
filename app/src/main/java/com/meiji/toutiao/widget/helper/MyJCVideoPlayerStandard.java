package com.meiji.toutiao.widget.helper;

import android.content.Context;
import android.util.AttributeSet;
import android.view.View;

import com.meiji.toutiao.R;
import com.meiji.toutiao.RxBus;
import com.meiji.toutiao.module.video.content.VideoContentActivity;

import fm.jiecao.jcvideoplayer_lib.JCVideoPlayerStandard;

/**
 * Created by Meiji on 2017/5/15.
 */

public class MyJCVideoPlayerStandard extends JCVideoPlayerStandard {

    public MyJCVideoPlayerStandard(Context context) {
        super(context);
    }

    public MyJCVideoPlayerStandard(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    @Override
    public void onClick(View v) {
        super.onClick(v);
        int id = v.getId();
        if (id == R.id.back || id == R.id.back_tiny || id == R.id.fullscreen) {
            RxBus.getInstance().post(VideoContentActivity.TAG, backPress());
        }
    }
}
