package com.example.flower.mvvm.view.fragment;

import android.os.Bundle;

import androidx.annotation.Nullable;

import com.example.flower.R;
import com.example.flower.BR;
import com.example.flower.base.BaseFragment;
import com.example.flower.databinding.FragmentSpecialBinding;
import com.example.flower.mvvm.viewmodel.SpecialViewModel;

/**
 * 专题Fragment
 *
 * @author ShenBen
 * @date 2019/9/22 13:40
 * @email 714081644@qq.com
 */
public class SpecialFragment extends BaseFragment<FragmentSpecialBinding, SpecialViewModel> {

    public static SpecialFragment getInstance() {
        return new SpecialFragment();
    }

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_special;
    }

    @Override
    protected Class<SpecialViewModel> getModelClass() {
        return SpecialViewModel.class;
    }

    @Override
    protected int getVariableId() {
        return BR.viewModel;
    }

    @Override
    protected void initData(@Nullable Bundle savedInstanceState) {

    }
}
