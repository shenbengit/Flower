package com.example.flower.http.bmob;

import cn.bmob.v3.BmobObject;

/**
 * 评论表
 *
 * @author ShenBen
 * @date 2020/1/5 16:22
 * @email 714081644@qq.com
 */
public class CommentBean extends BmobObject {
    /**
     * 评论内容
     */
    private String content;
    /**
     * 评论作者
     */
    private UserBean user;
    /**
     * 评论对应的帖子
     */
    private PostBean post;

    public CommentBean() {
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

    public PostBean getPost() {
        return post;
    }

    public void setPost(PostBean post) {
        this.post = post;
    }
}
