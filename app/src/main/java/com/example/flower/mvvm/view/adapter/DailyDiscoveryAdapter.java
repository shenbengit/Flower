package com.example.flower.mvvm.view.adapter;

import com.example.flower.R;
import com.example.flower.databinding.ItemDailyDiscoveryBinding;
import com.example.flower.http.bean.DailyDiscoveryBean;
import com.example.flower.mvvm.view.adapter.base.BaseBindingAdapter;
import com.example.flower.mvvm.view.adapter.base.BaseBindingViewHolder;

/**
 * @author ShenBen
 * @date 2019/10/6 21:14
 * @email 714081644@qq.com
 */
public class DailyDiscoveryAdapter extends BaseBindingAdapter<DailyDiscoveryBean.DataBean> {

    public DailyDiscoveryAdapter() {
        super(R.layout.item_daily_discovery);
    }

    @Override
    protected void convert(BaseBindingViewHolder helper, DailyDiscoveryBean.DataBean item) {
        ItemDailyDiscoveryBinding binding = helper.getBinding();
        binding.setBean(item);
    }
}
