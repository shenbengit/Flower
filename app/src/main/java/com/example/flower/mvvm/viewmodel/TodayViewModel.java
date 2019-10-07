package com.example.flower.mvvm.viewmodel;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.databinding.ObservableField;

import com.example.flower.base.BaseViewModel;
import com.example.flower.binding.command.BindingCommand;
import com.example.flower.mvvm.model.TodayModel;
import com.example.flower.util.LogUtil;

/**
 * @author ShenBen
 * @date 2019/10/7 11:06
 * @email 714081644@qq.com
 */
public class TodayViewModel extends BaseViewModel<TodayModel> {

    public static final String ACTIVITY_CLOSE = "ACTIVITY_CLOSE";

    public ObservableField<String> blurImageUrl = new ObservableField<>();
    public ObservableField<String> imageUrl = new ObservableField<>();

    public BindingCommand closeCommand;

    public TodayViewModel(@NonNull Application application) {
        super(application, new TodayModel());
    }

    @Override
    public void onCreate() {
        super.onCreate();
        closeCommand = new BindingCommand(() -> mBaseLiveData.setValue(ACTIVITY_CLOSE));
        mModel.getToday(todayBean -> {
            blurImageUrl.set(todayBean.getData().getCoverImage());
            imageUrl.set(todayBean.getData().getShareImage());
        }, throwable -> LogUtil.i("今日获取错误，onError: " + throwable.getMessage()));
    }
}
