package com.meiji.toutiao.bean.search;

import com.google.gson.annotations.SerializedName;

import java.util.List;

/**
 * Created by Meiji on 2017/6/13.
 */

public class SearchResultBean {

    /**
     * count : 10
     * return_count : 10
     * no_outsite_res : 0
     * keyword : keyword
     * has_more : 1
     * page_id : /search/
     * cur_ts : 1497529910291
     * cur_tab : 1
     * tab : {"tab_list":[{"tab_name":"综合","tab_id":1,"tab_code":"news"},{"tab_name":"视频","tab_id":2,"tab_code":"video"},{"tab_name":"图集","tab_id":3,"tab_code":"gallery"},{"tab_name":"用户","tab_id":4,"tab_code":"pgc"},{"tab_name":"问答","tab_id":5,"tab_code":"wenda"}],"cur_tab":1}
     * offset : 10
     * message : success
     * show_tabs : 1
     */

    private int count;
    private int return_count;
    private int no_outsite_res;
    private String keyword;
    private int has_more;
    private String page_id;
    private long cur_ts;
    private int cur_tab;
    private TabBean tab;
    private int offset;
    private String message;
    private int show_tabs;
    private List<DataBeanX> data;

    public int getCount() {
        return count;
    }

    public void setCount(int count) {
        this.count = count;
    }

    public int getReturn_count() {
        return return_count;
    }

    public void setReturn_count(int return_count) {
        this.return_count = return_count;
    }

    public int getNo_outsite_res() {
        return no_outsite_res;
    }

    public void setNo_outsite_res(int no_outsite_res) {
        this.no_outsite_res = no_outsite_res;
    }

    public String getKeyword() {
        return keyword;
    }

    public void setKeyword(String keyword) {
        this.keyword = keyword;
    }

    public int getHas_more() {
        return has_more;
    }

    public void setHas_more(int has_more) {
        this.has_more = has_more;
    }

    public String getPage_id() {
        return page_id;
    }

    public void setPage_id(String page_id) {
        this.page_id = page_id;
    }

    public long getCur_ts() {
        return cur_ts;
    }

    public void setCur_ts(long cur_ts) {
        this.cur_ts = cur_ts;
    }

    public int getCur_tab() {
        return cur_tab;
    }

    public void setCur_tab(int cur_tab) {
        this.cur_tab = cur_tab;
    }

    public TabBean getTab() {
        return tab;
    }

    public void setTab(TabBean tab) {
        this.tab = tab;
    }

    public int getOffset() {
        return offset;
    }

