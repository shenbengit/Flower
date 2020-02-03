package com.example.flower.rxbus.bean;

/**
 * @author ShenBen
 * @date 2020/2/3 13:51
 * @email 714081644@qq.com
 */
public class CommandBean {
    /**
     * 需要更新帖子通知
     */
    public static final String COMMAND_UPDATE_POST = "COMMAND_UPDATE_POST";
    /**
     * 用户登录成功通知
     */
    public static final String COMMAND_USER_LOGIN_SUCCESS = "COMMAND_USER_LOGIN_SUCCESS";
    /**
     * 需要更新帖子评论通知
     */
    public static final String COMMAND_UPDATE_COMMENT = "COMMAND_UPDATE_COMMENT";
    /**
     * 帖子发表成功通知
     */
    public static final String COMMAND_PUBLISH_POST_SUCCESS = "COMMAND_PUBLISH_POST_SUCCESS";


    private String command;

    public CommandBean() {
    }

    public CommandBean(String command) {
        this.command = command;
    }

    public String getCommand() {
        return command;
    }

    public void setCommand(String command) {
        this.command = command;
    }
}
