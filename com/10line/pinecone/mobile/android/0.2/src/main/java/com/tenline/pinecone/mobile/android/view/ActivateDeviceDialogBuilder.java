/**
 * 
 */
package com.tenline.pinecone.mobile.android.view;

import java.util.ArrayList;

import com.google.zxing.integration.android.IntentIntegrator;
import com.tenline.pinecone.mobile.android.DeviceActivity;
import com.tenline.pinecone.mobile.android.R;
import com.tenline.pinecone.mobile.android.service.RESTService;
import com.tenline.pinecone.mobile.android.service.ProgressTask;
import com.tenline.pinecone.platform.model.Device;

import android.content.Context;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.Toast;

/**
 * @author Bill
 *
 */
public class ActivateDeviceDialogBuilder extends AbstractDialogBuilder {
	
	public static final int DIALOG_ID = 0;

	/**
	 * 
	 * @param activity
	 */
	public ActivateDeviceDialogBuilder(final DeviceActivity activity) {
		super(activity);
		// TODO Auto-generated constructor stub
		View view = activity.getLayoutInflater().inflate(R.layout.activate_device, null);
		final FormEditText deviceCode = (FormEditText) view.findViewById(R.id.device_code_input);
		final FormEditText deviceName = (FormEditText) view.findViewById(R.id.device_name_input);
		((Button) view.findViewById(R.id.device_code_scan)).setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View view) {
				// TODO Auto-generated method stub
				new IntentIntegrator(activity).initiateScan();
			}
			
		});
		((Button) view.findViewById(R.id.app_ok)).setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View view) {
				// TODO Auto-generated method stub
				if (deviceCode.testValidity() && deviceName.testValidity()) {
					new DeviceCodeValidationTask(activity).execute(deviceCode, deviceName, 0, 0);
				}
			}
			
		});
		((Button) view.findViewById(R.id.app_cancel)).setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View view) {
				// TODO Auto-generated method stub
				dialog.cancel();
			}
			
		});
		setView(view);
		setTitle(R.string.device_activate);
		setIcon(android.R.drawable.ic_menu_rotate);
		setDialog(create());
	}
	
	/**
	 * 
	 * @author Bill
	 *
	 */
	private class DeviceCodeValidationTask extends ProgressTask {

		/**
		 * 
		 * @param context
		 */
		private DeviceCodeValidationTask(Context context) {
			super(context);
			// TODO Auto-generated constructor stub
		}

		@Override
		protected Object[] doTaskAction(Object... params) {
			// TODO Auto-generated method stub
			try {
				RESTService service = ((DeviceActivity) getDialog().getOwnerActivity()).getRESTService(); 
				String deviceCode = ((FormEditText) params[0]).getText().toString();
				ArrayList<?> devices = (ArrayList<?>) service.get("/device/search/codes?code=" + deviceCode); 
				params[2] = devices.size(); if (devices.size() > 0) params[3] = ((Device) devices.toArray()[0]).getId();
			} catch (Exception e) {Log.e(getClass().getSimpleName(), e.getMessage());} return params;
		}
		
		@Override
		protected void onPostExecute(Object[] result) {
			// TODO Auto-generated method stub
			if ((Integer) result[2] > 0) {new DeviceValidationTask(progress.getContext()).execute(result[0], result[1], result[3], 0);}
			else {((FormEditText) result[0]).setError(progress.getContext().getString(R.string.error_device_code_is_not_existed));}
			super.onPostExecute(result);
		}
		
	}
	
	/**
	 * 
	 * @author Bill
	 *
	 */
	private class DeviceValidationTask extends ProgressTask {

		/**
		 * 
		 * @param context
		 */
		private DeviceValidationTask(Context context) {
			super(context);
			// TODO Auto-generated constructor stub
		}

		@Override
		protected Object[] doTaskAction(Object... params) {
			// TODO Auto-generated method stub
			try {
				RESTService service = ((DeviceActivity) getDialog().getOwnerActivity()).getRESTService();
				params[3] = ((ArrayList<?>) service.get("/device/" + params[2] + "/user")).size();
			} catch (Exception e) {
				if (e.getMessage().equals("404 Not Found")) {params[3] = 0;}
				else Log.e(getClass().getSimpleName(), e.getMessage());
			} return params;
		}
		
		@Override
		protected void onPostExecute(Object[] result) {
			// TODO Auto-generated method stub
			if ((Integer) result[3] > 0) {((FormEditText) result[0]).setError(progress.getContext().getString(R.string.error_device_has_been_activated));}
			else {new ActivateDeviceTask(progress.getContext()).execute(result[1], result[2]);} super.onPostExecute(result);
		}
		
	}
	
	/**
	 * 
	 * @author Bill
	 *
	 */
	private class ActivateDeviceTask extends ProgressTask {

		/**
		 * 
		 * @param context
		 */
		private ActivateDeviceTask(Context context) {
			super(context);
			// TODO Auto-generated constructor stub
		}

		@Override
		protected Object[] doTaskAction(Object... params) {
			// TODO Auto-generated method stub
			try {
				DeviceActivity activity = (DeviceActivity) getDialog().getOwnerActivity();
				RESTService service = activity.getRESTService(); Device device = new Device();
				device.setName(((FormEditText) params[0]).getText().toString()); service.put("/device/" + params[1], device);
				service.post("/device/" + params[1] + "/user", "/user/" + activity.getIntent().getStringExtra("userId")); 	
			} catch (Exception e) {Log.e(getClass().getSimpleName(), e.getMessage());} return params;
		}
		
		@Override
		protected void onPostExecute(Object[] result) {
			// TODO Auto-generated method stub
			DeviceActivity activity = (DeviceActivity) getDialog().getOwnerActivity();
			activity.doInitListViewTask("/user/" + activity.getIntent().getStringExtra("userId") + "/devices");
			Toast.makeText(progress.getContext(), R.string.error_device_has_been_activated, Toast.LENGTH_LONG).show(); 
			getDialog().cancel(); super.onPostExecute(result);
		}
		
	}

}
