package com.meiji.toutiao.module.news.content;

import android.text.TextUtils;
import android.util.Log;
import android.webkit.JavascriptInterface;

import com.meiji.toutiao.ErrorAction;
import com.meiji.toutiao.ImageBrowserActivity;
import com.meiji.toutiao.InitApp;
import com.meiji.toutiao.api.INewsApi;
import com.meiji.toutiao.bean.news.MultiNewsArticleDataBean;
import com.meiji.toutiao.bean.news.NewsContentBean;
import com.meiji.toutiao.util.RetrofitFactory;
import com.meiji.toutiao.util.SettingUtil;

import java.util.ArrayList;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import io.reactivex.Observable;
import io.reactivex.ObservableOnSubscribe;
import io.reactivex.ObservableSource;
import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.annotations.NonNull;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Function;
import io.reactivex.schedulers.Schedulers;

/**
 * Created by Meiji on 2016/12/17.
 */

class NewsContentPresenter implements INewsContent.Presenter {

    private static final String TAG = "NewsContentPresenter";
    // 获取img标签正则
    private static final String IMAGE_URL_REGEX = "<img[^>]+src\\s*=\\s*['\"]([^'\"]+)['\"][^>]*>";
    private INewsContent.View view;
    private String groupId;
    private String itemId;
    private String html;

    NewsContentPresenter(INewsContent.View view) {
        this.view = view;
    }

    @Override
    public void doLoadData(MultiNewsArticleDataBean dataBean) {
        groupId = dataBean.getGroup_id() + "";
        itemId = dataBean.getItem_id() + "";
        final String url = dataBean.getDisplay_url();

        Observable
                .create((ObservableOnSubscribe<String>) e -> {
                    // https://toutiao.com/group/6530252650288513540/
                    // https://m.toutiao.com/i6530252650288513540/info/
                    String api = url.replace("www.", "")
                            .replace("toutiao", "m.toutiao")
                            .replace("group/", "i") + "info/";
                    e.onNext(api);
//                        try {
//                            Response<ResponseBody> response = RetrofitFactory.getRetrofit().create(INewsApi.class)
//                                    .getNewsContentRedirectUrl(url).execute();
//                            // 获取重定向后的 URL 用于拼凑API
//                            if (response.isSuccessful()) {
//                                String httpUrl = response.raw().request().url().toString();
//                                if (!TextUtils.isEmpty(httpUrl) && httpUrl.contains("toutiao")) {
//                                    String api = httpUrl + "info/";
//                                    e.onNext(api);
//                                } else {
//                                    e.onError(new Throwable());
//                                }
//                            } else {
//                                e.onError(new Throwable());
//                            }

//                        } catch (Exception e1) {
//                            e.onComplete();
//                            ErrorAction.print(e1);
//                        }
                })
                .subscribeOn(Schedulers.io())
                .switchMap((Function<String, ObservableSource<NewsContentBean>>) s -> RetrofitFactory.getRetrofit().create(INewsApi.class).getNewsContent(s))
                .map(this::getHTML)
                .observeOn(AndroidSchedulers.mainThread())
                .as(view.bindAutoDispose())
                .subscribe(new Observer<String>() {
                    @Override
                    public void onSubscribe(@NonNull Disposable d) {

                    }

                    @Override
                    public void onNext(@NonNull String s) {
                        view.onSetWebView(s, true);
                    }

                    @Override
                    public void onError(@NonNull Throwable e) {
                        view.onSetWebView(null, false);
                        ErrorAction.print(e);
                    }

                    @Override
                    public void onComplete() {
                        doShowNetError();
                    }
                });
    }

    private String getHTML(NewsContentBean bean) {
        String title = bean.getData().getTitle();
        String content = bean.getData().getContent();
        if (content != null) {

            String css = "<link rel=\"stylesheet\" href=\"file:///android_asset/toutiao_light.css\" type=\"text/css\">";
            if (SettingUtil.getInstance().getIsNightMode()) {
                css = css.replace("toutiao_light", "toutiao_dark");
            }

            html = "<!DOCTYPE html>\n" +
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
    public void doRefresh() {

    }

    @Override
    public void doShowNetError() {
        view.onHideLoading();
        view.onShowNetError();
    }

    /**
     * js 通信接口，定义供 JavaScript 调用的交互接口
     * 点击图片启动新的 Activity，并传入点击图片对应的 url 和页面所有图片
     * 对应的 url
     *
     * @param url 点击图片对应的 url
     */
    @JavascriptInterface
    public void openImage(String url) {
        if (!TextUtils.isEmpty(url)) {
            ArrayList<String> list = getAllImageUrlFromHtml(html);
            if (list.size() > 0) {
                ImageBrowserActivity.start(InitApp.AppContext, url, list);
                Log.d(TAG, "openImage: " + list.toString());
            }
        }
    }

    /***
     * 获取页面所有图片对应的地址对象，
     * 例如 <img src="http://sc1.hao123img.com/data/f44d0aab7bc35b8767de3c48706d429e" />
     */
    private ArrayList<String> getAllImageUrlFromHtml(String html) {
        Matcher matcher = Pattern.compile(IMAGE_URL_REGEX).matcher(html);
        ArrayList<String> imgUrlList = new ArrayList<>();
        while (matcher.find()) {
            int count = matcher.groupCount();
            if (count >= 1) {
                imgUrlList.add(matcher.group(1));
            }
        }
        return imgUrlList;
    }
}
