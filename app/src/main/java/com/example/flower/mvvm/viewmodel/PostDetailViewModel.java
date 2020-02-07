package com.example.flower.mvvm.viewmodel;

import android.app.Application;
import android.text.TextUtils;

import androidx.annotation.NonNull;
import androidx.databinding.ObservableField;

import com.alibaba.android.arouter.launcher.ARouter;
import com.example.flower.R;
import com.example.flower.base.BaseViewModel;
import com.example.flower.binding.command.BindingCommand;
import com.example.flower.constant.ARouterPath;
import com.example.flower.constant.Constant;
import com.example.flower.http.bmob.CommentBean;
import com.example.flower.http.bmob.PostBean;
import com.example.flower.http.bmob.UserBean;
import com.example.flower.mvvm.model.PostDetailModel;
import com.example.flower.mvvm.view.activity.CommentActivity;
import com.example.flower.mvvm.view.adapter.CommentListAdapter;
import com.example.flower.mvvm.view.adapter.NineGridAdapter;
import com.example.flower.rxbus.RxBus;
import com.example.flower.rxbus.RxSubscriptions;
import com.example.flower.rxbus.bean.CommandBean;
import com.example.flower.util.LogUtil;
import com.example.flower.util.RxUtil;
import com.example.flower.util.ToastUtil;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Set;

import cn.bmob.v3.BmobUser;
import io.reactivex.disposables.Disposable;

/**
 * @author ShenBen
 * @date 2020/2/2 15:01
 * @email 714081644@qq.com
 */
public class PostDetailViewModel extends BaseViewModel<PostDetailModel> {
    /**
     * 帖子信息
     */
    public ObservableField<PostBean> mPostBeanField = new ObservableField<>();
    /**
     * 帖子的作者的昵称
     */
    public ObservableField<String> mNickNameField = new ObservableField<>();
    /**
     * 九宫格view 的适配器
     */
    public NineGridAdapter mNineGridAdapter;
    /**
     * 评论列表的适配器
     */
    public CommentListAdapter mCommentListAdapter;
    /**
     * 当前帖子的ObjectId
     */
    private String mObjectId;
    /**
     * 喜欢的图标
     */
    public ObservableField<Integer> likesIconField = new ObservableField<>(R.drawable.community_item_collect);
    /**
     * 帖子的喜欢数量
     */
    public ObservableField<String> likesNumber = new ObservableField<>("0");
    /**
     * 帖子的评论数量
     */
    public ObservableField<String> commentNumber = new ObservableField<>("0");
    /**
     * 喜欢帖子的点击事件
     */
    public BindingCommand likesCommand;
    /**
     * 评论帖子的点击事件
     */
    public BindingCommand commentCommand;
    /**
     * 当前的用户
     */
    private UserBean mCurrentUser;
    /**
     * 一些通知事件的回调，类似EventBus
     */
    private Disposable mCommandDisposable;
    /**
     * 返回时，是否要通知更新帖子
     */
    private boolean isNeedUpdatePost = false;

    public PostDetailViewModel(@NonNull Application application) {
        super(application, new PostDetailModel());
        mNineGridAdapter = new NineGridAdapter();
        mCommentListAdapter = new CommentListAdapter();
        likesCommand = new BindingCommand(() -> {
            if (!BmobUser.isLogin()) {
                ToastUtil.warning(getApplication(), "请先登录哦！");
                ARouter.getInstance()
                        .build(ARouterPath.LOGIN_PATH)
                        .navigation();
            } else {
                updateLikes();
            }
        });
        commentCommand = new BindingCommand(() -> {
            if (!BmobUser.isLogin()) {
                ToastUtil.warning(getApplication(), "请先登录哦！");
                ARouter.getInstance()
                        .build(ARouterPath.LOGIN_PATH)
                        .navigation();
            } else {
                if (TextUtils.isEmpty(mObjectId)) {
                    return;
                }
                ARouter.getInstance()
                        .build(ARouterPath.COMMENT_PATH)
                        .withString(CommentActivity.POST_OBJECT_ID, mObjectId)
                        .navigation();
            }
        });

        mCommandDisposable = RxBus.getDefault()
                .register(CommandBean.class, RxUtil.io_main(), bean -> {
                    switch (bean.getCommand()) {
                        case CommandBean.COMMAND_USER_LOGIN_SUCCESS:
                            updatePostInfo();
                            mCommentListAdapter.updateCurrentUser();
                            break;
                        case CommandBean.COMMAND_UPDATE_COMMENT:
                            isNeedUpdatePost = true;
                            queryComment();
                            break;
                    }
                });
        RxSubscriptions.add(mCommandDisposable);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        if (isNeedUpdatePost) {
            RxBus.getDefault().postSticky(new CommandBean(CommandBean.COMMAND_UPDATE_POST));
        }
        RxSubscriptions.remove(mCommandDisposable);
    }

