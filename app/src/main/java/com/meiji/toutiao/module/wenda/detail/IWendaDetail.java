package com.meiji.toutiao.module.wenda.detail;

import com.meiji.toutiao.module.base.IBasePresenter;
import com.meiji.toutiao.module.base.IBaseView;

/**
 * Created by Meiji on 2017/5/23.
 */

public interface IWendaDetail {

    interface View extends IBaseView<Presenter> {

        /**
         * 加载网页
         */
        void onSetWebView(String url, boolean flag);
    }

    interface Presenter extends IBasePresenter {

        /**
         * 请求数据
         */
        void doLoadData(String url);
    }

}
