package com.example.flower.http;

import com.example.flower.constant.Constant;
import com.example.flower.util.LogUtil;
import com.example.flower.util.SystemUtil;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.util.concurrent.TimeUnit;

import okhttp3.OkHttpClient;
import okhttp3.Request;
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
        mClient = builder.build();

        Gson gson = new GsonBuilder().serializeNulls().create();
        mBuilder = new Retrofit.Builder()
                .client(mClient)
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .addConverterFactory(GsonConverterFactory.create(gson))
                .baseUrl(Constant.BASE_URL);
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
}
