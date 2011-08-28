/**
 * 
 */
package com.tenline.pinecone.platform.monitor;

import java.util.Collection;

import org.apache.log4j.Logger;
import org.osgi.framework.BundleActivator;
import org.osgi.framework.BundleContext;

import com.tenline.pinecone.platform.model.User;
import com.tenline.pinecone.platform.sdk.APIListener;
import com.tenline.pinecone.platform.sdk.UserAPI;

/**
 * @author Bill
 * 
 */
public class Activator implements BundleActivator {
	
	/**
	 * Logger
	 */
	private Logger logger = Logger.getLogger(getClass());
	
	/**
	 * Web Service API
	 */
	private UserAPI userAPI;
	
	/**
	 * Endpoint Admin
	 */
	private EndpointAdmin endpointAdmin;

	/**
	 * 
	 */
	public Activator() {
		// TODO Auto-generated constructor stub
		endpointAdmin = new EndpointAdmin();
		userAPI = new UserAPI(IConstants.WEB_SERVICE_HOST, IConstants.WEB_SERVICE_PORT, new APIListener() {

			@Override
			public void onMessage(Object message) {
				// TODO Auto-generated method stub
				Object[] users = ((Collection<?>) message).toArray();
				for (int i = 0; i < users.length; i++) {
					endpointAdmin.initialize((User) users[i]);
				}
			}

			@Override
			public void onError(String error) {
				// TODO Auto-generated method stub
				logger.error(error);
			}

		});
		
	}

	@Override
	public void start(BundleContext bundleContext) throws Exception {
		// TODO Auto-generated method stub
		BundleHelper.getInstance(bundleContext);
		userAPI.show("snsId=='251760162'");
	}

	@Override
	public void stop(BundleContext bundleContext) throws Exception {
		// TODO Auto-generated method stub
		endpointAdmin.close();
	}

}
