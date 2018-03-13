package com.he.domain.http;

import android.util.Log;

import java.io.IOException;

import okhttp3.Interceptor;
import okhttp3.Request;
import okhttp3.Response;
import okhttp3.logging.HttpLoggingInterceptor;

/**
 * 拦截器
 * Created by hesh on 2018/2/26.
 */

public class InterceptorUtil {

    private static final String TAG = "HTTP-LOG";

    //日志拦截器
    public static HttpLoggingInterceptor logInterceptor(){
        return new HttpLoggingInterceptor(new HttpLoggingInterceptor.Logger() {
            @Override
            public void log(String s) {
                Log.i(TAG, "log: "+s);
            }
        }).setLevel(HttpLoggingInterceptor.Level.BODY);
    }

    //拦截http请求
    public static Interceptor headInterceptor(){
        return new Interceptor() {
            @Override
            public Response intercept(Chain chain) throws IOException {
                //在这里可以做一些想做的事,比如token失效时,重新获取token,或者添加header等
                Request request=chain.request();
                return chain.proceed(request);
            }
        };
    }

}
