package com.he.presentation.widget.robototextview;

import android.content.Context;
import android.content.res.AssetManager;
import android.graphics.Typeface;

/**字体管理类*/
public class TextViewManager {
	
	private Typeface font;

	// -------------------------------单例限制-------------------------------------
	private static TextViewManager mTextViewManager = new TextViewManager();
	
	private TextViewManager() {};

	public static synchronized TextViewManager getInstance() {
		return mTextViewManager;
	}

	public void init(Context context) {
		AssetManager assetManager = context.getAssets();
		font = Typeface.createFromAsset(assetManager, "fonts/Roboto-Regular.ttf");
	}

	public Typeface getFont() {
		return font;
	}

	public void setFont(Typeface font) {
		this.font = font;
	}
	
}
