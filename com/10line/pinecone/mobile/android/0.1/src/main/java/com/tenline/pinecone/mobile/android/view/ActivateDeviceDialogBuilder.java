/**
 * 
 */
package com.tenline.pinecone.mobile.android.view;

import com.tenline.pinecone.mobile.android.DeviceActivity;
import com.tenline.pinecone.mobile.android.R;
import com.tenline.pinecone.mobile.android.service.TaskFacade;

import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;

/**
 * @author Bill
 *
 */
public class ActivateDeviceDialogBuilder extends AbstractBuilder {
	
	public static final int DIALOG_ID = 0;
	
	private FormEditText deviceCode;
	private FormEditText deviceName;
	
	public FormEditText getDeviceCode() {
		return deviceCode;
	}
	
	public FormEditText getDeviceName() {
		return deviceName;
	}

	/**
	 * 
	 * @param activity
	 */
	public ActivateDeviceDialogBuilder(final DeviceActivity activity) {
		super(activity);
		// TODO Auto-generated constructor stub
		View view = activity.getLayoutInflater().inflate(R.layout.activate_device, null);
		deviceCode = (FormEditText) view.findViewById(R.id.device_code_input);
		deviceName = (FormEditText) view.findViewById(R.id.device_name_input);
		((Button) view.findViewById(R.id.app_ok)).setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View view) {
				// TODO Auto-generated method stub
				if (deviceCode.testValidity() && deviceName.testValidity()) { 
					TaskFacade.initRESTTask(activity, DeviceActivity.VALIDATE_DEVICE_WITH_CODE, deviceCode.getText().toString(), "");
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
		setCancelable(false);
		setTitle(R.string.device_activate);
		setIcon(android.R.drawable.ic_menu_rotate);
		setDialog(create());
	}

}
