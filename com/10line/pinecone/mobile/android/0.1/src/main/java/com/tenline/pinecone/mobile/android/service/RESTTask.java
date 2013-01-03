/**
 * 
 */
package com.tenline.pinecone.mobile.android.service;

import android.os.AsyncTask;

/**
 * @author Bill
 *
 */
public class RESTTask extends AsyncTask<String, Void, Object[]> {
	
	private RESTTaskListener listener;
	
	public void setListener(RESTTaskListener listener) {
		this.listener = listener;
	}

	@Override
	protected Object[] doInBackground(String... params) {
		// TODO Auto-generated method stub
		return listener.doInBackground(params);
	}
	
	@Override
	protected void onPostExecute(Object[] result) {
		listener.onPostExecute(result);
	}
	
}
