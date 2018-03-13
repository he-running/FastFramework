package com.he.presentation.presenter;

import android.app.Activity;
import android.content.Context;
import android.util.Log;

import com.he.data.di.PerActivity;
import com.he.domain.usecase.HttpTestUseCase;
import com.he.domain.usecase.QueryDataBaseUseCase;
import com.he.presentation.PermissionUtil;
import com.he.presentation.base.BasePresenter;
import com.he.presentation.view.views.ImainView;

import java.util.Random;

import javax.inject.Inject;
import javax.inject.Named;

/**
 * Created by hesh on 2018/2/5.
 */
@PerActivity
public class MainPresenter extends BasePresenter<ImainView> {

    @Inject
    Context context;

    @Named("permissionGroup")
    @Inject
    String[] permissionGroup;//危险权限组

    @Inject
    QueryDataBaseUseCase queryDataBaseUseCase;

    @Inject
    HttpTestUseCase httpTestUseCase;


    @Inject
    public MainPresenter() {

    }


    @Override
    public void create() {
        super.create();
    }

    /**
     * 动态权限请求
     * @param activity
     */
    public void requestPermission(Activity activity) {
        PermissionUtil.requestMultiPermissions(activity, permissionGroup,
                new PermissionUtil.PermissionListener() {
            @Override
            public void succeed() {
                view.showToast("all permissions is passed");
            }

            @Override
            public void failed() {
                view.showToast("some permissions is refused");
            }

            @Override
            public void warnTips() {
                view.showToast("open in settingPage");
            }
        });
    }

    public void initView() {
        view.setText("幸运大抽奖开始喽！");
    }

    @Override
    public void resume() {

    }

    @Override
    public void pause() {

    }

    @Override
    public void destroy() {
        super.destroy();
    }

    public void getText() {
        String txt = view.getText();
        view.showToast(txt);
    }

    public void setText() {
        String txt = "幸运数字：" + new Random().nextInt(100);
        view.setText(txt);
    }

    public void saveData(){
        queryDataBaseUseCase.execute(o -> {
            if (o.equals("success")){
                Log.d(TAG, "saveData: success");
            }else {
                Log.w(TAG, "saveData: fail");
            }
        },throwable -> {
            Log.e(TAG, "saveData: error -> "+throwable.toString());
        });
    }

    public void testHttp() {
        httpTestUseCase.execute(s -> {
            Log.i(TAG, "***--返回数据--**: "+s);
            view.showToast(s);
        },throwable -> {
            Log.e(TAG, "***--http出错--**: "+throwable.toString());
        });
    }

}
