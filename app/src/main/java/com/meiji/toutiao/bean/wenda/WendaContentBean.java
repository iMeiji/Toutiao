package com.meiji.toutiao.bean.wenda;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.List;

/**
 * Created by Meiji on 2017/5/22.
 */

public class WendaContentBean {

    /**
     * show_format : {"show_module":1,"font_size":"18","answer_full_context_color":0}
     * err_tips :
     * err_no : 0
     * offset : 10
     * candidate_invite_user : []
     * module_list : [{"day_icon_url":"http://p3.pstatp.com/origin/1bf50001abbc1c7f8dba","text":"更多问答","icon_type":2,"night_icon_url":"http://p3.pstatp.com/origin/1bf40001abebc0717135","schema":"sslocal://feed?category=question_and_answer&concern_id=6260258266329123329&type=4&name=%E9%97%AE%E7%AD%94&api_param=%7B%22source%22%3A%22question_brow%22%2C%22origin_from%22%3Anull%2C%22enter_from%22%3Anull%7D"}]
     * has_more : true
     * channel_data : {"open_url":"sslocal://webview?url=https%3A%2F%2Fic.snssdk.com%2Fwenda%2Fv1%2Fwaphome%2Fbrow%2F%3Frecommend_from%3Drecommend_question_brow&title=%E5%A4%B4%E6%9D%A1%E9%97%AE%E7%AD%94","text":"关注问答频道，聊天更有谈资！","pos":3,"button_text":"进入","recommend_image":{"url":"https://p.pstatp.com/origin/159f000460df3e3f850c","url_list":[{"url":"http://p3.pstatp.com/list/r90/13530005a010f7ce835d"},{"url":"http://pb9.pstatp.com/list/r90/13530005a010f7ce835d"},{"url":"http://pb3.pstatp.com/list/r90/13530005a010f7ce835d"}],"uri":"list/r90/13530005a010f7ce835d","height":90,"width":90,"type":1},"type":1}
     * question : {"concern_tag_list":[{"concern_id":"6213182495320443393","name":"火车","schema":"sslocal://concern?tab_sname=wenda&api_param=%7B%22wenda_api_param%22%3A%7B%22scope%22%3A%22toutiao_wenda%22%2C%22origin_from%22%3A%22click_headline%22%2C%22parent_enter_from%22%3A%22click_headline%22%2C%22enter_from%22%3A%22question%22%7D%7D&cid=6213182495320443393"}],"can_delete":false,"post_answer_url":"sslocal://wenda_post?qid=6420544946419269889&gd_ext_json=%7B%22enter_type%22%3A%22question_and_answer%22%2C%22ansid%22%3A6422088403512197378%7D&qTitle=%E5%8D%B0%E5%BA%A6%E6%9C%80%E5%BF%AB%E7%9A%84%E5%88%97%E8%BD%A6%E6%97%B6%E9%80%9F160%EF%BC%8C%E5%BD%93%E5%9C%B0%E4%BA%BA%E9%97%AE%E2%80%9C%E4%B8%AD%E5%9B%BD%E7%81%AB%E8%BD%A6%E6%9C%89%E8%BF%99%E4%B9%88%E5%BF%AB%E5%90%97%E2%80%9D%EF%BC%8C%E6%80%8E%E4%B9%88%E5%9B%9E%E7%AD%94%EF%BC%9F","is_follow":false,"nice_ans_count":73,"create_time":1494899612,"normal_ans_count":851,"user":{"user_intro":"","uname":"yuejiao19926","avatar_url":"http://p0.pstatp.com/origin/3795/3033762272","user_id":"6796383301","is_verify":0},"share_data":{"content":"非常推荐！","image_url":"http://p0.pstatp.com/medium/6399/2275149767","share_url":"https://wenda.toutiao.com/m/wapshare/question/brow/?qid=6420544946419269889&","title":"头条问答-印度最快的列车时速160，当地人问\u201c中国火车有这么快吗\u201d，怎么回答？(924个回答)"},"can_edit":false,"show_delete":false,"title":"印度最快的列车时速160，当地人问\u201c中国火车有这么快吗\u201d，怎么回答？","follow_count":497,"content":{"text":"\n","thumb_image_list":[{"url":"http://p9.pstatp.com/list/r640/1dcd000e3ba14e6e61f8","url_list":[{"url":"http://p9.pstatp.com/list/r640/1dcd000e3ba14e6e61f8"},{"url":"http://pb1.pstatp.com/list/r640/1dcd000e3ba14e6e61f8"},{"url":"http://pb3.pstatp.com/list/r640/1dcd000e3ba14e6e61f8"}],"uri":"1dcd000e3ba14e6e61f8","height":379,"width":640,"type":1}],"large_image_list":[{"url":"http://p9.pstatp.com/large/1dcd000e3ba14e6e61f8","url_list":[{"url":"http://p9.pstatp.com/large/1dcd000e3ba14e6e61f8"},{"url":"http://pb1.pstatp.com/large/1dcd000e3ba14e6e61f8"},{"url":"http://pb3.pstatp.com/large/1dcd000e3ba14e6e61f8"}],"uri":"1dcd000e3ba14e6e61f8","height":379,"width":640,"type":1}]},"show_edit":false,"qid":"6420544946419269889","fold_reason":{"open_url":"sslocal://detail?groupid=6293724675596402946","title":"为什么折叠？"}}
     * module_count : 1
     * question_type : 0
     * api_param : {"origin_from": null, "enter_from": null}
     * question_header_content_fold_max_count : 1
     */

