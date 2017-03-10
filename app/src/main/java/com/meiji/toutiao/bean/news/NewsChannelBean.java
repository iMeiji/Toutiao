package com.meiji.toutiao.bean.news;

/**
 * Created by Meiji on 2017/3/10.
 */

public class NewsChannelBean {

    private String channelId;
    private String channelName;
    private int isEnable;

    public String getChannelId() {
        return channelId;
    }

    public void setChannelId(String channelId) {
        this.channelId = channelId;
    }

    public String getChannelName() {
        return channelName;
    }

    public void setChannelName(String channelName) {
        this.channelName = channelName;
    }

    public int getIsEnable() {
        return isEnable;
    }

    public void setIsEnable(int isEnable) {
        this.isEnable = isEnable;
    }
}
