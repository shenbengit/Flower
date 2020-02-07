package com.example.flower.mvvm.view.fragment;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.example.flower.BR;
import com.example.flower.R;
import com.example.flower.base.BaseFragment;
import com.example.flower.constant.Constant;
import com.example.flower.databinding.FragmentRecommendBinding;
import com.example.flower.mvvm.viewmodel.RecommendViewModel;
import com.scwang.smart.refresh.layout.api.RefreshLayout;
import com.scwang.smart.refresh.layout.listener.OnRefreshLoadMoreListener;

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
    protected void initView() {
        super.initView();
        mBinding.srlRefresh.setOnRefreshLoadMoreListener(new OnRefreshLoadMoreListener() {
            @Override
            public void onLoadMore(@NonNull RefreshLayout refreshLayout) {
                mViewModel.queryPost(true);
            }

            @Override
            public void onRefresh(@NonNull RefreshLayout refreshLayout) {
                mViewModel.queryPost(false);
            }
        });
    }

    @Override
    protected void initData(@Nullable Bundle savedInstanceState) {
        mBinding.srlRefresh.autoRefresh();
    }

    @Override
    protected void baseLiveDataObserver(String str) {
        super.baseLiveDataObserver(str);
        switch (str) {
            case Constant.AUTO_REFRESH:
                mBinding.srlRefresh.autoRefresh();
                break;
            case Constant.REFRESH_SUCCESS:
                mBinding.srlRefresh.finishRefresh(true);
                break;
            case Constant.REFRESH_FAIL:
                mBinding.srlRefresh.finishRefresh(false);
                break;
            case Constant.LOAD_MORE_SUCCESS:
                mBinding.srlRefresh.finishLoadMore(true);
                break;
            case Constant.LOAD_MORE_FAIL:
                mBinding.srlRefresh.finishLoadMore(false);
                break;
            case Constant.LOAD_MORE_COMPLETE:
                mBinding.srlRefresh.finishLoadMoreWithNoMoreData();
                break;
            case Constant.RESET_NO_MORE_DATA:
                mBinding.srlRefresh.resetNoMoreData();
                break;
            default:
                break;
        }
    }
}
