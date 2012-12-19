/**
 * 
 */
package com.tenline.pinecone.mobile.android;

import com.tenline.pinecone.mobile.android.service.RESTService;
import com.tenline.pinecone.mobile.android.view.FormEditText;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Toast;

/**
 * 
 * @author Bill
 *
 */
public class LoginActivity extends AbstractActivity {

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
		Log.i(getClass().getSimpleName(), "onCreate");
        setContentView(R.layout.login);
        final FormEditText userName = (FormEditText) findViewById(R.id.user_name_input);
        final FormEditText userPassword = (FormEditText) findViewById(R.id.user_password_input);
        findViewById(R.id.user_login).setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View view) {
				// TODO Auto-generated method stub
				Log.i(getClass().getSimpleName(), "onClick");
				if (userName.testValidity() && userPassword.testValidity()) {
					try {
						RESTService service = ((RESTService) helper.getService());
						if (!service.post(RESTService.LOGIN_URL, userName.getText().toString(), userPassword.getText().toString())
							.contains("Reason: Bad credentials")) {
							startActivity(new Intent(DeviceActivity.ACTIVITY_ACTION)); finish();
						} else {
							Toast.makeText(LoginActivity.this, R.string.error_login, Toast.LENGTH_LONG).show();
						}	
					} catch (Exception e) {
						Log.e(getClass().getSimpleName(), e.getMessage());
					}
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

