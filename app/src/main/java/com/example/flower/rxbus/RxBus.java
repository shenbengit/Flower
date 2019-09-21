package com.example.flower.rxbus;

import com.jakewharton.rxrelay2.PublishRelay;
import com.jakewharton.rxrelay2.Relay;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import io.reactivex.BackpressureStrategy;
import io.reactivex.Flowable;
import io.reactivex.FlowableTransformer;
import io.reactivex.Observable;
import io.reactivex.ObservableTransformer;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Action;
import io.reactivex.functions.Consumer;
import io.reactivex.internal.operators.flowable.FlowableOnBackpressureError;
import io.reactivex.plugins.RxJavaPlugins;
import io.reactivex.processors.FlowableProcessor;
import io.reactivex.processors.PublishProcessor;

/**
 * @author ShenBen
 * @date 2019/8/21 17:18
 * @email 714081644@qq.com
 */
public class RxBus {
    private static volatile RxBus sDefaultInstance;
    /**
     * 没有被压处理
     * 即使出现异常也不会终止订阅关系的 RxRelay
     */
    private final Relay<Object> mBus;
    /**
     * 有背压处理
     */
    private final FlowableProcessor<Object> mFlowableBus;
    private final Map<Class<?>, Object> mStickyEventMap;

    private RxBus() {
        mBus = PublishRelay.create().toSerialized();
        mFlowableBus = PublishProcessor.create().toSerialized();
        mStickyEventMap = new ConcurrentHashMap<>();//ConcurrentHashMap是一个线程安全的HashMap
    }

    public static RxBus getDefault() {
        if (sDefaultInstance == null) {
            synchronized (RxBus.class) {
                if (sDefaultInstance == null) {
                    sDefaultInstance = new RxBus();
                }
            }
        }
        return sDefaultInstance;
    }

    /**
     * 发送事件,默认不采用背压方式
     *
     * @param event 事件
     */
    public void post(Object event) {
        post(event, false);
    }

    /**
     * 发送事件
     *
     * @param event       事件
     * @param useFlowable 是否使用背压方式
     */
    public void post(Object event, boolean useFlowable) {
        if (useFlowable) {
            mFlowableBus.onNext(event);
        } else {
            mBus.accept(event);
        }
    }

    /**
     * 根据传递的 eventType 类型返回特定类型(eventType)的 被观察者
     *
     * @param eventType
     * @param <T>
     * @return 返回特定类型(eventType)的 被观察者
     */
    public <T> Observable<T> toObservable(Class<T> eventType) {
        return mBus.ofType(eventType);//ofType操作符只发射指定类型的数据
    }

    public <T> Flowable<T> toFlowable(Class<T> eventType) {
        return mFlowableBus.ofType(eventType);
    }

    /*** Sticky事件只指事件消费者在事件发布之后才注册的也能接收到该事件的特殊类型。**/

    /**
     * 发送一个Sticky事件
     *
     * @param event 事件
     */
    public void postSticky(Object event) {
        synchronized (mStickyEventMap) {
            mStickyEventMap.put(event.getClass(), event);
        }
        post(event);
    }

    /**
     * 订阅时toObservableSticky(Class<T> eventType)，
     * 先从Map中寻找是否包含该类型的事件，
     * 如果没有，则说明没有Sticky事件要发送，直接订阅Subject（此时作为被观察者Observable）；
     * 如果有，则说明有Sticky事件需要发送，订阅merge（Subject 和 Sticky事件）。
     *
     * @param eventType
     * @param <T>
     * @return
     */
    public <T> Observable<T> toObservableSticky(Class<T> eventType) {
        synchronized (mStickyEventMap) {
            Observable<T> observable = mBus.ofType(eventType);
            Object event = mStickyEventMap.get(eventType);
            if (event != null) {
                return observable.mergeWith(Observable.create(emitter -> emitter.onNext(eventType.cast(event))));
            } else {
                return observable;
            }
        }
    }

    /**
     * 无背压处理注册
     *
     * @param eventType
     * @param composer
     * @param onNext
     * @param <T>
     * @return
     */
    public <T> Disposable register(Class<T> eventType, ObservableTransformer<T, T> composer, Consumer<T> onNext) {
        return toObservable(eventType).compose(composer).subscribe(onNext);
    }

    public <T> Disposable register(Class<T> eventType, ObservableTransformer<T, T> composer, Consumer<T> onNext, Consumer<Throwable> onError) {
        return toObservable(eventType).compose(composer).subscribe(onNext, onError);
    }

    public <T> Disposable register(Class<T> eventType, ObservableTransformer<T, T> composer, Consumer<T> onNext, Consumer<Throwable> onError, Action onComplete) {
        return toObservable(eventType).compose(composer).subscribe(onNext, onError, onComplete);
    }

