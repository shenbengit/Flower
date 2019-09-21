package com.example.flower.http;

import io.reactivex.Observable;
import okhttp3.ResponseBody;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.Streaming;
import retrofit2.http.Url;

/**
 * @author ShenBen
 * @date 2019/8/21 9:23
 * @email 714081644@qq.com
 */
public interface ApiService {

    @Streaming
    @GET
    Observable<ResponseBody> downloadFile(@Url String url);

    @Streaming
    @GET
    Observable<ResponseBody> downloadFile(@Header("Range") String range, @Url String url);
}
