/**
 * 
 */
package com.tenline.pinecone.mobile.android;

import com.tenline.pinecone.mobile.android.view.FormEditText;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;

/**
 * 
 * @author Bill
 *
 */
public class LoginActivity extends Activity {

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
		Log.i(getClass().getSimpleName(), "onCreate");
        setContentView(R.layout.login);
        final FormEditText userEmail = (FormEditText) findViewById(R.id.user_email_input);
        final FormEditText userPassword = (FormEditText) findViewById(R.id.user_password_input);
        findViewById(R.id.user_login).setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View view) {
				// TODO Auto-generated method stub
				Log.i(getClass().getSimpleName(), "onClick");
				if (userEmail.testValidity() && userPassword.testValidity()) {
					startActivity(new Intent(DeviceActivity.ACTIVITY_ACTION));
					finish();
				}
			}
        	
        });
        findViewById(R.id.user_register).setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View view) {
				// TODO Auto-generated method stub
				Log.i(getClass().getSimpleName(), "onClick");
				startActivity(new Intent(RegisterActivity.ACTIVITY_ACTION));
			}
        	
        });
    }

}

