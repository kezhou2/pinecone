/**
 * 
 */
package com.tenline.pinecone.mobile.android;

import java.util.ArrayList;
import java.util.HashMap;

import com.tenline.pinecone.mobile.android.view.ActivateDeviceDialogBuilder;
import com.tenline.pinecone.platform.model.Device;

import android.app.Dialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.SimpleAdapter;
import android.widget.SimpleAdapter.ViewBinder;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ListView;

/**
 * @author Bill
 *
 */
public class DeviceActivity extends AbstractListActivity implements ViewBinder {
	
	public static final String ACTIVITY_ACTION = "com.tenline.pinecone.mobile.android.device";

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		doInitListViewTask("/user/" + getIntent().getStringExtra("userId") + "/devices");
		getListView().setChoiceMode(ListView.CHOICE_MODE_SINGLE);
		getListView().setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
				// TODO Auto-generated method stub
				Intent intent = new Intent(VariableActivity.ACTIVITY_ACTION);
				intent.putExtra("deviceId", String.valueOf(view.getTag()));
				startActivity(intent);
			}
			
		});
	}

	@Override
	@SuppressWarnings("deprecation")
	public boolean onOptionsItemSelected(MenuItem item) {
		switch(item.getItemId()) {
		case R.id.device_activate: showDialog(ActivateDeviceDialogBuilder.DIALOG_ID); break;
		case R.id.user_logout: new LogoutTask(this).execute(); break;}
		return super.onOptionsItemSelected(item);
	}
	
	@Override
	@SuppressWarnings("deprecation")
	protected Dialog onCreateDialog(int id) {
		switch(id) {
		case ActivateDeviceDialogBuilder.DIALOG_ID:
			return new ActivateDeviceDialogBuilder(this).getDialog();
		}
		return super.onCreateDialog(id);
	}

	@Override
	protected void buildListAdapter(Object[] result) {
		// TODO Auto-generated method stub
		if (result.length == 0) Toast.makeText(this, R.string.error_device_code_is_not_existed, Toast.LENGTH_LONG).show();
		ArrayList<HashMap<String, Device>> items = new ArrayList<HashMap<String, Device>>();
		for (int i=0; i<result.length; i++) {
			HashMap<String, Device> item = new HashMap<String, Device>();
			item.put("device", (Device) result[i]); items.add(item);
		}	
		SimpleAdapter adapter = new SimpleAdapter(this, items, android.R.layout.simple_list_item_checked, new String[]{"device"}, new int[]{android.R.id.text1});
        adapter.setViewBinder(this); setListAdapter(adapter);
	}
	
	@Override
	public boolean setViewValue(View view, Object data, String textRepresentation) {
		// TODO Auto-generated method stub
		Device device = (Device) data; view.setTag(Long.valueOf(device.getId()).intValue());
		((TextView) view).setText(device.getName()); return true;
	}

}
