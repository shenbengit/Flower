package com.example.flower.mvvm.viewmodel;

import android.app.Application;
import android.graphics.Rect;
import android.text.TextUtils;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import androidx.recyclerview.widget.StaggeredGridLayoutManager;

import com.alibaba.android.arouter.launcher.ARouter;
import com.example.flower.R;
import com.example.flower.base.BaseViewModel;
import com.example.flower.constant.ARouterPath;
import com.example.flower.constant.Constant;
import com.example.flower.http.bean.SpecialDetailBean;
import com.example.flower.mvvm.model.SpecialListModel;
import com.example.flower.mvvm.view.activity.DetailWebActivity;
import com.example.flower.mvvm.view.adapter.SpecialDetailAdapter;

import java.util.List;

/**
 * @author ShenBen
 * @date 2019/10/7 14:46
 * @email 714081644@qq.com
 */
public class SpecialListViewModel extends BaseViewModel<SpecialListModel> {

    /**
     * 专题类型的ID
     */
    private String mSpecialTypeId;


    private int mSpecialDetailPageIndex = -1;
    public SpecialDetailAdapter mSpecialDetailAdapter;
    public StaggeredGridLayoutManager mSpecialDetailLayoutManager;
    public RecyclerView.ItemDecoration mSpecialDetailItemDecoration;

    public SpecialListViewModel(@NonNull Application application) {
        super(application, new SpecialListModel());
        mSpecialDetailAdapter = new SpecialDetailAdapter();
        mSpecialDetailLayoutManager = new StaggeredGridLayoutManager(2, StaggeredGridLayoutManager.VERTICAL);
        mSpecialDetailLayoutManager.setGapStrategy(StaggeredGridLayoutManager.GAP_HANDLING_NONE);
        mSpecialDetailItemDecoration = new RecyclerView.ItemDecoration() {
            @Override
            public void getItemOffsets(@NonNull Rect outRect, @NonNull View view, @NonNull RecyclerView parent, @NonNull RecyclerView.State state) {
                int decoration = (int) getApplication().getResources().getDimension(R.dimen.dp_7);
                outRect.set(decoration, decoration, decoration, decoration);
            }
        };

        mSpecialDetailAdapter.setOnItemClickListener((adapter, view, position) -> ARouter.getInstance()
                .build(ARouterPath.DETAIL_WEB_ACTIVITY_PATH)
                .withParcelable(DetailWebActivity.DETAIL_BEAN, mSpecialDetailAdapter.getItem(position))
                .navigation());
    }

    public void setSpecialTypeId(String specialTypeId) {
        mSpecialTypeId = specialTypeId;
    }

    public void getSpecialList(boolean isLoadMore) {
        if (TextUtils.isEmpty(mSpecialTypeId)) {
            return;
        }
        if (!isLoadMore) {
            mBaseLiveData.setValue(Constant.RESET_NO_MORE_DATA);
            mSpecialDetailPageIndex = -1;
        }
        ++mSpecialDetailPageIndex;
        mModel.getSpecialList(mSpecialTypeId, mSpecialDetailPageIndex, "", specialDetailBean -> {
            //网络请求结果code为不成功
            if (!TextUtils.equals(Constant.RESULT_OK, specialDetailBean.getCode())) {
                --mSpecialDetailPageIndex;
                if (isLoadMore) {
                    mBaseLiveData.setValue(Constant.LOAD_MORE_FAIL);
                } else {
                    mBaseLiveData.setValue(Constant.REFRESH_FAIL);
                }
                return;
            }
            List<SpecialDetailBean.DataBean> data = specialDetailBean.getData();
            //如果数据为空
            if (data == null || data.isEmpty()) {
                if (!isLoadMore) {
                    //RecyclerView设置空布局
                    mSpecialDetailAdapter.setEmptyView(R.layout.layout_no_data);
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
                mSpecialDetailAdapter.addData(data);
                mBaseLiveData.setValue(Constant.LOAD_MORE_SUCCESS);
            } else {
                //下拉刷新数据成功
                mSpecialDetailAdapter.setNewData(data);
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
