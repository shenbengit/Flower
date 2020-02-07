package com.example.flower.mvvm.view.activity;


import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.Nullable;

import com.alibaba.android.arouter.facade.annotation.Route;
import com.example.flower.BR;
import com.example.flower.GlideEngine;
import com.example.flower.R;
import com.example.flower.base.BaseActivity;
import com.example.flower.constant.ARouterPath;
import com.example.flower.constant.Constant;
import com.example.flower.databinding.ActivityPostPublishBinding;
import com.example.flower.mvvm.view.widget.EnlargePictureDialog;
import com.example.flower.mvvm.viewmodel.PublishPostViewModel;
import com.example.flower.rxbus.RxBus;
import com.example.flower.rxbus.bean.CommandBean;
import com.luck.picture.lib.PictureSelector;
import com.luck.picture.lib.config.PictureConfig;
import com.luck.picture.lib.config.PictureMimeType;
import com.luck.picture.lib.entity.LocalMedia;

import java.util.List;

/**
 * 发帖页面
 */
@Route(path = ARouterPath.PUBLISH_POST_PATH)
public class PublishPostActivity extends BaseActivity<ActivityPostPublishBinding, PublishPostViewModel> {

    private EnlargePictureDialog mEnlargePictureDialog;

    @Override
    protected int getLayoutId() {
        return R.layout.activity_post_publish;
    }

    @Override
    protected Class<PublishPostViewModel> getModelClass() {
        return PublishPostViewModel.class;
    }

    @Override
    protected int getVariableId() {
        return BR.viewModel;
    }

    @Override
    protected void initView() {
        super.initView();
        mBinding.toolbar.setTitle("发帖");
        initToolbar(mBinding.toolbar);
        mEnlargePictureDialog = new EnlargePictureDialog(this);
        mEnlargePictureDialog.isShowDeleteButton(true);
        mEnlargePictureDialog.setOnDeletePictureListener(position -> mViewModel.deletePicture(position));
    }

    @Override
    protected void initData(@Nullable Bundle savedInstanceState) {
        mViewModel.mShowEnlargePictureLiveData.observe(this, integer -> {
            if (integer != null) {
                mEnlargePictureDialog.setImageList(mViewModel.getHaveChosenPictureList(), integer);
                mEnlargePictureDialog.show();
            }
        });
    }

    @Override
    protected void baseLiveDataObserver(String str) {
        super.baseLiveDataObserver(str);
        switch (str) {
            case PublishPostViewModel.PUBLISH_POST_SUCCESS:
                RxBus.getDefault().postSticky(new CommandBean(CommandBean.COMMAND_PUBLISH_POST_SUCCESS));
                onBackPressedSupport();
                break;
            case PublishPostViewModel.CHOOSE_IMAGE:
                //相册
                PictureSelector.create(PublishPostActivity.this)
                        .openGallery(PictureMimeType.ofImage())
                        .compress(true)//压缩
                        .compressQuality(90)//压缩质量
                        .maxSelectNum(mViewModel.canChooseImageNumber())//可以选择图片的最大数量
                        .enableCrop(false)
                        .isWeChatStyle(true)
                        .isReturnEmpty(true)// 选择数据时点击按钮是否可以返回
                        .selectionMode(PictureConfig.MULTIPLE)//单选模式
                        .compressSavePath(Constant.COMPRESS_SAVE_PATH)
                        .synOrAsy(false)//同步true或异步false 压缩 默认同步
                        .loadImageEngine(GlideEngine.createGlideEngine()) // 请参考Demo GlideEngine.java
                        .forResult(PictureConfig.CHOOSE_REQUEST);
                break;
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == PictureConfig.CHOOSE_REQUEST) {
            if (resultCode == RESULT_OK) {
                List<LocalMedia> list = PictureSelector.obtainMultipleResult(data);
                mViewModel.addPictureFromPhotoLibrary(list);
            }
        }
    }

}
