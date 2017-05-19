package com.meiji.toutiao.bean.video;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.SerializedName;

import java.util.List;

/**
 * Created by Meiji on 2017/3/29.
 */

public class VideoArticleBean {

    /**
     * has_more : true
     * message : success
     * next : {"max_behot_time":1490741264}
     */

    private boolean has_more;
    private String message;
    private NextBean next;
    private List<DataBean> data;

    public boolean isHas_more() {
        return has_more;
    }

    public void setHas_more(boolean has_more) {
        this.has_more = has_more;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public NextBean getNext() {
        return next;
    }

    public void setNext(NextBean next) {
        this.next = next;
    }

    public List<DataBean> getData() {
        return data;
    }

    public void setData(List<DataBean> data) {
        this.data = data;
    }

    public static class NextBean {
        /**
         * max_behot_time : 1490741264
         */

        private int max_behot_time;

        public int getMax_behot_time() {
            return max_behot_time;
        }

        public void setMax_behot_time(int max_behot_time) {
            this.max_behot_time = max_behot_time;
        }
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
         * video_id : d23d96e2e7a84494853546cd0bf12cef
         * media_name : 龙猫公社
         * abstract : 实拍钓友钓上300公斤巨型鲶鱼
         * impression_count : 15443946
         * image_list : []
         * media_avatar_url : http://p3.pstatp.com/large/4110000af483493ebd1
         * external_visit_count : 108197
         * article_type : 0
         * more_mode : false
         * tag : news
         * is_favorite : 0
         * has_m3u8_video : 0
         * keywords : 巨型鲶鱼,钓友钓
         * video_duration : 634
         * has_mp4_video : 0
         * favorite_count : 1738
         * display_url : http://toutiao.com/group/6384387360028623361/
         * article_sub_type : 0
         * bury_count : 510
         * title : 实拍钓友钓上300公斤巨型鲶鱼
         * datetime : 2017-03-29 11:59
         * has_video : true
         * share_url : http://toutiao.com/group/6384387360028623361/?iid=56853474916&app=news_article
         * id : 6384387360028624000
         * source : 龙猫公社
         * comment_count : 187
         * article_url : http://toutiao.com/group/6384387360028623361/
         * create_time : 1486481312
         * recommend : 0
         * image_url : http://p9.pstatp.com/list/16800003254264979704
         * tips : 0
         * aggr_type : 1
         * item_source_url : /item/6384387360028623361/
         * media_url : http://toutiao.com/m6208195991/
         * display_time : 1486481018
         * publish_time : 1486481018
         * go_detail_count : 334085
         * group_flags : 65
         * middle_mode : true
         * display_title :
         * gallary_image_count : 0
         * item_seo_url : /item/6384387360028623361/
         * tag_id : 6384387360028624000
         * source_url : /group/6384387360028623361/
         * video_style : 0
         * article_genre : video
         * large_mode : false
         * large_image_list : [{"url":"http://p9.pstatp.com/list/16800003254264979704","width":640,"url_list":[{"url":"http://p9.pstatp.com/list/16800003254264979704"},{"url":"http://pb3.pstatp.com/list/16800003254264979704"},{"url":"http://pb3.pstatp.com/list/16800003254264979704"}],"uri":"list/16800003254264979704","height":360}]
         * item_id : 6384387360028624000
         * video_detail_info : {"video_detail_info":{"show_pgc_subscribe":1,"detail_video_large_image":{"url":"http://p9.pstatp.com/video1609/16800003254264979704","width":580,"url_list":[{"url":"http://p9.pstatp.com/video1609/16800003254264979704"},{"url":"http://pb3.pstatp.com/video1609/16800003254264979704"},{"url":"http://pb3.pstatp.com/video1609/16800003254264979704"}],"uri":"video1609/16800003254264979704","height":327},"video_id":"d23d96e2e7a84494853546cd0bf12cef","group_flags":32832,"direct_play":1},"video_type":0,"direct_play":1}
         * natant_level : 0
         * is_digg : 0
         * seo_url : /group/6384387360028623361/
         * repin_count : 1738
         * url : http://toutiao.com/group/6384387360028623361/
         * video_duration_str : 10:34
         * level : 0
         * digg_count : 1569
         * behot_time : 1490759987
         * hot : 0
         * preload_web : 0
         * comments_count : 187
         * video_play_count : 3068659
         * has_image : false
         * is_bury : 0
         * video_resulotion : ultra
         * group_id : 6384387360028624000
         * middle_image : http://p9.pstatp.com/list/16800003254264979704
         * honey : true
         */

        private String video_id;
        private String media_name;
        @SerializedName("abstract")
        private String abstractX;
        private int impression_count;
        private String media_avatar_url;
        private int external_visit_count;
        private int article_type;
        private boolean more_mode;
        private String tag;
        private int is_favorite;
        private int has_m3u8_video;
        private String keywords;
        private int video_duration;
        private int has_mp4_video;
        private int favorite_count;
        private String display_url;
        private int article_sub_type;
        private int bury_count;
        private String title;
        private String datetime;
        private boolean has_video;
        private String share_url;
        private long id;
        private String source;
        private int comment_count;
        private String article_url;
        private int create_time;
        private int recommend;
        private String image_url;
        private int tips;
        private int aggr_type;
        private String item_source_url;
        private String media_url;
        private int display_time;
        private int publish_time;
        private int go_detail_count;
        private int group_flags;
        private boolean middle_mode;
        private String display_title;
        private int gallary_image_count;
        private String item_seo_url;
        private long tag_id;
        private String source_url;
        private int video_style;
        private String article_genre;
        private boolean large_mode;
        private long item_id;
        private VideoDetailInfoBeanX video_detail_info;
        private int natant_level;
        private int is_digg;
        private String seo_url;
        private int repin_count;
        private String url;
        private String video_duration_str;
        private int level;
        private int digg_count;
        private int behot_time;
        private int hot;
        private int preload_web;
        private int comments_count;
        private int video_play_count;
        private boolean has_image;
        private int is_bury;
        private String video_resulotion;
        private long group_id;
        private boolean honey;

        public DataBean() {

        }

        protected DataBean(Parcel in) {
            video_id = in.readString();
            media_name = in.readString();
            abstractX = in.readString();
            impression_count = in.readInt();
            media_avatar_url = in.readString();
            external_visit_count = in.readInt();
            article_type = in.readInt();
            more_mode = in.readByte() != 0;
            tag = in.readString();
            is_favorite = in.readInt();
            has_m3u8_video = in.readInt();
            keywords = in.readString();
            video_duration = in.readInt();
            has_mp4_video = in.readInt();
            favorite_count = in.readInt();
            display_url = in.readString();
            article_sub_type = in.readInt();
            bury_count = in.readInt();
            title = in.readString();
            datetime = in.readString();
            has_video = in.readByte() != 0;
            share_url = in.readString();
            id = in.readLong();
            source = in.readString();
            comment_count = in.readInt();
            article_url = in.readString();
            create_time = in.readInt();
            recommend = in.readInt();
            image_url = in.readString();
            tips = in.readInt();
            aggr_type = in.readInt();
            item_source_url = in.readString();
            media_url = in.readString();
            display_time = in.readInt();
            publish_time = in.readInt();
            go_detail_count = in.readInt();
            group_flags = in.readInt();
            middle_mode = in.readByte() != 0;
            display_title = in.readString();
            gallary_image_count = in.readInt();
            item_seo_url = in.readString();
            tag_id = in.readLong();
            source_url = in.readString();
            video_style = in.readInt();
            article_genre = in.readString();
            large_mode = in.readByte() != 0;
            item_id = in.readLong();
            natant_level = in.readInt();
            is_digg = in.readInt();
            seo_url = in.readString();
            repin_count = in.readInt();
            url = in.readString();
            video_duration_str = in.readString();
            level = in.readInt();
            digg_count = in.readInt();
            behot_time = in.readInt();
            hot = in.readInt();
            preload_web = in.readInt();
            comments_count = in.readInt();
            video_play_count = in.readInt();
            has_image = in.readByte() != 0;
            is_bury = in.readInt();
            video_resulotion = in.readString();
            group_id = in.readLong();
            honey = in.readByte() != 0;
        }

        @Override
        public void writeToParcel(Parcel dest, int flags) {
            dest.writeString(video_id);
            dest.writeString(media_name);
            dest.writeString(abstractX);
            dest.writeInt(impression_count);
            dest.writeString(media_avatar_url);
            dest.writeInt(external_visit_count);
            dest.writeInt(article_type);
            dest.writeByte((byte) (more_mode ? 1 : 0));
            dest.writeString(tag);
            dest.writeInt(is_favorite);
            dest.writeInt(has_m3u8_video);
            dest.writeString(keywords);
            dest.writeInt(video_duration);
            dest.writeInt(has_mp4_video);
            dest.writeInt(favorite_count);
            dest.writeString(display_url);
            dest.writeInt(article_sub_type);
            dest.writeInt(bury_count);
            dest.writeString(title);
            dest.writeString(datetime);
            dest.writeByte((byte) (has_video ? 1 : 0));
            dest.writeString(share_url);
            dest.writeLong(id);
            dest.writeString(source);
            dest.writeInt(comment_count);
            dest.writeString(article_url);
            dest.writeInt(create_time);
            dest.writeInt(recommend);
            dest.writeString(image_url);
            dest.writeInt(tips);
            dest.writeInt(aggr_type);
            dest.writeString(item_source_url);
            dest.writeString(media_url);
            dest.writeInt(display_time);
            dest.writeInt(publish_time);
            dest.writeInt(go_detail_count);
            dest.writeInt(group_flags);
            dest.writeByte((byte) (middle_mode ? 1 : 0));
            dest.writeString(display_title);
            dest.writeInt(gallary_image_count);
            dest.writeString(item_seo_url);
            dest.writeLong(tag_id);
            dest.writeString(source_url);
            dest.writeInt(video_style);
            dest.writeString(article_genre);
            dest.writeByte((byte) (large_mode ? 1 : 0));
            dest.writeLong(item_id);
            dest.writeInt(natant_level);
            dest.writeInt(is_digg);
            dest.writeString(seo_url);
            dest.writeInt(repin_count);
            dest.writeString(url);
            dest.writeString(video_duration_str);
            dest.writeInt(level);
            dest.writeInt(digg_count);
            dest.writeInt(behot_time);
            dest.writeInt(hot);
            dest.writeInt(preload_web);
            dest.writeInt(comments_count);
            dest.writeInt(video_play_count);
            dest.writeByte((byte) (has_image ? 1 : 0));
            dest.writeInt(is_bury);
            dest.writeString(video_resulotion);
            dest.writeLong(group_id);
            dest.writeByte((byte) (honey ? 1 : 0));
        }

        @Override
        public int describeContents() {
            return 0;
        }

        public String getVideo_id() {
            return video_id;
        }

        public void setVideo_id(String video_id) {
            this.video_id = video_id;
        }

        public String getMedia_name() {
            return media_name;
        }

        public void setMedia_name(String media_name) {
            this.media_name = media_name;
        }

        public String getAbstractX() {
            return abstractX;
        }

        public void setAbstractX(String abstractX) {
            this.abstractX = abstractX;
        }

        public int getImpression_count() {
            return impression_count;
        }

        public void setImpression_count(int impression_count) {
            this.impression_count = impression_count;
        }

        public String getMedia_avatar_url() {
            return media_avatar_url;
        }

        public void setMedia_avatar_url(String media_avatar_url) {
            this.media_avatar_url = media_avatar_url;
        }

        public int getExternal_visit_count() {
            return external_visit_count;
        }

        public void setExternal_visit_count(int external_visit_count) {
            this.external_visit_count = external_visit_count;
        }

        public int getArticle_type() {
            return article_type;
        }

        public void setArticle_type(int article_type) {
            this.article_type = article_type;
        }

        public boolean isMore_mode() {
            return more_mode;
        }

        public void setMore_mode(boolean more_mode) {
            this.more_mode = more_mode;
        }

        public String getTag() {
            return tag;
        }

        public void setTag(String tag) {
            this.tag = tag;
        }

        public int getIs_favorite() {
            return is_favorite;
        }

        public void setIs_favorite(int is_favorite) {
            this.is_favorite = is_favorite;
        }

        public int getHas_m3u8_video() {
            return has_m3u8_video;
        }

        public void setHas_m3u8_video(int has_m3u8_video) {
            this.has_m3u8_video = has_m3u8_video;
        }

        public String getKeywords() {
            return keywords;
        }

        public void setKeywords(String keywords) {
            this.keywords = keywords;
        }

        public int getVideo_duration() {
            return video_duration;
        }

        public void setVideo_duration(int video_duration) {
            this.video_duration = video_duration;
        }

        public int getHas_mp4_video() {
            return has_mp4_video;
        }

        public void setHas_mp4_video(int has_mp4_video) {
            this.has_mp4_video = has_mp4_video;
        }

        public int getFavorite_count() {
            return favorite_count;
        }

        public void setFavorite_count(int favorite_count) {
            this.favorite_count = favorite_count;
        }

        public String getDisplay_url() {
            return display_url;
        }

        public void setDisplay_url(String display_url) {
            this.display_url = display_url;
        }

        public int getArticle_sub_type() {
            return article_sub_type;
        }

        public void setArticle_sub_type(int article_sub_type) {
            this.article_sub_type = article_sub_type;
        }

        public int getBury_count() {
            return bury_count;
        }

        public void setBury_count(int bury_count) {
            this.bury_count = bury_count;
        }

        public String getTitle() {
            return title;
        }

        public void setTitle(String title) {
            this.title = title;
        }

        public String getDatetime() {
            return datetime;
        }

        public void setDatetime(String datetime) {
            this.datetime = datetime;
        }

        public boolean isHas_video() {
            return has_video;
        }

        public void setHas_video(boolean has_video) {
            this.has_video = has_video;
        }

        public String getShare_url() {
            return share_url;
        }

        public void setShare_url(String share_url) {
            this.share_url = share_url;
        }

        public long getId() {
            return id;
        }

        public void setId(long id) {
            this.id = id;
        }

        public String getSource() {
            return source;
        }

        public void setSource(String source) {
            this.source = source;
        }

        public int getComment_count() {
            return comment_count;
        }

        public void setComment_count(int comment_count) {
            this.comment_count = comment_count;
        }

        public String getArticle_url() {
            return article_url;
        }

        public void setArticle_url(String article_url) {
            this.article_url = article_url;
        }

        public int getCreate_time() {
            return create_time;
        }

        public void setCreate_time(int create_time) {
            this.create_time = create_time;
        }

        public int getRecommend() {
            return recommend;
        }

        public void setRecommend(int recommend) {
            this.recommend = recommend;
        }

        public String getImage_url() {
            return image_url;
        }

        public void setImage_url(String image_url) {
            this.image_url = image_url;
        }

        public int getTips() {
            return tips;
        }

        public void setTips(int tips) {
            this.tips = tips;
        }

        public int getAggr_type() {
            return aggr_type;
        }

        public void setAggr_type(int aggr_type) {
            this.aggr_type = aggr_type;
        }

        public String getItem_source_url() {
            return item_source_url;
        }

        public void setItem_source_url(String item_source_url) {
            this.item_source_url = item_source_url;
        }

        public String getMedia_url() {
            return media_url;
        }

        public void setMedia_url(String media_url) {
            this.media_url = media_url;
        }

        public int getDisplay_time() {
            return display_time;
        }

        public void setDisplay_time(int display_time) {
            this.display_time = display_time;
        }

        public int getPublish_time() {
            return publish_time;
        }

        public void setPublish_time(int publish_time) {
            this.publish_time = publish_time;
        }

        public int getGo_detail_count() {
            return go_detail_count;
        }

        public void setGo_detail_count(int go_detail_count) {
            this.go_detail_count = go_detail_count;
        }

        public int getGroup_flags() {
            return group_flags;
        }

        public void setGroup_flags(int group_flags) {
            this.group_flags = group_flags;
        }

        public boolean isMiddle_mode() {
            return middle_mode;
        }

        public void setMiddle_mode(boolean middle_mode) {
            this.middle_mode = middle_mode;
        }

        public String getDisplay_title() {
            return display_title;
        }

        public void setDisplay_title(String display_title) {
            this.display_title = display_title;
        }

        public int getGallary_image_count() {
            return gallary_image_count;
        }

        public void setGallary_image_count(int gallary_image_count) {
            this.gallary_image_count = gallary_image_count;
        }

        public String getItem_seo_url() {
            return item_seo_url;
        }

        public void setItem_seo_url(String item_seo_url) {
            this.item_seo_url = item_seo_url;
        }

        public long getTag_id() {
            return tag_id;
        }

        public void setTag_id(long tag_id) {
            this.tag_id = tag_id;
        }

        public String getSource_url() {
            return source_url;
        }

        public void setSource_url(String source_url) {
            this.source_url = source_url;
        }

        public int getVideo_style() {
            return video_style;
        }

        public void setVideo_style(int video_style) {
            this.video_style = video_style;
        }

        public String getArticle_genre() {
            return article_genre;
        }

        public void setArticle_genre(String article_genre) {
            this.article_genre = article_genre;
        }

        public boolean isLarge_mode() {
            return large_mode;
        }

        public void setLarge_mode(boolean large_mode) {
            this.large_mode = large_mode;
        }

        public long getItem_id() {
            return item_id;
        }

        public void setItem_id(long item_id) {
            this.item_id = item_id;
        }

        public VideoDetailInfoBeanX getVideo_detail_info() {
            return video_detail_info;
        }

        public void setVideo_detail_info(VideoDetailInfoBeanX video_detail_info) {
            this.video_detail_info = video_detail_info;
        }

        public int getNatant_level() {
            return natant_level;
        }

        public void setNatant_level(int natant_level) {
            this.natant_level = natant_level;
        }

        public int getIs_digg() {
            return is_digg;
        }

        public void setIs_digg(int is_digg) {
            this.is_digg = is_digg;
        }

        public String getSeo_url() {
            return seo_url;
        }

        public void setSeo_url(String seo_url) {
            this.seo_url = seo_url;
        }

        public int getRepin_count() {
            return repin_count;
        }

        public void setRepin_count(int repin_count) {
            this.repin_count = repin_count;
        }

        public String getUrl() {
            return url;
        }

        public void setUrl(String url) {
            this.url = url;
        }

        public String getVideo_duration_str() {
            return video_duration_str;
        }

        public void setVideo_duration_str(String video_duration_str) {
            this.video_duration_str = video_duration_str;
        }

        public int getLevel() {
            return level;
        }

        public void setLevel(int level) {
            this.level = level;
        }

        public int getDigg_count() {
            return digg_count;
        }

        public void setDigg_count(int digg_count) {
            this.digg_count = digg_count;
        }

        public int getBehot_time() {
            return behot_time;
        }

        public void setBehot_time(int behot_time) {
            this.behot_time = behot_time;
        }

        public int getHot() {
            return hot;
        }

        public void setHot(int hot) {
            this.hot = hot;
        }

        public int getPreload_web() {
            return preload_web;
        }

        public void setPreload_web(int preload_web) {
            this.preload_web = preload_web;
        }

        public int getComments_count() {
            return comments_count;
        }

        public void setComments_count(int comments_count) {
            this.comments_count = comments_count;
        }

        public int getVideo_play_count() {
            return video_play_count;
        }

        public void setVideo_play_count(int video_play_count) {
            this.video_play_count = video_play_count;
        }

        public boolean isHas_image() {
            return has_image;
        }

        public void setHas_image(boolean has_image) {
            this.has_image = has_image;
        }

        public int getIs_bury() {
            return is_bury;
        }

        public void setIs_bury(int is_bury) {
            this.is_bury = is_bury;
        }

        public String getVideo_resulotion() {
            return video_resulotion;
        }

        public void setVideo_resulotion(String video_resulotion) {
            this.video_resulotion = video_resulotion;
        }

        public long getGroup_id() {
            return group_id;
        }

        public void setGroup_id(long group_id) {
            this.group_id = group_id;
        }

        public boolean isHoney() {
            return honey;
        }

        public void setHoney(boolean honey) {
            this.honey = honey;
        }

        public static class VideoDetailInfoBeanX {
            /**
             * video_detail_info : {"show_pgc_subscribe":1,"detail_video_large_image":{"url":"http://p9.pstatp.com/video1609/16800003254264979704","width":580,"url_list":[{"url":"http://p9.pstatp.com/video1609/16800003254264979704"},{"url":"http://pb3.pstatp.com/video1609/16800003254264979704"},{"url":"http://pb3.pstatp.com/video1609/16800003254264979704"}],"uri":"video1609/16800003254264979704","height":327},"video_id":"d23d96e2e7a84494853546cd0bf12cef","group_flags":32832,"direct_play":1}
             * video_type : 0
             * direct_play : 1
             */

            private VideoDetailInfoBean video_detail_info;
            private int video_type;
            private int direct_play;

            public VideoDetailInfoBean getVideo_detail_info() {
                return video_detail_info;
            }

            public void setVideo_detail_info(VideoDetailInfoBean video_detail_info) {
                this.video_detail_info = video_detail_info;
            }

            public int getVideo_type() {
                return video_type;
            }

            public void setVideo_type(int video_type) {
                this.video_type = video_type;
            }

            public int getDirect_play() {
                return direct_play;
            }

            public void setDirect_play(int direct_play) {
                this.direct_play = direct_play;
            }

            public static class VideoDetailInfoBean {
                /**
                 * show_pgc_subscribe : 1
                 * detail_video_large_image : {"url":"http://p9.pstatp.com/video1609/16800003254264979704","width":580,"url_list":[{"url":"http://p9.pstatp.com/video1609/16800003254264979704"},{"url":"http://pb3.pstatp.com/video1609/16800003254264979704"},{"url":"http://pb3.pstatp.com/video1609/16800003254264979704"}],"uri":"video1609/16800003254264979704","height":327}
                 * video_id : d23d96e2e7a84494853546cd0bf12cef
                 * group_flags : 32832
                 * direct_play : 1
                 */

                private int show_pgc_subscribe;
                private DetailVideoLargeImageBean detail_video_large_image;
                private String video_id;
                private int group_flags;
                private int direct_play;

                public int getShow_pgc_subscribe() {
                    return show_pgc_subscribe;
                }

                public void setShow_pgc_subscribe(int show_pgc_subscribe) {
                    this.show_pgc_subscribe = show_pgc_subscribe;
                }

                public DetailVideoLargeImageBean getDetail_video_large_image() {
                    return detail_video_large_image;
                }

                public void setDetail_video_large_image(DetailVideoLargeImageBean detail_video_large_image) {
                    this.detail_video_large_image = detail_video_large_image;
                }

                public String getVideo_id() {
                    return video_id;
                }

                public void setVideo_id(String video_id) {
                    this.video_id = video_id;
                }

                public int getGroup_flags() {
                    return group_flags;
                }

                public void setGroup_flags(int group_flags) {
                    this.group_flags = group_flags;
                }

                public int getDirect_play() {
                    return direct_play;
                }

                public void setDirect_play(int direct_play) {
                    this.direct_play = direct_play;
                }

                public static class DetailVideoLargeImageBean {
                    /**
                     * url : http://p9.pstatp.com/video1609/16800003254264979704
                     * width : 580
                     * url_list : [{"url":"http://p9.pstatp.com/video1609/16800003254264979704"},{"url":"http://pb3.pstatp.com/video1609/16800003254264979704"},{"url":"http://pb3.pstatp.com/video1609/16800003254264979704"}]
                     * uri : video1609/16800003254264979704
                     * height : 327
                     */

                    private String url;
                    private int width;
                    private String uri;
                    private int height;
                    private List<UrlListBean> url_list;

                    public String getUrl() {
                        return url;
                    }

                    public void setUrl(String url) {
                        this.url = url;
                    }

                    public int getWidth() {
                        return width;
                    }

                    public void setWidth(int width) {
                        this.width = width;
                    }

                    public String getUri() {
                        return uri;
                    }

                    public void setUri(String uri) {
                        this.uri = uri;
                    }

                    public int getHeight() {
                        return height;
                    }

                    public void setHeight(int height) {
                        this.height = height;
                    }

                    public List<UrlListBean> getUrl_list() {
                        return url_list;
                    }

                    public void setUrl_list(List<UrlListBean> url_list) {
                        this.url_list = url_list;
                    }

                    public static class UrlListBean {
                        /**
                         * url : http://p9.pstatp.com/video1609/16800003254264979704
                         */

                        private String url;

                        public String getUrl() {
                            return url;
                        }

                        public void setUrl(String url) {
                            this.url = url;
                        }
                    }
                }
            }
        }
    }
}
