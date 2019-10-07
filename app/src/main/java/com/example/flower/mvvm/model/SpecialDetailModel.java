package com.example.flower.mvvm.model;

import com.example.flower.base.BaseModel;
import com.example.flower.http.RetrofitClient;
import com.example.flower.http.bean.SpecialDetailBean;
import com.example.flower.http.bean.SpecialTypeBean;
import com.example.flower.util.RxUtil;

import io.reactivex.Observer;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Consumer;

/**
 * @author ShenBen
 * @date 2019/10/7 14:46
 * @email 714081644@qq.com
 */
public class SpecialDetailModel extends BaseModel {

    public void getSpecialTypeList(String id, Consumer<SpecialTypeBean> onNext, Consumer<Throwable> onError) {
        RetrofitClient.getInstance()
                .getApiService()
                .getSpecialTypeList(id)
                .compose(RxUtil.io_main())
                .compose(RxUtil.bindToLifecycle(mLifecycleProvider))
                .subscribe(new Observer<SpecialTypeBean>() {
                    @Override
                    public void onSubscribe(Disposable d) {

                    }

                    @Override
                    public void onNext(SpecialTypeBean bean) {
                        if (onNext != null) {
                            try {
                                onNext.accept(bean);
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


    public void getSpecialDetail(String id, int pageIndex, String type, Consumer<SpecialDetailBean> onNext, Consumer<Throwable> onError) {
        RetrofitClient.getInstance()
                .getApiService()
                .getSpecialDetail(id, pageIndex, type)
                .compose(RxUtil.io_main())
                .compose(RxUtil.bindToLifecycle(mLifecycleProvider))
                .subscribe(new Observer<SpecialDetailBean>() {
                    @Override
                    public void onSubscribe(Disposable d) {

                    }

                    @Override
                    public void onNext(SpecialDetailBean bean) {
                        if (onNext != null) {
                            try {
                                onNext.accept(bean);
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
