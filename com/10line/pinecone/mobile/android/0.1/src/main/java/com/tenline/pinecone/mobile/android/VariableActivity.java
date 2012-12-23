/**
 * 
 */
package com.tenline.pinecone.mobile.android;

import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;

/**
 * @author Bill
 *
 */
public class VariableActivity extends AbstractListActivity {

	public static final String ACTIVITY_ACTION = "com.tenline.pinecone.mobile.android.variable";
	
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		Log.i(getClass().getSimpleName(), "onCreate");
		initFetchTask("/device/" + getIntent().getStringExtra("deviceId") + "/variables");
	}
	
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		getMenuInflater().inflate(R.menu.options, menu);
	    return super.onCreateOptionsMenu(menu);
	}
	
	@Override
    public boolean onPrepareOptionsMenu(Menu menu) {
    	menu.setGroupVisible(R.id.device_items, false);
    	return super.onPrepareOptionsMenu(menu);
	}
	
	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		switch(item.getItemId()) {
		case R.id.user_logout: logout(); break;
		}
		return super.onOptionsItemSelected(item);
	}

}
