package com.meiji.toutiao.database.dao;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.meiji.toutiao.InitApp;
import com.meiji.toutiao.R;
import com.meiji.toutiao.bean.news.NewsChannelBean;
import com.meiji.toutiao.database.DatabaseHelper;
import com.meiji.toutiao.database.table.NewsChannelTable;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Meiji on 2017/3/10.
 */

public class NewsChannelDao {

    private SQLiteDatabase db;

    public NewsChannelDao() {
        this.db = DatabaseHelper.getDatabase();
    }

    public void addInitData() {
        String categoryId[] = InitApp.AppContext.getResources().getStringArray(R.array.news_id);
        String categoryName[] = InitApp.AppContext.getResources().getStringArray(R.array.news_name);
        for (int i = 0; i < categoryId.length; i++) {
            add(categoryId[i], categoryName[i], 1);
        }
    }

    public boolean add(String channelId, String channelName, int isEnable) {
        ContentValues values = new ContentValues();
        values.put(NewsChannelTable.CHANNEL_ID, channelId);
        values.put(NewsChannelTable.CHANNEL_NAME, channelName);
        values.put(NewsChannelTable.CHANNEL_ISENABLE, isEnable);
        long result = db.insert(NewsChannelTable.TABLENAME, null, values);
        return result != -1;
    }

    public List<NewsChannelBean> query(int isEnable) {
        Cursor cursor = db.query(NewsChannelTable.TABLENAME, null, NewsChannelTable.CHANNEL_ISENABLE, new String[]{isEnable + ""}, null, null, null);
        List<NewsChannelBean> list = new ArrayList<>();
        while (cursor.moveToNext()) {
            NewsChannelBean bean = new NewsChannelBean();
            bean.setChannelId(cursor.getString(NewsChannelTable.ID_CHANNELID));
            bean.setChannelName(cursor.getString(NewsChannelTable.ID_CHANNELNAME));
            bean.setIsEnable(cursor.getInt(NewsChannelTable.ID_CHANNELISENABLE));
            list.add(bean);
        }
        cursor.close();
        return list;
    }

    public List<NewsChannelBean> queryAll() {
        Cursor cursor = db.query(NewsChannelTable.TABLENAME, null, null, null, null, null, null);
        List<NewsChannelBean> list = new ArrayList<>();
        while (cursor.moveToNext()) {
            NewsChannelBean bean = new NewsChannelBean();
            bean.setChannelId(cursor.getString(NewsChannelTable.ID_CHANNELID));
            bean.setChannelName(cursor.getString(NewsChannelTable.ID_CHANNELNAME));
            bean.setIsEnable(cursor.getInt(NewsChannelTable.ID_CHANNELISENABLE));
            list.add(bean);
        }
        cursor.close();
        return list;
    }

    public boolean removeAll() {
        int result = db.delete(NewsChannelTable.TABLENAME, null, null);
        return result != -1;
    }
}
