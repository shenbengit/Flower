package com.example.flower.mvvm.view.activity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import com.example.flower.BR;

import com.alibaba.android.arouter.facade.annotation.Route;
import com.alibaba.android.arouter.launcher.ARouter;
import com.example.flower.GlideEngine;
import com.example.flower.R;
import com.example.flower.base.BaseActivity;
import com.example.flower.constant.ARouterPath;
import com.example.flower.constant.Constant;
import com.example.flower.databinding.ActivityKnowFlowerBinding;
import com.example.flower.mvvm.view.widget.KnowFlowerPopupWindow;
import com.example.flower.mvvm.viewmodel.KnowFlowerViewModel;
import com.example.flower.util.FileUtil;
import com.example.flower.util.LogUtil;
import com.example.flower.util.ThreadUtil;
import com.jakewharton.rxbinding3.view.RxView;
import com.luck.picture.lib.PictureSelector;
import com.luck.picture.lib.config.PictureConfig;
import com.luck.picture.lib.config.PictureMimeType;
import com.luck.picture.lib.entity.LocalMedia;
import com.otaliastudios.cameraview.CameraException;
import com.otaliastudios.cameraview.CameraListener;
import com.otaliastudios.cameraview.PictureResult;

import java.io.File;
import java.io.IOException;
import java.util.List;
import java.util.concurrent.TimeUnit;

import okio.Okio;
import razerdp.basepopup.BasePopupWindow;
import top.zibin.luban.Luban;
import top.zibin.luban.OnCompressListener;

@Route(path = ARouterPath.KNOW_FLOWER_PATH)
public class KnowFlowerActivity extends BaseActivity<ActivityKnowFlowerBinding, KnowFlowerViewModel> {

    private KnowFlowerPopupWindow mPopupWindow;
    private boolean flag = false;

    @Override
    protected int getLayoutId() {
        return R.layout.activity_know_flower;
    }

    @Override
    protected Class<KnowFlowerViewModel> getModelClass() {
        return KnowFlowerViewModel.class;
    }

    @Override
    protected int getVariableId() {
        return BR.viewModel;
    }

    @SuppressLint("CheckResult")
    @Override
    protected void initView() {
        super.initView();
        mBinding.ibBack.setOnClickListener(v -> onBackPressedSupport());
        mPopupWindow = new KnowFlowerPopupWindow(this);
        mPopupWindow.setOnCheckItemListener(bean -> ARouter.getInstance()
                .build(ARouterPath.KNOW_FLOWER_DETAIL_PATH)
                .withString(KnowFlowerDetailActivity.FLOWER_DETAIL_URL, bean.getDetailUrl())
                .navigation());
        mPopupWindow.setOnDismissListener(new BasePopupWindow.OnDismissListener() {
            @Override
            public void onDismiss() {
                flag = false;
                mViewModel.isPhotoLibraryPreviewShow.set(false);
                mBinding.cameraView.open();

            }
        });
        mBinding.cameraView.addCameraListener(new CameraListener() {

            @Override
            public void onCameraError(@NonNull CameraException exception) {
                super.onCameraError(exception);

            }

            @Override
            public void onPictureTaken(@NonNull PictureResult result) {
                flag = true;
                mBinding.cameraView.close();
                mLoadingDialog.show();
                mPopupWindow.showPopupWindow();

                ThreadUtil.getFile().execute(() -> {
                    try {
                        //拍照保存的原始目录文件
                        File file = new File(Constant.PHOTO_SAVE_PATH + System.currentTimeMillis() + ".jpeg");
                        //先写入文件
                        Okio.buffer(Okio.sink(file))
                                .write(result.getData())
                                .close();
                        //压缩图片
                        Luban.with(KnowFlowerActivity.this)
                                .load(file)
                                .ignoreBy(300)
                                .setTargetDir(Constant.COMPRESS_SAVE_PATH)
                                .setCompressListener(new OnCompressListener() {
                                    @Override
                                    public void onStart() {

                                    }

                                    @Override
                                    public void onSuccess(File file) {
                                        mViewModel.uploadFile(file);
                                    }

                                    @Override
                                    public void onError(Throwable e) {
                                        runOnUiThread(() -> mLoadingDialog.dismiss());
                                    }
                                })
                                .launch();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                });
            }
        });
        //相册点击事件
        RxView.clicks(mBinding.ibCameraPhotoLibrary)
                .throttleFirst(1, TimeUnit.SECONDS)
                .subscribe(unit -> {
                    //相册
                    PictureSelector.create(KnowFlowerActivity.this)
                            .openGallery(PictureMimeType.ofImage())
                            .compress(true)//压缩
                            .compressQuality(90)//压缩质量
                            .enableCrop(false)
                            .isWeChatStyle(true)
                            .isReturnEmpty(true)// 选择数据时点击按钮是否可以返回
                            .selectionMode(PictureConfig.SINGLE)//单选模式
                            .isSingleDirectReturn(true)// 单选模式下是否直接返回，PictureConfig.SINGLE模式下有效
                            .compressSavePath(Constant.COMPRESS_SAVE_PATH)
                            .synOrAsy(false)//同步true或异步false 压缩 默认同步
                            .loadImageEngine(GlideEngine.createGlideEngine()) // 请参考Demo GlideEngine.java
                            .forResult(PictureConfig.CHOOSE_REQUEST);
                });
        //拍照点击事件
        RxView.clicks(mBinding.ibTakePhoto)
                .throttleFirst(1, TimeUnit.SECONDS)
                .subscribe(unit -> mBinding.cameraView.takePicture());
        //切换摄像头事件
        RxView.clicks(mBinding.ibToggleCamera)
                .throttleFirst(1, TimeUnit.SECONDS)
                .subscribe(unit -> mBinding.cameraView.toggleFacing());
    }

    @Override
    protected void initData(@Nullable Bundle savedInstanceState) {
        ThreadUtil.getFile().execute(() -> {
            //先删除，照片保存的目录的文件，避免占用过多资源
            FileUtil.deleteDirectory(Constant.PHOTO_SAVE_PATH);
        });
        mViewModel.mIdentifyResultsLiveData.observe(this, list -> {
            mLoadingDialog.dismiss();
            if (list == null || list.isEmpty()) {
                mPopupWindow.noData();
            } else {
                mPopupWindow.setResultData(list);
            }
        });

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == PictureConfig.CHOOSE_REQUEST && resultCode == RESULT_OK) {
            flag = true;
            List<LocalMedia> list = PictureSelector.obtainMultipleResult(data);
            if (list.isEmpty()) {
                return;
            }
            if (list.size() != 1) {
                LogUtil.w("选择的图片大小超过1，出错！");
                return;
            }
            mLoadingDialog.show();
            mPopupWindow.showPopupWindow();

            LocalMedia media = list.get(0);
            String path;
            if (media.isCompressed()) {
                path = media.getCompressPath();
            } else {
                path = media.getPath();
            }
            File file = new File(path);
            mViewModel.isPhotoLibraryPreviewShow.set(true);
            mViewModel.photoLibraryPreviewFile.set(file);
            mViewModel.uploadFile(file);
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        if (!flag) {
            mBinding.cameraView.open();
        }
    }

    @Override
    protected void onPause() {
        super.onPause();
        mBinding.cameraView.close();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        mBinding.cameraView.destroy();
    }
}
