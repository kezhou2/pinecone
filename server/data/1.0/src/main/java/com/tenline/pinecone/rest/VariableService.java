/**
 * 
 */
package com.tenline.pinecone.rest;

import java.util.Collection;

import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import com.tenline.pinecone.model.Variable;

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
	Response create(Variable variable);
	
	/**
	 * 
	 * @param variable
	 * @return
	 */
	@PUT
	@Path("/update")
	@Consumes(MediaType.APPLICATION_JSON)
	Response update(Variable variable);
	
	/**
	 * 
	 * @param id
	 * @return
	 */
	@GET
	@Path("/show/{id}")
	@Produces(MediaType.APPLICATION_JSON)
	Variable show(@PathParam("id") String id);

	/**
	 * 
	 * @return
	 */
	@GET
	@Path("/show/all")
	@Produces(MediaType.APPLICATION_JSON)
	Collection<Variable> showAll();
	
	/**
	 * 
	 * @param filter
	 * @return
	 */
	@GET
	@Path("/show/all/{filter}")
	@Produces(MediaType.APPLICATION_JSON)
	Collection<Variable> showAll(@PathParam("filter") String filter);
	
}
