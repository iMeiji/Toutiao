package com.meiji.toutiao.api;

import com.meiji.toutiao.bean.news.NewsArticleBean;

import io.reactivex.Observable;
import retrofit2.http.GET;
import retrofit2.http.Query;

/**
 * Created by Meiji on 2017/5/9.
 */

public interface ISearchApi {

    /**
     * 获取搜索文章标题等信息
     * http://www.toutiao.com/search_content/?format=json&keyword=关键词&autoload=true&count=20&cur_tab=1&offset=偏移量
     */
    @GET("search_content/?format=json&autoload=true&count=20&cur_tab=1")
    Observable<NewsArticleBean> getSearch(
            @Query("keyword") String keyword,
            @Query("offset") int offset);
}
