/**
 * 
 */
package com.tenline.pinecone.mobile.android;

import java.util.ArrayList;
import java.util.Arrays;

import com.tenline.pinecone.mobile.android.service.RESTService;
import com.tenline.pinecone.mobile.android.service.TaskFacade;
import com.tenline.pinecone.mobile.android.view.AddDeviceDialogBuilder;
import com.tenline.pinecone.platform.model.Device;

import android.app.Dialog;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Toast;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ListView;

/**
 * @author Bill
 *
 */
public class DeviceActivity extends AbstractListActivity {
	
	public static final String ACTIVITY_ACTION = "com.tenline.pinecone.mobile.android.device";
	
	private static final String GET_DEVICE_WITH_NAME = "getDeviceWithName";
	private static final String GET_DEVICE_WITH_CODE = "getDeviceWithCode";
	private static final String POST_DEVICE_WITH_USER = "postDeviceWithUser";
	private static final String PUT_DEVICE_WITH_NAME = "putDeviceWithName";
	private static final String GET_DEVICES_WITH_USER = "getDevicesWithUser";
	public static final String VALIDATE_DEVICE_WITH_CODE = "validateDeviceWithCode";
	private static final String VALIDATE_DEVICE_WITH_NAME = "validateDeviceWithName";
	private static final String VALIDATE_DEVICE_WITH_USER = "validateDeviceWithUser";
	
