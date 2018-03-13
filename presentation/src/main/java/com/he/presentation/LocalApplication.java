/*
 * Copyright (C) 2013 Square, Inc.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.he.presentation;


import android.app.Activity;
import android.support.multidex.MultiDexApplication;

import com.beardedhen.androidbootstrap.TypefaceProvider;
import com.he.data.db.DbConfig;
import com.he.presentation.di.component.ApplicationComponent;
import com.he.presentation.di.component.DaggerApplicationComponent;
import com.he.presentation.di.modules.ApplicationModule;
import com.he.presentation.view.activity.MainActivity;
import com.he.presentation.widget.robototextview.TextViewManager;

import java.util.HashSet;
import java.util.Set;

public class LocalApplication extends MultiDexApplication {

    private static final String TAG = "LocalApplication";

    private static LocalApplication instance;

    private ApplicationComponent applicationComponent;

    private Set<Activity> allActivities;

    @Override
    public void onCreate() {
        super.onCreate();

        instance=this;

        initCrashHandler();
        initInjector();
        initDatabase();
        initBaiduMap();
        initBootstrap();
        initRotoboTextView();
    }

    public static synchronized LocalApplication getInstance() {
        return instance;
    }

    @Override
    public void onTerminate() {
        super.onTerminate();
    }

    //初始化异常捕获
    private void initCrashHandler() {
        CrashHandler crashHandler = CrashHandler.getInstance();
        crashHandler.init(getApplicationContext(), MainActivity.class);
    }

    private void initDatabase() {
        DbConfig dbConfig = DbConfig.getInstance();
        dbConfig.init(this,"mytest.db");
    }

    /**
     * 初始化百度地图
     */
    private void initBaiduMap() {
//        SDKInitializer.initialize(this);
    }

    /**
     * 初始化AndroidBootstrap
     */
    private void initBootstrap() {
        TypefaceProvider.registerDefaultIconSets();
    }

    /**
     * 初始化兰亭黑字体
     */
    private void initRotoboTextView() {
        TextViewManager.getInstance().init(getApplicationContext());
    }

    private void initInjector() {
        this.applicationComponent = DaggerApplicationComponent.builder()
                .applicationModule(new ApplicationModule(this)).build();
    }

    public ApplicationComponent getApplicationComponent() {
        return this.applicationComponent;
    }

    /**
     * 添加activity
     */
    public void addActivity(Activity act) {
        if (allActivities == null) {
            allActivities = new HashSet<>();
        }
        allActivities.add(act);
    }

    /**
     * 移除activity
     */
    public void removeActivity(Activity act) {
        if (allActivities != null) {
            allActivities.remove(act);
        }
    }

    /**
     * 退出app
     */
    public void exitApp() {
        if (allActivities != null) {
            synchronized (allActivities) {
                for (Activity act : allActivities) {
                    act.finish();
                }
            }
        }
        android.os.Process.killProcess(android.os.Process.myPid());
        System.exit(0);
    }
}
