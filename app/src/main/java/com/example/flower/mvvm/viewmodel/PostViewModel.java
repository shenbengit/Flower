package com.example.flower.mvvm.viewmodel;

import android.app.Application;
import android.graphics.Rect;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.flower.R;
import com.example.flower.base.BaseViewModel;
import com.example.flower.constant.Constant;
import com.example.flower.mvvm.model.PostModel;
import com.example.flower.mvvm.view.adapter.PostAdapter;

/**
 * @author ShenBen
 * @date 2020/2/1 15:35
 * @email 714081644@qq.com
 */
public class PostViewModel extends BaseViewModel<PostModel> {
    public PostAdapter mPostAdapter;
    public RecyclerView.ItemDecoration mItemDecoration = new RecyclerView.ItemDecoration() {
        @Override
        public void getItemOffsets(@NonNull Rect outRect, @NonNull View view, @NonNull RecyclerView parent, @NonNull RecyclerView.State state) {
            outRect.bottom = (int) getApplication().getResources().getDimension(R.dimen.dp_5);
            outRect.top = (int) getApplication().getResources().getDimension(R.dimen.dp_5);
        }
    };

    private String mTypeObjectId;

    public PostViewModel(@NonNull Application application) {
        super(application, new PostModel());
        mPostAdapter = new PostAdapter();
    }

    public void setPostTypeObjectId(String objectId) {
        mTypeObjectId = objectId;
    }

    /**
     * 根据帖子类型查询帖子
     *
     * @param isLoadMore true: 上拉加载 ,false: 下拉刷新
     */
    public void queryPost(boolean isLoadMore) {
        mModel.queryPostByType(mTypeObjectId, isLoadMore, list -> {
            if (list == null) {
                if (isLoadMore) {
                    mBaseLiveData.setValue(Constant.LOAD_MORE_FAIL);
                } else {
                    mBaseLiveData.setValue(Constant.REFRESH_FAIL);
                }
                return;
            }
            if (list.isEmpty()) {
                if (!isLoadMore) {
                    //RecyclerView设置空布局
                    mPostAdapter.setEmptyView(R.layout.layout_no_data);
                    //如果是下拉刷新
                    //则说明没有数据
                    mBaseLiveData.setValue(Constant.REFRESH_SUCCESS);
                }
                //说明已经加载完所有数据
                mBaseLiveData.setValue(Constant.LOAD_MORE_COMPLETE);
                return;
            }
            if (isLoadMore) {
                mPostAdapter.addData(list);
                mBaseLiveData.setValue(Constant.LOAD_MORE_SUCCESS);
            } else {
                mPostAdapter.setNewData(list);
                mBaseLiveData.setValue(Constant.REFRESH_SUCCESS);
            }
            if (list.size() < PostModel.PAGE_SIZE) {
                //查询到的数据小于分页查询一页的数据，说明已经加载完所有数据
                mBaseLiveData.setValue(Constant.LOAD_MORE_COMPLETE);
            }
        }, throwable -> {
            if (isLoadMore) {
                mBaseLiveData.setValue(Constant.LOAD_MORE_FAIL);
            } else {
                mBaseLiveData.setValue(Constant.REFRESH_FAIL);
            }
        });
    }
}
