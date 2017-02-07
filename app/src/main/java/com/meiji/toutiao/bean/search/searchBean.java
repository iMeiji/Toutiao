package com.meiji.toutiao.bean.search;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.SerializedName;

import java.util.List;

/**
 * Created by Meiji on 2017/2/7.
 */

public class SearchBean {

    /**
     * count : 2
     * action_label : click_search
     * return_count : 2
     * no_outsite_res : 0
     * has_more : 1
     * page_id : /search/
     * cur_tab : 1
     * tab : {"tab_list":[{"tab_name":"综合","tab_id":1,"tab_code":"news"},{"tab_name":"视频","tab_id":2,"tab_code":"video"},{"tab_name":"图集","tab_id":3,"tab_code":"gallery"},{"tab_name":"头条号","tab_id":4,"tab_code":"pgc"},{"tab_name":"问答","tab_id":5,"tab_code":"wenda"}],"cur_tab":1}
     * offset : 4
     * action_label_web : click_search
     * show_tabs : 1
     * data : [{"media_name":"四川发布","repin_count":128,"ban_comment":0,"show_play_effective_count":0,"single_mode":true,"abstract":"↑ 点击上方\u201c四川发布\u201d关注我们新年过去，上班第一天的你过的如何？要不要来场考试精神一下？看过来！成都市人社局、发改委、经信委等31家主管部门的所属103个事业单位和成都大学等3家市直属事业单位开启一波公招。本次公开招聘为编制内招聘，考试分2个批次进行报名，第一批报名就在今天哟！","display_title":"","media_avatar_url":"http://p2.pstatp.com/large/3494/5703799476","datetime":"2017-02-03 19:45","article_type":0,"more_mode":true,"create_time":1486122323,"has_m3u8_video":0,"keywords":"全国新闻联播,规划,正月初七,H7N9禽流感,四川,遂宁市","has_mp4_video":0,"favorite_count":128,"aggr_type":0,"comments_count":4,"article_sub_type":0,"bury_count":0,"title":"今起报名！成都市属事业单位公招468名工作人员｜微说四川","has_video":false,"share_url":"http://toutiao.com/group/6382841915961213185/?iid=0&app=news_article","id":6382841915961213000,"source":"四川发布","comment_count":4,"article_url":"http://mp.weixin.qq.com/s?__biz=MzA3MDA4MTgyOA==&mid=2654907428&idx=5&sn=89039de335f7f0307ea7e03a77374e61","image_url":"http://p3.pstatp.com/list/190x124/15f00000cd98dbb29826","middle_mode":false,"large_mode":false,"item_source_url":"/group/6382841915961213185/","media_url":"http://toutiao.com/m4314843197/","display_time":1486121477,"publish_time":1486121477,"go_detail_count":5757,"image_list":[{"url":"http://p3.pstatp.com/list/190x124/15f00000cd98dbb29826"},{"url":"http://p1.pstatp.com/list/190x124/15f20000f7277aea907a"},{"url":"http://p2.pstatp.com/list/190x124/15f00000cd995704cc19"}],"gallary_image_count":0,"item_seo_url":"/i6382846776407228930/","tag_id":6382841915961213000,"source_url":"/group/6382841915961213185/","natant_level":0,"seo_url":"/i6382846776407228930/","display_url":"http://toutiao.com/group/6382841915961213185/","url":"http://mp.weixin.qq.com/s?__biz=MzA3MDA4MTgyOA==&mid=2654907428&idx=5&sn=89039de335f7f0307ea7e03a77374e61","level":0,"digg_count":0,"behot_time":1486121477,"image_detail":[{"url":"http://p3.pstatp.com/large/15f00000cd98dbb29826","width":776,"url_list":[{"url":"http://p3.pstatp.com/large/15f00000cd98dbb29826"},{"url":"http://pb2.pstatp.com/large/15f00000cd98dbb29826"},{"url":"http://pb3.pstatp.com/large/15f00000cd98dbb29826"}],"uri":"large/15f00000cd98dbb29826","height":4289},{"url":"http://p1.pstatp.com/large/15f20000f7277aea907a","width":333,"url_list":[{"url":"http://p1.pstatp.com/large/15f20000f7277aea907a"},{"url":"http://pb3.pstatp.com/large/15f20000f7277aea907a"},{"url":"http://pb3.pstatp.com/large/15f20000f7277aea907a"}],"uri":"large/15f20000f7277aea907a","height":552},{"url":"http://p2.pstatp.com/large/15f00000cd995704cc19","width":334,"url_list":[{"url":"http://p2.pstatp.com/large/15f00000cd995704cc19"},{"url":"http://pb3.pstatp.com/large/15f00000cd995704cc19"},{"url":"http://pb3.pstatp.com/large/15f00000cd995704cc19"}],"uri":"large/15f00000cd995704cc19","height":681},{"url":"http://p2.pstatp.com/large/15f00000cd9aa5eceb60","width":800,"url_list":[{"url":"http://p2.pstatp.com/large/15f00000cd9aa5eceb60"},{"url":"http://pb3.pstatp.com/large/15f00000cd9aa5eceb60"},{"url":"http://pb3.pstatp.com/large/15f00000cd9aa5eceb60"}],"uri":"large/15f00000cd9aa5eceb60","height":694},{"url":"http://p1.pstatp.com/large/15f20000f7287c158f39","width":419,"url_list":[{"url":"http://p1.pstatp.com/large/15f20000f7287c158f39"},{"url":"http://pb3.pstatp.com/large/15f20000f7287c158f39"},{"url":"http://pb3.pstatp.com/large/15f20000f7287c158f39"}],"uri":"large/15f20000f7287c158f39","height":281},{"url":"http://p3.pstatp.com/large/15ed0005c34c803cb649","width":441,"url_list":[{"url":"http://p3.pstatp.com/large/15ed0005c34c803cb649"},{"url":"http://pb2.pstatp.com/large/15ed0005c34c803cb649"},{"url":"http://pb3.pstatp.com/large/15ed0005c34c803cb649"}],"uri":"large/15ed0005c34c803cb649","height":300},{"url":"http://p3.pstatp.com/large/15ed0005c34de1b635b6","width":431,"url_list":[{"url":"http://p3.pstatp.com/large/15ed0005c34de1b635b6"},{"url":"http://pb2.pstatp.com/large/15ed0005c34de1b635b6"},{"url":"http://pb3.pstatp.com/large/15ed0005c34de1b635b6"}],"uri":"large/15ed0005c34de1b635b6","height":269},{"url":"http://p3.pstatp.com/large/15df0009347a5ea654ab","width":640,"url_list":[{"url":"http://p3.pstatp.com/large/15df0009347a5ea654ab"},{"url":"http://pb2.pstatp.com/large/15df0009347a5ea654ab"},{"url":"http://pb3.pstatp.com/large/15df0009347a5ea654ab"}],"uri":"large/15df0009347a5ea654ab","height":240}],"tag":"news_society","has_gallery":false,"has_image":true,"highlight":{"source":[],"abstract":[[16,2]],"title":[]},"group_id":6382841915961213000,"middle_image":{"url":"http://p3.pstatp.com/list/15f00000cd98dbb29826","width":776,"url_list":[{"url":"http://p3.pstatp.com/list/15f00000cd98dbb29826"},{"url":"http://pb2.pstatp.com/list/15f00000cd98dbb29826"},{"url":"http://pb3.pstatp.com/list/15f00000cd98dbb29826"}],"uri":"list/15f00000cd98dbb29826","height":4289}},{"media_name":"醉美邛崃","repin_count":0,"ban_comment":0,"show_play_effective_count":0,"single_mode":true,"abstract":"邛崃全媒体贺岁行动\u201c您送祝福我送礼物\u201d征集令发出后，海内外邛崃人积极响应，在邛外地人踊跃参与，成都崃岭茶业、文君故里酒业、金忠食品、文君茶业等邛崃企业主动提供贺岁礼品，高宇集团等多家企业也向邛崃人民送上了新春的问候。","display_title":"","media_avatar_url":"http://p9.pstatp.com/large/1353000c0a7e7f07c1c5","datetime":"2017-02-06 10:29","article_type":0,"more_mode":false,"create_time":1486348170,"has_m3u8_video":0,"keywords":"邛崃,共赴故乡约,卫星城,今日邛崃,多伦多,新春佳节,万事如意","has_mp4_video":0,"favorite_count":0,"aggr_type":0,"comments_count":2,"article_sub_type":0,"bury_count":0,"title":"2017新春贺岁特别报道\u2014\u2014共赴故乡约","has_video":false,"share_url":"http://toutiao.com/group/6383136599236215041/?iid=0&app=news_article","id":6383136599236215000,"source":"醉美邛崃","comment_count":2,"article_url":"http://toutiao.com/group/6383136599236215041/","image_url":"http://p3.pstatp.com/list/190x124/150d0011e91bff0619c9","middle_mode":true,"large_mode":false,"item_source_url":"/group/6383136599236215041/","media_url":"http://toutiao.com/m5791123466/","display_time":1486348163,"publish_time":1486348163,"go_detail_count":409,"image_list":[],"gallary_image_count":0,"item_seo_url":"/group/6383136599236215041/","tag_id":6383136599236215000,"source_url":"/group/6383136599236215041/","natant_level":0,"seo_url":"/group/6383136599236215041/","display_url":"http://toutiao.com/group/6383136599236215041/","url":"http://toutiao.com/group/6383136599236215041/","level":0,"digg_count":0,"behot_time":1486348163,"summary":"新年祝福吧。陈利军中国人民解放军军械工程学院副教授我19岁就离开家乡来到石家庄求学，","image_detail":[{"url":"http://p3.pstatp.com/large/16310002db929c25f0b2","width":500,"url_list":[{"url":"http://p3.pstatp.com/large/16310002db929c25f0b2"},{"url":"http://pb2.pstatp.com/large/16310002db929c25f0b2"},{"url":"http://pb3.pstatp.com/large/16310002db929c25f0b2"}],"uri":"large/16310002db929c25f0b2","height":300},{"url":"http://p1.pstatp.com/large/1632000623ddee508c1d","width":500,"url_list":[{"url":"http://p1.pstatp.com/large/1632000623ddee508c1d"},{"url":"http://pb3.pstatp.com/large/1632000623ddee508c1d"},{"url":"http://pb3.pstatp.com/large/1632000623ddee508c1d"}],"uri":"large/1632000623ddee508c1d","height":298},{"url":"http://p3.pstatp.com/large/16310002db9017b0ed6d","width":500,"url_list":[{"url":"http://p3.pstatp.com/large/16310002db9017b0ed6d"},{"url":"http://pb2.pstatp.com/large/16310002db9017b0ed6d"},{"url":"http://pb3.pstatp.com/large/16310002db9017b0ed6d"}],"uri":"large/16310002db9017b0ed6d","height":293},{"url":"http://p1.pstatp.com/large/1632000623def8e7e2f1","width":600,"url_list":[{"url":"http://p1.pstatp.com/large/1632000623def8e7e2f1"},{"url":"http://pb3.pstatp.com/large/1632000623def8e7e2f1"},{"url":"http://pb3.pstatp.com/large/1632000623def8e7e2f1"}],"uri":"large/1632000623def8e7e2f1","height":354},{"url":"http://p9.pstatp.com/large/16310002db93afed566d","width":500,"url_list":[{"url":"http://p9.pstatp.com/large/16310002db93afed566d"},{"url":"http://pb3.pstatp.com/large/16310002db93afed566d"},{"url":"http://pb3.pstatp.com/large/16310002db93afed566d"}],"uri":"large/16310002db93afed566d","height":295},{"url":"http://p1.pstatp.com/large/16310002db947118f83a","width":500,"url_list":[{"url":"http://p1.pstatp.com/large/16310002db947118f83a"},{"url":"http://pb3.pstatp.com/large/16310002db947118f83a"},{"url":"http://pb3.pstatp.com/large/16310002db947118f83a"}],"uri":"large/16310002db947118f83a","height":298},{"url":"http://p1.pstatp.com/large/1632000623e079024afb","width":500,"url_list":[{"url":"http://p1.pstatp.com/large/1632000623e079024afb"},{"url":"http://pb3.pstatp.com/large/1632000623e079024afb"},{"url":"http://pb3.pstatp.com/large/1632000623e079024afb"}],"uri":"large/1632000623e079024afb","height":295},{"url":"http://p1.pstatp.com/large/16310002db9660a403a6","width":500,"url_list":[{"url":"http://p1.pstatp.com/large/16310002db9660a403a6"},{"url":"http://pb3.pstatp.com/large/16310002db9660a403a6"},{"url":"http://pb3.pstatp.com/large/16310002db9660a403a6"}],"uri":"large/16310002db9660a403a6","height":350}],"tag":"news_society","has_gallery":false,"has_image":true,"highlight":{"source":[],"abstract":[],"summary":[[0,2]],"title":[]},"group_id":6383136599236215000,"middle_image":"http://p3.pstatp.com/list/150d0011e91bff0619c9"}]
     * message : success
     * action_label_pgc : click_search
     */

