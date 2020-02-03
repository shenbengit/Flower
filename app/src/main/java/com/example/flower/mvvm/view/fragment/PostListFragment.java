package com.example.flower.mvvm.view.fragment;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.databinding.library.baseAdapters.BR;

import com.example.flower.R;
import com.example.flower.base.BaseFragment;
import com.example.flower.constant.Constant;
import com.example.flower.databinding.FragmentPostListBinding;
import com.example.flower.mvvm.viewmodel.PostListViewModel;
import com.scwang.smart.refresh.layout.api.RefreshLayout;
import com.scwang.smart.refresh.layout.listener.OnRefreshLoadMoreListener;

/**
 * 贴子Fragment
 *
 * @author ShenBen
 * @date 2020/2/1 15:27
 * @email 714081644@qq.com
 */
public class PostListFragment extends BaseFragment<FragmentPostListBinding, PostListViewModel> {

    private static final String POST_TYPE_OBJECT_ID = "POST_TYPE_OBJECT_ID";

    public static PostListFragment newInstance(String objectId) {
        PostListFragment fragment = new PostListFragment();
        Bundle bundle = new Bundle();
        fragment.setArguments(bundle);
        bundle.putString(POST_TYPE_OBJECT_ID, objectId);
        return fragment;
    }

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_post_list;
    }

    @Override
    protected Class<PostListViewModel> getModelClass() {
        return PostListViewModel.class;
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
        Bundle bundle = getArguments();
        if (bundle != null) {
            String objectId = bundle.getString(POST_TYPE_OBJECT_ID);
            mViewModel.setPostTypeObjectId(objectId);
        }
        mViewModel.queryPost(false);
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
