/**
 * 
 */
package com.tenline.pinecone.mobile.android;

import java.util.ArrayList;

import com.tenline.pinecone.mobile.android.service.RESTService;
import com.tenline.pinecone.mobile.android.view.AddDeviceDialogBuilder;
import com.tenline.pinecone.platform.model.Device;

import android.app.Dialog;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ListView;

/**
 * @author Bill
 *
 */
public class DeviceActivity extends AbstractListActivity {
	
	public static final String ACTIVITY_ACTION = "com.tenline.pinecone.mobile.android.device";

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		Log.i(getClass().getSimpleName(), "onCreate");
		initFetchTask("/user/"+getIntent().getStringExtra("userId")+"/devices");
		getListView().setChoiceMode(ListView.CHOICE_MODE_SINGLE);
		getListView().setOnItemClickListener(new OnItemClickListener() {

			@Override
			@SuppressWarnings("unchecked")
			public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
				// TODO Auto-generated method stub
				try {
					RESTService service = (RESTService) helper.getService();
					String deviceName = getListView().getItemAtPosition(position).toString();
					Device device = (Device) ((ArrayList<Device>) service.get("/device/search/names?name="+deviceName)).toArray()[0];
					Intent intent = new Intent(VariableActivity.ACTIVITY_ACTION);
					intent.putExtra("deviceId", String.valueOf(device.getId()));
					startActivity(intent);
				} catch (Exception e) {
					// TODO Auto-generated catch block
					Log.e(getClass().getSimpleName(), e.getMessage());
				}
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
		case R.id.device_add: showDialog(AddDeviceDialogBuilder.DIALOG_ID); break;
		case R.id.user_logout: logout(); break;
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
