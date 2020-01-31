package com.example.flower.http.bmob;

import cn.bmob.v3.BmobObject;

/**
 * @author ShenBen
 * @date 2019/10/6 19:46
 * @email 714081644@qq.com
 */
public class ArticleBean extends BmobObject {
    private Integer id;
    private String enName;
    private String cnName;
    private String imgUrl;
    private Integer type;
    private String detailUrl;

    public ArticleBean() {

    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getEnName() {
        return enName;
    }

    public void setEnName(String enName) {
        this.enName = enName;
    }

    public String getCnName() {
        return cnName;
    }

    public void setCnName(String cnName) {
        this.cnName = cnName;
    }

    public String getImgUrl() {
        return imgUrl;
    }

    public void setImgUrl(String imgUrl) {
        this.imgUrl = imgUrl;
    }

    public Integer getType() {
        return type;
    }

    public void setType(Integer type) {
        this.type = type;
    }

    public String getDetailUrl() {
        return detailUrl;
    }

    public void setDetailUrl(String detailUrl) {
        this.detailUrl = detailUrl;
    }
}
