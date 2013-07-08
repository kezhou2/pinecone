/**
 * 
 */
package com.tenline.pinecone.mobile.android;

import java.util.ArrayList;

import com.actionbarsherlock.view.MenuItem;
import com.tenline.pinecone.mobile.android.service.ProgressTask;
import com.tenline.pinecone.mobile.android.validation.EqualityValidator;
import com.tenline.pinecone.mobile.android.view.FormEditText;
import com.tenline.pinecone.platform.model.Authority;
import com.tenline.pinecone.platform.model.User;

import android.content.Context;
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
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState); setContentView(R.layout.register);
		getSupportActionBar().setDisplayHomeAsUpEnabled(true); 
		getSupportActionBar().setHomeButtonEnabled(true); 
		getSupportActionBar().setTitle(R.string.user_register);
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
				if (userName.testValidity() && userEmail.testValidity() && userPassword.testValidity() && userConfirm.testValidity()) { 
					new UserNameValidationTask(RegisterActivity.this).execute(userName, userEmail, userPassword, 0);
				}
			}
        	
        });
	}
	
	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		switch(item.getItemId()) {case android.R.id.home: finish(); break;}
		return super.onOptionsItemSelected(item);
	}
	
	/**
	 * 
	 * @author Bill
	 *
	 */
	private class UserNameValidationTask extends ProgressTask {

		/**
		 * 
		 * @param context
		 */
		private UserNameValidationTask(Context context) {
			super(context);
			// TODO Auto-generated constructor stub
		}

		@Override
		protected Object[] doTaskAction(Object... params) {
			// TODO Auto-generated method stub
			try {
				FormEditText userName = (FormEditText) params[0];
				ArrayList<?> users = (ArrayList<?>) getRESTService().get("/user/search/names?name=" + userName.getText().toString()); 
				params[3] = users.size();
			} catch (Exception e) {Log.e(getClass().getSimpleName(), e.getMessage());} return params;
		}
		
		@Override
		protected void onPostExecute(Object[] result) {
			// TODO Auto-generated method stub
			if ((Integer) result[3] > 0) {((FormEditText) result[0]).setError(getString(R.string.error_user_name_is_existed));} 
			else {new UserEmailValidationTask(progress.getContext()).execute(result[0], result[1], result[2], 0);} super.onPostExecute(result);
		}
		
	}
	
	/**
	 * 
	 * @author Bill
	 *
	 */
	private class UserEmailValidationTask extends ProgressTask {

		/**
		 * 
		 * @param context
		 */
		private UserEmailValidationTask(Context context) {
			super(context);
			// TODO Auto-generated constructor stub
		}

		@Override
		protected Object[] doTaskAction(Object... params) {
			// TODO Auto-generated method stub
			try {
				FormEditText userEmail = (FormEditText) params[1];
				ArrayList<?> users = (ArrayList<?>) getRESTService().get("/user/search/emails?email=" + userEmail.getText().toString()); 
				params[3] = users.size();
			} catch (Exception e) {Log.e(getClass().getSimpleName(), e.getMessage());} return params;
		}
		
		@Override
		protected void onPostExecute(Object[] result) {
			// TODO Auto-generated method stub
			if ((Integer) result[3] > 0) {((FormEditText) result[1]).setError(getString(R.string.error_user_email_is_existed));} 
			else {new RegisterTask(progress.getContext()).execute(result[0], result[1], result[2]);} super.onPostExecute(result);
		}
		
	}
	
	/**
	 * 
	 * @author Bill
	 *
	 */
	private class RegisterTask extends ProgressTask {

		/**
		 * 
		 * @param context
		 */
		private RegisterTask(Context context) {
			super(context);
			// TODO Auto-generated constructor stub
		}

		@Override
		protected Object[] doTaskAction(Object... params) {
			// TODO Auto-generated method stub
			try {
				String userName = ((FormEditText) params[0]).getText().toString();
				String userEmail = ((FormEditText) params[1]).getText().toString();
				String userPassword = ((FormEditText) params[2]).getText().toString();
				User user = new User(); user.setName(userName); user.setEmail(userEmail);
				user.setPassword(userPassword); getRESTService().post("/user", user);
				user = (User) ((ArrayList<?>) getRESTService().get("/user/search/names?name=" + userName)).toArray()[0];
				Authority authority = new Authority(); authority.setAuthority("ROLE_USER");
				authority.setUserName(userName); getRESTService().post("/authority", authority);
				authority = (Authority) ((ArrayList<?>) getRESTService().get("/authority/search/userNames?userName=" + userName)).toArray()[0];
				getRESTService().post("/authority/" + authority.getId() + "/user", "/user/" + user.getId());
			} catch (Exception e) {Log.e(getClass().getSimpleName(), e.getMessage());} return params;
		}
		
		@Override
		protected void onPostExecute(Object[] result) {
			// TODO Auto-generated method stub
			finish(); super.onPostExecute(result);
		}
		
	}
	 
}
