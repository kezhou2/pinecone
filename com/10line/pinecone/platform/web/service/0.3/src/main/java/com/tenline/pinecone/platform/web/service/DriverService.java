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

import com.tenline.pinecone.platform.model.Driver;

/**
 * @author Bill
 *
 */
@Path("/api/driver")
public interface DriverService extends AbstractService {

	/**
	 * 
	 * @param driver
	 * @return
	 */
	@POST
	@Path("/create")
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	Driver create(Driver driver);
	
	/**
	 * 
	 * @param driver
	 * @return
	 */
	@PUT
	@Path("/update")
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	Driver update(Driver driver);
	
	/**
	 * 
	 * @param filter
	 * @return
	 */
	@GET
	@Path("/show/{filter}")
	@Produces(MediaType.APPLICATION_JSON)
	Collection<Driver> show(@PathParam("filter") String filter);
	
	/**
	 * 
	 * @param filter
	 * @return
	 */
	@GET
	@Path("/show/@Category/{filter}")
	@Produces(MediaType.APPLICATION_JSON)
	Collection<Driver> showByCategory(@PathParam("filter") String filter);
	
}
