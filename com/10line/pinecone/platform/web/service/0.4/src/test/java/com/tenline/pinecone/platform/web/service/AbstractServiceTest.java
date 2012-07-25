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
	protected static final String PORT = "8888";
	protected static final String CONTEXT = "service";

}
