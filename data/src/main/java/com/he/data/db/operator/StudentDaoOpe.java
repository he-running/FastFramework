package com.he.data.db.operator;

import com.he.data.db.IEntityDaoOperator;
import com.he.data.entity.Student;
import com.he.data.greendao.StudentDao;

import java.util.ArrayList;
import java.util.List;

import static com.he.data.db.DbConfig.getDaoSession;

/**
 * student表操作类
 * Created by hesh on 2018/02/09.
 */

public class StudentDaoOpe implements IEntityDaoOperator<Student> {

    public StudentDaoOpe(){

    }

    /**
     * 添加单条数据
     * @param stu
     */
    @Override
    public void insertData(Student stu){
        getDaoSession().getStudentDao().insert(stu);
    }

    /**
     * 添加列表数据,建议使用saveListData
     * @param list
     */
    @Override
    public void insertListData(List<Student> list){
        if (list!=null&&list.size()>0){
            getDaoSession().getStudentDao().insertInTx(list);
        }
    }

    /**
     * 保存一条数据，内部代码判断了若已存在则update更新，
     * 否则insert添加
     * @param stu
     */
    @Override
    public void saveData(Student stu){
        getDaoSession().getStudentDao().save(stu);
    }

    /**
     * 保存列表数据，内部代码判断了若已存在则update更新，
     * 否则insert添加
     * @param list
     */
    @Override
    public void saveListData(List<Student> list){
        if (list!=null&&list.size()>0){
            getDaoSession().getStudentDao().saveInTx(list);
        }
    }

    /**
     * 删除具体一条数据
     * @param stu
     */
    @Override
    public void deleteData(Student stu){
        getDaoSession().getStudentDao().delete(stu);
    }

    /**
     * 根据key删除数据
     * @param key
     */
    @Override
    public void deleteDataByKey(long key){
        getDaoSession().getStudentDao().deleteByKey(key);
    }

    /**
     * 删除所有数据
     * @param
     */
    @Override
    public void deleteAllData(){
        getDaoSession().getStudentDao().deleteAll();
    }

    /**
     * 根据id查询，其他字段类似
     * @param id
     * @return
     */
    @Override
    public List<Student> queryDataById(long id){
        List<Student> list=new ArrayList<>();
        list= getDaoSession().getStudentDao().queryBuilder()
                .where(StudentDao.Properties.Id.eq(id)).build().list();
        return list;
    }

    /**
     * 根据多个关键字查询
     * @param
     * @return
     */
    public List<Student> queryDataByKeys(String name,String score){
        List<Student> list=new ArrayList<>();
        list= getDaoSession().getStudentDao().queryBuilder()
                .where(StudentDao.Properties.Name.eq(name),StudentDao.Properties.Score.eq(score)).build().list();
        return list;
    }



    /**
     * 查询所有数据
     * @return
     */
    @Override
    public List<Student> queryAllData(){
        List<Student> list=new ArrayList<>();
        list= getDaoSession().getStudentDao().queryBuilder().build().list();
        return list;
    }

}
