package com.meiji.toutiao.api;

import okhttp3.ResponseBody;
import retrofit2.Call;
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
    Call<ResponseBody> getSearch(
            @Query("keyword") String keyword,
            @Query("offset") int offset);
}
