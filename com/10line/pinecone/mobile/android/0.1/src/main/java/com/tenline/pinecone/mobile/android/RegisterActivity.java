/**
 * 
 */
package com.tenline.pinecone.mobile.android;

import com.tenline.pinecone.mobile.android.validation.EqualityValidator;
import com.tenline.pinecone.mobile.android.view.FormEditText;

import android.app.Activity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;

/**
 * @author Bill
 *
 */
public class RegisterActivity extends Activity {
	
	public static final String ACTIVITY_ACTION = "com.tenline.pinecone.mobile.android.register";
	
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		Log.i(getClass().getSimpleName(), "onCreate");
        setContentView(R.layout.register);
        final FormEditText userEmail = (FormEditText) findViewById(R.id.user_email_input);
        final FormEditText userName = (FormEditText) findViewById(R.id.user_name_input);
        final FormEditText userPassword = (FormEditText) findViewById(R.id.user_password_input);
        final FormEditText userConfirm = (FormEditText) findViewById(R.id.user_confirm_input);
        userConfirm.addValidator(new EqualityValidator(getString(R.string.error_password_is_not_equal), userPassword));
        findViewById(R.id.user_submit).setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				Log.i(getClass().getSimpleName(), "onClick");
				if (userEmail.testValidity() && userName.testValidity() &&
					userPassword.testValidity() && userConfirm.testValidity()) { finish();}
			}
        	
        });
	}
	 
}
