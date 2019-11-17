package com.example.flower.mvvm.model;

import com.example.flower.base.BaseModel;
import com.example.flower.http.RetrofitClient;
import com.example.flower.http.bean.PlantsTypeBean;
import com.example.flower.util.RxUtil;

import io.reactivex.Observer;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Consumer;

/**
 * @author ShenBen
 * @date 2019/11/17 14:17
 * @email 714081644@qq.com
 */
public class KnowPlantsModel extends BaseModel {

    public void getPlantsType(int index, Consumer<PlantsTypeBean> onNext,Consumer<Throwable> onError){
        RetrofitClient.getInstance()
                .getApiService()
                .getPlantsType(index,"","")
                .compose(RxUtil.io_main())
                .compose(mLifecycleProvider.bindToLifecycle())
                .subscribe(new Observer<PlantsTypeBean>() {
                    @Override
                    public void onSubscribe(Disposable d) {

                    }

                    @Override
                    public void onNext(PlantsTypeBean plantsTypeBean) {
                        if (onNext != null) {
                            try {
                                onNext.accept(plantsTypeBean);
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
