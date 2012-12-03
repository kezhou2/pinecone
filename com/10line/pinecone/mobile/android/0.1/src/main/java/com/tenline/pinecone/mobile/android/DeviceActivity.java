/**
 * 
 */
package com.tenline.pinecone.mobile.android;

import android.app.ListActivity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

/**
 * @author Bill
 *
 */
public class DeviceActivity extends ListActivity {
	
	public static final String ACTIVITY_ACTION = "com.tenline.pinecone.mobile.android.device";
	 
	public static final int MENU_ITEM_ADD = 0;
	public static final int MENU_ITEM_LOGOUT = 1;

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
	
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		menu.add(Menu.NONE, MENU_ITEM_ADD, Menu.FIRST + 1, R.string.add_text).setIcon(android.R.drawable.ic_menu_add);
		menu.add(Menu.NONE, MENU_ITEM_LOGOUT, Menu.FIRST + 2, R.string.logout_text).setIcon(android.R.drawable.ic_menu_close_clear_cancel);
	    return super.onCreateOptionsMenu(menu);
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		switch(item.getItemId()) {
		case MENU_ITEM_ADD:
			Toast.makeText(this, R.string.add_text, Toast.LENGTH_LONG).show();
			break;
		case MENU_ITEM_LOGOUT:
			Toast.makeText(this, R.string.logout_text, Toast.LENGTH_LONG).show();
			break;
		}
		return super.onOptionsItemSelected(item);
	}

}
