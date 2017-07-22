package com.meiji.toutiao.api;

import com.meiji.toutiao.bean.search.SearchRecommentBean;
import com.meiji.toutiao.bean.search.SearchResultBean;
import com.meiji.toutiao.bean.search.SearchSuggestionBean;
import com.meiji.toutiao.bean.search.SearchVideoInfoBean;

import io.reactivex.Observable;
import okhttp3.ResponseBody;
import retrofit2.http.GET;
import retrofit2.http.Query;
import retrofit2.http.Url;

/**
 * Created by Meiji on 2017/6/13.
 */

public interface IMobileSearchApi {

    /**
     * 获取搜索建议
     * http://is.snssdk.com/2/article/search_sug/?keyword=3&from=search_tab&iid=10344168417&device_id=36394312781
     *
     * @param keyword 搜索内容
     */
    @GET("http://is.snssdk.com/2/article/search_sug/?from=search_tab&iid=10344168417&device_id=36394312781")
    Observable<SearchSuggestionBean> getSearchSuggestion(@Query("keyword") String keyword);

    /**
     * 获取搜索结果
     * http://is.snssdk.com/api/2/wap/search_content/?from=search_tab&keyword=123&iid=10344168417&device_id=36394312781&count=10&cur_tab=1&format=json&offset=20
     *
     * @param keyword 搜索内容
     * @param curTab  搜索栏目 1综合 2视频 3图集 4用户 5问答
     * @param offset  偏移量
     */
    @GET("http://is.snssdk.com/api/2/wap/search_content/?from=search_tab&iid=12507202490&device_id=37487219424&count=10&format=json")
    Observable<SearchResultBean> getSearchResult(
            @Query("keyword") String keyword,
            @Query("cur_tab") String curTab,
            @Query("offset") int offset);

    @GET("http://is.snssdk.com/api/2/wap/search_content/?from=search_tab&iid=12507202490&device_id=37487219424&count=10&format=json")
    Observable<ResponseBody> getSearchResult2(
            @Query("keyword") String keyword,
            @Query("cur_tab") String curTab,
            @Query("offset") int offset);

    /**
     * 获取搜索推荐
     * http://is.snssdk.com/search/suggest/wap/initial_page/?from=feed&sug_category=__all__&iid=10344168417&device_id=36394312781&format=json
     */
    @GET("http://is.snssdk.com/search/suggest/wap/initial_page/?from=feed&sug_category=__all__&iid=10344168417&device_id=36394312781&format=json")
    Observable<SearchRecommentBean> getSearchRecomment();

    /**
     * 获取搜索视频内容
     * https://m.365yg.com/i6436151402837312001/info/
     */
    @GET
    Observable<SearchVideoInfoBean> getSearchVideoInfo(@Url String url);
}