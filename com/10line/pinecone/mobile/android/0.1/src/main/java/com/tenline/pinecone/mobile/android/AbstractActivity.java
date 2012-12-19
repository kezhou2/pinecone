/**
 * 
 */
package com.tenline.pinecone.mobile.android;

import com.tenline.pinecone.mobile.android.service.RESTService;
import com.tenline.pinecone.mobile.android.service.ServiceConnectionHelper;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;

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
    protected void onStart() {
        super.onStart();
        bindService(new Intent(this, RESTService.class), helper, Context.BIND_AUTO_CREATE);
    }
	
	@Override
    protected void onStop() {
        super.onStop();
        if (helper.isBound()) {
            unbindService(helper);
            helper.setBound(false);
        }
    }

}
