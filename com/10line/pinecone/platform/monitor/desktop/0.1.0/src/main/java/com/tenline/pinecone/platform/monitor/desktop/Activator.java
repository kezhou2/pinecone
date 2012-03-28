/**
 * 
 */
package com.tenline.pinecone.platform.monitor.desktop;

import org.apache.log4j.Logger;
import org.osgi.framework.BundleActivator;
import org.osgi.framework.BundleContext;

import com.tenline.pinecone.platform.monitor.BundleHelper;
import com.tenline.pinecone.platform.monitor.ServiceHelper;

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
	 * Main Window
	 */
	private MainWindow window;

	/**
	 * 
	 */
	public Activator() {
		// TODO Auto-generated constructor stub
	}

	@Override
	public void start(BundleContext bundleContext) throws Exception {
		// TODO Auto-generated method stub
		BundleHelper.getInstance(bundleContext);
		ServiceHelper.getInstance(bundleContext);
		window = new MainWindow();
		window.setVisible(true);
		logger.info("Start Bundle");
	}

	@Override
	public void stop(BundleContext bundleContext) throws Exception {
		// TODO Auto-generated method stub
		window.close();
		logger.info("Stop Bundle");
	}

}
