/**
 * 
 */
package com.tenline.pinecone.mobile.android.validation;

import java.util.ArrayList;

import com.tenline.pinecone.mobile.android.DeviceActivity;
import com.tenline.pinecone.mobile.android.R;
import com.tenline.pinecone.mobile.android.service.RESTService;
import com.tenline.pinecone.platform.model.Device;

import android.util.Log;
import android.widget.EditText;

/**
 * @author Bill
 *
 */
public class DeviceCodeValidator extends DeviceValidator {

	/**
	 * 
	 * @param _customErrorMessage
	 * @param activity
	 */
	public DeviceCodeValidator(String _customErrorMessage, DeviceActivity activity) {
		super(_customErrorMessage, activity);
		// TODO Auto-generated constructor stub
	}

	@Override
	@SuppressWarnings("unchecked")
	public boolean isValid(EditText et) {
		// TODO Auto-generated method stub
		try {
			RESTService service = (RESTService) activity.getHelper().getService();
			ArrayList<Device> devices = (ArrayList<Device>) service.get("/device/search/codes?code=" + et.getText().toString());
			if (devices.size() > 0) {
				if (((ArrayList<?>)service.get("/device/"+((Device)devices.toArray()[0]).getId()+"/user")).size() > 0) {
					errorMessage = activity.getString(R.string.error_device_has_been_activated); return false;
				} else {return true;}
			} else {return false;}
		} catch (Exception e) {
			// TODO Auto-generated catch block
			Log.e(getClass().getSimpleName(), e.getMessage());
			if (e.getMessage().equals("404 Not Found")) return true;
			else return false;
		}
	}

}
