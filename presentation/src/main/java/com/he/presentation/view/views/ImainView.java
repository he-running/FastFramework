package com.he.presentation.view.views;

import com.he.presentation.base.BaseView;

/**
 * Created by hesh on 2018/2/5.
 */

public interface ImainView extends BaseView{

    void setText(String txt);

    String getText();

    void showToast(String msg);
}
