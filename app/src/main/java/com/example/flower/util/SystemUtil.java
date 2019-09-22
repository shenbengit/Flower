package com.example.flower.util;

import android.os.Build;

/**
 * 获取手机系统参数
 *
 * @author ShenBen
 * @date 2019/9/21 23:39
 * @email 714081644@qq.com
 */
public class SystemUtil {

    private static String sIMEI;

    /**
     * @return 当前手机系统版本号
     */
    public static String getSystemVersion() {
        return Build.VERSION.RELEASE;
    }

    /**
     * @return 当前手机型号
     */
    public static String getSystemModel() {
        return Build.MODEL;
    }

    /**
     * @return 当前手机厂商
     */
    public static String getDeviceBrand() {
        return Build.BRAND;
    }
}
