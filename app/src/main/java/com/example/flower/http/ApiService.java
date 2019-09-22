package com.example.flower.http;

import com.example.flower.http.bean.CoverPageBean;
import com.example.flower.http.bean.NewestVideoRemindBean;
import com.example.flower.http.bean.PaidArticleEveryDayNewsBean;
import com.example.flower.http.bean.WallpaperVersionBean;

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

    @GET(value = "cactus/link/getCoverPage")
    Observable<CoverPageBean> getCoverPage();

    @GET(value = "cactus/msg/wallpaper/version")
    Observable<WallpaperVersionBean> getWallpaperVersion();

    @GET(value = "cactus/paidArticle/getPaidArticleEveryDayNews")
    Observable<PaidArticleEveryDayNewsBean> getPaidArticleEveryDayNews();

    @GET(value = "cactus/researchCommunity/newestVideoRemind")
    Observable<NewestVideoRemindBean> getNewestVideoRemind();


}