    private ShowFormatBean show_format;
    private String err_tips;
    private int err_no;
    private int offset;
    private boolean has_more;
    private ChannelDataBean channel_data;
    private QuestionBean question;
    private int module_count;
    private int question_type;
    private String api_param;
    private int question_header_content_fold_max_count;
    private List<?> candidate_invite_user;
    private List<ModuleListBean> module_list;
    private List<AnsListBean> ans_list;

    public ShowFormatBean getShow_format() {
        return show_format;
    }

    public void setShow_format(ShowFormatBean show_format) {
        this.show_format = show_format;
    }

    public String getErr_tips() {
        return err_tips;
    }

    public void setErr_tips(String err_tips) {
        this.err_tips = err_tips;
    }

    public int getErr_no() {
        return err_no;
    }

    public void setErr_no(int err_no) {
        this.err_no = err_no;
    }

    public int getOffset() {
        return offset;
    }

    public void setOffset(int offset) {
        this.offset = offset;
    }

    public boolean isHas_more() {
        return has_more;
    }

    public void setHas_more(boolean has_more) {
        this.has_more = has_more;
    }

    public ChannelDataBean getChannel_data() {
        return channel_data;
    }

    public void setChannel_data(ChannelDataBean channel_data) {
        this.channel_data = channel_data;
    }

    public QuestionBean getQuestion() {
        return question;
    }

    public void setQuestion(QuestionBean question) {
        this.question = question;
    }

    public int getModule_count() {
        return module_count;
    }

    public void setModule_count(int module_count) {
        this.module_count = module_count;
    }

    public int getQuestion_type() {
        return question_type;
    }

    public void setQuestion_type(int question_type) {
        this.question_type = question_type;
    }

    public String getApi_param() {
        return api_param;
    }

    public void setApi_param(String api_param) {
        this.api_param = api_param;
    }

    public int getQuestion_header_content_fold_max_count() {
        return question_header_content_fold_max_count;
    }

    public void setQuestion_header_content_fold_max_count(int question_header_content_fold_max_count) {
        this.question_header_content_fold_max_count = question_header_content_fold_max_count;
    }

    public List<?> getCandidate_invite_user() {
        return candidate_invite_user;
    }

    public void setCandidate_invite_user(List<?> candidate_invite_user) {
        this.candidate_invite_user = candidate_invite_user;
    }

    public List<ModuleListBean> getModule_list() {
        return module_list;
    }

    public void setModule_list(List<ModuleListBean> module_list) {
        this.module_list = module_list;
    }

    public List<AnsListBean> getAns_list() {
        return ans_list;
    }

    public void setAns_list(List<AnsListBean> ans_list) {
        this.ans_list = ans_list;
    }

    public static class ShowFormatBean {
        /**
         * show_module : 1
         * font_size : 18
         * answer_full_context_color : 0
         */

        private int show_module;
        private String font_size;
        private int answer_full_context_color;

        public int getShow_module() {
            return show_module;
        }

        public void setShow_module(int show_module) {
            this.show_module = show_module;
        }

        public String getFont_size() {
            return font_size;
        }

        public void setFont_size(String font_size) {
            this.font_size = font_size;
        }

        public int getAnswer_full_context_color() {
            return answer_full_context_color;
        }

        public void setAnswer_full_context_color(int answer_full_context_color) {
            this.answer_full_context_color = answer_full_context_color;
        }
    }

    public static class ChannelDataBean {
        /**
         * open_url : sslocal://webview?url=https%3A%2F%2Fic.snssdk.com%2Fwenda%2Fv1%2Fwaphome%2Fbrow%2F%3Frecommend_from%3Drecommend_question_brow&title=%E5%A4%B4%E6%9D%A1%E9%97%AE%E7%AD%94
         * text : 关注问答频道，聊天更有谈资！
         * pos : 3
         * button_text : 进入
         * recommend_image : {"url":"https://p.pstatp.com/origin/159f000460df3e3f850c","url_list":[{"url":"http://p3.pstatp.com/list/r90/13530005a010f7ce835d"},{"url":"http://pb9.pstatp.com/list/r90/13530005a010f7ce835d"},{"url":"http://pb3.pstatp.com/list/r90/13530005a010f7ce835d"}],"uri":"list/r90/13530005a010f7ce835d","height":90,"width":90,"type":1}
         * type : 1
         */

