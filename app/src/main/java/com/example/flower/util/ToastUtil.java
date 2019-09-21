package com.example.flower.util;

import android.annotation.SuppressLint;
import android.content.Context;
import android.widget.Toast;

/**
 * 封装Toast,避免重复显示
 *
 * @author Ben
 * @date 2018/8/7
 * Email: 714081644@qq.com
 */

public class ToastUtil {
    private static Toast toast;

    /**
     * 静态toast
     * @param context
     * @param text
     */
    @SuppressLint("ShowToast")
    public static void show(Context context, String text) {
        // 1. 创建前 2.消失后toast为null
        if (toast == null) {
            toast = Toast.makeText(context, text, Toast.LENGTH_SHORT);
        }
        toast.setText(text);
        toast.show();
    }
}
