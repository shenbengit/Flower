package com.example.flower.mvvm.view.activity;

import android.os.Bundle;

import androidx.annotation.Nullable;

import com.alibaba.android.arouter.facade.annotation.Route;
import com.alibaba.android.arouter.launcher.ARouter;
import com.example.flower.GlideApp;
import com.example.flower.R;
import com.example.flower.base.BaseActivity;
import com.example.flower.base.BaseViewModel;
import com.example.flower.constant.ARouterPath;
import com.example.flower.databinding.ActivityKnowFlowerResultBinding;
import com.example.flower.http.bean.KnowFlowerResultBean;
import com.example.flower.mvvm.view.adapter.KnowFlowerResultAdapter;
import com.example.flower.mvvm.view.widget.carousellayoutmanager.CarouselLayoutManager;
import com.example.flower.mvvm.view.widget.carousellayoutmanager.CarouselZoomPostLayoutListener;
import com.example.flower.mvvm.view.widget.carousellayoutmanager.CenterScrollListener;

/**
 * 识花结果页面
 */
@Route(path = ARouterPath.KNOW_FLOWER_RESULT_PATH)
public class KnowFlowerResultActivity extends BaseActivity<ActivityKnowFlowerResultBinding, BaseViewModel> {
    /**
     * 拍照图片的url
     */
    public static final String PICTURE_URL = "PICTURE_URL";
    /**
     * 识别结果列表
     */
    public static final String RESULT_LIST = "RESULT_LIST";

    private KnowFlowerResultAdapter mAdapter;
    private CarouselLayoutManager mLayoutManager;

    private CarouselLayoutManager.OnCenterItemSelectionListener mListener = new CarouselLayoutManager.OnCenterItemSelectionListener() {
        @Override
        public void onCenterItemChanged(int adapterPosition) {
            KnowFlowerResultBean.ResponseBean.IdentifyResultsBean item = mAdapter.getItem(adapterPosition);
            if (item != null) {
                mBinding.tvFlowerName.setText(item.getName());
                mBinding.tvFlowerIntroduce.setText(item.getDesc());
            }
        }
    };

    @Override
    protected int getLayoutId() {
        return R.layout.activity_know_flower_result;
    }

    @Override
    protected Class<BaseViewModel> getModelClass() {
        return BaseViewModel.class;
    }

    @Override
    protected int getVariableId() {
        return 0;
    }

    @Override
    protected void initView() {
        super.initView();
        mBinding.ibBack.setOnClickListener(v -> onBackPressedSupport());
        mAdapter = new KnowFlowerResultAdapter();
        mAdapter.setOnItemClickListener((adapter, view, position) -> {
            KnowFlowerResultBean.ResponseBean.IdentifyResultsBean item = mAdapter.getItem(position);
            if (item == null) {
                return;
            }
            ARouter.getInstance()
                    .build(ARouterPath.KNOW_FLOWER_DETAIL_PATH)
                    .withString(KnowFlowerDetailActivity.FLOWER_DETAIL_URL, item.getDetailUrl())
                    .navigation();
        });
        mBinding.rvResult.setAdapter(mAdapter);
        mLayoutManager = new CarouselLayoutManager(CarouselLayoutManager.HORIZONTAL, false);
        mLayoutManager.setPostLayoutListener(new CarouselZoomPostLayoutListener());
        mLayoutManager.setMaxVisibleItems(1);
        mLayoutManager.addOnItemSelectionListener(mListener);
        mBinding.rvResult.setLayoutManager(mLayoutManager);
        mBinding.rvResult.setHasFixedSize(true);
        mBinding.rvResult.addOnScrollListener(new CenterScrollListener());
    }

    @Override
    protected void initData(@Nullable Bundle savedInstanceState) {
        if (getIntent() != null) {
            String picture = getIntent().getStringExtra(PICTURE_URL);
            GlideApp.with(mBinding.ivPicture)
                    .load(picture)
                    .error(R.drawable.grey_bg)
                    .placeholder(R.drawable.grey_bg)
                    .into(mBinding.ivPicture);
            mAdapter.setNewData(getIntent().getParcelableArrayListExtra(RESULT_LIST));
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        mLayoutManager.removeOnItemSelectionListener(mListener);
    }
}
