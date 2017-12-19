package com.meiji.toutiao.bean.media;

import java.util.List;

/**
 * Created by Meiji on 2017/7/4.
 */

public class MediaWendaBean {

    /**
     * cursor : 6428403867490189569
     * err_no : 0
     * err_tips :
     * api_param : {"origin_from":"out_wenda","enter_from":"out_wenda"}
     * has_more : 1
     * login_user : {}
     * total : 185
     * user_data : {"all_brow_cnt_str":"","is_verify":0,"curretn_month_digg_cnt_str":"本月共0人点赞","all_digg_cnt_str":"","current_month_brow_cnt":"0","youzhi_info":[],"laomo_info":[],"uname":"","all_brow_cnt":"0","is_valid":0,"current_month_brow_cnt_str":"本月共0人浏览","user_intro":"","current_month_digg_cnt":"0","all_digg_cnt":"0","user_profile_image_url":"","ming_ren_tang":"","schema":""}
     * can_ask : false
     */

    private String cursor;
    private int err_no;
    private String err_tips;
    //    private boolean has_more;
    private int total;
    private boolean can_ask;
    private List<AnswerQuestionBean> answer_question;

    public String getCursor() {
        return cursor;
    }

    public void setCursor(String cursor) {
        this.cursor = cursor;
    }

    public int getErr_no() {
        return err_no;
    }

    public void setErr_no(int err_no) {
        this.err_no = err_no;
    }

    public String getErr_tips() {
        return err_tips;
    }

    public void setErr_tips(String err_tips) {
        this.err_tips = err_tips;
    }

//    public boolean getHas_more() {
//        return has_more;
//    }

//    public void setHas_more(boolean has_more) {
//        this.has_more = has_more;
//    }

    public int getTotal() {
        return total;
    }

    public void setTotal(int total) {
        this.total = total;
    }

    public boolean isCan_ask() {
        return can_ask;
    }

    public void setCan_ask(boolean can_ask) {
        this.can_ask = can_ask;
    }

    public List<AnswerQuestionBean> getAnswer_question() {
        return answer_question;
    }

    public void setAnswer_question(List<AnswerQuestionBean> answer_question) {
        this.answer_question = answer_question;
    }

    public static class AnswerQuestionBean {
        /**
         */

        private AnswerBean answer;
        private QuestionBean question;

        public AnswerBean getAnswer() {
            return answer;
        }

        public void setAnswer(AnswerBean answer) {
            this.answer = answer;
        }

        public QuestionBean getQuestion() {
            return question;
        }

        public void setQuestion(QuestionBean question) {
            this.question = question;
        }

