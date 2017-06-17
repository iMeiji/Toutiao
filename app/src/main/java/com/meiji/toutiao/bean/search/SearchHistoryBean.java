package com.meiji.toutiao.bean.search;

/**
 * Created by Meiji on 2017/6/17.
 */

public class SearchHistoryBean {

    private String keyWord;
    private String time;
    private int type;

    public String getKeyWord() {
        return keyWord;
    }

    public void setKeyWord(String keyWord) {
        this.keyWord = keyWord;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }
}
