package com.example.flower.http.bean;

import java.util.List;

/**
 * @author ShenBen
 * @date 2019/10/7 16:07
 * @email 714081644@qq.com
 */
public class SpecialDetailBean {

    /**
     * code : 000000
     * text : 成功
     * data : [{"id":"01e30f33-c143-41e9-8255-d228ba8d18a7","coverImg":"http://static.htxq.net/UploadFiles/2017/11/14/test/20171114223114839.jpg","name":"欧式经典半球形桌花","readCount":75175,"cateName":"西式","detailUrl":"/shop/articleAction/preview.do?fnId=01e30f33-c143-41e9-8255-d228ba8d18a7","isVideo":false}]
     */

    private String code;
    private String text;
    private List<DataBean> data;

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public List<DataBean> getData() {
        return data;
    }

    public void setData(List<DataBean> data) {
        this.data = data;
    }

    public static class DataBean {
        /**
         * id : 01e30f33-c143-41e9-8255-d228ba8d18a7
         * coverImg : http://static.htxq.net/UploadFiles/2017/11/14/test/20171114223114839.jpg
         * name : 欧式经典半球形桌花
         * readCount : 75175
         * cateName : 西式
         * detailUrl : /shop/articleAction/preview.do?fnId=01e30f33-c143-41e9-8255-d228ba8d18a7
         * isVideo : false
         */

        private String id;
        private String coverImg;
        private String name;
        private int readCount;
        private String cateName;
        private String detailUrl;
        private boolean isVideo;
        private int imageWidth;
        private int imageHeight;

        public String getId() {
            return id;
        }

        public void setId(String id) {
            this.id = id;
        }

        public String getCoverImg() {
            return coverImg;
        }

        public void setCoverImg(String coverImg) {
            this.coverImg = coverImg;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public int getReadCount() {
            return readCount;
        }

        public void setReadCount(int readCount) {
            this.readCount = readCount;
        }

        public String getCateName() {
            return cateName;
        }

        public void setCateName(String cateName) {
            this.cateName = cateName;
        }

        public String getDetailUrl() {
            return detailUrl;
        }

        public void setDetailUrl(String detailUrl) {
            this.detailUrl = detailUrl;
        }

        public boolean isIsVideo() {
            return isVideo;
        }

        public void setIsVideo(boolean isVideo) {
            this.isVideo = isVideo;
        }

        public int getImageWidth() {
            return imageWidth;
        }

        public void setImageWidth(int imageWidth) {
            this.imageWidth = imageWidth;
        }

        public int getImageHeight() {
            return imageHeight;
        }

        public void setImageHeight(int imageHeight) {
            this.imageHeight = imageHeight;
        }
    }
}
