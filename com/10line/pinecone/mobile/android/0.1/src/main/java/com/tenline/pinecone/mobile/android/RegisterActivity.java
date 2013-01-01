/**
 * 
 */
package com.tenline.pinecone.mobile.android;

import java.util.ArrayList;

import com.tenline.pinecone.mobile.android.service.RESTService;
import com.tenline.pinecone.mobile.android.validation.UserEmailValidator;
import com.tenline.pinecone.mobile.android.validation.EqualityValidator;
import com.tenline.pinecone.mobile.android.validation.UserNameValidator;
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
	
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
        setContentView(R.layout.register);
        final FormEditText userEmail = (FormEditText) findViewById(R.id.user_email_input);
        userEmail.addValidator(new UserEmailValidator(getString(R.string.error_user_email_is_existed), this));
        final FormEditText userName = (FormEditText) findViewById(R.id.user_name_input);
        userName.addValidator(new UserNameValidator(getString(R.string.error_user_name_is_existed), this));
        final FormEditText userPassword = (FormEditText) findViewById(R.id.user_password_input);
        final FormEditText userConfirm = (FormEditText) findViewById(R.id.user_confirm_input);
        userConfirm.addValidator(new EqualityValidator(getString(R.string.error_password_is_not_equal), userPassword));
        findViewById(R.id.user_submit).setOnClickListener(new OnClickListener() {

			@Override
			@SuppressWarnings("rawtypes")
			public void onClick(View v) {
				// TODO Auto-generated method stub
				Log.i(getClass().getSimpleName(), "onClick");
				if (userName.testValidity() && userEmail.testValidity() &&
					userPassword.testValidity() && userConfirm.testValidity()) { 
					try {
						RESTService service = (RESTService) helper.getService();
						User user = new User();
						user.setName(userName.getText().toString());
						user.setEmail(userEmail.getText().toString());
						user.setPassword(userPassword.getText().toString());
						service.post("/user", user);
						user = (User) ((ArrayList) service.get("/user/search/names?name=" + user.getName())).toArray()[0];
						Authority authority = new Authority();
						authority.setAuthority("ROLE_USER");
						authority.setUserName(userName.getText().toString());
						service.post("/authority", authority);
						authority = (Authority) ((ArrayList) service.get("/authority/search/userNames?userName=" + authority.getUserName())).toArray()[0];
						service.post("/authority/" + authority.getId() + "/user", "/user/" + user.getId());
						finish();
					} catch (Exception e) {
						// TODO Auto-generated catch block
						Log.e(getClass().getSimpleName(), e.getMessage());
					}
				}
			}
        	
        });
	}
	 
}
