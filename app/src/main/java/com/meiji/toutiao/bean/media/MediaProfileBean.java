package com.meiji.toutiao.bean.media;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.List;

/**
 * Created by Meiji on 2017/6/29.
 */

public class MediaProfileBean {

    /**
     * message : success
     * data : {"status":0,"is_followed":false,"current_user_id":0,"media_id":5540918998,"description":"慧眼看中国，我们愿做你的4D眼镜。独家调查、深度报道，在这里，新京报能让你得到不一样的满足。","apply_auth_url":"sslocal://webview?url=https%3A%2F%2Fapi.snssdk.com%2Fuser%2Fprofile%2Fauth%2Fguide%2F&bounce_disable=1&hide_bar=1&title=","is_following":false,"bottom_tab":[],"article_limit_enable":1,"verified_agency":"头条认证","bg_img_url":"http://p3.pstatp.com/origin/bc30011684fa86d4b71","verified_content":"新京报官方帐号","screen_name":"新京报","common_friends":[],"pgc_like_count":0,"visit_count_recent":659921,"star_chart":{},"user_verified":true,"top_tab":[{"url":"http://issub.snssdk.com/dongtai/list/v8","is_default":false,"show_name":"动态","type":"dongtai"},{"url":"","is_default":true,"show_name":"文章","type":"all"},{"url":"","is_default":false,"show_name":"视频","type":"video"},{"url":"http://isub.snssdk.com/2/user/tab_wenda/","is_default":false,"show_name":"问答","type":"wenda"}],"user_auth_info":"{\"auth_type\": \"0\", \"auth_info\": \"新京报官方帐号\"}","is_blocking":0,"is_blocked":0,"user_id":5540918998,"name":"新京报","big_avatar_url":"http://p3.pstatp.com/large/8532/7581013616","area":null,"private_letter_permission":1,"gender":0,"industry":null,"apply_auth_entry_title":"我的认证","share_url":"http://m.toutiao.com/profile/5540918998/","show_private_letter":0,"ugc_publish_media_id":1553029780342785,"avatar_url":"http://p3.pstatp.com/medium/8532/7581013616","followers_count":654244,"media_type":"7","followings_count":2,"medals":[]}
     */

    private String message;
    private DataBean data;

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public DataBean getData() {
        return data;
    }

    public void setData(DataBean data) {
        this.data = data;
    }

    public static class DataBean implements Parcelable {
        public static final Creator<DataBean> CREATOR = new Creator<DataBean>() {
            @Override
            public DataBean createFromParcel(Parcel in) {
                return new DataBean(in);
            }

            @Override
            public DataBean[] newArray(int size) {
                return new DataBean[size];
            }
        };
        /**
         * status : 0
         * is_followed : false
         * current_user_id : 0
         * media_id : 5540918998
         * description : 慧眼看中国，我们愿做你的4D眼镜。独家调查、深度报道，在这里，新京报能让你得到不一样的满足。
         * apply_auth_url : sslocal://webview?url=https%3A%2F%2Fapi.snssdk.com%2Fuser%2Fprofile%2Fauth%2Fguide%2F&bounce_disable=1&hide_bar=1&title=
         * is_following : false
         * bottom_tab : []
         * article_limit_enable : 1
         * verified_agency : 头条认证
         * bg_img_url : http://p3.pstatp.com/origin/bc30011684fa86d4b71
         * verified_content : 新京报官方帐号
         * screen_name : 新京报
         * common_friends : []
         * pgc_like_count : 0
         * visit_count_recent : 659921
         * star_chart : {}
         * user_verified : true
         * top_tab : [{"url":"http://issub.snssdk.com/dongtai/list/v8","is_default":false,"show_name":"动态","type":"dongtai"},{"url":"","is_default":true,"show_name":"文章","type":"all"},{"url":"","is_default":false,"show_name":"视频","type":"video"},{"url":"http://isub.snssdk.com/2/user/tab_wenda/","is_default":false,"show_name":"问答","type":"wenda"}]
         * user_auth_info : {"auth_type": "0", "auth_info": "新京报官方帐号"}
         * is_blocking : 0
         * is_blocked : 0
         * user_id : 5540918998
         * name : 新京报
         * big_avatar_url : http://p3.pstatp.com/large/8532/7581013616
         * area : null
         * private_letter_permission : 1
         * gender : 0
         * industry : null
         * apply_auth_entry_title : 我的认证
         * share_url : http://m.toutiao.com/profile/5540918998/
         * show_private_letter : 0
         * ugc_publish_media_id : 1553029780342785
         * avatar_url : http://p3.pstatp.com/medium/8532/7581013616
         * followers_count : 654244
         * media_type : 7
         * followings_count : 2
         * medals : []
         */

