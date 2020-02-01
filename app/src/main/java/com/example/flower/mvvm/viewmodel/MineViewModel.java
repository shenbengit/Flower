package com.example.flower.mvvm.viewmodel;

import android.app.Application;

import androidx.annotation.NonNull;

import com.alibaba.android.arouter.launcher.ARouter;
import com.example.flower.base.BaseViewModel;
import com.example.flower.binding.command.BindingCommand;
import com.example.flower.constant.ARouterPath;
import com.example.flower.http.bmob.PostBean;
import com.example.flower.http.bmob.PostTypeBean;
import com.example.flower.http.bmob.UserBean;

import java.util.List;

import cn.bmob.v3.BmobQuery;
import cn.bmob.v3.BmobUser;
import cn.bmob.v3.datatype.BmobPointer;
import cn.bmob.v3.exception.BmobException;
import cn.bmob.v3.listener.FindListener;

/**
 * @author ShenBen
 * @date 2019/9/22 13:41
 * @email 714081644@qq.com
 */
public class MineViewModel extends BaseViewModel {

    public BindingCommand loginCommand;

    private UserBean mCurrentUser;

    public MineViewModel(@NonNull Application application) {
        super(application);
        loginCommand = new BindingCommand(() -> {
            ARouter.getInstance()
                    .build(ARouterPath.LOGIN_PATH)
                    .navigation();
        });
    }

    @Override
    public void onCreate() {
        super.onCreate();

    }

    @Override
    public void onStart() {
        super.onStart();
        updateCurrentUserInfo();
    }

    /**
     * 更新当前登录的用户信息
     */
    private void updateCurrentUserInfo() {
        mCurrentUser = BmobUser.getCurrentUser(UserBean.class);
        if (mCurrentUser != null) {

        }
    }
}