    public void setOffset(int offset) {
        this.offset = offset;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public int getShow_tabs() {
        return show_tabs;
    }

    public void setShow_tabs(int show_tabs) {
        this.show_tabs = show_tabs;
    }

    public List<DataBeanX> getData() {
        return data;
    }

    public void setData(List<DataBeanX> data) {
        this.data = data;
    }

    public static class TabBean {
        /**
         * tab_list : [{"tab_name":"综合","tab_id":1,"tab_code":"news"},{"tab_name":"视频","tab_id":2,"tab_code":"video"},{"tab_name":"图集","tab_id":3,"tab_code":"gallery"},{"tab_name":"用户","tab_id":4,"tab_code":"pgc"},{"tab_name":"问答","tab_id":5,"tab_code":"wenda"}]
         * cur_tab : 1
         */

        private int cur_tab;
        private List<TabListBean> tab_list;

        public int getCur_tab() {
            return cur_tab;
        }

        public void setCur_tab(int cur_tab) {
            this.cur_tab = cur_tab;
        }

        public List<TabListBean> getTab_list() {
            return tab_list;
        }

        public void setTab_list(List<TabListBean> tab_list) {
            this.tab_list = tab_list;
        }

        public static class TabListBean {
            /**
             * tab_name : 综合
             * tab_id : 1
             * tab_code : news
             */

            private String tab_name;
            private int tab_id;
            private String tab_code;

            public String getTab_name() {
                return tab_name;
            }

            public void setTab_name(String tab_name) {
                this.tab_name = tab_name;
            }

            public int getTab_id() {
                return tab_id;
            }

            public void setTab_id(int tab_id) {
                this.tab_id = tab_id;
            }

            public String getTab_code() {
                return tab_code;
            }

            public void setTab_code(String tab_code) {
                this.tab_code = tab_code;
            }
        }
    }

    public static class DataBeanX {
        /**
         * tokens : ["4"]
         * cell_type : 34
         * id_str : 6166641442
         * ala_src : zol
         * data : {"star":"3.8","reviewnum":"18","img":"http://2a.zol-img.com.cn/product/144_120x90/10/cewhLjlg1fNl6.jpg","title":"小辣椒4（LA4/移动4G）报价_点评_参数","url":"http://wap.zol.com.cn/392/391723/index.html?from=jinritoutiao","priceurl":"http://wap.zol.com.cn/392/391723/price_9999.html?from=jinritoutiao","sales":"0","reviewurl":"http://wap.zol.com.cn/392/391723/review.html?from=jinritoutiao","param":[{"val2":"1280x720像素","val1":"5英寸","name":"屏幕"},{"val2":"500万像素","val1":"1300万像素","name":"摄像头"},{"val2":"不可拆卸式电池","val1":"2200mAh","name":"电池"},{"val2":"1GB","val1":"四核","name":"性能"}],"showtext":"中关村在线","showurl":"http://wap.zol.com.cn?from=jinritoutiao","price":"￥1399-1999","imgurl":"http://wap.zol.com.cn/392/391723/pic.html?from=jinritoutiao"}
         * id : 6166641442
         * display : {"star":"3.8","reviewnum":"18","img":"http://2a.zol-img.com.cn/product/144_120x90/10/cewhLjlg1fNl6.jpg","title":"小辣椒4（LA4/移动4G）报价_点评_参数","url":"http://wap.zol.com.cn/392/391723/index.html?from=jinritoutiao","priceurl":"http://wap.zol.com.cn/392/391723/price_9999.html?from=jinritoutiao","sales":"0","reviewurl":"http://wap.zol.com.cn/392/391723/review.html?from=jinritoutiao","param":{"attribute":[{"val2":"1280x720像素","val1":"5英寸","name":"屏幕"},{"val2":"500万像素","val1":"1300万像素","name":"摄像头"},{"val2":"不可拆卸式电池","val1":"2200mAh","name":"电池"},{"val2":"1GB","val1":"四核","name":"性能"}]},"showtext":"中关村在线","showurl":"http://wap.zol.com.cn?from=jinritoutiao","price":"￥1399-1999","imgurl":"http://wap.zol.com.cn/392/391723/pic.html?from=jinritoutiao"}
         * media_name : 情爱人生
         * repin_count : 2936
         * ban_comment : 0
         * show_play_effective_count : 0
         * abstract : 人生四有有根则立身，有人则持敬，有数则势顺，有书则祛惑，立身正，持心敬，顺势为，祛惑行人生四无人生四别有钱别省有福别等有气别忍有恨别记人生四苦看不透喧嚣中的平淡，繁华后的宁静舍不得曾经的精彩输不起一截人生之败放不下早已尘封的是与非人生四行人生四得人生在世，要沉得住气，变得了脸，弯
         * display_title :
         * datetime : 2017-06-05 10:09
         * article_type : 0
         * more_mode : true
         * create_time : 1496628593
         * has_m3u8_video : 0
         * keywords : 是是非非
         * has_mp4_video : 0
         * favorite_count : 2936
         * aggr_type : 0
         * article_sub_type : 0
         * bury_count : 1
         * title : 人生：4有，4无，4苦，4别，4行，4得，4然（精辟）
         * has_video : false
         * share_url : http://toutiao.com/group/6427969080238653698/?iid=10344168417&app=news_article
         * source : 情爱人生
         * comment_count : 9
         * article_url : http://toutiao.com/group/6427969080238653698/
         * middle_mode : false
         * large_mode : false
         * item_source_url : /group/6427969080238653698/
         * media_url : http://toutiao.com/m1555847419538433/
         * display_time : 1496628531
         * publish_time : 1496628531
         * go_detail_count : 78846
         * image_list : [{"url":"http://p1.pstatp.com/list/243000056d0dd1740d1c"},{"url":"http://p3.pstatp.com/list/2432000174dc641e6ad8"},{"url":"http://p3.pstatp.com/list/243100022e6d0139037c"}]
         * item_seo_url : /group/6427969080238653698/
         * tag_id : 6427969080238653000
         * source_url : sslocal://detail?item_id=6427639133962764801&gd_ext_json=%7B%22source%22%3A+%22search_tab%22%2C+%22query%22%3A+%224%22%2C+%22city%22%3A+%7B%22cur_city%22%3A+%22%5Cu672c%5Cu5730%22%2C+%22city%22%3A+%22%5Cu672c%5Cu5730%22%2C+%22city_type%22%3A+0%2C+%22longitude%22%3A+null%2C+%22cur_city_type%22%3A+0%2C+%22latitude%22%3A+null%7D%7D&groupid=6427969080238653698&gd_label=click_search&aggr_type=1
         * item_id : 6427639133962764801
         * natant_level : 0
         * seo_url : /group/6427969080238653698/
         * display_url : http://toutiao.com/group/6427969080238653698/
         * url : http://toutiao.com/group/6427969080238653698/
         * level : 0
         * digg_count : 3
         * behot_time : 1496628531
         * tag : news_story
         * has_gallery : false
         * has_image : true
         * highlight : {"source":[],"abstract":[],"title":[[3,1],[6,1],[9,1],[12,1],[15,1],[18,1],[21,1]]}
         * group_id : 6427969080238653698
         * middle_image : {"url":"http://p1.pstatp.com/list/243000056d0dd1740d1c","width":1439,"url_list":[{"url":"http://p1.pstatp.com/list/243000056d0dd1740d1c"},{"url":"http://pb3.pstatp.com/list/243000056d0dd1740d1c"},{"url":"http://pb9.pstatp.com/list/243000056d0dd1740d1c"}],"uri":"list/243000056d0dd1740d1c","height":1299}
         * play_effective_count : 120
         * video_duration : 17
         * image_url : http://p3.pstatp.com/list/1d1700062111909ea91a
         * video_duration_str : 00:17
         * group_flags : 131072
         * gallery_pic_count : 7
         * queries : [{"url":"sslocal://search?from=sug_search_tab&keyword=%E7%BB%8F%E5%85%B8%E8%AF%AD%E5%BD%95","text":"经典语录"},{"url":"sslocal://search?from=sug_search_tab&keyword=%E4%B8%96%E4%B9%92%E8%B5%9B","text":"世乒赛"},{"url":"sslocal://search?from=sug_search_tab&keyword=%E6%A2%85%E8%A5%BF","text":"梅西"},{"url":"sslocal://search?from=sug_search_tab&keyword=%E9%98%9C%E9%98%B3","text":"阜阳"},{"url":"sslocal://search?from=sug_search_tab&keyword=%E5%BC%A0%E7%BB%A7%E7%A7%91","text":"张继科"},{"url":"sslocal://search?from=sug_search_tab&keyword=%E6%83%85%E7%88%B1","text":"情爱"},{"url":"sslocal://search?from=sug_search_tab&keyword=%E5%B0%8F%E7%B1%B34","text":"小米4"},{"url":"sslocal://search?from=sug_search_tab&keyword=%E5%88%98%E8%AF%97%E9%9B%AF","text":"刘诗雯"},{"url":"sslocal://search?from=sug_search_tab&keyword=%E8%A5%BF%E5%AE%89","text":"西安"}]
         */

        private int cell_type;
        private String id_str;
        private String ala_src;
        //        private DataBean data;
        private long id;
        //        private DisplayBean display;
        private String media_name;
        private int repin_count;
        private int ban_comment;
        private int show_play_effective_count;
        @SerializedName("abstract")
        private String abstractX;
        private String display_title;
        private String datetime;
        private int article_type;
        private boolean more_mode;
        private int create_time;
        private int has_m3u8_video;
        private String keywords;
        private int has_mp4_video;
        private int favorite_count;
        private int aggr_type;
        private int article_sub_type;
        private int bury_count;
        private String title;
        private boolean has_video;
        private String share_url;
        private String source;
        private int comment_count;
        private String article_url;
        private boolean middle_mode;
        private boolean large_mode;
        private String item_source_url;
        private String media_url;
        private int display_time;
        private int publish_time;
        private int go_detail_count;
        private String item_seo_url;
        private long tag_id;
        private String source_url;
        private long item_id;
        private int natant_level;
        private String seo_url;
        private String display_url;
        private String url;
        private int level;
        private int digg_count;
        private int behot_time;
        private String tag;
        private boolean has_gallery;
        private boolean has_image;
        private HighlightBean highlight;
        private long group_id;
        private MiddleImageBean middle_image;
        private String play_effective_count;
        private int video_duration;
        private String image_url;
        private String video_duration_str;
        private int group_flags;
        private int gallery_pic_count;
        //        private List<String> tokens;
        private List<ImageListBean> image_list;
        private List<QueriesBean> queries;

        @Override
        public boolean equals(Object o) {
            if (this == o)
                return true;
            if (o == null || getClass() != o.getClass())
                return false;

            DataBeanX dataBeanX = (DataBeanX) o;

            if (!abstractX.equals(dataBeanX.abstractX))
                return false;
            return title.equals(dataBeanX.title);
        }

        @Override
        public int hashCode() {
            int result = abstractX.hashCode();
            result = 31 * result + title.hashCode();
            return result;
        }

        public int getCell_type() {
            return cell_type;
        }

        public void setCell_type(int cell_type) {
            this.cell_type = cell_type;
        }

        public String getId_str() {
            return id_str;
        }

        public void setId_str(String id_str) {
            this.id_str = id_str;
        }

        public String getAla_src() {
            return ala_src;
        }

        public void setAla_src(String ala_src) {
            this.ala_src = ala_src;
        }

//        public DataBean getData() {
//            return data;
//        }
//
//        public void setData(DataBean data) {
//            this.data = data;
//        }

        public long getId() {
            return id;
        }

        public void setId(long id) {
            this.id = id;
        }

//        public DisplayBean getDisplay() {
//            return display;
//        }
//
//        public void setDisplay(DisplayBean display) {
//            this.display = display;
//        }

        public String getMedia_name() {
            return media_name;
        }

        public void setMedia_name(String media_name) {
            this.media_name = media_name;
        }

        public int getRepin_count() {
            return repin_count;
        }

        public void setRepin_count(int repin_count) {
            this.repin_count = repin_count;
        }

        public int getBan_comment() {
            return ban_comment;
        }

        public void setBan_comment(int ban_comment) {
            this.ban_comment = ban_comment;
        }

        public int getShow_play_effective_count() {
            return show_play_effective_count;
        }

        public void setShow_play_effective_count(int show_play_effective_count) {
            this.show_play_effective_count = show_play_effective_count;
        }

        public String getAbstractX() {
            return abstractX;
        }

        public void setAbstractX(String abstractX) {
            this.abstractX = abstractX;
        }

        public String getDisplay_title() {
            return display_title;
        }

        public void setDisplay_title(String display_title) {
            this.display_title = display_title;
        }

        public String getDatetime() {
            return datetime;
        }

        public void setDatetime(String datetime) {
            this.datetime = datetime;
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

        public int getCreate_time() {
            return create_time;
        }

        public void setCreate_time(int create_time) {
            this.create_time = create_time;
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

        public int getAggr_type() {
            return aggr_type;
        }

        public void setAggr_type(int aggr_type) {
            this.aggr_type = aggr_type;
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

        public boolean isMiddle_mode() {
            return middle_mode;
        }

        public void setMiddle_mode(boolean middle_mode) {
            this.middle_mode = middle_mode;
        }

        public boolean isLarge_mode() {
            return large_mode;
        }

        public void setLarge_mode(boolean large_mode) {
            this.large_mode = large_mode;
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

        public long getItem_id() {
            return item_id;
        }

        public void setItem_id(long item_id) {
            this.item_id = item_id;
        }

        public int getNatant_level() {
            return natant_level;
        }

        public void setNatant_level(int natant_level) {
            this.natant_level = natant_level;
        }

        public String getSeo_url() {
            return seo_url;
        }

        public void setSeo_url(String seo_url) {
            this.seo_url = seo_url;
        }

        public String getDisplay_url() {
            return display_url;
        }

        public void setDisplay_url(String display_url) {
            this.display_url = display_url;
        }

        public String getUrl() {
            return url;
        }

        public void setUrl(String url) {
            this.url = url;
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

        public String getTag() {
            return tag;
        }

        public void setTag(String tag) {
            this.tag = tag;
        }

        public boolean isHas_gallery() {
            return has_gallery;
        }

        public void setHas_gallery(boolean has_gallery) {
            this.has_gallery = has_gallery;
        }

        public boolean isHas_image() {
            return has_image;
        }

        public void setHas_image(boolean has_image) {
            this.has_image = has_image;
        }

        public HighlightBean getHighlight() {
            return highlight;
        }

        public void setHighlight(HighlightBean highlight) {
            this.highlight = highlight;
        }

        public long getGroup_id() {
            return group_id;
        }

        public void setGroup_id(long group_id) {
            this.group_id = group_id;
        }

        public MiddleImageBean getMiddle_image() {
            return middle_image;
        }

        public void setMiddle_image(MiddleImageBean middle_image) {
            this.middle_image = middle_image;
        }

        public String getPlay_effective_count() {
            return play_effective_count;
        }

        public void setPlay_effective_count(String play_effective_count) {
            this.play_effective_count = play_effective_count;
        }

        public int getVideo_duration() {
            return video_duration;
        }

        public void setVideo_duration(int video_duration) {
            this.video_duration = video_duration;
        }

        public String getImage_url() {
            return image_url;
        }

        public void setImage_url(String image_url) {
            this.image_url = image_url;
        }

        public String getVideo_duration_str() {
            return video_duration_str;
        }

        public void setVideo_duration_str(String video_duration_str) {
            this.video_duration_str = video_duration_str;
        }

        public int getGroup_flags() {
            return group_flags;
        }

        public void setGroup_flags(int group_flags) {
            this.group_flags = group_flags;
        }

        public int getGallery_pic_count() {
            return gallery_pic_count;
        }

        public void setGallery_pic_count(int gallery_pic_count) {
            this.gallery_pic_count = gallery_pic_count;
        }

//        public List<String> getTokens() {
//            return tokens;
//        }
//
//        public void setTokens(List<String> tokens) {
//            this.tokens = tokens;
//        }

        public List<ImageListBean> getImage_list() {
            return image_list;
        }

        public void setImage_list(List<ImageListBean> image_list) {
            this.image_list = image_list;
        }

        public List<QueriesBean> getQueries() {
            return queries;
        }

        public void setQueries(List<QueriesBean> queries) {
            this.queries = queries;
        }

        public static class DataBean {
            /**
             * star : 3.8
             * reviewnum : 18
             * img : http://2a.zol-img.com.cn/product/144_120x90/10/cewhLjlg1fNl6.jpg
             * title : 小辣椒4（LA4/移动4G）报价_点评_参数
             * url : http://wap.zol.com.cn/392/391723/index.html?from=jinritoutiao
             * priceurl : http://wap.zol.com.cn/392/391723/price_9999.html?from=jinritoutiao
             * sales : 0
             * reviewurl : http://wap.zol.com.cn/392/391723/review.html?from=jinritoutiao
             * param : [{"val2":"1280x720像素","val1":"5英寸","name":"屏幕"},{"val2":"500万像素","val1":"1300万像素","name":"摄像头"},{"val2":"不可拆卸式电池","val1":"2200mAh","name":"电池"},{"val2":"1GB","val1":"四核","name":"性能"}]
             * showtext : 中关村在线
             * showurl : http://wap.zol.com.cn?from=jinritoutiao
             * price : ￥1399-1999
             * imgurl : http://wap.zol.com.cn/392/391723/pic.html?from=jinritoutiao
             */

            private String star;
            private String reviewnum;
            private String img;
            private String title;
            private String url;
            private String priceurl;
            private String sales;
            private String reviewurl;
            private String showtext;
            private String showurl;
            private String price;
            private String imgurl;
            private List<ParamBean> param;

            public String getStar() {
                return star;
            }

            public void setStar(String star) {
                this.star = star;
            }

            public String getReviewnum() {
                return reviewnum;
            }

            public void setReviewnum(String reviewnum) {
                this.reviewnum = reviewnum;
            }

            public String getImg() {
                return img;
            }

            public void setImg(String img) {
                this.img = img;
            }

            public String getTitle() {
                return title;
            }

            public void setTitle(String title) {
                this.title = title;
            }

            public String getUrl() {
                return url;
            }

            public void setUrl(String url) {
                this.url = url;
            }

            public String getPriceurl() {
                return priceurl;
            }

            public void setPriceurl(String priceurl) {
                this.priceurl = priceurl;
            }

            public String getSales() {
                return sales;
            }

            public void setSales(String sales) {
                this.sales = sales;
            }

            public String getReviewurl() {
                return reviewurl;
            }

            public void setReviewurl(String reviewurl) {
                this.reviewurl = reviewurl;
            }

            public String getShowtext() {
                return showtext;
            }

            public void setShowtext(String showtext) {
                this.showtext = showtext;
            }

            public String getShowurl() {
                return showurl;
            }

            public void setShowurl(String showurl) {
                this.showurl = showurl;
            }

            public String getPrice() {
                return price;
            }

            public void setPrice(String price) {
                this.price = price;
            }

            public String getImgurl() {
                return imgurl;
            }

            public void setImgurl(String imgurl) {
                this.imgurl = imgurl;
            }

            public List<ParamBean> getParam() {
                return param;
            }

            public void setParam(List<ParamBean> param) {
                this.param = param;
            }

            public static class ParamBean {
                /**
                 * val2 : 1280x720像素
                 * val1 : 5英寸
                 * name : 屏幕
                 */

                private String val2;
                private String val1;
                private String name;

                public String getVal2() {
                    return val2;
                }

                public void setVal2(String val2) {
                    this.val2 = val2;
                }

                public String getVal1() {
                    return val1;
                }

                public void setVal1(String val1) {
                    this.val1 = val1;
                }

                public String getName() {
                    return name;
                }

                public void setName(String name) {
                    this.name = name;
                }
            }
        }

        public static class DisplayBean {
            /**
             * star : 3.8
             * reviewnum : 18
             * img : http://2a.zol-img.com.cn/product/144_120x90/10/cewhLjlg1fNl6.jpg
             * title : 小辣椒4（LA4/移动4G）报价_点评_参数
             * url : http://wap.zol.com.cn/392/391723/index.html?from=jinritoutiao
             * priceurl : http://wap.zol.com.cn/392/391723/price_9999.html?from=jinritoutiao
             * sales : 0
             * reviewurl : http://wap.zol.com.cn/392/391723/review.html?from=jinritoutiao
             * param : {"attribute":[{"val2":"1280x720像素","val1":"5英寸","name":"屏幕"},{"val2":"500万像素","val1":"1300万像素","name":"摄像头"},{"val2":"不可拆卸式电池","val1":"2200mAh","name":"电池"},{"val2":"1GB","val1":"四核","name":"性能"}]}
             * showtext : 中关村在线
             * showurl : http://wap.zol.com.cn?from=jinritoutiao
             * price : ￥1399-1999
             * imgurl : http://wap.zol.com.cn/392/391723/pic.html?from=jinritoutiao
             */

            private String star;
            private String reviewnum;
            private String img;
            private String title;
            private String url;
            private String priceurl;
            private String sales;
            private String reviewurl;
            private ParamBeanX param;
            private String showtext;
            private String showurl;
            private String price;
            private String imgurl;

            public String getStar() {
                return star;
            }

            public void setStar(String star) {
                this.star = star;
            }

            public String getReviewnum() {
                return reviewnum;
            }

            public void setReviewnum(String reviewnum) {
                this.reviewnum = reviewnum;
            }

            public String getImg() {
                return img;
            }

            public void setImg(String img) {
                this.img = img;
            }

            public String getTitle() {
                return title;
            }

            public void setTitle(String title) {
                this.title = title;
            }

            public String getUrl() {
                return url;
            }

            public void setUrl(String url) {
                this.url = url;
            }

            public String getPriceurl() {
                return priceurl;
            }

            public void setPriceurl(String priceurl) {
                this.priceurl = priceurl;
            }

            public String getSales() {
                return sales;
            }

            public void setSales(String sales) {
                this.sales = sales;
            }

            public String getReviewurl() {
                return reviewurl;
            }

            public void setReviewurl(String reviewurl) {
                this.reviewurl = reviewurl;
            }

            public ParamBeanX getParam() {
                return param;
            }

            public void setParam(ParamBeanX param) {
                this.param = param;
            }

            public String getShowtext() {
                return showtext;
            }

            public void setShowtext(String showtext) {
                this.showtext = showtext;
            }

            public String getShowurl() {
                return showurl;
            }

            public void setShowurl(String showurl) {
                this.showurl = showurl;
            }

            public String getPrice() {
                return price;
            }

            public void setPrice(String price) {
                this.price = price;
            }

            public String getImgurl() {
                return imgurl;
            }

            public void setImgurl(String imgurl) {
                this.imgurl = imgurl;
            }

            public static class ParamBeanX {
                private List<AttributeBean> attribute;

                public List<AttributeBean> getAttribute() {
                    return attribute;
                }

                public void setAttribute(List<AttributeBean> attribute) {
                    this.attribute = attribute;
                }

                public static class AttributeBean {
                    /**
                     * val2 : 1280x720像素
                     * val1 : 5英寸
                     * name : 屏幕
                     */

                    private String val2;
                    private String val1;
                    private String name;

                    public String getVal2() {
                        return val2;
                    }

                    public void setVal2(String val2) {
                        this.val2 = val2;
                    }

                    public String getVal1() {
                        return val1;
                    }

                    public void setVal1(String val1) {
                        this.val1 = val1;
                    }

                    public String getName() {
                        return name;
                    }

                    public void setName(String name) {
                        this.name = name;
                    }
                }
            }
        }

        public static class HighlightBean {
            private List<?> source;
            @SerializedName("abstract")
            private List<?> abstractX;
            private List<List<Integer>> title;

            public List<?> getSource() {
                return source;
            }

            public void setSource(List<?> source) {
                this.source = source;
            }

            public List<?> getAbstractX() {
                return abstractX;
            }

            public void setAbstractX(List<?> abstractX) {
                this.abstractX = abstractX;
            }

            public List<List<Integer>> getTitle() {
                return title;
            }

            public void setTitle(List<List<Integer>> title) {
                this.title = title;
            }
        }

        public static class MiddleImageBean {
            /**
             * url : http://p1.pstatp.com/list/243000056d0dd1740d1c
             * width : 1439
             * url_list : [{"url":"http://p1.pstatp.com/list/243000056d0dd1740d1c"},{"url":"http://pb3.pstatp.com/list/243000056d0dd1740d1c"},{"url":"http://pb9.pstatp.com/list/243000056d0dd1740d1c"}]
             * uri : list/243000056d0dd1740d1c
             * height : 1299
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
                 * url : http://p1.pstatp.com/list/243000056d0dd1740d1c
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

        public static class ImageListBean {
            /**
             * url : http://p1.pstatp.com/list/243000056d0dd1740d1c
             */

            private String url;

            public String getUrl() {
                return url;
            }

            public void setUrl(String url) {
                this.url = url;
            }
        }

        public static class QueriesBean {
            /**
             * url : sslocal://search?from=sug_search_tab&keyword=%E7%BB%8F%E5%85%B8%E8%AF%AD%E5%BD%95
             * text : 经典语录
             */

            private String url;
            private String text;

            public String getUrl() {
                return url;
            }

            public void setUrl(String url) {
                this.url = url;
            }

            public String getText() {
                return text;
            }

            public void setText(String text) {
                this.text = text;
            }
        }
    }
}
