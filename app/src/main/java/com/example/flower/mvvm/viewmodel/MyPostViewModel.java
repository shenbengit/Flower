package com.example.flower.mvvm.viewmodel;

import android.app.Application;
import android.text.TextUtils;
import android.view.Gravity;

import androidx.annotation.NonNull;
import androidx.databinding.ObservableField;

import com.alibaba.android.arouter.launcher.ARouter;
import com.example.flower.R;
import com.example.flower.base.BaseViewModel;
import com.example.flower.constant.ARouterPath;
import com.example.flower.constant.Constant;
import com.example.flower.http.bmob.PostBean;
import com.example.flower.http.bmob.UserBean;
import com.example.flower.mvvm.model.PostListModel;
import com.example.flower.mvvm.view.activity.PostDetailActivity;
import com.example.flower.mvvm.view.adapter.PostListAdapter;
import com.example.flower.mvvm.view.widget.DeleteIdentifyPopupWindow;
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
 * @date 2020/2/6 12:44
 * @email 714081644@qq.com
 */
public class MyPostViewModel extends BaseViewModel<PostListModel> {
    /**
     * 用户头像
     */
    public ObservableField<Object> userAvatarField = new ObservableField<>(R.drawable.icon_profile_default_portrait);
    /**
     * 用户昵称
     */
    public ObservableField<String> userNameField = new ObservableField<>();
    /**
     * 用户个性签名
     */
    public ObservableField<String> userSignatureField = new ObservableField<>();
    /**
     * 用户性别图标
     */
    public ObservableField<Integer> userSexField = new ObservableField<>(0);

    public PostListAdapter mPostListAdapter;
    private UserBean mCurrentUser;
    /**
     * 选择查看的帖子的位置
     */
    private int mCheckDetailPosition;

    /**
     * 一些通知事件的回调，类似EventBus
     */
    private Disposable mCommandDisposable;
    /**
     * 删除PopupWindow
     */
    private DeleteIdentifyPopupWindow mPopupWindow;

    public MyPostViewModel(@NonNull Application application) {
        super(application, new PostListModel());
        mCurrentUser = BmobUser.getCurrentUser(UserBean.class);
        mPostListAdapter = new PostListAdapter();
        mPostListAdapter.setShowMore(true);
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
                    if (mCurrentUser == null) {
                        return;
                    }
                    updateLikes(item, position);
                    break;
                case R.id.ivMore:
                    //显示 删除 PopupWindow
                    if (mPopupWindow == null) {
                        mPopupWindow = new DeleteIdentifyPopupWindow(view.getContext());
                        mPopupWindow.setPopupGravity(Gravity.CENTER_HORIZONTAL | Gravity.TOP);
                        mPopupWindow.setOnDeleteListener(() -> deletePost(position));
                    }
                    mPopupWindow.linkTo(view);
                    mPopupWindow.showPopupWindow(view);
                    break;
            }
        });
        mCommandDisposable = RxBus.getDefault()
                .register(CommandBean.class, RxUtil.io_main(), bean -> {
                    switch (bean.getCommand()) {
                        case CommandBean.COMMAND_UPDATE_POST:
                            updatePostByPosition(mCheckDetailPosition);
                            break;
                    }
                });
        RxSubscriptions.add(mCommandDisposable);
    }

    @Override
    public void onCreate() {
        super.onCreate();
        if (mCurrentUser != null) {
            userAvatarField.set(mCurrentUser.getHeadImg() == null ? R.drawable.icon_profile_default_portrait : mCurrentUser.getHeadImg().getFileUrl());
            userSexField.set(TextUtils.equals(mCurrentUser.getSex(), getApplication().getString(R.string.woman)) ? R.drawable.ic_user_woman : R.drawable.ic_user_man);
            userNameField.set(TextUtils.isEmpty(mCurrentUser.getNickName()) ? mCurrentUser.getUsername() : mCurrentUser.getNickName());
            userSignatureField.set(mCurrentUser.getSignature());
        }
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        RxSubscriptions.remove(mCommandDisposable);
    }

    /**
     * 根据帖子类型查询帖子
     *
     * @param isLoadMore true: 上拉加载 ,false: 下拉刷新
     */
    public void queryPost(boolean isLoadMore) {
        if (mCurrentUser == null) {
            ToastUtil.warning(getApplication(), "请先登录！");
            if (isLoadMore) {
                mBaseLiveData.setValue(Constant.LOAD_MORE_FAIL);
            } else {
                mPostListAdapter.setEmptyView(R.layout.layout_no_post);
                mBaseLiveData.setValue(Constant.REFRESH_FAIL);
            }
            return;
        }
        mModel.queryPostByUser(mCurrentUser, isLoadMore, list -> {
            if (list == null) {
                if (isLoadMore) {
                    mBaseLiveData.setValue(Constant.LOAD_MORE_FAIL);
                } else {
                    mPostListAdapter.setEmptyView(R.layout.layout_no_post);
                    mBaseLiveData.setValue(Constant.REFRESH_FAIL);
                }
                return;
            }
            if (list.isEmpty()) {
                if (!isLoadMore) {
                    mPostListAdapter.setNewData(null);
                    //RecyclerView设置空布局
                    mPostListAdapter.setEmptyView(R.layout.layout_no_post);
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

    /**
     * 删除帖子
     *
     * @param position
     */
    private void deletePost(int position) {
        PostBean item = mPostListAdapter.getItem(position);
        if (item == null) {
            return;
        }
        mModel.deletePostById(item.getObjectId(), e -> {
            if (e == null || e.getErrorCode() == Constant.BMOB_RESULT_OK) {
                ToastUtil.success(getApplication(), "删除成功");
                mPostListAdapter.remove(position);
            } else {
                ToastUtil.error(getApplication(), "删除失败，" + e.toString());
                LogUtil.e("删除帖子失败，" + e.toString());
            }
        });
    }
}
