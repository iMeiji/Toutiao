package com.meiji.toutiao.bean.media;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by Meiji on 2017/4/7.
 */

public class MediaChannelBean implements Parcelable {

    public static final Creator<MediaChannelBean> CREATOR = new Creator<MediaChannelBean>() {
        @Override
        public MediaChannelBean createFromParcel(Parcel in) {
            return new MediaChannelBean(in);
        }

        @Override
        public MediaChannelBean[] newArray(int size) {
            return new MediaChannelBean[size];
        }
    };
    private String id;
    private String name;
    private String avatar;
    private String type;
    private String followCount;
    private String descText;
    private String url;

    public MediaChannelBean() {
    }

    protected MediaChannelBean(Parcel in) {
        id = in.readString();
        name = in.readString();
        avatar = in.readString();
        type = in.readString();
        followCount = in.readString();
        descText = in.readString();
        url = in.readString();
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(id);
        dest.writeString(name);
        dest.writeString(avatar);
        dest.writeString(type);
        dest.writeString(followCount);
        dest.writeString(descText);
        dest.writeString(url);
    }

    @Override
    public int describeContents() {
        return 0;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getAvatar() {
        return avatar;
    }

    public void setAvatar(String avatar) {
        this.avatar = avatar;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getFollowCount() {
        return followCount;
    }

    public void setFollowCount(String followCount) {
        this.followCount = followCount;
    }

    public String getDescText() {
        return descText;
    }

    public void setDescText(String descText) {
        this.descText = descText;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }
}
