package com.example.flower.mvvm.view.activity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;

import androidx.annotation.Nullable;

import com.alibaba.android.arouter.facade.annotation.Route;
import com.bigkoo.pickerview.builder.OptionsPickerBuilder;
import com.bigkoo.pickerview.view.OptionsPickerView;
import com.example.flower.GlideApp;
import com.example.flower.GlideEngine;
import com.example.flower.R;
import com.example.flower.base.BaseActivity;
import com.example.flower.base.BaseViewModel;
import com.example.flower.constant.ARouterPath;
import com.example.flower.constant.Constant;
import com.example.flower.databinding.ActivityUserInfoBinding;
import com.example.flower.http.bmob.UserBean;
import com.example.flower.mvvm.view.widget.InputDialog;
import com.example.flower.rxbus.RxBus;
import com.example.flower.rxbus.bean.CommandBean;
import com.example.flower.util.ToastUtil;
import com.jakewharton.rxbinding3.view.RxView;
import com.luck.picture.lib.PictureSelector;
import com.luck.picture.lib.config.PictureConfig;
import com.luck.picture.lib.config.PictureMimeType;
import com.luck.picture.lib.entity.LocalMedia;

import java.io.File;
import java.util.Arrays;
import java.util.List;
import java.util.concurrent.TimeUnit;

import cn.bmob.v3.BmobUser;
import cn.bmob.v3.datatype.BmobFile;
import cn.bmob.v3.exception.BmobException;
import cn.bmob.v3.listener.UpdateListener;
import cn.bmob.v3.listener.UploadFileListener;

/**
 * 用户信息页面
 */
@Route(path = ARouterPath.USER_INFO_PATH)
public class UserInfoActivity extends BaseActivity<ActivityUserInfoBinding, BaseViewModel> {
    public static final String TYPE_NICK = "TYPE_NICK";
    public static final String TYPE_SIGNATURE = "TYPE_SIGNATURE";
    /**
     * 当前用户信息
     */
    private UserBean mCurrentUser;
    private boolean isNeedUpdate = false;
    private InputDialog mInputDialog;
    private String mCheckType;

    private final List<String> mSexList = Arrays.asList("男", "女");
    private OptionsPickerView mSexPickerView;

    @Override
    protected int getLayoutId() {
        return R.layout.activity_user_info;
    }

    @Override
    protected Class<BaseViewModel> getModelClass() {
        return BaseViewModel.class;
    }

    @Override
    protected int getVariableId() {
        return 0;
    }

    @SuppressLint("CheckResult")
    @Override
    protected void initView() {
        super.initView();
        mBinding.toolbar.setTitle("个人中心");
        initToolbar(mBinding.toolbar);
        mInputDialog = new InputDialog(this);
        mInputDialog.setContentCallback(content -> {
            if (TextUtils.equals(mCheckType, TYPE_NICK)) {
                updateNickName(content);
                updateUserNickName(content);
            } else if (TextUtils.equals(mCheckType, TYPE_SIGNATURE)) {
                updateSignature(content);
                updateUserSignature(content);
            }
        });

        mSexPickerView = new OptionsPickerBuilder(this, (options1, options2, options3, v) -> {
            String sex = mSexList.get(options1);
            updateSex(sex);
            updateUserSex(sex);
        }).build();
        mSexPickerView.setPicker(mSexList);

        //点击事件
        RxView.clicks(mBinding.viewAvatarBg)
                .throttleFirst(1, TimeUnit.SECONDS)
                .subscribe(unit -> {
                    //相册
                    PictureSelector.create(UserInfoActivity.this)
                            .openGallery(PictureMimeType.ofImage())
                            .isWithVideoImage(false)
                            .compress(true)//压缩
                            .compressQuality(90)//压缩质量
                            .enableCrop(true)
                            .withAspectRatio(1, 1)
                            .isWeChatStyle(true)
                            .isReturnEmpty(true)// 选择数据时点击按钮是否可以返回
                            .selectionMode(PictureConfig.SINGLE)//单选模式
                            .isSingleDirectReturn(true)// 单选模式下是否直接返回，PictureConfig.SINGLE模式下有效
                            .compressSavePath(Constant.COMPRESS_SAVE_PATH)
                            .synOrAsy(false)//同步true或异步false 压缩 默认同步
                            .loadImageEngine(GlideEngine.createGlideEngine()) // 请参考Demo GlideEngine.java
                            .forResult(PictureConfig.CHOOSE_REQUEST);
                });
        RxView.clicks(mBinding.viewNickName)
                .throttleFirst(1, TimeUnit.SECONDS)
                .subscribe(unit -> {
                    mCheckType = TYPE_NICK;
                    mInputDialog.setTitle("请输入昵称");
                    mInputDialog.setContent(mBinding.tvNickName.getText(), "");
                    mInputDialog.show();
                });
        RxView.clicks(mBinding.viewSex)
                .throttleFirst(1, TimeUnit.SECONDS)
                .subscribe(unit -> mSexPickerView.show());
        RxView.clicks(mBinding.viewSignature)
                .throttleFirst(1, TimeUnit.SECONDS)
                .subscribe(unit -> {
                    mCheckType = TYPE_SIGNATURE;
                    mInputDialog.setTitle("请输入个性签名");
                    mInputDialog.setContent(mBinding.tvSignature.getText(), "");
                    mInputDialog.show();
                });
    }

