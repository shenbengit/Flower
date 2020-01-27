package com.example.flower.mvvm.view.widget;

import android.content.Context;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.DecelerateInterpolator;
import android.view.animation.TranslateAnimation;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.example.flower.R;
import com.example.flower.http.bean.KnowFlowerResultBean;
import com.example.flower.mvvm.view.adapter.KnowFlowerAdapter;
import com.example.flower.mvvm.view.widget.carousellayoutmanager.CarouselLayoutManager;
import com.example.flower.mvvm.view.widget.carousellayoutmanager.CarouselZoomPostLayoutListener;
import com.example.flower.mvvm.view.widget.carousellayoutmanager.CenterScrollListener;

import java.util.List;

import razerdp.basepopup.BasePopupWindow;

/**
 * @author ShenBen
 * @date 2020/1/26 15:50
 * @email 714081644@qq.com
 */
public class KnowFlowerPopupWindow extends BasePopupWindow {

    private TextView tvHint;
    private TextView tvFlowerName;
    private TextView tvFlowerIntroduce;
    private RecyclerView rvResult;

    private KnowFlowerAdapter mAdapter;
    private CarouselLayoutManager mLayoutManager;
    private CarouselLayoutManager.OnCenterItemSelectionListener mListener = new CarouselLayoutManager.OnCenterItemSelectionListener() {
        @Override
        public void onCenterItemChanged(int adapterPosition) {
            KnowFlowerResultBean.ResponseBean.IdentifyResultsBean item = mAdapter.getItem(adapterPosition);
            if (item != null) {
                tvFlowerName.setText(item.getName());
                tvFlowerIntroduce.setText(item.getDesc());
            }
        }
    };

    public KnowFlowerPopupWindow(Context context) {
        super(context, ViewGroup.LayoutParams.MATCH_PARENT, (int) context.getResources().getDimension(R.dimen.dp_370));
        findViewById(R.id.ibBack).setOnClickListener(v -> dismiss());
        tvHint = findViewById(R.id.tvHint);
        tvFlowerName = findViewById(R.id.tvFlowerName);
        tvFlowerIntroduce = findViewById(R.id.tvFlowerIntroduce);
        rvResult = findViewById(R.id.rvResult);

        setPopupGravity(Gravity.BOTTOM);
        setBackPressEnable(true);
        setOutSideDismiss(false);

        mLayoutManager = new CarouselLayoutManager(CarouselLayoutManager.HORIZONTAL, false);
        mLayoutManager.setPostLayoutListener(new CarouselZoomPostLayoutListener());
        mLayoutManager.setMaxVisibleItems(2);
        rvResult.setLayoutManager(mLayoutManager);
        rvResult.setHasFixedSize(true);
        rvResult.addOnScrollListener(new CenterScrollListener());

        mAdapter = new KnowFlowerAdapter();
        rvResult.setAdapter(mAdapter);
    }

    @Override
    public View onCreateContentView() {
        return createPopupById(R.layout.popup_know_flower);
    }

    @Override
    protected Animation onCreateShowAnimation() {
        return createTranslateAnimation(0f, 0f, 1f, 0f);
    }

    @Override
    protected Animation onCreateDismissAnimation() {
        return createTranslateAnimation(0f, 0f, 0f, 1f);
    }

    public void setResultData(List<KnowFlowerResultBean.ResponseBean.IdentifyResultsBean> list) {
        setStatus();
        mAdapter.setNewData(list);
    }

    @Override
    public void showPopupWindow() {
        mLayoutManager.addOnItemSelectionListener(mListener);
        resetStatus();
        super.showPopupWindow();
    }


    @Override
    public void dismiss() {
        mLayoutManager.removeOnItemSelectionListener(mListener);
        super.dismiss();
    }

    /**
     * 重置布局显示状态
     */
    private void resetStatus() {
        tvHint.setVisibility(View.VISIBLE);
        tvFlowerName.setVisibility(View.INVISIBLE);
        tvFlowerIntroduce.setVisibility(View.INVISIBLE);
        rvResult.setVisibility(View.INVISIBLE);
    }

    /**
     * 布局显示状态
     */
    private void setStatus() {
        tvHint.setVisibility(View.INVISIBLE);
        tvFlowerName.setVisibility(View.VISIBLE);
        tvFlowerIntroduce.setVisibility(View.VISIBLE);
        rvResult.setVisibility(View.VISIBLE);
    }

    private Animation createTranslateAnimation(float fromX, float toX, float fromY, float toY) {
        Animation animation = new TranslateAnimation(Animation.RELATIVE_TO_SELF,
                fromX,
                Animation.RELATIVE_TO_SELF,
                toX,
                Animation.RELATIVE_TO_SELF,
                fromY,
                Animation.RELATIVE_TO_SELF,
                toY);
        animation.setDuration(360);
        animation.setInterpolator(new DecelerateInterpolator());
        return animation;
    }

}
