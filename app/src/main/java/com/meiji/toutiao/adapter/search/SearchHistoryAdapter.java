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
import io.reactivex.ObservableOnSubscribe;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;

/**
 * Created by Meiji on 2017/6/17.
 */

public class SearchHistoryAdapter extends ArrayAdapter<SearchHistoryBean> {

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

        ViewHolder viewHolder;
        if (convertView == null) {
            viewHolder = new ViewHolder();
            convertView = inflater.inflate(R.layout.item_search_history, null);
            viewHolder.iv_history = convertView.findViewById(R.id.iv_history);
            viewHolder.tv_keyword = convertView.findViewById(R.id.tv_keyword);
            viewHolder.iv_close = convertView.findViewById(R.id.iv_close);
            convertView.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) convertView.getTag();
        }
        viewHolder.tv_keyword.setText(data.get(position).getKeyWord());
        viewHolder.iv_close.setOnClickListener(v -> Observable
                .create((ObservableOnSubscribe<List<SearchHistoryBean>>) e -> {
                    SearchHistoryDao dao = new SearchHistoryDao();
                    dao.delete(data.get(position).getKeyWord());
                    e.onNext(dao.queryAll());
                })
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(this::updateDataSource, ErrorAction.error()));
        return convertView;
    }

    @Override
    public SearchHistoryBean getItem(int pos) {
        return data.get(pos);
    }

    @Override
    public int getCount() {
        return data.size();
    }

    static class ViewHolder {
        ImageView iv_history;
        TextView tv_keyword;
        ImageView iv_close;
    }
}