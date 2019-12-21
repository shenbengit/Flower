package com.example.flower.mvvm.view.adapter;

import com.example.flower.R;
import com.example.flower.databinding.ItemKnowPlantsDetailBinding;
import com.example.flower.http.bean.PlantsDetailBean;
import com.example.flower.mvvm.view.adapter.base.BaseBindingAdapter;
import com.example.flower.mvvm.view.adapter.base.BaseBindingViewHolder;

/**
 * @author ShenBen
 * @date 2019/12/1 15:35
 * @email 714081644@qq.com
 */
public class KnowPlantsDetailAdapter extends BaseBindingAdapter<PlantsDetailBean.DataBean> {
    public KnowPlantsDetailAdapter() {
        super(R.layout.item_know_plants_detail);
    }

    @Override
    protected void convert(BaseBindingViewHolder helper, PlantsDetailBean.DataBean item) {
        ItemKnowPlantsDetailBinding binding = helper.getBinding();
        binding.setItem(item);
    }
}
