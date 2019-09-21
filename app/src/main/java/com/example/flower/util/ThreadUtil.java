package com.example.flower.util;

import com.lzh.easythread.Callback;
import com.lzh.easythread.EasyThread;

/**
 * @author ShenBen
 * @date 2019/8/21 14:29
 * @email 714081644@qq.com
 */
public class ThreadUtil {
    private final static EasyThread io;
    private final static EasyThread cache;
    private final static EasyThread calculator;
    private final static EasyThread file;

    public static EasyThread getIO() {
        return io;
    }

    public static EasyThread getCache() {
        return cache;
    }

    public static EasyThread getCalculator() {
        return calculator;
    }

    public static EasyThread getFile() {
        return file;
    }

    static {
        io = EasyThread.Builder.createFixed(6).setName("IO").setPriority(7).setCallback(new DefaultCallback()).build();
        cache = EasyThread.Builder.createCacheable().setName("cache").setCallback(new DefaultCallback()).build();
        calculator = EasyThread.Builder.createFixed(4).setName("calculator").setPriority(Thread.MAX_PRIORITY).setCallback(new DefaultCallback()).build();
        file = EasyThread.Builder.createFixed(4).setName("file").setPriority(3).setCallback(new DefaultCallback()).build();
    }

    private static class DefaultCallback implements Callback {

        @Override
        public void onError(String threadName, Throwable t) {
            LogUtil.e("Task with thread %s has occurs an error: %s", threadName, t.getMessage());
        }

        @Override
        public void onCompleted(String threadName) {
            LogUtil.d("Task with thread %s completed", threadName);
        }

        @Override
        public void onStart(String threadName) {
            LogUtil.d("Task with thread %s start running!", threadName);
        }
    }
}
