package com.he.presentation.widget.robototextview;

import android.content.Context;
import android.support.v7.widget.AppCompatTextView;
import android.util.AttributeSet;

/**方正兰亭黑体TextView*/
public class RobotoTextView extends AppCompatTextView{

	public RobotoTextView(Context context) {
		super(context);
		initView();
	}

	public RobotoTextView(Context context, AttributeSet attrs) {
		super(context, attrs);
		initView();
	}

	public RobotoTextView(Context context, AttributeSet attrs, int defStyle) {
		super(context, attrs, defStyle);
		initView();
	}
	
	private void initView(){
		setTypeface(TextViewManager.getInstance().getFont());
	}

}
