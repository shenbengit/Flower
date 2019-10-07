package com.example.flower.base;

import com.trello.rxlifecycle3.LifecycleProvider;

/**
 * @author ShenBen
 * @date 2018/12/4 15:19
 * @email 714081644@qq.com
 */
public interface IBaseModel {
    /**
     * 设置LifecycleProvider
     * @param provider
     */
    void setLifecycleProvider(LifecycleProvider provider);

    /**
     * 进行资源回收释放
     */
    void onDestroy();
}
