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

    public boolean add(String mediaId, String mediaName, String mediaType) {
        ContentValues values = new ContentValues();
        values.put(MediaChannelTable.MEDIA_ID, mediaId);
        values.put(MediaChannelTable.MEDIA_NAME, mediaName);
        values.put(MediaChannelTable.MEDIA_TYPE, mediaType);
        long result = db.insert(MediaChannelTable.TABLENAME, null, values);
        return result != -1;
    }

    public List<MediaChannelBean> queryAll() {
        Cursor cursor = db.query(MediaChannelTable.TABLENAME, null, null, null, null, null, null);
        List<MediaChannelBean> list = new ArrayList<>();
        while (cursor.moveToNext()) {
            MediaChannelBean bean = new MediaChannelBean();
            bean.setMediaId(cursor.getString(MediaChannelTable.ID_MEDIAID));
            bean.setMediaName(cursor.getString(MediaChannelTable.ID_MEDIANAME));
            bean.setMediaType(cursor.getString(MediaChannelTable.ID_MEDIATYPE));
            list.add(bean);
        }
        cursor.close();
        return list;
    }
}
