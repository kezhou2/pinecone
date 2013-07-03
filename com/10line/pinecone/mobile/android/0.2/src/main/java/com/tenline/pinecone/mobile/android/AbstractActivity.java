/**
 * 
 */
package com.tenline.pinecone.mobile.android;

import com.actionbarsherlock.app.SherlockActivity;
import com.tenline.pinecone.mobile.android.receiver.NetworkReceiver;
import com.tenline.pinecone.mobile.android.service.RESTService;
import com.tenline.pinecone.mobile.android.service.ServiceConnectionHelper;

import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.net.ConnectivityManager;
import android.os.Bundle;
import android.util.Log;

/**
 * @author Bill
 *
 */
public abstract class AbstractActivity extends SherlockActivity {

	private NetworkReceiver receiver = new NetworkReceiver();

	protected ServiceConnectionHelper restHelper = new ServiceConnectionHelper();
	
	protected RESTService getRESTService() {return (RESTService) restHelper.getService();}
	
	@Override
    protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState); Log.i(getClass().getSimpleName(), "onCreate");
        if (!restHelper.isBound()) bindService(new Intent(this, RESTService.class), restHelper, Context.BIND_AUTO_CREATE);
        IntentFilter filter = new IntentFilter(); filter.addAction(ConnectivityManager.CONNECTIVITY_ACTION); registerReceiver(receiver, filter);
    }
	
	@Override
    protected void onDestroy() {
        super.onDestroy(); Log.i(getClass().getSimpleName(), "onDestroy");
        if (restHelper.isBound()) {unbindService(restHelper); restHelper.setBound(false);} unregisterReceiver(receiver);
    }

}
