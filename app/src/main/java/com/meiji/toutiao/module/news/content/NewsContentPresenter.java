package com.meiji.toutiao.module.news.content;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;

import com.meiji.toutiao.R;
import com.meiji.toutiao.RetrofitFactory;
import com.meiji.toutiao.api.INewsApi;
import com.meiji.toutiao.bean.news.NewsArticleBean;
import com.meiji.toutiao.bean.news.NewsContentBean;
import com.meiji.toutiao.module.news.comment.NewsCommentFragment;
import com.meiji.toutiao.utils.SettingsUtil;

import io.reactivex.Observable;
import io.reactivex.ObservableEmitter;
import io.reactivex.ObservableOnSubscribe;
import io.reactivex.ObservableSource;
import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.annotations.NonNull;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Function;
import io.reactivex.schedulers.Schedulers;
import okhttp3.HttpUrl;
import okhttp3.ResponseBody;
import retrofit2.Response;

/**
 * Created by Meiji on 2016/12/17.
 */

class NewsContentPresenter implements INewsContent.Presenter {

    private static final String TAG = "NewsContentPresenter";
    private INewsContent.View view;
    private String group_id;
    private String item_id;

    NewsContentPresenter(INewsContent.View view) {
        this.view = view;
    }

    @Override
    public void doLoadData(NewsArticleBean.DataBean dataBean) {
        group_id = dataBean.getGroup_id() + "";
        item_id = dataBean.getItem_id() + "";
        final String url = dataBean.getDisplay_url();

        Observable
                .create(new ObservableOnSubscribe<String>() {
                    @Override
                    public void subscribe(@NonNull ObservableEmitter<String> e) throws Exception {
                        Response<ResponseBody> response = RetrofitFactory.getRetrofit().create(INewsApi.class)
                                .getNewsContentRedirectUrl(url).execute();
                        // 获取重定向后的 URL 用于拼凑API
                        if (response.isSuccessful()) {
                            HttpUrl httpUrl = response.raw().request().url();
                            String api = httpUrl + "info";
                            e.onNext(api);
                        } else {
                            e.onComplete();
                        }
                    }
                })
                .subscribeOn(Schedulers.io())
                .observeOn(Schedulers.io())
                .flatMap(new Function<String, ObservableSource<NewsContentBean>>() {
                    @Override
                    public ObservableSource<NewsContentBean> apply(@NonNull String s) throws Exception {
                        return RetrofitFactory.getRetrofit().create(INewsApi.class).getNewsContent(s);
                    }
                })
                .map(new Function<NewsContentBean, String>() {
                    @Override
                    public String apply(@NonNull NewsContentBean bean) throws Exception {
                        return getHTML(bean);
                    }
                })
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<String>() {
                    @Override
                    public void onSubscribe(@NonNull Disposable d) {
                        view.onShowLoading();
                    }

                    @Override
                    public void onNext(@NonNull String s) {
                        view.onSetWebView(s, true);
                    }

                    @Override
                    public void onError(@NonNull Throwable e) {
                        view.onSetWebView(null, false);
                    }

                    @Override
                    public void onComplete() {
                        view.onShowNetError();
                    }
                });
    }

    private String getHTML(NewsContentBean bean) {
        String title = bean.getData().getTitle();
        String content = bean.getData().getContent();
        if (content != null) {

            String css = "<link rel=\"stylesheet\" href=\"file:///android_asset/toutiao_light.css\" type=\"text/css\">";
            if (SettingsUtil.getInstance().getIsNightMode()) {
                css = css.replace("toutiao_light", "toutiao_dark");
            }

            String html = "<!DOCTYPE html>\n" +
                    "<html lang=\"en\">\n" +
                    "<head>\n" +
                    "    <meta charset=\"UTF-8\">" +
                    css +
                    "<body>\n" +
                    "<article class=\"article-container\">\n" +
                    "    <div class=\"article__content article-content\">" +
                    "<h1 class=\"article-title\">" +
                    title +
                    "</h1>" +
                    content +
                    "    </div>\n" +
                    "</article>\n" +
                    "</body>\n" +
                    "</html>";

            return html;
        } else {
            return null;
        }
    }

    @Override
    public void doShowComment(FragmentActivity context, Fragment fragment) {
        context.getSupportFragmentManager().beginTransaction()
                .add(R.id.container, NewsCommentFragment.newInstance(group_id, item_id), NewsCommentFragment.class.getName())
                .addToBackStack(NewsCommentFragment.class.getName())
                .hide(fragment)
                .commit();
    }
}
