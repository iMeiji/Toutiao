package com.meiji.toutiao.setting;

import android.app.Fragment;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.jaygoo.widget.RangeSeekBar;
import com.meiji.toutiao.R;
import com.meiji.toutiao.module.base.BaseListFragment;
import com.meiji.toutiao.util.RxBus;
import com.meiji.toutiao.util.SettingUtil;

import java.text.DecimalFormat;

/**
 * Created by Meiji on 2017/8/16.
 */

public class TextSizeFragment extends Fragment {

    private RangeSeekBar seekbar;
    private TextView text;
    private DecimalFormat df = new DecimalFormat("0");
    private int currentSize = -1;
    private SettingUtil settingUtil = SettingUtil.getInstance();

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_setting_textsize, container, false);
        initView(view);
        return view;
    }

    private void initView(View view) {
        seekbar = view.findViewById(R.id.seekbar);
        text = view.findViewById(R.id.text);
        text.setTextSize(settingUtil.getTextSize());
        seekbar.setValue(settingUtil.getTextSize() - 14);
        seekbar.setLineColor(0, settingUtil.getColor());
        seekbar.setOnRangeChangedListener(new RangeSeekBar.OnRangeChangedListener() {
            @Override
            public void onRangeChanged(RangeSeekBar view, final float min, float max, boolean isFromUser) {
                if (isFromUser) {
                    int size = Integer.parseInt(df.format(min));
                    if (currentSize != size) {
                        setText(size);
                        currentSize = size;
                    }
                }
            }

            @Override
            public void onStartTrackingTouch(RangeSeekBar view, boolean isLeft) {

            }

            @Override
            public void onStopTrackingTouch(RangeSeekBar view, boolean isLeft) {

            }
        });
    }

    private void setText(int size) {
        // 最小 14sp
        size = 14 + size;
        text.setTextSize(size);
        settingUtil.setTextSize(size);
        RxBus.getInstance().post(BaseListFragment.TAG, size);
    }
}
