package com.meiji.toutiao.module.search.result;

import android.text.TextUtils;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonDeserializer;
import com.google.gson.JsonElement;
import com.google.gson.JsonParseException;
import com.meiji.toutiao.ErrorAction;
import com.meiji.toutiao.RetrofitFactory;
import com.meiji.toutiao.api.IMobileSearchApi;
import com.meiji.toutiao.bean.news.MultiNewsArticleDataBean;
import com.meiji.toutiao.bean.search.SearchResultBean;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

import io.reactivex.Observable;
import io.reactivex.ObservableSource;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.annotations.NonNull;
import io.reactivex.functions.Consumer;
import io.reactivex.functions.Function;
import io.reactivex.functions.Predicate;
import io.reactivex.schedulers.Schedulers;
import okhttp3.ResponseBody;

/**
 * Created by Meiji on 2017/2/7.
 */

class SearchResultPresenter implements ISearchResult.Presenter {

    private static final String TAG = "SearchResultPresenter";
    private int offset = 0;
    private String query;
    private String curTab;
    private ISearchResult.View view;
    private List<MultiNewsArticleDataBean> list = new ArrayList<>();
    private Gson gson = new GsonBuilder().registerTypeHierarchyAdapter(SearchResultBean.DataBeanX.MiddleImageBean.class,
            new JsonDeserializer<SearchResultBean.DataBeanX.MiddleImageBean>() {
                @Override
                public SearchResultBean.DataBeanX.MiddleImageBean deserialize(JsonElement json, Type typeOfT, JsonDeserializationContext context) throws JsonParseException {
                    if (json.isJsonObject()) {
                        Gson newGson = new Gson();
                        return newGson.fromJson(json, typeOfT);
                    } else {
                        SearchResultBean.DataBeanX.MiddleImageBean bean = new SearchResultBean.DataBeanX.MiddleImageBean();
                        bean.setUrl(json.getAsString());
                        return bean;
                    }
                }
            }).create();

    SearchResultPresenter(ISearchResult.View view) {
        this.view = view;
    }

    public void doLoadData(String... parameter) {

        try {
            if (null == this.query) {
                this.query = parameter[0];
                this.curTab = parameter[1];
            }
        } catch (Exception e) {
            ErrorAction.print(e);
        }

        RetrofitFactory.getRetrofit().create(IMobileSearchApi.class)
                .getSearchResult2(this.query, curTab, offset)
                .subscribeOn(Schedulers.io())
                .observeOn(Schedulers.io())
                .flatMap(new Function<ResponseBody, ObservableSource<SearchResultBean.DataBeanX>>() {
                    @Override
                    public ObservableSource<SearchResultBean.DataBeanX> apply(@NonNull ResponseBody responseBody) throws Exception {
                        String json = responseBody.string();
                        SearchResultBean searchResultBean = gson.fromJson(json, SearchResultBean.class);
                        return Observable.fromIterable(searchResultBean.getData());
                    }
                })
                .filter(new Predicate<SearchResultBean.DataBeanX>() {
                    @Override
                    public boolean test(@NonNull SearchResultBean.DataBeanX dataBeanX) throws Exception {
                        return !TextUtils.isEmpty(dataBeanX.getTitle());
                    }
                })
                .map(new Function<SearchResultBean.DataBeanX, MultiNewsArticleDataBean>() {
                    @Override
                    public MultiNewsArticleDataBean apply(@NonNull SearchResultBean.DataBeanX dataBeanX) throws Exception {
                        String json = gson.toJson(dataBeanX);
                        MultiNewsArticleDataBean bean = gson.fromJson(json, MultiNewsArticleDataBean.class);
                        MultiNewsArticleDataBean.MediaInfoBean mediaInfo = new MultiNewsArticleDataBean.MediaInfoBean();
                        mediaInfo.setMedia_id(dataBeanX.getMedia_url());
                        bean.setMedia_info(mediaInfo);
                        return bean;
                    }
                })
                .toList()
                .compose(view.<List<MultiNewsArticleDataBean>>bindToLife())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Consumer<List<MultiNewsArticleDataBean>>() {
                    @Override
                    public void accept(@NonNull List<MultiNewsArticleDataBean> list) throws Exception {
                        doSetAdapter(list);
                    }
                }, new Consumer<Throwable>() {
                    @Override
                    public void accept(@NonNull Throwable throwable) throws Exception {
                        ErrorAction.print(throwable);
                        doShowNetError();
                    }
                });
    }

    @Override
    public void doLoadMoreData() {
        offset += 20;
        doLoadData();
    }

    @Override
    public void doSetAdapter(List<MultiNewsArticleDataBean> dataList) {
        list.addAll(dataList);
        view.onSetAdapter(list);
        view.onHideLoading();
    }

    @Override
    public void doRefresh() {
        if (list.size() != 0) {
            list.clear();
            offset = 0;
        }
        doLoadData();
    }

    @Override
    public void doShowNetError() {
        view.onHideLoading();
        view.onShowNetError();
    }
}
