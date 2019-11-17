package com.example.flower.mvvm.view.fragment;

import android.os.Bundle;

import androidx.annotation.Nullable;

import com.example.flower.R;
import com.example.flower.base.BaseFragment;
import com.example.flower.base.BaseViewModel;
import com.example.flower.databinding.FragmentFindBinding;
import com.example.flower.mvvm.view.adapter.FragmentFindAdapter;
import com.google.android.material.tabs.TabLayoutMediator;

/**
 * 发现Fragment
 *
 * @author ShenBen
 * @date 2019/11/17 13:40
 * @email 714081644@qq.com
 */
public class FindFragment extends BaseFragment<FragmentFindBinding, BaseViewModel> {
    /**
     * 认识植物Fragment标志位置
     */
    public static final int KNOW_PLANTS_TAG = 0;
    /**
     * 壁纸Fragment标志位置
     */
    public static final int WALLPAPER_TAG = 1;

    public static FindFragment newInstance() {
        return new FindFragment();
    }

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_find;
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
    protected void initView() {
        super.initView();
        FragmentFindAdapter adapter = new FragmentFindAdapter(this);
        mBinding.viewPager.setAdapter(adapter);
        TabLayoutMediator tabLayoutMediator = new TabLayoutMediator(mBinding.tabLayout,
                mBinding.viewPager, (tab, position) -> {
            if (position == KNOW_PLANTS_TAG) {
                tab.setText("认识植物");
            } else if (position == WALLPAPER_TAG) {
                tab.setText("壁纸");
            }
        });
        tabLayoutMediator.attach();
    }

    @Override
    protected void initData(@Nullable Bundle savedInstanceState) {

    }
}