	private AddDeviceDialogBuilder builder;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		TaskFacade.initRESTTask(this, GET_DEVICES_WITH_USER, "/user/"+getIntent().getStringExtra("userId")+"/devices");
		getListView().setChoiceMode(ListView.CHOICE_MODE_SINGLE);
		getListView().setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
				// TODO Auto-generated method stub
				TaskFacade.initRESTTask(DeviceActivity.this, GET_DEVICE_WITH_NAME, getListView().getItemAtPosition(position).toString());
			}
			
		});
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
			builder = new AddDeviceDialogBuilder(this);
			return builder.getDialog();
		}
		return super.onCreateDialog(id);
	}

	@Override
	protected void buildListAdapter(Object[] result) {
		// TODO Auto-generated method stub
		if (result.length == 0) Toast.makeText(this, R.string.error_device_code_is_not_existed, Toast.LENGTH_LONG).show();
		String[] items = new String[result.length];
		for (int i=0; i<result.length; i++) {items[i] = ((Device) result[i]).getName();}
		setListAdapter(new ArrayAdapter<String>(this, android.R.layout.simple_list_item_checked, items));	
	}
	
	@Override
	@SuppressWarnings("rawtypes")
	public Object[] doInBackground(String... params) {
		// TODO Auto-generated method stub
		Object[] result = Arrays.asList(params).toArray();
		try {
			while (!helper.isBound()) {Thread.sleep(100);}
			RESTService service = (RESTService) helper.getService();
			if (result[0].equals(GET_DEVICE_WITH_NAME)) {
				result[1] = String.valueOf(((Device) ((ArrayList) service.get("/device/search/names?name=" + result[1])).toArray()[0]).getId());
			} else if (result[0].equals(GET_DEVICE_WITH_CODE)) {
				result[1] = String.valueOf(((Device) ((ArrayList)service.get("/device/search/codes?code=" + result[1])).toArray()[0]).getId());
			} else if (result[0].equals(POST_DEVICE_WITH_USER)) {
				service.post("/device/" + result[1] + "/user", "/user/" + getIntent().getStringExtra("userId"));
			} else if (result[0].equals(PUT_DEVICE_WITH_NAME)) {
				Device device = new Device(); device.setName(result[2].toString()); service.put("/device/" + result[1], device);
			} else if (result[0].equals(GET_DEVICES_WITH_USER)) {
				Object[] temp = ((ArrayList<?>) service.get(result[1].toString())).toArray();
				Object[] flag = {result[0]}; result = new Object[temp.length + 1];
				System.arraycopy(flag, 0, result, 0, 1); System.arraycopy(temp, 0, result, 1, temp.length);
			} else if (result[0].equals(VALIDATE_DEVICE_WITH_CODE)) {
				ArrayList<?> devices = (ArrayList<?>) service.get("/device/search/codes?code=" + result[1]);
				result[1] = String.valueOf(devices.size());
				if (devices.size() > 0) result[2] = String.valueOf(((Device) devices.toArray()[0]).getId());	
			} else if (result[0].equals(VALIDATE_DEVICE_WITH_NAME)) {
				result[1] = String.valueOf(((ArrayList<?>) service.get("/device/search/names?name=" + result[1])).size());
			} else if (result[0].equals(VALIDATE_DEVICE_WITH_USER)) {
				result[1] = String.valueOf(((ArrayList<?>) service.get("/device/" + result[1] + "/user")).size());
			} else {
				result = super.doInBackground(params);	
			}
		} catch (Exception e) {
			// TODO Auto-generated catch block
			if (e.getMessage().equals("404 Not Found")) {result[1] = "0";}
			else Log.e(getClass().getSimpleName(), e.getMessage());
		}
		return result;
	}
	
	@Override
	public void onPostExecute(Object[] result) {
		// TODO Auto-generated method stub
		try {
			if (result[0].equals(GET_DEVICE_WITH_NAME)) {
				Intent intent = new Intent(VariableActivity.ACTIVITY_ACTION);
				intent.putExtra("deviceId", result[1].toString());
				startActivity(intent);
			} else if (result[0].equals(GET_DEVICE_WITH_CODE)) {
				TaskFacade.initRESTTask(this, POST_DEVICE_WITH_USER, result[1].toString(), result[2].toString());
			} else if (result[0].equals(POST_DEVICE_WITH_USER)) {
				TaskFacade.initRESTTask(this, PUT_DEVICE_WITH_NAME, result[1].toString(), result[2].toString());
			} else if (result[0].equals(PUT_DEVICE_WITH_NAME)) {
				TaskFacade.initRESTTask(this, GET_DEVICES_WITH_USER, "/user/" + getIntent().getStringExtra("userId") + "/devices");
				Toast.makeText(this, R.string.error_device_has_been_activated, Toast.LENGTH_LONG).show(); builder.getDialog().cancel();
			} else if (result[0].equals(GET_DEVICES_WITH_USER)) {
				Object[] data = new Object[result.length - 1];
				System.arraycopy(result, 1, data, 0, data.length); buildListAdapter(data);
			} else if (result[0].equals(VALIDATE_DEVICE_WITH_CODE)) {
				if (Integer.valueOf(result[1].toString()) > 0) {TaskFacade.initRESTTask(this, VALIDATE_DEVICE_WITH_USER, result[2].toString());}
				else {builder.getDeviceCode().setError(getString(R.string.error_device_code_is_not_existed));}
			} else if (result[0].equals(VALIDATE_DEVICE_WITH_NAME)) {
				if (Integer.valueOf(result[1].toString()) > 0) {builder.getDeviceName().setError(getString(R.string.error_device_name_is_existed));}
				else {TaskFacade.initRESTTask(this, GET_DEVICE_WITH_CODE, builder.getDeviceCode().getText().toString(), builder.getDeviceName().getText().toString());}
			} else if (result[0].equals(VALIDATE_DEVICE_WITH_USER)) {
				if (Integer.valueOf(result[1].toString()) > 0) {builder.getDeviceCode().setError(getString(R.string.error_device_has_been_activated));}
				else {TaskFacade.initRESTTask(this, VALIDATE_DEVICE_WITH_NAME, builder.getDeviceName().getText().toString());} 
			} else {
				super.onPostExecute(result);
			}
		} catch (Exception e) {
			// TODO Auto-generated catch block
			Log.e(getClass().getSimpleName(), e.getMessage());
		}
	}

}
