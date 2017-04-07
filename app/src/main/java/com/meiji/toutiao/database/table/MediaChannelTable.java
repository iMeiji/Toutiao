package com.meiji.toutiao.database.table;

/**
 * Created by Meiji on 2017/4/7.
 */

public class MediaChannelTable {

    /**
     * 头条号信息表
     */
    public static final String TABLENAME = "MediaChannelTable";

    /**
     * 字段部分
     */
    public static final String MEDIA_ID = "id";
    public static final String MEDIA_NAME = "name";
    public static final String MEDIA_TYPE = "type";

    /**
     * 字段ID 数据库操作建立字段对应关系 从0开始
     */
    public static final int ID_MEDIAID = 0;
    public static final int ID_MEDIANAME = 0;
    public static final int ID_MEDIATYPE = 0;

    /**
     * 创建表
     */
    public static final String CREATE_TABLE = "create table if not exists " + TABLENAME + "(" +
            MEDIA_ID + " text primary key, " +
            MEDIA_NAME + " text, " +
            MEDIA_TYPE + " text) ";
}
