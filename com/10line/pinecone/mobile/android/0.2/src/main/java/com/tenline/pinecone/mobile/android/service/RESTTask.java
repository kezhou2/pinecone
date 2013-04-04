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
public abstract class RESTTask extends AsyncTask<Object, Object, Object[]> {
	
	protected ProgressDialog progress;
	
	/**
	 * 
	 * @param context
	 */
	public RESTTask(Context context) {
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
	protected void onPostExecute(Object[] result) {
		progress.dismiss(); super.onPostExecute(result);
	}
	
}
