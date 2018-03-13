package com.he.presentation;

import android.content.Context;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Environment;
import android.os.Looper;
import android.os.SystemClock;
import android.util.Log;
import android.widget.Toast;

import java.io.File;
import java.io.FileOutputStream;
import java.io.PrintWriter;
import java.io.StringWriter;
import java.io.Writer;
import java.lang.reflect.Field;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

/**
 * 未捕捉异常处理类
 * Created by he on 2017/12/12.
 */

public class CrashHandler implements Thread.UncaughtExceptionHandler{

    private static final String TAG = "CrashHandler";

    private Context mContext;

    private Class mainclazz;//重启后打开的页面

    //饿汉式单例，在加载类时创建
    private static final CrashHandler  instance=new CrashHandler();
    //系统默认异常处理类
    private Thread.UncaughtExceptionHandler defaultHandler;
    //日志保存路径
    private static final String CRASHLOG_PATH=getSdCardAbsolutePath()+ "CrashLog";
    //日志文件日期格式
    private DateFormat format=new SimpleDateFormat("yyyyMMdd-HHmmss");

    //保存异常日志
    private Map<String,String> infos=new HashMap<>();

    private CrashHandler(){

    }

    /**
     * 获取异常处理单例
     * @return
     */
    public static CrashHandler getInstance(){
        return instance;
    }

    /**
     * 初始化
     * @param context   系统application
     * @param clazz     重启后打开的页面
     */
    public void init(Context context,Class clazz){
        mContext=context;
        mainclazz=clazz;
        defaultHandler=Thread.getDefaultUncaughtExceptionHandler();
        Thread.setDefaultUncaughtExceptionHandler(this);
    }

    @Override
    public void uncaughtException(Thread t, Throwable e) {
        if (!handleExcepTion(e)&&defaultHandler!=null){
            // 如果用户没有处理则让系统默认的异常处理器来处理
            defaultHandler.uncaughtException(t,e);
        }else {
            e.printStackTrace();
            SystemClock.sleep(3000);
            //重启，有时会导致不停重启，先注释掉
//            Intent intent = new Intent(mContext, mainclazz);
//            PendingIntent restartIntent = PendingIntent.getActivity(mContext,0,intent,PendingIntent.FLAG_CANCEL_CURRENT);
//            AlarmManager mgr = (AlarmManager) mContext.getSystemService(Context.ALARM_SERVICE);
//            mgr.set(AlarmManager.RTC, System.currentTimeMillis() + 500, restartIntent);

            android.os.Process.killProcess(android.os.Process.myPid());
            System.exit(1);
        }
    }

    /**
     * 当UncaughtException发生时会转入该函数来处理
     * @param ex
     * @return
     */
    private boolean handleExcepTion(Throwable ex){
        if (ex==null){
            return false;
        }
        new Thread(new Runnable() {
            @Override
            public void run() {
                Looper.prepare();
                Toast.makeText(mContext,"抱歉，程序停止运行",Toast.LENGTH_SHORT).show();
                Looper.loop();
            }
        }).start();
        //收集系统信息
        collectDeviceInfo();
        //保存日志
        saveExceptionLog(ex);
        return true;
    }

    /**
     * 收集系统信息
     */
    private void collectDeviceInfo(){
        try {
            PackageManager pm=mContext.getPackageManager();
            PackageInfo pi=pm.getPackageInfo(mContext.getPackageName(),PackageManager.GET_ACTIVITIES);
            if (pi!=null){
                String versionName=pi.versionName==null ? "null":pi.versionName;
                String versionCode=pi.versionCode+"";

                infos.put("versionName",versionName);
                infos.put("versionCode",versionCode);
            }
        } catch (Exception e) {
            e.printStackTrace();
            Log.e(TAG, "收集异常日志发生异常");
        }
        Field[] fields=Build.class.getFields();
        for (Field field:fields){
            if (field!=null){
                try {
                    field.setAccessible(true);
                    infos.put(field.getName(),field.get(null).toString());
                } catch (IllegalAccessException e) {
                    e.printStackTrace();
                    Log.e(TAG, "收集异常日志发生异常");
                }
            }
        }
    }


    /**
     * 保存异常日志到指定文件夹
     */
    private void saveExceptionLog(Throwable ex){
        StringBuffer sb=new StringBuffer();
        //读取手机配置信息
        for (Map.Entry<String,String> entry:infos.entrySet()){
            if (entry!=null){
                String key= entry.getKey();
                String value= entry.getValue();
                sb.append(key+"="+value+"\n");
            }
        }
        //读取异常信息
        Writer writer=new StringWriter();
        PrintWriter printWriter=new PrintWriter(writer);
        ex.printStackTrace(printWriter);//把异常信息放进打印writer中
        Throwable cause=ex.getCause();//再次获取剩余的异常原因
        while (cause!=null){//直到读取完所有的异常原因和路径信息
            cause.printStackTrace(printWriter);
            cause=cause.getCause();
        }
        printWriter.close();
        String result=writer.toString();
        sb.append(result);

        //保存信息
        if (Environment.getExternalStorageState().equals(Environment.MEDIA_MOUNTED)){
            File file=new File(CRASHLOG_PATH);
            if (!file.exists()){
                file.mkdirs();//创建保存文件夹
            }
            String time=format.format(new Date());
            String fileName="["+"崩溃日志"+"]"+time+".log";
            String filePath=CRASHLOG_PATH+File.separator+fileName;

            try {
                FileOutputStream fos=new FileOutputStream(filePath);
                fos.write(sb.toString().getBytes());
                fos.flush();
                fos.close();
            } catch (Exception e) {
                e.printStackTrace();
                Log.e(TAG, "saveExceptionLog: 保存日志异常");
            }
        }else {
            Log.e(TAG, "saveExceptionLog: SD卡异常");
        }
    }

    /**
     * 获取SD卡根目录
     * @return
     */
    private static String getSdCardAbsolutePath(){
        return Environment.getExternalStorageDirectory().getAbsolutePath()+ File.separator;
    }
}
