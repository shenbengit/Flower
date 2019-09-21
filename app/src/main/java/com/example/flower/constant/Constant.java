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
    /**
     * 文件路径,基础路径
     */
    public static final String FILE_SAVE_PATH = Environment.getExternalStorageDirectory().getPath() + File.separator + "Flower" + File.separator;

    public static final String ERROR_FILE_PATH = FILE_SAVE_PATH + "error" + File.separator;

    public static final int UDP_PORT = 38002;

    /**
     * Bmob云Application ID
     */
    public static final String BMOB_APPLICATION_ID = "945d0c586b07ad3df4d022af5186a3f5";
}
