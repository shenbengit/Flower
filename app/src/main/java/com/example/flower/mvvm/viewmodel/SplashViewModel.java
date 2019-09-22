package com.example.flower.mvvm.viewmodel;

import android.app.Application;

import androidx.annotation.NonNull;

import com.example.flower.base.BaseViewModel;
import com.example.flower.http.bean.CoverPageBean;
import com.example.flower.http.bean.NewestVideoRemindBean;
import com.example.flower.http.bean.PaidArticleEveryDayNewsBean;
import com.example.flower.http.bean.WallpaperVersionBean;
import com.example.flower.mvvm.model.SplashModel;
import com.example.flower.mvvm.view.activity.SplashActivity;
import com.example.flower.rxbus.RxSubscriptions;
import com.example.flower.util.LogUtil;

import java.util.concurrent.TimeUnit;

import io.reactivex.Observable;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Consumer;

/**
 * @author ShenBen
 * @date 2019/9/21 23:50
 * @email 714081644@qq.com
 */
public class SplashViewModel extends BaseViewModel<SplashModel> {

    private Disposable mDisposable;

    public SplashViewModel(@NonNull Application application) {
        super(application, new SplashModel());
    }

    @Override
    public void onCreate() {
        super.onCreate();
        mModel.getCoverPage(mLifecycleProvider, new Consumer<CoverPageBean>() {
            @Override
            public void accept(CoverPageBean coverPageBean) throws Exception {

            }
        }, throwable -> LogUtil.e("getCoverPage-onError: " + throwable.getMessage()));

        mModel.getWallpaperVersion(mLifecycleProvider, new Consumer<WallpaperVersionBean>() {
            @Override
            public void accept(WallpaperVersionBean coverPageBean) throws Exception {

            }
        }, throwable -> LogUtil.e("getWallpaperVersion-onError: " + throwable.getMessage()));

        mModel.getPaidArticleEveryDayNews(mLifecycleProvider, new Consumer<PaidArticleEveryDayNewsBean>() {
            @Override
            public void accept(PaidArticleEveryDayNewsBean coverPageBean) throws Exception {

            }
        }, throwable -> LogUtil.e("getPaidArticleEveryDayNews-onError: " + throwable.getMessage()));

        mModel.getNewestVideoRemind(mLifecycleProvider, new Consumer<NewestVideoRemindBean>() {
            @Override
            public void accept(NewestVideoRemindBean coverPageBean) throws Exception {

            }
        }, throwable -> LogUtil.e("getNewestVideoRemind-onError: " + throwable.getMessage()));

        //3秒之后跳转到主页
        mDisposable = Observable.timer(3, TimeUnit.SECONDS)
                .subscribe(aLong -> mBaseLiveData.postValue(SplashActivity.TO_MAIN));
        RxSubscriptions.add(mDisposable);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        RxSubscriptions.remove(mDisposable);
    }
}
