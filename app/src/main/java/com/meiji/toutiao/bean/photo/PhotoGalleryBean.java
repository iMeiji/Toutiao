package com.meiji.toutiao.bean.photo;

import java.util.List;

/**
 * Created by Meiji on 2017/2/17.
 */

public class PhotoGalleryBean {
    /**
     * count : 4
     * sub_images : [{"url":"http://p1.pstatp.com/origin/15a200038842f326999a","width":640,"url_list":[{"url":"http://p1.pstatp.com/origin/15a200038842f326999a"},{"url":"http://pb3.pstatp.com/origin/15a200038842f326999a"},{"url":"http://pb3.pstatp.com/origin/15a200038842f326999a"}],"uri":"origin/15a200038842f326999a","height":480},{"url":"http://p3.pstatp.com/origin/159d0006773f6f518b75","width":640,"url_list":[{"url":"http://p3.pstatp.com/origin/159d0006773f6f518b75"},{"url":"http://pb2.pstatp.com/origin/159d0006773f6f518b75"},{"url":"http://pb3.pstatp.com/origin/159d0006773f6f518b75"}],"uri":"origin/159d0006773f6f518b75","height":480},{"url":"http://p3.pstatp.com/origin/15a2000388885d729114","width":640,"url_list":[{"url":"http://p3.pstatp.com/origin/15a2000388885d729114"},{"url":"http://pb2.pstatp.com/origin/15a2000388885d729114"},{"url":"http://pb3.pstatp.com/origin/15a2000388885d729114"}],"uri":"origin/15a2000388885d729114","height":386},{"url":"http://p3.pstatp.com/origin/159d000670614491aa2c","width":640,"url_list":[{"url":"http://p3.pstatp.com/origin/159d000670614491aa2c"},{"url":"http://pb2.pstatp.com/origin/159d000670614491aa2c"},{"url":"http://pb3.pstatp.com/origin/159d000670614491aa2c"}],"uri":"origin/159d000670614491aa2c","height":480}]
     * max_img_width : 640
     * labels : ["董明珠","王健林","SUV","皮卡","新能源"]
     * sub_abstracts : ["董明珠和王健林不仅是好朋友，更是商业上的好伙伴，前段时间，王健林为董明珠注资，支持她打造属于中国的汽车品牌。","图中是一款黑色的SUV，虽然看起来很普通，但是却是以新能源为主而打造出来的，董明珠倡导围绕新能源打造新品牌，符合时代发展规律。","图中是他们预设计的一款皮卡，虽然看起来有点像面包车，但是车的整体性能挺好，储存空间特别大，可以作为商业用车，也可以作为家庭用车。","相信在不久的将来，她能打造出属于我们中国人的汽车品牌，跻身于汽车界，让中国的汽车也走向全世界！"]
     * sub_titles : ["王健林注资，董明珠终于要打造属于中国的汽车品牌！","王健林注资，董明珠终于要打造属于中国的汽车品牌！","王健林注资，董明珠终于要打造属于中国的汽车品牌！","王健林注资，董明珠终于要打造属于中国的汽车品牌！"]
     */

    private int count;
    private int max_img_width;
    private List<SubImagesBean> sub_images;
    private List<String> labels;
    private List<String> sub_abstracts;
    private List<String> sub_titles;

    public int getCount() {
        return count;
    }

    public void setCount(int count) {
        this.count = count;
    }

    public int getMax_img_width() {
        return max_img_width;
    }

    public void setMax_img_width(int max_img_width) {
        this.max_img_width = max_img_width;
    }

    public List<SubImagesBean> getSub_images() {
        return sub_images;
    }

    public void setSub_images(List<SubImagesBean> sub_images) {
        this.sub_images = sub_images;
    }

    public List<String> getLabels() {
        return labels;
    }

    public void setLabels(List<String> labels) {
        this.labels = labels;
    }

    public List<String> getSub_abstracts() {
        return sub_abstracts;
    }

    public void setSub_abstracts(List<String> sub_abstracts) {
        this.sub_abstracts = sub_abstracts;
    }

    public List<String> getSub_titles() {
        return sub_titles;
    }

    public void setSub_titles(List<String> sub_titles) {
        this.sub_titles = sub_titles;
    }

    public static class SubImagesBean {
        /**
         * url : http://p1.pstatp.com/origin/15a200038842f326999a
         * width : 640
         * url_list : [{"url":"http://p1.pstatp.com/origin/15a200038842f326999a"},{"url":"http://pb3.pstatp.com/origin/15a200038842f326999a"},{"url":"http://pb3.pstatp.com/origin/15a200038842f326999a"}]
         * uri : origin/15a200038842f326999a
         * height : 480
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
             * url : http://p1.pstatp.com/origin/15a200038842f326999a
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
