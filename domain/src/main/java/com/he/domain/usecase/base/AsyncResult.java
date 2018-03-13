package com.he.domain.usecase.base;

import java.io.Serializable;

/**
 * 异步结果临时存储对象
 * Created by luyh on 2016/6/22.
 */
public class AsyncResult<R> implements Serializable {

    private R result;
    private Throwable throwable;

    public R getResult() {
        return result;
    }

    public void setResult(R result) {
        this.result = result;
    }

    public Throwable getThrowable() {
        return throwable;
    }

    public void setThrowable(Throwable throwable) {
        this.throwable = throwable;
    }

}
