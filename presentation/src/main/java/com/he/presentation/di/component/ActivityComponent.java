package com.he.presentation.di.component;


import com.he.data.di.PerActivity;
import com.he.presentation.di.modules.ActivityModule;
import com.he.presentation.view.activity.MainActivity;

import dagger.Subcomponent;

/**
 * activity的依赖组件管理者
 */
@PerActivity
@Subcomponent(modules =ActivityModule.class)
public interface ActivityComponent {

    void inject(MainActivity mainActivity);
}
