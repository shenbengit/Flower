package com.example.flower.mvvm.view.adapter;

import android.text.TextUtils;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.fragment.app.FragmentManager;
import androidx.lifecycle.Lifecycle;
import androidx.viewpager2.adapter.FragmentStateAdapter;

import com.example.flower.mvvm.view.fragment.CollectionFragment;
import com.example.flower.mvvm.view.fragment.PopularFragment;
import com.example.flower.mvvm.view.fragment.SpaceFragment;

import java.util.ArrayList;
import java.util.List;

/**
 * @author ShenBen
 * @date 2020/1/30 16:20
 * @email 714081644@qq.com
 */
public class SpaceFragmentAdapter extends FragmentStateAdapter {

    private final List<String> mData = new ArrayList<>();

    public SpaceFragmentAdapter(@NonNull FragmentActivity fragmentActivity) {
        super(fragmentActivity);
    }

    public SpaceFragmentAdapter(@NonNull Fragment fragment) {
        super(fragment);
    }

    public SpaceFragmentAdapter(@NonNull FragmentManager fragmentManager, @NonNull Lifecycle lifecycle) {
        super(fragmentManager, lifecycle);
    }

    public void setNewData(List<String> list) {
        mData.clear();
        if (list != null && !list.isEmpty()) {
            mData.addAll(list);
        }
        notifyDataSetChanged();
    }

    public String getItem(int position) {
        if (position >= 0 && position < mData.size()) {
            return mData.get(position);
        } else {
            return null;
        }
    }

    @NonNull
    @Override
    public Fragment createFragment(int position) {
        if (TextUtils.equals(SpaceFragment.POPULAR, getItem(position))) {
            //发现
            return PopularFragment.newInstance();
        } else if (TextUtils.equals(SpaceFragment.COLLECTION, getItem(position))) {
            //收藏
            return CollectionFragment.newInstance();
        }
        return new Fragment();
    }

    @Override
    public int getItemCount() {
        return mData.size();
    }
}
