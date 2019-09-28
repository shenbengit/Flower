package com.example.flower.mvvm.view.adapter;

import com.example.flower.R;
import com.example.flower.databinding.ItemSpecialBinding;
import com.example.flower.http.bean.HomePageBean;
import com.example.flower.mvvm.view.adapter.base.BaseBindingAdapter;
import com.example.flower.mvvm.view.adapter.base.BaseBindingViewHolder;

/**
 * @author ShenBen
 * @date 2019/9/27 22:31
 * @email 714081644@qq.com
 */
public class SpecialAdapter extends BaseBindingAdapter<HomePageBean.DataBean.CommunityHomePageSecondPlateViewBean.CategoryForSecondPlateViewsBean> {
    public SpecialAdapter() {
        super(R.layout.item_special);
    }

    @Override
    protected void convert(BaseBindingViewHolder helper, HomePageBean.DataBean.CommunityHomePageSecondPlateViewBean.CategoryForSecondPlateViewsBean item) {
        ItemSpecialBinding binding = helper.getBinding();
        binding.setBean(item);
    }
}
