package com.he.data.db;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;

import com.he.data.greendao.DaoMaster;
import com.he.data.greendao.DaoSession;


/**
 * 数据库配置类
 * Created by hesh on 2018/02/09.
 */

public class DbConfig {

    private static final String TAG = "DbConfig";

    public static final boolean ISENCRYPTED = true;//数据库加密

    private static String DB_NAME = "";

    private static Context mContext;

    private static DbConfig dbConfig = new DbConfig();//使用饿汉式单例

    private static DaoMaster.DevOpenHelper devOpenHelper;

    private static DaoMaster daoMaster;//在此使用双重锁式单例

    private static DaoSession daoSession;//在此使用双重锁式单例


    public DbConfig() {

    }

    /**
     * 初始化配置
     *
     * @param context
     * @param db_name 数据库名称
     */
    public void init(Context context, String db_name) {
        mContext = context;
        DB_NAME = db_name;
        devOpenHelper = new DaoMaster.DevOpenHelper(mContext, DB_NAME);
    }

    /**
     * 获取DbManager单例
     *
     * @return
     */
    public static DbConfig getInstance() {
        return dbConfig;
    }

    /**
     * 获取可写数据库
     *
     * @return
     */
    private static SQLiteDatabase getWritableDatabase() {
        if (devOpenHelper == null) {
            devOpenHelper = new DaoMaster.DevOpenHelper(mContext, DB_NAME);
        }
        return devOpenHelper.getWritableDatabase();
    }

    /**
     * 获取可读性数据库
     *
     * @return
     */
    private static SQLiteDatabase getReadableDatabase() {
        if (devOpenHelper == null) {
            devOpenHelper = new DaoMaster.DevOpenHelper(mContext, DB_NAME);
        }
        return devOpenHelper.getReadableDatabase();
    }

    /**
     * 获取DaoMaster单例
     *
     * @return
     */
    public static DaoMaster getDaoMaster() {
        if (daoMaster == null) {
            synchronized (DaoMaster.class) {
                if (daoMaster == null) {
                    daoMaster = new DaoMaster(getWritableDatabase());
                }
            }
        }
        return daoMaster;
    }

    /**
     * 获取DaoSession单例
     *
     * @return
     */
    public static DaoSession getDaoSession() {
        if (daoSession == null) {
            synchronized (DaoSession.class) {
                if (daoSession == null) {
                    if (daoMaster == null) {
                        getDaoMaster();
                    }
                    daoSession = daoMaster.newSession();
                }
            }
        }
        return daoSession;
    }

}
