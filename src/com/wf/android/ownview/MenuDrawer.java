package com.wf.android.ownview;

import android.content.Context;
import android.content.res.TypedArray;
import android.util.AttributeSet;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.animation.Animation;
import android.view.animation.Animation.AnimationListener;
import android.view.animation.TranslateAnimation;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;

import com.wf.android.R;

public class MenuDrawer extends RelativeLayout implements OnClickListener, AnimationListener {

	// Attribute
	private String parentText;
//	private int parentIconId;
	private int parentBackgroundId;
	private int parentBackgroundColor;
	private float parentMargin;
	private int childBackgroundId;
	private int childBackgroundColor;
	private float childMargin;
	private boolean toggleOut = false;
	private int orientation;
	private RelativeLayout.LayoutParams parentLayoutParams;
	private RelativeLayout.LayoutParams childLayoutParams;
	private RelativeLayout.LayoutParams childLayoutParamsRightOf;
	private RelativeLayout.LayoutParams childLayoutParamsBelow;
	private LinearLayout.LayoutParams itemLayoutParams;
	private OnClickListener onClickListener;
	
	// Views
	private Button buttParent;
	private LinearLayout lytChild;
	
	// Animation
	private TranslateAnimation outAnim;
	private TranslateAnimation inAnim;
	
