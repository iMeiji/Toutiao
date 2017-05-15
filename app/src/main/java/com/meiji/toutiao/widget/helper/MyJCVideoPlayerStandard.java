package com.meiji.toutiao.widget.helper;

import android.content.Context;
import android.util.AttributeSet;
import android.view.View;

import com.meiji.toutiao.R;

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
    public void setUp(String url, int screen, Object... objects) {
        super.setUp(url, screen, objects);
        if (objects.length == 0)
            return;
        titleTextView.setText(objects[0].toString());
        // 隐藏所有按钮 后期可增加一个分辨率选项
        fullscreenButton.setVisibility(View.GONE);
        backButton.setVisibility(View.GONE);
        tinyBackImageView.setVisibility(View.GONE);
        if (currentScreen == SCREEN_WINDOW_FULLSCREEN) {
//            fullscreenButton.setVisibility(View.GONE);
//            fullscreenButton.setImageResource(R.drawable.jc_enlarge);
//            backButton.setVisibility(View.VISIBLE);
//            tinyBackImageView.setVisibility(View.INVISIBLE);
            changeStartButtonSize((int) getResources().getDimension(R.dimen.jc_start_button_w_h_fullscreen));
        } else if (currentScreen == SCREEN_LAYOUT_NORMAL
                || currentScreen == SCREEN_LAYOUT_LIST) {
//            fullscreenButton.setImageResource(R.drawable.jc_enlarge);
//            backButton.setVisibility(View.GONE);
//            tinyBackImageView.setVisibility(View.INVISIBLE);
            changeStartButtonSize((int) getResources().getDimension(R.dimen.jc_start_button_w_h_normal));
        } else if (currentScreen == SCREEN_WINDOW_TINY) {
//            tinyBackImageView.setVisibility(View.VISIBLE);
            setAllControlsVisible(View.INVISIBLE, View.INVISIBLE, View.INVISIBLE,
                    View.INVISIBLE, View.INVISIBLE, View.INVISIBLE, View.INVISIBLE);
        }
    }

    @Override
    public int getLayoutId() {
        return R.layout.my_jc_layout_standard;
    }
}
