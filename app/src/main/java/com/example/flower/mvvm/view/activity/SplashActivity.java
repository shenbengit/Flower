package com.example.flower.mvvm.view.activity;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;

import androidx.annotation.Nullable;

import com.alibaba.android.arouter.launcher.ARouter;
import com.example.flower.BR;
import com.example.flower.R;
import com.example.flower.base.BaseActivity;
import com.example.flower.constant.ARouterPath;
import com.example.flower.databinding.ActivitySplashBinding;
import com.example.flower.mvvm.viewmodel.SplashViewModel;

public class SplashActivity extends BaseActivity<ActivitySplashBinding, SplashViewModel> {
    /**
     * 跳转去MainActivity标识
     */
    public static final String TO_MAIN = "TO_MAIN";

    private static final int MAIN_REQUEST_CODE = 100;

    @Override
    protected int getLayoutId() {
        return R.layout.activity_splash;
    }

    @Override
    protected Class<SplashViewModel> getModelClass() {
        return SplashViewModel.class;
    }

    @Override
    protected int getVariableId() {
        return BR.viewModel;
    }

    @Override
    protected void initData(@Nullable Bundle savedInstanceState) {

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == MAIN_REQUEST_CODE && resultCode == RESULT_OK) {
            onBackPressed();
        }
    }

    @Override
    protected void baseLiveDataObserver(String str) {
        super.baseLiveDataObserver(str);
        if (TextUtils.equals(str, TO_MAIN)) {
            ARouter.getInstance()
                    .build(ARouterPath.MAIN_ACTIVITY_PATH)
                    .navigation(SplashActivity.this, MAIN_REQUEST_CODE);
        }
    }
}
