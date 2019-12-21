package com.example.flower.constant;

import android.os.Environment;

import java.io.File;

/**
 * @author Ben
 * @date 2018/9/12
 * Email: 714081644@qq.com
 */
public class Constant {
    public static final String TAG = "Flower";

    public static final String BASE_URL = "http://api.htxq.net";
    /**
     * 文件路径,基础路径
     */
    public static final String FILE_SAVE_PATH = Environment.getExternalStorageDirectory().getPath() + File.separator + "Flower" + File.separator;
    /**
     * 图片保存路径
     */
    public static final String WALLPAPER_SAVE_PATH = FILE_SAVE_PATH + "wallpaper" + File.separator;
    public static final String ERROR_FILE_PATH = FILE_SAVE_PATH + "error" + File.separator;
    /**
     * 网络请求成功
     */
    public static final String RESULT_OK = "000000";

    /**
     * Bmob云Application ID
     */
    public static final String BMOB_APPLICATION_ID = "945d0c586b07ad3df4d022af5186a3f5";
    /**
     * 花田APP 用户id
     */
    public static final String USER_ID = "55977b26-8acd-494c-acf0-6f9b948e4095";

    /**
     * 下拉刷新成功
     */
    public static final String REFRESH_SUCCESS = "REFRESH_SUCCESS";
    /**
     * 下拉刷新失败
     */
    public static final String REFRESH_FAIL = "REFRESH_FAIL";
    /**
     * 上拉加载成功
     */
    public static final String LOAD_MORE_SUCCESS = "LOAD_MORE_SUCCESS";
    /**
     * 上拉加载失败
     */
    public static final String LOAD_MORE_FAIL = "LOAD_MORE_FAIL";
    /**
     * 上拉加载完所有数据
     */
    public static final String LOAD_MORE_COMPLETE = "LOAD_MORE_COMPLETE";
    /**
     * 重置没有更多数据的状态
     */
    public static final String RESET_NO_MORE_DATA = "RESET_NO_MORE_DATA";

    /**
     * 显示dialog
     */
    public static final String SHOW_DIALOG = "SHOW_DIALOG";
    /**
     * 隐藏dialog
     */
    public static final String DISMISS_DIALOG = "DISMISS_DIALOG";

}
