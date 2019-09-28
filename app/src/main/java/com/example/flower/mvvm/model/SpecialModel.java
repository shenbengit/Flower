package com.example.flower.mvvm.model;

import com.example.flower.base.BaseModel;
import com.example.flower.http.RetrofitClient;
import com.example.flower.http.bean.HomePageBean;
import com.example.flower.http.bean.RecommendedTodayBean;
import com.example.flower.util.RxUtil;

import io.reactivex.Observer;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Consumer;

/**
 * @author ShenBen
 * @date 2019/9/22 22:28
 * @email 714081644@qq.com
 */
public class SpecialModel extends BaseModel {

    public void getHomePageForNewVersion(String city, Consumer<HomePageBean> onNext, Consumer<Throwable> onError) {
        RetrofitClient.getInstance()
                .getApiService()
                .getHomePageForNewVersion(city)
                .compose(RxUtil.io_main())
                .compose(RxUtil.bindToLifecycle(mLifecycleProvider))
                .subscribe(new Observer<HomePageBean>() {
                    @Override
                    public void onSubscribe(Disposable d) {

                    }

                    @Override
                    public void onNext(HomePageBean homePageBean) {
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


    public void getRecommendedToday(int pageIndex, String customerId, String token, Consumer<RecommendedTodayBean> onNext, Consumer<Throwable> onError) {
        RetrofitClient.getInstance()
                .getApiService()
                .getRecommendedToday(pageIndex, customerId, token)
                .compose(RxUtil.io_main())
                .compose(RxUtil.bindToLifecycle(mLifecycleProvider))
                .subscribe(new Observer<RecommendedTodayBean>() {
                    @Override
                    public void onSubscribe(Disposable d) {

                    }

                    @Override
                    public void onNext(RecommendedTodayBean homePageBean) {
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