	public MenuDrawer(Context context, AttributeSet attrs) {
		super(context, attrs);
		// TODO Auto-generated constructor stub
		TypedArray a = context.obtainStyledAttributes(attrs, R.styleable.menudrawer, 0, 0);
		parentText = a.getString(R.styleable.menudrawer_parent_text);
//		parentIconId = a.getResourceId(R.styleable.menudrawer_parent_icon, 0);
		parentBackgroundId = a.getResourceId(R.styleable.menudrawer_parent_background_image, 0);
		parentBackgroundColor = a.getColor(R.styleable.menudrawer_parent_background_color, 0);
		parentMargin = a.getDimension(R.styleable.menudrawer_parent_margin, 0);
		childBackgroundId = a.getResourceId(R.styleable.menudrawer_child_background_image, 0);
		childBackgroundColor = a.getColor(R.styleable.menudrawer_child_background_color, 0);
		childMargin = a.getDimension(R.styleable.menudrawer_child_margin, 0);
		orientation = a.getInt(R.styleable.menudrawer_drawer_orientation, 0);
		float width = a.getDimension(R.styleable.menudrawer_button_width, 0);
		float height = a.getDimension(R.styleable.menudrawer_button_height, 0);
		int sizeOrientation = a.getInt(R.styleable.menudrawer_button_size_orientation, 0);
		
		if(sizeOrientation == 0 && (width == 0 || height == 0)) {
			parentLayoutParams = new RelativeLayout.LayoutParams(LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT);
			itemLayoutParams = new LinearLayout.LayoutParams(LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT);
		} else {
			parentLayoutParams = new RelativeLayout.LayoutParams((int) width, (int) height);
			itemLayoutParams = new LinearLayout.LayoutParams((int) width, (int) height);
		}
		childLayoutParams = new RelativeLayout.LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.MATCH_PARENT);
		childLayoutParamsRightOf = new RelativeLayout.LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.MATCH_PARENT);
		childLayoutParamsBelow = new RelativeLayout.LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.MATCH_PARENT);
		parentLayoutParams.setMargins(0, 0, (int) parentMargin, (int) parentMargin);
		itemLayoutParams.setMargins(0, 0, (int) childMargin, (int) childMargin);
		
		a.recycle();
		
		initButtonParent(context);
		initChildLayout(context);
		
		addView(lytChild, childLayoutParams);
		addView(buttParent, parentLayoutParams);
		
	}
	
	public MenuDrawer(Context context) {
		super(context, null);
	}	
		
	@Override
	protected void onFinishInflate() {
		// TODO Auto-generated method stub
		initChildItems();
		super.onFinishInflate();
	}
	
	private void initButtonParent(Context context) {
		buttParent = new Button(context);
		buttParent.setId(7);
		if(buttParent != null) {
			if(parentText != null) buttParent.setText(parentText);
			buttParent.setBackgroundResource(parentBackgroundId);
			if(parentBackgroundId <= 0) buttParent.setBackgroundColor(parentBackgroundColor);
			buttParent.setOnClickListener(this);
		}
		childLayoutParamsRightOf.addRule(RIGHT_OF, buttParent.getId());
		childLayoutParamsBelow.addRule(BELOW, buttParent.getId());
	}
	
	private void initChildLayout(Context context) {
		lytChild = new LinearLayout(context);
		lytChild.setId(8);
		lytChild.setOrientation(orientation);
		lytChild.setVisibility(View.INVISIBLE);
	}
	
	private void initChildItems() {
		for(int i=0; i<getChildCount(); i++) {
			View v = getChildAt(i);
			if(v instanceof MenuDrawerItem) {
				removeView(v);
				MenuDrawerItem item = (MenuDrawerItem) v;
				item.setIndex(i);
				initItem(item);
				lytChild.addView(item, itemLayoutParams);
				i--;
			}
		}
		
		if(orientation == LinearLayout.HORIZONTAL) {
			outAnim = new TranslateAnimation(
					Animation.RELATIVE_TO_PARENT, - (lytChild.getChildCount() + 1), 
					Animation.RELATIVE_TO_PARENT, 0, 
					Animation.RELATIVE_TO_SELF, 0, 
					Animation.RELATIVE_TO_SELF, 0);
			outAnim.setDuration(200);
			outAnim.setAnimationListener(this);
			
			inAnim = new TranslateAnimation(
					Animation.RELATIVE_TO_PARENT, 0, 
					Animation.RELATIVE_TO_PARENT, - (lytChild.getChildCount() + 1), 
					Animation.RELATIVE_TO_SELF, 0, 
					Animation.RELATIVE_TO_SELF, 0);
			inAnim.setDuration(200);
			inAnim.setAnimationListener(this);
		} else {
			outAnim = new TranslateAnimation(
					Animation.RELATIVE_TO_SELF, 0, 
					Animation.RELATIVE_TO_SELF, 0, 
					Animation.RELATIVE_TO_PARENT, - (lytChild.getChildCount()), 
					Animation.RELATIVE_TO_PARENT, 0);
			outAnim.setDuration(200);
			outAnim.setAnimationListener(this);
			
			inAnim = new TranslateAnimation(
					Animation.RELATIVE_TO_SELF, 0, 
					Animation.RELATIVE_TO_SELF, 0,
					Animation.RELATIVE_TO_PARENT, 0, 
					Animation.RELATIVE_TO_PARENT, - (lytChild.getChildCount()));
			inAnim.setDuration(200);
			inAnim.setAnimationListener(this);
		}
	}
	
	private void initItem(MenuDrawerItem item) {
		item.setBackgroundResource(childBackgroundId);
		if(childBackgroundId <= 0) item.setBackgroundColor(childBackgroundColor);
		item.setOnClickListener(this);
	}

	@Override
	public void onClick(View v) {
		// TODO Auto-generated method stub
		if(v == buttParent) {
			if(!toggleOut) {
				lytChild.startAnimation(outAnim);
				toggleOut = true;
			} else {
				lytChild.startAnimation(inAnim);
				toggleOut = false;
			}
			invalidate();
		} else if(v instanceof MenuDrawerItem) {
			if(onClickListener != null) {
				onClickListener.onClick(v);
			}
		}
	}
	
	public void setOnClickListener(OnClickListener onClickListener) {
		this.onClickListener = onClickListener;
	}

	@Override
	public void onAnimationEnd(Animation anim) {
		// TODO Auto-generated method stub
		if(anim == outAnim) {
			lytChild.setVisibility(View.VISIBLE);
		} else if(anim == inAnim) {
			lytChild.setLayoutParams(childLayoutParams);
			lytChild.setVisibility(View.INVISIBLE);
		}
	}

	@Override
	public void onAnimationRepeat(Animation anim) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void onAnimationStart(Animation anim) {
		// TODO Auto-generated method stub
		if(anim == outAnim) {
			if(orientation == LinearLayout.HORIZONTAL) lytChild.setLayoutParams(childLayoutParamsRightOf);
			else lytChild.setLayoutParams(childLayoutParamsBelow);
		}
	}

}
