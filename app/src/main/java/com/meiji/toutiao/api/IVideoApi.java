package com.meiji.toutiao.api;

import com.meiji.toutiao.bean.video.VideoArticleBean;
import com.meiji.toutiao.bean.video.VideoContentBean;

import io.reactivex.Observable;
import retrofit2.http.GET;
import retrofit2.http.Query;
import retrofit2.http.Url;

/**
 * Created by Meiji on 2017/5/9.
 */

public interface IVideoApi {

    /**
     * 获取视频标题等信息
     * http://toutiao.com/api/article/recent/?source=2&category=类型&as=A105177907376A5&cp=5797C7865AD54E1&count=20"
     */
    @GET("api/article/recent/?source=2&as=A105177907376A5&cp=5797C7865AD54E1&count=30")
    Observable<VideoArticleBean> getVideoArticle(
            @Query("category") String category,
            @Query("_") int max_behot_time);

    /**
     * 获取视频信息
     * Api 生成较复杂 详情查看 {@linkplain com.meiji.toutiao.module.video.content.VideoContentPresenter#doLoadVideoData(String)}
     * http://ib.365yg.com/video/urls/v/1/toutiao/mp4/视频ID?r=17位随机数&s=加密结果
     */
    @GET
    Observable<VideoContentBean> getVideoContent(@Url String url);

}
