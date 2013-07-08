/**
 * 
 */
package com.tenline.pinecone.mobile.android.service;

import com.tenline.pinecone.mobile.android.R;

import android.app.ProgressDialog;
import android.content.Context;
import android.net.ConnectivityManager;
import android.os.AsyncTask;
import android.widget.Toast;

/**
 * @author Bill
 *
 */
public abstract class ProgressTask extends AsyncTask<Object, Object, Object[]> {
	
	protected ProgressDialog progress;
	
	/**
	 * 
	 * @param context
	 */
	public ProgressTask(Context context) {
		progress = new ProgressDialog(context);
		progress.setCancelable(false); progress.setCanceledOnTouchOutside(false);
		progress.setMessage(context.getString(R.string.progress_message));
	}
	
	@Override  
    protected void onPreExecute() { 
		super.onPreExecute(); progress.show();
		if (((ConnectivityManager) progress.getContext().getSystemService(Context.CONNECTIVITY_SERVICE)).getActiveNetworkInfo() == null) {
			if (cancel(true)) {progress.dismiss(); Toast.makeText(progress.getContext(), R.string.network_turn_off, Toast.LENGTH_LONG).show();};
		}
    }
	
	@Override
	protected Object[] doInBackground(Object... params) {
		if (!isCancelled()) {params = doTaskAction(params);} return params;
	}
	
	protected abstract Object[] doTaskAction(Object... params);
	
	@Override
	protected void onPostExecute(Object[] result) {
		progress.dismiss(); super.onPostExecute(result);
	}
	
}
