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
import android.util.Log;

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
			Intent intent = new Intent();
			intent.setClass(AbstractListActivity.this, LoginActivity.class);
			startActivity(intent); finish();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			Log.e(getClass().getSimpleName(), e.getMessage());
		}
	}
	
	@Override
    protected void onStart() {
        super.onStart();
		Log.i(getClass().getSimpleName(), "onStart");
        bindService(new Intent(this, RESTService.class), helper, Context.BIND_AUTO_CREATE);
    }
	
	@Override
    protected void onStop() {
        super.onStop();
		Log.i(getClass().getSimpleName(), "onStop");
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
