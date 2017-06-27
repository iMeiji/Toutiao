package com.meiji.toutiao.bean.search;

/**
 * Created by Meiji on 2017/6/27.
 */

public class SearchVideoInfoBean {


    /**
     * _ck : {}
     * data : {"detail_source":"爱开箱","media_user":{"screen_name":"爱开箱","no_display_pgc_icon":false,"avatar_url":"https://p3.pstatp.com/thumb/18a100189b4cd40fe3b6","id":"1560094012081153","user_auth_info":{"auth_type":"0","auth_info":"视频达人"}},"publish_time":1498533274,"title":"日本推出的\u201c黑暗料理\u201d看上去很恐怖，吃起来一言难尽","url":"http://toutiao.com/group/6436151402837312001/","is_original":true,"is_pgc_article":true,"content":"<p><div class=\"tt-video-box\" tt-videoid='aa97c92d453047b9aff3fecac5470edc' tt-poster='http://p3.pstatp.com/large/2a400004ade483b3b9b4'>视频加载中...<\/div><script src=\"https://s0.pstatp.com/tt_player/tt.player.js?v=20160723\"><\/script><\/p><p>咖喱 勇者斗恶龙 超辣咖喱 美食 零食 特色小吃 黑暗料理 食物 日本<\/p>","source":"爱开箱","video_play_count":31525}
     * success : true
     */

    private DataBean data;
    private boolean success;

    public DataBean getData() {
        return data;
    }

    public void setData(DataBean data) {
        this.data = data;
    }

    public boolean isSuccess() {
        return success;
    }

    public void setSuccess(boolean success) {
        this.success = success;
    }

    public static class DataBean {
        /**
         * detail_source : 爱开箱
         * media_user : {"screen_name":"爱开箱","no_display_pgc_icon":false,"avatar_url":"https://p3.pstatp.com/thumb/18a100189b4cd40fe3b6","id":"1560094012081153","user_auth_info":{"auth_type":"0","auth_info":"视频达人"}}
         * publish_time : 1498533274
         * title : 日本推出的“黑暗料理”看上去很恐怖，吃起来一言难尽
         * url : http://toutiao.com/group/6436151402837312001/
         * is_original : true
         * is_pgc_article : true
         * content : <p><div class="tt-video-box" tt-videoid='aa97c92d453047b9aff3fecac5470edc' tt-poster='http://p3.pstatp.com/large/2a400004ade483b3b9b4'>视频加载中...</div><script src="https://s0.pstatp.com/tt_player/tt.player.js?v=20160723"></script></p><p>咖喱 勇者斗恶龙 超辣咖喱 美食 零食 特色小吃 黑暗料理 食物 日本</p>
         * source : 爱开箱
         * video_play_count : 31525
         */

        private String detail_source;
        private MediaUserBean media_user;
        private int publish_time;
        private String title;
        private String url;
        private boolean is_original;
        private boolean is_pgc_article;
        private String content;
        private String source;
        private int video_play_count;

        public String getDetail_source() {
            return detail_source;
        }

        public void setDetail_source(String detail_source) {
            this.detail_source = detail_source;
        }

        public MediaUserBean getMedia_user() {
            return media_user;
        }

        public void setMedia_user(MediaUserBean media_user) {
            this.media_user = media_user;
        }

        public int getPublish_time() {
            return publish_time;
        }

        public void setPublish_time(int publish_time) {
            this.publish_time = publish_time;
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

        public boolean isIs_original() {
            return is_original;
        }

        public void setIs_original(boolean is_original) {
            this.is_original = is_original;
        }

        public boolean isIs_pgc_article() {
            return is_pgc_article;
        }

        public void setIs_pgc_article(boolean is_pgc_article) {
            this.is_pgc_article = is_pgc_article;
        }

        public String getContent() {
            return content;
        }

        public void setContent(String content) {
            this.content = content;
        }

        public String getSource() {
            return source;
        }

        public void setSource(String source) {
            this.source = source;
        }

        public int getVideo_play_count() {
            return video_play_count;
        }

        public void setVideo_play_count(int video_play_count) {
            this.video_play_count = video_play_count;
        }

        public static class MediaUserBean {
            /**
             * screen_name : 爱开箱
             * no_display_pgc_icon : false
             * avatar_url : https://p3.pstatp.com/thumb/18a100189b4cd40fe3b6
             * id : 1560094012081153
             * user_auth_info : {"auth_type":"0","auth_info":"视频达人"}
             */

            private String screen_name;
            private boolean no_display_pgc_icon;
            private String avatar_url;
            private String id;
            private UserAuthInfoBean user_auth_info;

            public String getScreen_name() {
                return screen_name;
            }

            public void setScreen_name(String screen_name) {
                this.screen_name = screen_name;
            }

            public boolean isNo_display_pgc_icon() {
                return no_display_pgc_icon;
            }

            public void setNo_display_pgc_icon(boolean no_display_pgc_icon) {
                this.no_display_pgc_icon = no_display_pgc_icon;
            }

            public String getAvatar_url() {
                return avatar_url;
            }

            public void setAvatar_url(String avatar_url) {
                this.avatar_url = avatar_url;
            }

            public String getId() {
                return id;
            }

            public void setId(String id) {
                this.id = id;
            }

            public UserAuthInfoBean getUser_auth_info() {
                return user_auth_info;
            }

            public void setUser_auth_info(UserAuthInfoBean user_auth_info) {
                this.user_auth_info = user_auth_info;
            }

            public static class UserAuthInfoBean {
                /**
                 * auth_type : 0
                 * auth_info : 视频达人
                 */

                private String auth_type;
                private String auth_info;

                public String getAuth_type() {
                    return auth_type;
                }

                public void setAuth_type(String auth_type) {
                    this.auth_type = auth_type;
                }

                public String getAuth_info() {
                    return auth_info;
                }

                public void setAuth_info(String auth_info) {
                    this.auth_info = auth_info;
                }
            }
        }
    }
}
