package com.meiji.toutiao.bean.other.joke;

import java.util.List;

/**
 * Created by Meiji on 2016/12/28.
 */

public class JokeContentBean {

    /**
     * has_more : true
     * message : success
     * data : []
     * next : {"max_behot_time":1482909099}
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
         * max_behot_time : 1482909099
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
         * group : {"text":"我知道这里大神多，巨友帮忙翻译个句子，谢谢了，\u201c如果每天早上醒来都能看到这张脸，那你丈夫我，还会去追求其他的什么么？\u201d这个，翻译成文言文，谢谢了","create_time":1482909018,"id":53329555537,"favorite_count":10,"go_detail_count":3525,"user_favorite":0,"share_type":1,"is_can_share":1,"comment_count":44,"share_url":"http://m.neihanshequ.com/share/group/53329555537/?iid=0&app=joke_essay","label":4,"content":"我知道这里大神多，巨友帮忙翻译个句子，谢谢了，\u201c如果每天早上醒来都能看到这张脸，那你丈夫我，还会去追求其他的什么么？\u201d这个，翻译成文言文，谢谢了","category_type":1,"id_str":"53329555537","media_type":0,"share_count":48,"type":3,"status":102,"has_comments":0,"user_bury":0,"status_desc":"已发表","user":{"is_following":false,"avatar_url":"http://p3.pstatp.com/thumb/123200136e8a76748b9f","user_id":51169444246,"name":"进击的穆穆","user_verified":false},"user_digg":0,"online_time":1482909018,"category_name":"内涵段子","category_visible":false,"bury_count":18,"is_anonymous":false,"repin_count":10,"digg_count":98,"has_hot_comments":1,"user_repin":0,"activity":{},"group_id":53329555537,"category_id":1}
         * comments : []
         * type : 1
         * display_time : 1482914799
         * online_time : 1482914799
         */

        private GroupBean group;
        private int type;
        //        private int display_time;
//        private int online_time;
        private List<?> comments;

        public GroupBean getGroup() {
            return group;
        }

        public void setGroup(GroupBean group) {
            this.group = group;
        }

        public int getType() {
            return type;
        }

        public void setType(int type) {
            this.type = type;
        }

//        public int getDisplay_time() {
//            return display_time;
//        }
//
//        public void setDisplay_time(int display_time) {
//            this.display_time = display_time;
//        }
//
//        public int getOnline_time() {
//            return online_time;
//        }
//
//        public void setOnline_time(int online_time) {
//            this.online_time = online_time;
//        }

        public List<?> getComments() {
            return comments;
        }

        public void setComments(List<?> comments) {
            this.comments = comments;
        }

        public static class GroupBean {
            /**
             * text : 我知道这里大神多，巨友帮忙翻译个句子，谢谢了，“如果每天早上醒来都能看到这张脸，那你丈夫我，还会去追求其他的什么么？”这个，翻译成文言文，谢谢了
             * create_time : 1482909018
             * id : 53329555537
             * favorite_count : 10
             * go_detail_count : 3525
             * user_favorite : 0
             * share_type : 1
             * is_can_share : 1
             * comment_count : 44
             * share_url : http://m.neihanshequ.com/share/group/53329555537/?iid=0&app=joke_essay
             * label : 4
             * content : 我知道这里大神多，巨友帮忙翻译个句子，谢谢了，“如果每天早上醒来都能看到这张脸，那你丈夫我，还会去追求其他的什么么？”这个，翻译成文言文，谢谢了
             * category_type : 1
             * id_str : 53329555537
             * media_type : 0
             * share_count : 48
             * type : 3
             * status : 102
             * has_comments : 0
             * user_bury : 0
             * status_desc : 已发表
             * user : {"is_following":false,"avatar_url":"http://p3.pstatp.com/thumb/123200136e8a76748b9f","user_id":51169444246,"name":"进击的穆穆","user_verified":false}
             * user_digg : 0
             * online_time : 1482909018
             * category_name : 内涵段子
             * category_visible : false
             * bury_count : 18
             * is_anonymous : false
             * repin_count : 10
             * digg_count : 98
             * has_hot_comments : 1
             * user_repin : 0
             * activity : {}
             * group_id : 53329555537
             * category_id : 1
             */

            private String text;
            private int create_time;
            private long id;
            private int favorite_count;
            private int go_detail_count;
            private int user_favorite;
            private int share_type;
            private int is_can_share;
            private int comment_count;
            private String share_url;
            private int label;
            private String content;
            private int category_type;
            private String id_str;
            private int media_type;
            private int share_count;
            private int type;
            private int status;
            private int has_comments;
            private int user_bury;
            private String status_desc;
            private UserBean user;
            private int user_digg;
            private int online_time;
            private String category_name;
            private boolean category_visible;
            private int bury_count;
            private boolean is_anonymous;
            private int repin_count;
            private int digg_count;
            private int has_hot_comments;
            private int user_repin;
            private ActivityBean activity;
            private long group_id;
            private int category_id;

            public String getText() {
                return text;
            }

            public void setText(String text) {
                this.text = text;
            }

            public int getCreate_time() {
                return create_time;
            }

            public void setCreate_time(int create_time) {
                this.create_time = create_time;
            }

            public long getId() {
                return id;
            }

            public void setId(long id) {
                this.id = id;
            }

            public int getFavorite_count() {
                return favorite_count;
            }

