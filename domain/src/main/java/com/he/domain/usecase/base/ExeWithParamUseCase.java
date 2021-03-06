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

import org.reactivestreams.Subscription;

import io.reactivex.Observable;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Action;
import io.reactivex.functions.Consumer;
import io.reactivex.schedulers.Schedulers;

/**
 * Abstract class for a Use Case (Interactor in terms of Clean Architecture).
 * This interface represents a execution unit for different use cases (this means any use case
 * in the application should implement this contract).
 * <p>
 * By convention each UseCase implementation will return the result using a {@link .ObservableEmitter}
 * that will execute its job in a background thread and will post the result in the UI thread.
 * <p>
 * Created by luyh on 2016/2/28.
 * Modified by hesh on 2018/02/05
 */
public abstract class ExeWithParamUseCase<T, P> {

    protected static String LOG_TAG;

    protected final ThreadExecutor threadExecutor;
    protected final PostExecutionThread postExecutionThread;

    private Disposable disposable = null;

    private boolean isSingleTask = false;
    private boolean running = false;
    private boolean isAsyncTaskMode = false;

    protected ExeWithParamUseCase(ThreadExecutor threadExecutor,
                                  PostExecutionThread postExecutionThread) {

        LOG_TAG = getClass().getSimpleName();

        this.threadExecutor = threadExecutor;
        this.postExecutionThread = postExecutionThread;
    }

    /**
     * Executes the current use case.
     */
    public abstract Observable<T> buildUseCaseObservable(P param);

    /**
     * Executes the current use case.
     */
    public void execute(P param) {
        execute(param, var -> {
                },
                var -> {
                },
                () -> {
                });
    }

    /**
     * Executes the current use case.
     */
    public void execute(P param, Consumer<T> onNext) {
        execute(param, onNext, var -> {
        }, () -> {
        });
    }

    /**
     * Executes the current use case.
     */
    public void execute(P param, Consumer<T> onNext, Consumer<Throwable> onError) {
        execute(param, onNext, onError, () -> {
        });
    }

    /**
     * Executes the current use case.
     */
    public void execute(P param, Consumer<T> onNext, Consumer<Throwable> onError, Action onCompleted) {

        if (isSingleTask && running) {
            return;
        }

        if (!isAsyncTaskMode) {
            unsubscribe();
        }

        try {
            this.disposable = this.buildUseCaseObservable(param)
                    .subscribeOn(Schedulers.from(threadExecutor))
                    .observeOn(postExecutionThread.getScheduler())
                    .subscribe(onNext,throwable -> {
//                        disposable.dispose();
                        onError.accept(throwable);
                        running = false;
                        isAsyncTaskMode = false;
                    },() -> {
                        onCompleted.run();
                        running = false;
                        isAsyncTaskMode = false;
                    });
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * Unsubscribes from current {@link Subscription}.
     */
    public void unsubscribe() {
        if (disposable!=null&&!disposable.isDisposed()) {
            disposable.dispose();
        }
    }

    /**
     * 如果调用该方法则该UseCase以异步任务执行，
     * 每次执行execute方法不会unsubscribe
     *
     * @return
     */
    public ExeWithParamUseCase<T, P> setAsyncTaskMode() {
        isAsyncTaskMode = true;

        return this;
    }

    /**
     * 设UseCase为多任务模式
     * 一旦设置为该模式，无论该UseCase 是否在onCompleted或onError之前，都会继续执行该UseCase线程
     * 在非运行状态的时候设置有效
     */
    public ExeWithParamUseCase<T, P> setMultiTaskMode() {
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
    public ExeWithParamUseCase<T, P> setSingleTaskMode() {
        if (!running) {
            isSingleTask = true;
        }

        return this;
    }
}
