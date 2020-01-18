package com.example.flower.http;

import android.text.TextUtils;

import com.example.flower.constant.Constant;
import com.example.flower.util.LogUtil;
import com.example.flower.util.RxUtil;
import com.example.flower.util.SystemUtil;
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
    private static final long DEFAULT_MILLISECONDS = 60000;      //默认的超时时间
    private static long REFRESH_TIME = 300;                      //回调刷新时间（单位ms）

    private Retrofit mRetrofit;
    private ApiService mService;

    private static final class Holder {
        private static final RetrofitClient INSTANCE = new RetrofitClient();
    }

    public static RetrofitClient getInstance() {
        return Holder.INSTANCE;
    }

    private RetrofitClient() {
        OkHttpClient.Builder builder = new OkHttpClient.Builder();
        HttpLoggingInterceptor interceptor = new HttpLoggingInterceptor(message -> LogUtil.i(message));
        interceptor.setLevel(HttpLoggingInterceptor.Level.BASIC);
        builder.addInterceptor(interceptor);
        builder.addInterceptor(chain -> {
            //设置公共请求头
            Request request = chain.request().newBuilder()
                    .addHeader("Content-Type", "application/json;charset=UTF-8")
                    .addHeader("Accept", "application/json;charset=UTF-8")
                    .addHeader("client-channel", "qqmark")
                    .addHeader("client-platform", SystemUtil.getSystemVersion())
                    .addHeader("client-terminal", SystemUtil.getSystemModel())
                    .addHeader("client-unique", "")
                    .addHeader("client-version", "7.5.2")
                    .build();
            return chain.proceed(request);
        });
        builder.readTimeout(DEFAULT_MILLISECONDS, TimeUnit.MILLISECONDS);
        builder.writeTimeout(DEFAULT_MILLISECONDS, TimeUnit.MILLISECONDS);
        builder.connectTimeout(DEFAULT_MILLISECONDS, TimeUnit.MILLISECONDS);
        OkHttpClient client = builder.build();

        Gson gson = new GsonBuilder().serializeNulls().create();
        Retrofit.Builder retrofitBuilder = new Retrofit.Builder()
                .client(client)
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .addConverterFactory(GsonConverterFactory.create(gson))
                .baseUrl(Constant.BASE_URL);
        mRetrofit = retrofitBuilder.build();
        mService = mRetrofit.create(ApiService.class);
    }

    public ApiService getApiService() {
        return mService;
    }

    public void setBaseUrl(String baseUrl) {
        if (!TextUtils.isEmpty(baseUrl)) {
            mRetrofit = mRetrofit.newBuilder().baseUrl(baseUrl).build();
            mService = mRetrofit.create(ApiService.class);
        }
    }
    public void dowdloadPicture(String url, String path, String fileName) {
        getApiService().downloadFile(url)
                .compose(RxUtil.io_io())
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
