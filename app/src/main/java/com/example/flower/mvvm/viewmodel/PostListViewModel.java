package com.example.flower.mvvm.viewmodel;

import android.app.Application;

import androidx.annotation.NonNull;

import com.alibaba.android.arouter.launcher.ARouter;
import com.example.flower.R;
import com.example.flower.base.BaseViewModel;
import com.example.flower.constant.ARouterPath;
import com.example.flower.constant.Constant;
import com.example.flower.http.bmob.PostBean;
import com.example.flower.mvvm.model.PostListModel;
import com.example.flower.mvvm.view.activity.PostDetailActivity;
import com.example.flower.mvvm.view.adapter.PostListAdapter;
import com.example.flower.rxbus.RxBus;
import com.example.flower.rxbus.RxSubscriptions;
import com.example.flower.rxbus.bean.CommandBean;
import com.example.flower.util.LogUtil;
import com.example.flower.util.RxUtil;
import com.example.flower.util.ToastUtil;

import cn.bmob.v3.BmobUser;
import io.reactivex.disposables.Disposable;

/**
 * @author ShenBen
 * @date 2020/2/1 15:35
 * @email 714081644@qq.com
 */
public class PostListViewModel extends BaseViewModel<PostListModel> {
    public PostListAdapter mPostListAdapter;
    private String mTypeObjectId;
    /**
     * 选择查看的帖子的位置
     */
    private int mCheckDetailPosition;

    /**
     * 一些通知事件的回调，类似EventBus
     */
    private Disposable mCommandDisposable;

    public PostListViewModel(@NonNull Application application) {
        super(application, new PostListModel());
        mPostListAdapter = new PostListAdapter();
        mPostListAdapter.setOnItemClickListener((adapter, view, position) -> {
            mCheckDetailPosition = position;
            PostBean item = mPostListAdapter.getItem(position);
            if (item == null) {
                return;
            }
            ARouter.getInstance()
                    .build(ARouterPath.POST_DETAIL_PATH)
                    .withString(PostDetailActivity.POST_OBJECT_ID, item.getObjectId())
                    .navigation();
        });
        mPostListAdapter.setOnItemChildClickListener((adapter, view, position) -> {
            PostBean item = mPostListAdapter.getItem(position);
            if (item == null) {
                return;
            }
            switch (view.getId()) {
                case R.id.ivLikes:
                    if (!BmobUser.isLogin()) {
                        ToastUtil.warning(getApplication(), "请先登录哦！");
                        ARouter.getInstance()
                                .build(ARouterPath.LOGIN_PATH)
                                .navigation();
                    } else {
                        updateLikes(item, position);
                    }
                    break;
            }
        });
        mCommandDisposable = RxBus.getDefault()
                .register(CommandBean.class, RxUtil.io_main(), bean -> {
                    switch (bean.getCommand()) {
                        case CommandBean.COMMAND_UPDATE_POST:
                            updatePostByPosition(mCheckDetailPosition);
                            break;
                        case CommandBean.COMMAND_USER_LOGIN_SUCCESS:
                        case CommandBean.COMMAND_USER_UPDATE_INFO:
                            mPostListAdapter.updateCurrentUser();
                            break;
                        case CommandBean.COMMAND_PUBLISH_POST_SUCCESS:
                            //通知自动刷新
                            mBaseLiveData.setValue(Constant.AUTO_REFRESH);
                            break;
                    }
                });
        RxSubscriptions.add(mCommandDisposable);
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
                    mPostListAdapter.setEmptyView(R.layout.layout_no_data);
                    mBaseLiveData.setValue(Constant.REFRESH_FAIL);
                }
                return;
            }
            if (list.isEmpty()) {
                if (!isLoadMore) {
                    mPostListAdapter.setNewData(null);
                    //RecyclerView设置空布局
                    mPostListAdapter.setEmptyView(R.layout.layout_no_data);
                    //如果是下拉刷新
                    //则说明没有数据
                    mBaseLiveData.setValue(Constant.REFRESH_SUCCESS);
                }
                //说明已经加载完所有数据
                mBaseLiveData.setValue(Constant.LOAD_MORE_COMPLETE);
                return;
            }
            if (isLoadMore) {
                mPostListAdapter.addData(list);
                mBaseLiveData.setValue(Constant.LOAD_MORE_SUCCESS);
            } else {
                mPostListAdapter.setNewData(list);
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

    @Override
    public void onDestroy() {
        super.onDestroy();
        RxSubscriptions.remove(mCommandDisposable);
    }

    /**
     * 更新喜欢状态
     *
     * @param item
     * @param position
     */
    private void updateLikes(PostBean item, int position) {
        mModel.updateLikes(item, e -> {
            if (e == null || e.getErrorCode() == Constant.BMOB_RESULT_OK) {
                //说明操作成功
                updatePostByPosition(position);
            } else {
                ToastUtil.error(getApplication(), "操作失败，" + e.toString());
                LogUtil.e("操作失败，" + e.toString());
            }
        });
    }

    /**
     * 更新某个帖子的数据
     *
     * @param position
     */
    private void updatePostByPosition(int position) {
        PostBean item = mPostListAdapter.getItem(position);
        if (item == null) {
            return;
        }
        mModel.queryPostByObjectId(item.getObjectId(), bean -> {
            //更新数据
            mPostListAdapter.getData().set(position, bean);
            mPostListAdapter.notifyItemChanged(position + mPostListAdapter.getHeaderLayoutCount(), PostListAdapter.UPDATE_ITEM_LIKES_COMMENT);
        }, null);
    }
}
