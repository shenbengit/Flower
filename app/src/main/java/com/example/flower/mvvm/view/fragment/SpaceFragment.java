package com.example.flower.mvvm.view.fragment;

import android.os.Bundle;

import androidx.annotation.Nullable;

import com.example.flower.BR;
import com.example.flower.R;
import com.example.flower.base.BaseFragment;
import com.example.flower.databinding.FragmentSpaceBinding;
import com.example.flower.mvvm.view.adapter.SpaceFragmentAdapter;
import com.example.flower.mvvm.viewmodel.SpaceViewModel;
import com.google.android.material.tabs.TabLayoutMediator;

import java.util.Arrays;

/**
 * 花展Fragment
 *
 * @author ShenBen
 * @date 2019/9/22 13:40
 * @email 714081644@qq.com
 */
public class SpaceFragment extends BaseFragment<FragmentSpaceBinding, SpaceViewModel> {
    public static final String POPULAR = "热门";
    public static final String COLLECTION = "收藏";

    public static SpaceFragment newInstance() {
        return new SpaceFragment();
    }

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_space;
    }

    @Override
    protected Class<SpaceViewModel> getModelClass() {
        return SpaceViewModel.class;
    }

    @Override
    protected int getVariableId() {
        return BR.viewModel;
    }

    @Override
    protected void initView() {
        super.initView();
        SpaceFragmentAdapter adapter = new SpaceFragmentAdapter(this);
        mBinding.vpDetail.setAdapter(adapter);
        TabLayoutMediator tabLayoutMediator = new TabLayoutMediator(mBinding.tabLayout,
                mBinding.vpDetail, (tab, position) -> tab.setText(adapter.getItem(position)));
        tabLayoutMediator.attach();

        adapter.setNewData(Arrays.asList(POPULAR, COLLECTION));
    }

    @Override
    protected void initData(@Nullable Bundle savedInstanceState) {

    }
}
