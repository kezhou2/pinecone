/**
 * 
 */
package com.tenline.pinecone.platform.web.service;

import java.util.logging.Logger;

/**
 * @author Bill
 *
 */
public abstract class AbstractServiceTest {

	protected Logger logger = Logger.getLogger(getClass().getName());

	protected static final String HOST = "localhost";
	protected static final String REST_PORT = "8080";
	protected static final String MQTT_PORT = "1883";
	protected static final String CONTEXT = "service";

}
