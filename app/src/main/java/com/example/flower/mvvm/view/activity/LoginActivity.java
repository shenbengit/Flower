package com.example.flower.mvvm.view.activity;

import android.os.Bundle;
import android.text.TextUtils;

import androidx.annotation.Nullable;

import com.alibaba.android.arouter.facade.annotation.Route;
import com.example.flower.BR;
import com.example.flower.R;
import com.example.flower.base.BaseActivity;
import com.example.flower.constant.ARouterPath;
import com.example.flower.databinding.ActivityLoginBinding;
import com.example.flower.mvvm.viewmodel.LoginViewModel;
import com.example.flower.rxbus.RxBus;
import com.example.flower.rxbus.bean.CommandBean;

/**
 * 登录页
 */
@Route(path = ARouterPath.LOGIN_PATH)
public class LoginActivity extends BaseActivity<ActivityLoginBinding, LoginViewModel> {

    @Override
    protected int getLayoutId() {
        return R.layout.activity_login;
    }

    @Override
    protected Class<LoginViewModel> getModelClass() {
        return LoginViewModel.class;
    }

    @Override
    protected int getVariableId() {
        return BR.viewModel;
    }

    @Override
    protected void initView() {
        mBinding.toolbar.setTitle("登录");
        initToolbar(mBinding.toolbar);
    }

    @Override
    protected void initData(@Nullable Bundle savedInstanceState) {

    }

    @Override
    protected void baseLiveDataObserver(String str) {
        super.baseLiveDataObserver(str);
        if (TextUtils.equals(LoginViewModel.LOGIN_SUCCESS, str)) {
            RxBus.getDefault()
                    .postSticky(new CommandBean(CommandBean.COMMAND_USER_LOGIN_SUCCESS));

            onBackPressedSupport();
        }
    }
}
