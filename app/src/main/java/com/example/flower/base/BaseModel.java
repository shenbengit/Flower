package com.example.flower.base;

import com.trello.rxlifecycle3.LifecycleProvider;

/**
 * @author ShenBen
 * @date 2018/12/4 15:20
 * @email 714081644@qq.com
 */
public class BaseModel implements IBaseModel {

    protected LifecycleProvider mLifecycleProvider;

    public void setLifecycleProvider(LifecycleProvider provider) {
        mLifecycleProvider = provider;
    }

    @Override
    public void onDestroy() {

    }
}
