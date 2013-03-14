/**
 * 
 */
package com.tenline.pinecone.mobile.android.validation;

import android.text.TextUtils;
import android.widget.EditText;

/**
 * @author Bill
 *
 */
public class EqualityValidator extends Validator {
	
	private EditText objects;

	/**
	 * 
	 * @param _customErrorMessage
	 * @param objects
	 */
	public EqualityValidator(String _customErrorMessage, EditText objects) {
		super(_customErrorMessage);
		// TODO Auto-generated constructor stub
		this.objects = objects;
	}

	@Override
	public boolean isValid(EditText et) {
		// TODO Auto-generated method stub
		return TextUtils.equals(et.getText().toString(), objects.getText().toString());
	}

}
