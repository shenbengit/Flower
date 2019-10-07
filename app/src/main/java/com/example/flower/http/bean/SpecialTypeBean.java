package com.example.flower.http.bean;

import java.util.List;

/**
 * @author ShenBen
 * @date 2019/10/7 15:11
 * @email 714081644@qq.com
 */
public class SpecialTypeBean {

    /**
     * code : 000000
     * text : 成功
     * data : [{"id":"67edceb6-9611-40d3-8cef-41e46389e2de","name":"西式"},{"id":"91c6c8ca-6cb1-438f-9f63-9f4a012fd45c","name":"中式"},{"id":"851bbbc0-00da-498c-a0a3-efed0aa21ace","name":"小技巧"},{"id":"1f332c50-69ec-4d5c-8ed5-9cbedba74fa9","name":"花间小事"}]
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
         * id : 67edceb6-9611-40d3-8cef-41e46389e2de
         * name : 西式
         */

        private String id;
        private String name;
        private boolean isChecked;

        public String getId() {
            return id;
        }

        public void setId(String id) {
            this.id = id;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public boolean isChecked() {
            return isChecked;
        }

        public void setChecked(boolean checked) {
            isChecked = checked;
        }
    }
}
