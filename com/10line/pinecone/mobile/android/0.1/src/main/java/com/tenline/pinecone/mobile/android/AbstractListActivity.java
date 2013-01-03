/**
 * 
 */
package com.tenline.pinecone.mobile.android;

import java.util.Arrays;

import com.tenline.pinecone.mobile.android.service.RESTService;
import com.tenline.pinecone.mobile.android.service.RESTTaskListener;
import com.tenline.pinecone.mobile.android.service.ServiceConnectionHelper;
import com.tenline.pinecone.mobile.android.service.TaskFacade;

import android.app.ListActivity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;

/**
 * @author Bill
 *
 */
public abstract class AbstractListActivity extends ListActivity implements RESTTaskListener {
	
	private static final String GET_TO_LOGOUT = "getToLogout";

	protected ServiceConnectionHelper helper = new ServiceConnectionHelper();
	
	public ServiceConnectionHelper getHelper() {
		return helper;
	}
	
	protected void logout() {
		TaskFacade.initRESTTask(this, GET_TO_LOGOUT);
	}
	
	@Override
    protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		Log.i(getClass().getSimpleName(), "onCreate");
        if (!helper.isBound()) bindService(new Intent(this, RESTService.class), helper, Context.BIND_AUTO_CREATE);
    }
	
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		getMenuInflater().inflate(R.menu.options, menu);
	    return super.onCreateOptionsMenu(menu);
	}
	
	@Override
    protected void onDestroy() {
        super.onDestroy();
		Log.i(getClass().getSimpleName(), "onDestroy");
        if (helper.isBound()) {
            unbindService(helper);
            helper.setBound(false);
        }
    }
	
	protected abstract void buildListAdapter(Object[] result);
	
	public Object[] doInBackground(String... params) {
		// TODO Auto-generated method stub
		Object[] result = Arrays.asList(params).toArray();
		try {
			RESTService service = (RESTService) helper.getService();
			if (result[0].equals(GET_TO_LOGOUT)) {service.get(RESTService.LOGOUT_URL);}
		} catch (Exception e) {
			// TODO Auto-generated catch block
			Log.e(getClass().getSimpleName(), e.getMessage());
		}
		return result;
	}
	
	public void onPostExecute(Object[] result) {
		// TODO Auto-generated method stub
		try {
			if (result[0].equals(GET_TO_LOGOUT)) {
				Intent intent = new Intent(this, LoginActivity.class);
				intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
				startActivity(intent);
			}
		} catch (Exception e) {
			// TODO Auto-generated catch block
			Log.e(getClass().getSimpleName(), e.getMessage());
		}
	}

}
