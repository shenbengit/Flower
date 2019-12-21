package com.example.flower.mvvm.view.activity;

import android.os.Bundle;

import androidx.annotation.Nullable;

import com.alibaba.android.arouter.facade.annotation.Route;
import com.example.flower.GlideApp;
import com.example.flower.R;
import com.example.flower.base.BaseActivity;
import com.example.flower.base.BaseViewModel;
import com.example.flower.constant.ARouterPath;
import com.example.flower.constant.Constant;
import com.example.flower.databinding.ActivityWallpaperDetailBinding;
import com.example.flower.http.RetrofitClient;
import com.example.flower.util.RxUtil;
import com.example.flower.util.ToastUtil;
import com.gyf.immersionbar.ImmersionBar;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;

import io.reactivex.Observer;
import io.reactivex.disposables.Disposable;
import okhttp3.ResponseBody;

/**
 * 壁纸详情页
 */
@Route(path = ARouterPath.WALLPAPER_DETAIL_PATH)
public class WallpaperDetailActivity extends BaseActivity<ActivityWallpaperDetailBinding, BaseViewModel> {

    public static final String DETAIL_URL = "DETAIL_URL";

    @Override
    protected int getLayoutId() {
        return R.layout.activity_wallpaper_detail;
    }

    @Override
    protected Class<BaseViewModel> getModelClass() {
        return BaseViewModel.class;
    }

    @Override
    protected int getVariableId() {
        return 0;
    }

    @Override
    protected void initView() {
        super.initView();
        ImmersionBar.with(this)
                .transparentBar()
                .init();
        mBinding.ibBack.setOnClickListener(v -> onBackPressedSupport());

        String url = getIntent().getStringExtra(DETAIL_URL);
        mBinding.ibDownload.setOnClickListener(v -> {
                    RetrofitClient.getInstance()
                            .getApiService()
                            .downloadFile(url)
                            .compose(RxUtil.io_io())
                            .subscribe(new Observer<ResponseBody>() {
                                @Override
                                public void onSubscribe(Disposable d) {

                                }

                                @Override
                                public void onNext(ResponseBody responseBody) {
                                    //文件名，当前系统时间戳
                                    String fileName = System.currentTimeMillis() + ".jpg";
                                    File file = new File(Constant.WALLPAPER_SAVE_PATH, fileName);
                                    File parentFile = file.getParentFile();
                                    //父文件不存在，则先创建文件夹
                                    if (parentFile != null && !parentFile.exists()) {
                                        parentFile.mkdirs();
                                    }
                                    //文件存在则先删除
                                    if (file.exists()) {
                                        file.delete();
                                    }
                                    try {
                                        FileOutputStream outputStream = new FileOutputStream(file);
                                        byte[] bytes = new byte[4096];
                                        InputStream inputStream = responseBody.byteStream();
                                        int readLength;
                                        while ((readLength = inputStream.read(bytes)) != -1) {
                                            outputStream.write(bytes, 0, readLength);
                                        }
                                        runOnUiThread(() -> ToastUtil.show(WallpaperDetailActivity.this, "照片保存在" + file.getAbsolutePath()));
                                    } catch (IOException e) {
                                        e.printStackTrace();
                                    }

                                }

                                @Override
                                public void onError(Throwable e) {
                                    runOnUiThread(() -> ToastUtil.show(WallpaperDetailActivity.this, "文件下载失败，" + e.getMessage()));
                                }

                                @Override
                                public void onComplete() {
                                }
                            });
                }
        );
    }

    @Override
    protected void initData(@Nullable Bundle savedInstanceState) {
        String url = getIntent().getStringExtra(DETAIL_URL);
        GlideApp.with(mBinding.ivDetail)
                .load(url)
                .error(R.drawable.grey_bg)
                .placeholder(R.drawable.grey_bg)
                .into(mBinding.ivDetail);
    }
}
