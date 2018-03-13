package com.he.domain.http;

import com.he.domain.http.bean.BaseEntity;
import com.he.domain.http.bean.TranslateEntity;

import io.reactivex.Observable;
import retrofit2.Call;
import retrofit2.http.GET;

/**
 * 所有网络请求接口的入口
 * Created by hesh on 2018/2/26.
 */

public interface ApiService {

    /**
     * 测试样例
     */
    @GET("ajax.php?a=fy&f=auto&t=auto&w=hello%20world")
    Call<BaseEntity<TranslateEntity>> getCall();

    /**
     * 测试样例,使用rxjava
     */
    @GET("ajax.php?a=fy&f=auto&t=auto&w=hello%20world")
    Observable<BaseEntity<TranslateEntity>> getCall2();
}
