package com.meiji.toutiao.bean.media;

import com.google.gson.annotations.SerializedName;

import java.util.List;

/**
 * Created by Meiji on 2017/4/7.
 */

public class MediaArticleBean {

    /**
     * media_id : 5827361912
     * has_more : 1
     * next : {"max_behot_time":1491555120}
     * page_type : 1
     * message : success
     */

    private long media_id;
    private int has_more;
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

    public int getHas_more() {
        return has_more;
    }

    public void setHas_more(int has_more) {
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
         * max_behot_time : 1491555120
         */

        private int max_behot_time;

        public int getMax_behot_time() {
            return max_behot_time;
        }

        public void setMax_behot_time(int max_behot_time) {
            this.max_behot_time = max_behot_time;
        }
    }

    public static class DataBean {
        /**
         * play_effective_count : 0
         * gallery_pic_count : 0
         * go_detail_count : 0
         * title : 注意！今晚深圳这里可能严重拥堵！违停罚1000，务必提前绕道
         * internal_visit_count_format : 0
         * image_list : [{"url":"http://p2.pstatp.com/list/1ad50001ea3982ad0ab9","pc_url":"http://p2.pstatp.com/list/194x108/1ad50001ea3982ad0ab9"},{"url":"http://p2.pstatp.com/list/1adb0001cded2b01f724","pc_url":"http://p2.pstatp.com/list/194x108/1adb0001cded2b01f724"},{"url":"http://p2.pstatp.com/list/1ad80005b8212b3c5069","pc_url":"http://p2.pstatp.com/list/194x108/1ad80005b8212b3c5069"}]
         * abstract : 现在在看这条帖子的你是70后、80后、还是90后？
         * show_play_effective_count : 0
         * middle_mode : false
         * external_visit_count : 0
         * source_url : http://www.toutiao.com/item/6406233087969067521/
         * datetime : 2017-04-07 20:13
         * live_status : 0
         * more_mode : true
         * has_gallery : false
         * comments_count : 0
         * video_duration_str : 00:00
         * has_video : false
         * pc_image_url :
         * external_visit_count_format : 0
         * internal_visit_count : 0
         */

        private String play_effective_count;
        private int gallery_pic_count;
        private String go_detail_count;
        private String title;
        private String internal_visit_count_format;
        @SerializedName("abstract")
        private String abstractX;
        private String show_play_effective_count;
        private boolean middle_mode;
        private int external_visit_count;
        private String source_url;
        private String datetime;
        private int live_status;
        private boolean more_mode;
        private boolean has_gallery;
        private String comments_count;
        private String video_duration_str;
        private boolean has_video;
        private String pc_image_url;
        private String external_visit_count_format;
        private int internal_visit_count;
        private List<ImageListBean> image_list;

        public String getPlay_effective_count() {
            return play_effective_count;
        }

        public void setPlay_effective_count(String play_effective_count) {
            this.play_effective_count = play_effective_count;
        }

        public int getGallery_pic_count() {
            return gallery_pic_count;
        }

        public void setGallery_pic_count(int gallery_pic_count) {
            this.gallery_pic_count = gallery_pic_count;
        }

        public String getGo_detail_count() {
            return go_detail_count;
        }

        public void setGo_detail_count(String go_detail_count) {
            this.go_detail_count = go_detail_count;
        }

        public String getTitle() {
            return title;
        }

        public void setTitle(String title) {
            this.title = title;
        }

        public String getInternal_visit_count_format() {
            return internal_visit_count_format;
        }

        public void setInternal_visit_count_format(String internal_visit_count_format) {
            this.internal_visit_count_format = internal_visit_count_format;
        }

        public String getAbstractX() {
            return abstractX;
        }

        public void setAbstractX(String abstractX) {
            this.abstractX = abstractX;
        }

        public String getShow_play_effective_count() {
            return show_play_effective_count;
        }

        public void setShow_play_effective_count(String show_play_effective_count) {
            this.show_play_effective_count = show_play_effective_count;
        }

        public boolean isMiddle_mode() {
            return middle_mode;
        }

        public void setMiddle_mode(boolean middle_mode) {
            this.middle_mode = middle_mode;
        }

        public int getExternal_visit_count() {
            return external_visit_count;
        }

        public void setExternal_visit_count(int external_visit_count) {
            this.external_visit_count = external_visit_count;
        }

        public String getSource_url() {
            return source_url;
        }

        public void setSource_url(String source_url) {
            this.source_url = source_url;
        }

        public String getDatetime() {
            return datetime;
        }

        public void setDatetime(String datetime) {
            this.datetime = datetime;
        }

        public int getLive_status() {
            return live_status;
        }

        public void setLive_status(int live_status) {
            this.live_status = live_status;
        }

        public boolean isMore_mode() {
            return more_mode;
        }

        public void setMore_mode(boolean more_mode) {
            this.more_mode = more_mode;
        }

        public boolean isHas_gallery() {
            return has_gallery;
        }

        public void setHas_gallery(boolean has_gallery) {
            this.has_gallery = has_gallery;
        }

        public String getComments_count() {
            return comments_count;
        }

        public void setComments_count(String comments_count) {
            this.comments_count = comments_count;
        }

        public String getVideo_duration_str() {
            return video_duration_str;
        }

        public void setVideo_duration_str(String video_duration_str) {
            this.video_duration_str = video_duration_str;
        }

        public boolean isHas_video() {
            return has_video;
        }

        public void setHas_video(boolean has_video) {
            this.has_video = has_video;
        }

        public String getPc_image_url() {
            return pc_image_url;
        }

        public void setPc_image_url(String pc_image_url) {
            this.pc_image_url = pc_image_url;
        }

        public String getExternal_visit_count_format() {
            return external_visit_count_format;
        }

        public void setExternal_visit_count_format(String external_visit_count_format) {
            this.external_visit_count_format = external_visit_count_format;
        }

        public int getInternal_visit_count() {
            return internal_visit_count;
        }

        public void setInternal_visit_count(int internal_visit_count) {
            this.internal_visit_count = internal_visit_count;
        }

        public List<ImageListBean> getImage_list() {
            return image_list;
        }

        public void setImage_list(List<ImageListBean> image_list) {
            this.image_list = image_list;
        }

        public static class ImageListBean {
            /**
             * url : http://p2.pstatp.com/list/1ad50001ea3982ad0ab9
             * pc_url : http://p2.pstatp.com/list/194x108/1ad50001ea3982ad0ab9
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
    }
}
