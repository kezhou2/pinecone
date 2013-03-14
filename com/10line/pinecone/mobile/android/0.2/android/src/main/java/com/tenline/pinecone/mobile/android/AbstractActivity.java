/**
 * 
 */
package com.tenline.pinecone.mobile.android;

import com.tenline.pinecone.mobile.android.service.RESTService;
import com.tenline.pinecone.mobile.android.service.ServiceConnectionHelper;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;

/**
 * @author Bill
 *
 */
public abstract class AbstractActivity extends Activity {

	protected ServiceConnectionHelper restHelper = new ServiceConnectionHelper();
	
	protected RESTService getRESTService() {return (RESTService) restHelper.getService();}
	
	@Override
    protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState); Log.i(getClass().getSimpleName(), "onCreate");
        if (!restHelper.isBound()) bindService(new Intent(this, RESTService.class), restHelper, Context.BIND_AUTO_CREATE);
    }
	
	@Override
    protected void onDestroy() {
        super.onDestroy(); Log.i(getClass().getSimpleName(), "onDestroy");
        if (restHelper.isBound()) {unbindService(restHelper); restHelper.setBound(false);}
    }

}
