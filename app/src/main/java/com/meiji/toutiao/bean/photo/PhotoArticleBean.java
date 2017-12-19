package com.meiji.toutiao.bean.photo;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.List;

/**
 * Created by Meiji on 2017/2/16.
 */

public class PhotoArticleBean {

    /**
     * has_more : true
     * message : success
     * next : {"max_behot_time":1494154227}
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
         * max_behot_time : 1494154227
         */

        private String max_behot_time;

        public String getMax_behot_time() {
            return max_behot_time;
        }

        public void setMax_behot_time(String max_behot_time) {
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
         * image_url : http://p9.pstatp.com/list/640x360/1b820001767b34813c82
         * media_avatar_url : http://p1.pstatp.com/large/d2a0011291a5cfef5c3
         * article_genre : gallery
         * is_diversion_page : false
         * title : 我拍少女签约模特王纯粹少女视觉
         * middle_mode : false
         * gallary_image_count : 41
         * image_list : [{"url":"http://p3.pstatp.com/list/640x360/1b820001767b34813c82","width":620,"url_list":[{"url":"http://p9.pstatp.com/list/640x360/1b820001767b34813c82"},{"url":"http://pb1.pstatp.com/list/640x360/1b820001767b34813c82"},{"url":"http://pb3.pstatp.com/list/640x360/1b820001767b34813c82"}],"uri":"list/640x360/1b820001767b34813c82","height":855},{"url":"http://p3.pstatp.com/list/640x360/1b8700015c172e7de742","width":1280,"url_list":[{"url":"http://p3.pstatp.com/list/1b8700015c172e7de742"},{"url":"http://pb9.pstatp.com/list/1b8700015c172e7de742"},{"url":"http://pb1.pstatp.com/list/1b8700015c172e7de742"}],"uri":"list/1b8700015c172e7de742","height":720},{"url":"http://p9.pstatp.com/list/640x360/1b8500015b6a1c897705","width":1656,"url_list":[{"url":"http://p9.pstatp.com/list/1b8500015b6a1c897705"},{"url":"http://pb1.pstatp.com/list/1b8500015b6a1c897705"},{"url":"http://pb3.pstatp.com/list/1b8500015b6a1c897705"}],"uri":"list/1b8500015b6a1c897705","height":2415}]
         * more_mode : true
         * behot_time : 1494158550
         * source_url : /group/6408674663173767426/
         * source : 我拍少女
         * hot : 1
         * is_feed_ad : false
         * has_gallery : true
         * single_mode : false
         * comments_count : 1
         * group_id : 6408674663173767426
         * media_url : http://toutiao.com/m4439122761/
         * honey : true
         */

        private String image_url;
        private String media_avatar_url;
        private String article_genre;
        private boolean is_diversion_page;
        private String title;
        private boolean middle_mode;
        private int gallary_image_count;
        private boolean more_mode;
        private int behot_time;
        private String source_url;
        private String source;
        private int hot;
        private boolean is_feed_ad;
        private boolean has_gallery;
        private boolean single_mode;
        private int comments_count;
        private String group_id;
        private String media_url;
        private boolean honey;
        private List<ImageListBean> image_list;
        protected DataBean(Parcel in) {
            image_url = in.readString();
            media_avatar_url = in.readString();
            article_genre = in.readString();
            is_diversion_page = in.readByte() != 0;
            title = in.readString();
            middle_mode = in.readByte() != 0;
            gallary_image_count = in.readInt();
            more_mode = in.readByte() != 0;
            behot_time = in.readInt();
            source_url = in.readString();
            source = in.readString();
            hot = in.readInt();
            is_feed_ad = in.readByte() != 0;
            has_gallery = in.readByte() != 0;
            single_mode = in.readByte() != 0;
            comments_count = in.readInt();
            group_id = in.readString();
            media_url = in.readString();
            honey = in.readByte() != 0;
        }

        @Override
        public boolean equals(Object o) {
            if (this == o)
                return true;
            if (o == null || getClass() != o.getClass())
                return false;

            DataBean dataBean = (DataBean) o;

            if (!title.equals(dataBean.title))
                return false;
            return source_url.equals(dataBean.source_url);
        }

        @Override
        public int hashCode() {
            int result = title.hashCode();
            result = 31 * result + source_url.hashCode();
            return result;
        }

        @Override
        public void writeToParcel(Parcel dest, int flags) {
            dest.writeString(image_url);
            dest.writeString(media_avatar_url);
            dest.writeString(article_genre);
            dest.writeByte((byte) (is_diversion_page ? 1 : 0));
            dest.writeString(title);
            dest.writeByte((byte) (middle_mode ? 1 : 0));
            dest.writeInt(gallary_image_count);
            dest.writeByte((byte) (more_mode ? 1 : 0));
            dest.writeInt(behot_time);
            dest.writeString(source_url);
            dest.writeString(source);
            dest.writeInt(hot);
            dest.writeByte((byte) (is_feed_ad ? 1 : 0));
            dest.writeByte((byte) (has_gallery ? 1 : 0));
            dest.writeByte((byte) (single_mode ? 1 : 0));
            dest.writeInt(comments_count);
            dest.writeString(group_id);
            dest.writeString(media_url);
            dest.writeByte((byte) (honey ? 1 : 0));
        }

        @Override
        public int describeContents() {
            return 0;
        }

        public String getImage_url() {
            return image_url;
        }

        public void setImage_url(String image_url) {
            this.image_url = image_url;
        }

        public String getMedia_avatar_url() {
            return media_avatar_url;
        }

        public void setMedia_avatar_url(String media_avatar_url) {
            this.media_avatar_url = media_avatar_url;
        }

        public String getArticle_genre() {
            return article_genre;
        }

        public void setArticle_genre(String article_genre) {
            this.article_genre = article_genre;
        }

        public boolean isIs_diversion_page() {
            return is_diversion_page;
        }

        public void setIs_diversion_page(boolean is_diversion_page) {
            this.is_diversion_page = is_diversion_page;
        }

        public String getTitle() {
            return title;
        }

        public void setTitle(String title) {
            this.title = title;
        }

        public boolean isMiddle_mode() {
            return middle_mode;
        }

        public void setMiddle_mode(boolean middle_mode) {
            this.middle_mode = middle_mode;
        }

        public int getGallary_image_count() {
            return gallary_image_count;
        }

        public void setGallary_image_count(int gallary_image_count) {
            this.gallary_image_count = gallary_image_count;
        }

        public boolean isMore_mode() {
            return more_mode;
        }

        public void setMore_mode(boolean more_mode) {
            this.more_mode = more_mode;
        }

        public int getBehot_time() {
            return behot_time;
        }

        public void setBehot_time(int behot_time) {
            this.behot_time = behot_time;
        }

        public String getSource_url() {
            return source_url;
        }

        public void setSource_url(String source_url) {
            this.source_url = source_url;
        }

        public String getSource() {
            return source;
        }

        public void setSource(String source) {
            this.source = source;
        }

        public int getHot() {
            return hot;
        }

        public void setHot(int hot) {
            this.hot = hot;
        }

        public boolean isIs_feed_ad() {
            return is_feed_ad;
        }

        public void setIs_feed_ad(boolean is_feed_ad) {
            this.is_feed_ad = is_feed_ad;
        }

        public boolean isHas_gallery() {
            return has_gallery;
        }

        public void setHas_gallery(boolean has_gallery) {
            this.has_gallery = has_gallery;
        }

        public boolean isSingle_mode() {
            return single_mode;
        }

        public void setSingle_mode(boolean single_mode) {
            this.single_mode = single_mode;
        }

        public int getComments_count() {
            return comments_count;
        }

        public void setComments_count(int comments_count) {
            this.comments_count = comments_count;
        }

        public String getGroup_id() {
            return group_id;
        }

        public void setGroup_id(String group_id) {
            this.group_id = group_id;
        }

        public String getMedia_url() {
            return media_url;
        }

        public void setMedia_url(String media_url) {
            this.media_url = media_url;
        }

        public boolean isHoney() {
            return honey;
        }

        public void setHoney(boolean honey) {
            this.honey = honey;
        }

        public List<ImageListBean> getImage_list() {
            return image_list;
        }

        public void setImage_list(List<ImageListBean> image_list) {
            this.image_list = image_list;
        }

        public static class ImageListBean {
            /**
             * url : http://p3.pstatp.com/list/640x360/1b820001767b34813c82
             * width : 620
             * url_list : [{"url":"http://p9.pstatp.com/list/640x360/1b820001767b34813c82"},{"url":"http://pb1.pstatp.com/list/640x360/1b820001767b34813c82"},{"url":"http://pb3.pstatp.com/list/640x360/1b820001767b34813c82"}]
             * uri : list/640x360/1b820001767b34813c82
             * height : 855
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
                 * url : http://p9.pstatp.com/list/640x360/1b820001767b34813c82
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
