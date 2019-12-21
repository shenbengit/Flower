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
import com.example.flower.http.bean.WallpaperBean;
import com.example.flower.mvvm.model.WallpaperModel;
import com.example.flower.mvvm.view.activity.WallpaperDetailActivity;
import com.example.flower.mvvm.view.adapter.WallpaperAdapter;

import java.util.List;

/**
 * @author ShenBen
 * @date 2019/9/22 13:46
 * @email 714081644@qq.com
 */
public class WallpaperViewModel extends BaseViewModel<WallpaperModel> {

    public WallpaperAdapter mWallpaperAdapter;
    public RecyclerView.ItemDecoration mWallpaperItemDecoration;
    private int mPageIndex = -1;

    public WallpaperViewModel(@NonNull Application application) {
        super(application, new WallpaperModel());
        mWallpaperAdapter = new WallpaperAdapter();
        mWallpaperAdapter.setOnItemClickListener((adapter, view, position) -> {
            WallpaperBean.DataBean item = mWallpaperAdapter.getItem(position);
            if (item != null) {
                ARouter.getInstance()
                        .build(ARouterPath.WALLPAPER_DETAIL_PATH)
                        .withString(WallpaperDetailActivity.DETAIL_URL, item.getBigImg())
                        .navigation();
            }
        });
        mWallpaperItemDecoration = new RecyclerView.ItemDecoration() {
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

    public void getWallpaper(boolean isLoadMore) {
        if (!isLoadMore) {
            mBaseLiveData.setValue(Constant.RESET_NO_MORE_DATA);
            mPageIndex = -1;
        }
        ++mPageIndex;
        mModel.getWallpaper(mPageIndex, wallpaperBean -> {
            if (TextUtils.equals(Constant.RESULT_OK, wallpaperBean.getCode())) {
                List<WallpaperBean.DataBean> data = wallpaperBean.getData();
                //如果数据为空
                if (data == null || data.isEmpty()) {
                    if (!isLoadMore) {
                        //RecyclerView设置空布局
                        mWallpaperAdapter.setEmptyView(R.layout.layout_no_data);
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
                    mWallpaperAdapter.addData(data);
                    mBaseLiveData.setValue(Constant.LOAD_MORE_SUCCESS);
                } else {
                    //下拉刷新数据成功
                    mWallpaperAdapter.setNewData(data);
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
