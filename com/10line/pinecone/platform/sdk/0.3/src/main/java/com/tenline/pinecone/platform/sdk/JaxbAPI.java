/**
 * 
 */
package com.tenline.pinecone.platform.sdk;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.Marshaller;
import javax.xml.bind.Unmarshaller;

import com.tenline.pinecone.platform.sdk.oauth.AuthorizationAPI;

/**
 * @author Bill
 *
 */
public abstract class JaxbAPI extends ResourceAPI {

	protected JAXBContext context;
	protected Marshaller marshaller;
	protected Unmarshaller unmarshaller;
	
	/**
	 * 
	 * @param host
	 * @param port
	 * @param authorizationAPI
	 */
	public JaxbAPI(String host, String port, AuthorizationAPI authorizationAPI) {
		super(host, port, authorizationAPI);
		// TODO Auto-generated constructor stub
	}

}
