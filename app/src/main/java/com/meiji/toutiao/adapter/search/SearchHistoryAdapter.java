package com.meiji.toutiao.adapter.search;

import android.content.Context;
import android.support.annotation.NonNull;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.meiji.toutiao.ErrorAction;
import com.meiji.toutiao.R;
import com.meiji.toutiao.bean.search.SearchHistoryBean;
import com.meiji.toutiao.database.dao.SearchHistoryDao;

import java.util.ArrayList;
import java.util.List;

import io.reactivex.Observable;
import io.reactivex.ObservableEmitter;
import io.reactivex.ObservableOnSubscribe;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.functions.Consumer;
import io.reactivex.schedulers.Schedulers;

/**
 * Created by Meiji on 2017/6/17.
 */

public class SearchHistoryAdapter extends ArrayAdapter<SearchHistoryBean> {

    public static final int TYPE_SUG = 0;
    public static final int TYPE_HIS = 1;
    private static final String TAG = "SearchHistoryAdapter";
    private LayoutInflater inflater;
    private List<SearchHistoryBean> data;

    public SearchHistoryAdapter(Context context, int resource) {
        super(context, resource);
        inflater = LayoutInflater.from(context);
        data = new ArrayList<>();
    }

    // 更新数据并notifyDataSetChanged
    public void updateDataSource(@NonNull List<SearchHistoryBean> data) {
        this.data = data;
        this.notifyDataSetChanged();
    }

    @NonNull
    @Override
    public View getView(final int position, View convertView, @NonNull ViewGroup parent) {
        int itemViewType = getItemViewType(position);
        switch (itemViewType) {
            case TYPE_HIS:
                ViewHolder viewHolder = new ViewHolder();
                convertView = inflater.inflate(R.layout.item_search_history, null);
                viewHolder.iv_history = (ImageView) convertView.findViewById(R.id.iv_history);
                viewHolder.tv_keyword = (TextView) convertView.findViewById(R.id.tv_keyword);
                viewHolder.iv_close = (ImageView) convertView.findViewById(R.id.iv_close);
                viewHolder.iv_close.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Observable
                                .create(new ObservableOnSubscribe<List<SearchHistoryBean>>() {
                                    @Override
                                    public void subscribe(@io.reactivex.annotations.NonNull ObservableEmitter<List<SearchHistoryBean>> e) throws Exception {
                                        SearchHistoryDao dao = new SearchHistoryDao();
                                        dao.delete(data.get(position).getKeyWord());
                                        e.onNext(dao.query());
                                    }
                                })
                                .subscribeOn(Schedulers.io())
                                .observeOn(AndroidSchedulers.mainThread())
                                .subscribe(new Consumer<List<SearchHistoryBean>>() {
                                    @Override
                                    public void accept(@io.reactivex.annotations.NonNull List<SearchHistoryBean> list) throws Exception {
                                        updateDataSource(list);
                                    }
                                }, ErrorAction.error());
                    }
                });
                viewHolder.tv_keyword.setText(data.get(position).getKeyWord());
                return convertView;
            case TYPE_SUG:
                convertView = inflater.inflate(android.R.layout.simple_list_item_1, null);
                TextView tv_keyword = (TextView) convertView.findViewById(android.R.id.text1);
                tv_keyword.setText(data.get(position).getKeyWord());
                return convertView;
        }
        return null;
    }

    @Override
    public SearchHistoryBean getItem(int pos) {
        return data.get(pos);
    }

    @Override
    public int getCount() {
        return data.size();
    }

    @Override
    public int getItemViewType(int position) {
        return data.get(position).getType();
    }

    public static class ViewHolder {
        ImageView iv_history;
        TextView tv_keyword;
        ImageView iv_close;
    }
}