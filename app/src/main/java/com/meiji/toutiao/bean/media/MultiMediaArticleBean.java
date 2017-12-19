package com.meiji.toutiao.bean.media;

import com.google.gson.annotations.SerializedName;

import java.util.List;

/**
 * Created by Meiji on 2017/7/1.
 */

public class MultiMediaArticleBean {

    /**
     * media_id : 1562831380917250
     * has_more : 1
     * next : {"max_behot_time":1496726727}
     * page_type : 1
     * message : success
     */

    private long media_id;
    private String has_more;
    private NextBean next;
    private int page_type;
    private String message;
    private List<DataBean> data;

    public long getMedia_id() {
        return media_id;
    }

    public void setMedia_id(long media_id) {
        this.media_id = media_id;
    }

    public String getHas_more() {
        return has_more;
    }

    public void setHas_more(String has_more) {
        this.has_more = has_more;
    }

    public NextBean getNext() {
        return next;
    }

    public void setNext(NextBean next) {
        this.next = next;
    }

    public int getPage_type() {
        return page_type;
    }

    public void setPage_type(int page_type) {
        this.page_type = page_type;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public List<DataBean> getData() {
        return data;
    }

    public void setData(List<DataBean> data) {
        this.data = data;
    }

    public static class NextBean {
        /**
         * max_behot_time : 1496726727
         */

        private String max_behot_time;

        public String getMax_behot_time() {
            return max_behot_time;
        }

        public void setMax_behot_time(String max_behot_time) {
            this.max_behot_time = max_behot_time;
        }
    }

    public static class DataBean {
        /**
         * item_status : 20
         * datetime : 2017-06-20 19:31
         * video_infos : []
         * has_m3u8_video : 0
         * keywords : 淘宝客,阿里妈妈
         * article_live_type : none
         * has_mp4_video : 0
         * title : 淘宝客每个月扣除的10%真的合理吗？
         * source : 神勇依旧
         * natant_level : 0
         * own_group : false
         * share_count : 0
         * list_play_effective_count : 0
         * verify_detail : {"pass_time":"2017-06-20 19:32:29","auto":{"status":10},"editor":{"status":20}}
         * categories : ["news_tech/other","news_tech"]
         * repin_count : 4
         * display_status : 0
         * level : 0
         * digg_count : 0
         * comments_count : 1
         * cover_image_infos : [{"mimetype":"image/jpeg","image_type":1,"height":183,"width":1709,"web_uri":"289800014415a3581e72","desc":""},{"mimetype":"image/jpeg","image_type":1,"height":188,"width":1711,"web_uri":"289500042fec5ebb4c19","desc":""},{"mimetype":"image/jpeg","image_type":1,"height":576,"width":697,"web_uri":"2892000245cb9654fa20","desc":""}]
         * max_comments : 0
         * middle_image : http://p2.pstatp.com/list/2892000245cb9654fa20
         * pgc_id : 6433681916943140000
         * ad_type : 2
         * create_time : 1497958309
         * book_info :
         * article_sub_type : 0
         * label : ["科技"]
         * pgc_ad : 0
         * group_source : 2
         * image_url : http://p2.pstatp.com/list/2892000245cb9654fa20
         * is_key_item : 1
         * str_group_id : 6433679784035221761
         * source_url : http://is.snssdk.com/item/6433681916943139329/
         * item_id : 6433681916943140000
         * good_voice : false
         * group_id : 6433679784035222000
         * language : zh
         * display_url : http://toutiao.com/group/6433679784035221761/
         * region : 156
         * content_cards :
         * has_gallery : false
         * modify_time : 1497958355
         * content_cntw : 250
         * external_visit_count_format : 327
         * detail_mode : 0
         * impression_count : 4015
         * image_list : [{"url":"http://p2.pstatp.com/list/2892000245cb9654fa20","pc_url":"http://p2.pstatp.com/list/194x108/2892000245cb9654fa20"}]
         * str_item_id : 6433681916943139329
         * group_status : 1
         * creator_uid : 6619635172
         * original_media_id : 0
         * city :
         * bury_count : 0
         * web_article_type : 1
         * review_comment : false
         * comment_count : 1
         * internal_visit_count : 208
         * media_id : 1562831380917250
         * go_detail_count : 535
         * group_flags : 0
         * total_read_count : 535
         * detail_play_effective_count : 0
         * visibility : 3
         * pc_image_url : http://p2.pstatp.com/list/126x82/2892000245cb9654fa20
         * was_recommended : 1
         * thumb_image : [{"url":"http://p9.pstatp.com/thumb/289800014415a3581e72","width":1709,"url_list":[{"url":"http://p9.pstatp.com/thumb/289800014415a3581e72"},{"url":"http://pb1.pstatp.com/thumb/289800014415a3581e72"},{"url":"http://pb3.pstatp.com/thumb/289800014415a3581e72"}],"uri":"thumb/289800014415a3581e72","height":183},{"url":"http://p3.pstatp.com/thumb/289500042fec5ebb4c19","width":1711,"url_list":[{"url":"http://p3.pstatp.com/thumb/289500042fec5ebb4c19"},{"url":"http://pb9.pstatp.com/thumb/289500042fec5ebb4c19"},{"url":"http://pb1.pstatp.com/thumb/289500042fec5ebb4c19"}],"uri":"thumb/289500042fec5ebb4c19","height":188},{"url":"http://p1.pstatp.com/thumb/2892000245cb9654fa20","width":697,"url_list":[{"url":"http://p1.pstatp.com/thumb/2892000245cb9654fa20"},{"url":"http://pb3.pstatp.com/thumb/2892000245cb9654fa20"},{"url":"http://pb9.pstatp.com/thumb/2892000245cb9654fa20"}],"uri":"thumb/2892000245cb9654fa20","height":576}]
         * seo_url : http://is.snssdk.com/item/6433681916943139329/
         * external_visit_count : 327
         * image_detail : [{"url":"http://p9.pstatp.com/large/289800014415a3581e72","width":1709,"url_list":[{"url":"http://p9.pstatp.com/large/289800014415a3581e72"},{"url":"http://pb1.pstatp.com/large/289800014415a3581e72"},{"url":"http://pb3.pstatp.com/large/289800014415a3581e72"}],"uri":"large/289800014415a3581e72","height":183},{"url":"http://p3.pstatp.com/large/289500042fec5ebb4c19","width":1711,"url_list":[{"url":"http://p3.pstatp.com/large/289500042fec5ebb4c19"},{"url":"http://pb9.pstatp.com/large/289500042fec5ebb4c19"},{"url":"http://pb1.pstatp.com/large/289500042fec5ebb4c19"}],"uri":"large/289500042fec5ebb4c19","height":188},{"url":"http://p1.pstatp.com/large/2892000245cb9654fa20","width":697,"url_list":[{"url":"http://p1.pstatp.com/large/2892000245cb9654fa20"},{"url":"http://pb3.pstatp.com/large/2892000245cb9654fa20"},{"url":"http://pb9.pstatp.com/large/2892000245cb9654fa20"}],"uri":"large/2892000245cb9654fa20","height":576}]
         * ban_action : false
         * review_comment_mode : 0
         * has_inner_video : false
         * has_image : true
         * play_effective_count : 0
         * ban_comment : 0
         * abstract : 又到了阿里妈妈发工资的时间了，几家欢喜几家愁，对于一些大牛和老淘客来说，今天和平时没啥区别，就是账户多了很多数字。我有一个朋友初入淘客，今天同样的也去领薪水了，不过结果她很郁闷，为啥少了。于是乎我让她截图给我，于是我举例给她了。---- 我跟她说你，不要忘记看这个了。
         * middle_mode : true
         * is_original : false
         * ban_bury : false
         * article_type : 0
         * tag : news_tech
         * image_infos : [{"mimetype":"image/png","image_type":1,"height":183,"width":1709,"web_uri":"289800014415a3581e72","desc":""},{"mimetype":"image/png","image_type":1,"height":188,"width":1711,"web_uri":"289500042fec5ebb4c19","desc":""},{"mimetype":"image/png","image_type":1,"height":576,"width":697,"web_uri":"2892000245cb9654fa20","desc":""}]
         * optional_data : {"golden_second_awards":"0","pgc_source":"0"}
         * app_url : sslocal://detail?item_id=6433681916943139329&groupid=6433679784035221761&gd_label=click_pgc&aggr_type=2
         * internal_visit_count_format : 208
         * has_video : false
         * article_url : http://toutiao.com/group/6433679784035221761/
         * display_mode :
         * composition : 8
         * publish_time : 1497958302
         * wap_open : 0
         * tag_id : 6433679784035222000
         * pgc_article_type : 0
         * display_type : 0
         * gallery : []
         * detail_source : 神勇依旧
         * url : http://toutiao.com/group/6433679784035221761/
         * web_display_type : 4
         * behot_time : 1497958302
         * more_mode : true
         * show_play_effective_count : true
         * video_duration_str : 03:49
         * flags : 64
         */

        private int item_status;
        private String datetime;
        private int has_m3u8_video;
        private String keywords;
        private String article_live_type;
        private int has_mp4_video;
        private String title;
        private String source;
        private int natant_level;
        private boolean own_group;
        private int share_count;
        private int list_play_effective_count;
        private VerifyDetailBean verify_detail;
        private int repin_count;
        private int display_status;
        private int level;
        private int digg_count;
        private String comments_count;
        private int max_comments;
        private String middle_image;
        private long pgc_id;
        private int ad_type;
        private int create_time;
        private String book_info;
        private int article_sub_type;
        private int pgc_ad;
        private int group_source;
        private String image_url;
        private int is_key_item;
        private String str_group_id;
        private String source_url;
        private long item_id;
        private boolean good_voice;
        private long group_id;
        private String language;
        private String display_url;
        private int region;
        private String content_cards;
        private boolean has_gallery;
        private int modify_time;
        private int content_cntw;
        private String external_visit_count_format;
        private int detail_mode;
        private int impression_count;
        private String str_item_id;
        private int group_status;
        private long creator_uid;
        private int original_media_id;
        private String city;
        private int bury_count;
        private int web_article_type;
        private boolean review_comment;
        private int comment_count;
        private int internal_visit_count;
        private long media_id;
        private String go_detail_count;
        private int group_flags;
        private int total_read_count;
        private int detail_play_effective_count;
        private int visibility;
        private String pc_image_url;
        private int was_recommended;
        private String seo_url;
        private int external_visit_count;
        private boolean ban_action;
        private int review_comment_mode;
        private boolean has_inner_video;
        private boolean has_image;
        private String play_effective_count;
        private int ban_comment;
        @SerializedName("abstract")
        private String abstractX;
        private boolean middle_mode;
        private boolean is_original;
        private boolean ban_bury;
        private int article_type;
        private String tag;
        private OptionalDataBean optional_data;
        private String app_url;
        private String internal_visit_count_format;
        private boolean has_video;
        private String article_url;
        private String display_mode;
        private int composition;
        private int publish_time;
        private int wap_open;
        private long tag_id;
        private int pgc_article_type;
        private int display_type;
        private String detail_source;
        private String url;
        private int web_display_type;
        private int behot_time;
        private boolean more_mode;
        private boolean show_play_effective_count;
        private String video_duration_str;
        private int flags;
        private List<VideoInfosBean> video_infos;
        private List<String> categories;
        private List<CoverImageInfosBean> cover_image_infos;
        private List<String> label;
        private List<ImageListBean> image_list;
        private List<ThumbImageBean> thumb_image;
        private List<ImageDetailBean> image_detail;
        private List<ImageInfosBean> image_infos;
        private List<?> gallery;

        @Override
        public boolean equals(Object o) {
            if (this == o)
                return true;
            if (o == null || getClass() != o.getClass())
                return false;

            DataBean dataBean = (DataBean) o;

            if (title != null ? !title.equals(dataBean.title) : dataBean.title != null)
                return false;
            return abstractX != null ? !abstractX.equals(dataBean.abstractX) : dataBean.abstractX != null;
        }

        @Override
        public int hashCode() {
            int result = item_status;
            result = 31 * result + (title != null ? title.hashCode() : 0);
            result = 31 * result + (abstractX != null ? abstractX.hashCode() : 0);
            return result;
        }

        public int getItem_status() {
            return item_status;
        }

        public void setItem_status(int item_status) {
            this.item_status = item_status;
        }

        public String getDatetime() {
            return datetime;
        }

        public void setDatetime(String datetime) {
            this.datetime = datetime;
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

        public String getArticle_live_type() {
            return article_live_type;
        }

        public void setArticle_live_type(String article_live_type) {
            this.article_live_type = article_live_type;
        }

        public int getHas_mp4_video() {
            return has_mp4_video;
        }

        public void setHas_mp4_video(int has_mp4_video) {
            this.has_mp4_video = has_mp4_video;
        }

        public String getTitle() {
            return title;
        }

        public void setTitle(String title) {
            this.title = title;
        }

        public String getSource() {
            return source;
        }

        public void setSource(String source) {
            this.source = source;
        }

        public int getNatant_level() {
            return natant_level;
        }

        public void setNatant_level(int natant_level) {
            this.natant_level = natant_level;
        }

        public boolean isOwn_group() {
            return own_group;
        }

        public void setOwn_group(boolean own_group) {
            this.own_group = own_group;
        }

        public int getShare_count() {
            return share_count;
        }

        public void setShare_count(int share_count) {
            this.share_count = share_count;
        }

        public int getList_play_effective_count() {
            return list_play_effective_count;
        }

        public void setList_play_effective_count(int list_play_effective_count) {
            this.list_play_effective_count = list_play_effective_count;
        }

        public VerifyDetailBean getVerify_detail() {
            return verify_detail;
        }

        public void setVerify_detail(VerifyDetailBean verify_detail) {
            this.verify_detail = verify_detail;
        }

        public int getRepin_count() {
            return repin_count;
        }

        public void setRepin_count(int repin_count) {
            this.repin_count = repin_count;
        }

        public int getDisplay_status() {
            return display_status;
        }

        public void setDisplay_status(int display_status) {
            this.display_status = display_status;
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

        public String getComments_count() {
            return comments_count;
        }

        public void setComments_count(String comments_count) {
            this.comments_count = comments_count;
        }

        public int getMax_comments() {
            return max_comments;
        }

        public void setMax_comments(int max_comments) {
            this.max_comments = max_comments;
        }

        public String getMiddle_image() {
            return middle_image;
        }

        public void setMiddle_image(String middle_image) {
            this.middle_image = middle_image;
        }

        public long getPgc_id() {
            return pgc_id;
        }

        public void setPgc_id(long pgc_id) {
            this.pgc_id = pgc_id;
        }

        public int getAd_type() {
            return ad_type;
        }

        public void setAd_type(int ad_type) {
            this.ad_type = ad_type;
        }

        public int getCreate_time() {
            return create_time;
        }

        public void setCreate_time(int create_time) {
            this.create_time = create_time;
        }

        public String getBook_info() {
            return book_info;
        }

        public void setBook_info(String book_info) {
            this.book_info = book_info;
        }

        public int getArticle_sub_type() {
            return article_sub_type;
        }

        public void setArticle_sub_type(int article_sub_type) {
            this.article_sub_type = article_sub_type;
        }

        public int getPgc_ad() {
            return pgc_ad;
        }

        public void setPgc_ad(int pgc_ad) {
            this.pgc_ad = pgc_ad;
        }

        public int getGroup_source() {
            return group_source;
        }

        public void setGroup_source(int group_source) {
            this.group_source = group_source;
        }

        public String getImage_url() {
            return image_url;
        }

        public void setImage_url(String image_url) {
            this.image_url = image_url;
        }

        public int getIs_key_item() {
            return is_key_item;
        }

        public void setIs_key_item(int is_key_item) {
            this.is_key_item = is_key_item;
        }

        public String getStr_group_id() {
            return str_group_id;
        }

        public void setStr_group_id(String str_group_id) {
            this.str_group_id = str_group_id;
        }

        public String getSource_url() {
            return source_url;
        }

        public void setSource_url(String source_url) {
            this.source_url = source_url;
        }

        public long getItem_id() {
            return item_id;
        }

        public void setItem_id(long item_id) {
            this.item_id = item_id;
        }

        public boolean isGood_voice() {
            return good_voice;
        }

        public void setGood_voice(boolean good_voice) {
            this.good_voice = good_voice;
        }

        public long getGroup_id() {
            return group_id;
        }

        public void setGroup_id(long group_id) {
            this.group_id = group_id;
        }

        public String getLanguage() {
            return language;
        }

        public void setLanguage(String language) {
            this.language = language;
        }

        public String getDisplay_url() {
            return display_url;
        }

        public void setDisplay_url(String display_url) {
            this.display_url = display_url;
        }

        public int getRegion() {
            return region;
        }

        public void setRegion(int region) {
            this.region = region;
        }

        public String getContent_cards() {
            return content_cards;
        }

        public void setContent_cards(String content_cards) {
            this.content_cards = content_cards;
        }

        public boolean isHas_gallery() {
            return has_gallery;
        }

        public void setHas_gallery(boolean has_gallery) {
            this.has_gallery = has_gallery;
        }

        public int getModify_time() {
            return modify_time;
        }

        public void setModify_time(int modify_time) {
            this.modify_time = modify_time;
        }

        public int getContent_cntw() {
            return content_cntw;
        }

        public void setContent_cntw(int content_cntw) {
            this.content_cntw = content_cntw;
        }

        public String getExternal_visit_count_format() {
            return external_visit_count_format;
        }

        public void setExternal_visit_count_format(String external_visit_count_format) {
            this.external_visit_count_format = external_visit_count_format;
        }

        public int getDetail_mode() {
            return detail_mode;
        }

        public void setDetail_mode(int detail_mode) {
            this.detail_mode = detail_mode;
        }

        public int getImpression_count() {
            return impression_count;
        }

        public void setImpression_count(int impression_count) {
            this.impression_count = impression_count;
        }

        public String getStr_item_id() {
            return str_item_id;
        }

        public void setStr_item_id(String str_item_id) {
            this.str_item_id = str_item_id;
        }

        public int getGroup_status() {
            return group_status;
        }

        public void setGroup_status(int group_status) {
            this.group_status = group_status;
        }

        public long getCreator_uid() {
            return creator_uid;
        }

        public void setCreator_uid(long creator_uid) {
            this.creator_uid = creator_uid;
        }

        public int getOriginal_media_id() {
            return original_media_id;
        }

        public void setOriginal_media_id(int original_media_id) {
            this.original_media_id = original_media_id;
        }

        public String getCity() {
            return city;
        }

        public void setCity(String city) {
            this.city = city;
        }

        public int getBury_count() {
            return bury_count;
        }

        public void setBury_count(int bury_count) {
            this.bury_count = bury_count;
        }

        public int getWeb_article_type() {
            return web_article_type;
        }

        public void setWeb_article_type(int web_article_type) {
            this.web_article_type = web_article_type;
        }

        public boolean isReview_comment() {
            return review_comment;
        }

        public void setReview_comment(boolean review_comment) {
            this.review_comment = review_comment;
        }

        public int getComment_count() {
            return comment_count;
        }

        public void setComment_count(int comment_count) {
            this.comment_count = comment_count;
        }

        public int getInternal_visit_count() {
            return internal_visit_count;
        }

        public void setInternal_visit_count(int internal_visit_count) {
            this.internal_visit_count = internal_visit_count;
        }

        public long getMedia_id() {
            return media_id;
        }

        public void setMedia_id(long media_id) {
            this.media_id = media_id;
        }

        public String getGo_detail_count() {
            return go_detail_count;
        }

        public void setGo_detail_count(String go_detail_count) {
            this.go_detail_count = go_detail_count;
        }

        public int getGroup_flags() {
            return group_flags;
        }

        public void setGroup_flags(int group_flags) {
            this.group_flags = group_flags;
        }

        public int getTotal_read_count() {
            return total_read_count;
        }

        public void setTotal_read_count(int total_read_count) {
            this.total_read_count = total_read_count;
        }

        public int getDetail_play_effective_count() {
            return detail_play_effective_count;
        }

        public void setDetail_play_effective_count(int detail_play_effective_count) {
            this.detail_play_effective_count = detail_play_effective_count;
        }

        public int getVisibility() {
            return visibility;
        }

        public void setVisibility(int visibility) {
            this.visibility = visibility;
        }

        public String getPc_image_url() {
            return pc_image_url;
        }

        public void setPc_image_url(String pc_image_url) {
            this.pc_image_url = pc_image_url;
        }

        public int getWas_recommended() {
            return was_recommended;
        }

        public void setWas_recommended(int was_recommended) {
            this.was_recommended = was_recommended;
        }

        public String getSeo_url() {
            return seo_url;
        }

        public void setSeo_url(String seo_url) {
            this.seo_url = seo_url;
        }

        public int getExternal_visit_count() {
            return external_visit_count;
        }

        public void setExternal_visit_count(int external_visit_count) {
            this.external_visit_count = external_visit_count;
        }

        public boolean isBan_action() {
            return ban_action;
        }

        public void setBan_action(boolean ban_action) {
            this.ban_action = ban_action;
        }

        public int getReview_comment_mode() {
            return review_comment_mode;
        }

        public void setReview_comment_mode(int review_comment_mode) {
            this.review_comment_mode = review_comment_mode;
        }

        public boolean isHas_inner_video() {
            return has_inner_video;
        }

        public void setHas_inner_video(boolean has_inner_video) {
            this.has_inner_video = has_inner_video;
        }

        public boolean isHas_image() {
            return has_image;
        }

        public void setHas_image(boolean has_image) {
            this.has_image = has_image;
        }

        public String getPlay_effective_count() {
            return play_effective_count;
        }

        public void setPlay_effective_count(String play_effective_count) {
            this.play_effective_count = play_effective_count;
        }

        public int getBan_comment() {
            return ban_comment;
        }

        public void setBan_comment(int ban_comment) {
            this.ban_comment = ban_comment;
        }

        public String getAbstractX() {
            return abstractX;
        }

        public void setAbstractX(String abstractX) {
            this.abstractX = abstractX;
        }

        public boolean isMiddle_mode() {
            return middle_mode;
        }

        public void setMiddle_mode(boolean middle_mode) {
            this.middle_mode = middle_mode;
        }

        public boolean isIs_original() {
            return is_original;
        }

        public void setIs_original(boolean is_original) {
            this.is_original = is_original;
        }

        public boolean isBan_bury() {
            return ban_bury;
        }

        public void setBan_bury(boolean ban_bury) {
            this.ban_bury = ban_bury;
        }

        public int getArticle_type() {
            return article_type;
        }

        public void setArticle_type(int article_type) {
            this.article_type = article_type;
        }

        public String getTag() {
            return tag;
        }

        public void setTag(String tag) {
            this.tag = tag;
        }

        public OptionalDataBean getOptional_data() {
            return optional_data;
        }

        public void setOptional_data(OptionalDataBean optional_data) {
            this.optional_data = optional_data;
        }

        public String getApp_url() {
            return app_url;
        }

        public void setApp_url(String app_url) {
            this.app_url = app_url;
        }

        public String getInternal_visit_count_format() {
            return internal_visit_count_format;
        }

        public void setInternal_visit_count_format(String internal_visit_count_format) {
            this.internal_visit_count_format = internal_visit_count_format;
        }

        public boolean isHas_video() {
            return has_video;
        }

        public void setHas_video(boolean has_video) {
            this.has_video = has_video;
        }

        public String getArticle_url() {
            return article_url;
        }

        public void setArticle_url(String article_url) {
            this.article_url = article_url;
        }

        public String getDisplay_mode() {
            return display_mode;
        }

        public void setDisplay_mode(String display_mode) {
            this.display_mode = display_mode;
        }

        public int getComposition() {
            return composition;
        }

        public void setComposition(int composition) {
            this.composition = composition;
        }

        public int getPublish_time() {
            return publish_time;
        }

        public void setPublish_time(int publish_time) {
            this.publish_time = publish_time;
        }

        public int getWap_open() {
            return wap_open;
        }

        public void setWap_open(int wap_open) {
            this.wap_open = wap_open;
        }

        public long getTag_id() {
            return tag_id;
        }

        public void setTag_id(long tag_id) {
            this.tag_id = tag_id;
        }

        public int getPgc_article_type() {
            return pgc_article_type;
        }

        public void setPgc_article_type(int pgc_article_type) {
            this.pgc_article_type = pgc_article_type;
        }

        public int getDisplay_type() {
            return display_type;
        }

        public void setDisplay_type(int display_type) {
            this.display_type = display_type;
        }

        public String getDetail_source() {
            return detail_source;
        }

        public void setDetail_source(String detail_source) {
            this.detail_source = detail_source;
        }

        public String getUrl() {
            return url;
        }

        public void setUrl(String url) {
            this.url = url;
        }

        public int getWeb_display_type() {
            return web_display_type;
        }

        public void setWeb_display_type(int web_display_type) {
            this.web_display_type = web_display_type;
        }

        public int getBehot_time() {
            return behot_time;
        }

        public void setBehot_time(int behot_time) {
            this.behot_time = behot_time;
        }

        public boolean isMore_mode() {
            return more_mode;
        }

        public void setMore_mode(boolean more_mode) {
            this.more_mode = more_mode;
        }

        public boolean isShow_play_effective_count() {
            return show_play_effective_count;
        }

        public void setShow_play_effective_count(boolean show_play_effective_count) {
            this.show_play_effective_count = show_play_effective_count;
        }

        public String getVideo_duration_str() {
            return video_duration_str;
        }

        public void setVideo_duration_str(String video_duration_str) {
            this.video_duration_str = video_duration_str;
        }

        public int getFlags() {
            return flags;
        }

        public void setFlags(int flags) {
            this.flags = flags;
        }

        public List<VideoInfosBean> getVideo_infos() {
            return video_infos;
        }

        public void setVideo_infos(List<VideoInfosBean> video_infos) {
            this.video_infos = video_infos;
        }

        public List<String> getCategories() {
            return categories;
        }

        public void setCategories(List<String> categories) {
            this.categories = categories;
        }

        public List<CoverImageInfosBean> getCover_image_infos() {
            return cover_image_infos;
        }

        public void setCover_image_infos(List<CoverImageInfosBean> cover_image_infos) {
            this.cover_image_infos = cover_image_infos;
        }

        public List<String> getLabel() {
            return label;
        }

        public void setLabel(List<String> label) {
            this.label = label;
        }

        public List<ImageListBean> getImage_list() {
            return image_list;
        }

        public void setImage_list(List<ImageListBean> image_list) {
            this.image_list = image_list;
        }

        public List<ThumbImageBean> getThumb_image() {
            return thumb_image;
        }

        public void setThumb_image(List<ThumbImageBean> thumb_image) {
            this.thumb_image = thumb_image;
        }

        public List<ImageDetailBean> getImage_detail() {
            return image_detail;
        }

        public void setImage_detail(List<ImageDetailBean> image_detail) {
            this.image_detail = image_detail;
        }

        public List<ImageInfosBean> getImage_infos() {
            return image_infos;
        }

        public void setImage_infos(List<ImageInfosBean> image_infos) {
            this.image_infos = image_infos;
        }

        public List<?> getGallery() {
            return gallery;
        }

        public void setGallery(List<?> gallery) {
            this.gallery = gallery;
        }

        public static class VerifyDetailBean {
            /**
             * pass_time : 2017-06-20 19:32:29
             * auto : {"status":10}
             * editor : {"status":20}
             */

            private String pass_time;
            private AutoBean auto;
            private EditorBean editor;

            public String getPass_time() {
                return pass_time;
            }

            public void setPass_time(String pass_time) {
                this.pass_time = pass_time;
            }

            public AutoBean getAuto() {
                return auto;
            }

            public void setAuto(AutoBean auto) {
                this.auto = auto;
            }

            public EditorBean getEditor() {
                return editor;
            }

            public void setEditor(EditorBean editor) {
                this.editor = editor;
            }

            public static class AutoBean {
                /**
                 * status : 10
                 */

                private int status;

                public int getStatus() {
                    return status;
                }

                public void setStatus(int status) {
                    this.status = status;
                }
            }

            public static class EditorBean {
                /**
                 * status : 20
                 */

                private int status;

                public int getStatus() {
                    return status;
                }

                public void setStatus(int status) {
                    this.status = status;
                }
            }
        }

        public static class OptionalDataBean {
            /**
             * golden_second_awards : 0
             * pgc_source : 0
             */

            private String golden_second_awards;
            private String pgc_source;

            public String getGolden_second_awards() {
                return golden_second_awards;
            }

            public void setGolden_second_awards(String golden_second_awards) {
                this.golden_second_awards = golden_second_awards;
            }

            public String getPgc_source() {
                return pgc_source;
            }

            public void setPgc_source(String pgc_source) {
                this.pgc_source = pgc_source;
            }
        }

        public static class VideoInfosBean {
            /**
             * thumb_height : 626
             * sp : toutiao
             * vid : 4becfef9e95d4152beab7f360270dacb
             * thumb_width : 360
             * video_partner : 0
             * duration : 229
             * thumb_url : 28500007053c906d74f7
             * video_size : {"high":{"h":640,"subjective_score":0,"w":368,"file_size":18369097},"normal":{"h":626,"subjective_score":0,"w":360,"file_size":16674479}}
             * vu : 4becfef9e95d4152beab7f360270dacb
             */

            private int thumb_height;
            private String sp;
            private String vid;
            private int thumb_width;
            private int video_partner;
            private int duration;
            private String thumb_url;
            private VideoSizeBean video_size;
            private String vu;

            public int getThumb_height() {
                return thumb_height;
            }

            public void setThumb_height(int thumb_height) {
                this.thumb_height = thumb_height;
            }

            public String getSp() {
                return sp;
            }

            public void setSp(String sp) {
                this.sp = sp;
            }

            public String getVid() {
                return vid;
            }

            public void setVid(String vid) {
                this.vid = vid;
            }

            public int getThumb_width() {
                return thumb_width;
            }

            public void setThumb_width(int thumb_width) {
                this.thumb_width = thumb_width;
            }

            public int getVideo_partner() {
                return video_partner;
            }

            public void setVideo_partner(int video_partner) {
                this.video_partner = video_partner;
            }

            public int getDuration() {
                return duration;
            }

            public void setDuration(int duration) {
                this.duration = duration;
            }

            public String getThumb_url() {
                return thumb_url;
            }

            public void setThumb_url(String thumb_url) {
                this.thumb_url = thumb_url;
            }

            public VideoSizeBean getVideo_size() {
                return video_size;
            }

            public void setVideo_size(VideoSizeBean video_size) {
                this.video_size = video_size;
            }

            public String getVu() {
                return vu;
            }

            public void setVu(String vu) {
                this.vu = vu;
            }

            public static class VideoSizeBean {
                /**
                 * high : {"h":640,"subjective_score":0,"w":368,"file_size":18369097}
                 * normal : {"h":626,"subjective_score":0,"w":360,"file_size":16674479}
                 */

                private HighBean high;
                private NormalBean normal;

                public HighBean getHigh() {
                    return high;
                }

                public void setHigh(HighBean high) {
                    this.high = high;
                }

                public NormalBean getNormal() {
                    return normal;
                }

                public void setNormal(NormalBean normal) {
                    this.normal = normal;
                }

                public static class HighBean {
                    /**
                     * h : 640
                     * subjective_score : 0
                     * w : 368
                     * file_size : 18369097
                     */

                    private int h;
                    private int subjective_score;
                    private int w;
                    private int file_size;

                    public int getH() {
                        return h;
                    }

                    public void setH(int h) {
                        this.h = h;
                    }

                    public int getSubjective_score() {
                        return subjective_score;
                    }

                    public void setSubjective_score(int subjective_score) {
                        this.subjective_score = subjective_score;
                    }

                    public int getW() {
                        return w;
                    }

                    public void setW(int w) {
                        this.w = w;
                    }

                    public int getFile_size() {
                        return file_size;
                    }

                    public void setFile_size(int file_size) {
                        this.file_size = file_size;
                    }
                }

                public static class NormalBean {
                    /**
                     * h : 626
                     * subjective_score : 0
                     * w : 360
                     * file_size : 16674479
                     */

                    private int h;
                    private int subjective_score;
                    private int w;
                    private int file_size;

                    public int getH() {
                        return h;
                    }

                    public void setH(int h) {
                        this.h = h;
                    }

                    public int getSubjective_score() {
                        return subjective_score;
                    }

                    public void setSubjective_score(int subjective_score) {
                        this.subjective_score = subjective_score;
                    }

                    public int getW() {
                        return w;
                    }

                    public void setW(int w) {
                        this.w = w;
                    }

                    public int getFile_size() {
                        return file_size;
                    }

                    public void setFile_size(int file_size) {
                        this.file_size = file_size;
                    }
                }
            }
        }

        public static class CoverImageInfosBean {
            /**
             * mimetype : image/jpeg
             * image_type : 1
             * height : 183
             * width : 1709
             * web_uri : 289800014415a3581e72
             * desc :
             */

            private String mimetype;
            private int image_type;
            private int height;
            private int width;
            private String web_uri;
            private String desc;

            public String getMimetype() {
                return mimetype;
            }

            public void setMimetype(String mimetype) {
                this.mimetype = mimetype;
            }

            public int getImage_type() {
                return image_type;
            }

            public void setImage_type(int image_type) {
                this.image_type = image_type;
            }

            public int getHeight() {
                return height;
            }

            public void setHeight(int height) {
                this.height = height;
            }

            public int getWidth() {
                return width;
            }

            public void setWidth(int width) {
                this.width = width;
            }

            public String getWeb_uri() {
                return web_uri;
            }

            public void setWeb_uri(String web_uri) {
                this.web_uri = web_uri;
            }

            public String getDesc() {
                return desc;
            }

            public void setDesc(String desc) {
                this.desc = desc;
            }
        }

        public static class ImageListBean {
            /**
             * url : http://p2.pstatp.com/list/2892000245cb9654fa20
             * pc_url : http://p2.pstatp.com/list/194x108/2892000245cb9654fa20
             */

            private String url;
            private String pc_url;

            public String getUrl() {
                return url;
            }

            public void setUrl(String url) {
                this.url = url;
            }

            public String getPc_url() {
                return pc_url;
            }

            public void setPc_url(String pc_url) {
                this.pc_url = pc_url;
            }
        }

        public static class ThumbImageBean {
            /**
             * url : http://p9.pstatp.com/thumb/289800014415a3581e72
             * width : 1709
             * url_list : [{"url":"http://p9.pstatp.com/thumb/289800014415a3581e72"},{"url":"http://pb1.pstatp.com/thumb/289800014415a3581e72"},{"url":"http://pb3.pstatp.com/thumb/289800014415a3581e72"}]
             * uri : thumb/289800014415a3581e72
             * height : 183
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
                 * url : http://p9.pstatp.com/thumb/289800014415a3581e72
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

        public static class ImageDetailBean {
            /**
             * url : http://p9.pstatp.com/large/289800014415a3581e72
             * width : 1709
             * url_list : [{"url":"http://p9.pstatp.com/large/289800014415a3581e72"},{"url":"http://pb1.pstatp.com/large/289800014415a3581e72"},{"url":"http://pb3.pstatp.com/large/289800014415a3581e72"}]
             * uri : large/289800014415a3581e72
             * height : 183
             */

            private String url;
            private int width;
            private String uri;
            private int height;
            private List<UrlListBeanX> url_list;

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

            public List<UrlListBeanX> getUrl_list() {
                return url_list;
            }

            public void setUrl_list(List<UrlListBeanX> url_list) {
                this.url_list = url_list;
            }

            public static class UrlListBeanX {
                /**
                 * url : http://p9.pstatp.com/large/289800014415a3581e72
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

        public static class ImageInfosBean {
            /**
             * mimetype : image/png
             * image_type : 1
             * height : 183
             * width : 1709
             * web_uri : 289800014415a3581e72
             * desc :
             */

            private String mimetype;
            private int image_type;
            private int height;
            private int width;
            private String web_uri;
            private String desc;

            public String getMimetype() {
                return mimetype;
            }

            public void setMimetype(String mimetype) {
                this.mimetype = mimetype;
            }

            public int getImage_type() {
                return image_type;
            }

            public void setImage_type(int image_type) {
                this.image_type = image_type;
            }

            public int getHeight() {
                return height;
            }

            public void setHeight(int height) {
                this.height = height;
            }

            public int getWidth() {
                return width;
            }

            public void setWidth(int width) {
                this.width = width;
            }

            public String getWeb_uri() {
                return web_uri;
            }

            public void setWeb_uri(String web_uri) {
                this.web_uri = web_uri;
            }

            public String getDesc() {
                return desc;
            }

            public void setDesc(String desc) {
                this.desc = desc;
            }
        }
    }
}