    private int count;
    private String action_label;
    private int return_count;
    private int no_outsite_res;
    private int has_more;
    private String page_id;
    private int cur_tab;
    private TabBean tab;
    private int offset;
    private String action_label_web;
    private int show_tabs;
    private String message;
    private String action_label_pgc;
    private List<DataBean> data;

    public int getCount() {
        return count;
    }

    public void setCount(int count) {
        this.count = count;
    }

    public String getAction_label() {
        return action_label;
    }

    public void setAction_label(String action_label) {
        this.action_label = action_label;
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

    public String getAction_label_web() {
        return action_label_web;
    }

    public void setAction_label_web(String action_label_web) {
        this.action_label_web = action_label_web;
    }

    public int getShow_tabs() {
        return show_tabs;
    }

    public void setShow_tabs(int show_tabs) {
        this.show_tabs = show_tabs;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getAction_label_pgc() {
        return action_label_pgc;
    }

    public void setAction_label_pgc(String action_label_pgc) {
        this.action_label_pgc = action_label_pgc;
    }

    public List<DataBean> getData() {
        return data;
    }

    public void setData(List<DataBean> data) {
        this.data = data;
    }

    public static class TabBean {
        /**
         * tab_list : [{"tab_name":"综合","tab_id":1,"tab_code":"news"},{"tab_name":"视频","tab_id":2,"tab_code":"video"},{"tab_name":"图集","tab_id":3,"tab_code":"gallery"},{"tab_name":"头条号","tab_id":4,"tab_code":"pgc"},{"tab_name":"问答","tab_id":5,"tab_code":"wenda"}]
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
         * media_name : 四川发布
         * repin_count : 128
         * ban_comment : 0
         * show_play_effective_count : 0
         * single_mode : true
         * abstract : ↑ 点击上方“四川发布”关注我们新年过去，上班第一天的你过的如何？要不要来场考试精神一下？看过来！成都市人社局、发改委、经信委等31家主管部门的所属103个事业单位和成都大学等3家市直属事业单位开启一波公招。本次公开招聘为编制内招聘，考试分2个批次进行报名，第一批报名就在今天哟！
         * display_title :
         * media_avatar_url : http://p2.pstatp.com/large/3494/5703799476
         * datetime : 2017-02-03 19:45
         * article_type : 0
         * more_mode : true
         * create_time : 1486122323
         * has_m3u8_video : 0
         * keywords : 全国新闻联播,规划,正月初七,H7N9禽流感,四川,遂宁市
         * has_mp4_video : 0
         * favorite_count : 128
         * aggr_type : 0
         * comments_count : 4
         * article_sub_type : 0
         * bury_count : 0
         * title : 今起报名！成都市属事业单位公招468名工作人员｜微说四川
         * has_video : false
         * share_url : http://toutiao.com/group/6382841915961213185/?iid=0&app=news_article
         * id : 6382841915961213000
         * source : 四川发布
         * comment_count : 4
         * article_url : http://mp.weixin.qq.com/s?__biz=MzA3MDA4MTgyOA==&mid=2654907428&idx=5&sn=89039de335f7f0307ea7e03a77374e61
         * image_url : http://p3.pstatp.com/list/190x124/15f00000cd98dbb29826
         * middle_mode : false
         * large_mode : false
         * item_source_url : /group/6382841915961213185/
         * media_url : http://toutiao.com/m4314843197/
         * display_time : 1486121477
         * publish_time : 1486121477
         * go_detail_count : 5757
         * image_list : [{"url":"http://p3.pstatp.com/list/190x124/15f00000cd98dbb29826"},{"url":"http://p1.pstatp.com/list/190x124/15f20000f7277aea907a"},{"url":"http://p2.pstatp.com/list/190x124/15f00000cd995704cc19"}]
         * gallary_image_count : 0
         * item_seo_url : /i6382846776407228930/
         * tag_id : 6382841915961213000
         * source_url : /group/6382841915961213185/
         * natant_level : 0
         * seo_url : /i6382846776407228930/
         * display_url : http://toutiao.com/group/6382841915961213185/
         * url : http://mp.weixin.qq.com/s?__biz=MzA3MDA4MTgyOA==&mid=2654907428&idx=5&sn=89039de335f7f0307ea7e03a77374e61
         * level : 0
         * digg_count : 0
         * behot_time : 1486121477
         * image_detail : [{"url":"http://p3.pstatp.com/large/15f00000cd98dbb29826","width":776,"url_list":[{"url":"http://p3.pstatp.com/large/15f00000cd98dbb29826"},{"url":"http://pb2.pstatp.com/large/15f00000cd98dbb29826"},{"url":"http://pb3.pstatp.com/large/15f00000cd98dbb29826"}],"uri":"large/15f00000cd98dbb29826","height":4289},{"url":"http://p1.pstatp.com/large/15f20000f7277aea907a","width":333,"url_list":[{"url":"http://p1.pstatp.com/large/15f20000f7277aea907a"},{"url":"http://pb3.pstatp.com/large/15f20000f7277aea907a"},{"url":"http://pb3.pstatp.com/large/15f20000f7277aea907a"}],"uri":"large/15f20000f7277aea907a","height":552},{"url":"http://p2.pstatp.com/large/15f00000cd995704cc19","width":334,"url_list":[{"url":"http://p2.pstatp.com/large/15f00000cd995704cc19"},{"url":"http://pb3.pstatp.com/large/15f00000cd995704cc19"},{"url":"http://pb3.pstatp.com/large/15f00000cd995704cc19"}],"uri":"large/15f00000cd995704cc19","height":681},{"url":"http://p2.pstatp.com/large/15f00000cd9aa5eceb60","width":800,"url_list":[{"url":"http://p2.pstatp.com/large/15f00000cd9aa5eceb60"},{"url":"http://pb3.pstatp.com/large/15f00000cd9aa5eceb60"},{"url":"http://pb3.pstatp.com/large/15f00000cd9aa5eceb60"}],"uri":"large/15f00000cd9aa5eceb60","height":694},{"url":"http://p1.pstatp.com/large/15f20000f7287c158f39","width":419,"url_list":[{"url":"http://p1.pstatp.com/large/15f20000f7287c158f39"},{"url":"http://pb3.pstatp.com/large/15f20000f7287c158f39"},{"url":"http://pb3.pstatp.com/large/15f20000f7287c158f39"}],"uri":"large/15f20000f7287c158f39","height":281},{"url":"http://p3.pstatp.com/large/15ed0005c34c803cb649","width":441,"url_list":[{"url":"http://p3.pstatp.com/large/15ed0005c34c803cb649"},{"url":"http://pb2.pstatp.com/large/15ed0005c34c803cb649"},{"url":"http://pb3.pstatp.com/large/15ed0005c34c803cb649"}],"uri":"large/15ed0005c34c803cb649","height":300},{"url":"http://p3.pstatp.com/large/15ed0005c34de1b635b6","width":431,"url_list":[{"url":"http://p3.pstatp.com/large/15ed0005c34de1b635b6"},{"url":"http://pb2.pstatp.com/large/15ed0005c34de1b635b6"},{"url":"http://pb3.pstatp.com/large/15ed0005c34de1b635b6"}],"uri":"large/15ed0005c34de1b635b6","height":269},{"url":"http://p3.pstatp.com/large/15df0009347a5ea654ab","width":640,"url_list":[{"url":"http://p3.pstatp.com/large/15df0009347a5ea654ab"},{"url":"http://pb2.pstatp.com/large/15df0009347a5ea654ab"},{"url":"http://pb3.pstatp.com/large/15df0009347a5ea654ab"}],"uri":"large/15df0009347a5ea654ab","height":240}]
         * tag : news_society
         * has_gallery : false
         * has_image : true
         * highlight : {"source":[],"abstract":[[16,2]],"title":[]}
         * group_id : 6382841915961213000
         * middle_image : {"url":"http://p3.pstatp.com/list/15f00000cd98dbb29826","width":776,"url_list":[{"url":"http://p3.pstatp.com/list/15f00000cd98dbb29826"},{"url":"http://pb2.pstatp.com/list/15f00000cd98dbb29826"},{"url":"http://pb3.pstatp.com/list/15f00000cd98dbb29826"}],"uri":"list/15f00000cd98dbb29826","height":4289}
         * summary : 新年祝福吧。陈利军中国人民解放军军械工程学院副教授我19岁就离开家乡来到石家庄求学，
         */

        private String media_name;
        private int repin_count;
        private int ban_comment;
        private int show_play_effective_count;
        private boolean single_mode;
        @SerializedName("abstract")
        private String abstractX;
        private String display_title;
        private String media_avatar_url;
        private String datetime;
        private int article_type;
        private boolean more_mode;
        private int create_time;
        private int has_m3u8_video;
        private String keywords;
        private int has_mp4_video;
        private int favorite_count;
        private int aggr_type;
        private int comments_count;
        private int article_sub_type;
        private int bury_count;
        private String title;
        private boolean has_video;
        private String share_url;
        private long id;
        private String source;
        private int comment_count;
        private String article_url;
        private String image_url;
        private boolean middle_mode;
        private boolean large_mode;
        private String item_source_url;
        private String media_url;
        private int display_time;
        private int publish_time;
        private int go_detail_count;
        private int gallary_image_count;
        private String item_seo_url;
        private long tag_id;
        private String source_url;
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
        //private MiddleImageBean middle_image;
        private String summary;
        private List<ImageListBean> image_list;
        private List<ImageDetailBean> image_detail;

        protected DataBean(Parcel in) {
            media_name = in.readString();
            repin_count = in.readInt();
            ban_comment = in.readInt();
            show_play_effective_count = in.readInt();
            single_mode = in.readByte() != 0;
            abstractX = in.readString();
            display_title = in.readString();
            media_avatar_url = in.readString();
            datetime = in.readString();
            article_type = in.readInt();
            more_mode = in.readByte() != 0;
            create_time = in.readInt();
            has_m3u8_video = in.readInt();
            keywords = in.readString();
            has_mp4_video = in.readInt();
            favorite_count = in.readInt();
            aggr_type = in.readInt();
            comments_count = in.readInt();
            article_sub_type = in.readInt();
            bury_count = in.readInt();
            title = in.readString();
            has_video = in.readByte() != 0;
            share_url = in.readString();
            id = in.readLong();
            source = in.readString();
            comment_count = in.readInt();
            article_url = in.readString();
            image_url = in.readString();
            middle_mode = in.readByte() != 0;
            large_mode = in.readByte() != 0;
            item_source_url = in.readString();
            media_url = in.readString();
            display_time = in.readInt();
            publish_time = in.readInt();
            go_detail_count = in.readInt();
            gallary_image_count = in.readInt();
            item_seo_url = in.readString();
            tag_id = in.readLong();
            source_url = in.readString();
            natant_level = in.readInt();
            seo_url = in.readString();
            display_url = in.readString();
            url = in.readString();
            level = in.readInt();
            digg_count = in.readInt();
            behot_time = in.readInt();
            tag = in.readString();
            has_gallery = in.readByte() != 0;
            has_image = in.readByte() != 0;
            group_id = in.readLong();
            summary = in.readString();
        }

        @Override
        public void writeToParcel(Parcel dest, int flags) {
            dest.writeString(media_name);
            dest.writeInt(repin_count);
            dest.writeInt(ban_comment);
            dest.writeInt(show_play_effective_count);
            dest.writeByte((byte) (single_mode ? 1 : 0));
            dest.writeString(abstractX);
            dest.writeString(display_title);
            dest.writeString(media_avatar_url);
            dest.writeString(datetime);
            dest.writeInt(article_type);
            dest.writeByte((byte) (more_mode ? 1 : 0));
            dest.writeInt(create_time);
            dest.writeInt(has_m3u8_video);
            dest.writeString(keywords);
            dest.writeInt(has_mp4_video);
            dest.writeInt(favorite_count);
            dest.writeInt(aggr_type);
            dest.writeInt(comments_count);
            dest.writeInt(article_sub_type);
            dest.writeInt(bury_count);
            dest.writeString(title);
            dest.writeByte((byte) (has_video ? 1 : 0));
            dest.writeString(share_url);
            dest.writeLong(id);
            dest.writeString(source);
            dest.writeInt(comment_count);
            dest.writeString(article_url);
            dest.writeString(image_url);
            dest.writeByte((byte) (middle_mode ? 1 : 0));
            dest.writeByte((byte) (large_mode ? 1 : 0));
            dest.writeString(item_source_url);
            dest.writeString(media_url);
            dest.writeInt(display_time);
            dest.writeInt(publish_time);
            dest.writeInt(go_detail_count);
            dest.writeInt(gallary_image_count);
            dest.writeString(item_seo_url);
            dest.writeLong(tag_id);
            dest.writeString(source_url);
            dest.writeInt(natant_level);
            dest.writeString(seo_url);
            dest.writeString(display_url);
            dest.writeString(url);
            dest.writeInt(level);
            dest.writeInt(digg_count);
            dest.writeInt(behot_time);
            dest.writeString(tag);
            dest.writeByte((byte) (has_gallery ? 1 : 0));
            dest.writeByte((byte) (has_image ? 1 : 0));
            dest.writeLong(group_id);
            dest.writeString(summary);
        }

        @Override
        public int describeContents() {
            return 0;
        }

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

        public boolean isSingle_mode() {
            return single_mode;
        }

        public void setSingle_mode(boolean single_mode) {
            this.single_mode = single_mode;
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

        public String getMedia_avatar_url() {
            return media_avatar_url;
        }

        public void setMedia_avatar_url(String media_avatar_url) {
            this.media_avatar_url = media_avatar_url;
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

        public int getComments_count() {
            return comments_count;
        }

        public void setComments_count(int comments_count) {
            this.comments_count = comments_count;
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

        public String getImage_url() {
            return image_url;
        }

        public void setImage_url(String image_url) {
            this.image_url = image_url;
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

//        public MiddleImageBean getMiddle_image() {
//            return middle_image;
//        }
//
//        public void setMiddle_image(MiddleImageBean middle_image) {
//            this.middle_image = middle_image;
//        }

        public String getSummary() {
            return summary;
        }

        public void setSummary(String summary) {
            this.summary = summary;
        }

        public List<ImageListBean> getImage_list() {
            return image_list;
        }

        public void setImage_list(List<ImageListBean> image_list) {
            this.image_list = image_list;
        }

        public List<ImageDetailBean> getImage_detail() {
            return image_detail;
        }

        public void setImage_detail(List<ImageDetailBean> image_detail) {
            this.image_detail = image_detail;
        }

        public static class HighlightBean {
            private List<?> source;
            @SerializedName("abstract")
            private List<List<Integer>> abstractX;
            private List<?> title;

            public List<?> getSource() {
                return source;
            }

            public void setSource(List<?> source) {
                this.source = source;
            }

            public List<List<Integer>> getAbstractX() {
                return abstractX;
            }

            public void setAbstractX(List<List<Integer>> abstractX) {
                this.abstractX = abstractX;
            }

            public List<?> getTitle() {
                return title;
            }

            public void setTitle(List<?> title) {
                this.title = title;
            }
        }

        public static class MiddleImageBean {
            /**
             * url : http://p3.pstatp.com/list/15f00000cd98dbb29826
             * width : 776
             * url_list : [{"url":"http://p3.pstatp.com/list/15f00000cd98dbb29826"},{"url":"http://pb2.pstatp.com/list/15f00000cd98dbb29826"},{"url":"http://pb3.pstatp.com/list/15f00000cd98dbb29826"}]
             * uri : list/15f00000cd98dbb29826
             * height : 4289
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
                 * url : http://p3.pstatp.com/list/15f00000cd98dbb29826
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
             * url : http://p3.pstatp.com/list/190x124/15f00000cd98dbb29826
             */

            private String url;

            public String getUrl() {
                return url;
            }

            public void setUrl(String url) {
                this.url = url;
            }
        }

        public static class ImageDetailBean {
            /**
             * url : http://p3.pstatp.com/large/15f00000cd98dbb29826
             * width : 776
             * url_list : [{"url":"http://p3.pstatp.com/large/15f00000cd98dbb29826"},{"url":"http://pb2.pstatp.com/large/15f00000cd98dbb29826"},{"url":"http://pb3.pstatp.com/large/15f00000cd98dbb29826"}]
             * uri : large/15f00000cd98dbb29826
             * height : 4289
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
                 * url : http://p3.pstatp.com/large/15f00000cd98dbb29826
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
