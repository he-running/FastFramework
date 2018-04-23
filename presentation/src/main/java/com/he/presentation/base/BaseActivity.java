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
package com.he.presentation.base;

import android.app.Fragment;
import android.app.FragmentTransaction;
import android.media.AudioManager;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.Toast;

import com.he.presentation.LocalApplication;
import com.he.presentation.di.component.ApplicationComponent;

import butterknife.ButterKnife;
import butterknife.Unbinder;

import static android.widget.Toast.makeText;

/**
 * Created by hesh on 2018/2/5.
 */
public abstract class BaseActivity extends AppCompatActivity {

    protected String TAG;

    private Unbinder unbinder;

    private Toast shortToast;
    private Toast longToast;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(getLayout());

        TAG = getClass().getSimpleName();
        //butterknife控件注入
        unbinder= ButterKnife.bind(this);

        initInjector();
        initActivity();

        //音量按钮控制媒体音量
        this.setVolumeControlStream(AudioManager.STREAM_MUSIC);

        LocalApplication.getInstance().addActivity(this);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        //butterknife注销
        if (unbinder!=null){
            unbinder.unbind();
        }

        LocalApplication.getInstance().removeActivity(this);
    }

    /**
     * 获取布局
     */
    protected  abstract int getLayout();

    /**
     * 初始化依赖注入
     */
    protected abstract void initInjector();

    /**
     * 初始化界面
     */
    protected abstract void initActivity();

    /**
     *
     * @param containerViewId
     * @param fragment
     */
    protected void addFragment(int containerViewId, Fragment fragment) {
        FragmentTransaction fragmentTransaction = this.getFragmentManager().beginTransaction();
        fragmentTransaction.add(containerViewId, fragment);
        fragmentTransaction.commit();
    }

    /**
     * Get the Main Application component for dependency injection.
     */
    protected LocalApplication getLocationApplication() {
        return ((LocalApplication) getApplication());
    }

    /**
     * Get the Main Application component for dependency injection.
     */
    protected ApplicationComponent getApplicationComponent() {
        return LocalApplication.getInstance().getApplicationComponent();
    }

    /**
     * Shows a {@link Toast} ic_message.
     *
     * @param message An string representing a short ic_message to be shown.
     */
    protected void showShortToast(String message) {
        if (shortToast==null){
            shortToast=makeText(this, message, Toast.LENGTH_SHORT);
            shortToast.show();
        }
        shortToast.setText(message);
        shortToast.show();
    }

    /**
     * Shows a {@link Toast} ic_message.
     *
     * @param message An string representing a long ic_message to be shown.
     */
    protected void showLongToast(String message) {
        if (longToast==null){
            longToast=makeText(this, message, Toast.LENGTH_LONG);
            longToast.show();
        }
        longToast.setText(message);
        longToast.show();
    }

}
