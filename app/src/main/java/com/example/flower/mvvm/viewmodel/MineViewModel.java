package com.example.flower.mvvm.viewmodel;

import android.app.Application;
import android.text.TextUtils;
import android.view.Gravity;

import androidx.annotation.NonNull;
import androidx.databinding.ObservableField;

import com.alibaba.android.arouter.launcher.ARouter;
import com.example.flower.R;
import com.example.flower.base.BaseViewModel;
import com.example.flower.binding.command.BindingCommand;
import com.example.flower.constant.ARouterPath;
import com.example.flower.constant.Constant;
import com.example.flower.http.bmob.IdentifyResultBean;
import com.example.flower.http.bmob.UserBean;
import com.example.flower.mvvm.model.MineModel;
import com.example.flower.mvvm.view.activity.KnowFlowerResultActivity;
import com.example.flower.mvvm.view.adapter.KnowFlowerRecordAdapter;
import com.example.flower.mvvm.view.widget.DeleteIdentifyPopupWindow;
import com.example.flower.rxbus.RxBus;
import com.example.flower.rxbus.RxSubscriptions;
import com.example.flower.rxbus.bean.CommandBean;
import com.example.flower.util.LogUtil;
import com.example.flower.util.RxUtil;
import com.example.flower.util.ToastUtil;

import java.util.ArrayList;

import cn.bmob.v3.BmobUser;
import io.reactivex.disposables.Disposable;

/**
 * @author ShenBen
 * @date 2019/9/22 13:41
 * @email 714081644@qq.com
 */
public class MineViewModel extends BaseViewModel<MineModel> {

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
    /**
     * 用户的发帖数量
     */
    public ObservableField<Integer> userPostNumberField = new ObservableField<>(0);
    /**
     * 用户的收藏数量
     */
    public ObservableField<Integer> userCollectionNumberField = new ObservableField<>(0);
    /**
     * 用户识花记录数量
     */
    public ObservableField<Integer> userRecordNumberField = new ObservableField<>(0);
    /**
     * 设置点击事件
     */
    public BindingCommand settingCommand;

    /**
     * 用户头像点击事件
     */
    public BindingCommand userAvatarCommand;
    /**
     * 帖子点击事件
     */
    public BindingCommand userPostCommand;
    /**
     * 收藏点击事件
     */
    public BindingCommand userCollectionCommand;
    /**
     * 添加识花记录时间
     */
    public BindingCommand addRecordCommand;

    private UserBean mCurrentUser;
    /**
     * 一些通知事件的回调，类似EventBus
     */
    private Disposable mCommandDisposable;

    public KnowFlowerRecordAdapter mRecordAdapter;
    private DeleteIdentifyPopupWindow mPopupWindow;

    public MineViewModel(@NonNull Application application) {
        super(application, new MineModel());
        mRecordAdapter = new KnowFlowerRecordAdapter();
        mRecordAdapter.setOnItemClickListener((adapter, view, position) -> {
            IdentifyResultBean item = mRecordAdapter.getItem(position);
            if (item == null) {
                return;
            }
            ARouter.getInstance()
                    .build(ARouterPath.KNOW_FLOWER_RESULT_PATH)
                    .withString(KnowFlowerResultActivity.PICTURE_URL, item.getPicture() == null ? "" : item.getPicture().getFileUrl())
                    .withParcelableArrayList(KnowFlowerResultActivity.RESULT_LIST, new ArrayList<>(item.getResults()))
                    .navigation();
        });
        mRecordAdapter.setOnItemChildClickListener((adapter, view, position) -> {
            if (view.getId() == R.id.ivMore) {
                //显示 删除 PopupWindow
                if (mPopupWindow == null) {
                    mPopupWindow = new DeleteIdentifyPopupWindow(view.getContext());
                    mPopupWindow.setPopupGravity(Gravity.CENTER_HORIZONTAL | Gravity.TOP);
                    mPopupWindow.setOnDeleteListener(() -> deleteRecord(position));
                }
                mPopupWindow.linkTo(view);
                mPopupWindow.showPopupWindow(view);
            }
        });


        settingCommand = new BindingCommand(() -> {
            if (mCurrentUser == null) {
                toLoginActivity();
            } else {
                ARouter.getInstance()
                        .build(ARouterPath.SETTING_PATH)
                        .navigation();
            }
        });
        userAvatarCommand = new BindingCommand(() -> {
            if (mCurrentUser == null) {
                toLoginActivity();
            } else {

            }
        });
        userPostCommand = new BindingCommand(() -> {
            if (mCurrentUser == null) {
                toLoginActivity();
            } else {
                ARouter.getInstance()
                        .build(ARouterPath.MY_POST_PATH)
                        .navigation();
            }
        });
        userCollectionCommand = new BindingCommand(() -> {
            if (mCurrentUser == null) {
                toLoginActivity();
            } else {
                ARouter.getInstance()
                        .build(ARouterPath.COLLECTION_PATH)
                        .navigation();
            }
        });
        addRecordCommand = new BindingCommand(() -> {
            if (mCurrentUser == null) {
                toLoginActivity();
            } else {
                //跳转到识花页面
                ARouter.getInstance()
                        .build(ARouterPath.KNOW_FLOWER_PATH)
                        .navigation();
            }
        });
        mCommandDisposable = RxBus.getDefault()
                .register(CommandBean.class, RxUtil.io_main(), bean -> {
                    switch (bean.getCommand()) {
                        case CommandBean.COMMAND_USER_LOGIN_SUCCESS://用户登录成功
                        case CommandBean.COMMAND_USER_LOGIN_OUT://用户退出登录
                            updateCurrentUser();
                            break;
                    }
                });
        RxSubscriptions.add(mCommandDisposable);
    }

