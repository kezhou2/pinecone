/**
 * 
 */
package com.tenline.pinecone.mobile.android.view;

import java.util.ArrayList;

import com.tenline.pinecone.mobile.android.DeviceActivity;
import com.tenline.pinecone.mobile.android.R;
import com.tenline.pinecone.mobile.android.service.RESTService;
import com.tenline.pinecone.mobile.android.validation.DeviceCodeValidator;
import com.tenline.pinecone.mobile.android.validation.DeviceNameValidator;
import com.tenline.pinecone.platform.model.Device;

import android.app.AlertDialog;
import android.app.AlertDialog.Builder;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.Toast;

/**
 * @author Bill
 *
 */
public class AddDeviceDialogBuilder extends Builder {
	
	public static final int DIALOG_ID = 0;
	private AlertDialog dialog;

	/**
	 * 
	 * @param activity
	 */
	public AddDeviceDialogBuilder(final DeviceActivity activity) {
		super(activity);
		// TODO Auto-generated constructor stub
		View view = activity.getLayoutInflater().inflate(R.layout.add_device, null);
		final FormEditText deviceCode = (FormEditText) view.findViewById(R.id.device_code_input);
		deviceCode.addValidator(new DeviceCodeValidator(activity.getString(R.string.error_device_code_is_not_existed), activity));
		final FormEditText deviceName = (FormEditText) view.findViewById(R.id.device_name_input);
		deviceName.addValidator(new DeviceNameValidator(activity.getString(R.string.error_device_name_is_existed), activity));
		((Button) view.findViewById(R.id.app_ok)).setOnClickListener(new OnClickListener() {

			@Override
			@SuppressWarnings("unchecked")
			public void onClick(View view) {
				// TODO Auto-generated method stub
				try {
					if (deviceCode.testValidity() && deviceName.testValidity()) {
						RESTService service = (RESTService) activity.getHelper().getService();
						String filter = "/device/search/codes?code=" + deviceCode.getText().toString();
						Device device = (Device) ((ArrayList<Device>)service.get(filter)).toArray()[0];
						service.post("/device/" + device.getId() + "/user", "/user/" + activity.getIntent().getStringExtra("userId"));
						device.setName(deviceName.getText().toString()); service.put("/device/" + device.getId(), device);
						activity.initFetchTask("/user/" + activity.getIntent().getStringExtra("userId") + "/devices");
						Toast.makeText(activity, R.string.device_add, Toast.LENGTH_LONG).show(); dialog.cancel();
					}
				} catch (Exception e) {
					// TODO Auto-generated catch block
					Log.e(getClass().getSimpleName(), e.getMessage());
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
		setTitle(R.string.device_add);
		setIcon(android.R.drawable.ic_menu_add);
		setDialog(create());
	}

	/**
	 * @return the dialog
	 */
	public AlertDialog getDialog() {
		return dialog;
	}

	/**
	 * @param dialog the dialog to set
	 */
	public void setDialog(AlertDialog dialog) {
		this.dialog = dialog;
	}

}
