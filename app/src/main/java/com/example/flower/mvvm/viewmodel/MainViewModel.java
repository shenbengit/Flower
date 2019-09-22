package com.example.flower.mvvm.viewmodel;

import android.app.Application;
import android.widget.RadioButton;

import androidx.annotation.NonNull;

import com.example.flower.base.BaseModel;
import com.example.flower.base.BaseViewModel;
import com.example.flower.binding.command.BindingCommand;

/**
 * @author ShenBen
 * @date 2019/9/19 22:50
 * @email 714081644@qq.com
 */
public class MainViewModel extends BaseViewModel<BaseModel> {
    public BindingCommand<RadioButton> radioGroupChecked;

    public MainViewModel(@NonNull Application application) {
        super(application, new BaseModel());
        initCommand();
    }

    private void initCommand() {

    }
}
