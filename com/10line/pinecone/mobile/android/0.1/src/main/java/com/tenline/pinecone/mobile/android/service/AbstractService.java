/**
 * 
 */
package com.tenline.pinecone.mobile.android.service;

import android.app.Service;
import android.content.Intent;
import android.os.Binder;
import android.os.IBinder;

/**
 * @author Bill
 *
 */
public abstract class AbstractService extends Service {
	
	protected String baseUrl = "http://pinecone-service.cloudfoundry.com";
	
	/**
	 * 
	 * @author Bill
	 *
	 */
	public class ServiceBinder extends Binder {
		AbstractService getService() {return AbstractService.this;}
	}

	@Override
	public IBinder onBind(Intent intent) {
		// TODO Auto-generated method stub
		return new ServiceBinder();
	}

}
