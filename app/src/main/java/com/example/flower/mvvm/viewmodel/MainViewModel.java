package com.example.flower.mvvm.viewmodel;

import android.app.Application;

import androidx.annotation.NonNull;

import com.example.flower.base.BaseViewModel;
import com.example.flower.mvvm.model.MainModel;

/**
 * @author ShenBen
 * @date 2019/9/19 22:50
 * @email 714081644@qq.com
 */
public class MainViewModel extends BaseViewModel<MainModel> {

    public MainViewModel(@NonNull Application application) {
        super(application, new MainModel());
        initCommand();
    }

    private void initCommand() {

    }
}
