package com.meiji.toutiao.api;

import com.meiji.toutiao.Constant;
import com.meiji.toutiao.bean.news.NewsArticleBean;
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
     * "http://toutiao.com/api/article/recent/?source=2&category=类型&as=A105177907376A5&cp=5797C7865AD54E1&_=时间&count=30";
     * 获取新闻标题等信息
     */
    @GET("api/article/recent/?source=2&as=A105177907376A5&cp=5797C7865AD54E1&count=30")
    Observable<NewsArticleBean> getNewsArticle1(
            @Query("category") String category,
            @Query("_") int max_behot_time);

    /**
     * "http://toutiao.com/api/article/recent/?source=2&category=类型&as=A105177907376A5&cp=5797C7865AD54E1&_=时间&count=30";
     * 获取新闻标题等信息 部分请求参数不同上
     */
    @GET("api/article/recent/?source=2&as=A1F519007D6B4BB&cp=590D4B541BBB1E1")
    @Headers({
            "User-Agent:" + Constant.USER_AGENT_PC,
            "X-Requested-With:XMLHttpRequest"
    })
    Observable<NewsArticleBean> getNewsArticle2(
            @Query("category") String category);

    /**
     * 获取新闻内容的API
     */
    @GET
    @Headers("User-Agent:" + Constant.USER_AGENT_PHONE)
    Call<ResponseBody> getNewsContentRedirectUrl(@Url String url);

    /**
     * 获取新闻HTML内容
     * http://m.toutiao.com/i6364969235889783298/info/
     */
    @GET
    Observable<NewsContentBean> getNewsContent(@Url String url);
}
