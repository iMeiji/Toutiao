package com.meiji.toutiao.bean.search;

import java.util.List;

/**
 * Created by Meiji on 2017/6/23.
 */

public class SearchRecommentBean {

    /**
     * message : success
     * data : {"page_style":4,"page_font":1,"suggest_words":["郭文贵","尤果","佛","nba","面试技巧","人过五十","肝脏排毒","电动汽车","正阳门下","刘国梁马龙","疏通血管","派遣员工","人一辈子","美国航母","事业单位涨工资","7月1日","心血管疾病","牧羊曲郑绪岚","血管健康","笛子演奏"],"suggest_icon":0,"suggest_word_list":[{"type":"hist","word":"郭文贵"},{"type":"hist","word":"尤果"},{"type":"hist","word":"佛"},{"type":"hist","word":"nba"},{"type":"recom","word":"面试技巧"},{"type":"recom","word":"人过五十"},{"type":"recom","word":"肝脏排毒"},{"type":"recom","word":"电动汽车"},{"type":"recom","word":"正阳门下"},{"type":"recom","word":"刘国梁马龙"},{"type":"recom","word":"疏通血管"},{"type":"recom","word":"派遣员工"},{"type":"recom","word":"人一辈子"},{"type":"recom","word":"美国航母"},{"type":"recom","word":"事业单位涨工资"},{"type":"recom","word":"7月1日"},{"type":"recom","word":"心血管疾病"},{"type":"recom","word":"牧羊曲郑绪岚"},{"type":"recom","word":"血管健康"},{"type":"recom","word":"笛子演奏"}]}
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
         * page_style : 4
         * page_font : 1
         * suggest_words : ["郭文贵","尤果","佛","nba","面试技巧","人过五十","肝脏排毒","电动汽车","正阳门下","刘国梁马龙","疏通血管","派遣员工","人一辈子","美国航母","事业单位涨工资","7月1日","心血管疾病","牧羊曲郑绪岚","血管健康","笛子演奏"]
         * suggest_icon : 0
         * suggest_word_list : [{"type":"hist","word":"郭文贵"},{"type":"hist","word":"尤果"},{"type":"hist","word":"佛"},{"type":"hist","word":"nba"},{"type":"recom","word":"面试技巧"},{"type":"recom","word":"人过五十"},{"type":"recom","word":"肝脏排毒"},{"type":"recom","word":"电动汽车"},{"type":"recom","word":"正阳门下"},{"type":"recom","word":"刘国梁马龙"},{"type":"recom","word":"疏通血管"},{"type":"recom","word":"派遣员工"},{"type":"recom","word":"人一辈子"},{"type":"recom","word":"美国航母"},{"type":"recom","word":"事业单位涨工资"},{"type":"recom","word":"7月1日"},{"type":"recom","word":"心血管疾病"},{"type":"recom","word":"牧羊曲郑绪岚"},{"type":"recom","word":"血管健康"},{"type":"recom","word":"笛子演奏"}]
         */

        private int page_style;
        private int page_font;
        private int suggest_icon;
        private List<String> suggest_words;
        private List<SuggestWordListBean> suggest_word_list;

        public int getPage_style() {
            return page_style;
        }

        public void setPage_style(int page_style) {
            this.page_style = page_style;
        }

        public int getPage_font() {
            return page_font;
        }

        public void setPage_font(int page_font) {
            this.page_font = page_font;
        }

        public int getSuggest_icon() {
            return suggest_icon;
        }

        public void setSuggest_icon(int suggest_icon) {
            this.suggest_icon = suggest_icon;
        }

        public List<String> getSuggest_words() {
            return suggest_words;
        }

        public void setSuggest_words(List<String> suggest_words) {
            this.suggest_words = suggest_words;
        }

        public List<SuggestWordListBean> getSuggest_word_list() {
            return suggest_word_list;
        }

        public void setSuggest_word_list(List<SuggestWordListBean> suggest_word_list) {
            this.suggest_word_list = suggest_word_list;
        }

        public static class SuggestWordListBean {
            /**
             * type : hist
             * word : 郭文贵
             */

            private String type;
            private String word;

            public String getType() {
                return type;
            }

            public void setType(String type) {
                this.type = type;
            }

            public String getWord() {
                return word;
            }

            public void setWord(String word) {
                this.word = word;
            }
        }
    }
}