        private String open_url;
        private String text;
        private int pos;
        private String button_text;
        private RecommendImageBean recommend_image;
        private int type;

        public String getOpen_url() {
            return open_url;
        }

        public void setOpen_url(String open_url) {
            this.open_url = open_url;
        }

        public String getText() {
            return text;
        }

        public void setText(String text) {
            this.text = text;
        }

        public int getPos() {
            return pos;
        }

        public void setPos(int pos) {
            this.pos = pos;
        }

        public String getButton_text() {
            return button_text;
        }

        public void setButton_text(String button_text) {
            this.button_text = button_text;
        }

        public RecommendImageBean getRecommend_image() {
            return recommend_image;
        }

        public void setRecommend_image(RecommendImageBean recommend_image) {
            this.recommend_image = recommend_image;
        }

        public int getType() {
            return type;
        }

        public void setType(int type) {
            this.type = type;
        }

        public static class RecommendImageBean {
            /**
             * url : https://p.pstatp.com/origin/159f000460df3e3f850c
             * url_list : [{"url":"http://p3.pstatp.com/list/r90/13530005a010f7ce835d"},{"url":"http://pb9.pstatp.com/list/r90/13530005a010f7ce835d"},{"url":"http://pb3.pstatp.com/list/r90/13530005a010f7ce835d"}]
             * uri : list/r90/13530005a010f7ce835d
             * height : 90
             * width : 90
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
                 * url : http://p3.pstatp.com/list/r90/13530005a010f7ce835d
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

    public static class QuestionBean {
        /**
         * concern_tag_list : [{"concern_id":"6213182495320443393","name":"火车","schema":"sslocal://concern?tab_sname=wenda&api_param=%7B%22wenda_api_param%22%3A%7B%22scope%22%3A%22toutiao_wenda%22%2C%22origin_from%22%3A%22click_headline%22%2C%22parent_enter_from%22%3A%22click_headline%22%2C%22enter_from%22%3A%22question%22%7D%7D&cid=6213182495320443393"}]
         * can_delete : false
         * post_answer_url : sslocal://wenda_post?qid=6420544946419269889&gd_ext_json=%7B%22enter_type%22%3A%22question_and_answer%22%2C%22ansid%22%3A6422088403512197378%7D&qTitle=%E5%8D%B0%E5%BA%A6%E6%9C%80%E5%BF%AB%E7%9A%84%E5%88%97%E8%BD%A6%E6%97%B6%E9%80%9F160%EF%BC%8C%E5%BD%93%E5%9C%B0%E4%BA%BA%E9%97%AE%E2%80%9C%E4%B8%AD%E5%9B%BD%E7%81%AB%E8%BD%A6%E6%9C%89%E8%BF%99%E4%B9%88%E5%BF%AB%E5%90%97%E2%80%9D%EF%BC%8C%E6%80%8E%E4%B9%88%E5%9B%9E%E7%AD%94%EF%BC%9F
         * is_follow : false
         * nice_ans_count : 73
         * create_time : 1494899612
         * normal_ans_count : 851
         * user : {"user_intro":"","uname":"yuejiao19926","avatar_url":"http://p0.pstatp.com/origin/3795/3033762272","user_id":"6796383301","is_verify":0}
         * share_data : {"content":"非常推荐！","image_url":"http://p0.pstatp.com/medium/6399/2275149767","share_url":"https://wenda.toutiao.com/m/wapshare/question/brow/?qid=6420544946419269889&","title":"头条问答-印度最快的列车时速160，当地人问\u201c中国火车有这么快吗\u201d，怎么回答？(924个回答)"}
         * can_edit : false
         * show_delete : false
         * title : 印度最快的列车时速160，当地人问“中国火车有这么快吗”，怎么回答？
         * follow_count : 497
         * content : {"text":"\n","thumb_image_list":[{"url":"http://p9.pstatp.com/list/r640/1dcd000e3ba14e6e61f8","url_list":[{"url":"http://p9.pstatp.com/list/r640/1dcd000e3ba14e6e61f8"},{"url":"http://pb1.pstatp.com/list/r640/1dcd000e3ba14e6e61f8"},{"url":"http://pb3.pstatp.com/list/r640/1dcd000e3ba14e6e61f8"}],"uri":"1dcd000e3ba14e6e61f8","height":379,"width":640,"type":1}],"large_image_list":[{"url":"http://p9.pstatp.com/large/1dcd000e3ba14e6e61f8","url_list":[{"url":"http://p9.pstatp.com/large/1dcd000e3ba14e6e61f8"},{"url":"http://pb1.pstatp.com/large/1dcd000e3ba14e6e61f8"},{"url":"http://pb3.pstatp.com/large/1dcd000e3ba14e6e61f8"}],"uri":"1dcd000e3ba14e6e61f8","height":379,"width":640,"type":1}]}
         * show_edit : false
         * qid : 6420544946419269889
         * fold_reason : {"open_url":"sslocal://detail?groupid=6293724675596402946","title":"为什么折叠？"}
         */

