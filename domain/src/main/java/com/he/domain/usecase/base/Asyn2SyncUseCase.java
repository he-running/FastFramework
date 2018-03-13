package com.he.domain.usecase.base;


import com.he.data.executor.PostExecutionThread;
import com.he.data.executor.ThreadExecutor;

import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

import io.reactivex.Observable;
import io.reactivex.ObservableEmitter;

/**
 * 异步转同步的用例抽象类
 * @param <T> Observable<T>中需要的类型
 * @param <AR> 执行异步方法asyncExecutor后所得到的数据的类型
 */
public abstract class Asyn2SyncUseCase<T, AR> extends UseCase<T> {

    protected String LOG_TAG;

    protected Asyn2SyncUseCase(ThreadExecutor threadExecutor, PostExecutionThread postExecutionThread) {
        super(threadExecutor, postExecutionThread);

        LOG_TAG = getClass().getSimpleName();
    }

    /**
     * 默认超时时间30秒
     */
    private Long timeout = 30000l;

    @Override
    public Observable<T> buildUseCaseObservable() {
        return Observable.create(subscriber -> {

            final Lock lock = new ReentrantLock();
            final Condition condition = lock.newCondition();

            AsyncResult<AR> asyncResult = new AsyncResult();

            threadExecutor.execute(() -> {
                try {
                    asyncExecutor(condition, lock, asyncResult);
                } catch (Exception e) {
                    subscriber.onError(e);
                    unlock(condition, lock);
                }
            });

            //加锁等待
            try {
                lock.lock();
                condition.await(timeout, TimeUnit.MILLISECONDS);

                asyncResultHandler(subscriber, asyncResult);
            } catch (InterruptedException e) {
                e.printStackTrace();
            } finally {
                lock.unlock();
            }

        });
    }

    /**
     * 解锁，在异步获取到数据结果之后，需要调用该方法对阻塞线程进行解锁
     * @param condition
     * @param lock
     */
    protected void unlock(Condition condition, Lock lock) {
        lock.lock();
        try {
            condition.signal();
        } finally {
            lock.unlock();
        }
    }

    /**
     * 设置超时时间
     * @param timeout
     */
    public void setTimeout(long timeout) {
        this.timeout = timeout;
    }

    protected abstract void asyncExecutor(Condition condition, Lock lock, AsyncResult<AR> asyncResult) throws Exception;

    protected abstract void asyncResultHandler(ObservableEmitter<? super T> subscriber, AsyncResult<AR> asyncResult);


}
