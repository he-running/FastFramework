/**
 * Copyright (C) 2015 Fernando Cejas Open Source Project
 * <p>
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 * <p>
 * http://www.apache.org/licenses/LICENSE-2.0
 * <p>
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.he.domain.usecase.base;


import com.he.data.executor.PostExecutionThread;
import com.he.data.executor.ThreadExecutor;

import io.reactivex.Observable;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Action;
import io.reactivex.functions.Consumer;
import io.reactivex.schedulers.Schedulers;

/**
 * Abstract class for a Use Case (Interactor in terms of Clean Architecture).
 * This interface represents a execution unit for different use cases (this means any use case
 * in the application should implement this contract).
 *
 * By convention each UseCase implementation will return the result using a {@link .ObservableEmitter}
 * that will execute its job in a background thread and will post the result in the UI thread.
 *
 * Created by luyh on 2016/2/28.
 * Modified by hesh on 2018/02/05
 */
public abstract class UseCase<T> {

    protected static String LOG_TAG;

    protected final ThreadExecutor threadExecutor;
    protected final PostExecutionThread postExecutionThread;

    private Disposable disposable = null;

    private boolean isSingleTask = false;
    private boolean running = false;
    private boolean isAsyncTaskMode = false;

    protected UseCase(ThreadExecutor threadExecutor,
                      PostExecutionThread postExecutionThread) {

        LOG_TAG = getClass().getSimpleName();

        this.threadExecutor = threadExecutor;
        this.postExecutionThread = postExecutionThread;
    }

    /**
     * Executes the current use case.
     */
    public abstract Observable<T> buildUseCaseObservable() ;



    /**
     * Executes the current use case.
     */
    public void execute() {
        execute(var -> {},
                var -> {},
                () -> {});
    }

    /**
     * Executes the current use case.
     */
    public void execute(Consumer<T> onNext) {
        execute(onNext, var -> {
        }, () -> {
        });
    }

    /**
     * Executes the current use case.
     */
    public void execute(Consumer<T> onNext, Consumer<Throwable> onError) {
        execute(onNext, onError, () -> {
        });
    }

//    Action0 改成了 Action
//    Action1<T> 改成了 Consumer
//    Action2<T> 改成了 BiConsumer

//    被观察者--> 观察者
//    Observable-->Observer
//    Flowable --> Subscriber

    /**
     * Executes the current use case.
     */
    public void execute(Consumer<T> onNext, Consumer<Throwable> onError, Action onCompleted) {

        if(isSingleTask && running) {
            return;
        }

        if(!isAsyncTaskMode) {
            unsubscribe();
        }

        try {
            this.disposable = this.buildUseCaseObservable()
                    .subscribeOn(Schedulers.from(threadExecutor))
                    .observeOn(postExecutionThread.getScheduler())
                    .subscribe(onNext,throwable -> {
                        running = false;
                        isAsyncTaskMode = false;
                        onError.accept(throwable);
//                        disposable.dispose();
                    },() -> {
                        running = false;
                        isAsyncTaskMode = false;
                        onCompleted.run();
//                        disposable.dispose();
                    });
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * Unsubscribes from current.
     */
    public void unsubscribe() {
        if (disposable!=null&&!disposable.isDisposed()) {
            disposable.dispose();
        }
    }

    /**
     * 如果调用该方法则该UseCase以异步任务执行，
     * 每次执行execute方法不会unsubscribe
     * @return
     */
    public UseCase<T> setAsyncTaskMode() {
        isAsyncTaskMode = true;

        return this;
    }

    /**
     * 设UseCase为多任务模式
     * 一旦设置为该模式，无论该UseCase 是否在onCompleted或onError之前，都会继续执行该UseCase线程
     * 在非运行状态的时候设置有效
     */
    public UseCase<T> setMultiTaskMode() {
        if (!running) {
            isSingleTask = false;
        }

        return this;
    }

    /**
     * 设置UseCase为单任务模式
     * 一旦设置为该模式，则在该UseCase onCompleted或onError之前，都不会再执行该UseCase线程
     * 在非运行状态的时候设置有效
     */
    public UseCase<T> setSingleTaskMode() {
        if (!running) {
            isSingleTask = true;
        }

        return this;
    }
}
