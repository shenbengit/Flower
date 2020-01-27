package com.example.flower.mvvm.view.widget.carousellayoutmanager;

import android.view.View;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

/**
 * @author ShenBen
 * @date 2020/1/26 17:05
 * @email 714081644@qq.com
 */
public abstract class CarouselChildSelectionListener {

    @NonNull
    private final RecyclerView mRecyclerView;
    @NonNull
    private final CarouselLayoutManager mCarouselLayoutManager;

    private final View.OnClickListener mOnClickListener = new View.OnClickListener() {
        @Override
        public void onClick(final View v) {
            final RecyclerView.ViewHolder holder = mRecyclerView.getChildViewHolder(v);
            final int position = holder.getAdapterPosition();

            if (position == mCarouselLayoutManager.getCenterItemPosition()) {
                onCenterItemClicked(mRecyclerView, mCarouselLayoutManager, v);
            } else {
                onBackItemClicked(mRecyclerView, mCarouselLayoutManager, v);
            }
        }
    };

    protected CarouselChildSelectionListener(@NonNull final RecyclerView recyclerView, @NonNull final CarouselLayoutManager carouselLayoutManager) {
        mRecyclerView = recyclerView;
        mCarouselLayoutManager = carouselLayoutManager;

        mRecyclerView.addOnChildAttachStateChangeListener(new RecyclerView.OnChildAttachStateChangeListener() {
            @Override
            public void onChildViewAttachedToWindow(final View view) {
                view.setOnClickListener(mOnClickListener);
            }

            @Override
            public void onChildViewDetachedFromWindow(final View view) {
                view.setOnClickListener(null);
            }
        });
    }

    protected abstract void onCenterItemClicked(@NonNull final RecyclerView recyclerView, @NonNull final CarouselLayoutManager carouselLayoutManager, @NonNull final View v);

    protected abstract void onBackItemClicked(@NonNull final RecyclerView recyclerView, @NonNull final CarouselLayoutManager carouselLayoutManager, @NonNull final View v);
}