/**
 * 
 */
package com.tenline.pinecone.platform.model;

import javax.jdo.annotations.IdentityType;
import javax.jdo.annotations.PersistenceCapable;
import javax.jdo.annotations.Persistent;
import javax.xml.bind.annotation.XmlRootElement;

/**
 * @author Bill
 *
 */
@XmlRootElement
@SuppressWarnings("serial")
@PersistenceCapable(identityType = IdentityType.APPLICATION, 
		detachable = "true", table = "device_dependency")
public class DeviceDependency extends Entity {
	
	/**
	 * Whether is optional to execute consumer or not
	 */
	@Persistent
	private Boolean isOptional = false;
	
	@Persistent(defaultFetchGroup = "true")
	private Device device;
	
	@Persistent(defaultFetchGroup = "true")
	private Consumer consumer;

	/**
	 * 
	 */
	public DeviceDependency() {
		// TODO Auto-generated constructor stub
	}

	/**
	 * @param isOptional the isOptional to set
	 */
	public void setOptional(Boolean isOptional) {
		this.isOptional = isOptional;
	}

	/**
	 * @return the isOptional
	 */
	public Boolean isOptional() {
		return isOptional;
	}

	/**
	 * @param device the device to set
	 */
	public void setDevice(Device device) {
		this.device = device;
	}

	/**
	 * @return the device
	 */
	public Device getDevice() {
		return device;
	}

	/**
	 * @param consumer the consumer to set
	 */
	public void setConsumer(Consumer consumer) {
		this.consumer = consumer;
	}

	/**
	 * @return the consumer
	 */
	public Consumer getConsumer() {
		return consumer;
	}

}
