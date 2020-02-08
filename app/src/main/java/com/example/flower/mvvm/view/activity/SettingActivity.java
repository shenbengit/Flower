package com.example.flower.mvvm.view.activity;

import android.annotation.SuppressLint;
import android.os.Bundle;

import androidx.annotation.Nullable;

import com.alibaba.android.arouter.facade.annotation.Route;
import com.alibaba.android.arouter.launcher.ARouter;
import com.example.flower.R;
import com.example.flower.base.BaseActivity;
import com.example.flower.base.BaseViewModel;
import com.example.flower.constant.ARouterPath;
import com.example.flower.databinding.ActivitySettingBinding;
import com.example.flower.mvvm.view.widget.LoginOutDialog;
import com.example.flower.rxbus.RxBus;
import com.example.flower.rxbus.bean.CommandBean;
import com.example.flower.util.ToastUtil;
import com.jakewharton.rxbinding3.view.RxView;

import java.util.concurrent.TimeUnit;

import cn.bmob.v3.BmobUser;

/**
 * 设置页面
 */
@Route(path = ARouterPath.SETTING_PATH)
public class SettingActivity extends BaseActivity<ActivitySettingBinding, BaseViewModel> {

    private LoginOutDialog mLoginOutDialog;

    @Override
    protected int getLayoutId() {
        return R.layout.activity_setting;
    }

    @Override
    protected Class<BaseViewModel> getModelClass() {
        return BaseViewModel.class;
    }

    @Override
    protected int getVariableId() {
        return 0;
    }

    @SuppressLint("CheckResult")
    @Override
    protected void initView() {
        super.initView();
        mLoginOutDialog = new LoginOutDialog(this);
        mLoginOutDialog.setOnLoginOutListener(() -> {
            RxBus.getDefault().postSticky(new CommandBean(CommandBean.COMMAND_USER_LOGIN_OUT));
            //退出登录
            BmobUser.logOut();
            //跳转到登录页
            ARouter.getInstance()
                    .build(ARouterPath.LOGIN_PATH)
                    .navigation();

            onBackPressedSupport();
        });
        mBinding.toolbar.setTitle("设置");
        initToolbar(mBinding.toolbar);

        RxView.clicks(mBinding.viewUserBg)
                .throttleFirst(1, TimeUnit.SECONDS)
                .subscribe(unit -> ARouter.getInstance()
                        .build(ARouterPath.USER_INFO_PATH)
                        .navigation());

        RxView.clicks(mBinding.viewFeedbackBg)
                .throttleFirst(1, TimeUnit.SECONDS)
                .subscribe(unit -> ARouter.getInstance()
                        .build(ARouterPath.FEEDBACK_PATH)
                        .navigation());

        RxView.clicks(mBinding.viewAboutBg)
                .throttleFirst(1, TimeUnit.SECONDS)
                .subscribe(unit -> {
                    ToastUtil.normal(getApplication(), "功能开发中...");
                });

        RxView.clicks(mBinding.btnLoginOut)
                .throttleFirst(1, TimeUnit.SECONDS)
                .subscribe(unit -> {
                    if (BmobUser.isLogin()) {
                        mLoginOutDialog.show();
                    } else {
                        ToastUtil.warning(getApplication(), "请先登录！");
                    }
                });
    }

    @Override
    protected void initData(@Nullable Bundle savedInstanceState) {

    }
}
