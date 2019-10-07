package com.example.flower.mvvm.model;

import com.example.flower.base.BaseModel;
import com.example.flower.constant.Constant;
import com.example.flower.http.RetrofitClient;
import com.example.flower.http.bean.TodayBean;
import com.example.flower.util.RxUtil;

import io.reactivex.Observer;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Consumer;

/**
 * @author ShenBen
 * @date 2019/10/7 11:06
 * @email 714081644@qq.com
 */
public class TodayModel extends BaseModel {
    public void getToday(Consumer<TodayBean> onNext, Consumer<Throwable> onError) {
        RetrofitClient.getInstance()
                .getApiService()
                .getToday(Constant.USER_ID)
                .compose(RxUtil.io_main())
                .compose(RxUtil.bindToLifecycle(mLifecycleProvider))
                .subscribe(new Observer<TodayBean>() {
                    @Override
                    public void onSubscribe(Disposable d) {

                    }

                    @Override
                    public void onNext(TodayBean homePageBean) {
                        if (onNext != null) {
                            try {
                                onNext.accept(homePageBean);
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