    @Override
    protected void initData(@Nullable Bundle savedInstanceState) {
        mCurrentUser = BmobUser.getCurrentUser(UserBean.class);
        if (mCurrentUser != null) {
            if (mCurrentUser.getHeadImg() != null) {
                updateAvatar(mCurrentUser.getHeadImg().getFileUrl());
            } else {
                updateAvatar(R.drawable.icon_profile_default_portrait);
            }
            updateNickName(mCurrentUser.getTrueNickName());
            updateSex(mCurrentUser.getSex());
            updateSignature(mCurrentUser.getTrueSignature());
            for (int i = 0; i < mSexList.size(); i++) {
                if (TextUtils.equals(mCurrentUser.getSex(), mSexList.get(i))) {
                    mSexPickerView.setSelectOptions(i);
                    break;
                }
            }
        } else {
            ToastUtil.warning(getApplication(), "个人信息出错啦！");
            onBackPressedSupport();
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == PictureConfig.CHOOSE_REQUEST) {
            if (resultCode == RESULT_OK) {
                List<LocalMedia> list = PictureSelector.obtainMultipleResult(data);
                if (list.size() == 1) {
                    LocalMedia media = list.get(0);
                    String path;
                    if (media.isCut()) {
                        path = media.getCutPath();
                    } else if (media.isCompressed()) {
                        path = media.getCompressPath();
                    } else {
                        path = media.getPath();
                    }
                    File file = new File(path);
                    if (file.exists()) {
                        updateAvatar(file);
                        uploadFile(file);
                    }
                }
            }
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (isNeedUpdate) {
            //通知更新
            RxBus.getDefault().postSticky(new CommandBean(CommandBean.COMMAND_USER_UPDATE_INFO));
        }
    }

    /**
     * 更新头像
     *
     * @param object
     */
    private void updateAvatar(Object object) {
        GlideApp.with(mBinding.civAvatar)
                .load(object)
                .error(R.drawable.icon_profile_default_portrait)
                .placeholder(R.drawable.icon_profile_default_portrait)
                .into(mBinding.civAvatar);
    }

    /**
     * 更新昵称
     *
     * @param name
     */
    private void updateNickName(String name) {
        mBinding.tvNickName.setText(name);
    }

    /**
     * 更新性别
     *
     * @param sex
     */
    private void updateSex(String sex) {
        mBinding.tvSex.setText(sex);
    }

    /**
     * 更新个性签名
     *
     * @param signature
     */
    private void updateSignature(String signature) {
        mBinding.tvSignature.setText(signature);
    }

    /**
     * 上传图片
     *
     * @param file
     */
    private void uploadFile(File file) {
        mLoadingDialog.show();
        BmobFile bmobFile = new BmobFile(file);
        bmobFile.uploadblock(new UploadFileListener() {
            @Override
            public void done(BmobException e) {
                mLoadingDialog.dismiss();
                if (e == null) {
                    updateUserAvatar(bmobFile);
                } else {
                    ToastUtil.success(UserInfoActivity.this.getApplication(), "头像修改失败，" + e.toString());
                }
            }
        });
    }

    private void updateUserAvatar(BmobFile file) {
        UserBean bean = new UserBean();
        bean.setHeadImg(file);
        bean.update(mCurrentUser.getObjectId(), new UpdateListener() {
            @Override
            public void done(BmobException e) {
                if (e == null) {
                    isNeedUpdate = true;
                    ToastUtil.success(UserInfoActivity.this.getApplication(), "头像修改成功！");
                } else {
                    ToastUtil.error(UserInfoActivity.this.getApplication(), "头像修改失败，" + e.toString());
                }
            }
        });
    }

    private void updateUserNickName(String nick) {
        UserBean bean = new UserBean();
        bean.setNickName(nick);
        bean.update(mCurrentUser.getObjectId(), new UpdateListener() {
            @Override
            public void done(BmobException e) {
                if (e == null) {
                    isNeedUpdate = true;
                    ToastUtil.success(UserInfoActivity.this.getApplication(), "昵称修改成功！");
                } else {
                    ToastUtil.error(UserInfoActivity.this.getApplication(), "昵称修改失败，" + e.toString());
                }
            }
        });
    }

    private void updateUserSex(String sex) {
        UserBean bean = new UserBean();
        bean.setSex(sex);
        bean.update(mCurrentUser.getObjectId(), new UpdateListener() {
            @Override
            public void done(BmobException e) {
                if (e == null) {
                    isNeedUpdate = true;
                    ToastUtil.success(UserInfoActivity.this.getApplication(), "性别修改成功！");
                } else {
                    ToastUtil.error(UserInfoActivity.this.getApplication(), "性别修改失败，" + e.toString());
                }
            }
        });
    }

    private void updateUserSignature(String signature) {
        UserBean bean = new UserBean();
        bean.setSignature(signature);
        bean.update(mCurrentUser.getObjectId(), new UpdateListener() {
            @Override
            public void done(BmobException e) {
                if (e == null) {
                    isNeedUpdate = true;
                    ToastUtil.success(UserInfoActivity.this.getApplication(), "签名修改成功！");
                } else {
                    ToastUtil.error(UserInfoActivity.this.getApplication(), "签名修改失败，" + e.toString());
                }
            }
        });
    }
}
