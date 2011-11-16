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

	protected JAXBContext jaxbContext;
	protected Marshaller marshaller;
	protected Unmarshaller unmarshaller;
	
	/**
	 * 
	 * @param host
	 * @param port
	 * @param context
	 */
	public JaxbAPI(String host, String port, String context) {
		super(host, port, context);
		// TODO Auto-generated constructor stub
	}

}
