/**
 * 
 */
package com.honking.pet.aquarium.efish;

import java.util.Hashtable;

import org.osgi.framework.BundleActivator;
import org.osgi.framework.BundleContext;
import org.osgi.framework.ServiceRegistration;

import com.tenline.pinecone.platform.monitor.AbstractProtocolBuilder;
import com.tenline.pinecone.platform.monitor.mina.AbstractMinaProtocolDecoder;
import com.tenline.pinecone.platform.monitor.mina.AbstractMinaProtocolEncoder;

/**
 * @author Bill
 *
 */
public class Activator implements BundleActivator {
	
	private ServiceRegistration builderRegistration;
	
	private ServiceRegistration decoderRegistration;
	
	private ServiceRegistration encoderRegistration;

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
				new EfishProtocolBuilder(context.getBundle()), properties);
		decoderRegistration = context.registerService(AbstractMinaProtocolDecoder.class.getName(), 
				new EfishProtocolDecoder(context.getBundle()), properties);
		encoderRegistration = context.registerService(AbstractMinaProtocolEncoder.class.getName(), 
				new EfishProtocolEncoder(context.getBundle()), properties);
	}

	@Override
	public void stop(BundleContext context) throws Exception {
		// TODO Auto-generated method stub
		builderRegistration.unregister();
		decoderRegistration.unregister();
		encoderRegistration.unregister();
	}

}
