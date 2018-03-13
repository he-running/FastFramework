package com.he.presentation.view.activity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.he.data.db.DbManager;
import com.he.presentation.R;
import com.he.presentation.base.BaseActivity;
import com.he.presentation.di.component.ActivityComponent;
import com.he.presentation.di.modules.ActivityModule;
import com.he.presentation.presenter.MainPresenter;
import com.he.presentation.view.views.ImainView;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.OnClick;

public class MainActivity extends BaseActivity implements ImainView {

    ActivityComponent activityComponent;

    @Inject
    DbManager dbManager;

    @Inject
    MainPresenter mainPresenter;


    @BindView(R.id.btn_set)
    Button btnSet;
    @BindView(R.id.btn_get)
    Button btnGet;
    @BindView(R.id.tv_txt)
    TextView tvTxt;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        mainPresenter.requestPermission(this);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
    }

    @Override
    protected int getLayout() {
        return R.layout.activity_main;
    }

    @Override
    protected void initInjector() {
        activityComponent = getApplicationComponent().plus(new ActivityModule(this));
        activityComponent.inject(this);
    }

    @Override
    protected void initActivity() {
        mainPresenter.setView(this);
        mainPresenter.create();
        mainPresenter.initView();
    }

    @Override
    public void setText(String txt) {
        tvTxt.setText(txt);
    }

    @Override
    public String getText() {
        return tvTxt.getText().toString();
    }

    @Override
    public void showToast(String msg) {
        showShortToast(msg);
    }


    @OnClick({R.id.btn_set, R.id.btn_get})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.btn_set:
                mainPresenter.setText();

//                mainPresenter.saveData();//数据库
                mainPresenter.testHttp();//网络
                break;
            case R.id.btn_get:
                mainPresenter.getText();

//                List<Student> studentList=dbManager.getStuDaoOpe().queryAllData();
//                StringBuffer sb=new StringBuffer();
//                for (Student stu:studentList){
//                    sb.append(stu.toString()+"\n");
//                }
//                showLongToast(sb.toString());
                break;
        }
    }
}
