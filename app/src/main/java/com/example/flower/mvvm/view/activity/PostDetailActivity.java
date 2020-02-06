package com.example.flower.mvvm.view.activity;

import android.os.Bundle;

import androidx.annotation.Nullable;
import com.example.flower.BR;

import com.alibaba.android.arouter.facade.annotation.Route;
import com.example.flower.R;
import com.example.flower.base.BaseActivity;
import com.example.flower.constant.ARouterPath;
import com.example.flower.constant.Constant;
import com.example.flower.databinding.ActivityPostDetailBinding;
import com.example.flower.mvvm.viewmodel.PostDetailViewModel;

/**
 * 帖子详情页面
 */
@Route(path = ARouterPath.POST_DETAIL_PATH)
public class PostDetailActivity extends BaseActivity<ActivityPostDetailBinding, PostDetailViewModel> {

    public static final String POST_OBJECT_ID = "POST_OBJECT_ID";
    /**
     * 帖子的ObjectId
     */
    private String mObjectId;

    @Override
    protected int getLayoutId() {
        return R.layout.activity_post_detail;
    }

    @Override
    protected Class<PostDetailViewModel> getModelClass() {
        return PostDetailViewModel.class;
    }

    @Override
    protected int getVariableId() {
        return BR.viewModel;
    }

    @Override
    protected void initView() {
        super.initView();
        mBinding.toolbar.setTitle("详情");
        initToolbar(mBinding.toolbar);

        mBinding.srlRefresh.setOnRefreshListener(refreshLayout -> mViewModel.queryComment());
    }

    @Override
    protected void initData(@Nullable Bundle savedInstanceState) {
        if (getIntent() != null) {
            mObjectId = getIntent().getStringExtra(POST_OBJECT_ID);
            mViewModel.queryPost(mObjectId);
            mBinding.srlRefresh.autoRefresh();
        }
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