    @Override
    public void onCreate() {
        super.onCreate();
        updateCurrentUser();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        RxSubscriptions.remove(mCommandDisposable);
    }

    /**
     * 更新用户信息
     */
    private void updateCurrentUser() {
        mCurrentUser = BmobUser.getCurrentUser(UserBean.class);
        if (mCurrentUser == null) {
            userAvatarField.set(R.drawable.icon_profile_login_button);
            userSexField.set(0);
            userNameField.set("登录");
            userSignatureField.set("");
            userPostNumberField.set(0);
            userCollectionNumberField.set(0);
            userRecordNumberField.set(0);
            return;
        }
        userAvatarField.set(mCurrentUser.getHeadImg() == null ? R.drawable.icon_profile_default_portrait : mCurrentUser.getHeadImg().getFileUrl());
        userSexField.set(TextUtils.equals(mCurrentUser.getSex(), getApplication().getString(R.string.woman)) ? R.drawable.ic_user_woman : R.drawable.ic_user_man);
        userNameField.set(TextUtils.isEmpty(mCurrentUser.getNickName()) ? mCurrentUser.getUsername() : mCurrentUser.getNickName());
        userSignatureField.set(mCurrentUser.getSignature());
    }

    /**
     * 跳转到登录页面
     */
    private void toLoginActivity() {
        ARouter.getInstance()
                .build(ARouterPath.LOGIN_PATH)
                .navigation();
    }

    /**
     * 当Fragment可见时，更新数据
     */
    public void onFragmentVisible() {
        if (mCurrentUser == null) {
            return;
        }
        //帖子的数量
        mModel.queryCurrentUserAllPostCount(mCurrentUser, integer -> {
            if (integer == null) {
                userPostNumberField.set(0);
            } else {
                userPostNumberField.set(integer);
            }
        }, throwable -> {
            LogUtil.e("查询当前用户发表的帖子数量失败，" + throwable.toString());
            userPostNumberField.set(0);
        });
        //收藏数量
        mModel.queryCurrentUserAllCollectionCount(mCurrentUser, integer -> {
            if (integer == null) {
                userCollectionNumberField.set(0);
            } else {
                userCollectionNumberField.set(integer);
            }
        }, throwable -> {
            LogUtil.e("查询当前用户发表的收藏数量失败，" + throwable.toString());
            userCollectionNumberField.set(0);
        });
        //查询当前用户发表识花记录
        mModel.queryCurrentUserKnowFlowerRecordCount(mCurrentUser, list -> {
            if (list == null) {
                userRecordNumberField.set(0);
            } else {
                userRecordNumberField.set(list.size());
            }
            mRecordAdapter.setNewData(list);
        }, throwable -> {
            LogUtil.e("查询当前用户发表识花记录失败，" + throwable.toString());
            userRecordNumberField.set(0);
        });
    }

    /**
     * 删除某条识花记录
     *
     * @param position
     */
    private void deleteRecord(int position) {
        IdentifyResultBean item = mRecordAdapter.getItem(position);
        if (item == null) {
            return;
        }
        mModel.deleteIdentifyResult(item.getObjectId(), e -> {
            if (e == null || e.getErrorCode() == Constant.BMOB_RESULT_OK) {
                ToastUtil.success(getApplication(), "删除成功");
                mRecordAdapter.remove(position);
                //更新数据
                userRecordNumberField.set(mRecordAdapter.getData().size());
            } else {
                ToastUtil.error(getApplication(), "删除失败，" + e.toString());
                LogUtil.e("删除识花记录失败，" + e.toString());
            }
        });

    }
}
