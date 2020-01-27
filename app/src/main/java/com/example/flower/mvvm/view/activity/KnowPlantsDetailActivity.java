package com.example.flower.mvvm.view.activity;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.alibaba.android.arouter.facade.annotation.Route;
import com.example.flower.BR;
import com.example.flower.R;
import com.example.flower.base.BaseActivity;
import com.example.flower.constant.ARouterPath;
import com.example.flower.constant.Constant;
import com.example.flower.databinding.ActivityKnowPlantsDetailBinding;
import com.example.flower.mvvm.view.widget.PlantsDetailDialog;
import com.example.flower.mvvm.viewmodel.KnowPlantsDetailViewModel;
import com.scwang.smart.refresh.layout.api.RefreshLayout;
import com.scwang.smart.refresh.layout.listener.OnRefreshLoadMoreListener;

/**
 * 认识植物详情信息页面
 */
@Route(path = ARouterPath.KNOW_PLANTS_DETAIL_PATH)
public class KnowPlantsDetailActivity extends BaseActivity<ActivityKnowPlantsDetailBinding, KnowPlantsDetailViewModel> {

    public static final String TITLE = "TITLE";
    public static final String TYPE_ID = "TYPE_ID";

    private PlantsDetailDialog mDialog;

    @Override
    protected int getLayoutId() {
        return R.layout.activity_know_plants_detail;
    }

    @Override
    protected Class<KnowPlantsDetailViewModel> getModelClass() {
        return KnowPlantsDetailViewModel.class;
    }

    @Override
    protected int getVariableId() {
        return BR.viewModel;
    }

    @Override
    protected void initView() {
        super.initView();
        mDialog = new PlantsDetailDialog(this);
        if (getIntent() != null) {
            mBinding.toolbar.setTitle(getIntent().getStringExtra(TITLE));
            mViewModel.setTypeId(getIntent().getStringExtra(TYPE_ID));

        }
        initToolbar(mBinding.toolbar);
        mBinding.srlRefresh.setOnRefreshLoadMoreListener(new OnRefreshLoadMoreListener() {
            @Override
            public void onLoadMore(@NonNull RefreshLayout refreshLayout) {
                mViewModel.getDetail(true);
            }

            @Override
            public void onRefresh(@NonNull RefreshLayout refreshLayout) {
                mViewModel.getDetail(false);
            }
        });
    }

    @Override
    protected void initData(@Nullable Bundle savedInstanceState) {
        mViewModel.mDetailBeanLiveData.observe(this, dataBean -> {
            if (!mDialog.isShowing()) {
                mDialog.setDetail(dataBean);
                mDialog.show();
            }
        });
        mBinding.srlRefresh.autoRefresh();
    }

    @Override
    protected void baseLiveDataObserver(String str) {
        super.baseLiveDataObserver(str);
        switch (str) {
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
