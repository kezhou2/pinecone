/**
 * 
 */
package com.tenline.pinecone.platform.web.store.server;

import com.google.gwt.user.server.rpc.RemoteServiceServlet;

/**
 * @author Bill
 *
 */
@SuppressWarnings("serial")
public abstract class AbstractService extends RemoteServiceServlet {

	protected static final String HOST = "pinecone-service.cloudfoundry.com";
	protected static final String PORT = "80";
	protected static final String CONTEXT = null;

}
