package com.wf.android.activity;

import android.app.Activity;
import android.os.Bundle;
import android.view.Menu;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Toast;

import com.wf.android.R;
import com.wf.android.ownview.MenuDrawer;
import com.wf.android.ownview.MenuDrawerItem;

public class MainActivity extends Activity implements OnClickListener {

	private MenuDrawer drawer;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		
		drawer = (MenuDrawer) findViewById(R.id.menudrawer);
		drawer.setOnClickListener(this);
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}

	@Override
	public void onClick(View v) {
		// TODO Auto-generated method stub
		if(v instanceof MenuDrawerItem) {
			Toast.makeText(this, ((MenuDrawerItem) v).getText(), Toast.LENGTH_SHORT).show();
		}
	}

}
