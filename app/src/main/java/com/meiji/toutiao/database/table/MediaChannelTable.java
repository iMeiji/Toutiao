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
    public static final String ID = "id";
    public static final String NAME = "name";
    public static final String AVATAR = "avatar";
    public static final String TYPE = "type";
    public static final String FOLLOWCOUNT = "followCount";
    public static final String DESCTEXT = "descText";
    public static final String URL = "url";

    /**
     * 字段ID 数据库操作建立字段对应关系 从0开始
     */
    public static final int ID_ID = 0;
    public static final int ID_NAME = 1;
    public static final int ID_AVATAR = 2;
    public static final int ID_TYPE = 3;
    public static final int ID_FOLLOWCOUNT = 4;
    public static final int ID_DESCTEXT = 5;
    public static final int ID_URL = 6;

    /**
     * 创建表
     */
    public static final String CREATE_TABLE = "create table if not exists " + TABLENAME + "(" +
            ID + " text primary key, " +
            NAME + " text, " +
            AVATAR + " text, " +
            TYPE + " text, " +
            FOLLOWCOUNT + " text, " +
            DESCTEXT + " text, " +
            URL + " text) ";
}
