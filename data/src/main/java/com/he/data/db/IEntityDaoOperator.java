package com.he.data.db;

import java.util.List;

/**
 * 实体类表的操作类
 * Created by hesh on 2018/02/09.
 */

public abstract interface IEntityDaoOperator<T> {

    /**
     * 添加单条数据
     *
     * @param
     */
    public abstract void insertData(T t);

    /**
     * 添加列表数据
     *
     * @param list
     */
    public abstract void insertListData(List<T> list);

    /**
     * 保存一条数据，内部代码判断了若已存在则update更新，
     * 否则insert添加
     *
     * @param t
     */
    public abstract void saveData(T t);

    /**
     * 保存列表数据，内部代码判断了若已存在则update更新，
     * 否则insert添加
     *
     * @param list
     */
    public abstract void saveListData(List<T> list);

    /**
     * 删除具体一条数据
     *
     * @param t
     */
    public abstract void deleteData(T t);

    /**
     * 根据key删除数据
     *
     * @param key
     */
    public abstract void deleteDataByKey(long key);

    /**
     * 删除所有数据
     *
     * @param
     */
    public abstract void deleteAllData();

    /**
     * 根据id查询，其他字段类似
     *
     * @param id
     * @return
     */
    public abstract List<T> queryDataById(long id);

    /**
     * 查询所有数据
     *
     * @return
     */
    public abstract List<T> queryAllData();

}
