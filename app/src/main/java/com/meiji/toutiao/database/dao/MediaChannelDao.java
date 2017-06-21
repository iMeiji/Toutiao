package com.meiji.toutiao.database.dao;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.meiji.toutiao.bean.media.MediaChannelBean;
import com.meiji.toutiao.database.DatabaseHelper;
import com.meiji.toutiao.database.table.MediaChannelTable;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Meiji on 2017/4/7.
 */

public class MediaChannelDao {

    private SQLiteDatabase db;

    public MediaChannelDao() {
        this.db = DatabaseHelper.getDatabase();
    }

    public void initData() {
        add("4377795668", "新华网", "http://p2.pstatp.com/large/3658/7378365093", "news",
                "", "传播中国，报道世界；权威声音，亲切表达。", "http://toutiao.com/m4377795668/");
        add("52445544609", "互联网的这点事", "http://p3.pstatp.com/large/ef300164e786ff295da", "news",
                "", "每天为你速递最新、最鲜、最有料的互联网科技资讯！", "http://toutiao.com/m52445544609/");
    }

    public boolean add(String id,
                       String name,
                       String avatar,
                       String type,
                       String followCount,
                       String descText,
                       String url) {
        ContentValues values = new ContentValues();
        values.put(MediaChannelTable.ID, id);
        values.put(MediaChannelTable.NAME, name);
        values.put(MediaChannelTable.AVATAR, avatar);
        values.put(MediaChannelTable.TYPE, type);
        values.put(MediaChannelTable.FOLLOWCOUNT, followCount);
        values.put(MediaChannelTable.DESCTEXT, descText);
        values.put(MediaChannelTable.URL, url);
        long result = db.insert(MediaChannelTable.TABLENAME, null, values);
        return result != -1;
    }

    public List<MediaChannelBean> queryAll() {
        Cursor cursor = db.query(MediaChannelTable.TABLENAME, null, null, null, null, null, null);
        List<MediaChannelBean> list = new ArrayList<>();
        while (cursor.moveToNext()) {
            MediaChannelBean bean = new MediaChannelBean();
            bean.setId(cursor.getString(MediaChannelTable.ID_ID));
            bean.setName(cursor.getString(MediaChannelTable.ID_NAME));
            bean.setAvatar(cursor.getString(MediaChannelTable.ID_AVATAR));
            bean.setType(cursor.getString(MediaChannelTable.ID_TYPE));
            bean.setFollowCount(cursor.getString(MediaChannelTable.ID_FOLLOWCOUNT));
            bean.setDescText(cursor.getString(MediaChannelTable.ID_DESCTEXT));
            bean.setUrl(cursor.getString(MediaChannelTable.ID_URL));
            list.add(bean);
        }
        cursor.close();
        return list;
    }

    public boolean queryIsExist(String id) {
        Cursor cursor = db.query(MediaChannelTable.TABLENAME, null, MediaChannelTable.ID + "=?", new String[]{id}, null, null, null);
        if (cursor.moveToNext()) {
            cursor.close();
            return true;
        }
        cursor.close();
        return false;
    }

    public boolean delete(String mediaId) {
        int id = db.delete(MediaChannelTable.TABLENAME, MediaChannelTable.ID + "=?", new String[]{mediaId});
        return id != -1;
    }
}
