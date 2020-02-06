package com.example.flower.mvvm.view.fragment;

import android.os.Bundle;

import androidx.annotation.Nullable;

import com.example.flower.BR;
import com.example.flower.R;
import com.example.flower.base.BaseFragment;
import com.example.flower.databinding.FragmentMineBinding;
import com.example.flower.mvvm.viewmodel.MineViewModel;

/**
 * 我的Fragment
 *
 * @author ShenBen
 * @date 2019/9/22 13:40
 * @email 714081644@qq.com
 */
public class MineFragment extends BaseFragment<FragmentMineBinding, MineViewModel> {

    public static MineFragment newInstance() {
        return new MineFragment();
    }

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_mine;
    }

    @Override
    protected Class<MineViewModel> getModelClass() {
        return MineViewModel.class;
    }

    @Override
    protected int getVariableId() {
        return BR.viewModel;
    }

    @Override
    protected void initView() {
        super.initView();
    }

    @Override
    protected void initData(@Nullable Bundle savedInstanceState) {

    }

    /**
     * 当前Fragment可见时调用
     */
    @Override
    public void onSupportVisible() {
        super.onSupportVisible();
        mViewModel.onFragmentVisible();
    }
}
