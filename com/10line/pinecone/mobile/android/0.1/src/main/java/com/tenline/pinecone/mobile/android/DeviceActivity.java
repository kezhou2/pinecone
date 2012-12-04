/**
 * 
 */
package com.tenline.pinecone.mobile.android;

import android.app.AlertDialog;
import android.app.Dialog;
import android.app.ListActivity;
import android.content.DialogInterface;
import android.content.DialogInterface.OnClickListener;
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
	
	private static final int DEVICE_ADD_DIALOG = 0;

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
			showDialog(DEVICE_ADD_DIALOG);
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
		super.onCreateDialog(id);
		Dialog dialog = null;
		switch(id) {
		case DEVICE_ADD_DIALOG:
			dialog = new AlertDialog.Builder(this)
			.setPositiveButton(R.string.app_ok, new OnClickListener() {

				@Override
				public void onClick(DialogInterface dialog, int which) {
					// TODO Auto-generated method stub
					Toast.makeText(DeviceActivity.this, R.string.device_add, Toast.LENGTH_LONG).show();
				}
				
			})
			.setNegativeButton(R.string.app_cancel, new OnClickListener() {

				@Override
				public void onClick(DialogInterface dialog, int which) {
					// TODO Auto-generated method stub
					dialog.cancel();
				}
				
			})
			.setView(getLayoutInflater().inflate(R.layout.add_device, null))
			.setTitle(R.string.device_add)
			.setIcon(android.R.drawable.ic_menu_add)
			.create();
			break;
		}
		return dialog;
	}

}
