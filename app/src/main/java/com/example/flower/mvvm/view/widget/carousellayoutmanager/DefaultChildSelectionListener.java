package com.example.flower.mvvm.view.widget.carousellayoutmanager;

import android.view.View;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

/**
 * @author ShenBen
 * @date 2020/1/26 17:09
 * @email 714081644@qq.com
 */
public class DefaultChildSelectionListener extends CarouselChildSelectionListener {

    @NonNull
    private final OnCenterItemClickListener mOnCenterItemClickListener;

    protected DefaultChildSelectionListener(@NonNull final OnCenterItemClickListener onCenterItemClickListener, @NonNull final RecyclerView recyclerView, @NonNull final CarouselLayoutManager carouselLayoutManager) {
        super(recyclerView, carouselLayoutManager);

        mOnCenterItemClickListener = onCenterItemClickListener;
    }

    @Override
    protected void onCenterItemClicked(@NonNull final RecyclerView recyclerView, @NonNull final CarouselLayoutManager carouselLayoutManager, @NonNull final View v) {
        mOnCenterItemClickListener.onCenterItemClicked(recyclerView, carouselLayoutManager, v);
    }

    @Override
    protected void onBackItemClicked(@NonNull final RecyclerView recyclerView, @NonNull final CarouselLayoutManager carouselLayoutManager, @NonNull final View v) {
        recyclerView.smoothScrollToPosition(carouselLayoutManager.getPosition(v));
    }

    public static DefaultChildSelectionListener initCenterItemListener(@NonNull final OnCenterItemClickListener onCenterItemClickListener, @NonNull final RecyclerView recyclerView, @NonNull final CarouselLayoutManager carouselLayoutManager) {
        return new DefaultChildSelectionListener(onCenterItemClickListener, recyclerView, carouselLayoutManager);
    }

    public interface OnCenterItemClickListener {

        void onCenterItemClicked(@NonNull final RecyclerView recyclerView, @NonNull final CarouselLayoutManager carouselLayoutManager, @NonNull final View v);
    }
}