    /**
     * 查询帖子
     *
     * @param objectId
     */
    public void queryPost(String objectId) {
        if (TextUtils.isEmpty(objectId)) {
            ToastUtil.warning(getApplication(), "出错啦");
            return;
        }
        mObjectId = objectId;

        mModel.queryPostByObjectId(objectId, bean -> {
            mPostBeanField.set(bean);
            updatePostInfo();
        }, null);
    }

    /**
     * 查询评论
     */
    public void queryComment() {
        if (TextUtils.isEmpty(mObjectId)) {
            ToastUtil.warning(getApplication(), "出错啦");
            return;
        }
        mModel.queryCommentByPostObjectId(mObjectId, list -> {
            if (list == null) {
                mCommentListAdapter.setEmptyView(R.layout.layout_no_comment);
                mBaseLiveData.setValue(Constant.REFRESH_FAIL);
                return;
            }

            //评论的数量
            commentNumber.set(String.valueOf(list.size()));

            if (list.isEmpty()) {
                mCommentListAdapter.setNewData(null);
                //RecyclerView设置空布局
                mCommentListAdapter.setEmptyView(R.layout.layout_no_comment);
                //如果是下拉刷新
                //则说明没有数据
            } else {
                mCommentListAdapter.setNewData(list);
            }
            mBaseLiveData.setValue(Constant.REFRESH_SUCCESS);

            Set<String> strings = new HashSet<>();
            for (CommentBean bean : list) {
                strings.add(bean.getUser().getObjectId());
            }
            //更新帖子的评价数据
            mModel.updateCommentNumber(mObjectId, list.size(), new ArrayList<>(strings), null);

        }, throwable -> {
            LogUtil.e("获取帖子评论列表失败，" + throwable.toString());
            mCommentListAdapter.setEmptyView(R.layout.layout_no_comment);
            mBaseLiveData.setValue(Constant.REFRESH_FAIL);
        });
    }

    /**
     * 更新帖子信息
     */
    private void updatePostInfo() {
        PostBean bean = mPostBeanField.get();
        if (bean == null) {
            return;
        }
        mCurrentUser = BmobUser.getCurrentUser(UserBean.class);
        UserBean author = bean.getAuthor();

        if (mCurrentUser != null && TextUtils.equals(mCurrentUser.getObjectId(), author.getObjectId())) {
            mNickNameField.set("我");
            //判断当前用户是否已经喜欢过该帖子
            for (String userId : bean.getLikesUserIds()) {
                if (TextUtils.equals(userId, mCurrentUser.getObjectId())) {
                    likesIconField.set(R.drawable.community_item_collect_focus);
                    break;
                }
            }
        } else {
            mNickNameField.set(author.getNickName());
        }

        //喜欢的数量
        likesNumber.set(String.valueOf(bean.getLikesUserIds().size()));
    }

    /**
     * 更好喜欢状态
     */
    private void updateLikes() {
        PostBean bean = mPostBeanField.get();
        if (bean == null) {
            return;
        }
        mModel.updateLikes(bean, e -> {
            if (e == null || e.getErrorCode() == Constant.BMOB_RESULT_OK) {
                isNeedUpdatePost = true;
                likesIconField.set(R.drawable.community_item_collect);
                //判断当前用户是否已经喜欢过该帖子
                for (String userId : bean.getLikesUserIds()) {
                    if (TextUtils.equals(userId, mCurrentUser.getObjectId())) {
                        likesIconField.set(R.drawable.community_item_collect_focus);
                        break;
                    }
                }
                //喜欢的数量
                likesNumber.set(String.valueOf(bean.getLikesUserIds().size()));
            } else {
                LogUtil.i("帖子详情-更改喜欢状态失败，" + e.toString());
                ToastUtil.error(getApplication(), "操作失败，请稍后再试！");
            }
        });
    }

}
