package com.he.domain.Utils;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.util.Log;

/**
 * 手机网络工具
 * Created by hesh on 2018/4/19.
 */
public class NetUtil {

    private static final String TAG = "NetUtil";

    /**
     * 网络是否可行
     *
     * @return
     */
    public static boolean isNetworkActive(Context context) {
        ConnectivityManager manager = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo networkInfo = manager.getActiveNetworkInfo();
        if (networkInfo != null && networkInfo.isAvailable()) {
            Log.i(TAG, "当前网络可用");
            return true;
        } else {
            Log.e(TAG, "当前网络不可用，请打开网络");
            return false;
        }
    }

    /**
     * 是否为移动网络
     *
     * @param context
     * @return
     */
    public static boolean isMobile(Context context) {
        ConnectivityManager manager = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo networkInfo = manager.getActiveNetworkInfo();
        if (networkInfo != null && networkInfo.getType() == ConnectivityManager.TYPE_MOBILE) {
            return true;
        }
        return false;
    }

    public static boolean isWifi(Context context){
        ConnectivityManager manager= (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo networkInfo=manager.getActiveNetworkInfo();
        if (networkInfo!=null&&
                networkInfo.getType()==ConnectivityManager.TYPE_WIFI){
            return true;
        }
        return false;
    }
}