            public void setFavorite_count(int favorite_count) {
                this.favorite_count = favorite_count;
            }

            public int getGo_detail_count() {
                return go_detail_count;
            }

            public void setGo_detail_count(int go_detail_count) {
                this.go_detail_count = go_detail_count;
            }

            public int getUser_favorite() {
                return user_favorite;
            }

            public void setUser_favorite(int user_favorite) {
                this.user_favorite = user_favorite;
            }

            public int getShare_type() {
                return share_type;
            }

            public void setShare_type(int share_type) {
                this.share_type = share_type;
            }

            public int getIs_can_share() {
                return is_can_share;
            }

            public void setIs_can_share(int is_can_share) {
                this.is_can_share = is_can_share;
            }

            public int getComment_count() {
                return comment_count;
            }

            public void setComment_count(int comment_count) {
                this.comment_count = comment_count;
            }

            public String getShare_url() {
                return share_url;
            }

            public void setShare_url(String share_url) {
                this.share_url = share_url;
            }

            public int getLabel() {
                return label;
            }

            public void setLabel(int label) {
                this.label = label;
            }

            public String getContent() {
                return content;
            }

            public void setContent(String content) {
                this.content = content;
            }

            public int getCategory_type() {
                return category_type;
            }

            public void setCategory_type(int category_type) {
                this.category_type = category_type;
            }

            public String getId_str() {
                return id_str;
            }

            public void setId_str(String id_str) {
                this.id_str = id_str;
            }

            public int getMedia_type() {
                return media_type;
            }

            public void setMedia_type(int media_type) {
                this.media_type = media_type;
            }

            public int getShare_count() {
                return share_count;
            }

            public void setShare_count(int share_count) {
                this.share_count = share_count;
            }

            public int getType() {
                return type;
            }

            public void setType(int type) {
                this.type = type;
            }

            public int getStatus() {
                return status;
            }

            public void setStatus(int status) {
                this.status = status;
            }

            public int getHas_comments() {
                return has_comments;
            }

            public void setHas_comments(int has_comments) {
                this.has_comments = has_comments;
            }

            public int getUser_bury() {
                return user_bury;
            }

            public void setUser_bury(int user_bury) {
                this.user_bury = user_bury;
            }

            public String getStatus_desc() {
                return status_desc;
            }

            public void setStatus_desc(String status_desc) {
                this.status_desc = status_desc;
            }

            public UserBean getUser() {
                return user;
            }

            public void setUser(UserBean user) {
                this.user = user;
            }

            public int getUser_digg() {
                return user_digg;
            }

            public void setUser_digg(int user_digg) {
                this.user_digg = user_digg;
            }

            public int getOnline_time() {
                return online_time;
            }

            public void setOnline_time(int online_time) {
                this.online_time = online_time;
            }

            public String getCategory_name() {
                return category_name;
            }

            public void setCategory_name(String category_name) {
                this.category_name = category_name;
            }

            public boolean isCategory_visible() {
                return category_visible;
            }

            public void setCategory_visible(boolean category_visible) {
                this.category_visible = category_visible;
            }

            public int getBury_count() {
                return bury_count;
            }

            public void setBury_count(int bury_count) {
                this.bury_count = bury_count;
            }

            public boolean isIs_anonymous() {
                return is_anonymous;
            }

            public void setIs_anonymous(boolean is_anonymous) {
                this.is_anonymous = is_anonymous;
            }

            public int getRepin_count() {
                return repin_count;
            }

            public void setRepin_count(int repin_count) {
                this.repin_count = repin_count;
            }

            public int getDigg_count() {
                return digg_count;
            }

            public void setDigg_count(int digg_count) {
                this.digg_count = digg_count;
            }

            public int getHas_hot_comments() {
                return has_hot_comments;
            }

            public void setHas_hot_comments(int has_hot_comments) {
                this.has_hot_comments = has_hot_comments;
            }

            public int getUser_repin() {
                return user_repin;
            }

            public void setUser_repin(int user_repin) {
                this.user_repin = user_repin;
            }

            public ActivityBean getActivity() {
                return activity;
            }

            public void setActivity(ActivityBean activity) {
                this.activity = activity;
            }

            public long getGroup_id() {
                return group_id;
            }

            public void setGroup_id(long group_id) {
                this.group_id = group_id;
            }

            public int getCategory_id() {
                return category_id;
            }

            public void setCategory_id(int category_id) {
                this.category_id = category_id;
            }

            public static class UserBean {
                /**
                 * is_following : false
                 * avatar_url : http://p3.pstatp.com/thumb/123200136e8a76748b9f
                 * user_id : 51169444246
                 * name : 进击的穆穆
                 * user_verified : false
                 */

                private boolean is_following;
                private String avatar_url;
                private long user_id;
                private String name;
                private boolean user_verified;

                public boolean isIs_following() {
                    return is_following;
                }

                public void setIs_following(boolean is_following) {
                    this.is_following = is_following;
                }

                public String getAvatar_url() {
                    return avatar_url;
                }

                public void setAvatar_url(String avatar_url) {
                    this.avatar_url = avatar_url;
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

                public boolean isUser_verified() {
                    return user_verified;
                }

                public void setUser_verified(boolean user_verified) {
                    this.user_verified = user_verified;
                }
            }

            public static class ActivityBean {
            }
        }
    }
}
