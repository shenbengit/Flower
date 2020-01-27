package com.example.flower.http;

import com.example.flower.http.bean.ArticleInfoBean;
import com.example.flower.http.bean.CoverPageBean;
import com.example.flower.http.bean.DailyDiscoveryBean;
import com.example.flower.http.bean.DailyDiscoveryDetailBean;
import com.example.flower.http.bean.HomePageBean;
import com.example.flower.http.bean.KnowFlowerResultBean;
import com.example.flower.http.bean.NewestVideoRemindBean;
import com.example.flower.http.bean.PaidArticleEveryDayNewsBean;
import com.example.flower.http.bean.PlantsDetailBean;
import com.example.flower.http.bean.PlantsTypeBean;
import com.example.flower.http.bean.RecommendedTodayBean;
import com.example.flower.http.bean.SpecialDetailBean;
import com.example.flower.http.bean.SpecialTypeBean;
import com.example.flower.http.bean.TodayBean;
import com.example.flower.http.bean.WallpaperBean;
import com.example.flower.http.bean.WallpaperVersionBean;

import io.reactivex.Observable;
import okhttp3.ResponseBody;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.Headers;
import retrofit2.http.POST;
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

    @GET(value = "/cactus/link/getCoverPage")
    Observable<CoverPageBean> getCoverPage();

    @GET(value = "/cactus/msg/wallpaper/version")
    Observable<WallpaperVersionBean> getWallpaperVersion();

    @GET(value = "/cactus/paidArticle/getPaidArticleEveryDayNews")
    Observable<PaidArticleEveryDayNewsBean> getPaidArticleEveryDayNews();

    @GET(value = "/cactus/researchCommunity/newestVideoRemind")
    Observable<NewestVideoRemindBean> getNewestVideoRemind();

    /**
     * 获取主页数据
     *
     * @param city 当前定位的城市
     * @return
     */
    @GET(value = "/cactus/communityHomePage/getHomePageForNewVersion")
    Observable<HomePageBean> getHomePageForNewVersion(@Query("city") String city);

    /**
     * 今日推荐数据
     *
     * @param pageIndex
     * @param customerId
     * @param token
     * @return
     */
    @GET(value = "/cactus/sysArticle/getRecommendArticleListV2")
    Observable<RecommendedTodayBean> getRecommendedToday(@Query("pageIndex") int pageIndex, @Query("customerId") String customerId, @Query("token") String token);

    /**
     * 每日发现
     *
     * @param index
     * @param customerId
     * @param token
     * @return
     */
    @GET(value = "/cactus/article/v2/list")
    Observable<DailyDiscoveryBean> getDailyDiscovery(@Query("index") int index, @Query("customerId") String customerId, @Query("token") String token);

    /**
     * 今日推送的图片数据
     *
     * @param customerId
     * @return
     */
    @FormUrlEncoded
    @POST(value = "/cactus/checkin/today")
    @Headers({"Content-Type:application/x-www-form-urlencoded;charset=UTF-8"})
    Observable<TodayBean> getToday(@Field("customerId") String customerId);

    /**
     * 获取专题分类的内容分类
     *
     * @param id
     * @return
     */

    @GET(value = "/cactus/sysArticle/getArticleCartList")
    Observable<SpecialTypeBean> getSpecialTypeList(@Query("id") String id);

    /**
     * 获取专题分类里面的列表
     *
     * @param id
     * @param pageIndex
     * @param type
     * @return
     */
    @GET(value = "/cactus/sysArticle/getCartArticleList")
    Observable<SpecialDetailBean> getSpecialList(@Query("id") String id, @Query("pageIndex") int pageIndex, @Query("type") String type);

    /**
     * 获取详情
     *
     * @param uId
     * @param aId id
     * @return
     */
    @GET(value = "/cactus/sysArticle/getArticleInfo")
    Observable<ArticleInfoBean> getArticleInfo(@Query("uId") String uId, @Query("aId") String aId);

    /**
     * 每日发现详情
     *
     * @param id
     * @param customerId
     * @param token
     * @return
     */
    @GET(value = "/cactus/article/v2/detail")
    Observable<DailyDiscoveryDetailBean> getDailyDiscoveryDetail(@Query("id") String id, @Query("customerId") String customerId, @Query("token") String token);

    /**
     * 认识植物
     *
     * @param index      分页标志
     * @param customerId
     * @param token
     * @return
     */
    @GET(value = "/cactus/flower/index")
    Observable<PlantsTypeBean> getPlantsType(@Query("index") int index, @Query("customerId") String customerId, @Query("token") String token);

    /**
     * 获取壁纸信息
     *
     * @param index      分页标志
     * @param type
     * @param scale
     * @param customerId
     * @param token
     * @return
     */
    @GET(value = "/cactus/wallpaper/v2/index")
    Observable<WallpaperBean> getWallpaper(@Query("index") int index, @Query("type") String type, @Query("scale") String scale, @Query("customerId") String customerId, @Query("token") String token);


    @GET(value = "/cactus/flower/list")
    Observable<PlantsDetailBean> getPlantsDetail(@Query("cid") String cid, @Query("index") int index, @Query("customerId") String customerId, @Query("token") String token);

    /**
     * 识花接口
     *
     * @param url           接口url
     * @param Authorization 请求头
     * @param imageUrl      提交花的base64
     * @return
     */
    @POST
    @FormUrlEncoded
    @Headers("Content-Type:application/x-www-form-urlencoded; charset=UTF-8")
    Observable<KnowFlowerResultBean> knowFlower(@Url String url, @Header("Authorization") String Authorization, @Field("image_url") String imageUrl);

}
