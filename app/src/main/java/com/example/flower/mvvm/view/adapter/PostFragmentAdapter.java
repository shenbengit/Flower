package com.example.flower.mvvm.view.adapter;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.fragment.app.FragmentManager;
import androidx.lifecycle.Lifecycle;
import androidx.viewpager2.adapter.FragmentStateAdapter;

import com.example.flower.http.bmob.PostTypeBean;
import com.example.flower.mvvm.view.fragment.PostListFragment;

import java.util.ArrayList;
import java.util.List;

/**
 * @author ShenBen
 * @date 2020/2/1 15:20
 * @email 714081644@qq.com
 */
public class PostFragmentAdapter extends FragmentStateAdapter {
    private List<PostTypeBean> mList;

    public PostFragmentAdapter(@NonNull FragmentActivity fragmentActivity) {
        super(fragmentActivity);
    }

    public PostFragmentAdapter(@NonNull Fragment fragment) {
        super(fragment);
    }

    public PostFragmentAdapter(@NonNull FragmentManager fragmentManager, @NonNull Lifecycle lifecycle) {
        super(fragmentManager, lifecycle);
    }

    public void setNewData(List<PostTypeBean> list) {
        mList = list == null ? new ArrayList<>() : list;
        notifyDataSetChanged();
    }

    public PostTypeBean getItem(int position) {
        if (position >= 0 && position < mList.size()) {
            return mList.get(position);
        } else {
            return null;
        }
    }

    @NonNull
    @Override
    public Fragment createFragment(int position) {
        return PostListFragment.newInstance(getItem(position).getObjectId());
    }

    @Override
    public int getItemCount() {
        return mList == null ? 0 : mList.size();
    }
}
