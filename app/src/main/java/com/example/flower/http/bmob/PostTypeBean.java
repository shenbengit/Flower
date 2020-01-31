package com.example.flower.http.bmob;

import cn.bmob.v3.BmobObject;

/**
 * 帖子类型表
 *
 * @author ShenBen
 * @date 2020/1/29 19:36
 * @email 714081644@qq.com
 */
public class PostTypeBean extends BmobObject {
    /**
     * 帖子类型id
     * 和PostBean表中postType关联
     */
    private Integer typeId;
    /**
     * 帖子类型名称
     */
    private String typeName;
    /**
     * 是否启用
     */
    private Boolean isEnable;

    public PostTypeBean() {
    }

    public Integer getTypeId() {
        return typeId;
    }

    public void setTypeId(Integer typeId) {
        this.typeId = typeId;
    }

    public String getTypeName() {
        return typeName;
    }

    public void setTypeName(String typeName) {
        this.typeName = typeName;
    }

    public Boolean getEnable() {
        return isEnable;
    }

    public void setEnable(Boolean enable) {
        isEnable = enable;
    }
}
