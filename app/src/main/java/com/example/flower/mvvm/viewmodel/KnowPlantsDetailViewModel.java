package com.example.flower.mvvm.viewmodel;

import android.app.Application;
import android.graphics.Rect;
import android.text.TextUtils;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.lifecycle.MutableLiveData;
import androidx.recyclerview.widget.RecyclerView;

import com.example.flower.R;
import com.example.flower.base.BaseViewModel;
import com.example.flower.constant.Constant;
import com.example.flower.http.bean.PlantsDetailBean;
import com.example.flower.mvvm.model.KnowPlantsDetailModel;
import com.example.flower.mvvm.view.adapter.KnowPlantsDetailAdapter;

import java.util.List;

/**
 * @author ShenBen
 * @date 2019/11/30 15:24
 * @email 714081644@qq.com
 */
public class KnowPlantsDetailViewModel extends BaseViewModel<KnowPlantsDetailModel> {

    public KnowPlantsDetailAdapter mDetailAdapter;
    public RecyclerView.ItemDecoration mDetailItemDecoration;
    private int mSpecialDetailPageIndex = -1;
    private String mTypeId;

    public final MutableLiveData<PlantsDetailBean.DataBean> mDetailBeanLiveData = new MutableLiveData();

    public KnowPlantsDetailViewModel(@NonNull Application application) {
        super(application, new KnowPlantsDetailModel());
        mDetailAdapter = new KnowPlantsDetailAdapter();
        mDetailAdapter.setOnItemClickListener((adapter, view, position) -> mDetailBeanLiveData.setValue(mDetailAdapter.getItem(position)));
        mDetailItemDecoration = new RecyclerView.ItemDecoration() {
            @Override
            public void getItemOffsets(@NonNull Rect outRect, @NonNull View view, @NonNull RecyclerView parent, @NonNull RecyclerView.State state) {
                int decoration = (int) getApplication().getResources().getDimension(R.dimen.dp_7);
                outRect.set(decoration, decoration, decoration, decoration);
            }
        };
    }

    public void setTypeId(String id) {
        mTypeId = id;
    }

    public void getDetail(boolean isLoadMore) {
        if (TextUtils.isEmpty(mTypeId)) {
            return;
        }
        if (!isLoadMore) {
            mBaseLiveData.setValue(Constant.RESET_NO_MORE_DATA);
            mSpecialDetailPageIndex = -1;
        }
        ++mSpecialDetailPageIndex;
        mModel.getPlantsDetail(mTypeId, mSpecialDetailPageIndex, plantsDetailBean -> {
            //网络请求结果code为不成功
            if (!TextUtils.equals(Constant.RESULT_OK, plantsDetailBean.getCode())) {
                --mSpecialDetailPageIndex;
                if (isLoadMore) {
                    mBaseLiveData.setValue(Constant.LOAD_MORE_FAIL);
                } else {
                    mBaseLiveData.setValue(Constant.REFRESH_FAIL);
                }
                return;
            }
            List<PlantsDetailBean.DataBean> data = plantsDetailBean.getData();
            //如果数据为空
            if (data == null || data.isEmpty()) {
                if (!isLoadMore) {
                    //RecyclerView设置空布局
                    mDetailAdapter.setEmptyView(R.layout.layout_no_data);
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
                mDetailAdapter.addData(data);
                mBaseLiveData.setValue(Constant.LOAD_MORE_SUCCESS);
            } else {
                //下拉刷新数据成功
                mDetailAdapter.setNewData(data);
                mBaseLiveData.setValue(Constant.REFRESH_SUCCESS);
            }
        }, throwable -> {
            --mSpecialDetailPageIndex;
            if (isLoadMore) {
                mBaseLiveData.setValue(Constant.LOAD_MORE_FAIL);
            } else {
                mBaseLiveData.setValue(Constant.REFRESH_FAIL);
            }
        });
    }
}
