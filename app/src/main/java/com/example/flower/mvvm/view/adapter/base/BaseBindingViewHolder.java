package com.example.flower.mvvm.view.adapter.base;

import android.view.View;

import androidx.databinding.ViewDataBinding;

import com.chad.library.adapter.base.BaseViewHolder;
import com.example.flower.R;

/**
 * 封装RecyclerView.ViewHolder基类ViewHolder
 * 使用DataBinding
 *
 * @author ShenBen
 * @date 2018/11/8 13:17
 * @email 714081644@qq.com
 */
public class BaseBindingViewHolder extends BaseViewHolder {

    public BaseBindingViewHolder(View view) {
        super(view);
    }

    @SuppressWarnings("unchecked")
    public <T extends ViewDataBinding> T getBinding() {
        return (T) itemView.getTag(R.id.BaseQuickAdapter_databinding_support);
    }
}
