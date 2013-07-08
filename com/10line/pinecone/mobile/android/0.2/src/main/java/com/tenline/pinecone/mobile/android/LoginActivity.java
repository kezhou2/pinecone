/**
 * 
 */
package com.tenline.pinecone.mobile.android;

import java.util.ArrayList;

import com.tenline.pinecone.mobile.android.service.RESTService;
import com.tenline.pinecone.mobile.android.service.ProgressTask;
import com.tenline.pinecone.mobile.android.view.FormEditText;
import com.tenline.pinecone.platform.model.User;

import android.content.Context;
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
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState); setContentView(R.layout.login);
        final FormEditText userName = (FormEditText) findViewById(R.id.user_name_input);
        final FormEditText userPassword = (FormEditText) findViewById(R.id.user_password_input);
        findViewById(R.id.user_login).setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View view) {
				// TODO Auto-generated method stub
				Log.i(getClass().getSimpleName(), "onClick");
				if (userName.testValidity() && userPassword.testValidity()) {
					new LoginValidationTask(LoginActivity.this).execute(userName.getText().toString(), userPassword.getText().toString());
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
    
    /**
     * 
     * @author Bill
     *
     */
    private class LoginValidationTask extends ProgressTask {

    	/**
    	 * 
    	 * @param context
    	 */
		private LoginValidationTask(Context context) {
			super(context);
			// TODO Auto-generated constructor stub
		}

		@Override
		protected Object[] doTaskAction(Object... params) {
			// TODO Auto-generated method stub
			try {params[1] = getRESTService().post(RESTService.LOGIN_URL, params[0], params[1]);}
			catch (Exception e) {Log.e(getClass().getSimpleName(), e.getMessage());} return params;
		}
		
		@Override
		protected void onPostExecute(Object[] result) {
			// TODO Auto-generated method stub
			if (!result[1].toString().contains("not successful")) {new LoginTask(progress.getContext()).execute(result[0]);} 
			else {Toast.makeText(progress.getContext(), R.string.error_login, Toast.LENGTH_LONG).show();} super.onPostExecute(result);
		}
    	
    }
    
    /**
     * 
     * @author Bill
     *
     */
    private class LoginTask extends ProgressTask {

    	/**
    	 * 
    	 * @param context
    	 */
		private LoginTask(Context context) {
			super(context);
			// TODO Auto-generated constructor stub
		}

		@Override
		protected Object[] doTaskAction(Object... params) {
			// TODO Auto-generated method stub
			try {params[0] = ((User) ((ArrayList<?>) getRESTService().get("/user/search/names?name=" + params[0])).toArray()[0]).getId();} 
			catch (Exception e) {Log.e(getClass().getSimpleName(), e.getMessage());} return params;
		}
		
		@Override
		protected void onPostExecute(Object[] result) {
			// TODO Auto-generated method stub
			Intent intent = new Intent(DeviceActivity.ACTIVITY_ACTION); 
			intent.putExtra("userId", result[0].toString()); 
			startActivity(intent); super.onPostExecute(result);
		}
    	
    }

}
