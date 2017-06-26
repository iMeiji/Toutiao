package com.meiji.toutiao.database.table;

/**
 * Created by Meiji on 2017/6/17.
 */

public class SearchHistoryTable {

    /**
     * 浏览记录表
     */
    public static final String TABLENAME = "SearchHistoryTable";

    /**
     * 字段部分
     */
    public static final String ID = "id";
    public static final String KEYWORD = "keyWord";
    public static final String TIME = "time";

    /**
     * 字段ID 数据库操作建立字段对应关系 从0开始
     */
    public static final int ID_ID = 0;
    public static final int ID_KEYWORD = 1;
    public static final int ID_TIME = 2;

    /**
     * 创建表
     */
    public static final String CREATE_TABLE = "create table if not exists " + TABLENAME + "(" +
            ID + " text auto_increment, " +
            KEYWORD + " text primary key, " +
            TIME + " text) ";
}
