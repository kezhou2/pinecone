/**
 * 
 */
package com.tenline.pinecone.mobile.android.view;

import com.tenline.pinecone.mobile.android.DeviceActivity;
import com.tenline.pinecone.mobile.android.R;
import com.tenline.pinecone.mobile.android.service.ProgressTask;

import android.content.Context;
import android.content.DialogInterface;
import android.content.DialogInterface.OnClickListener;
import android.util.Log;
import android.widget.Toast;

/**
 * @author Bill
 *
 */
public class DeleteDeviceDialogBuilder extends AbstractDialogBuilder {

	public static final int DIALOG_ID = 3;
	
	/**
	 * 
	 * @param activity
	 */
	public DeleteDeviceDialogBuilder(final DeviceActivity activity) {
		super(activity);
		// TODO Auto-generated constructor stub
		setPositiveButton(R.string.app_ok, new OnClickListener() {

			@Override
			public void onClick(DialogInterface dialog, int which) {
				// TODO Auto-generated method stub
				new DeleteDeviceTask(activity).execute(activity.getIntent().getStringExtra("deviceId")); dialog.cancel();
			}
			
		});
		setNegativeButton(R.string.app_cancel, new OnClickListener() {

			@Override
			public void onClick(DialogInterface dialog, int which) {
				// TODO Auto-generated method stub
				dialog.cancel();
			}
			
		});
		setMessage(activity.getString(R.string.device_delete_message));
		setTitle(activity.getIntent().getStringExtra("deviceName"));
		setIcon(android.R.drawable.ic_menu_delete);
		setDialog(create());
	}
	
	/**
	 * 
	 * @author Bill
	 *
	 */
	private class DeleteDeviceTask extends ProgressTask {

		/**
		 * 
		 * @param context
		 */
		private DeleteDeviceTask(Context context) {
			super(context);
			// TODO Auto-generated constructor stub
		}

		@Override
		protected Object[] doTaskAction(Object... params) {
			// TODO Auto-generated method stub
			try {((DeviceActivity) getDialog().getOwnerActivity()).getRESTService().delete("/device/" + params[0]);} 
			catch (Exception e) {Log.e(getClass().getSimpleName(), e.getMessage());} return params;
		}
		
		@Override
		protected void onPostExecute(Object[] result) {
			// TODO Auto-generated method stub
			DeviceActivity activity = ((DeviceActivity) getDialog().getOwnerActivity());
			activity.doInitListViewTask("/user/" + activity.getIntent().getStringExtra("userId") + "/devices");
			Toast.makeText(progress.getContext(), R.string.device_delete_tips, Toast.LENGTH_LONG).show(); 
			super.onPostExecute(result);
		}
		
	}

}
