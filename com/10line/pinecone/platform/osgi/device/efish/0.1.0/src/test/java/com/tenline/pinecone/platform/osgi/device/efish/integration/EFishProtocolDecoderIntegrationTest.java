/**
 * 
 */
package com.tenline.pinecone.platform.osgi.device.efish.integration;

import static org.junit.Assert.assertNotNull;

import org.junit.Test;

/**
 * @author Bill
 *
 */
public class EFishProtocolDecoderIntegrationTest extends
		AbstractIntegrationTest {

	@Test
	public void test() {
		// Using pax-exam testing framework to do integration tests.
		assertNotNull(bundleContext.getBundle().getSymbolicName());
	}

}
