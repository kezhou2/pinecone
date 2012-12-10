/**
 * 
 */
package com.tenline.pinecone.mobile.android;

import com.tenline.pinecone.mobile.android.view.AddDeviceDialogBuilder;

import android.app.Dialog;
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
		getMenuInflater().inflate(R.menu.options, menu);
	    return super.onCreateOptionsMenu(menu);
	}

	@Override
	@SuppressWarnings("deprecation")
	public boolean onOptionsItemSelected(MenuItem item) {
		switch(item.getItemId()) {
		case R.id.device_add:
			showDialog(AddDeviceDialogBuilder.DIALOG_ID);
			break;
		case R.id.user_logout:
			Toast.makeText(this, R.string.user_logout, Toast.LENGTH_LONG).show();
			break;
		}
		return super.onOptionsItemSelected(item);
	}
	
	@Override
	@SuppressWarnings("deprecation")
	public Dialog onCreateDialog(int id) {
		switch(id) {
		case AddDeviceDialogBuilder.DIALOG_ID:
			return new AddDeviceDialogBuilder(this).getDialog();
		}
		return super.onCreateDialog(id);
	}

}
