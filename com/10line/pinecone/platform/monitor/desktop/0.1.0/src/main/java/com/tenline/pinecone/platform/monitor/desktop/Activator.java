/**
 * 
 */
package com.tenline.pinecone.platform.monitor.desktop;

import java.util.Properties;

import javax.swing.UIManager;

import org.apache.log4j.Logger;
import org.osgi.framework.BundleActivator;
import org.osgi.framework.BundleContext;

import com.jtattoo.plaf.hifi.HiFiLookAndFeel;
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
		// Setup Java Swing Skin
		Properties props = new Properties();
		props.put("logoString", "");
		HiFiLookAndFeel.setCurrentTheme(props);
		UIManager.setLookAndFeel(new HiFiLookAndFeel());
		UIManager.getDefaults().put("ClassLoader", HiFiLookAndFeel.class.getClassLoader());
		window = new MainWindow(bundleContext);
		logger.info("Start Bundle");
	}

	@Override
	public void stop(BundleContext bundleContext) throws Exception {
		// TODO Auto-generated method stub
		window.close();
		logger.info("Stop Bundle");
	}

}
