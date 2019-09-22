package com.example.flower.mvvm.view.fragment;

import android.os.Bundle;

import androidx.annotation.Nullable;

import com.example.flower.BR;
import com.example.flower.R;
import com.example.flower.base.BaseFragment;
import com.example.flower.databinding.FragmentCollegeBinding;
import com.example.flower.mvvm.viewmodel.CollegeViewModel;

/**
 * 学院Fragment
 *
 * @author ShenBen
 * @date 2019/9/22 13:40
 * @email 714081644@qq.com
 */
public class CollegeFragment extends BaseFragment<FragmentCollegeBinding, CollegeViewModel> {

    public static CollegeFragment getInstance() {
        return new CollegeFragment();
    }

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_college;
    }

    @Override
    protected Class<CollegeViewModel> getModelClass() {
        return CollegeViewModel.class;
    }

    @Override
    protected int getVariableId() {
        return BR.viewModel;
    }

    @Override
    protected void initData(@Nullable Bundle savedInstanceState) {

    }
}
