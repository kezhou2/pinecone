/**
 * 
 */
package com.huishi.security.camera.huishi;

import java.util.Hashtable;

import org.osgi.framework.BundleActivator;
import org.osgi.framework.BundleContext;
import org.osgi.framework.ServiceRegistration;

import com.tenline.pinecone.platform.monitor.AbstractProtocolBuilder;
import com.tenline.pinecone.platform.monitor.http.AbstractHttpClientProtocolExecutor;

/**
 * @author Bill
 *
 */
public class Activator implements BundleActivator {
	
	private ServiceRegistration builderRegistration;
	
	private ServiceRegistration executorRegistration;

	/**
	 * 
	 */
	public Activator() {
		// TODO Auto-generated constructor stub
	}

	@Override
	public void start(BundleContext context) throws Exception {
		// TODO Auto-generated method stub
		Hashtable<String, String> properties = new Hashtable<String, String>();
		properties.put("symbolicName", context.getBundle().getSymbolicName());
		properties.put("version", context.getBundle().getVersion().toString());
		builderRegistration = context.registerService(AbstractProtocolBuilder.class.getName(), 
				new HuishiProtocolBuilder(context.getBundle()), properties);
		executorRegistration = context.registerService(AbstractHttpClientProtocolExecutor.class.getName(), 
				new HuishiProtocolExecutor(context.getBundle()), properties);
	}

	@Override
	public void stop(BundleContext arg0) throws Exception {
		// TODO Auto-generated method stub
		builderRegistration.unregister();
		executorRegistration.unregister();
	}

}
