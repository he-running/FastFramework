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

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentTransaction;
import android.widget.Toast;

import com.he.presentation.LocalApplication;
import com.he.presentation.di.component.ApplicationComponent;

import butterknife.ButterKnife;
import butterknife.Unbinder;

import static android.widget.Toast.makeText;

/**
 * Created by hesh on 2018/2/5.
 */
public abstract class BaseFragmentActivity extends FragmentActivity {

    protected String TAG;

    private Unbinder unbinder;

    private Toast shortToast;
    private Toast longToast;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(getLayout());

//        this.getApplicationComponent().inject(this);

        TAG = getClass().getSimpleName();

        //butterknife控件注入
        unbinder= ButterKnife.bind(this);

        initializeInjector();
        initializeActivity();
    }

    @Override
    protected void onDestroy() {
        //butterknife注销
        unbinder.unbind();

        super.onDestroy();
    }

    /**
     * 获取布局
     */
    protected  abstract int getLayout();

    /**
     * 初始化界面
     */
    protected abstract void initializeActivity();

    /**
     * 初始化依赖注入
     */
    protected abstract void initializeInjector();

    /**
     * Adds a {@link Fragment} to this com.smart.loaction.baidumap.activity's layout.
     *
     * @param containerViewId The container view to where add the fragment.
     * @param fragment        The fragment to be added.
     */
    protected void addFragment(int containerViewId, Fragment fragment) {
        FragmentTransaction fragmentTransaction = this.getSupportFragmentManager().beginTransaction();
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
        return ((LocalApplication) getApplication()).getApplicationComponent();
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
