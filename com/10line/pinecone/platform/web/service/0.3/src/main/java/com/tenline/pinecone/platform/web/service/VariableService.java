/**
 * 
 */
package com.tenline.pinecone.platform.web.service;

import java.util.Collection;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;

import com.tenline.pinecone.platform.model.Variable;

/**
 * @author Bill
 *
 */
@Path("/api/variable")
public interface VariableService extends AbstractService {
	
	/**
	 * 
	 * @param variable
	 * @return
	 */
	@POST
	@Path("/create")
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	Variable create(Variable variable);
	
	/**
	 * 
	 * @param variable
	 * @return
	 */
	@PUT
	@Path("/update")
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	Variable update(Variable variable);
	
	/**
	 * 
	 * @param filter
	 * @return
	 */
	@GET
	@Path("/show/{filter}")
	@Produces(MediaType.APPLICATION_JSON)
	Collection<Variable> show(@PathParam("filter") String filter);
	
	/**
	 * 
	 * @param filter
	 * @return
	 */
	@GET
	@Path("/show/@Device/{filter}")
	@Produces(MediaType.APPLICATION_JSON)
	Collection<Variable> showByDevice(@PathParam("filter") String filter,
									  @Context HttpServletRequest request, 
									  @Context HttpServletResponse response);
	
}
