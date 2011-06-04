/**
 * 
 */
package com.tenline.pinecone.model;

import java.util.Collection;

import javax.jdo.annotations.Element;
import javax.jdo.annotations.Extension;
import javax.jdo.annotations.IdentityType;
import javax.jdo.annotations.Order;
import javax.jdo.annotations.PersistenceCapable;
import javax.jdo.annotations.Persistent;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;

/**
 * @author Bill
 *
 */
@XmlRootElement
@PersistenceCapable(identityType = IdentityType.APPLICATION, detachable = "true")
public class Device extends Entity {

	@Persistent
	private String name;
	
	@Persistent
	private String groupId;
	
	@Persistent
	private String artifactId;
	
	@Persistent
	private String version;
	
	@Persistent(defaultFetchGroup = "true")
	private User user;

	@Persistent(mappedBy = "device", defaultFetchGroup = "true")
    @Element(dependent = "true")
    @Order(extensions = @Extension(vendorName="datanucleus", key="list-ordering", value="name asc"))
	private Collection<Variable> variables;
	
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
	 * @param groupId the groupId to set
	 */
	public void setGroupId(String groupId) {
		this.groupId = groupId;
	}

	/**
	 * @return the groupId
	 */
	public String getGroupId() {
		return groupId;
	}

	/**
	 * @param artifactId the artifactId to set
	 */
	public void setArtifactId(String artifactId) {
		this.artifactId = artifactId;
	}

	/**
	 * @return the artifactId
	 */
	public String getArtifactId() {
		return artifactId;
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
	 * @param user the user to set
	 */
	public void setUser(User user) {
		this.user = user;
	}

	/**
	 * @return the user
	 */
	public User getUser() {
		return user;
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

}
