package com.example.flower.mvvm.view.adapter;

import android.graphics.Bitmap;
import android.graphics.drawable.Drawable;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.bumptech.glide.request.target.CustomTarget;
import com.bumptech.glide.request.transition.Transition;
import com.example.flower.GlideApp;
import com.example.flower.R;
import com.example.flower.databinding.ItemRecommendedTodayBinding;
import com.example.flower.http.bean.RecommendedTodayBean;
import com.example.flower.mvvm.view.adapter.base.BaseBindingAdapter;
import com.example.flower.mvvm.view.adapter.base.BaseBindingViewHolder;

/**
 * @author ShenBen
 * @date 2019/9/28 12:54
 * @email 714081644@qq.com
 */
public class RecommendedTodayAdapter extends BaseBindingAdapter<RecommendedTodayBean.DataBean> {
    public RecommendedTodayAdapter() {
        super(R.layout.item_recommended_today);
    }

    @Override
    protected void convert(BaseBindingViewHolder helper, RecommendedTodayBean.DataBean item) {
        ItemRecommendedTodayBinding binding = helper.getBinding();
        binding.setBean(item);
        //如果有宽高，直接设置
        if (item.getImageWidth() > 0 && item.getImageHeight() > 0) {
            ViewGroup.LayoutParams params = binding.iv.getLayoutParams();
            params.width = item.getImageWidth();
            params.height = item.getImageHeight();
            GlideApp.with(binding.iv)
                    .load(item.getCoverImg())
                    .override(item.getImageWidth(), item.getImageHeight())
                    .into(binding.iv);
        } else {
            GlideApp.with(binding.iv)
                    .asBitmap()
                    .load(item.getCoverImg())
                    .into(new CustomTarget<Bitmap>() {
                        @Override
                        public void onResourceReady(@NonNull Bitmap resource, @Nullable Transition<? super Bitmap> transition) {
                            item.setImageWidth(binding.iv.getWidth());
                            //等比缩放，设置高度
                            item.setImageHeight(item.getImageWidth() * resource.getHeight() / resource.getWidth());
                            ViewGroup.LayoutParams params = binding.iv.getLayoutParams();
                            params.width = item.getImageWidth();
                            params.height = item.getImageHeight();
                            binding.iv.setImageBitmap(resource);
                        }

                        @Override
                        public void onLoadCleared(@Nullable Drawable placeholder) {

                        }
                    });
        }

    }

    /**
     * 会解决多次滑动上方留白问题
     *
     * @param position
     * @return
     */
    @Override
    public int getItemViewType(int position) {
        return position;
    }
}
