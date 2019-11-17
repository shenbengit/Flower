package com.example.flower.mvvm.view.adapter;

import com.example.flower.R;
import com.example.flower.databinding.ItemPlantsTypeBinding;
import com.example.flower.http.bean.PlantsTypeBean;
import com.example.flower.mvvm.view.adapter.base.BaseBindingAdapter;
import com.example.flower.mvvm.view.adapter.base.BaseBindingViewHolder;

/**
 * @author ShenBen
 * @date 2019/11/17 14:18
 * @email 714081644@qq.com
 */
public class PlantsTypeAdapter extends BaseBindingAdapter<PlantsTypeBean.DataBean> {

    public PlantsTypeAdapter() {
        super(R.layout.item_plants_type);
    }

    @Override
    protected void convert(BaseBindingViewHolder helper, PlantsTypeBean.DataBean item) {
        ItemPlantsTypeBinding binding = helper.getBinding();
        binding.setBean(item);
    }
}
