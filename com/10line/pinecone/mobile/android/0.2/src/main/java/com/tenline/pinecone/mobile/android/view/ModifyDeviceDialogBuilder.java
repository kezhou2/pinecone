/**
 * 
 */
package com.tenline.pinecone.mobile.android.view;

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
public class ModifyDeviceDialogBuilder extends AbstractDialogBuilder {

	public static final int DIALOG_ID = 2;
	
	/**
	 * 
	 * @param activity
	 */
	public ModifyDeviceDialogBuilder(final DeviceActivity activity) {
		super(activity);
		// TODO Auto-generated constructor stub
		View view = activity.getLayoutInflater().inflate(R.layout.modify_device, null);
		final FormEditText deviceName = (FormEditText) view.findViewById(R.id.device_name_input);
		((Button) view.findViewById(R.id.app_ok)).setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View view) {
				// TODO Auto-generated method stub
				if (deviceName.testValidity()) {new ModifyDeviceTask(activity).execute(deviceName);}
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
		setTitle(activity.getIntent().getStringExtra("deviceName"));
		setIcon(android.R.drawable.ic_menu_edit);
		setDialog(create());
	}
	
	/**
	 * 
	 * @author Bill
	 *
	 */
	private class ModifyDeviceTask extends ProgressTask {

		/**
		 * 
		 * @param context
		 */
		private ModifyDeviceTask(Context context) {
			super(context);
			// TODO Auto-generated constructor stub
		}

		@Override
		protected Object[] doTaskAction(Object... params) {
			// TODO Auto-generated method stub
			try {
				DeviceActivity activity = (DeviceActivity) getDialog().getOwnerActivity();
				RESTService service = activity.getRESTService(); Device device = new Device();
				device.setName(((FormEditText) params[0]).getText().toString()); 
				service.put("/device/" + activity.getIntent().getStringExtra("deviceId"), device);
			} catch (Exception e) {Log.e(getClass().getSimpleName(), e.getMessage());} return params;
		}
		
		@Override
		protected void onPostExecute(Object[] result) {
			// TODO Auto-generated method stub
			DeviceActivity activity = (DeviceActivity) getDialog().getOwnerActivity();
			activity.doInitListViewTask("/user/" + activity.getIntent().getStringExtra("userId") + "/devices");
			Toast.makeText(progress.getContext(), R.string.device_modify_tips, Toast.LENGTH_LONG).show(); 
			getDialog().cancel(); super.onPostExecute(result);
		}
		
	}

}
