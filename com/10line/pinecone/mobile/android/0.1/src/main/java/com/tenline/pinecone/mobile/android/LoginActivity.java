/**
 * 
 */
package com.tenline.pinecone.mobile.android;

import java.util.ArrayList;
import java.util.Arrays;

import com.tenline.pinecone.mobile.android.service.RESTService;
import com.tenline.pinecone.mobile.android.service.TaskFacade;
import com.tenline.pinecone.mobile.android.view.FormEditText;
import com.tenline.pinecone.platform.model.User;

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
	
	private static final String POST_TO_LOGIN = "postToLogin";
	private static final String GET_USER = "getUser";

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.login);
        final FormEditText userName = (FormEditText) findViewById(R.id.user_name_input);
        final FormEditText userPassword = (FormEditText) findViewById(R.id.user_password_input);
        findViewById(R.id.user_login).setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View view) {
				// TODO Auto-generated method stub
				Log.i(getClass().getSimpleName(), "onClick");
				if (userName.testValidity() && userPassword.testValidity()) {
					TaskFacade.initRESTTask(LoginActivity.this, POST_TO_LOGIN, userName.getText().toString(), userPassword.getText().toString());
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

	@Override
	@SuppressWarnings("rawtypes")
	public Object[] doInBackground(String... params) {
		// TODO Auto-generated method stub
		Object[] result = Arrays.asList(params).toArray();
		try {
			RESTService service = ((RESTService) helper.getService());
			if (result[0].equals(POST_TO_LOGIN)) {
				result[2] = service.post(RESTService.LOGIN_URL, result[1], result[2]);
			} else if (result[0].equals(GET_USER)) {
				result[1] = String.valueOf(((User) ((ArrayList) service.get("/user/search/names?name=" + result[1])).toArray()[0]).getId());
			}
		} catch (Exception e) {
			// TODO Auto-generated catch block
			Log.e(getClass().getSimpleName(), e.getMessage());
		}
		return result;
	}

	@Override
	public void onPostExecute(Object[] result) {
		// TODO Auto-generated method stub
		try {
			if (result[0].equals(POST_TO_LOGIN)) {
				if (!result[2].toString().contains("Reason: Bad credentials")) {TaskFacade.initRESTTask(this, GET_USER, result[1].toString());} 
				else {Toast.makeText(this, R.string.error_login, Toast.LENGTH_LONG).show();}	
			} else if (result[0].equals(GET_USER)) {
				Intent intent = new Intent(DeviceActivity.ACTIVITY_ACTION);
				intent.putExtra("userId", result[1].toString());
				startActivity(intent);
			}
		} catch (Exception e) {
			// TODO Auto-generated catch block
			Log.e(getClass().getSimpleName(), e.getMessage());
		}
	}

}
