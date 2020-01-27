package com.example.flower.mvvm.view.fragment;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.example.flower.BR;
import com.example.flower.R;
import com.example.flower.base.BaseFragment;
import com.example.flower.constant.Constant;
import com.example.flower.databinding.FragmentWallpaperBinding;
import com.example.flower.mvvm.viewmodel.WallpaperViewModel;
import com.scwang.smart.refresh.layout.api.RefreshLayout;
import com.scwang.smart.refresh.layout.listener.OnRefreshLoadMoreListener;

/**
 * 壁纸Fragment
 *
 * @author ShenBen
 * @date 2019/9/22 13:40
 * @email 714081644@qq.com
 */
public class WallpaperFragment extends BaseFragment<FragmentWallpaperBinding, WallpaperViewModel> {

    public static WallpaperFragment newInstance() {
        return new WallpaperFragment();
    }

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_wallpaper;
    }

    @Override
    protected Class<WallpaperViewModel> getModelClass() {
        return WallpaperViewModel.class;
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
                mViewModel.getWallpaper(true);
            }

            @Override
            public void onRefresh(@NonNull RefreshLayout refreshLayout) {
                mViewModel.getWallpaper(false);
            }
        });
    }

    @Override
    protected void initData(@Nullable Bundle savedInstanceState) {
        mViewModel.getWallpaper(false);
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
