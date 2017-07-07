package com.meiji.toutiao.api;

import com.meiji.toutiao.Constant;

import io.reactivex.Observable;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Headers;
import retrofit2.http.Query;
import retrofit2.http.Url;

/**
 * Created by Meiji on 2017/5/10.
 */

public interface IMediaApi {

    /**
     * 获取头条号文章
     * http://www.toutiao.com/pgc/ma/?media_id=头条号&page_type=1&count=20&version=2&platform=pc&as=479BB4B7254C150&cp=585DB1871ED64E1&max_behot_time=时间
     * 手机版
     * http://toutiao.com/pgc/ma/?page_type=0&max_behot_time=&media_id=6547479326&output=json&is_json=1&count=20&from=user_profile_app&version=2&as=479BB4B7254C150&cp=58E7182DD50EFE1&callback=jsonp4
     */
    @GET("pgc/ma/?page_type=1&count=20&version=2&platform=pc&as=479BB4B7254C150&cp=585DB1871ED64E1")
    Observable<ResponseBody> getMediaArticle(
            @Query("media_id") String mediaId,
            @Query("max_behot_time") int time);

    /**
     * 获取评论请求参数
     */
    @GET
    @Headers("User-Agent:" + Constant.USER_AGENT_MOBILE)
    Call<ResponseBody> getCommentParameter(@Url String url);
}
