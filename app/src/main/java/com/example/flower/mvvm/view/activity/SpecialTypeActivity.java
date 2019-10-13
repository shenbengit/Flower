package com.example.flower.mvvm.view.activity;

import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.Nullable;

import com.alibaba.android.arouter.facade.annotation.Route;
import com.example.flower.BR;
import com.example.flower.R;
import com.example.flower.base.BaseActivity;
import com.example.flower.constant.ARouterPath;
import com.example.flower.databinding.ActivitySpecialTypeBinding;
import com.example.flower.http.bean.HomePageBean;
import com.example.flower.mvvm.view.adapter.SpecialTypeAdapter;
import com.example.flower.mvvm.viewmodel.SpecialTypeViewModel;
import com.google.android.material.tabs.TabLayoutMediator;

/**
 * 专题分类
 */
@Route(path = ARouterPath.SPECIAL_TYPE_ACTIVITY_PATH)
public class SpecialTypeActivity extends BaseActivity<ActivitySpecialTypeBinding, SpecialTypeViewModel> {
    public static final String SPECIAL_DETAIL = "SPECIAL_DETAIL";

    private SpecialTypeAdapter mVpAdapter;
    private HomePageBean.DataBean.CommunityHomePageSecondPlateViewBean.CategoryForSecondPlateViewsBean mBean;

    @Override
    protected int getLayoutId() {
        return R.layout.activity_special_type;
    }

    @Override
    protected Class<SpecialTypeViewModel> getModelClass() {
        return SpecialTypeViewModel.class;
    }

    @Override
    protected int getVariableId() {
        return BR.viewModel;
    }

    @Override
    protected void initView() {
        super.initView();
        Intent intent = getIntent();
        if (intent != null) {
            mBean = intent.getParcelableExtra(SPECIAL_DETAIL);
        }
        if (mBean != null) {
            mBinding.toolbar.setTitle(mBean.getName());
        }
        initToolbar(mBinding.toolbar);
        mVpAdapter = new SpecialTypeAdapter(this);
        mBinding.vpDetail.setAdapter(mVpAdapter);

        TabLayoutMediator tabLayoutMediator = new TabLayoutMediator(mBinding.tabLayout,
                mBinding.vpDetail, (tab, position) -> tab.setText(mVpAdapter.getData().get(position).getName()));
        tabLayoutMediator.attach();
    }

    @Override
    protected void initData(@Nullable Bundle savedInstanceState) {
        mViewModel.mSpecialTypeData.observe(this, bean -> {
            if (bean.getData() != null && !bean.getData().isEmpty()) {
                mVpAdapter.setNewData(bean.getData());
            }
        });
        if (mBean != null) {
            mViewModel.getSpecialTypeList(mBean.getId());
        }

    }
}
