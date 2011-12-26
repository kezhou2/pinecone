/**
 * 
 */
package com.tenline.pinecone.platform.model;

import java.util.Collection;

import javax.jdo.annotations.Element;
import javax.jdo.annotations.IdentityType;
import javax.jdo.annotations.PersistenceCapable;
import javax.jdo.annotations.Persistent;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;

/**
 * @author Bill
 *
 */
@XmlRootElement
@SuppressWarnings("serial")
@PersistenceCapable(identityType = IdentityType.APPLICATION, detachable = "true")
public class Device extends Entity {

	@Persistent
	private String name;
	
	@Persistent
	private String symbolicName;
	
	@Persistent
	private String version;
	
	@Persistent
	private byte[] icon;

	@Persistent(mappedBy = "device", defaultFetchGroup = "true")
    @Element(dependent = "true")
	private Collection<Variable> variables;
	
	@Persistent(mappedBy = "device", defaultFetchGroup = "true")
    @Element(dependent = "true")
	private Collection<DeviceDependency> deviceDependencies;
	
	@Persistent(mappedBy = "device", defaultFetchGroup = "true")
    @Element(dependent = "true")
	private Collection<DeviceInstallation> deviceInstallations;
	
	@Persistent(mappedBy = "device", defaultFetchGroup = "true")
    @Element(dependent = "true")
	private Collection<Record> records;
	
	/**
	 * 
	 */
	public Device() {
		// TODO Auto-generated constructor stub
	}

	/**
	 * @param name the name to set
	 */
	public void setName(String name) {
		this.name = name;
	}

	/**
	 * @return the name
	 */
	public String getName() {
		return name;
	}

	/**
	 * @param symbolicName the symbolicName to set
	 */
	public void setSymbolicName(String symbolicName) {
		this.symbolicName = symbolicName;
	}

	/**
	 * @return the symbolicName
	 */
	public String getSymbolicName() {
		return symbolicName;
	}

	/**
	 * @param version the version to set
	 */
	public void setVersion(String version) {
		this.version = version;
	}

	/**
	 * @return the version
	 */
	public String getVersion() {
		return version;
	}

	/**
	 * @param icon the icon to set
	 */
	public void setIcon(byte[] icon) {
		this.icon = icon;
	}

	/**
	 * @return the icon
	 */
	public byte[] getIcon() {
		return icon;
	}

	/**
	 * @param variables the variables to set
	 */
	@XmlTransient
	public void setVariables(Collection<Variable> variables) {
		this.variables = variables;
	}

	/**
	 * @return the variables
	 */
	public Collection<Variable> getVariables() {
		return variables;
	}

	/**
	 * @param deviceDependencies the deviceDependencies to set
	 */
	@XmlTransient
	public void setDeviceDependencies(Collection<DeviceDependency> deviceDependencies) {
		this.deviceDependencies = deviceDependencies;
	}

	/**
	 * @return the deviceDependencies
	 */
	public Collection<DeviceDependency> getDeviceDependencies() {
		return deviceDependencies;
	}

	/**
	 * @param deviceInstallations the deviceInstallations to set
	 */
	@XmlTransient
	public void setDeviceInstallations(Collection<DeviceInstallation> deviceInstallations) {
		this.deviceInstallations = deviceInstallations;
	}

	/**
	 * @return the deviceInstallations
	 */
	public Collection<DeviceInstallation> getDeviceInstallations() {
		return deviceInstallations;
	}

	/**
	 * @param records the records to set
	 */
	@XmlTransient
	public void setRecords(Collection<Record> records) {
		this.records = records;
	}

	/**
	 * @return the records
	 */
	public Collection<Record> getRecords() {
		return records;
	}

}
