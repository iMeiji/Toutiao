package com.meiji.toutiao.bean.other.joke;

import java.util.List;

/**
 * Created by Meiji on 2017/1/2.
 */

public class JokeCommentBean {
    /**
     * message : success
     * data : {"comment_pagination":{"count":1,"total_count":121,"url_fmt":"?offset=%s&amp;count=%s","offset":0},"group":{"status":1,"type":3,"id":53404620593,"_id":892139876},"comments":[{"status":1,"user_uid":null,"description":"这个用户很懒，神马都木有写","reply_to_user_name":null,"reply_to_user_verified":null,"text":"抽烟害人害己","target_id":null,"forum_id":0,"reply_count":1,"reply_to_user_id":null,"is_blocked":0,"user_verified":false,"user_followers_count":0,"is_blocking":0,"user_id":5874570453,"bury_count":0,"type":2,"digg_count":769,"id":53409568162,"platform_id":0,"rate_score":0,"create_time":1483093293,"user_name":"今天回家吃晚饭","user_profile_image_url":"http://p9.pstatp.com/thumb/bc3000463d913c1744c"}],"page_display_range":[1,2,3,4,5,6,7],"page":1,"page_size":1,"page_last":121}
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

    public static class DataBean {
        /**
         * comment_pagination : {"count":1,"total_count":121,"url_fmt":"?offset=%s&amp;count=%s","offset":0}
         * group : {"status":1,"type":3,"id":53404620593,"_id":892139876}
         * comments : [{"status":1,"user_uid":null,"description":"这个用户很懒，神马都木有写","reply_to_user_name":null,"reply_to_user_verified":null,"text":"抽烟害人害己","target_id":null,"forum_id":0,"reply_count":1,"reply_to_user_id":null,"is_blocked":0,"user_verified":false,"user_followers_count":0,"is_blocking":0,"user_id":5874570453,"bury_count":0,"type":2,"digg_count":769,"id":53409568162,"platform_id":0,"rate_score":0,"create_time":1483093293,"user_name":"今天回家吃晚饭","user_profile_image_url":"http://p9.pstatp.com/thumb/bc3000463d913c1744c"}]
         * page_display_range : [1,2,3,4,5,6,7]
         * page : 1
         * page_size : 1
         * page_last : 121
         */

        private CommentPaginationBean comment_pagination;
        private GroupBean group;
        private int page;
        private int page_size;
        private int page_last;
        private List<CommentsBean> comments;
        private List<Integer> page_display_range;

        public CommentPaginationBean getComment_pagination() {
            return comment_pagination;
        }

        public void setComment_pagination(CommentPaginationBean comment_pagination) {
            this.comment_pagination = comment_pagination;
        }

        public GroupBean getGroup() {
            return group;
        }

        public void setGroup(GroupBean group) {
            this.group = group;
        }

        public int getPage() {
            return page;
        }

        public void setPage(int page) {
            this.page = page;
        }

        public int getPage_size() {
            return page_size;
        }

        public void setPage_size(int page_size) {
            this.page_size = page_size;
        }

        public int getPage_last() {
            return page_last;
        }

        public void setPage_last(int page_last) {
            this.page_last = page_last;
        }

        public List<CommentsBean> getComments() {
            return comments;
        }

        public void setComments(List<CommentsBean> comments) {
            this.comments = comments;
        }

        public List<Integer> getPage_display_range() {
            return page_display_range;
        }

        public void setPage_display_range(List<Integer> page_display_range) {
            this.page_display_range = page_display_range;
        }

        public static class CommentPaginationBean {
            /**
             * count : 1
             * total_count : 121
             * url_fmt : ?offset=%s&amp;count=%s
             * offset : 0
             */

            private int count;
            private int total_count;
            private String url_fmt;
            private int offset;

            public int getCount() {
                return count;
            }

            public void setCount(int count) {
                this.count = count;
            }

            public int getTotal_count() {
                return total_count;
            }

            public void setTotal_count(int total_count) {
                this.total_count = total_count;
            }

            public String getUrl_fmt() {
                return url_fmt;
            }

            public void setUrl_fmt(String url_fmt) {
                this.url_fmt = url_fmt;
            }

            public int getOffset() {
                return offset;
            }

            public void setOffset(int offset) {
                this.offset = offset;
            }
        }

        public static class GroupBean {
            /**
             * status : 1
             * type : 3
             * id : 53404620593
             * _id : 892139876
             */

            private int status;
            private int type;
            private long id;
            private int _id;

            public int getStatus() {
                return status;
            }

            public void setStatus(int status) {
                this.status = status;
            }

            public int getType() {
                return type;
            }

            public void setType(int type) {
                this.type = type;
            }

            public long getId() {
                return id;
            }

            public void setId(long id) {
                this.id = id;
            }

            public int get_id() {
                return _id;
            }

            public void set_id(int _id) {
                this._id = _id;
            }
        }

