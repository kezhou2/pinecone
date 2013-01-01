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

	protected ServiceConnectionHelper helper = new ServiceConnectionHelper();
	
	public ServiceConnectionHelper getHelper() {
		return helper;
	}
	
	@Override
    protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		Log.i(getClass().getSimpleName(), "onCreate");
        if (!helper.isBound()) bindService(new Intent(this, RESTService.class), helper, Context.BIND_AUTO_CREATE);
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

}
