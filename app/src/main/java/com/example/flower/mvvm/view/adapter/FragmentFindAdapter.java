package com.example.flower.mvvm.view.adapter;

import android.util.SparseArray;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.viewpager2.adapter.FragmentStateAdapter;

import com.example.flower.mvvm.view.fragment.FindFragment;
import com.example.flower.mvvm.view.fragment.KnowPlantsFragment;
import com.example.flower.mvvm.view.fragment.WallpaperFragment;

/**
 * @author ShenBen
 * @date 2019/11/17 12:37
 * @email 714081644@qq.com
 */
public class FragmentFindAdapter extends FragmentStateAdapter {

    private final SparseArray<Fragment> mFragmentArray = new SparseArray<>();

    public FragmentFindAdapter(@NonNull Fragment fragment) {
        super(fragment);
        mFragmentArray.put(FindFragment.KNOW_PLANTS_TAG, KnowPlantsFragment.newInstance());
        mFragmentArray.put(FindFragment.WALLPAPER_TAG, WallpaperFragment.newInstance());
    }

    @NonNull
    @Override
    public Fragment createFragment(int position) {
        return mFragmentArray.get(position);
    }

    @Override
    public int getItemCount() {
        return mFragmentArray.size();
    }
}