        private boolean can_delete;
        private String post_answer_url;
        private boolean is_follow;
        private int nice_ans_count;
        private int create_time;
        private int normal_ans_count;
        private UserBean user;
        private ShareDataBean share_data;
        private boolean can_edit;
        private boolean show_delete;
        private String title;
        private int follow_count;
        private ContentBean content;
        private boolean show_edit;
        private String qid;
        private FoldReasonBean fold_reason;
        private List<ConcernTagListBean> concern_tag_list;

        public boolean isCan_delete() {
            return can_delete;
        }

        public void setCan_delete(boolean can_delete) {
            this.can_delete = can_delete;
        }

        public String getPost_answer_url() {
            return post_answer_url;
        }

        public void setPost_answer_url(String post_answer_url) {
            this.post_answer_url = post_answer_url;
        }

        public boolean isIs_follow() {
            return is_follow;
        }

        public void setIs_follow(boolean is_follow) {
            this.is_follow = is_follow;
        }

        public int getNice_ans_count() {
            return nice_ans_count;
        }

        public void setNice_ans_count(int nice_ans_count) {
            this.nice_ans_count = nice_ans_count;
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

        public UserBean getUser() {
            return user;
        }

        public void setUser(UserBean user) {
            this.user = user;
        }

        public ShareDataBean getShare_data() {
            return share_data;
        }

        public void setShare_data(ShareDataBean share_data) {
            this.share_data = share_data;
        }

        public boolean isCan_edit() {
            return can_edit;
        }

        public void setCan_edit(boolean can_edit) {
            this.can_edit = can_edit;
        }

        public boolean isShow_delete() {
            return show_delete;
        }

        public void setShow_delete(boolean show_delete) {
            this.show_delete = show_delete;
        }

        public String getTitle() {
            return title;
        }

        public void setTitle(String title) {
            this.title = title;
        }

        public int getFollow_count() {
            return follow_count;
        }

        public void setFollow_count(int follow_count) {
            this.follow_count = follow_count;
        }

        public ContentBean getContent() {
            return content;
        }

        public void setContent(ContentBean content) {
            this.content = content;
        }

        public boolean isShow_edit() {
            return show_edit;
        }

        public void setShow_edit(boolean show_edit) {
            this.show_edit = show_edit;
        }

        public String getQid() {
            return qid;
        }

        public void setQid(String qid) {
            this.qid = qid;
        }

        public FoldReasonBean getFold_reason() {
            return fold_reason;
        }

        public void setFold_reason(FoldReasonBean fold_reason) {
            this.fold_reason = fold_reason;
        }

        public List<ConcernTagListBean> getConcern_tag_list() {
            return concern_tag_list;
        }

        public void setConcern_tag_list(List<ConcernTagListBean> concern_tag_list) {
            this.concern_tag_list = concern_tag_list;
        }

        public static class UserBean {
            /**
             * user_intro :
             * uname : yuejiao19926
             * avatar_url : http://p0.pstatp.com/origin/3795/3033762272
             * user_id : 6796383301
             * is_verify : 0
             */

            private String user_intro;
            private String uname;
            private String avatar_url;
            private String user_id;
            private int is_verify;

            public String getUser_intro() {
                return user_intro;
            }

            public void setUser_intro(String user_intro) {
                this.user_intro = user_intro;
            }

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
        }

        public static class ShareDataBean {
            /**
             * content : 非常推荐！
             * image_url : http://p0.pstatp.com/medium/6399/2275149767
             * share_url : https://wenda.toutiao.com/m/wapshare/question/brow/?qid=6420544946419269889&
             * title : 头条问答-印度最快的列车时速160，当地人问“中国火车有这么快吗”，怎么回答？(924个回答)
             */

            private String content;
            private String image_url;
            private String share_url;
            private String title;

            public String getContent() {
                return content;
            }

            public void setContent(String content) {
                this.content = content;
            }

            public String getImage_url() {
                return image_url;
            }

            public void setImage_url(String image_url) {
                this.image_url = image_url;
            }

            public String getShare_url() {
                return share_url;
            }

            public void setShare_url(String share_url) {
                this.share_url = share_url;
            }

            public String getTitle() {
                return title;
            }

            public void setTitle(String title) {
                this.title = title;
            }
        }

        public static class ContentBean {
            /**
             * text :
             * <p>
             * thumb_image_list : [{"url":"http://p9.pstatp.com/list/r640/1dcd000e3ba14e6e61f8","url_list":[{"url":"http://p9.pstatp.com/list/r640/1dcd000e3ba14e6e61f8"},{"url":"http://pb1.pstatp.com/list/r640/1dcd000e3ba14e6e61f8"},{"url":"http://pb3.pstatp.com/list/r640/1dcd000e3ba14e6e61f8"}],"uri":"1dcd000e3ba14e6e61f8","height":379,"width":640,"type":1}]
             * large_image_list : [{"url":"http://p9.pstatp.com/large/1dcd000e3ba14e6e61f8","url_list":[{"url":"http://p9.pstatp.com/large/1dcd000e3ba14e6e61f8"},{"url":"http://pb1.pstatp.com/large/1dcd000e3ba14e6e61f8"},{"url":"http://pb3.pstatp.com/large/1dcd000e3ba14e6e61f8"}],"uri":"1dcd000e3ba14e6e61f8","height":379,"width":640,"type":1}]
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
                 * url : http://p9.pstatp.com/list/r640/1dcd000e3ba14e6e61f8
                 * url_list : [{"url":"http://p9.pstatp.com/list/r640/1dcd000e3ba14e6e61f8"},{"url":"http://pb1.pstatp.com/list/r640/1dcd000e3ba14e6e61f8"},{"url":"http://pb3.pstatp.com/list/r640/1dcd000e3ba14e6e61f8"}]
                 * uri : 1dcd000e3ba14e6e61f8
                 * height : 379
                 * width : 640
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
                     * url : http://p9.pstatp.com/list/r640/1dcd000e3ba14e6e61f8
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
                 * url : http://p9.pstatp.com/large/1dcd000e3ba14e6e61f8
                 * url_list : [{"url":"http://p9.pstatp.com/large/1dcd000e3ba14e6e61f8"},{"url":"http://pb1.pstatp.com/large/1dcd000e3ba14e6e61f8"},{"url":"http://pb3.pstatp.com/large/1dcd000e3ba14e6e61f8"}]
                 * uri : 1dcd000e3ba14e6e61f8
                 * height : 379
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
                     * url : http://p9.pstatp.com/large/1dcd000e3ba14e6e61f8
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

        public static class ConcernTagListBean {
            /**
             * concern_id : 6213182495320443393
             * name : 火车
             * schema : sslocal://concern?tab_sname=wenda&api_param=%7B%22wenda_api_param%22%3A%7B%22scope%22%3A%22toutiao_wenda%22%2C%22origin_from%22%3A%22click_headline%22%2C%22parent_enter_from%22%3A%22click_headline%22%2C%22enter_from%22%3A%22question%22%7D%7D&cid=6213182495320443393
             */

            private String concern_id;
            private String name;
            private String schema;

            public String getConcern_id() {
                return concern_id;
            }

            public void setConcern_id(String concern_id) {
                this.concern_id = concern_id;
            }

            public String getName() {
                return name;
            }

            public void setName(String name) {
                this.name = name;
            }

            public String getSchema() {
                return schema;
            }

            public void setSchema(String schema) {
                this.schema = schema;
            }
        }
    }

