package com.tenline.pinecone.platform.osgi.device.efish;

// Java
/*
 * Licensed to the Apache Software Foundation (ASF)
 * ...
 */

import static org.ops4j.pax.exam.CoreOptions.felix;
import static org.ops4j.pax.exam.CoreOptions.options;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.ops4j.pax.exam.Inject;
import org.ops4j.pax.exam.Option;
import org.ops4j.pax.exam.junit.Configuration;
import org.ops4j.pax.exam.junit.JUnit4TestRunner;
import org.osgi.framework.BundleContext;
import org.osgi.util.tracker.ServiceTracker;

import com.tenline.pinecone.platform.osgi.monitor.UsbFishTankBundle;

@RunWith(JUnit4TestRunner.class)
public class FeaturesTest {
	@Inject
	protected BundleContext bundleContext;

	@Test
	public void testFeatures() throws Exception {
		System.out.println("start testFeatures!");
		ServiceTracker tracker = new ServiceTracker(bundleContext,
				"com.tenline.pinecone.platform.osgi.monitor.UsbFishTankBundle",
				null);
		tracker.open(true);
		System.out.println(tracker.size());
		UsbFishTankBundle fishBundle = (UsbFishTankBundle) tracker
				.waitForService(10000);
		System.out.println(tracker.getTrackingCount());
		System.out.println(fishBundle);
		System.out.println(bundleContext);
		tracker.close();

	}

	@Configuration
	public static Option[] configuration() throws Exception {
		return options(felix());
	}

}