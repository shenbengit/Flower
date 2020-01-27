package com.example.flower.mvvm.view.widget.carousellayoutmanager;

/**
 * @author ShenBen
 * @date 2020/1/26 17:09
 * @email 714081644@qq.com
 */
public class ItemTransformation {

    final float mScaleX;
    final float mScaleY;
    final float mTranslationX;
    final float mTranslationY;

    public ItemTransformation(final float scaleX, final float scaleY, final float translationX, final float translationY) {
        mScaleX = scaleX;
        mScaleY = scaleY;
        mTranslationX = translationX;
        mTranslationY = translationY;
    }
}