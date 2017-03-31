package com.meiji.toutiao.video.content;

import android.util.Base64;
import android.util.Log;

import com.google.gson.Gson;
import com.google.gson.JsonSyntaxException;
import com.meiji.toutiao.InitApp;
import com.meiji.toutiao.api.VideoApi;
import com.meiji.toutiao.bean.video.VideoContentBean;
import com.meiji.toutiao.news.comment.NewsCommentModel;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import okhttp3.Request;
import okhttp3.Response;

/**
 * Created by Meiji on 2017/3/30.
 */

public class VideoContentModel extends NewsCommentModel implements IVideoContent.Model {

    private static final String TAG = "VideoContentModel";
    private List<VideoContentBean> videoContentBeanList = new ArrayList<>();
    private Gson gson = new Gson();

    @Override
    public boolean requestVideoData(String videoid) {
        boolean flag = false;
        String url = VideoApi.getVideoContentUrl(videoid);
        Log.d(TAG, "requestVideoData: " + url);

        Request request = new Request.Builder()
                .url(url)
                .get()
                .build();
        try {
            Response response = InitApp.getOkHttpClient().newCall(request).execute();
            if (response.isSuccessful()) {
                flag = true;
                String responseJson = response.body().string();
                //String result = ChineseUtil.UnicodeToChs(responseJson);
                Log.d(TAG, "requestData: " + responseJson);
                VideoContentBean bean = gson.fromJson(responseJson, VideoContentBean.class);
                videoContentBeanList.add(bean);
            }
        } catch (IOException | JsonSyntaxException e) {
            e.printStackTrace();
        }
        return flag;
    }

    @Override
    public String getVideoUrl() {
        try {
            int total = videoContentBeanList.get(0).getTotal();
            if (total != 0) {
                String url;
                VideoContentBean.DataBean.VideoListBean video_list = videoContentBeanList.get(0).getData().getVideo_list();

                if (video_list.getVideo_3() != null) {
                    String base64 = video_list.getVideo_3().getMain_url();
                    url = (new String(Base64.decode(base64.getBytes(), Base64.DEFAULT)));
                    Log.d(TAG, "getVideoUrls: " + url);
                    return url;
                }

                if (video_list.getVideo_2() != null) {
                    String base64 = video_list.getVideo_2().getMain_url();
                    url = (new String(Base64.decode(base64.getBytes(), Base64.DEFAULT)));
                    Log.d(TAG, "getVideoUrls: " + url);
                    return url;
                }

                if (video_list.getVideo_1() != null) {
                    String base64 = video_list.getVideo_1().getMain_url();
                    url = (new String(Base64.decode(base64.getBytes(), Base64.DEFAULT)));
                    Log.d(TAG, "getVideoUrls: " + url);
                    return url;
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }
}
