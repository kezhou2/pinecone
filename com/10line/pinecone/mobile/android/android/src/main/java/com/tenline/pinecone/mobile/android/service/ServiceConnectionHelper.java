/**
 * 
 */
package com.tenline.pinecone.mobile.android.service;

import com.tenline.pinecone.mobile.android.service.AbstractService.ServiceBinder;

import android.content.ComponentName;
import android.content.ServiceConnection;
import android.os.IBinder;

/**
 * @author Bill
 *
 */
public class ServiceConnectionHelper implements ServiceConnection {
	
	private AbstractService service;
	private boolean isBound;

	@Override
	public void onServiceConnected(ComponentName className, IBinder service) {
		// TODO Auto-generated method stub
		this.service = ((ServiceBinder) service).getService();
		this.isBound = true;
	}

	@Override
	public void onServiceDisconnected(ComponentName className) {
		// TODO Auto-generated method stub
		this.isBound = false;
	}

	/**
	 * @return the service
	 */
	public AbstractService getService() {
		return service;
	}

	/**
	 * @param service the service to set
	 */
	public void setService(AbstractService service) {
		this.service = service;
	}

	/**
	 * @return the isBound
	 */
	public boolean isBound() {
		return isBound;
	}

	/**
	 * @param isBound the isBound to set
	 */
	public void setBound(boolean isBound) {
		this.isBound = isBound;
	}

}
