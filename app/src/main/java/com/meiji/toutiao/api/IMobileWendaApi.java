package com.meiji.toutiao.api;

import com.meiji.toutiao.Constant;
import com.meiji.toutiao.bean.wenda.WendaArticleBean;
import com.meiji.toutiao.bean.wenda.WendaContentBean;

import io.reactivex.Observable;
import okhttp3.ResponseBody;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.Headers;
import retrofit2.http.POST;
import retrofit2.http.Query;
import retrofit2.http.Url;

/**
 * Created by Meiji on 2017/5/20.
 */

public interface IMobileWendaApi {

    /**
     * 获取头条问答标题等信息
     * http://is.snssdk.com/wenda/v1/native/feedbrow/?category=question_and_answer&wd_version=5&count=20&max_behot_time=1495245397?iid=10344168417&device_id=36394312781
     *
     * @param maxBehotTime 时间轴
     */
    @GET("http://is.snssdk.com/wenda/v1/native/feedbrow/?iid=10344168417&device_id=36394312781&category=question_and_answer&wd_version=5&count=20&aid=13")
    Observable<WendaArticleBean> getWendaArticle(
            @Query("max_behot_time") String maxBehotTime);

    /**
     * 获取头条问答优质回答
     * http://is.snssdk.com/wenda/v1/question/brow/?iid=10344168417&device_id=36394312781
     *
     * @param qid 问答ID
     */
    @POST("http://is.snssdk.com/wenda/v1/question/brow/?iid=10344168417&device_id=36394312781")
    @FormUrlEncoded
    Observable<WendaContentBean> getWendaNiceContent(@Field("qid") String qid);

    /**
     * 获取头条问答优质回答(加载更多)
     * http://is.snssdk.com/wenda/v1/question/loadmore/?iid=10344168417&device_id=36394312781
     *
     * @param qid    问答ID
     * @param offset 偏移量
     */
    @POST("http://is.snssdk.com/wenda/v1/question/loadmore/?iid=10344168417&device_id=36394312781")
    @FormUrlEncoded
    Observable<WendaContentBean> getWendaNiceContentLoadMore(
            @Field("qid") String qid,
            @Field("offset") int offset);

    /**
     * 获取头条问答普通回答
     * http://is.snssdk.com/wenda/v1/questionother/brow/?iid=10344168417&device_id=36394312781
     *
     * @param qid 问答ID
     */
    @POST("http://is.snssdk.com/wenda/v1/questionother/brow/?iid=10344168417&device_id=36394312781")
    @FormUrlEncoded
    Observable<WendaContentBean> getWendaNormalContent(@Field("qid") String qid);

    /**
     * 获取头条问答普通回答(加载更多)
     * http://is.snssdk.com/wenda/v1/questionother/loadmore/?iid=10344168417&device_id=36394312781
     *
     * @param qid    问答ID
     * @param offset 偏移量
     */
    @POST("http://is.snssdk.com/wenda/v1/questionother/loadmore/?iid=10344168417&device_id=36394312781")
    @FormUrlEncoded
    Observable<WendaContentBean> getWendaNormalContentLoadMore(
            @Field("qid") String qid,
            @Field("offset") int offset);

    /**
     * 获取头条问答回答正文
     */
    @GET
    @Headers("User-Agent:" + Constant.USER_AGENT_MOBILE)
    Observable<ResponseBody> getWendaAnsDetail(@Url String url);
}
