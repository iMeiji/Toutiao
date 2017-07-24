package com.meiji.toutiao.api;

import com.meiji.toutiao.bean.media.MediaProfileBean;
import com.meiji.toutiao.bean.media.MediaWendaBean;
import com.meiji.toutiao.bean.media.MultiMediaArticleBean;

import io.reactivex.Observable;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

/**
 * Created by Meiji on 2017/5/20.
 */

public interface IMobileMediaApi {

    /**
     * 头条号主页信息
     * https://is.snssdk.com/user/profile/homepage/v3/json/?media_id=4377795668&to_html=0&source=article_top_author&refer=all
     *
     * @param mediaId 头条号ID
     */
    @GET("https://is.snssdk.com/user/profile/homepage/v3/json/?to_html=0&source=article_top_author&refer=all&aid=13")
    Observable<MediaProfileBean> getMediaProfile(
            @Query("media_id") String mediaId);

    /**
     * 获取头条号文章
     * https://is.snssdk.com/pgc/ma/?page_type=1&max_behot_time=1495181160&media_id=52445544609&output=json&is_json=1&count=10&from=user_profile_app&version=2&as=479BB4B7254C150&cp=585DB1871ED64E1
     *
     * @param mediaId      头条号ID
     * @param maxBehotTime 时间轴
     */
    @GET("https://is.snssdk.com/pgc/ma/?page_type=1&output=json&is_json=1&count=20&from=user_profile_app&version=2")
    Observable<MultiMediaArticleBean> getMediaArticle(
            @Query("media_id") String mediaId,
            @Query("max_behot_time") String maxBehotTime,
            @Query("as") String as,
            @Query("cp") String cp);

    /**
     * 获取头条号视频
     * https://is.snssdk.com/pgc/ma/?page_type=0&max_behot_time=1495181160&media_id=52445544609&output=json&is_json=1&count=10&from=user_profile_app&version=2&as=479BB4B7254C150&cp=585DB1871ED64E1
     *
     * @param mediaId      头条号ID
     * @param maxBehotTime 时间轴
     */
    @GET("https://is.snssdk.com/pgc/ma/?page_type=0&output=json&is_json=1&count=10&from=user_profile_app&version=2")
    Observable<MultiMediaArticleBean> getMediaVideo(
            @Query("media_id") String mediaId,
            @Query("max_behot_time") String maxBehotTime,
            @Query("as") String as,
            @Query("cp") String cp);

    /**
     * 获取头条号问答
     * https://is.snssdk.com/wenda/profile/wendatab/brow/?other_id=6619635172&format=json&from_channel=media_channel
     *
     * @param mediaId 头条号ID
     */
    @GET("https://is.snssdk.com/wenda/profile/wendatab/brow/?format=json&from_channel=media_channel")
    Observable<MediaWendaBean> getMediaWenda(
            @Query("other_id") String mediaId);

    /**
     * 获取头条号问答(加载更多)
     * http://is.snssdk.com/wenda/profile/wendatab/loadmore/?other_id=53294853357&format=json&from_channel=media_channel&cursor=6428177292098273538&count=10&offset=undefined
     *
     * @param mediaId 头条号ID
     * @param cursor  偏移量
     */
    @GET("http://is.snssdk.com/wenda/profile/wendatab/loadmore/?format=json&from_channel=media_channel&count=10&offset=undefined")
    Observable<MediaWendaBean> getMediaWendaLoadMore(
            @Query("other_id") String mediaId,
            @Query("cursor") String cursor);

    /**
     * 获取头条号动态
     * https://is.snssdk.com/dongtai/list/v11/?user_id=6619635172&max_cursor=1494916016999
     *
     * @param mediaId   头条号ID
     * @param maxCursor 偏移量
     */
    @GET("https://is.snssdk.com/dongtai/list/v11/?")
    Call<ResponseBody> getMediaDongtai(
            @Query("user_id") String mediaId,
            @Query("max_cursor") int maxCursor);
}
