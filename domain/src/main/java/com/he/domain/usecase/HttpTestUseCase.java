package com.he.domain.usecase;

import android.util.Log;

import com.he.data.executor.PostExecutionThread;
import com.he.data.executor.ThreadExecutor;
import com.he.domain.http.BaseObserver;
import com.he.domain.http.RetrofitFactory;
import com.he.domain.http.bean.BaseEntity;
import com.he.domain.http.bean.TranslateEntity;
import com.he.domain.usecase.base.UseCase;

import javax.inject.Inject;

import io.reactivex.Observable;

/**
 * 测试样例
 * Created by hesh on 2018/2/27.
 */

public class HttpTestUseCase extends UseCase<String>{


    @Inject
    protected HttpTestUseCase(ThreadExecutor threadExecutor, PostExecutionThread postExecutionThread) {
        super(threadExecutor, postExecutionThread);
    }

    @Override
    public Observable<String> buildUseCaseObservable() {
        return Observable.create(emitter -> {
            try {
                RetrofitFactory.getInstance().getApiService()
                        .getCall2()
                        .onErrorResumeNext(new RetrofitFactory.HttpResponseFunc<BaseEntity<TranslateEntity>>())
                        .flatMap(translateEntityBaseEntity -> {
                            Log.i("thread", "buildUseCaseObservable: "+Thread.currentThread().getName());
                            return Observable.just(translateEntityBaseEntity);
                        })
//                        .subscribeOn(Schedulers.io())
//                        .observeOn(AndroidSchedulers.mainThread())
                        .subscribe(new BaseObserver<BaseEntity<TranslateEntity>>() {
                            @Override
                            protected void onSuccess(BaseEntity<TranslateEntity> entity) {
                                Log.i("成功", "onSuccess: "+entity.toString());
                                emitter.onNext(entity.toString());

                                Log.i("thread", "onSuccess: "+Thread.currentThread().getName());
                            }

                            @Override
                            protected void onFailure(Throwable e) {
                                Log.i("失败", "onFailure: "+e.toString());
                                emitter.onError(e);

                                Log.i("thread", "onFailure: "+Thread.currentThread().getName());

                            }
                        });
            }catch (Exception ex){
                emitter.onError(ex);
            }
        });
    }
}
