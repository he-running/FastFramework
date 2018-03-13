package com.he.domain.usecase;

import android.util.Log;

import com.he.data.db.DbManager;
import com.he.data.entity.Student;
import com.he.data.executor.PostExecutionThread;
import com.he.data.executor.ThreadExecutor;
import com.he.domain.usecase.base.UseCase;

import javax.inject.Inject;

import io.reactivex.Observable;

/**
 * Created by hesh on 2018/2/24.
 */

public class QueryDataBaseUseCase extends UseCase<Object> {

    @Inject
    DbManager dbManager;


    @Inject
    protected QueryDataBaseUseCase(ThreadExecutor threadExecutor, PostExecutionThread postExecutionThread) {
        super(threadExecutor, postExecutionThread);
    }

    @Override
    public Observable<Object> buildUseCaseObservable() {

        return Observable.create(e -> {

            Log.i("usecase", "thread: "+Thread.currentThread().getName());
            try{
//                List<Student> list=new ArrayList<>();
                for (int i=0;i<10;i++){
                    Student student=new Student();
                    student.setCode(i);
                    student.setName("hello"+i);
                    student.setAge(20+i);
                    student.setSex(i>5? "男":"女");
                    student.setScore(85+i);

//                    list.add(student);
                    dbManager.getStuDaoOpe().saveData(student);
                }
//                dbManager.getStuDaoOpe().saveListData(list);

                e.onNext("success");
                e.onComplete();
            }catch (Exception ex){
                e.onError(ex);
            }
        });
    }
}
