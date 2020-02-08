package com.example.flower.mvvm.view.activity;

import android.os.Bundle;

import androidx.annotation.Nullable;

import com.alibaba.android.arouter.facade.annotation.Route;
import com.example.flower.R;
import com.example.flower.BR;
import com.example.flower.base.BaseActivity;
import com.example.flower.constant.ARouterPath;
import com.example.flower.databinding.ActivityFeedbackBinding;
import com.example.flower.mvvm.viewmodel.FeedbackViewModel;

/**
 * 意见反馈页面
 */
@Route(path = ARouterPath.FEEDBACK_PATH)
public class FeedbackActivity extends BaseActivity<ActivityFeedbackBinding, FeedbackViewModel> {

    @Override
    protected int getLayoutId() {
        return R.layout.activity_feedback;
    }

    @Override
    protected Class<FeedbackViewModel> getModelClass() {
        return FeedbackViewModel.class;
    }

    @Override
    protected int getVariableId() {
        return BR.viewModel;
    }

    @Override
    protected void initView() {
        super.initView();
        mBinding.toolbar.setTitle("意见反馈");
        initToolbar(mBinding.toolbar);
    }

    @Override
    protected void initData(@Nullable Bundle savedInstanceState) {

    }
}
