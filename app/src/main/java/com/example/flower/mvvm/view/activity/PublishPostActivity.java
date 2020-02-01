package com.example.flower.mvvm.view.activity;


import android.content.Intent;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.provider.MediaStore;
import android.text.TextUtils;

import androidx.annotation.Nullable;
import androidx.core.content.FileProvider;
import androidx.databinding.library.baseAdapters.BR;

import com.alibaba.android.arouter.facade.annotation.Route;
import com.example.flower.BuildConfig;
import com.example.flower.GlideEngine;
import com.example.flower.R;
import com.example.flower.base.BaseActivity;
import com.example.flower.constant.ARouterPath;
import com.example.flower.constant.Constant;
import com.example.flower.databinding.ActivityPostPublishBinding;
import com.example.flower.mvvm.view.widget.ChooseImageDialog;
import com.example.flower.mvvm.view.widget.EnlargePictureDialog;
import com.example.flower.mvvm.viewmodel.PublishPostViewModel;
import com.example.flower.util.LogUtil;
import com.luck.picture.lib.PictureSelector;
import com.luck.picture.lib.config.PictureConfig;
import com.luck.picture.lib.config.PictureMimeType;
import com.luck.picture.lib.entity.LocalMedia;

import java.io.File;
import java.util.List;

import top.zibin.luban.Luban;
import top.zibin.luban.OnCompressListener;

/**
 * 发帖页面
 */
@Route(path = ARouterPath.PUBLISH_POST_PATH)
public class PublishPostActivity extends BaseActivity<ActivityPostPublishBinding, PublishPostViewModel> {

    private static final int CAMERA_REQUEST_CODE = 101;
    /**
     * 拍照返回的Uri地址
     */
    private Uri uri;
    /**
     * 拍照保存的文件地址
     */
    private File file;

    private ChooseImageDialog mChooseImageDialog;
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
        mChooseImageDialog = new ChooseImageDialog(this);
        mChooseImageDialog.setOnChooseImageListener(new ChooseImageDialog.OnChooseImageListener() {
            @Override
            public void fromCamera() {
                file = new File(Constant.PHOTO_SAVE_PATH, System.currentTimeMillis() + ".jpeg");

                //跳转至相机
                Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                //兼容android7.0版本
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
                    uri = FileProvider.getUriForFile(PublishPostActivity.this, BuildConfig.APPLICATION_ID + ".provider", file);
                    intent.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);
                } else {
                    uri = Uri.fromFile(file);
                }
                //MediaStore.EXTRA_OUTPUT参数不设置时,系统会自动生成一个uri,但是只会返回一个缩略图
                intent.putExtra(MediaStore.EXTRA_OUTPUT, uri);
                startActivityForResult(intent, CAMERA_REQUEST_CODE);
            }

            @Override
            public void fromPhotoLibrary() {
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
            }
        });
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
        if (TextUtils.equals(str, PublishPostViewModel.CHOOSE_IMAGE)) {
            mChooseImageDialog.show();
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        switch (requestCode) {
            case CAMERA_REQUEST_CODE:
                //相机拍照返回
                if (resultCode == RESULT_OK) {
                    if (uri != null && file != null) {
                        //压缩拍照的文件
                        Luban.with(this)
                                .load(file)
                                .ignoreBy(300)
                                .setTargetDir(Constant.COMPRESS_SAVE_PATH)
                                .setCompressListener(new OnCompressListener() {
                                    @Override
                                    public void onStart() {
                                        mLoadingDialog.show();
                                    }

                                    @Override
                                    public void onSuccess(File file) {
                                        mLoadingDialog.dismiss();
                                        mViewModel.addPictureFromCamera(file);

                                    }

                                    @Override
                                    public void onError(Throwable e) {
                                        LogUtil.e("发帖-拍照文件压缩失败：" + e.toString());
                                        mLoadingDialog.dismiss();
                                    }
                                })
                                .launch();

                    }
                }
                break;
            case PictureConfig.CHOOSE_REQUEST:
                //相册选取返回
                if (resultCode == RESULT_OK) {
                    List<LocalMedia> list = PictureSelector.obtainMultipleResult(data);
                    mViewModel.addPictureFromPhotoLibrary(list);
                }
                break;
            default:
                break;
        }
    }

}
