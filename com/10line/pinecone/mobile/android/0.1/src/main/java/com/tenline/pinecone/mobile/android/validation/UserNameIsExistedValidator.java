/**
 * 
 */
package com.tenline.pinecone.mobile.android.validation;

import java.util.ArrayList;

import com.tenline.pinecone.mobile.android.RegisterActivity;
import com.tenline.pinecone.mobile.android.service.RESTService;

import android.util.Log;
import android.widget.EditText;

/**
 * @author Bill
 *
 */
public class UserNameIsExistedValidator extends Validator {
	
	private RegisterActivity activity;

	/**
	 * 
	 * @param _customErrorMessage
	 * @param activity
	 */
	public UserNameIsExistedValidator(String _customErrorMessage, RegisterActivity activity) {
		super(_customErrorMessage);
		// TODO Auto-generated constructor stub
		this.activity = activity;
	}

	@Override
	public boolean isValid(EditText et) {
		// TODO Auto-generated method stub
		try {
			RESTService service = (RESTService) activity.getHelper().getService();
			return !(((ArrayList<?>) service.get("/user/search/names?name=" + et.getText().toString())).size() > 0);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			Log.e(getClass().getSimpleName(), e.getMessage());
			return false;
		}
	}

}
