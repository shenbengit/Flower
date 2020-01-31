package com.example.flower.mvvm.view.fragment;

import android.os.Bundle;

import androidx.annotation.Nullable;

import com.example.flower.R;
import com.example.flower.base.BaseFragment;
import com.example.flower.base.BaseViewModel;
import com.example.flower.databinding.FragmentPopularBinding;

/**
 * 热门 Fragment
 *
 * @author ShenBen
 * @date 2020/1/30 16:50
 * @email 714081644@qq.com
 */
public class PopularFragment extends BaseFragment<FragmentPopularBinding, BaseViewModel> {

    public static PopularFragment newInstance() {
        return new PopularFragment();
    }

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_popular;
    }

    @Override
    protected Class<BaseViewModel> getModelClass() {
        return BaseViewModel.class;
    }

    @Override
    protected int getVariableId() {
        return 0;
    }

    @Override
    protected void initData(@Nullable Bundle savedInstanceState) {

    }
}
