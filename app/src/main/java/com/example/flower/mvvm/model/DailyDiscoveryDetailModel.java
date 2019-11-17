package com.example.flower.mvvm.model;

import com.example.flower.base.BaseModel;
import com.example.flower.http.RetrofitClient;
import com.example.flower.http.bean.DailyDiscoveryDetailBean;
import com.example.flower.util.RxUtil;

import io.reactivex.Observer;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Consumer;

/**
 * @author ShenBen
 * @date 2019/10/20 14:22
 * @email 714081644@qq.com
 */
public class DailyDiscoveryDetailModel extends BaseModel {

    public void getDailyDiscoveryDetail(String id, Consumer<DailyDiscoveryDetailBean> onNext, Consumer<Throwable> onError) {
        RetrofitClient.getInstance()
                .getApiService()
                .getDailyDiscoveryDetail(id, "", "")
                .compose(RxUtil.io_main())
                .compose(RxUtil.bindToLifecycle(mLifecycleProvider))
                .subscribe(new Observer<DailyDiscoveryDetailBean>() {
                    @Override
                    public void onSubscribe(Disposable d) {

                    }

                    @Override
                    public void onNext(DailyDiscoveryDetailBean bean) {
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
