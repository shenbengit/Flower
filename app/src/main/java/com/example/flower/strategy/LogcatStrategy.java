package com.example.flower.strategy;

import android.text.TextUtils;
import android.util.Log;

import com.orhanobut.logger.LogStrategy;

import org.jetbrains.annotations.NotNull;

/**
 * @author Ben
 * @date 2018/9/12
 * Email: 714081644@qq.com
 */
public class LogcatStrategy implements LogStrategy {
    private int last;
    private static final String DEFAULT_TAG = "NO_TAG";

    @Override
    public void log(int priority, String tag, @NotNull String message) {
        if (TextUtils.isEmpty(message)) {
            return;
        }
        if (tag == null) {
            tag = DEFAULT_TAG;
        }
        //android studio 3.1 logcat控制台 在短时间内不能同时打印相同tag的日志
        Log.println(priority, randomKey() + tag, message);
    }

    private String randomKey() {
        int random = (int) (10 * Math.random());
        if (random == last) {
            random = (random + 1) % 10;
        }
        last = random;
        return String.valueOf(random);
    }

}
