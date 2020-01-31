package com.example.flower.mvvm.view.adapter;

import android.graphics.Color;
import android.util.SparseBooleanArray;

import androidx.annotation.Nullable;

import com.example.flower.R;
import com.example.flower.databinding.ItemPostTypeBinding;
import com.example.flower.http.bmob.PostTypeBean;
import com.example.flower.mvvm.view.adapter.base.BaseBindingAdapter;
import com.example.flower.mvvm.view.adapter.base.BaseBindingViewHolder;

import java.util.List;

/**
 * @author ShenBen
 * @date 2020/1/31 14:33
 * @email 714081644@qq.com
 */
public class PostTypeAdapter extends BaseBindingAdapter<PostTypeBean> {
    /**
     * 记录点击位置的标志
     */
    private SparseBooleanArray mBooleanArray = new SparseBooleanArray();
    /**
     * 记录上一次点击的位置,默认值为-1
     */
    private int mLastCheckedPosition = -1;

    public PostTypeAdapter() {
        super(R.layout.item_post_type);
    }

    @Override
    public void setNewData(@Nullable List<PostTypeBean> data) {
        mBooleanArray.clear();
        if (data != null) {
            for (int i = 0; i < data.size(); i++) {
                mBooleanArray.put(i, false);
            }
        }
        super.setNewData(data);
    }

    /**
     * 单选
     *
     * @param position 选中的位置
     */
    public void singleElectionPosition(int position) {
        //上一次点击的位置和当前点击的位置一样，则不处理
        if (mLastCheckedPosition == position) {
            return;
        }
        //如果上一次点击的位置不是-1，则说明已经点击过了，需要把以前选中的状态给置为未选择状态
        if (mLastCheckedPosition != -1) {
            mBooleanArray.put(mLastCheckedPosition, false);
            notifyItemChanged(mLastCheckedPosition);
        }
        //当前选中的位置置为选中状态
        mBooleanArray.put(position, true);
        notifyItemChanged(position);
        //把当前点击的位置赋值给上一次点击的位置
        mLastCheckedPosition = position;
    }


    @Override
    protected void convert(BaseBindingViewHolder helper, PostTypeBean item) {
        ItemPostTypeBinding binding = helper.getBinding();
        binding.setItem(item);
        int position = helper.getLayoutPosition() - getHeaderLayoutCount();
        boolean isChecked = mBooleanArray.get(position);
        binding.tvType.setBackgroundResource(isChecked ? R.drawable.post_type_checked : R.drawable.post_type_unchecked);
        binding.tvType.setTextColor(isChecked ? Color.parseColor("#ffffff") : Color.parseColor("#323232"));
    }
}
