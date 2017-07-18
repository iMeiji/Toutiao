package com.meiji.toutiao.widget;

import android.content.Context;
import android.preference.Preference;
import android.util.AttributeSet;
import android.view.View;

import com.afollestad.materialdialogs.color.CircleView;
import com.meiji.toutiao.R;
import com.meiji.toutiao.util.SettingUtil;

/**
 * Created by Meiji on 2017/7/18.
 */

public class IconPreference extends Preference {

    private CircleView circleImageView;

    public IconPreference(Context context, AttributeSet attrs) {
        super(context, attrs);

        setWidgetLayoutResource(R.layout.item_icon_preference_preview);
    }

    @Override
    protected void onBindView(View view) {
        super.onBindView(view);
        int color = SettingUtil.getInstance().getColor();
        circleImageView = view.findViewById(R.id.iv_preview);
        circleImageView.setBackgroundColor(color);
    }

    public void setView() {
        int color = SettingUtil.getInstance().getColor();
        circleImageView.setBackgroundColor(color);
    }
}