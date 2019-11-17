package com.example.flower.mvvm.model;

import com.example.flower.base.BaseModel;
import com.example.flower.http.RetrofitClient;
import com.example.flower.http.bean.WallpaperBean;
import com.example.flower.util.RxUtil;

import io.reactivex.Observer;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Consumer;

/**
 * @author ShenBen
 * @date 2019/11/17 16:41
 * @email 714081644@qq.com
 */
public class WallpaperModel extends BaseModel {

    public void getWallpaper(int index, Consumer<WallpaperBean> onNext, Consumer<Throwable> onError) {
        RetrofitClient.getInstance()
                .getApiService()
                .getWallpaper(index, "android", "3.0", "", "")
                .compose(RxUtil.io_main())
                .compose(mLifecycleProvider.bindToLifecycle())
                .subscribe(new Observer<WallpaperBean>() {
                    @Override
                    public void onSubscribe(Disposable d) {

                    }

                    @Override
                    public void onNext(WallpaperBean wallpaperBean) {
                        if (onNext != null) {
                            try {
                                onNext.accept(wallpaperBean);
                            } catch (Exception e) {
                                e.printStackTrace();
                            }
                        }
                    }

                    @Override
                    public void onError(Throwable e) {
                        if (onError != null) {
                            try {
                                onError.accept(e);
                            } catch (Exception ex) {
                                ex.printStackTrace();
                            }
                        }
                    }

                    @Override
                    public void onComplete() {

                    }
                });
    }
}
