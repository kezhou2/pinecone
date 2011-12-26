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

import com.tenline.pinecone.platform.model.DeviceDependency;

/**
 * @author Bill
 *
 */
@Path("/api/device/dependency")
public interface DeviceDependencyService extends AbstractService {
	
	/**
	 * 
	 * @param deviceDependency
	 * @return
	 */
	@POST
	@Path("/create")
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	DeviceDependency create(DeviceDependency deviceDependency);
	
	/**
	 * 
	 * @param deviceDependency
	 * @return
	 */
	@PUT
	@Path("/update")
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	DeviceDependency update(DeviceDependency deviceDependency);
	
	/**
	 * 
	 * @param filter
	 * @return
	 */
	@GET
	@Path("/show/{filter}")
	@Produces(MediaType.APPLICATION_JSON)
	Collection<DeviceDependency> show(@PathParam("filter") String filter);

	/**
	 * 
	 * @param filter
	 * @return
	 */
	@GET
	@Path("/show/@Consumer/{filter}")
	@Produces(MediaType.APPLICATION_JSON)
	Collection<DeviceDependency> showByConsumer(@PathParam("filter") String filter);
	
	/**
	 * 
	 * @param filter
	 * @return
	 */
	@GET
	@Path("/show/@Device/{filter}")
	@Produces(MediaType.APPLICATION_JSON)
	Collection<DeviceDependency> showByDevice(@PathParam("filter") String filter);
	
}
