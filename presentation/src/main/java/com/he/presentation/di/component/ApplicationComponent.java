/**
 * Copyright (C) 2015 Fernando Cejas Open Source Project
 * <p>
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 * <p>
 * http://www.apache.org/licenses/LICENSE-2.0
 * <p>
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.he.presentation.di.component;


import com.he.presentation.base.BaseActivity;
import com.he.presentation.base.BaseFragmentActivity;
import com.he.presentation.di.modules.ActivityModule;
import com.he.presentation.di.modules.ApplicationModule;

import javax.inject.Singleton;

import dagger.Component;

/**
 * A component whose lifetime is the life of the application.
 */
@Singleton
@Component(modules = ApplicationModule.class)
public interface ApplicationComponent {

    //添加activity模块依赖组件
    ActivityComponent plus(ActivityModule activityModule);

    void inject(BaseActivity baseActivity);
    void inject(BaseFragmentActivity baseFragmentActivity);
}