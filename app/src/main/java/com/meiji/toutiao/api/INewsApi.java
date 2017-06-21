package com.meiji.toutiao.api;

import com.meiji.toutiao.Constant;
import com.meiji.toutiao.bean.news.NewsContentBean;

import io.reactivex.Observable;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Headers;
import retrofit2.http.Query;
import retrofit2.http.Url;

/**
 * Created by Meiji on 2017/5/6.
 */

public interface INewsApi {

    String HOST = "http://toutiao.com/";

    /**
     * 获取新闻标题等信息
     * "http://toutiao.com/api/article/recent/?source=2&category=类型&as=A105177907376A5&cp=5797C7865AD54E1&_=时间&count=30";
     * <p>
     * 其他 API
     * "http://www.toutiao.com/api/article/feed/?category=类型&as=A115C8457F69B85&cp=585F294B8845EE1&_=时间&count=30";
     * "http://www.toutiao.com/api/pc/feed/?category=类型&utm_source=toutiao&widen=1&max_behot_time=时间&max_behot_time_tmp=时间&tadrequire=true&as=A1C598BB87BE7DA&cp=58B72ED7AD3A0E1";
     */
    @GET("api/article/recent/?source=2&as=A105177907376A5&cp=5797C7865AD54E1&count=30")
    Call<ResponseBody> getNewsArticle1(
            @Query("category") String category,
            @Query("_") int max_behot_time);

    /**
     * 获取新闻标题等信息 部分请求参数不同上
     * "http://toutiao.com/api/article/recent/?source=2&category=类型&as=A105177907376A5&cp=5797C7865AD54E1&_=时间&count=30";
     */
    @GET("api/article/recent/?source=2&as=A1F519007D6B4BB&cp=590D4B541BBB1E1")
    @Headers({
            "User-Agent:" + Constant.USER_AGENT_PC,
            "X-Requested-With:XMLHttpRequest"
    })
    Call<ResponseBody> getNewsArticle2(
            @Query("category") String category,
            @Query("_") int max_behot_time);

    /**
     * 获取新闻内容的API
     */
    @GET
    @Headers("User-Agent:" + Constant.USER_AGENT_MOBILE)
    Call<ResponseBody> getNewsContentRedirectUrl(@Url String url);

    /**
     * 获取新闻HTML内容
     * http://m.toutiao.com/i6364969235889783298/info/
     */
    @GET
    Observable<NewsContentBean> getNewsContent(@Url String url);

    /**
     * 获取新闻评论
     * http://www.toutiao.com/api/comment/list/?group_id=头条号&item_id=文章号&offset=偏移量&count=数量
     */
    @GET("api/comment/list/?count=10")
    Call<ResponseBody> getNewsComment(
            @Query("group_id") String groupId,
            @Query("item_id") String itemId,
            @Query("offset") int offset);

}
