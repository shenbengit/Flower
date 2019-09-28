package com.example.flower.http;

import com.example.flower.http.bean.CoverPageBean;
import com.example.flower.http.bean.HomePageBean;
import com.example.flower.http.bean.NewestVideoRemindBean;
import com.example.flower.http.bean.PaidArticleEveryDayNewsBean;
import com.example.flower.http.bean.RecommendedTodayBean;
import com.example.flower.http.bean.WallpaperVersionBean;

import io.reactivex.Observable;
import okhttp3.ResponseBody;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.Query;
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

    /**
     * 获取主页数据
     *
     * @param city 当前定位的城市
     * @return
     */
    @GET(value = "cactus/communityHomePage/getHomePageForNewVersion")
    Observable<HomePageBean> getHomePageForNewVersion(@Query("city") String city);


    @GET(value = "cactus/sysArticle/getRecommendArticleListV2")
    Observable<RecommendedTodayBean> getRecommendedToday(@Query("pageIndex") int pageIndex, @Query("customerId") String customerId, @Query("token") String token);


}
