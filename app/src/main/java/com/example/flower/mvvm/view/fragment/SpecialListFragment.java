package com.example.flower.mvvm.view.fragment;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.example.flower.BR;
import com.example.flower.R;
import com.example.flower.base.BaseFragment;
import com.example.flower.databinding.FragmentSpecialListBinding;
import com.example.flower.mvvm.viewmodel.SpecialListViewModel;
import com.scwang.smart.refresh.layout.api.RefreshLayout;
import com.scwang.smart.refresh.layout.listener.OnRefreshLoadMoreListener;

/**
 * 专题列表Fragment
 *
 * @author ShenBen
 * @date 2019/10/13 10:32
 * @email 714081644@qq.com
 */
public class SpecialListFragment extends BaseFragment<FragmentSpecialListBinding, SpecialListViewModel> {

    private static final String TYPE_ID = "TYPE_ID";
    private String mTypeId;

    public static SpecialListFragment newInstance(String typeId) {
        SpecialListFragment fragment = new SpecialListFragment();
        Bundle bundle = new Bundle();
        bundle.putString(TYPE_ID, typeId);
        fragment.setArguments(bundle);
        return fragment;
    }

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_special_list;
    }

    @Override
    protected Class<SpecialListViewModel> getModelClass() {
        return SpecialListViewModel.class;
    }

    @Override
    protected int getVariableId() {
        return BR.viewModel;
    }

    @Override
    protected void initView() {
        super.initView();
        Bundle bundle = getArguments();
        if (bundle != null) {
            mTypeId = bundle.getString(TYPE_ID);
        }
        mBinding.srlRefresh.setOnRefreshLoadMoreListener(new OnRefreshLoadMoreListener() {
            @Override
            public void onLoadMore(@NonNull RefreshLayout refreshLayout) {
                mViewModel.getSpecialDetail(true);
            }

            @Override
            public void onRefresh(@NonNull RefreshLayout refreshLayout) {
                mViewModel.getSpecialDetail(false);
            }
        });

        mBinding.rvDetail.setItemAnimator(null);
    }

    @Override
    protected void initData(@Nullable Bundle savedInstanceState) {
        mViewModel.mBaseLiveData.observe(this, s -> {
            switch (s) {
                case SpecialListViewModel.REFRESH_SUCCESS:
                    mBinding.srlRefresh.finishRefresh(true);
                    break;
                case SpecialListViewModel.REFRESH_FAIL:
                    mBinding.srlRefresh.finishRefresh(false);
                    break;
                case SpecialListViewModel.LOAD_MORE_SUCCESS:
                    mBinding.srlRefresh.finishLoadMore(true);
                    break;
                case SpecialListViewModel.LOAD_MORE_FAIL:
                    mBinding.srlRefresh.finishLoadMore(false);
                    break;
                case SpecialListViewModel.LOAD_MORE_COMPLETE:
                    mBinding.srlRefresh.finishLoadMoreWithNoMoreData();
                    break;
                case SpecialListViewModel.RESET_NO_MORE_DATA:
                    mBinding.srlRefresh.resetNoMoreData();
                    break;
                default:
                    break;
            }
        });

        mViewModel.setSpecialTypeId(mTypeId);
        mViewModel.getSpecialDetail(false);
    }
}
