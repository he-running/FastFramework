package com.he.data.db;

import android.content.Context;

import com.he.data.db.operator.StudentDaoOpe;

import javax.inject.Inject;
import javax.inject.Singleton;

/**
 * 数据表管理类，用于获取所有的operator
 * Created by hesh on 2018/2/9.
 */
@Singleton
public class DbManager {

    private Context context;

    private DbConfig dbConfig;

    private StudentDaoOpe studentDaoOpe;

    @Inject
    public DbManager(Context context){
        this.context=context;
        dbConfig=DbConfig.getInstance();
        initOperator();
    }

    private void initOperator(){
        studentDaoOpe=new StudentDaoOpe();
    }

    /**
     * 获取student数据表的操作类
     * @return
     */
    public StudentDaoOpe getStuDaoOpe(){
        return studentDaoOpe;
    }

}
