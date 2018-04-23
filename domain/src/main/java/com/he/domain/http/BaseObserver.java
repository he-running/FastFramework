package com.he.domain.http;

import io.reactivex.Observer;
import io.reactivex.annotations.NonNull;
import io.reactivex.disposables.Disposable;

/**
 * 网络请求订阅者基类
 * Created by hesh on 2018/2/26.
 */

public abstract class BaseObserver<T> implements Observer<T> {

    private Disposable disposable;

    @Override
    public void onSubscribe(@NonNull Disposable d) {
        this.disposable = d;

//        showProgress();
    }

    @Override
    public void onNext(@NonNull T t) {
//        closeProgress();
        onSuccess(t);
    }

    @Override
    public void onError(@NonNull Throwable e) {
//        closeProgress();
        onFailure(e);
        disposable.dispose();
    }

    @Override
    public void onComplete() {
        disposable.dispose();
    }

    /**
     * 成功
     *
     * @param t
     * @throws Exception
     */
    protected abstract void onSuccess(T t);

    /**
     * 返回成功，但code错误
     * @param t
     * @throws Exception
     */
//    protected abstract  void onCodeError(T t) throws  Exception;


    /**
     * 返回失败
     *
     * @param e
     * @throws Exception
     */
    protected abstract void onFailure(Throwable e);


    protected void showProgress() {

    }

    protected void closeProgress() {

    }

}
