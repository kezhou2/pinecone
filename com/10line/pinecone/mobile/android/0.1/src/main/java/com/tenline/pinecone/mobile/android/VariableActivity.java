/**
 * 
 */
package com.tenline.pinecone.mobile.android;

import android.app.ListActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.ArrayAdapter;

/**
 * @author Bill
 *
 */
public class VariableActivity extends ListActivity {

	public static final String ACTIVITY_ACTION = "com.tenline.pinecone.mobile.android.variable";
	
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		Log.i(getClass().getSimpleName(), "onCreate");
		setListAdapter(new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, new String[]{"Temperature", "Humidity"}));
	}

}