    public static class ModuleListBean {
        /**
         * day_icon_url : http://p3.pstatp.com/origin/1bf50001abbc1c7f8dba
         * text : 更多问答
         * icon_type : 2
         * night_icon_url : http://p3.pstatp.com/origin/1bf40001abebc0717135
         * schema : sslocal://feed?category=question_and_answer&concern_id=6260258266329123329&type=4&name=%E9%97%AE%E7%AD%94&api_param=%7B%22source%22%3A%22question_brow%22%2C%22origin_from%22%3Anull%2C%22enter_from%22%3Anull%7D
         */

        private String day_icon_url;
        private String text;
        private int icon_type;
        private String night_icon_url;
        private String schema;

        public String getDay_icon_url() {
            return day_icon_url;
        }

        public void setDay_icon_url(String day_icon_url) {
            this.day_icon_url = day_icon_url;
        }

        public String getText() {
            return text;
        }

        public void setText(String text) {
            this.text = text;
        }

        public int getIcon_type() {
            return icon_type;
        }

        public void setIcon_type(int icon_type) {
            this.icon_type = icon_type;
        }

        public String getNight_icon_url() {
            return night_icon_url;
        }

        public void setNight_icon_url(String night_icon_url) {
            this.night_icon_url = night_icon_url;
        }

        public String getSchema() {
            return schema;
        }

        public void setSchema(String schema) {
            this.schema = schema;
        }
    }

