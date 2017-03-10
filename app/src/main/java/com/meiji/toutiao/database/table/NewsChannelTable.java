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
    public static final String CHANNEL_ID = "channelId";
    public static final String CHANNEL_NAME = "channelName";
    public static final String CHANNEL_ISENABLE = "channelIsEnable";

    /**
     * 字段ID 数据库操作建立字段对应关系 从0开始
     */
    public static final int ID_CHANNELID = 0;
    public static final int ID_CHANNELNAME = 1;
    public static final int ID_CHANNELISENABLE = 2;

    /**
     * 创建表
     */
    public static final String CREATE_TABLE = "create table if not exists " + TABLENAME + "(" +
            CHANNEL_ID + " text primary key," +
            CHANNEL_NAME + " text, " +
            CHANNEL_ISENABLE + " text default '1' )";
}
