package com.example.flower.mvvm.view.adapter;

import androidx.annotation.NonNull;

import com.example.flower.R;
import com.example.flower.databinding.ItemSpecialTypeBinding;
import com.example.flower.http.bean.SpecialTypeBean;
import com.example.flower.mvvm.view.adapter.base.BaseBindingAdapter;
import com.example.flower.mvvm.view.adapter.base.BaseBindingViewHolder;

import java.util.List;

/**
 * @author ShenBen
 * @date 2019/10/7 15:12
 * @email 714081644@qq.com
 */
public class SpecialTypeAdapter extends BaseBindingAdapter<SpecialTypeBean.DataBean> {
    public SpecialTypeAdapter() {
        super(R.layout.item_special_type);
    }

    @Override
    public void onBindViewHolder(@NonNull BaseBindingViewHolder holder, int position, @NonNull List<Object> payloads) {
        if (payloads.isEmpty()) {
            onBindViewHolder(holder, position);
        } else {
            ItemSpecialTypeBinding binding = holder.getBinding();
            binding.setBean(getItem(position));
        }
    }

    @Override
    protected void convert(BaseBindingViewHolder helper, SpecialTypeBean.DataBean item) {
        //itemView 里子控件添加点击事件监听
        helper.addOnClickListener(R.id.tvSpecialType);
        ItemSpecialTypeBinding binding = helper.getBinding();
        binding.setBean(item);
    }
}
