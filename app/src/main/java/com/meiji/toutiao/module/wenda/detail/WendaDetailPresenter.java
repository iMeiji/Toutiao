package com.meiji.toutiao.module.wenda.detail;

import com.meiji.toutiao.RetrofitFactory;
import com.meiji.toutiao.api.IMobileWendaApi;
import com.meiji.toutiao.utils.SettingsUtil;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.annotations.NonNull;
import io.reactivex.functions.Consumer;
import io.reactivex.schedulers.Schedulers;
import okhttp3.ResponseBody;

/**
 * Created by Meiji on 2017/5/23.
 */

public class WendaDetailPresenter implements IWendaDetail.Presenter {

    private IWendaDetail.View view;

    WendaDetailPresenter(IWendaDetail.View view) {
        this.view = view;
    }

    @Override
    public void doRefresh() {

    }

    @Override
    public void doShowNetError() {

    }

    @Override
    public void doLoadData(String url) {
        RetrofitFactory.getRetrofit().create(IMobileWendaApi.class)
                .getWendaAnsDetail(url)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Consumer<ResponseBody>() {
                    @Override
                    public void accept(@NonNull ResponseBody responseBody) throws Exception {
                        String result = getHTML(responseBody.string());
                        if (result != null) {
                            view.onSetWebView(result, true);
                        } else {
                            view.onSetWebView(null, false);
                        }
                    }
                }, new Consumer<Throwable>() {
                    @Override
                    public void accept(@NonNull Throwable throwable) throws Exception {
                        throwable.printStackTrace();
                        view.onSetWebView(null, false);
                    }
                });
    }

    private String getHTML(String response) {
        Document doc = Jsoup.parse(response, "UTF-8");
        Elements elements = doc.getElementsByClass("con-words");
        String content = null;
        for (Element element : elements) {
            content = element.toString();
            break;
        }
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
}
