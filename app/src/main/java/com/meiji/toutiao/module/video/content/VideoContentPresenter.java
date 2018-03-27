package com.meiji.toutiao.module.video.content;

import android.util.Base64;
import android.util.Log;

import com.meiji.toutiao.ErrorAction;
import com.meiji.toutiao.api.IVideoApi;
import com.meiji.toutiao.bean.video.VideoContentBean;
import com.meiji.toutiao.module.news.comment.NewsCommentPresenter;
import com.meiji.toutiao.util.RetrofitFactory;

import java.util.Random;
import java.util.zip.CRC32;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;

/**
 * Created by Meiji on 2017/3/30.
 */

public class VideoContentPresenter extends NewsCommentPresenter implements IVideoContent.Presenter {

    private static final String TAG = "VideoContentPresenter";
    private IVideoContent.View view;

    VideoContentPresenter(IVideoContent.View view) {
        super(view);
        this.view = view;
    }

    private static String getVideoContentApi(String videoid) {
        String VIDEO_HOST = "http://ib.365yg.com";
        String VIDEO_URL = "/video/urls/v/1/toutiao/mp4/%s?r=%s";
        String r = getRandom();
        String s = String.format(VIDEO_URL, videoid, r);
        // 将/video/urls/v/1/toutiao/mp4/{videoid}?r={Math.random()} 进行crc32加密
        CRC32 crc32 = new CRC32();
        crc32.update(s.getBytes());
        String crcString = crc32.getValue() + "";
        String url = VIDEO_HOST + s + "&s=" + crcString;
        return url;
    }

    private static String getRandom() {
        Random random = new Random();
        StringBuilder result = new StringBuilder();
        for (int i = 0; i < 16; i++) {
            result.append(random.nextInt(10));
        }
        return result.toString();
    }

    @Override
    public void doLoadVideoData(String videoid) {
        String url = getVideoContentApi(videoid);
        RetrofitFactory.getRetrofit().create(IVideoApi.class).getVideoContent(url)
                .subscribeOn(Schedulers.io())
                .map(videoContentBean -> {
                    VideoContentBean.DataBean.VideoListBean videoList = videoContentBean.getData().getVideo_list();
                    if (videoList.getVideo_3() != null) {
                        String base64 = videoList.getVideo_3().getMain_url();
                        String url1 = (new String(Base64.decode(base64.getBytes(), Base64.DEFAULT)));
                        Log.d(TAG, "getVideoUrls: " + url1);
                        return url1;
                    }

                    if (videoList.getVideo_2() != null) {
                        String base64 = videoList.getVideo_2().getMain_url();
                        String url1 = (new String(Base64.decode(base64.getBytes(), Base64.DEFAULT)));
                        Log.d(TAG, "getVideoUrls: " + url1);
                        return url1;
                    }

                    if (videoList.getVideo_1() != null) {
                        String base64 = videoList.getVideo_1().getMain_url();
                        String url1 = (new String(Base64.decode(base64.getBytes(), Base64.DEFAULT)));
                        Log.d(TAG, "getVideoUrls: " + url1);
                        return url1;
                    }
                    return null;
                })
                .observeOn(AndroidSchedulers.mainThread())
                .as(view.bindAutoDispose())
                .subscribe(s -> {
                    view.onSetVideoPlay(s);
                    view.onHideLoading();
                }, throwable -> {
                    view.onShowNetError();
                    view.onHideLoading();
                    ErrorAction.print(throwable);
                });
    }
}
