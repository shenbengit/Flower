package com.example.flower.mvvm.view.fragment;

import android.os.Bundle;

import androidx.annotation.Nullable;

import com.example.flower.R;
import com.example.flower.base.BaseFragment;
import com.example.flower.base.BaseViewModel;
import com.example.flower.databinding.FragmentCollectionBinding;

/**
 * @author ShenBen
 * @date 2020/1/30 18:08
 * @email 714081644@qq.com
 */
public class CollectionFragment extends BaseFragment<FragmentCollectionBinding, BaseViewModel> {

    public static CollectionFragment newInstance() {
        return new CollectionFragment();
    }

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_collection;
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
