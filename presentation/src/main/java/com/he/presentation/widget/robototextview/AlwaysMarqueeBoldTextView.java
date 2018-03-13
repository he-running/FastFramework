package com.he.presentation.widget.robototextview;

import android.content.Context;
import android.support.v7.widget.AppCompatTextView;
import android.util.AttributeSet;
import android.widget.RemoteViews;

/**
 * Created by zky on 2017/12/8.
 */
@RemoteViews.RemoteView
public class AlwaysMarqueeBoldTextView extends AppCompatTextView {


    public AlwaysMarqueeBoldTextView(Context context) {
        super(context);
    }

    public AlwaysMarqueeBoldTextView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public AlwaysMarqueeBoldTextView(Context context, AttributeSet attrs,
                                     int defStyle) {
        super(context, attrs, defStyle);
    }


    @Override
    public boolean isFocused() {
        return true;
    }
}
