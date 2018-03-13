package com.he.domain.http;

import com.he.domain.http.config.HttpConfig;
import com.jakewharton.retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;

import java.util.concurrent.TimeUnit;

import io.reactivex.Observable;
import io.reactivex.annotations.NonNull;
import io.reactivex.functions.Function;
import okhttp3.OkHttpClient;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * retrofit网络请求工具类
 * Created by hesh on 2018/2/26.
 */

public class RetrofitFactory {

//    @Inject
//    Context context;//全局context

    private static RetrofitFactory retrofitFactory;
    private static ApiService apiService;

    private RetrofitFactory(){
//        Cache cache=new Cache(new File(context.getCacheDir(),"HttpCache")
//                ,1024*1024*100);// 指定缓存路径,缓存大小100Mb
        OkHttpClient client=new OkHttpClient.Builder()
//                .cache(cache)
                .connectTimeout(HttpConfig.HTTP_TIME, TimeUnit.SECONDS)
                .readTimeout(HttpConfig.HTTP_TIME, TimeUnit.SECONDS)
                .writeTimeout(HttpConfig.HTTP_TIME, TimeUnit.SECONDS)
                .addInterceptor(InterceptorUtil.headInterceptor())
                .addInterceptor(InterceptorUtil.logInterceptor())
                .build();

        Retrofit retrofit=new Retrofit.Builder()
                .baseUrl(HttpConfig.BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .client(client)
                .build();

        apiService=retrofit.create(ApiService.class);
    }


    public synchronized static RetrofitFactory getInstance(){
        if (retrofitFactory==null){
            synchronized (RetrofitFactory.class){
                if (retrofitFactory==null){
                    retrofitFactory=new RetrofitFactory();
                }
            }
        }
        return retrofitFactory;
    }

    public ApiService getApiService(){
        return apiService;
    }


    /**
     * 处理网络连接的异常内部类
     * 返回自定义的异常信息
     * @param <T>
     */
    public static class HttpResponseFunc<T> implements Function<Throwable,Observable<T>>{

        @Override
        public Observable<T> apply(@NonNull Throwable throwable) throws Exception {
            return Observable.error(NetworkException.handleException(throwable));
        }
    }

}
