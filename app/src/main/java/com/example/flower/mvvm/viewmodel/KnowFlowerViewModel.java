package com.example.flower.mvvm.viewmodel;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.databinding.ObservableBoolean;
import androidx.databinding.ObservableField;
import androidx.lifecycle.MutableLiveData;

import com.example.flower.base.BaseViewModel;
import com.example.flower.constant.Constant;
import com.example.flower.http.RetrofitClient;
import com.example.flower.http.bean.KnowFlowerResultBean;
import com.example.flower.util.LogUtil;
import com.example.flower.util.RxUtil;

import java.io.File;
import java.util.List;

import cn.bmob.v3.datatype.BmobFile;
import cn.bmob.v3.exception.BmobException;
import cn.bmob.v3.listener.UploadFileListener;
import io.reactivex.Observer;
import io.reactivex.disposables.Disposable;

/**
 * @author ShenBen
 * @date 2020/1/23 19:06
 * @email 714081644@qq.com
 */
public class KnowFlowerViewModel extends BaseViewModel {
    public ObservableField<File> photoLibraryPreviewFile = new ObservableField<>();
    public ObservableBoolean isPhotoLibraryPreviewShow = new ObservableBoolean(false);

    public MutableLiveData<List<KnowFlowerResultBean.ResponseBean.IdentifyResultsBean>> mIdentifyResultsLiveData = new MutableLiveData<>();

    public KnowFlowerViewModel(@NonNull Application application) {
        super(application);
    }

    /**
     * 上传文件
     *
     * @param file 本地文件
     */
    public void uploadFile(File file) {
        if (file == null || !file.exists()) {
            mIdentifyResultsLiveData.setValue(null);
            return;
        }
        BmobFile bmobFile = new BmobFile(file);
        bmobFile.uploadblock(new UploadFileListener() {
            @Override
            public void done(BmobException e) {
                if (e == null) {
                    //开始识别
                    LogUtil.i("上传图片成功：" + bmobFile.getFileUrl());
                    startDistinguish(bmobFile.getFileUrl());
                } else {
                    LogUtil.e("上传图片失败：" + e.toString());
                    mIdentifyResultsLiveData.setValue(null);
                }
            }
        });
    }

    /**
     * 开始识别
     *
     * @param url
     */
    private void startDistinguish(String url) {
        RetrofitClient.getInstance().getApiService()
                .knowFlower(Constant.XING_SE_API_URL, "APPCODE " + Constant.XING_SE_APP_CODE, url)
                .compose(RxUtil.io_main())
                .compose(mLifecycleProvider.bindToLifecycle())
                .subscribe(new Observer<KnowFlowerResultBean>() {
                    @Override
                    public void onSubscribe(Disposable d) {

                    }

                    @Override
                    public void onNext(KnowFlowerResultBean bean) {
                        List<KnowFlowerResultBean.ResponseBean.IdentifyResultsBean> results = null;
                        if (bean.getResult() == 1 && bean.getResponse() != null) {
                            results = bean.getResponse().getIdentifyResults();
                        }
                        mIdentifyResultsLiveData.setValue(results);
                    }

                    @Override
                    public void onError(Throwable e) {
                        mIdentifyResultsLiveData.setValue(null);
                        LogUtil.e("识别结果-出错：" + e.getMessage());
                    }

                    @Override
                    public void onComplete() {

                    }
                });
    }
}
