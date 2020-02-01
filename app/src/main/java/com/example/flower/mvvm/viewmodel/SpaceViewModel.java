package com.example.flower.mvvm.viewmodel;

import android.app.Application;

import androidx.annotation.NonNull;

import com.alibaba.android.arouter.launcher.ARouter;
import com.example.flower.base.BaseViewModel;
import com.example.flower.binding.command.BindingCommand;
import com.example.flower.constant.ARouterPath;
import com.example.flower.util.ToastUtil;

import cn.bmob.v3.BmobUser;

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
        postCommand = new BindingCommand(() -> {
            if (BmobUser.isLogin()) {
                //如果已经登录，则跳转到发表帖子页面
                ARouter.getInstance()
                        .build(ARouterPath.PUBLISH_POST_PATH)
                        .navigation();
            } else {
                ToastUtil.warning(getApplication(), "请先登录哦！");
                //如果当前没有用户登录，则跳转到登录页
                ARouter.getInstance()
                        .build(ARouterPath.LOGIN_PATH)
                        .navigation();
            }
        });
    }
}
