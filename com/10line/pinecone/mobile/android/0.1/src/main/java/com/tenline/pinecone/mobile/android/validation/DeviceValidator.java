/**
 * 
 */
package com.tenline.pinecone.mobile.android.validation;

import com.tenline.pinecone.mobile.android.DeviceActivity;

/**
 * @author Bill
 *
 */
public abstract class DeviceValidator extends Validator {
	
	protected DeviceActivity activity;

	/**
	 * 
	 * @param _customErrorMessage
	 * @param activity
	 */
	public DeviceValidator(String _customErrorMessage, DeviceActivity activity) {
		super(_customErrorMessage);
		// TODO Auto-generated constructor stub
		this.activity = activity;
	}

}
