package com.example.flower.mvvm.view.adapter;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.fragment.app.FragmentManager;
import androidx.lifecycle.Lifecycle;
import androidx.viewpager2.adapter.FragmentStateAdapter;

import com.example.flower.http.bean.SpecialTypeBean;
import com.example.flower.mvvm.view.fragment.SpecialListFragment;

import java.util.ArrayList;
import java.util.List;

/**
 * @author ShenBen
 * @date 2019/10/7 15:12
 * @email 714081644@qq.com
 */
public class SpecialTypeAdapter extends FragmentStateAdapter {
    private final List<SpecialTypeBean.DataBean> mList = new ArrayList<>();

    public SpecialTypeAdapter(@NonNull FragmentActivity fragmentActivity) {
        super(fragmentActivity);
    }

    public SpecialTypeAdapter(@NonNull Fragment fragment) {
        super(fragment);
    }

    public SpecialTypeAdapter(@NonNull FragmentManager fragmentManager, @NonNull Lifecycle lifecycle) {
        super(fragmentManager, lifecycle);
    }

    public void setNewData(List<SpecialTypeBean.DataBean> list) {
        mList.clear();
        if (list != null && !list.isEmpty()) {
            mList.addAll(list);
        }
        notifyDataSetChanged();
    }

    public List<SpecialTypeBean.DataBean> getData() {
        return mList;
    }

    @NonNull
    @Override
    public Fragment createFragment(int position) {
        return SpecialListFragment.newInstance(mList.get(position).getId());
    }

    @Override
    public int getItemCount() {
        return mList.size();
    }
}
