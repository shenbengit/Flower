package com.example.flower.mvvm.viewmodel;

import android.app.Application;

import androidx.annotation.NonNull;

import com.alibaba.android.arouter.launcher.ARouter;
import com.example.flower.R;
import com.example.flower.base.BaseViewModel;
import com.example.flower.constant.ARouterPath;
import com.example.flower.constant.Constant;
import com.example.flower.http.bmob.PostBean;
import com.example.flower.http.bmob.UserBean;
import com.example.flower.mvvm.model.PostListModel;
import com.example.flower.mvvm.view.activity.PostDetailActivity;
import com.example.flower.mvvm.view.adapter.CollectionAdapter;
import com.example.flower.rxbus.RxBus;
import com.example.flower.rxbus.RxSubscriptions;
import com.example.flower.rxbus.bean.CommandBean;
import com.example.flower.util.RxUtil;
import com.example.flower.util.ToastUtil;

import cn.bmob.v3.BmobUser;
import io.reactivex.disposables.Disposable;

/**
 * @author ShenBen
 * @date 2020/2/3 16:27
 * @email 714081644@qq.com
 */
public class CollectionViewModel extends BaseViewModel<PostListModel> {
    public CollectionAdapter mCollectionAdapter;
    private UserBean mCurrentUser;
    /**
     * 一些通知事件的回调，类似EventBus
     */
    private Disposable mCommandDisposable;

    public CollectionViewModel(@NonNull Application application) {
        super(application, new PostListModel());
        mCurrentUser = BmobUser.getCurrentUser(UserBean.class);
        mCollectionAdapter = new CollectionAdapter();
        mCollectionAdapter.setOnItemClickListener((adapter, view, position) -> {
            PostBean item = mCollectionAdapter.getItem(position);
            if (item == null) {
                return;
            }
            ARouter.getInstance()
                    .build(ARouterPath.POST_DETAIL_PATH)
                    .withString(PostDetailActivity.POST_OBJECT_ID, item.getObjectId())
                    .navigation();
        });
        mCommandDisposable = RxBus.getDefault()
                .register(CommandBean.class, RxUtil.io_main(), bean -> {
                    switch (bean.getCommand()) {
                        case CommandBean.COMMAND_UPDATE_POST:
                            queryCollection(false);
                            break;
                    }
                });
        RxSubscriptions.add(mCommandDisposable);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        RxSubscriptions.remove(mCommandDisposable);
    }

    /**
     * 查询收藏列表
     *
     * @param isLoadMore
     */
    public void queryCollection(boolean isLoadMore) {
        if (mCurrentUser == null) {
            ToastUtil.warning(getApplication(), "请先登录！");
            if (isLoadMore) {
                mBaseLiveData.setValue(Constant.LOAD_MORE_FAIL);
            } else {
                mCollectionAdapter.setEmptyView(R.layout.layout_no_post);
                mBaseLiveData.setValue(Constant.REFRESH_FAIL);
            }
            return;
        }
        mModel.queryUserCollectionPost(mCurrentUser.getObjectId(), isLoadMore, list -> {
            if (list == null) {
                if (isLoadMore) {
                    mBaseLiveData.setValue(Constant.LOAD_MORE_FAIL);
                } else {
                    mCollectionAdapter.setEmptyView(R.layout.layout_no_post);
                    mBaseLiveData.setValue(Constant.REFRESH_FAIL);
                }
                return;
            }
            if (list.isEmpty()) {
                if (!isLoadMore) {
                    mCollectionAdapter.setNewData(null);
                    //RecyclerView设置空布局
                    mCollectionAdapter.setEmptyView(R.layout.layout_no_post);
                    //如果是下拉刷新
                    //则说明没有数据
                    mBaseLiveData.setValue(Constant.REFRESH_SUCCESS);
                }
                //说明已经加载完所有数据
                mBaseLiveData.setValue(Constant.LOAD_MORE_COMPLETE);
                return;
            }
            if (isLoadMore) {
                mCollectionAdapter.addData(list);
                mBaseLiveData.setValue(Constant.LOAD_MORE_SUCCESS);
            } else {
                mCollectionAdapter.setNewData(list);
                mBaseLiveData.setValue(Constant.REFRESH_SUCCESS);
            }
            if (list.size() < PostListModel.PAGE_SIZE) {
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
