package com.example.flower.mvvm.view.fragment;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.example.flower.BR;
import com.example.flower.R;
import com.example.flower.base.BaseFragment;
import com.example.flower.databinding.FragmentSpecialBinding;
import com.example.flower.mvvm.viewmodel.SpecialViewModel;
import com.scwang.smart.refresh.layout.api.RefreshLayout;
import com.scwang.smart.refresh.layout.listener.OnRefreshLoadMoreListener;

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
    protected void initView() {
        mBinding.rvRecommended.setItemAnimator(null);
        mBinding.srlRefresh.setOnRefreshLoadMoreListener(new OnRefreshLoadMoreListener() {
            @Override
            public void onLoadMore(@NonNull RefreshLayout refreshLayout) {
                mViewModel.getRecommendedToday(true);
            }

            @Override
            public void onRefresh(@NonNull RefreshLayout refreshLayout) {
                mViewModel.refreshData();
            }
        });
        mBinding.srlRefresh.autoRefresh();
    }

    @Override
    protected void initData(@Nullable Bundle savedInstanceState) {
        mViewModel.mBaseLiveData.observe(this, s -> {
            switch (s) {
                case SpecialViewModel.REFRESH_SUCCESS:
                    mBinding.srlRefresh.finishRefresh(true);
                    break;
                case SpecialViewModel.REFRESH_FAIL:
                    mBinding.srlRefresh.finishRefresh(false);
                    break;
                case SpecialViewModel.LOAD_MORE_SUCCESS:
                    mBinding.srlRefresh.finishLoadMore(true);
                    break;
                case SpecialViewModel.LOAD_MORE_FAIL:
                    mBinding.srlRefresh.finishLoadMore(false);
                    break;
                case SpecialViewModel.LOAD_MORE_COMPLETE:
                    mBinding.srlRefresh.finishLoadMoreWithNoMoreData();
                    break;
                default:
                    break;
            }
        });
    }
}
