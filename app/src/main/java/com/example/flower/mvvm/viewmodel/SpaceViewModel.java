package com.example.flower.mvvm.viewmodel;

import android.app.Application;

import androidx.annotation.NonNull;

import com.alibaba.android.arouter.launcher.ARouter;
import com.example.flower.base.BaseViewModel;
import com.example.flower.binding.command.BindingCommand;
import com.example.flower.constant.ARouterPath;

/**
 * @author ShenBen
 * @date 2019/9/22 13:41
 * @email 714081644@qq.com
 */
public class SpaceViewModel extends BaseViewModel {

    /**
     * 发帖点击事件
     */
    public BindingCommand postCommand;

    public SpaceViewModel(@NonNull Application application) {
        super(application);
        postCommand = new BindingCommand(() -> ARouter
                .getInstance()
                .build(ARouterPath.POST_PATH)
                .navigation());
    }
}