        private int status;
        private boolean is_followed;
        private int current_user_id;
        private String media_id;
        private String description;
        private String apply_auth_url;
        private boolean is_following;
        private int article_limit_enable;
        private String verified_agency;
        private String bg_img_url;
        private String verified_content;
        private String screen_name;
        private int pgc_like_count;
        private int visit_count_recent;
        private StarChartBean star_chart;
        private boolean user_verified;
        private String user_auth_info;
        private int is_blocking;
        private int is_blocked;
        private long user_id;
        private String name;
        private String big_avatar_url;
        private Object area;
        private int private_letter_permission;
        private int gender;
        private Object industry;
        private String apply_auth_entry_title;
        private String share_url;
        private int show_private_letter;
        private long ugc_publish_media_id;
        private String avatar_url;
        private String followers_count;
        private String media_type;
        private int followings_count;
        private List<?> bottom_tab;
        private List<?> common_friends;
        private List<TopTabBean> top_tab;
        private List<?> medals;

        protected DataBean(Parcel in) {
            status = in.readInt();
            is_followed = in.readByte() != 0;
            current_user_id = in.readInt();
            media_id = in.readString();
            description = in.readString();
            apply_auth_url = in.readString();
            is_following = in.readByte() != 0;
            article_limit_enable = in.readInt();
            verified_agency = in.readString();
            bg_img_url = in.readString();
            verified_content = in.readString();
            screen_name = in.readString();
            pgc_like_count = in.readInt();
            visit_count_recent = in.readInt();
            user_verified = in.readByte() != 0;
            user_auth_info = in.readString();
            is_blocking = in.readInt();
            is_blocked = in.readInt();
            user_id = in.readLong();
            name = in.readString();
            big_avatar_url = in.readString();
            private_letter_permission = in.readInt();
            gender = in.readInt();
            apply_auth_entry_title = in.readString();
            share_url = in.readString();
            show_private_letter = in.readInt();
            ugc_publish_media_id = in.readLong();
            avatar_url = in.readString();
            followers_count = in.readString();
            media_type = in.readString();
            followings_count = in.readInt();
        }

        @Override
        public void writeToParcel(Parcel dest, int flags) {
            dest.writeInt(status);
            dest.writeByte((byte) (is_followed ? 1 : 0));
            dest.writeInt(current_user_id);
            dest.writeString(media_id);
            dest.writeString(description);
            dest.writeString(apply_auth_url);
            dest.writeByte((byte) (is_following ? 1 : 0));
            dest.writeInt(article_limit_enable);
            dest.writeString(verified_agency);
            dest.writeString(bg_img_url);
            dest.writeString(verified_content);
            dest.writeString(screen_name);
            dest.writeInt(pgc_like_count);
            dest.writeInt(visit_count_recent);
            dest.writeByte((byte) (user_verified ? 1 : 0));
            dest.writeString(user_auth_info);
            dest.writeInt(is_blocking);
            dest.writeInt(is_blocked);
            dest.writeLong(user_id);
            dest.writeString(name);
            dest.writeString(big_avatar_url);
            dest.writeInt(private_letter_permission);
            dest.writeInt(gender);
            dest.writeString(apply_auth_entry_title);
            dest.writeString(share_url);
            dest.writeInt(show_private_letter);
            dest.writeLong(ugc_publish_media_id);
            dest.writeString(avatar_url);
            dest.writeString(followers_count);
            dest.writeString(media_type);
            dest.writeInt(followings_count);
        }

        @Override
        public int describeContents() {
            return 0;
        }

        public int getStatus() {
            return status;
        }

        public void setStatus(int status) {
            this.status = status;
        }

        public boolean isIs_followed() {
            return is_followed;
        }

        public void setIs_followed(boolean is_followed) {
            this.is_followed = is_followed;
        }

        public int getCurrent_user_id() {
            return current_user_id;
        }

        public void setCurrent_user_id(int current_user_id) {
            this.current_user_id = current_user_id;
        }

        public String getMedia_id() {
            return media_id;
        }

        public void setMedia_id(String media_id) {
            this.media_id = media_id;
        }

        public String getDescription() {
            return description;
        }

        public void setDescription(String description) {
            this.description = description;
        }

        public String getApply_auth_url() {
            return apply_auth_url;
        }

        public void setApply_auth_url(String apply_auth_url) {
            this.apply_auth_url = apply_auth_url;
        }

        public boolean isIs_following() {
            return is_following;
        }

        public void setIs_following(boolean is_following) {
            this.is_following = is_following;
        }

        public int getArticle_limit_enable() {
            return article_limit_enable;
        }

        public void setArticle_limit_enable(int article_limit_enable) {
            this.article_limit_enable = article_limit_enable;
        }

        public String getVerified_agency() {
            return verified_agency;
        }

        public void setVerified_agency(String verified_agency) {
            this.verified_agency = verified_agency;
        }

        public String getBg_img_url() {
            return bg_img_url;
        }

        public void setBg_img_url(String bg_img_url) {
            this.bg_img_url = bg_img_url;
        }

        public String getVerified_content() {
            return verified_content;
        }

        public void setVerified_content(String verified_content) {
            this.verified_content = verified_content;
        }

        public String getScreen_name() {
            return screen_name;
        }