    public static class AnsListBean implements Parcelable {
        public static final Creator<AnsListBean> CREATOR = new Creator<AnsListBean>() {
            @Override
            public AnsListBean createFromParcel(Parcel in) {
                return new AnsListBean(in);
            }

            @Override
            public AnsListBean[] newArray(int size) {
                return new AnsListBean[size];
            }
        };
        /**
         * content_abstract : {"text":"我去过印度，觉得印度人有时也太可爱了，在他们眼里，印度几乎就是唯一的，他们接受新事物的能力似乎非常的有限。但真心是想不到，印度居然还是IT大国。去过印度的人通常都会从导游那里知道：从新德里出发到阿格拉的泰姬陵之间的一趟列车，时速最高的时候达到了160公里/每小时，被印度人称为当地最快的火车。因为印度人非常的热情，看到中国游客就会用蹩脚的汉语跟中国人搭讪，甚至会问：\u201c中国有没有这样快的火车呀？\u201d，这让人尴尬不已，不知道如何回答是好。我在想如下回答，如何？----对不起，中国没有时速160的火车，只有时速360的动车。----我们中国的火车坐的人少，拉轻，印度的火车超载了，跑不快，所以中国的火车要快一点。----你们印度人是坐在车外面的，所以感觉很快，我们的高铁是坐里面的，所以感觉不到快。","thumb_image_list":[{"url":"http://p1.pstatp.com/list/r498/216d000c29349bc2648f","url_list":[{"url":"http://p1.pstatp.com/list/r498/216d000c29349bc2648f"},{"url":"http://pb3.pstatp.com/list/r498/216d000c29349bc2648f"},{"url":"http://pb3.pstatp.com/list/r498/216d000c29349bc2648f"}],"uri":"list/r498/216d000c29349bc2648f","height":350,"width":498,"type":1}],"large_image_list":[{"url":"http://p1.pstatp.com/large/216d000c29349bc2648f","url_list":[{"url":"http://p1.pstatp.com/large/216d000c29349bc2648f"},{"url":"http://pb3.pstatp.com/large/216d000c29349bc2648f"},{"url":"http://pb3.pstatp.com/large/216d000c29349bc2648f"}],"uri":"large/216d000c29349bc2648f","height":350,"width":498,"type":1}],"video_list":[]}
         * create_time : 1494936653
         * user : {"uname":"媒体人杨壮波的落脚地","avatar_url":"http://p9.pstatp.com/thumb/1787/4062932054","user_id":"3747947486","is_verify":0,"create_time":1419220894,"user_intro":"","user_auth_info":"","schema":"sslocal://profile?uid=3747947486&refer=wenda"}
         * share_data : {"content":"我去过印度，觉得印度人有时也太可爱了，在他们眼里，印度几乎就是唯一的，他们接受新事物的能力似乎非常的有限。但真心是想不到，印度居然还是IT大国。去过印度的人通常都会从导游那里知道：从新德里出发到阿格拉的泰姬陵之间的一趟列车，时速最高的时候达到了160公里/每小时，被印度人称为当地最快的火车。因为印度人非常的热情，看到中国游客就会用蹩脚的汉语跟中国人搭讪，甚至会问：\u201c中国有没有这样快的火车呀？\u201d，这让人尴尬不已，不知道如何回答是好。我在想如下回答，如何？----对不起，中国没有时速160的火车，只有时速360的动车。----我们中国的火车坐的人少，拉轻，印度的火车超载了，跑不快，所以中国的火车要快一点。----你们印度人是坐在车外面的，所以感觉很快，我们的高铁是坐里面的，所以感觉不到快。","image_url":"http://p1.pstatp.com/list/r498/216d000c29349bc2648f","share_url":"https://wenda.toutiao.com/m/wapshare/answer/brow/?ansid=6420704033253622018&","title":"头条问答-印度最快的列车时速160，当地人问\u201c中国火车有这么快吗\u201d，怎么回答？"}
         * ans_url : https://ic.snssdk.com/wenda/v1/wapanswer/content/?ansid=6420704033253622018
         * ansid : 6420704033253622018
         * is_show_bury : true
         * is_buryed : false
         * bury_count : 34
         * title :
         * digg_count : 566
         * is_digg : false
         * schema : sslocal://wenda_detail?gd_ext_json=%7B%22ansid%22%3A6420704033253622018%7D&ansid=6420704033253622018&api_param=%7B%22in_offset%22%3A0%2C%22has_more%22%3Atrue%2C%22next_offset%22%3A10%2C%22answer_list%22%3A%5B6420704033253622018%2C6420545734315081985%2C6420999813550047490%2C6420564644980588801%2C6420722026490626306%2C6420724394624041217%2C6420874208393298177%2C6420766146428928258%2C6420914953204531457%2C6422088403512197378%5D%2C%22answer_type%22%3A%22nice_answer%22%7D
         */

        private ContentAbstractBean content_abstract;
        private int create_time;
        private UserBeanX user;
        private ShareDataBeanX share_data;
        private String ans_url;
        private String ansid;
        private String qid;
        private boolean is_show_bury;
        private boolean is_buryed;
        private int bury_count;
        private String title;
        private int digg_count;
        private boolean is_digg;
        private String schema;
        public AnsListBean(Parcel in) {
            create_time = in.readInt();
            user = in.readParcelable(UserBeanX.class.getClassLoader());
            share_data = in.readParcelable(ShareDataBeanX.class.getClassLoader());
            ans_url = in.readString();
            ansid = in.readString();
            qid = in.readString();
            is_show_bury = in.readByte() != 0;
            is_buryed = in.readByte() != 0;
            bury_count = in.readInt();
            title = in.readString();
            digg_count = in.readInt();
            is_digg = in.readByte() != 0;
            schema = in.readString();
        }
        public AnsListBean() {
        }

        @Override
        public boolean equals(Object o) {
            if (this == o)
                return true;
            if (o == null || getClass() != o.getClass())
                return false;

            AnsListBean that = (AnsListBean) o;

            if (!ans_url.equals(that.ans_url))
                return false;
            return ansid.equals(that.ansid);
        }