        public static class AnswerBean {
            /**
             * show_time : 2017.06.22
             * content_abstract : {"text":"这个问题的发问，还是围绕在卡上面。如果证明身份的方式不在是这张卡，而是你的眼角膜，或是指纹，那么所有录入的信息就可以通过你证明的物件来提取了，还要看整个社会在一个什么高度。如果一卡在手，那如果遗失了，就很麻烦了。卡的合并，那么这个办卡的部门也需要合并，这个是个大工程了。如果身份证，是你身体的一部分，那么，什么都简单了。","thumb_image_list":[{"url":"http://p3.pstatp.com/list/r555/289d0019a74b9a89ddfe","url_list":[{"url":"http://p3.pstatp.com/list/r555/289d0019a74b9a89ddfe"},{"url":"http://pb9.pstatp.com/list/r555/289d0019a74b9a89ddfe"},{"url":"http://pb3.pstatp.com/list/r555/289d0019a74b9a89ddfe"}],"uri":"list/r555/289d0019a74b9a89ddfe","height":555,"width":354,"type":1}],"large_image_list":[{"url":"http://p3.pstatp.com/large/289d0019a74b9a89ddfe","url_list":[{"url":"http://p3.pstatp.com/large/289d0019a74b9a89ddfe"},{"url":"http://pb9.pstatp.com/large/289d0019a74b9a89ddfe"},{"url":"http://pb3.pstatp.com/large/289d0019a74b9a89ddfe"}],"uri":"large/289d0019a74b9a89ddfe","height":555,"width":354,"type":1}]}
             * user : {"uname":"神勇依旧","avatar_url":"http://p3.pstatp.com/thumb/1a6a000adbc3a1f0a4c5","user_id":"6619635172","is_verify":0,"create_time":1464350006,"user_intro":"","user_auth_info":"","schema":"sslocal://profile?uid=6619635172&refer=wenda"}
             * ans_url : https://ic.snssdk.com/wenda/v1/wapanswer/content/?ansid=6434402977758314753
             * ansid : 6434402977758314753
             * is_show_bury : false
             * wap_url : https://wenda.toutiao.com/m/wapshare/answer/brow/?ansid=6434402977758314753&
             * is_buryed : false
             * bury_count : 0
             * title :
             * is_delete : 0
             * digg_count : 0
             * content : <p><p>这个问题的发问，还是围绕在卡上面。如果证明身份的方式不在是这张卡，而是你的眼角膜，或是指纹，那么所有录入的信息就可以通过你证明的物件来提取了，还要看整个社会在一个什么高度。如果一卡在手，那如果遗失了，就很麻烦了。卡的合并，那么这个办卡的部门也需要合并，这个是个大工程了。如果身份证，是你身体的一部分，那么，什么都简单了。</p><br/><img src="http://p3.pstatp.com/origin/289d0019a74b9a89ddfe" img_width="354" img_height="555" onerror="javascript:errorimg.call(this);" ></p>
             * brow_count : 18
             * is_digg : false
             * schema : sslocal://wenda_detail?ansid=6434402977758314753
             */

            private String show_time;
            private ContentAbstractBean content_abstract;
            private UserBean user;
            private String ans_url;
            private String ansid;
            private boolean is_show_bury;
            private String wap_url;
            private boolean is_buryed;
            private int bury_count;
            private String title;
            private int is_delete;
            private int digg_count;
            private String content;
            private int brow_count;
            private boolean is_digg;
            private String schema;

            public String getShow_time() {
                return show_time;
            }

            public void setShow_time(String show_time) {
                this.show_time = show_time;
            }

            public ContentAbstractBean getContent_abstract() {
                return content_abstract;
            }

            public void setContent_abstract(ContentAbstractBean content_abstract) {
                this.content_abstract = content_abstract;
            }

            public UserBean getUser() {
                return user;
            }

            public void setUser(UserBean user) {
                this.user = user;
            }

            public String getAns_url() {
                return ans_url;
            }

            public void setAns_url(String ans_url) {
                this.ans_url = ans_url;
            }

            public String getAnsid() {
                return ansid;
            }

            public void setAnsid(String ansid) {
                this.ansid = ansid;
            }

            public boolean isIs_show_bury() {
                return is_show_bury;
            }

            public void setIs_show_bury(boolean is_show_bury) {
                this.is_show_bury = is_show_bury;
            }

            public String getWap_url() {
                return wap_url;
            }

            public void setWap_url(String wap_url) {
                this.wap_url = wap_url;
            }

            public boolean isIs_buryed() {
                return is_buryed;
            }

