package com.example.flower.http.bmob;

import cn.bmob.v3.BmobObject;

/**
 * 意见反馈表
 *
 * @author ShenBen
 * @date 2020/2/8 15:17
 * @email 714081644@qq.com
 */
public class FeedbackBean extends BmobObject {

    private UserBean user;
    /**
     * 意见内容
     */
    private String opinion;
    /**
     * 联系方式
     * 手机号、QQ号、邮箱地址
     */
    private String contactInformation;

    public FeedbackBean() {
    }

    public UserBean getUser() {
        return user;
    }

    public void setUser(UserBean user) {
        this.user = user;
    }

    public String getOpinion() {
        return opinion;
    }

    public void setOpinion(String opinion) {
        this.opinion = opinion;
    }

    public String getContactInformation() {
        return contactInformation;
    }

    public void setContactInformation(String contactInformation) {
        this.contactInformation = contactInformation;
    }
}
