package com.ktw.ktwlib.widget;

import android.content.Context;
import androidx.appcompat.widget.AppCompatTextView;
import android.util.AttributeSet;

/**
 * 
 * =====================================
 * @author： xudengqiao
 * @date： 2018-1-11
 * @description：跑马灯(textView)
 * =====================================
 */
public class AlwaysMarqueeTextView extends AppCompatTextView {

	public AlwaysMarqueeTextView(Context context) {
		super(context);
		}

		public AlwaysMarqueeTextView(Context context, AttributeSet attrs) {
		super(context, attrs);
		}

		public AlwaysMarqueeTextView(Context context, AttributeSet attrs, int defStyle) {
		super(context, attrs, defStyle);
		}

		@Override
		public boolean isFocused() {
		return true;
		}
}
