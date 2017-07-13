package com.meiji.toutiao.adapter.search;

import android.content.Context;
import android.support.annotation.NonNull;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.meiji.toutiao.bean.search.SearchSuggestionBean;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Meiji on 2017/6/17.
 */

public class SearchSuggestionAdapter extends ArrayAdapter<SearchSuggestionBean.DataBean> {

    private static final String TAG = "SearchSuggestionAdapter";
    private LayoutInflater inflater;
    private List<SearchSuggestionBean.DataBean> data;

    public SearchSuggestionAdapter(Context context, int resource) {
        super(context, resource);
        inflater = LayoutInflater.from(context);
        data = new ArrayList<>();
    }

    // 更新数据并notifyDataSetChanged
    public void updateDataSource(@NonNull List<SearchSuggestionBean.DataBean> data) {
        this.data = data;
        this.notifyDataSetChanged();
    }

    @NonNull
    @Override
    public View getView(final int position, View convertView, @NonNull ViewGroup parent) {
        ViewHolder viewHolder;
        if (convertView == null) {
            viewHolder = new ViewHolder();
            convertView = inflater.inflate(android.R.layout.simple_list_item_1, null);
            viewHolder.tv_keyword = convertView.findViewById(android.R.id.text1);
            convertView.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) convertView.getTag();
        }
        viewHolder.tv_keyword.setText(data.get(position).getKeyword());
        return convertView;
    }

    @Override
    public SearchSuggestionBean.DataBean getItem(int pos) {
        return data.get(pos);
    }

    @Override
    public int getCount() {
        return data.size();
    }

    static class ViewHolder {
        TextView tv_keyword;
    }
}