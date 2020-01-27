package com.example.flower.mvvm.view.activity;

import android.os.Bundle;

import androidx.annotation.Nullable;

import com.alibaba.android.arouter.facade.annotation.Route;
import com.example.flower.BR;
import com.example.flower.R;
import com.example.flower.base.BaseActivity;
import com.example.flower.constant.ARouterPath;
import com.example.flower.databinding.ActivityTodayBinding;
import com.example.flower.mvvm.viewmodel.TodayViewModel;
import com.gyf.immersionbar.ImmersionBar;

@Route(path = ARouterPath.TODAY_ACTIVITY_PATH)
public class TodayActivity extends BaseActivity<ActivityTodayBinding, TodayViewModel> {

    @Override
    protected int getLayoutId() {
        return R.layout.activity_today;
    }

    @Override
    protected Class<TodayViewModel> getModelClass() {
        return TodayViewModel.class;
    }

    @Override
    protected int getVariableId() {
        return BR.viewModel;
    }

    @Override
    protected void initView() {
        super.initView();
        ImmersionBar.with(this)
                .transparentBar()
                .init();

    }

    @Override
    protected void initData(@Nullable Bundle savedInstanceState) {

    }

    @Override
    protected void baseLiveDataObserver(String str) {
        super.baseLiveDataObserver(str);
        if (TodayViewModel.ACTIVITY_CLOSE.equals(str)) {
            onBackPressedSupport();
        }
    }
}