        public static class CommentsBean {
            /**
             * status : 1
             * user_uid : null
             * description : 这个用户很懒，神马都木有写
             * reply_to_user_name : null
             * reply_to_user_verified : null
             * text : 抽烟害人害己
             * target_id : null
             * forum_id : 0
             * reply_count : 1
             * reply_to_user_id : null
             * is_blocked : 0
             * user_verified : false
             * user_followers_count : 0
             * is_blocking : 0
             * user_id : 5874570453
             * bury_count : 0
             * type : 2
             * digg_count : 769
             * id : 53409568162
             * platform_id : 0
             * rate_score : 0
             * create_time : 1483093293
             * user_name : 今天回家吃晚饭
             * user_profile_image_url : http://p9.pstatp.com/thumb/bc3000463d913c1744c
             */

            private int status;
            private Object user_uid;
            private String description;
            private Object reply_to_user_name;
            private Object reply_to_user_verified;
            private String text;
            private Object target_id;
            private int forum_id;
            private int reply_count;
            private Object reply_to_user_id;
            private int is_blocked;
            private boolean user_verified;
            private int user_followers_count;
            private int is_blocking;
            private long user_id;
            private int bury_count;
            private int type;
            private int digg_count;
            private long id;
            private int platform_id;
            private int rate_score;
            private int create_time;
            private String user_name;
            private String user_profile_image_url;

            public int getStatus() {
                return status;
            }

            public void setStatus(int status) {
                this.status = status;
            }

            public Object getUser_uid() {
                return user_uid;
            }

            public void setUser_uid(Object user_uid) {
                this.user_uid = user_uid;
            }

            public String getDescription() {
                return description;
            }

            public void setDescription(String description) {
                this.description = description;
            }

            public Object getReply_to_user_name() {
                return reply_to_user_name;
            }

            public void setReply_to_user_name(Object reply_to_user_name) {
                this.reply_to_user_name = reply_to_user_name;
            }

            public Object getReply_to_user_verified() {
                return reply_to_user_verified;
            }

            public void setReply_to_user_verified(Object reply_to_user_verified) {
                this.reply_to_user_verified = reply_to_user_verified;
            }

            public String getText() {
                return text;
            }

            public void setText(String text) {
                this.text = text;
            }

            public Object getTarget_id() {
                return target_id;
            }

            public void setTarget_id(Object target_id) {
                this.target_id = target_id;
            }

            public int getForum_id() {
                return forum_id;
            }

            public void setForum_id(int forum_id) {
                this.forum_id = forum_id;
            }

            public int getReply_count() {
                return reply_count;
            }

            public void setReply_count(int reply_count) {
                this.reply_count = reply_count;
            }

            public Object getReply_to_user_id() {
                return reply_to_user_id;
            }

            public void setReply_to_user_id(Object reply_to_user_id) {
                this.reply_to_user_id = reply_to_user_id;
            }

            public int getIs_blocked() {
                return is_blocked;
            }

            public void setIs_blocked(int is_blocked) {
                this.is_blocked = is_blocked;
            }

            public boolean isUser_verified() {
                return user_verified;
            }

            public void setUser_verified(boolean user_verified) {
                this.user_verified = user_verified;
            }

            public int getUser_followers_count() {
                return user_followers_count;
            }

            public void setUser_followers_count(int user_followers_count) {
                this.user_followers_count = user_followers_count;
            }

            public int getIs_blocking() {
                return is_blocking;
            }

            public void setIs_blocking(int is_blocking) {
                this.is_blocking = is_blocking;
            }

            public long getUser_id() {
                return user_id;
            }

            public void setUser_id(long user_id) {
                this.user_id = user_id;
            }

            public int getBury_count() {
                return bury_count;
            }

            public void setBury_count(int bury_count) {
                this.bury_count = bury_count;
            }

            public int getType() {
                return type;
            }

            public void setType(int type) {
                this.type = type;
            }

            public int getDigg_count() {
                return digg_count;
            }

            public void setDigg_count(int digg_count) {
                this.digg_count = digg_count;
            }

            public long getId() {
                return id;
            }

            public void setId(long id) {
                this.id = id;
            }

            public int getPlatform_id() {
                return platform_id;
            }

            public void setPlatform_id(int platform_id) {
                this.platform_id = platform_id;
            }

            public int getRate_score() {
                return rate_score;
            }

            public void setRate_score(int rate_score) {
                this.rate_score = rate_score;
            }

            public int getCreate_time() {
                return create_time;
            }

            public void setCreate_time(int create_time) {
                this.create_time = create_time;
            }

            public String getUser_name() {
                return user_name;
            }

            public void setUser_name(String user_name) {
                this.user_name = user_name;
            }

            public String getUser_profile_image_url() {
                return user_profile_image_url;
            }

            public void setUser_profile_image_url(String user_profile_image_url) {
                this.user_profile_image_url = user_profile_image_url;
            }
        }
    }
}
