package com.example.flower.mvvm.view.activity;

import com.example.flower.R;
import com.example.flower.base.BaseActivity;
import com.example.flower.base.BaseViewModel;
import com.example.flower.databinding.ActivityMainBinding;

public class MainActivity extends BaseActivity<ActivityMainBinding, BaseViewModel> {

    @Override
    protected int getLayoutId() {
        return R.layout.activity_main;
    }

    @Override
    protected Class<BaseViewModel> getModelClass() {
        return null;
    }

    @Override
    protected int getVariableId() {
        return 0;
    }

    @Override
    protected void initData() {

    }
}
