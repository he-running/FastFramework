package com.he.presentation;

import android.app.Activity;

import com.tbruyelle.rxpermissions2.RxPermissions;

/**
 * 6.0动态权限申请工具类
 * Created by hesh on 2018/2/6.
 */

public class PermissionUtil {

    /**
     * 申请单个权限
     * @param activity
     * @param permission
     * @param listener
     */
    public static void requestOnePermission(Activity activity,String permission,PermissionListener listener){
        RxPermissions rxPermissions=new RxPermissions(activity);
        rxPermissions.requestEach(permission)
                .subscribe(permission1 -> {
                    if (permission1.granted) {
                        // 用户已经同意该权限
                        listener.succeed();
                    } else if (permission1.shouldShowRequestPermissionRationale) {
                        // 用户拒绝了该权限，没有选中『不再询问』；下次启动时，仍会提示请求权限的对话框
                        listener.failed();
                    } else {
                        // 用户拒绝了该权限，并且选中『不再询问』；下次启动不会弹窗，这时提醒用户手动打开权限
                        listener.warnTips();
                    }
                });
    }

    /**
     * 申请多个权限，同时开启
     * @param activity
     * @param permissions
     * @param listener
     */
    public static void requestMultiPermissions(Activity activity,String[] permissions,PermissionListener listener){
        RxPermissions rxPermissions=new RxPermissions(activity);
        rxPermissions.request(permissions)
                .subscribe(aBoolean -> {
                    if (aBoolean){
                        //所有权限都开启aBoolean才为true，否则为false
                        listener.succeed();
                    }else {
                        listener.failed();
                    }
                });
    }

    /**
     * 逐一申请多个权限，并分开处理
     * @param activity
     * @param permissions
     * @param listener
     */
    public static void requestOneByOnePermissions(Activity activity,String[] permissions,PermissionListener listener){
        RxPermissions rxPermissions=new RxPermissions(activity);
        rxPermissions.requestEach(permissions)
                .subscribe(permission -> {
                    if (permission.granted) {
                        // 用户已经同意该权限
                        listener.succeed();
                    } else if (permission.shouldShowRequestPermissionRationale) {
                        // 用户拒绝了该权限，没有选中『不再询问』；下次启动时，仍会提示请求权限的对话框
                        listener.failed();
                    } else {
                        // 用户拒绝了该权限，并且选中『不再询问』；下次启动不会弹窗，这时提醒用户手动打开权限
                        listener.warnTips();
                    }
                });
    }


    /**
     * 申请结果回调
     */
    public interface PermissionListener{
        void succeed();
        void failed();
        void warnTips();
    }
}
