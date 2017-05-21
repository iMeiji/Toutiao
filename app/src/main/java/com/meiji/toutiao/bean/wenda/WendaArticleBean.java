package com.meiji.toutiao.bean.wenda;

import java.util.List;

/**
 * Created by Meiji on 2017/5/20.
 */

public class WendaArticleBean {

    /**
     * add_first_page : 1
     * has_more_to_refresh : true
     * login_status : 0
     * total_number : 10
     * extra :
     * has_more : true
     * message : success
     * api_param : {"origin_from":"","enter_from":""}
     * tips : {"display_info":"今日头条推荐引擎有10条更新","open_url":"","type":"app","display_duration":2,"app_name":"今日头条"}
     */

    private int add_first_page;
    private boolean has_more_to_refresh;
    private int login_status;
    private int total_number;
    private String extra;
    private boolean has_more;
    private String message;
    private String api_param;
    private TipsBean tips;
    private List<DataBean> data;

    public int getAdd_first_page() {
        return add_first_page;
    }

    public void setAdd_first_page(int add_first_page) {
        this.add_first_page = add_first_page;
    }

    public boolean isHas_more_to_refresh() {
        return has_more_to_refresh;
    }

    public void setHas_more_to_refresh(boolean has_more_to_refresh) {
        this.has_more_to_refresh = has_more_to_refresh;
    }

    public int getLogin_status() {
        return login_status;
    }

    public void setLogin_status(int login_status) {
        this.login_status = login_status;
    }

    public int getTotal_number() {
        return total_number;
    }

    public void setTotal_number(int total_number) {
        this.total_number = total_number;
    }

    public String getExtra() {
        return extra;
    }

    public void setExtra(String extra) {
        this.extra = extra;
    }

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

    public String getApi_param() {
        return api_param;
    }

    public void setApi_param(String api_param) {
        this.api_param = api_param;
    }

    public TipsBean getTips() {
        return tips;
    }

    public void setTips(TipsBean tips) {
        this.tips = tips;
    }

    public List<DataBean> getData() {
        return data;
    }

    public void setData(List<DataBean> data) {
        this.data = data;
    }

    public static class TipsBean {
        /**
         * display_info : 今日头条推荐引擎有10条更新
         * open_url :
         * type : app
         * display_duration : 2
         * app_name : 今日头条
         */

        private String display_info;
        private String open_url;
        private String type;
        private int display_duration;
        private String app_name;

        public String getDisplay_info() {
            return display_info;
        }

        public void setDisplay_info(String display_info) {
            this.display_info = display_info;
        }

        public String getOpen_url() {
            return open_url;
        }

        public void setOpen_url(String open_url) {
            this.open_url = open_url;
        }

        public String getType() {
            return type;
        }

        public void setType(String type) {
            this.type = type;
        }

        public int getDisplay_duration() {
            return display_duration;
        }

        public void setDisplay_duration(int display_duration) {
            this.display_duration = display_duration;
        }

        public String getApp_name() {
            return app_name;
        }

        public void setApp_name(String app_name) {
            this.app_name = app_name;
        }
    }

    public static class DataBean {
        /**
         * content : {"cell_height":120,"data_url":"https:\/\/ic.snssdk.com\/wenda\/v1\/channel\/announcement\/","template_url":"http:\/\/ic.snssdk.com\/wenda\/v1\/native\/widget\/?api_param=%7B%22wd_version%22%3A5%7D","data_flag":true,"id":"50052410574","template_md5":"bc58854942d0559324kjl435298234fs","cell_type":25,"is_deleted":false,"behot_time":1495245397,"cursor":1,"refresh_interval":60,"data_callback":"window.refresh"}
         * code :
         */

        private String content;
        private String code;

        public String getContent() {
            return content;
        }

        public void setContent(String content) {
            this.content = content;
        }

        public String getCode() {
            return code;
        }

        public void setCode(String code) {
            this.code = code;
        }
    }
}
