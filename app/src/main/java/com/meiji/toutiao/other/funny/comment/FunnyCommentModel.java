package com.meiji.toutiao.other.funny.comment;

import android.util.Log;

import com.google.gson.Gson;
import com.google.gson.JsonSyntaxException;
import com.meiji.toutiao.InitApp;
import com.meiji.toutiao.bean.other.funny.FunnyCommentBean;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import okhttp3.Request;
import okhttp3.Response;

/**
 * Created by Meiji on 2017/1/25.
 */

class FunnyCommentModel implements IFunnyComment.Model {

    private static final String TAG = "FunnyCommentModel";
    private Gson gson = new Gson();
    private List<FunnyCommentBean> commentBeanList = new ArrayList<>();
    private List<FunnyCommentBean.DataBean> dataBeanList = new ArrayList<>();
    private List<FunnyCommentBean.DataBean.CommentsBean> commentsBeanList = new ArrayList<>();
    private List<FunnyCommentBean.DataBean.CommentsBean.UserBean> userBeanList = new ArrayList<>();

    FunnyCommentModel() {
    }

    @Override
    public boolean requestData(String url) {
        // 清除旧数据
        if (commentBeanList.size() != 0) {
            commentBeanList.clear();
        }

        System.out.println("newsCommentApi -- " + url);
        boolean flag = false;
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
                FunnyCommentBean bean = gson.fromJson(responseJson, FunnyCommentBean.class);
                commentBeanList.add(bean);
            }
        } catch (IOException | JsonSyntaxException e) {
            e.printStackTrace();
        }
        return flag;
    }

    @Override
    public List<FunnyCommentBean.DataBean.CommentsBean> getDataList() {

        for (FunnyCommentBean newsCommentBean : commentBeanList) {
            for (FunnyCommentBean.DataBean.CommentsBean commentsBean : newsCommentBean.getData().getComments()) {
                commentsBeanList.add(commentsBean);
            }
        }

        return commentsBeanList;
    }
}
