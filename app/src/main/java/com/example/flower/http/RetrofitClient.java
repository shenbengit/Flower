package com.example.flower.http;

import com.example.flower.constant.SharedPreferencesKey;
import com.example.flower.util.LogUtil;
import com.example.flower.util.RxUtil;
import com.example.flower.util.SharedPreferencesUtil;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.util.concurrent.TimeUnit;

import io.reactivex.Observer;
import io.reactivex.disposables.Disposable;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.ResponseBody;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * @author ShenBen
 * @date 2019/8/21 9:21
 * @email 714081644@qq.com
 */
public class RetrofitClient {
    private static String BASE_URL;
    private static final long DEFAULT_MILLISECONDS = 60000;      //默认的超时时间
    private static long REFRESH_TIME = 300;                      //回调刷新时间（单位ms）

    private OkHttpClient mClient;
    private Retrofit.Builder mBuilder;
    private ApiService mService;

    private static final class Holder {
        private static final RetrofitClient INSTANCE = new RetrofitClient();
    }

    public static RetrofitClient getInstance() {
        return Holder.INSTANCE;
    }

    private RetrofitClient() {
        BASE_URL = SharedPreferencesUtil.getInstance().getString(SharedPreferencesKey.BASE_URL);
        OkHttpClient.Builder builder = new OkHttpClient.Builder();
        HttpLoggingInterceptor interceptor = new HttpLoggingInterceptor(message -> LogUtil.i(message));
        interceptor.setLevel(HttpLoggingInterceptor.Level.BASIC);
        builder.addInterceptor(interceptor);
        builder.addInterceptor(chain -> {
            Request request = chain.request().newBuilder()
                    .addHeader("Content-Type", "application/json")
                    .addHeader("Accept", "application/json")
                    .build();
            return chain.proceed(request);
        });
        builder.readTimeout(DEFAULT_MILLISECONDS, TimeUnit.MILLISECONDS);
        builder.writeTimeout(DEFAULT_MILLISECONDS, TimeUnit.MILLISECONDS);
        builder.connectTimeout(DEFAULT_MILLISECONDS, TimeUnit.MILLISECONDS);
        mClient = builder.build();

        Gson gson = new GsonBuilder().serializeNulls().create();
        mBuilder = new Retrofit.Builder()
                .client(mClient)
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .addConverterFactory(GsonConverterFactory.create(gson))
                .baseUrl(BASE_URL);
        Retrofit retrofit = mBuilder.build();
        mService = retrofit.create(ApiService.class);
    }

    public OkHttpClient getOkHttpClient() {
        return mClient;
    }

    public Retrofit.Builder getBuilder() {
        return mBuilder;
    }

    public ApiService getApiService() {
        return mService;
    }


    public void downloadFile(String url) {
        getApiService().downloadFile(url)
                .compose(RxUtil.io_main())
                .subscribe(new Observer<ResponseBody>() {
                    @Override
                    public void onSubscribe(Disposable d) {

                    }

                    @Override
                    public void onNext(ResponseBody responseBody) {

                    }

                    @Override
                    public void onError(Throwable e) {

                    }

                    @Override
                    public void onComplete() {

                    }
                });
    }


    public void downloadFile(long range, String url) {
        getApiService().downloadFile("bytes=" + range + "-", url)
                .compose(RxUtil.io_main())
                .subscribe(new Observer<ResponseBody>() {
                    @Override
                    public void onSubscribe(Disposable d) {

                    }

                    @Override
                    public void onNext(ResponseBody responseBody) {

                    }

                    @Override
                    public void onError(Throwable e) {

                    }

                    @Override
                    public void onComplete() {

                    }
                });
    }
}
