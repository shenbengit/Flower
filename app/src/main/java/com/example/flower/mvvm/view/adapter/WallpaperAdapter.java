package com.example.flower.mvvm.view.adapter;

import com.example.flower.R;
import com.example.flower.databinding.ItemWallpaperBinding;
import com.example.flower.http.bean.WallpaperBean;
import com.example.flower.mvvm.view.adapter.base.BaseBindingAdapter;
import com.example.flower.mvvm.view.adapter.base.BaseBindingViewHolder;

/**
 * @author ShenBen
 * @date 2019/11/17 16:46
 * @email 714081644@qq.com
 */
public class WallpaperAdapter extends BaseBindingAdapter<WallpaperBean.DataBean> {


    public WallpaperAdapter() {
        super(R.layout.item_wallpaper);
    }

    @Override
    protected void convert(BaseBindingViewHolder helper, WallpaperBean.DataBean item) {
        ItemWallpaperBinding binding = helper.getBinding();
        binding.setBean(item);
    }
}
