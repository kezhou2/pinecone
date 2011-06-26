/**
 * 
 */
package com.tenline.pinecone.platform.osgi.device.efish.integration;

import static org.ops4j.pax.exam.CoreOptions.felix;
import static org.ops4j.pax.exam.CoreOptions.options;

import org.junit.runner.RunWith;
import org.ops4j.pax.exam.Inject;
import org.ops4j.pax.exam.Option;
import org.ops4j.pax.exam.junit.Configuration;
import org.ops4j.pax.exam.junit.JUnit4TestRunner;
import org.osgi.framework.BundleContext;

/**
 * @author Bill
 *
 */
@RunWith(JUnit4TestRunner.class)
public abstract class AbstractIntegrationTest {

	@Inject
	protected BundleContext bundleContext;
	
	@Configuration
    public static Option[] configuration() throws Exception{
		return options(felix());
    }

}