    /**
     * 背压处理注册
     * 默认@see {@link BackpressureStrategy#ERROR}
     *
     * @param eventType
     * @param onNext
     * @param <T>
     * @return
     */
    public <T> Disposable register(Class<T> eventType, FlowableTransformer<T, T> composer, Consumer<T> onNext) {
        return register(eventType, BackpressureStrategy.ERROR, composer, onNext);
    }

    public <T> Disposable register(Class<T> eventType, FlowableTransformer<T, T> composer, Consumer<T> onNext, Consumer<Throwable> onError) {
        return register(eventType, BackpressureStrategy.ERROR, composer, onNext, onError);
    }

    public <T> Disposable register(Class<T> eventType, FlowableTransformer<T, T> composer, Consumer<T> onNext, Consumer<Throwable> onError, Action onComplete) {
        return register(eventType, BackpressureStrategy.ERROR, composer, onNext, onError, onComplete);
    }

    public <T> Disposable register(Class<T> eventType, BackpressureStrategy strategy, FlowableTransformer<T, T> composer, Consumer<T> onNext) {
        switch (strategy) {
            case DROP:
                return toFlowable(eventType).onBackpressureDrop().compose(composer).subscribe(onNext);
            case ERROR:
                return RxJavaPlugins.onAssembly(new FlowableOnBackpressureError<>(toFlowable(eventType))).compose(composer).subscribe(onNext);
            case LATEST:
                return toFlowable(eventType).onBackpressureLatest().compose(composer).subscribe(onNext);
            case MISSING:
                return toFlowable(eventType).compose(composer).subscribe(onNext);
            default:
                return toFlowable(eventType).onBackpressureBuffer().compose(composer).subscribe(onNext);
        }
    }

    public <T> Disposable register(Class<T> eventType, BackpressureStrategy strategy, FlowableTransformer<T, T> composer, Consumer<T> onNext, Consumer<Throwable> onError) {
        switch (strategy) {
            case DROP:
                return toFlowable(eventType).onBackpressureDrop().compose(composer).subscribe(onNext, onError);
            case ERROR:
                return RxJavaPlugins.onAssembly(new FlowableOnBackpressureError<>(toFlowable(eventType))).compose(composer).subscribe(onNext, onError);
            case LATEST:
                return toFlowable(eventType).onBackpressureLatest().compose(composer).subscribe(onNext, onError);
            case MISSING:
                return toFlowable(eventType).compose(composer).subscribe(onNext, onError);
            default:
                return toFlowable(eventType).compose(composer).onBackpressureBuffer().subscribe(onNext, onError);
        }
    }

    public <T> Disposable register(Class<T> eventType, BackpressureStrategy strategy, FlowableTransformer<T, T> composer, Consumer<T> onNext, Consumer<Throwable> onError, Action onComplete) {
        switch (strategy) {
            case DROP:
                return toFlowable(eventType).onBackpressureDrop().compose(composer).subscribe(onNext, onError, onComplete);
            case ERROR:
                return RxJavaPlugins.onAssembly(new FlowableOnBackpressureError<>(toFlowable(eventType))).compose(composer).subscribe(onNext, onError, onComplete);
            case LATEST:
                return toFlowable(eventType).onBackpressureLatest().compose(composer).subscribe(onNext, onError, onComplete);
            case MISSING:
                return toFlowable(eventType).compose(composer).subscribe(onNext, onError, onComplete);
            default:
                return toFlowable(eventType).onBackpressureBuffer().compose(composer).subscribe(onNext, onError, onComplete);
        }
    }


    /**
     * 判断是否有订阅者
     *
     * @return 是否有订阅者
     */
    public boolean hasObservers() {
        return mBus.hasObservers();
    }

    /**
     * 背压是否有订阅者
     *
     * @return 是否有订阅者
     */
    public boolean hasSubscribers() {
        return mFlowableBus.hasSubscribers();
    }

    /**
     * 根据eventType获取Sticky事件
     */
    public <T> T getStickyEvent(Class<T> eventType) {
        synchronized (mStickyEventMap) {
            return eventType.cast(mStickyEventMap.get(eventType));
        }
    }

    /**
     * 移除指定eventType的Sticky事件
     */
    public <T> T removeStickyEvent(Class<T> eventType) {
        synchronized (mStickyEventMap) {
            return eventType.cast(mStickyEventMap.remove(eventType));
        }
    }

    /**
     * 移除所有的Sticky事件
     */
    public void removeAllStickyEvents() {
        synchronized (mStickyEventMap) {
            mStickyEventMap.clear();
        }
    }
}
