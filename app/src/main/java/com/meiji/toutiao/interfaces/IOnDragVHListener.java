package com.meiji.toutiao.interfaces;

/**
 * Created by Meiji on 2017/3/11.
 */

public interface IOnDragVHListener {

    /**
     * Item被选中时触发
     */
    void onItemSelected();


    /**
     * Item在拖拽结束/滑动结束后触发
     */
    void onItemFinish();
}
