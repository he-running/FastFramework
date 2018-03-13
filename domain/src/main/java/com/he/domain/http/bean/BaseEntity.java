package com.he.domain.http.bean;

import java.io.Serializable;

/**
 * 网络实体基类，适用于code-msg-data等格式的协议
 * Created by hesh on 2018/2/26.
 */

public class BaseEntity<T> implements Serializable {

    //测试样例
    private int status;
    private T content;

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public T getContent() {
        return content;
    }

    public void setContent(T content) {
        this.content = content;
    }

    @Override
    public String toString() {
        return "BaseEntity{" +
                "status=" + status +
                ", content=" + content.toString() +
                '}';
    }
}
