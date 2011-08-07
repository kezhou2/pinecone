/**
 * 
 */
package com.tenline.pinecone.platform.osgi.device.efish;

import org.junit.Test;
import org.osgi.framework.Bundle;
import org.osgi.framework.BundleException;

/**
 * @author wong
 * 
 */
public class TestEfish extends AbstractIntegrationTest {

	@Test
	public void test() {
		// Using pax-exam testing framework to do integration tests.
		Bundle bundle = bundleContext.getBundle();
		try {
			System.out.println(bundle.toString()+"\t$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$");
			bundle.start();
		} catch (BundleException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
