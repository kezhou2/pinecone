/**
 * 
 */
package com.tenline.pinecone.platform.web.service;

import java.util.Collection;

import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import com.tenline.pinecone.platform.model.DeviceInstallation;

/**
 * @author Bill
 *
 */
@Path("/api/device/installation")
public interface DeviceInstallationService extends AbstractService {

	/**
	 * 
	 * @param deviceInstallation
	 * @return
	 */
	@POST
	@Path("/create")
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	DeviceInstallation create(DeviceInstallation deviceInstallation);
	
	/**
	 * 
	 * @param deviceInstallation
	 * @return
	 */
	@PUT
	@Path("/update")
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	DeviceInstallation update(DeviceInstallation deviceInstallation);
	
	/**
	 * 
	 * @param filter
	 * @return
	 */
	@GET
	@Path("/show/{filter}")
	@Produces(MediaType.APPLICATION_JSON)
	Collection<DeviceInstallation> show(@PathParam("filter") String filter);
	
	/**
	 * 
	 * @param filter
	 * @return
	 */
	@GET
	@Path("/show/@Device/{filter}")
	@Produces(MediaType.APPLICATION_JSON)
	Collection<DeviceInstallation> showByDevice(@PathParam("filter") String filter);
	
	/**
	 * 
	 * @param filter
	 * @return
	 */
	@GET
	@Path("/show/@User/{filter}")
	@Produces(MediaType.APPLICATION_JSON)
	Collection<DeviceInstallation> showByUser(@PathParam("filter") String filter);
	
}