        public void setScreen_name(String screen_name) {
            this.screen_name = screen_name;
        }

        public int getPgc_like_count() {
            return pgc_like_count;
        }

        public void setPgc_like_count(int pgc_like_count) {
            this.pgc_like_count = pgc_like_count;
        }

        public int getVisit_count_recent() {
            return visit_count_recent;
        }

        public void setVisit_count_recent(int visit_count_recent) {
            this.visit_count_recent = visit_count_recent;
        }

        public StarChartBean getStar_chart() {
            return star_chart;
        }

        public void setStar_chart(StarChartBean star_chart) {
            this.star_chart = star_chart;
        }

        public boolean isUser_verified() {
            return user_verified;
        }

        public void setUser_verified(boolean user_verified) {
            this.user_verified = user_verified;
        }

        public String getUser_auth_info() {
            return user_auth_info;
        }

        public void setUser_auth_info(String user_auth_info) {
            this.user_auth_info = user_auth_info;
        }

        public int getIs_blocking() {
            return is_blocking;
        }

        public void setIs_blocking(int is_blocking) {
            this.is_blocking = is_blocking;
        }

        public int getIs_blocked() {
            return is_blocked;
        }

        public void setIs_blocked(int is_blocked) {
            this.is_blocked = is_blocked;
        }

        public long getUser_id() {
            return user_id;
        }

        public void setUser_id(long user_id) {
            this.user_id = user_id;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public String getBig_avatar_url() {
            return big_avatar_url;
        }

        public void setBig_avatar_url(String big_avatar_url) {
            this.big_avatar_url = big_avatar_url;
        }

        public Object getArea() {
            return area;
        }

        public void setArea(Object area) {
            this.area = area;
        }

        public int getPrivate_letter_permission() {
            return private_letter_permission;
        }

        public void setPrivate_letter_permission(int private_letter_permission) {
            this.private_letter_permission = private_letter_permission;
        }

        public int getGender() {
            return gender;
        }

        public void setGender(int gender) {
            this.gender = gender;
        }

        public Object getIndustry() {
            return industry;
        }

        public void setIndustry(Object industry) {
            this.industry = industry;
        }

        public String getApply_auth_entry_title() {
            return apply_auth_entry_title;
        }

        public void setApply_auth_entry_title(String apply_auth_entry_title) {
            this.apply_auth_entry_title = apply_auth_entry_title;
        }

        public String getShare_url() {
            return share_url;
        }

        public void setShare_url(String share_url) {
            this.share_url = share_url;
        }

        public int getShow_private_letter() {
            return show_private_letter;
        }

        public void setShow_private_letter(int show_private_letter) {
            this.show_private_letter = show_private_letter;
        }

        public long getUgc_publish_media_id() {
            return ugc_publish_media_id;
        }

        public void setUgc_publish_media_id(long ugc_publish_media_id) {
            this.ugc_publish_media_id = ugc_publish_media_id;
        }

        public String getAvatar_url() {
            return avatar_url;
        }

        public void setAvatar_url(String avatar_url) {
            this.avatar_url = avatar_url;
        }

        public String getFollowers_count() {
            return followers_count;
        }

        public void setFollowers_count(String followers_count) {
            this.followers_count = followers_count;
        }

        public String getMedia_type() {
            return media_type;
        }

        public void setMedia_type(String media_type) {
            this.media_type = media_type;
        }

        public int getFollowings_count() {
            return followings_count;
        }

        public void setFollowings_count(int followings_count) {
            this.followings_count = followings_count;
        }

        public List<?> getBottom_tab() {
            return bottom_tab;
        }

        public void setBottom_tab(List<?> bottom_tab) {
            this.bottom_tab = bottom_tab;
        }

        public List<?> getCommon_friends() {
            return common_friends;
        }

        public void setCommon_friends(List<?> common_friends) {
            this.common_friends = common_friends;
        }

        public List<TopTabBean> getTop_tab() {
            return top_tab;
        }

        public void setTop_tab(List<TopTabBean> top_tab) {
            this.top_tab = top_tab;
        }

        public List<?> getMedals() {
            return medals;
        }

        public void setMedals(List<?> medals) {
            this.medals = medals;
        }

        public static class StarChartBean {
        }

        public static class TopTabBean {
            /**
             * url : http://issub.snssdk.com/dongtai/list/v8
             * is_default : false
             * show_name : 动态
             * type : dongtai
             */

            private String url;
            private boolean is_default;
            private String show_name;
            private String type;

            public String getUrl() {
                return url;
            }

            public void setUrl(String url) {
                this.url = url;
            }

            public boolean isIs_default() {
                return is_default;
            }

            public void setIs_default(boolean is_default) {
                this.is_default = is_default;
            }

            public String getShow_name() {
                return show_name;
            }

            public void setShow_name(String show_name) {
                this.show_name = show_name;
            }

            public String getType() {
                return type;
            }

            public void setType(String type) {
                this.type = type;
            }
        }
    }
}
