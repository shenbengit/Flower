package com.example.flower.mvvm.view.fragment;

import android.os.Bundle;

import androidx.annotation.Nullable;

import com.example.flower.BR;
import com.example.flower.R;
import com.example.flower.base.BaseFragment;
import com.example.flower.databinding.FragmentWallpaperBinding;
import com.example.flower.mvvm.viewmodel.WallpaperViewModel;

/**
 * 壁纸Fragment
 *
 * @author ShenBen
 * @date 2019/9/22 13:40
 * @email 714081644@qq.com
 */
public class WallpaperFragment extends BaseFragment<FragmentWallpaperBinding, WallpaperViewModel> {

    public static WallpaperFragment getInstance() {
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
    protected void initData(@Nullable Bundle savedInstanceState) {

    }
}
