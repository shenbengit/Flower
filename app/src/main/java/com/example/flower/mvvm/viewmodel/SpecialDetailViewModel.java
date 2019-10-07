package com.example.flower.mvvm.viewmodel;

import android.app.Application;
import android.graphics.Rect;
import android.text.TextUtils;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.recyclerview.widget.StaggeredGridLayoutManager;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.example.flower.R;
import com.example.flower.base.BaseViewModel;
import com.example.flower.http.bean.SpecialDetailBean;
import com.example.flower.mvvm.model.SpecialDetailModel;
import com.example.flower.mvvm.view.adapter.SpecialDetailAdapter;
import com.example.flower.mvvm.view.adapter.SpecialTypeAdapter;
import com.example.flower.util.ToastUtil;

import java.util.List;
import java.util.Objects;

/**
 * @author ShenBen
 * @date 2019/10/7 14:46
 * @email 714081644@qq.com
 */
public class SpecialDetailViewModel extends BaseViewModel<SpecialDetailModel> {

    /**
     * 下拉刷新成功
     */
    public static final String REFRESH_SUCCESS = "REFRESH_SUCCESS";
    /**
     * 下拉刷新失败
     */
    public static final String REFRESH_FAIL = "REFRESH_FAIL";
    /**
     * 上拉加载成功
     */
    public static final String LOAD_MORE_SUCCESS = "LOAD_MORE_SUCCESS";
    /**
     * 上拉加载失败
     */
    public static final String LOAD_MORE_FAIL = "LOAD_MORE_FAIL";
    /**
     * 上拉加载完所有数据
     */
    public static final String LOAD_MORE_COMPLETE = "LOAD_MORE_COMPLETE";
    /**
     * 重置没有更多数据的状态
     */
    public static final String RESET_NO_MORE_DATA = "RESET_NO_MORE_DATA";

    /**
     * 分类相关
     */
    public SpecialTypeAdapter mSpecialTypeAdapter;
    public LinearLayoutManager mSpecialTypeLayoutManager;
    public RecyclerView.ItemDecoration mSpecialTypeItemDecoration;
    private int mSpecialTypeCheckedPosition = 0;
    private String mSpecialTypeId;

    private int mSpecialDetailPageIndex = -1;
    public SpecialDetailAdapter mSpecialDetailAdapter;
    public StaggeredGridLayoutManager mSpecialDetailLayoutManager;
    public RecyclerView.ItemDecoration mSpecialDetailItemDecoration;

    public SpecialDetailViewModel(@NonNull Application application) {
        super(application, new SpecialDetailModel());
        mSpecialTypeAdapter = new SpecialTypeAdapter();
        mSpecialTypeLayoutManager = new LinearLayoutManager(application, LinearLayoutManager.HORIZONTAL, false);
        mSpecialTypeItemDecoration = new RecyclerView.ItemDecoration() {
            @Override
            public void getItemOffsets(@NonNull Rect outRect, @NonNull View view, @NonNull RecyclerView parent, @NonNull RecyclerView.State state) {
                int left;
                int right;
                left = right = (int) getApplication().getResources().getDimension(R.dimen.dp_15);
                outRect.set(left, 0, right, 0);
            }
        };
        mSpecialTypeAdapter.setOnItemChildClickListener((adapter, view, position) -> {
            switch (view.getId()) {
                case R.id.tvSpecialType:
                    if (mSpecialTypeCheckedPosition == position) {
                        return;
                    }
                    Objects.requireNonNull(mSpecialTypeAdapter.getItem(mSpecialTypeCheckedPosition)).setChecked(false);
                    mSpecialTypeAdapter.notifyItemChanged(mSpecialTypeCheckedPosition, "unchecked");
                    Objects.requireNonNull(mSpecialTypeAdapter.getItem(position)).setChecked(true);
                    mSpecialTypeAdapter.notifyItemChanged(position, "checked");
                    mSpecialTypeCheckedPosition = position;

                    mSpecialTypeId = mSpecialTypeAdapter.getItem(mSpecialTypeCheckedPosition).getId();
                    getSpecialDetail(false);
                    break;
                default:
                    break;
            }
        });


        mSpecialDetailAdapter = new SpecialDetailAdapter();
        mSpecialDetailLayoutManager = new StaggeredGridLayoutManager(2, StaggeredGridLayoutManager.VERTICAL);
        mSpecialDetailItemDecoration = new RecyclerView.ItemDecoration() {
            @Override
            public void getItemOffsets(@NonNull Rect outRect, @NonNull View view, @NonNull RecyclerView parent, @NonNull RecyclerView.State state) {
                int decoration = (int) getApplication().getResources().getDimension(R.dimen.dp_7);
                outRect.set(decoration, decoration, decoration, decoration);
            }
        };

        mSpecialDetailAdapter.setOnItemClickListener(new BaseQuickAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
                ToastUtil.show(getApplication(), "点击详情列表，position：" + position);
            }
        });
    }

    public void getSpecialTypeList(String id) {
        mSpecialTypeCheckedPosition = 0;

        mModel.getSpecialTypeList(id, bean -> {
            if (bean.getData() != null && !bean.getData().isEmpty()) {
                bean.getData().get(mSpecialTypeCheckedPosition).setChecked(true);
                mSpecialTypeAdapter.setNewData(bean.getData());

                mSpecialTypeId = bean.getData().get(mSpecialTypeCheckedPosition).getId();
                getSpecialDetail(false);
            }
        }, null);
    }

    public void getSpecialDetail(boolean isLoadMore) {
        if (TextUtils.isEmpty(mSpecialTypeId)) {
            return;
        }
        if (!isLoadMore) {
            mBaseLiveData.setValue(RESET_NO_MORE_DATA);
            mSpecialDetailPageIndex = -1;
        }
        ++mSpecialDetailPageIndex;
        mModel.getSpecialDetail(mSpecialTypeId, mSpecialDetailPageIndex, "", specialDetailBean -> {
            List<SpecialDetailBean.DataBean> data = specialDetailBean.getData();
            //如果数据为空
            if (data == null || data.isEmpty()) {
                if (isLoadMore) {
                    //如果是上拉加载
                    //则说明已经加载完所有数据
                    mBaseLiveData.setValue(LOAD_MORE_COMPLETE);
                } else {
                    //如果是下拉刷新
                    //则说明没有数据
                    mBaseLiveData.setValue(REFRESH_SUCCESS);
                    mSpecialDetailAdapter.setNewData(null);
                    ToastUtil.show(getApplication(), "没有数据");
                }
                return;
            }
            //数据不为空的情况
            if (isLoadMore) {
                //上拉加载数据成功
                mSpecialDetailAdapter.addData(data);
                mBaseLiveData.setValue(LOAD_MORE_SUCCESS);
            } else {
                //下拉刷新数据成功
                mSpecialDetailAdapter.setNewData(data);
                mBaseLiveData.setValue(REFRESH_SUCCESS);
            }
        }, throwable -> {
            if (isLoadMore) {
                mBaseLiveData.setValue(LOAD_MORE_FAIL);
            } else {
                mBaseLiveData.setValue(REFRESH_FAIL);
            }
        });
    }

}
