package com.meiji.toutiao.other.funny.comment;

import com.meiji.toutiao.bean.other.funny.FunnyCommentBean;

import java.util.List;

/**
 * Created by Meiji on 2017/1/25.
 */

public class IFunnyComment {

    interface View {
        void onRequestData();

        void onSetAdapter(List<FunnyCommentBean.DataBean.CommentsBean> list);

        void onShowRefreshing();

        void onHideRefreshing();

        void onFail();
    }

    interface Presenter {

        void doGetUrl(String group_id, String item_id);

        void doRequestData(String url);

        void doSetAdapter();

        void doRefresh();

        void onFail();
    }

    interface Model {
        boolean requestData(String url);

        List<FunnyCommentBean.DataBean.CommentsBean> getDataList();
    }
}
