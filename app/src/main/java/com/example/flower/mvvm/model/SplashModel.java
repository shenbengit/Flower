package com.example.flower.mvvm.model;

import com.example.flower.base.BaseModel;
import com.example.flower.http.RetrofitClient;
import com.example.flower.http.bean.CoverPageBean;
import com.example.flower.http.bean.NewestVideoRemindBean;
import com.example.flower.http.bean.PaidArticleEveryDayNewsBean;
import com.example.flower.http.bean.WallpaperVersionBean;
import com.example.flower.util.RxUtil;

import io.reactivex.Observer;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Consumer;

/**
 * @author ShenBen
 * @date 2019/9/22 10:17
 * @email 714081644@qq.com
 */
public class SplashModel extends BaseModel {

    public void getCoverPage( Consumer<CoverPageBean> onNext, Consumer<Throwable> onError) {
        RetrofitClient
                .getInstance()
                .getApiService()
                .getCoverPage()
                .compose(RxUtil.bindToLifecycle(mLifecycleProvider))
                .compose(RxUtil.io_main())
                .subscribe(new Observer<CoverPageBean>() {
                    @Override
                    public void onSubscribe(Disposable d) {

                    }

                    @Override
                    public void onNext(CoverPageBean coverPageBean) {
                        if (onNext != null) {
                            try {
                                onNext.accept(coverPageBean);
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


    public void getWallpaperVersion(Consumer<WallpaperVersionBean> onNext, Consumer<Throwable> onError) {
        RetrofitClient
                .getInstance()
                .getApiService()
                .getWallpaperVersion()
                .compose(RxUtil.bindToLifecycle(mLifecycleProvider))
                .compose(RxUtil.io_main())
                .subscribe(new Observer<WallpaperVersionBean>() {
                    @Override
                    public void onSubscribe(Disposable d) {

                    }

                    @Override
                    public void onNext(WallpaperVersionBean coverPageBean) {
                        if (onNext != null) {
                            try {
                                onNext.accept(coverPageBean);
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

    public void getPaidArticleEveryDayNews(Consumer<PaidArticleEveryDayNewsBean> onNext, Consumer<Throwable> onError) {
        RetrofitClient
                .getInstance()
                .getApiService()
                .getPaidArticleEveryDayNews()
                .compose(RxUtil.bindToLifecycle(mLifecycleProvider))
                .compose(RxUtil.io_main())
                .subscribe(new Observer<PaidArticleEveryDayNewsBean>() {
                    @Override
                    public void onSubscribe(Disposable d) {

                    }

                    @Override
                    public void onNext(PaidArticleEveryDayNewsBean coverPageBean) {
                        if (onNext != null) {
                            try {
                                onNext.accept(coverPageBean);
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

    public void getNewestVideoRemind( Consumer<NewestVideoRemindBean> onNext, Consumer<Throwable> onError) {
        RetrofitClient
                .getInstance()
                .getApiService()
                .getNewestVideoRemind()
                .compose(RxUtil.bindToLifecycle(mLifecycleProvider))
                .compose(RxUtil.io_main())
                .subscribe(new Observer<NewestVideoRemindBean>() {
                    @Override
                    public void onSubscribe(Disposable d) {

                    }

                    @Override
                    public void onNext(NewestVideoRemindBean coverPageBean) {
                        if (onNext != null) {
                            try {
                                onNext.accept(coverPageBean);
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
