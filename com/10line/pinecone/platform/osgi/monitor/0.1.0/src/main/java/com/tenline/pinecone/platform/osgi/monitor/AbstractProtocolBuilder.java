/**
 * 
 */
package com.tenline.pinecone.platform.osgi.monitor;

import java.io.IOException;
import java.util.LinkedList;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

import org.osgi.framework.Bundle;
import org.w3c.dom.Document;
import org.xml.sax.SAXException;

import com.tenline.pinecone.platform.model.Device;

/**
 * @author Bill
 *
 */
public abstract class AbstractProtocolBuilder {
	
	/**
	 * Protocol Document
	 */
	protected Document document;

	/**
	 * 
	 * @param device
	 */
	public AbstractProtocolBuilder(Device device) {
		// TODO Auto-generated constructor stub
		try {
			DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance(); 
			DocumentBuilder builder = factory.newDocumentBuilder(); 
			Bundle bundle = Activator.getBundle(device.getSymbolicName());
			document = builder.parse(bundle.getResource("META-INF/protocol.xml").openStream());
		} catch (SAXException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (ParserConfigurationException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	/**
	 * Initialize Read Queue
	 * @param queue
	 */
	public abstract void initializeReadQueue(LinkedList<Device> queue);

}
