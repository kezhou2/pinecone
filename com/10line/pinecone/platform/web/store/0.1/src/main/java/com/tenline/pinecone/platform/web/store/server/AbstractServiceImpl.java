/**
 * 
 */
package com.tenline.pinecone.platform.web.store.server;

import com.google.gwt.user.server.rpc.RemoteServiceServlet;

/**
 * @author Bill
 *
 */
public abstract class AbstractServiceImpl extends RemoteServiceServlet {

	/**
	 * 
	 */
	private static final long serialVersionUID = 796944131931187237L;
	
	protected static final String HOST = "pinecone-service.cloudfoundry.com";
	protected static final String PORT = "80";
	protected static final String CONTEXT = null;

}
