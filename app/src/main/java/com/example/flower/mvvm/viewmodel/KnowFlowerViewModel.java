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
import com.example.flower.http.bmob.IdentifyResultBean;
import com.example.flower.http.bmob.UserBean;
import com.example.flower.util.LogUtil;
import com.example.flower.util.RxUtil;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import cn.bmob.v3.BmobUser;
import cn.bmob.v3.datatype.BmobFile;
import cn.bmob.v3.exception.BmobException;
import cn.bmob.v3.listener.SaveListener;
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
                    startDistinguish(bmobFile);
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
     * @param file
     */
    private void startDistinguish(BmobFile file) {
        RetrofitClient.getInstance().getApiService()
                .knowFlower(Constant.XING_SE_API_URL, "APPCODE " + Constant.XING_SE_APP_CODE, file.getFileUrl())
                .compose(RxUtil.io_main())
                .compose(mLifecycleProvider.bindToLifecycle())
                .subscribe(new Observer<KnowFlowerResultBean>() {
                    @Override
                    public void onSubscribe(Disposable d) {

                    }

                    @Override
                    public void onNext(KnowFlowerResultBean bean) {
                        List<KnowFlowerResultBean.ResponseBean.IdentifyResultsBean> results = new ArrayList<>();
                        if (bean.getResult() == 1 && bean.getResponse() != null) {
                            results.addAll(bean.getResponse().getIdentifyResults());
                        }
                        mIdentifyResultsLiveData.setValue(results);

                        UserBean currentUser = BmobUser.getCurrentUser(UserBean.class);
                        //如果当前用户已经登录，则保存用户的识花结果
                        if (currentUser != null) {
                            IdentifyResultBean resultBean = new IdentifyResultBean();
                            resultBean.setUser(currentUser);
                            resultBean.setResults(results);
                            resultBean.setPicture(file);
                            resultBean.save(new SaveListener<String>() {
                                @Override
                                public void done(String objectId, BmobException e) {
                                    if (e == null) {
                                        LogUtil.i("当前用户保存识花结果成功，objectId: " + objectId);
                                    } else {
                                        LogUtil.e("当前用户保存识花结果失败，error：" + e.toString());
                                    }
                                }
                            });
                        }
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
