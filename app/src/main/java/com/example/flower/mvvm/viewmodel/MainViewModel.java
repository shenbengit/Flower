package com.example.flower.mvvm.viewmodel;

import android.app.Application;

import androidx.annotation.NonNull;

import com.example.flower.base.BaseViewModel;
import com.example.flower.http.bmob.UserBean;
import com.example.flower.util.LogUtil;

import cn.bmob.v3.BmobUser;
import io.reactivex.Observer;
import io.reactivex.disposables.Disposable;

/**
 * @author ShenBen
 * @date 2019/9/19 22:50
 * @email 714081644@qq.com
 */
public class MainViewModel extends BaseViewModel {

    public MainViewModel(@NonNull Application application) {
        super(application);
    }

    @Override
    public void onCreate() {
        super.onCreate();
        UserBean currentUser = BmobUser.getCurrentUser(UserBean.class);
        if (currentUser != null) {
            currentUser.loginObservable(UserBean.class)
                    .compose(mLifecycleProvider.bindToLifecycle())
                    .subscribe(new Observer<UserBean>() {
                        @Override
                        public void onSubscribe(Disposable d) {

                        }

                        @Override
                        public void onNext(UserBean userBean) {
                            LogUtil.i("用户登录成功");
                        }

                        @Override
                        public void onError(Throwable e) {
                            LogUtil.i("用户登录失败，" + e.getMessage());
                        }

                        @Override
                        public void onComplete() {

                        }
                    });
        }
    }
}
