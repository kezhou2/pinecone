/**
 * 
 */
package com.tenline.pinecone.mobile.android;

import java.util.ArrayList;
import java.util.Arrays;

import com.tenline.pinecone.mobile.android.service.RESTService;
import com.tenline.pinecone.mobile.android.service.TaskFacade;
import com.tenline.pinecone.mobile.android.validation.EqualityValidator;
import com.tenline.pinecone.mobile.android.view.FormEditText;
import com.tenline.pinecone.platform.model.Authority;
import com.tenline.pinecone.platform.model.User;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;

/**
 * @author Bill
 *
 */
public class RegisterActivity extends AbstractActivity {
	
	public static final String ACTIVITY_ACTION = "com.tenline.pinecone.mobile.android.register";
	
	private static final String POST_USER = "postUser";
	private static final String POST_AUTHORITY = "postAuthority";
	private static final String POST_AUTHORITY_WITH_USER = "postAuthorityWithUser";
	private static final String GET_USER = "getUser";
	private static final String GET_AUTHORITY = "getAuthority";
	private static final String VALIDATE_USER_WITH_NAME = "validateUserWithName";
	private static final String VALIDATE_USER_WITH_EMAIL = "validateUserWithEmail";
	
	private FormEditText userEmail;
	private FormEditText userName;
	private FormEditText userPassword;
	
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
        setContentView(R.layout.register);
        userEmail = (FormEditText) findViewById(R.id.user_email_input);
        userName = (FormEditText) findViewById(R.id.user_name_input);
        userPassword = (FormEditText) findViewById(R.id.user_password_input);
        final FormEditText userConfirm = (FormEditText) findViewById(R.id.user_confirm_input);
        userConfirm.addValidator(new EqualityValidator(getString(R.string.error_password_is_not_equal), userPassword));
        findViewById(R.id.user_submit).setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				Log.i(getClass().getSimpleName(), "onClick");
				if (userName.testValidity() && userEmail.testValidity() && userPassword.testValidity() && userConfirm.testValidity()) { 
					TaskFacade.initRESTTask(RegisterActivity.this, VALIDATE_USER_WITH_NAME, userName.getText().toString());
				}
			}
        	
        });
	}

	@Override
	@SuppressWarnings("rawtypes")
	public Object[] doInBackground(String... params) {
		// TODO Auto-generated method stub
		Object[] result = Arrays.asList(params).toArray();
		try {
			RESTService service = (RESTService) helper.getService();
			if (result[0].equals(POST_USER)) {
				User user = new User();
				user.setName(result[1].toString());
				user.setEmail(result[2].toString());
				user.setPassword(result[3].toString());
				service.post("/user", user);
			} else if (result[0].equals(POST_AUTHORITY)) {
				Authority authority = new Authority();
				authority.setAuthority("ROLE_USER");
				authority.setUserName(result[1].toString());
				service.post("/authority", authority);
			} else if (result[0].equals(POST_AUTHORITY_WITH_USER)) {
				service.post("/authority/" + result[1] + "/user", "/user/" + result[2]);
			} else if (result[0].equals(GET_USER)) {
				result[2] = String.valueOf(((User) ((ArrayList) service.get("/user/search/names?name=" + result[1])).toArray()[0]).getId());
			} else if (result[0].equals(GET_AUTHORITY)) {
				result[1] = String.valueOf(((Authority) ((ArrayList) service.get("/authority/search/userNames?userName=" + result[1])).toArray()[0]).getId());
			} else if (result[0].equals(VALIDATE_USER_WITH_NAME)) {
				result[1] = String.valueOf(((ArrayList<?>) service.get("/user/search/names?name=" + result[1])).size());
			} else if (result[0].equals(VALIDATE_USER_WITH_EMAIL)) {
				result[1] = String.valueOf(((ArrayList<?>) service.get("/user/search/emails?email=" + result[1])).size());
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
			if (result[0].equals(POST_USER)) {TaskFacade.initRESTTask(this, GET_USER, result[1].toString(), "");}
			else if (result[0].equals(POST_AUTHORITY)) {TaskFacade.initRESTTask(this, GET_AUTHORITY, result[1].toString(), result[2].toString());}
			else if (result[0].equals(POST_AUTHORITY_WITH_USER)) {finish();}
			else if (result[0].equals(GET_USER)) {TaskFacade.initRESTTask(this, POST_AUTHORITY, result[1].toString(), result[2].toString());}
			else if (result[0].equals(GET_AUTHORITY)) {TaskFacade.initRESTTask(this, POST_AUTHORITY_WITH_USER, result[1].toString(), result[2].toString());}
			else if (result[0].equals(VALIDATE_USER_WITH_NAME)) {
				if (Integer.valueOf(result[1].toString()) > 0) {userName.setError(getString(R.string.error_user_name_is_existed));} 
				else {TaskFacade.initRESTTask(this, VALIDATE_USER_WITH_EMAIL, userEmail.getText().toString());}
			} else if (result[0].equals(VALIDATE_USER_WITH_EMAIL)) {
				if (Integer.valueOf(result[1].toString()) > 0) {userEmail.setError(getString(R.string.error_user_email_is_existed));} 
				else {TaskFacade.initRESTTask(this, POST_USER, userName.getText().toString(), userEmail.getText().toString(), userPassword.getText().toString());}
			}
		} catch (Exception e) {
			// TODO Auto-generated catch block
			Log.e(getClass().getSimpleName(), e.getMessage());
		}
	}
	 
}
