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
    private UserBean author;
    /**
     * 喜欢帖子的人
     */
    private BmobRelation likes;
    /**
     * 帖子里面的图片
     */
    private List<BmobFile> pictures;
    /**
     * 帖子的类型
     */
    private PostTypeBean postType;
    /**
     * 喜欢这个帖子的数量
     */
    private Integer likesNumber = 0;
    /**
     * 评论这个帖子的数量
     */
    private Integer commentNumber = 0;

    public PostBean() {
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public UserBean getAuthor() {
        return author;
    }

    public void setAuthor(UserBean author) {
        this.author = author;
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

    public PostTypeBean getPostType() {
        return postType;
    }

    public void setPostType(PostTypeBean postType) {
        this.postType = postType;
    }

    public Integer getLikesNumber() {
        return likesNumber;
    }

    public void setLikesNumber(Integer likesNumber) {
        this.likesNumber = likesNumber;
    }

    public Integer getCommentNumber() {
        return commentNumber;
    }

    public void setCommentNumber(Integer commentNumber) {
        this.commentNumber = commentNumber;
    }
}
