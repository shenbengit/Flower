package com.example.flower.rxbus;

import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.disposables.Disposable;

/**
 * @author ShenBen
 * @date 2019/8/21 17:19
 * @email 714081644@qq.com
 */
public class RxSubscriptions {

    private static CompositeDisposable sSubscriptions = new CompositeDisposable();

    public static synchronized void add(Disposable disposable) {
        if (disposable != null) {
            sSubscriptions.add(disposable);
        }
    }

    public static synchronized void remove(Disposable disposable) {
        if (disposable != null) {
            sSubscriptions.remove(disposable);
        }
    }

    public static void clear() {
        sSubscriptions.clear();
    }

    public static void dispose() {
        sSubscriptions.dispose();
    }
}
