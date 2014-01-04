package com.wf.android.ownview;

import android.content.Context;
import android.content.res.TypedArray;
import android.util.AttributeSet;
import android.widget.Button;

import com.wf.android.R;

public class MenuDrawerItem extends Button {

	private int index;
	private int iconId;
	
	public MenuDrawerItem(Context context, AttributeSet attrs) {
		super(context, attrs);
		// TODO Auto-generated constructor stub
		TypedArray a = context.obtainStyledAttributes(attrs, R.styleable.menudrawer, 0, 0);
		String text = a.getString(R.styleable.menudrawer_child_text);
		iconId = a.getResourceId(R.styleable.menudrawer_child_icon, 0);
		
		setText(text);
		a.recycle();
	}

	public int getIndex() {
		return index;
	}

	public void setIndex(int index) {
		this.index = index;
	}

	public int getIconId() {
		return iconId;
	}

	public void setIconId(int iconId) {
		this.iconId = iconId;
	}
	
}
