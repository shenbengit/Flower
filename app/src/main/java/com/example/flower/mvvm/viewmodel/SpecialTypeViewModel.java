package com.example.flower.mvvm.viewmodel;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.MutableLiveData;

import com.example.flower.base.BaseViewModel;
import com.example.flower.http.bean.SpecialTypeBean;
import com.example.flower.mvvm.model.SpecialListModel;

/**
 * @author ShenBen
 * @date 2019/10/7 14:46
 * @email 714081644@qq.com
 */
public class SpecialTypeViewModel extends BaseViewModel<SpecialListModel> {
    public final MutableLiveData<SpecialTypeBean> mSpecialTypeData = new MutableLiveData<>();

    public SpecialTypeViewModel(@NonNull Application application) {
        super(application, new SpecialListModel());
    }

    public void getSpecialTypeList(String id) {
        mModel.getSpecialTypeList(id, mSpecialTypeData::setValue, null);
    }
}
