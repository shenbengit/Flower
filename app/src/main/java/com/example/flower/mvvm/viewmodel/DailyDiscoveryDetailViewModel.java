package com.example.flower.mvvm.viewmodel;

import android.app.Application;
import android.text.TextUtils;

import androidx.annotation.NonNull;
import androidx.databinding.ObservableField;
import androidx.lifecycle.MutableLiveData;

import com.example.flower.base.BaseViewModel;
import com.example.flower.mvvm.model.DailyDiscoveryDetailModel;

/**
 * @author ShenBen
 * @date 2019/10/20 14:23
 * @email 714081644@qq.com
 */
public class DailyDiscoveryDetailViewModel extends BaseViewModel<DailyDiscoveryDetailModel> {

    public ObservableField<String> imageUrl = new ObservableField<>();

    public MutableLiveData<String> pageUrl = new MutableLiveData<>();

    public DailyDiscoveryDetailViewModel(@NonNull Application application) {
        super(application, new DailyDiscoveryDetailModel());
    }

    public void getDailyDiscoveryDetail(String id) {
        if (TextUtils.isEmpty(id)) {
            return;
        }
        mModel.getDailyDiscoveryDetail(id, dailyDiscoveryDetailBean -> {
            if (dailyDiscoveryDetailBean.getData() != null) {
                imageUrl.set(dailyDiscoveryDetailBean.getData().getCoverImage());
                pageUrl.setValue(dailyDiscoveryDetailBean.getData().getPageUrl());
            }
        }, throwable -> {

        });
    }
}
