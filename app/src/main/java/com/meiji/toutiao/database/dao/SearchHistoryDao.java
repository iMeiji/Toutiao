package com.meiji.toutiao.database.dao;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.meiji.toutiao.adapter.search.SearchHistoryAdapter;
import com.meiji.toutiao.bean.search.SearchHistoryBean;
import com.meiji.toutiao.database.DatabaseHelper;
import com.meiji.toutiao.database.table.SearchHistoryTable;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * Created by Meiji on 2017/6/17.
 */

public class SearchHistoryDao {

    private SQLiteDatabase db;

    public SearchHistoryDao() {
        this.db = DatabaseHelper.getDatabase();
    }

    public boolean add(String keyWord) {
        ContentValues values = new ContentValues();
        values.put(SearchHistoryTable.KEYWORD, keyWord);
        values.put(SearchHistoryTable.TIME, new Date(System.currentTimeMillis()).getTime() / 1000);
        values.put(SearchHistoryTable.TYPE, SearchHistoryAdapter.TYPE_HIS);
        long result = db.insert(SearchHistoryTable.TABLENAME, null, values);
        return result != -1;
    }

    public List<SearchHistoryBean> query() {
        Cursor cursor = db.query(SearchHistoryTable.TABLENAME, null, null, null, null, null, SearchHistoryTable.TIME + " desc");
        List<SearchHistoryBean> list = new ArrayList<>();
        while (cursor.moveToNext()) {
            SearchHistoryBean bean = new SearchHistoryBean();
            bean.setKeyWord(cursor.getString(SearchHistoryTable.ID_KEYWORD));
            bean.setTime(cursor.getString(SearchHistoryTable.ID_TIME));
            bean.setType(Integer.parseInt(cursor.getString(SearchHistoryTable.ID_TYPE)));
            list.add(bean);
        }
        cursor.close();
        return list;
    }

    public boolean delete(String keyWord) {
        int id = db.delete(SearchHistoryTable.TABLENAME, SearchHistoryTable.KEYWORD + "=?", new String[]{keyWord});
        return id != -1;
    }
}
