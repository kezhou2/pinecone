/**
 * 
 */
package com.tenline.pinecone.mobile.android;

import java.util.ArrayList;

import com.tenline.pinecone.mobile.android.service.RESTService;
import com.tenline.pinecone.mobile.android.service.ServiceConnectionHelper;

import android.app.ListActivity;
import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;

/**
 * @author Bill
 *
 */
public abstract class AbstractListActivity extends ListActivity {

	protected ServiceConnectionHelper helper = new ServiceConnectionHelper();
	
	public ServiceConnectionHelper getHelper() {
		return helper;
	}
	
	protected void logout() {
		try {
			RESTService service = (RESTService) helper.getService();
			service.get(RESTService.LOGOUT_URL);
			Intent intent = new Intent(this, LoginActivity.class);
			intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
			startActivity(intent);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			Log.e(getClass().getSimpleName(), e.getMessage());
		}
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
	
	public void initFetchTask(String url) {
		new FetchTask().execute(url);
	}
	
	protected abstract void buildListAdapter(ArrayList<?> result);
	
	/**
	 * 
	 * @author Bill
	 *
	 */
	private class FetchTask extends AsyncTask<String, Void, ArrayList<?>> {

		@Override
		protected ArrayList<?> doInBackground(String... params) {
			// TODO Auto-generated method stub
			ArrayList<?> result = null; 
			try {
				while (!helper.isBound()) {Thread.sleep(100);}
				RESTService service = (RESTService) helper.getService();
				result = (ArrayList<?>) service.get(params[0]);			
			} catch (Exception e) {
				// TODO Auto-generated catch block
				Log.e(getClass().getSimpleName(), e.getMessage());
			}
			return result;
		}
		
		@Override
		protected void onPostExecute(ArrayList<?> result) {
			buildListAdapter(result);
	    }
		
	}

}
