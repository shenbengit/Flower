package com.example.flower.util;


import com.trello.rxlifecycle3.LifecycleProvider;
import com.trello.rxlifecycle3.LifecycleTransformer;

import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;

import io.reactivex.FlowableTransformer;
import io.reactivex.ObservableTransformer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.annotations.NonNull;
import io.reactivex.functions.Function;
import io.reactivex.schedulers.Schedulers;

/**
 * @author Ben
 * @date 2018/9/17
 * Email: 714081644@qq.com
 */
public class RxUtil {
    /**
     * 普通类反射获取泛型方式，获取需要实际解析的类型
     *
     * @param <T>
     * @return
     */
    public static <T> Type findNeedClass(Class<T> cls) {
        //以下代码是通过泛型解析实际参数,泛型必须传
        Type genType = cls.getGenericSuperclass();
        Type[] params = ((ParameterizedType) genType).getActualTypeArguments();
        Type type = params[0];
        Type finalNeedType;
        //这个类是：CacheResult<SkinTestResult> 2层
        if (params.length > 1) {
            if (!(type instanceof ParameterizedType)) {
                throw new IllegalStateException("没有填写泛型参数");
            }
            finalNeedType = ((ParameterizedType) type).getActualTypeArguments()[0];
            //Type rawType = ((ParameterizedType) type).getRawType();
        } else {//这个类似是:SkinTestResult  1层
            finalNeedType = type;
        }
        return finalNeedType;
    }

    /**
     * 线程相关
     * <p>
     * I/O线程：磁盘操作，查询数据库，网络访问，具有线程缓存机制
     * Android主线程
     * </p>
     *
     * @param <T>
     * @return
     */
    public static <T> ObservableTransformer<T, T> io_main() {
        return upstream -> upstream.subscribeOn(Schedulers.io())
                .unsubscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .map((Function<T, T>) t -> t);
    }

    /**
     * 线程相关
     * <p>
     * newThread线程---->虽然使用Schedulers.io()的地方都可以使用Schedulers.newThread(),但是总体上的Schedulers.newThread()的效率没有Schedulers.io()的高。
     * Android主线程
     * </p>
     *
     * @param <T>
     * @return
     */
    public static <T> ObservableTransformer<T, T> newThread_main() {
        return upstream -> upstream.subscribeOn(Schedulers.newThread())
                .unsubscribeOn(Schedulers.newThread())
                .observeOn(AndroidSchedulers.mainThread())
                .map((Function<T, T>) t -> t);
    }

    /**
     * 线程相关
     * <p>
     * computation线程 --->用于cpu密集型计算任务，即不会被被I/O等操作限制性能的耗时操作，例如XML，JSON文件的解析，Bitmap图片的压缩取样等，
     * Android主线程
     * </p>
     *
     * @param <T>
     * @return
     */
    public static <T> ObservableTransformer<T, T> computation_main() {
        return upstream -> upstream.subscribeOn(Schedulers.computation())
                .unsubscribeOn(Schedulers.computation())
                .observeOn(AndroidSchedulers.mainThread())
                .map((Function<T, T>) t -> t);
    }

    /**
     * 背压线程调度封装
     *
     * @param <T>
     * @return
     */
    public static <T> FlowableTransformer<T, T> flowable_io_main() {
        return upstream -> upstream.subscribeOn(Schedulers.io())
                .unsubscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .map(t -> t);
    }

    public static <T> FlowableTransformer<T, T> flowable_newThread_main() {
        return upstream -> upstream.subscribeOn(Schedulers.newThread())
                .unsubscribeOn(Schedulers.newThread())
                .observeOn(AndroidSchedulers.mainThread())
                .map(t -> t);
    }

    public static <T> FlowableTransformer<T, T> flowable_computation_main() {
        return upstream -> upstream.subscribeOn(Schedulers.computation())
                .unsubscribeOn(Schedulers.computation())
                .observeOn(AndroidSchedulers.mainThread())
                .map(t -> t);
    }

    /**
     * 生命周期绑定
     *
     * @param lifecycleProvider RxLifecycle生命周期
     */
    public static <T> LifecycleTransformer bindToLifecycle(@NonNull LifecycleProvider lifecycleProvider) {
        return lifecycleProvider.bindToLifecycle();
    }

}
