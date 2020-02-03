package com.example.flower.http.bmob;

import java.util.ArrayList;
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
     * 喜欢这个帖子的用户的Id
     */
    private List<String> likesUserIds;
    /**
     * 评论这个帖子的用户的Id
     */
    private List<String> commentUserIds;
    /**
     * 该帖子评论的数量
     * 不能用[commentUserIds.size()],因为同一用户可以评论多次
     */
    private Integer commentNumber;

    public PostBean() {
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public UserBean getAuthor() {
        return author == null ? new UserBean() : author;
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

    public List<String> getLikesUserIds() {
        if (likesUserIds == null) {
            setLikesUserIds(new ArrayList<>());
        }
        return likesUserIds;
    }

    public void setLikesUserIds(List<String> likesUserIds) {
        this.likesUserIds = likesUserIds;
    }

    public List<String> getCommentUserIds() {
        if (commentUserIds == null) {
            setCommentUserIds(new ArrayList<>());
        }
        return commentUserIds;
    }

    public void setCommentUserIds(List<String> commentUserIds) {
        this.commentUserIds = commentUserIds;
    }

    public Integer getCommentNumber() {
        return commentNumber == null ? 0 : commentNumber;
    }

    public void setCommentNumber(Integer commentNumber) {
        this.commentNumber = commentNumber;
    }
}
