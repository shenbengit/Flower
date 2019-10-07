package com.example.flower.mvvm.view.activity;

import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.alibaba.android.arouter.facade.annotation.Route;
import com.example.flower.BR;
import com.example.flower.R;
import com.example.flower.base.BaseActivity;
import com.example.flower.constant.ARouterPath;
import com.example.flower.databinding.ActivitySpecialDetailBinding;
import com.example.flower.http.bean.HomePageBean;
import com.example.flower.mvvm.viewmodel.SpecialDetailViewModel;
import com.scwang.smart.refresh.layout.api.RefreshLayout;
import com.scwang.smart.refresh.layout.listener.OnRefreshLoadMoreListener;

/**
 * 专题分类详情
 */
@Route(path = ARouterPath.SPECIAL_DETAIL_ACTIVITY_PATH)
public class SpecialDetailActivity extends BaseActivity<ActivitySpecialDetailBinding, SpecialDetailViewModel> {
    public static final String SPECIAL_DETAIL = "SPECIAL_DETAIL";

    private HomePageBean.DataBean.CommunityHomePageSecondPlateViewBean.CategoryForSecondPlateViewsBean mBean;

    @Override
    protected int getLayoutId() {
        return R.layout.activity_special_detail;
    }

    @Override
    protected Class<SpecialDetailViewModel> getModelClass() {
        return SpecialDetailViewModel.class;
    }

    @Override
    protected int getVariableId() {
        return BR.viewModel;
    }

    @Override
    protected void initView() {
        super.initView();
        Intent intent = getIntent();
        if (intent != null) {
            mBean = intent.getParcelableExtra(SPECIAL_DETAIL);
        }
        if (mBean != null) {
            mBinding.toolbar.setTitle(mBean.getName());
        }
        initToolbar(mBinding.toolbar);

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
    }

    @Override
    protected void initData(@Nullable Bundle savedInstanceState) {
        mViewModel.mBaseLiveData.observe(this, s -> {
            switch (s) {
                case SpecialDetailViewModel.REFRESH_SUCCESS:
                    mBinding.srlRefresh.finishRefresh(true);
                    break;
                case SpecialDetailViewModel.REFRESH_FAIL:
                    mBinding.srlRefresh.finishRefresh(false);
                    break;
                case SpecialDetailViewModel.LOAD_MORE_SUCCESS:
                    mBinding.srlRefresh.finishLoadMore(true);
                    break;
                case SpecialDetailViewModel.LOAD_MORE_FAIL:
                    mBinding.srlRefresh.finishLoadMore(false);
                    break;
                case SpecialDetailViewModel.LOAD_MORE_COMPLETE:
                    mBinding.srlRefresh.finishLoadMoreWithNoMoreData();
                    break;
                case SpecialDetailViewModel.RESET_NO_MORE_DATA:
                    mBinding.srlRefresh.resetNoMoreData();
                    break;
                default:
                    break;
            }
        });
        if (mBean != null) {
            mViewModel.getSpecialTypeList(mBean.getId());
        }

    }
}
