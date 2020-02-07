package com.example.flower.http.bmob;

import android.text.TextUtils;

import com.example.flower.constant.Constant;

import cn.bmob.v3.BmobUser;
import cn.bmob.v3.datatype.BmobFile;

/**
 * 用户表
 *
 * @author ShenBen
 * @date 2020/1/5 15:23
 * @email 714081644@qq.com
 */
public class UserBean extends BmobUser {
    /**
     * 设置昵称
     */
    private String nickName;
    /**
     * 性别
     */
    private String sex;
    /**
     * 生日
     */
    private String birthday;
    /**
     * 头像
     */
    private BmobFile headImg;
    /**
     * 个性签名
     */
    private String signature;

    public UserBean() {
    }

    public String getNickName() {
        return TextUtils.isEmpty(nickName) ? "用户" + ((getObjectId().length() > 6) ? getObjectId().substring(1, 5) : "") : nickName;
    }

    public String getTrueNickName() {
        return nickName;
    }

    public void setNickName(String nickName) {
        this.nickName = nickName;
    }

    public String getSex() {
        return sex;
    }

    public void setSex(String sex) {
        this.sex = sex;
    }

    public String getBirthday() {
        return birthday;
    }

    public void setBirthday(String birthday) {
        this.birthday = birthday;
    }

    public BmobFile getHeadImg() {
        return headImg;
    }

    public void setHeadImg(BmobFile headImg) {
        this.headImg = headImg;
    }

    public String getSignature() {
        return TextUtils.isEmpty(signature) ? Constant.DEFAULT_SIGNATURE : signature;
    }

    public String getTrueSignature() {
        return signature;
    }

    public void setSignature(String signature) {
        this.signature = signature;
    }
}
