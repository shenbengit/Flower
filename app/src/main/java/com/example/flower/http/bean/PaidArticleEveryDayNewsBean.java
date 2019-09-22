package com.example.flower.http.bean;

import java.util.List;

/**
 * @author ShenBen
 * @date 2019/9/22 10:11
 * @email 714081644@qq.com
 */
public class PaidArticleEveryDayNewsBean {

    /**
     * code : 000000
     * text : 成功
     * data : []
     */

    private String code;
    private String text;
    private List<?> data;

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

    public List<?> getData() {
        return data;
    }

    public void setData(List<?> data) {
        this.data = data;
    }
}
