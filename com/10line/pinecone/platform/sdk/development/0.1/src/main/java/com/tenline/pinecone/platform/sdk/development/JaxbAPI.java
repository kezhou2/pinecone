/**
 * 
 */
package com.tenline.pinecone.platform.sdk.development;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.Marshaller;
import javax.xml.bind.Unmarshaller;

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
	 */
	public JaxbAPI(String host, String port) {
		super(host, port);
		// TODO Auto-generated constructor stub
	}

}
