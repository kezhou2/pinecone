/**
 * 
 */
package com.tenline.pinecone.mobile.android.validation;

import com.tenline.pinecone.mobile.android.RegisterActivity;

/**
 * @author Bill
 *
 */
public abstract class UserValidator extends Validator {
	
	protected RegisterActivity activity;

	/**
	 * 
	 * @param _customErrorMessage
	 * @param activity
	 */
	public UserValidator(String _customErrorMessage, RegisterActivity activity) {
		super(_customErrorMessage);
		// TODO Auto-generated constructor stub
		this.activity = activity;
	}

}
