package com.example.flower.mvvm.view.fragment;

import android.os.Bundle;

import androidx.annotation.Nullable;

import com.example.flower.BR;
import com.example.flower.R;
import com.example.flower.base.BaseFragment;
import com.example.flower.databinding.FragmentRecommendBinding;
import com.example.flower.mvvm.viewmodel.RecommendViewModel;

/**
 * 推荐数据为
 * 从帖子表里查找喜欢数量最多的数据，降序查询
 *
 * @author ShenBen
 * @date 2020/1/30 18:08
 * @email 714081644@qq.com
 */
public class RecommendFragment extends BaseFragment<FragmentRecommendBinding, RecommendViewModel> {

    public static RecommendFragment newInstance() {
        return new RecommendFragment();
    }

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_recommend;
    }

    @Override
    protected Class<RecommendViewModel> getModelClass() {
        return RecommendViewModel.class;
    }

    @Override
    protected int getVariableId() {
        return BR.viewModel;
    }

    @Override
    protected void initData(@Nullable Bundle savedInstanceState) {

    }
}
