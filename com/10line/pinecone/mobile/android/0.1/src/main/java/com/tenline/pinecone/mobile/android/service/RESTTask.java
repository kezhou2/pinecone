/**
 * 
 */
package com.tenline.pinecone.mobile.android.service;

import com.tenline.pinecone.mobile.android.R;

import android.app.ProgressDialog;
import android.content.Context;
import android.os.AsyncTask;

/**
 * @author Bill
 *
 */
public class RESTTask extends AsyncTask<String, Void, Object[]> {
	
	private RESTTaskListener listener;
	
	private ProgressDialog progress;
	
	public RESTTask(Context context) {
		progress = new ProgressDialog(context);
		progress.setCanceledOnTouchOutside(false);
		progress.setMessage(context.getString(R.string.progress_message));
		listener = (RESTTaskListener) context;
	}

	@Override
	protected Object[] doInBackground(String... params) {
		// TODO Auto-generated method stub
		return listener.doInBackground(params);
	}
	
	@Override  
    protected void onPreExecute() { 
        progress.show();
        super.onPreExecute();  
    }  
	
	@Override
	protected void onPostExecute(Object[] result) {
		listener.onPostExecute(result);
		progress.dismiss();
		super.onPostExecute(result);
	}
	
}
