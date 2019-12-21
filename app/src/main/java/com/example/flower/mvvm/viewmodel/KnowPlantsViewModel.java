package com.example.flower.mvvm.viewmodel;

import android.app.Application;
import android.graphics.Rect;
import android.text.TextUtils;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.alibaba.android.arouter.launcher.ARouter;
import com.example.flower.R;
import com.example.flower.base.BaseViewModel;
import com.example.flower.constant.ARouterPath;
import com.example.flower.constant.Constant;
import com.example.flower.http.bean.PlantsTypeBean;
import com.example.flower.mvvm.model.KnowPlantsModel;
import com.example.flower.mvvm.view.activity.KnowPlantsDetailActivity;
import com.example.flower.mvvm.view.adapter.PlantsTypeAdapter;

import java.util.List;

public class KnowPlantsViewModel extends BaseViewModel<KnowPlantsModel> {

    public PlantsTypeAdapter mPlantsTypeAdapter;
    public RecyclerView.ItemDecoration mPlantsTypeItemDecoration;
    private int mPageIndex = -1;

    public KnowPlantsViewModel(@NonNull Application application) {
        super(application, new KnowPlantsModel());
        mPlantsTypeAdapter = new PlantsTypeAdapter();
        mPlantsTypeAdapter.setOnItemClickListener((adapter, view, position) -> {
            PlantsTypeBean.DataBean item = mPlantsTypeAdapter.getItem(position);
            if (item == null) {
                return;
            }
            ARouter.getInstance()
                    .build(ARouterPath.KNOW_PLANTS_DETAIL_PATH)
                    .withString(KnowPlantsDetailActivity.TITLE, item.getTitle())
                    .withString(KnowPlantsDetailActivity.TYPE_ID, item.getId())
                    .navigation();
        });
        mPlantsTypeItemDecoration = new RecyclerView.ItemDecoration() {
            @Override
            public void getItemOffsets(@NonNull Rect outRect, @NonNull View view, @NonNull RecyclerView parent, @NonNull RecyclerView.State state) {
                RecyclerView.LayoutManager layoutManager = parent.getLayoutManager();
                if (layoutManager instanceof GridLayoutManager) {
                    GridLayoutManager gridLayoutManager = (GridLayoutManager) layoutManager;

                    int dimension_5 = (int) application.getResources().getDimension(R.dimen.dp_5);
                    int dimension_10 = (int) application.getResources().getDimension(R.dimen.dp_10);
                    //列数
                    int spanCount = gridLayoutManager.getSpanCount();
                    //当前view的position
                    int position = parent.getChildLayoutPosition(view);

                    //实际位置+1，position是下标
                    ++position;
                    int column = (position) % spanCount;
                    if (column == 1) {
                        //在第一列
                        outRect.set(dimension_10, dimension_5, dimension_5, dimension_5);
                    } else if (column == 0) {
                        //在最后一列
                        outRect.set(dimension_5, dimension_5, dimension_10, dimension_5);
                    } else {
                        //在中间的位置
                        outRect.set(dimension_5, dimension_5, dimension_5, dimension_5);
                    }
                }

            }
        };
    }

    public void getPlantsType(boolean isLoadMore) {
        if (!isLoadMore) {
            mBaseLiveData.setValue(Constant.RESET_NO_MORE_DATA);
            mPageIndex = -1;
        }
        ++mPageIndex;
        mModel.getPlantsType(mPageIndex, plantsTypeBean -> {
            if (TextUtils.equals(Constant.RESULT_OK, plantsTypeBean.getCode())) {
                List<PlantsTypeBean.DataBean> data = plantsTypeBean.getData();
                //如果数据为空
                if (data == null || data.isEmpty()) {
                    if (!isLoadMore) {
                        //RecyclerView设置空布局
                        mPlantsTypeAdapter.setEmptyView(R.layout.layout_no_data);
                        //如果是下拉刷新
                        //则说明没有数据
                        mBaseLiveData.setValue(Constant.REFRESH_SUCCESS);
                    }
                    //说明已经加载完所有数据
                    mBaseLiveData.setValue(Constant.LOAD_MORE_COMPLETE);
                    return;
                }
                //数据不为空的情况
                if (isLoadMore) {
                    //上拉加载数据成功
                    mPlantsTypeAdapter.addData(data);
                    mBaseLiveData.setValue(Constant.LOAD_MORE_SUCCESS);
                } else {
                    //下拉刷新数据成功
                    mPlantsTypeAdapter.setNewData(data);
                    mBaseLiveData.setValue(Constant.REFRESH_SUCCESS);
                }
            } else {
                --mPageIndex;
                if (isLoadMore) {
                    mBaseLiveData.setValue(Constant.LOAD_MORE_FAIL);
                } else {
                    mBaseLiveData.setValue(Constant.REFRESH_FAIL);
                }
            }
        }, throwable -> {
            --mPageIndex;
            if (isLoadMore) {
                mBaseLiveData.setValue(Constant.LOAD_MORE_FAIL);
            } else {
                mBaseLiveData.setValue(Constant.REFRESH_FAIL);
            }
        });
    }


}
