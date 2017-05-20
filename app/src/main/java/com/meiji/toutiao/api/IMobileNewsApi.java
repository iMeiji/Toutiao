package com.meiji.toutiao.api;

import com.meiji.toutiao.bean.news.MultiNewsArticleBean;
import com.meiji.toutiao.bean.news.NewsCommentMobileBean;

import io.reactivex.Observable;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Query;

/**
 * Created by Meiji on 2017/5/17.
 * 参考 :　https://github.com/hrscy/TodayNews/blob/master/news.json
 */

public interface IMobileNewsApi {

    String HOST = "http://is.snssdk.com/";

    /**
     * 获取个性化新闻
     * http://is.snssdk.com/api/news/feed/v53/?iid=5034850950&device_id=6096495334&category=news_society
     * http://lf.snssdk.com/api/news/feed/v53/?iid=10247804300&device_id=36328180756&category=news_society
     * http://ib.snssdk.com/api/news/feed/v53/?
     *
     * @param iid      用户ID
     * @param deviceId 设备ID
     * @param category 新闻/图片/视频栏目
     */
    @POST("http://is.snssdk.com/api/news/feed/v53/")
    Call<ResponseBody> getNewsArticle(
            @Query("iid") String iid,
            @Query("device_id") String deviceId,
            @Query("category") String category);

    @POST("http://is.snssdk.com/api/news/feed/v53/")
    Observable<MultiNewsArticleBean> getNewsArticle(
            @Query("category") String category);

    @POST("http://lf.snssdk.com/api/news/feed/v53/?iid=10247804300&device_id=36328180756")
    Observable<MultiNewsArticleBean> getNewsArticle2(
            @Query("category") String category);

    /**
     * 获取新闻评论
     * http://is.snssdk.com/article/v53/tab_comments/?group_id=6314103921648926977&offset=0
     *
     * @param groupId 新闻ID
     * @param offset  偏移量
     */
    @GET("http://is.snssdk.com/article/v53/tab_comments/")
    Observable<NewsCommentMobileBean> getNewsComment(
            @Query("group_id") String groupId,
            @Query("offset") int offset);
}
