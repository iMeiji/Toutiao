package com.meiji.toutiao.database.table;

/**
 * Created by Meiji on 2017/3/10.
 */

public class NewsChannelTable {

    /**
     * 新闻频道信息表
     */
    public static final String TABLENAME = "NewsChannelTable";

    /**
     * 字段部分
     */
    public static final String ID = "id";
    public static final String NAME = "name";
    public static final String IS_ENABLE = "isEnable";
    public static final String POSITION = "position";

    /**
     * 字段ID 数据库操作建立字段对应关系 从0开始
     */
    public static final int ID_ID = 0;
    public static final int ID_NAME = 1;
    public static final int ID_ISENABLE = 2;
    public static final int ID_POSITION = 3;

    /**
     * 创建表
     */
    public static final String CREATE_TABLE = "create table if not exists " + TABLENAME + "(" +
            ID + " text primary key, " +
            NAME + " text, " +
            IS_ENABLE + " text default '1', " +
            POSITION + " text) ";
}
