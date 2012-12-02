/**
 * 
 */
package com.tenline.pinecone.mobile.android;

import android.app.ListActivity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ArrayAdapter;
import android.widget.ListView;

/**
 * @author Bill
 *
 */
public class DeviceActivity extends ListActivity {
	
	public static final String ACTIVITY_ACTION = "com.tenline.pinecone.mobile.android.device";
	 
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		Log.i(getClass().getSimpleName(), "onCreate");
		setListAdapter(new ArrayAdapter<String>(this, android.R.layout.simple_list_item_checked, new String[]{"Bill's Robot", "Jane's Smart Lamp"}));
		getListView().setChoiceMode(ListView.CHOICE_MODE_SINGLE);
		getListView().setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
				// TODO Auto-generated method stub
				startActivity(new Intent(VariableActivity.ACTIVITY_ACTION));
			}
			
		});
	}

}
