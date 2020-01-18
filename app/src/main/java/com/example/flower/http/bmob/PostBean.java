package com.example.flower.http.bmob;

import java.util.List;

import cn.bmob.v3.BmobObject;
import cn.bmob.v3.datatype.BmobFile;
import cn.bmob.v3.datatype.BmobRelation;

/**
 * 帖子表
 *
 * @author ShenBen
 * @date 2020/1/5 16:20
 * @email 714081644@qq.com
 */
public class PostBean extends BmobObject {
    /**
     * 帖子内容
     */
    private String content;
    /**
     * 帖子作者
     */
    private UserBean user;
    /**
     * 喜欢帖子的人
     */
    private BmobRelation likes;
    /**
     * 帖子里面的图片
     */
    private List<BmobFile> pictures;

    public PostBean() {
        super("Post");
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public UserBean getUser() {
        return user;
    }

    public void setUser(UserBean user) {
        this.user = user;
    }

    public BmobRelation getLikes() {
        return likes;
    }

    public void setLikes(BmobRelation likes) {
        this.likes = likes;
    }

    public List<BmobFile> getPictures() {
        return pictures;
    }

    public void setPictures(List<BmobFile> pictures) {
        this.pictures = pictures;
    }
}
