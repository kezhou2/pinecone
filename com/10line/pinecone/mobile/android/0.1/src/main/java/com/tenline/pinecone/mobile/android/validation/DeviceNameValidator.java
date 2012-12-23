/**
 * 
 */
package com.tenline.pinecone.mobile.android.validation;

import java.util.ArrayList;

import com.tenline.pinecone.mobile.android.DeviceActivity;
import com.tenline.pinecone.mobile.android.service.RESTService;

import android.util.Log;
import android.widget.EditText;

/**
 * @author Bill
 *
 */
public class DeviceNameValidator extends DeviceValidator {

	/**
	 * 
	 * @param _customErrorMessage
	 * @param activity
	 */
	public DeviceNameValidator(String _customErrorMessage, DeviceActivity activity) {
		super(_customErrorMessage, activity);
		// TODO Auto-generated constructor stub
	}

	@Override
	public boolean isValid(EditText et) {
		// TODO Auto-generated method stub
		try {
			RESTService service = (RESTService) activity.getHelper().getService();
			return !(((ArrayList<?>) service.get("/device/search/names?name=" + et.getText().toString())).size() > 0);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			Log.e(getClass().getSimpleName(), e.getMessage());
			return false;
		}
	}

}