            public void setIs_buryed(boolean is_buryed) {
                this.is_buryed = is_buryed;
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

            public int getIs_delete() {
                return is_delete;
            }

            public void setIs_delete(int is_delete) {
                this.is_delete = is_delete;
            }

            public int getDigg_count() {
                return digg_count;
            }

            public void setDigg_count(int digg_count) {
                this.digg_count = digg_count;
            }

            public String getContent() {
                return content;
            }

            public void setContent(String content) {
                this.content = content;
            }

            public int getBrow_count() {
                return brow_count;
            }

            public void setBrow_count(int brow_count) {
                this.brow_count = brow_count;
            }

            public boolean isIs_digg() {
                return is_digg;
            }

            public void setIs_digg(boolean is_digg) {
                this.is_digg = is_digg;
            }

            public String getSchema() {
                return schema;
            }

            public void setSchema(String schema) {
                this.schema = schema;
            }

            public static class ContentAbstractBean {
                /**
                 * text : 这个问题的发问，还是围绕在卡上面。如果证明身份的方式不在是这张卡，而是你的眼角膜，或是指纹，那么所有录入的信息就可以通过你证明的物件来提取了，还要看整个社会在一个什么高度。如果一卡在手，那如果遗失了，就很麻烦了。卡的合并，那么这个办卡的部门也需要合并，这个是个大工程了。如果身份证，是你身体的一部分，那么，什么都简单了。
                 * thumb_image_list : [{"url":"http://p3.pstatp.com/list/r555/289d0019a74b9a89ddfe","url_list":[{"url":"http://p3.pstatp.com/list/r555/289d0019a74b9a89ddfe"},{"url":"http://pb9.pstatp.com/list/r555/289d0019a74b9a89ddfe"},{"url":"http://pb3.pstatp.com/list/r555/289d0019a74b9a89ddfe"}],"uri":"list/r555/289d0019a74b9a89ddfe","height":555,"width":354,"type":1}]
                 * large_image_list : [{"url":"http://p3.pstatp.com/large/289d0019a74b9a89ddfe","url_list":[{"url":"http://p3.pstatp.com/large/289d0019a74b9a89ddfe"},{"url":"http://pb9.pstatp.com/large/289d0019a74b9a89ddfe"},{"url":"http://pb3.pstatp.com/large/289d0019a74b9a89ddfe"}],"uri":"large/289d0019a74b9a89ddfe","height":555,"width":354,"type":1}]
                 */

                private String text;
                private List<ThumbImageListBean> thumb_image_list;
                private List<LargeImageListBean> large_image_list;

                public String getText() {
                    return text;
                }

                public void setText(String text) {
                    this.text = text;
                }

                public List<ThumbImageListBean> getThumb_image_list() {
                    return thumb_image_list;
                }

                public void setThumb_image_list(List<ThumbImageListBean> thumb_image_list) {
                    this.thumb_image_list = thumb_image_list;
                }

                public List<LargeImageListBean> getLarge_image_list() {
                    return large_image_list;
                }

                public void setLarge_image_list(List<LargeImageListBean> large_image_list) {
                    this.large_image_list = large_image_list;
                }

                public static class ThumbImageListBean {
                    /**
                     * url : http://p3.pstatp.com/list/r555/289d0019a74b9a89ddfe
                     * url_list : [{"url":"http://p3.pstatp.com/list/r555/289d0019a74b9a89ddfe"},{"url":"http://pb9.pstatp.com/list/r555/289d0019a74b9a89ddfe"},{"url":"http://pb3.pstatp.com/list/r555/289d0019a74b9a89ddfe"}]
                     * uri : list/r555/289d0019a74b9a89ddfe
                     * height : 555
                     * width : 354
                     * type : 1
                     */

                    private String url;
                    private String uri;
                    private int height;
                    private int width;
                    private int type;
                    private List<UrlListBean> url_list;

                    public String getUrl() {
                        return url;
                    }

                    public void setUrl(String url) {
                        this.url = url;
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

                    public int getWidth() {
                        return width;
                    }

                    public void setWidth(int width) {
                        this.width = width;
                    }

                    public int getType() {
                        return type;
                    }

                    public void setType(int type) {
                        this.type = type;
                    }

                    public List<UrlListBean> getUrl_list() {
                        return url_list;
                    }

                    public void setUrl_list(List<UrlListBean> url_list) {
                        this.url_list = url_list;
                    }

                    public static class UrlListBean {
                        /**
                         * url : http://p3.pstatp.com/list/r555/289d0019a74b9a89ddfe
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

                public static class LargeImageListBean {
                    /**
                     * url : http://p3.pstatp.com/large/289d0019a74b9a89ddfe
                     * url_list : [{"url":"http://p3.pstatp.com/large/289d0019a74b9a89ddfe"},{"url":"http://pb9.pstatp.com/large/289d0019a74b9a89ddfe"},{"url":"http://pb3.pstatp.com/large/289d0019a74b9a89ddfe"}]
                     * uri : large/289d0019a74b9a89ddfe
                     * height : 555
                     * width : 354
                     * type : 1
                     */

                    private String url;
                    private String uri;
                    private int height;
                    private int width;
                    private int type;
                    private List<UrlListBeanX> url_list;

                    public String getUrl() {
                        return url;
                    }

                    public void setUrl(String url) {
                        this.url = url;
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

                    public int getWidth() {
                        return width;
                    }

                    public void setWidth(int width) {
                        this.width = width;
                    }

                    public int getType() {
                        return type;
                    }

                    public void setType(int type) {
                        this.type = type;
                    }

                    public List<UrlListBeanX> getUrl_list() {
                        return url_list;
                    }

                    public void setUrl_list(List<UrlListBeanX> url_list) {
                        this.url_list = url_list;
                    }

                    public static class UrlListBeanX {
                        /**
                         * url : http://p3.pstatp.com/large/289d0019a74b9a89ddfe
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

            public static class UserBean {
                /**
                 * uname : 神勇依旧
                 * avatar_url : http://p3.pstatp.com/thumb/1a6a000adbc3a1f0a4c5
                 * user_id : 6619635172
                 * is_verify : 0
                 * create_time : 1464350006
                 * user_intro :
                 * user_auth_info :
                 * schema : sslocal://profile?uid=6619635172&refer=wenda
                 */

                private String uname;
                private String avatar_url;
                private String user_id;
                private int is_verify;
                private int create_time;
                private String user_intro;
                private String user_auth_info;
                private String schema;

                public String getUname() {
                    return uname;
                }

                public void setUname(String uname) {
                    this.uname = uname;
                }

                public String getAvatar_url() {
                    return avatar_url;
                }

                public void setAvatar_url(String avatar_url) {
                    this.avatar_url = avatar_url;
                }

                public String getUser_id() {
                    return user_id;
                }

                public void setUser_id(String user_id) {
                    this.user_id = user_id;
                }

                public int getIs_verify() {
                    return is_verify;
                }

                public void setIs_verify(int is_verify) {
                    this.is_verify = is_verify;
                }

                public int getCreate_time() {
                    return create_time;
                }

                public void setCreate_time(int create_time) {
                    this.create_time = create_time;
                }

                public String getUser_intro() {
                    return user_intro;
                }

                public void setUser_intro(String user_intro) {
                    this.user_intro = user_intro;
                }

                public String getUser_auth_info() {
                    return user_auth_info;
                }

                public void setUser_auth_info(String user_auth_info) {
                    this.user_auth_info = user_auth_info;
                }

                public String getSchema() {
                    return schema;
                }

                public void setSchema(String schema) {
                    this.schema = schema;
                }
            }
        }

        public static class QuestionBean {
            /**
             * content : {"text":"大家觉得《身份证》实现驾驶证、医保卡、银行卡、公交卡等一卡多用怎么样？外观变化不大，但是针对不同的刷卡或扫描就可以实现其他卡的功能。可以设置密码、指纹识别等功能是不是就更方便快捷！","pic_uri_list":[{"width":640,"type":"1","web_uri":"289e00197d4a3ff1c255","height":398}],"thumb_image_list":[{"url":"http://p3.pstatp.com/list/r640/289e00197d4a3ff1c255","url_list":[{"url":"http://p3.pstatp.com/list/r640/289e00197d4a3ff1c255"},{"url":"http://pb9.pstatp.com/list/r640/289e00197d4a3ff1c255"},{"url":"http://pb3.pstatp.com/list/r640/289e00197d4a3ff1c255"}],"uri":"289e00197d4a3ff1c255","height":398,"width":640,"type":1}],"large_image_list":[{"url":"http://p3.pstatp.com/large/289e00197d4a3ff1c255","url_list":[{"url":"http://p3.pstatp.com/large/289e00197d4a3ff1c255"},{"url":"http://pb9.pstatp.com/large/289e00197d4a3ff1c255"},{"url":"http://pb3.pstatp.com/large/289e00197d4a3ff1c255"}],"uri":"289e00197d4a3ff1c255","height":398,"width":640,"type":1}]}
             * tag_name :
             * create_time : 1498091836
             * normal_ans_count : 11
             * user : {"uname":"霸鱼1982","avatar_url":"http://p3.pstatp.com/thumb/307/3518148306","user_id":"1959892080","is_verify":0,"create_time":1375854608,"user_intro":"","user_auth_info":"","schema":"sslocal://profile?uid=1959892080&refer=wenda"}
             * title : 大家觉得身份证实现驾驶证、医保卡、银行卡、公交卡等一卡多用怎么样？
             * qid : 6434255442293031169
             * nice_ans_count : 7
             * tag_id : 0
             * fold_reason : {"open_url":"sslocal://detail?groupid=6293724675596402946","title":"为什么折叠？"}
             */

            private ContentBean content;
            private String tag_name;
            private int create_time;
            private int normal_ans_count;
            private UserBeanX user;
            private String title;
            private String qid;
            private int nice_ans_count;
            private int tag_id;
            private FoldReasonBean fold_reason;

            @Override
            public boolean equals(Object o) {
                if (this == o)
                    return true;
                if (o == null || getClass() != o.getClass())
                    return false;

                QuestionBean that = (QuestionBean) o;

                if (!title.equals(that.title))
                    return false;
                return qid.equals(that.qid);
            }

            @Override
            public int hashCode() {
                int result = title.hashCode();
                result = 31 * result + qid.hashCode();
                return result;
            }

            public ContentBean getContent() {
                return content;
            }

            public void setContent(ContentBean content) {
                this.content = content;
            }

            public String getTag_name() {
                return tag_name;
            }

            public void setTag_name(String tag_name) {
                this.tag_name = tag_name;
            }

            public int getCreate_time() {
                return create_time;
            }

            public void setCreate_time(int create_time) {
                this.create_time = create_time;
            }

            public int getNormal_ans_count() {
                return normal_ans_count;
            }

            public void setNormal_ans_count(int normal_ans_count) {
                this.normal_ans_count = normal_ans_count;
            }

            public UserBeanX getUser() {
                return user;
            }

            public void setUser(UserBeanX user) {
                this.user = user;
            }

            public String getTitle() {
                return title;
            }

            public void setTitle(String title) {
                this.title = title;
            }

            public String getQid() {
                return qid;
            }

            public void setQid(String qid) {
                this.qid = qid;
            }

            public int getNice_ans_count() {
                return nice_ans_count;
            }

            public void setNice_ans_count(int nice_ans_count) {
                this.nice_ans_count = nice_ans_count;
            }

            public int getTag_id() {
                return tag_id;
            }

            public void setTag_id(int tag_id) {
                this.tag_id = tag_id;
            }

            public FoldReasonBean getFold_reason() {
                return fold_reason;
            }

            public void setFold_reason(FoldReasonBean fold_reason) {
                this.fold_reason = fold_reason;
            }

            public static class ContentBean {
                /**
                 * text : 大家觉得《身份证》实现驾驶证、医保卡、银行卡、公交卡等一卡多用怎么样？外观变化不大，但是针对不同的刷卡或扫描就可以实现其他卡的功能。可以设置密码、指纹识别等功能是不是就更方便快捷！
                 * pic_uri_list : [{"width":640,"type":"1","web_uri":"289e00197d4a3ff1c255","height":398}]
                 * thumb_image_list : [{"url":"http://p3.pstatp.com/list/r640/289e00197d4a3ff1c255","url_list":[{"url":"http://p3.pstatp.com/list/r640/289e00197d4a3ff1c255"},{"url":"http://pb9.pstatp.com/list/r640/289e00197d4a3ff1c255"},{"url":"http://pb3.pstatp.com/list/r640/289e00197d4a3ff1c255"}],"uri":"289e00197d4a3ff1c255","height":398,"width":640,"type":1}]
                 * large_image_list : [{"url":"http://p3.pstatp.com/large/289e00197d4a3ff1c255","url_list":[{"url":"http://p3.pstatp.com/large/289e00197d4a3ff1c255"},{"url":"http://pb9.pstatp.com/large/289e00197d4a3ff1c255"},{"url":"http://pb3.pstatp.com/large/289e00197d4a3ff1c255"}],"uri":"289e00197d4a3ff1c255","height":398,"width":640,"type":1}]
                 */

                private String text;
                private List<PicUriListBean> pic_uri_list;
                private List<ThumbImageListBeanX> thumb_image_list;
                private List<LargeImageListBeanX> large_image_list;

                public String getText() {
                    return text;
                }

                public void setText(String text) {
                    this.text = text;
                }

                public List<PicUriListBean> getPic_uri_list() {
                    return pic_uri_list;
                }

                public void setPic_uri_list(List<PicUriListBean> pic_uri_list) {
                    this.pic_uri_list = pic_uri_list;
                }

                public List<ThumbImageListBeanX> getThumb_image_list() {
                    return thumb_image_list;
                }

                public void setThumb_image_list(List<ThumbImageListBeanX> thumb_image_list) {
                    this.thumb_image_list = thumb_image_list;
                }

                public List<LargeImageListBeanX> getLarge_image_list() {
                    return large_image_list;
                }

                public void setLarge_image_list(List<LargeImageListBeanX> large_image_list) {
                    this.large_image_list = large_image_list;
                }

                public static class PicUriListBean {
                    /**
                     * width : 640
                     * type : 1
                     * web_uri : 289e00197d4a3ff1c255
                     * height : 398
                     */

                    private int width;
                    private String type;
                    private String web_uri;
                    private int height;

                    public int getWidth() {
                        return width;
                    }

                    public void setWidth(int width) {
                        this.width = width;
                    }

                    public String getType() {
                        return type;
                    }

                    public void setType(String type) {
                        this.type = type;
                    }

                    public String getWeb_uri() {
                        return web_uri;
                    }

                    public void setWeb_uri(String web_uri) {
                        this.web_uri = web_uri;
                    }

                    public int getHeight() {
                        return height;
                    }

                    public void setHeight(int height) {
                        this.height = height;
                    }
                }

                public static class ThumbImageListBeanX {
                    /**
                     * url : http://p3.pstatp.com/list/r640/289e00197d4a3ff1c255
                     * url_list : [{"url":"http://p3.pstatp.com/list/r640/289e00197d4a3ff1c255"},{"url":"http://pb9.pstatp.com/list/r640/289e00197d4a3ff1c255"},{"url":"http://pb3.pstatp.com/list/r640/289e00197d4a3ff1c255"}]
                     * uri : 289e00197d4a3ff1c255
                     * height : 398
                     * width : 640
                     * type : 1
                     */

                    private String url;
                    private String uri;
                    private int height;
                    private int width;
                    private int type;
                    private List<UrlListBeanXX> url_list;

                    public String getUrl() {
                        return url;
                    }

                    public void setUrl(String url) {
                        this.url = url;
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

                    public int getWidth() {
                        return width;
                    }

                    public void setWidth(int width) {
                        this.width = width;
                    }

                    public int getType() {
                        return type;
                    }

                    public void setType(int type) {
                        this.type = type;
                    }

                    public List<UrlListBeanXX> getUrl_list() {
                        return url_list;
                    }

                    public void setUrl_list(List<UrlListBeanXX> url_list) {
                        this.url_list = url_list;
                    }

                    public static class UrlListBeanXX {
                        /**
                         * url : http://p3.pstatp.com/list/r640/289e00197d4a3ff1c255
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

                public static class LargeImageListBeanX {
                    /**
                     * url : http://p3.pstatp.com/large/289e00197d4a3ff1c255
                     * url_list : [{"url":"http://p3.pstatp.com/large/289e00197d4a3ff1c255"},{"url":"http://pb9.pstatp.com/large/289e00197d4a3ff1c255"},{"url":"http://pb3.pstatp.com/large/289e00197d4a3ff1c255"}]
                     * uri : 289e00197d4a3ff1c255
                     * height : 398
                     * width : 640
                     * type : 1
                     */

                    private String url;
                    private String uri;
                    private int height;
                    private int width;
                    private int type;
                    private List<UrlListBeanXXX> url_list;

                    public String getUrl() {
                        return url;
                    }

                    public void setUrl(String url) {
                        this.url = url;
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

                    public int getWidth() {
                        return width;
                    }

                    public void setWidth(int width) {
                        this.width = width;
                    }

                    public int getType() {
                        return type;
                    }

                    public void setType(int type) {
                        this.type = type;
                    }

                    public List<UrlListBeanXXX> getUrl_list() {
                        return url_list;
                    }

                    public void setUrl_list(List<UrlListBeanXXX> url_list) {
                        this.url_list = url_list;
                    }

                    public static class UrlListBeanXXX {
                        /**
                         * url : http://p3.pstatp.com/large/289e00197d4a3ff1c255
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

            public static class UserBeanX {
                /**
                 * uname : 霸鱼1982
                 * avatar_url : http://p3.pstatp.com/thumb/307/3518148306
                 * user_id : 1959892080
                 * is_verify : 0
                 * create_time : 1375854608
                 * user_intro :
                 * user_auth_info :
                 * schema : sslocal://profile?uid=1959892080&refer=wenda
                 */

                private String uname;
                private String avatar_url;
                private String user_id;
                private int is_verify;
                private int create_time;
                private String user_intro;
                private String user_auth_info;
                private String schema;

                public String getUname() {
                    return uname;
                }

                public void setUname(String uname) {
                    this.uname = uname;
                }

                public String getAvatar_url() {
                    return avatar_url;
                }

                public void setAvatar_url(String avatar_url) {
                    this.avatar_url = avatar_url;
                }

                public String getUser_id() {
                    return user_id;
                }

                public void setUser_id(String user_id) {
                    this.user_id = user_id;
                }

                public int getIs_verify() {
                    return is_verify;
                }

                public void setIs_verify(int is_verify) {
                    this.is_verify = is_verify;
                }

                public int getCreate_time() {
                    return create_time;
                }

                public void setCreate_time(int create_time) {
                    this.create_time = create_time;
                }

                public String getUser_intro() {
                    return user_intro;
                }

                public void setUser_intro(String user_intro) {
                    this.user_intro = user_intro;
                }

                public String getUser_auth_info() {
                    return user_auth_info;
                }

                public void setUser_auth_info(String user_auth_info) {
                    this.user_auth_info = user_auth_info;
                }

                public String getSchema() {
                    return schema;
                }

                public void setSchema(String schema) {
                    this.schema = schema;
                }
            }

            public static class FoldReasonBean {
                /**
                 * open_url : sslocal://detail?groupid=6293724675596402946
                 * title : 为什么折叠？
                 */

                private String open_url;
                private String title;

                public String getOpen_url() {
                    return open_url;
                }

                public void setOpen_url(String open_url) {
                    this.open_url = open_url;
                }

                public String getTitle() {
                    return title;
                }

                public void setTitle(String title) {
                    this.title = title;
                }
            }
        }
    }
}