        @Override
        public int hashCode() {
            int result = ans_url.hashCode();
            result = 31 * result + ansid.hashCode();
            return result;
        }

        @Override
        public void writeToParcel(Parcel dest, int flags) {
            dest.writeInt(create_time);
            dest.writeParcelable(user, flags);
            dest.writeParcelable(share_data, flags);
            dest.writeString(ans_url);
            dest.writeString(ansid);
            dest.writeString(qid);
            dest.writeByte((byte) (is_show_bury ? 1 : 0));
            dest.writeByte((byte) (is_buryed ? 1 : 0));
            dest.writeInt(bury_count);
            dest.writeString(title);
            dest.writeInt(digg_count);
            dest.writeByte((byte) (is_digg ? 1 : 0));
            dest.writeString(schema);
        }

        @Override
        public int describeContents() {
            return 0;
        }

        public ContentAbstractBean getContent_abstract() {
            return content_abstract;
        }

        public void setContent_abstract(ContentAbstractBean content_abstract) {
            this.content_abstract = content_abstract;
        }

        public String getQid() {
            return qid;
        }

        public void setQid(String qid) {
            this.qid = qid;
        }

        public int getCreate_time() {
            return create_time;
        }

        public void setCreate_time(int create_time) {
            this.create_time = create_time;
        }

        public UserBeanX getUser() {
            return user;
        }

        public void setUser(UserBeanX user) {
            this.user = user;
        }

        public ShareDataBeanX getShare_data() {
            return share_data;
        }

