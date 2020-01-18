package com.example.flower.util;

import android.content.Context;

import es.dmoral.toasty.Toasty;

/**
 * 封装Toast,避免重复显示
 *
 * @author Ben
 * @date 2018/8/7
 * Email: 714081644@qq.com
 */

public class ToastUtil {

    public static void error(Context context, String msg) {
        Toasty.error(context, msg).show();
    }

    public static void success(Context context, String msg) {
        Toasty.success(context, msg).show();
    }

    public static void info(Context context, String msg) {
        Toasty.info(context, msg).show();
    }

    public static void warning(Context context, String msg) {
        Toasty.warning(context, msg).show();
    }

    public static void normal(Context context, String msg) {
        Toasty.normal(context, msg).show();
    }
}
