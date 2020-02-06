package com.example.flower.mvvm.view.adapter;

import com.example.flower.R;
import com.example.flower.databinding.ItemKnowFlowerBinding;
import com.example.flower.http.bean.KnowFlowerResultBean;
import com.example.flower.mvvm.view.adapter.base.BaseBindingAdapter;
import com.example.flower.mvvm.view.adapter.base.BaseBindingViewHolder;

/**
 * @author ShenBen
 * @date 2020/1/26 17:29
 * @email 714081644@qq.com
 */
public class KnowFlowerResultAdapter extends BaseBindingAdapter<KnowFlowerResultBean.ResponseBean.IdentifyResultsBean> {

    public KnowFlowerResultAdapter() {
        super(R.layout.item_know_flower);
    }

    @Override
    protected void convert(BaseBindingViewHolder helper, KnowFlowerResultBean.ResponseBean.IdentifyResultsBean item) {
        ItemKnowFlowerBinding binding = helper.getBinding();
        binding.setItem(item);
    }
}
