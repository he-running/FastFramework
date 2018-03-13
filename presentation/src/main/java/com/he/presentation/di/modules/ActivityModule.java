package com.he.presentation.di.modules;

import android.app.Activity;

import com.he.data.di.PerActivity;
import com.he.presentation.R;

import javax.inject.Named;

import dagger.Module;
import dagger.Provides;

/**
 * 主页面等模块的依赖注入类
 */
@Module
public class ActivityModule {
    private final Activity activity;

    public ActivityModule(Activity activity) {
        this.activity = activity;
    }

    @Provides
    @PerActivity
    public Activity provideActivity(){
        return this.activity;
    }


    @Provides
    @PerActivity
    @Named("permissionGroup")
    String[] providePermisionGroup(){
        String[] data=activity.getResources().getStringArray(R.array.permission_group);
        return data;
    }
}
