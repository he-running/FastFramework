package com.he.domain.http;


import android.net.ParseException;

import com.google.gson.JsonParseException;
import com.jakewharton.retrofit2.adapter.rxjava2.HttpException;

import org.json.JSONException;

import java.net.ConnectException;
import java.util.concurrent.TimeoutException;


/**
 * 自定义网络操作异常工具类
 * Created by hesh on 2018/2/26.
 */

public class NetworkException{

    //对应HTTP的标准状态码，也可根据自定义通信协议修改
    private static final int UNAUTHORIZED = 401;
    private static final int FORBIDDEN = 403;
    private static final int NOT_FOUND = 404;
    private static final int REQUEST_TIMEOUT = 408;
    private static final int INTERNAL_SERVER_ERROR = 500;
    private static final int BAD_GATEWAY = 502;
    private static final int SERVICE_UNAVAILABLE = 503;
    private static final int GATEWAY_TIMEOUT = 504;


    public static Exception handleException(Throwable e){
        String ex;
        if (e instanceof HttpException){
            //HTTP错误
            HttpException httpException= (HttpException) e;
            switch (httpException.code()){
                case UNAUTHORIZED:
                case FORBIDDEN:
                case NOT_FOUND:
                case REQUEST_TIMEOUT:
                case GATEWAY_TIMEOUT:
                case INTERNAL_SERVER_ERROR:
                case BAD_GATEWAY:
                case SERVICE_UNAVAILABLE:
                    ex = "网络异常";  //均视为网络异常
                    break;
                default:
                    ex = "未知错误";
                    break;
            }
        }else if (e instanceof JsonParseException
                || e instanceof JSONException
                || e instanceof ParseException){
            ex="数据解析错误";
        }else if (e instanceof ConnectException
                || e instanceof TimeoutException){
            ex="网络连接失败";
        }else {
            ex=e.getMessage();//未知错误或服务器返回
        }

        return new Exception(ex);
    }
}