        public void setShare_data(ShareDataBeanX share_data) {
            this.share_data = share_data;
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

        public int getDigg_count() {
            return digg_count;
        }

        public void setDigg_count(int digg_count) {
            this.digg_count = digg_count;
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
             * text : 我去过印度，觉得印度人有时也太可爱了，在他们眼里，印度几乎就是唯一的，他们接受新事物的能力似乎非常的有限。但真心是想不到，印度居然还是IT大国。去过印度的人通常都会从导游那里知道：从新德里出发到阿格拉的泰姬陵之间的一趟列车，时速最高的时候达到了160公里/每小时，被印度人称为当地最快的火车。因为印度人非常的热情，看到中国游客就会用蹩脚的汉语跟中国人搭讪，甚至会问：“中国有没有这样快的火车呀？”，这让人尴尬不已，不知道如何回答是好。我在想如下回答，如何？----对不起，中国没有时速160的火车，只有时速360的动车。----我们中国的火车坐的人少，拉轻，印度的火车超载了，跑不快，所以中国的火车要快一点。----你们印度人是坐在车外面的，所以感觉很快，我们的高铁是坐里面的，所以感觉不到快。
             * thumb_image_list : [{"url":"http://p1.pstatp.com/list/r498/216d000c29349bc2648f","url_list":[{"url":"http://p1.pstatp.com/list/r498/216d000c29349bc2648f"},{"url":"http://pb3.pstatp.com/list/r498/216d000c29349bc2648f"},{"url":"http://pb3.pstatp.com/list/r498/216d000c29349bc2648f"}],"uri":"list/r498/216d000c29349bc2648f","height":350,"width":498,"type":1}]
             * large_image_list : [{"url":"http://p1.pstatp.com/large/216d000c29349bc2648f","url_list":[{"url":"http://p1.pstatp.com/large/216d000c29349bc2648f"},{"url":"http://pb3.pstatp.com/large/216d000c29349bc2648f"},{"url":"http://pb3.pstatp.com/large/216d000c29349bc2648f"}],"uri":"large/216d000c29349bc2648f","height":350,"width":498,"type":1}]
             * video_list : []
             */

            private String text;
            private List<ThumbImageListBeanX> thumb_image_list;
            private List<LargeImageListBeanX> large_image_list;
            private List<?> video_list;

            public String getText() {
                return text;
            }

            public void setText(String text) {
                this.text = text;
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

            public List<?> getVideo_list() {
                return video_list;
            }

            public void setVideo_list(List<?> video_list) {
                this.video_list = video_list;
            }

            public static class ThumbImageListBeanX {
                /**
                 * url : http://p1.pstatp.com/list/r498/216d000c29349bc2648f
                 * url_list : [{"url":"http://p1.pstatp.com/list/r498/216d000c29349bc2648f"},{"url":"http://pb3.pstatp.com/list/r498/216d000c29349bc2648f"},{"url":"http://pb3.pstatp.com/list/r498/216d000c29349bc2648f"}]
                 * uri : list/r498/216d000c29349bc2648f
                 * height : 350
                 * width : 498
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
                     * url : http://p1.pstatp.com/list/r498/216d000c29349bc2648f
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
                 * url : http://p1.pstatp.com/large/216d000c29349bc2648f
                 * url_list : [{"url":"http://p1.pstatp.com/large/216d000c29349bc2648f"},{"url":"http://pb3.pstatp.com/large/216d000c29349bc2648f"},{"url":"http://pb3.pstatp.com/large/216d000c29349bc2648f"}]
                 * uri : large/216d000c29349bc2648f
                 * height : 350
                 * width : 498
                 * type : 1
                 */

                private String url;
                private String uri;
                private int height;
                private int width;
                private int type;
                private List<UrlListBeanXXXX> url_list;

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

                public List<UrlListBeanXXXX> getUrl_list() {
                    return url_list;
                }

                public void setUrl_list(List<UrlListBeanXXXX> url_list) {
                    this.url_list = url_list;
                }

                public static class UrlListBeanXXXX {
                    /**
                     * url : http://p1.pstatp.com/large/216d000c29349bc2648f
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

        public static class UserBeanX implements Parcelable {
            public static final Creator<UserBeanX> CREATOR = new Creator<UserBeanX>() {
                @Override
                public UserBeanX createFromParcel(Parcel in) {
                    return new UserBeanX(in);
                }

                @Override
                public UserBeanX[] newArray(int size) {
                    return new UserBeanX[size];
                }
            };
            /**
             * uname : 媒体人杨壮波的落脚地
             * avatar_url : http://p9.pstatp.com/thumb/1787/4062932054
             * user_id : 3747947486
             * is_verify : 0
             * create_time : 1419220894
             * user_intro :
             * user_auth_info :
             * schema : sslocal://profile?uid=3747947486&refer=wenda
             */

            private String uname;
            private String avatar_url;
            private String user_id;
            private int is_verify;
            private int create_time;
            private String user_intro;
            private String user_auth_info;
            private String schema;

            public UserBeanX(Parcel in) {
                uname = in.readString();
                avatar_url = in.readString();
                user_id = in.readString();
                is_verify = in.readInt();
                create_time = in.readInt();
                user_intro = in.readString();
                user_auth_info = in.readString();
                schema = in.readString();
            }

            public UserBeanX() {
            }

            @Override
            public void writeToParcel(Parcel dest, int flags) {
                dest.writeString(uname);
                dest.writeString(avatar_url);
                dest.writeString(user_id);
                dest.writeInt(is_verify);
                dest.writeInt(create_time);
                dest.writeString(user_intro);
                dest.writeString(user_auth_info);
                dest.writeString(schema);
            }

            @Override
            public int describeContents() {
                return 0;
            }

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

        public static class ShareDataBeanX implements Parcelable {
            public static final Creator<ShareDataBeanX> CREATOR = new Creator<ShareDataBeanX>() {
                @Override
                public ShareDataBeanX createFromParcel(Parcel in) {
                    return new ShareDataBeanX(in);
                }

                @Override
                public ShareDataBeanX[] newArray(int size) {
                    return new ShareDataBeanX[size];
                }
            };
            /**
             * content : 我去过印度，觉得印度人有时也太可爱了，在他们眼里，印度几乎就是唯一的，他们接受新事物的能力似乎非常的有限。但真心是想不到，印度居然还是IT大国。去过印度的人通常都会从导游那里知道：从新德里出发到阿格拉的泰姬陵之间的一趟列车，时速最高的时候达到了160公里/每小时，被印度人称为当地最快的火车。因为印度人非常的热情，看到中国游客就会用蹩脚的汉语跟中国人搭讪，甚至会问：“中国有没有这样快的火车呀？”，这让人尴尬不已，不知道如何回答是好。我在想如下回答，如何？----对不起，中国没有时速160的火车，只有时速360的动车。----我们中国的火车坐的人少，拉轻，印度的火车超载了，跑不快，所以中国的火车要快一点。----你们印度人是坐在车外面的，所以感觉很快，我们的高铁是坐里面的，所以感觉不到快。
             * image_url : http://p1.pstatp.com/list/r498/216d000c29349bc2648f
             * share_url : https://wenda.toutiao.com/m/wapshare/answer/brow/?ansid=6420704033253622018&
             * title : 头条问答-印度最快的列车时速160，当地人问“中国火车有这么快吗”，怎么回答？
             */

            private String content;
            private String image_url;
            private String share_url;
            private String title;

            public ShareDataBeanX(Parcel in) {
                content = in.readString();
                image_url = in.readString();
                share_url = in.readString();
                title = in.readString();
            }

            public ShareDataBeanX() {
            }

            @Override
            public void writeToParcel(Parcel dest, int flags) {
                dest.writeString(content);
                dest.writeString(image_url);
                dest.writeString(share_url);
                dest.writeString(title);
            }

            @Override
            public int describeContents() {
                return 0;
            }

            public String getContent() {
                return content;
            }

            public void setContent(String content) {
                this.content = content;
            }

            public String getImage_url() {
                return image_url;
            }

            public void setImage_url(String image_url) {
                this.image_url = image_url;
            }

            public String getShare_url() {
                return share_url;
            }

            public void setShare_url(String share_url) {
                this.share_url = share_url;
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
