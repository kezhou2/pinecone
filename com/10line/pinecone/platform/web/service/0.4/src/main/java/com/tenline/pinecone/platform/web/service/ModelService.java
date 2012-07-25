/**
 * 
 */
package com.tenline.pinecone.platform.web.service;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.Response;

/**
 * @author Bill
 *
 */
@Path("/api/model")
public interface ModelService {

	/**
	 * 
	 * @param request
	 * @param response
	 */
	@POST
	@Path("/create")
	void create(@Context HttpServletRequest request, @Context HttpServletResponse response);
	
	/**
	 * 
	 * @param entityClass
	 * @param id
	 * @return
	 */
	@DELETE
	@Path("/delete/{entityClass}/{id}")
	Response delete(@PathParam("entityClass") String entityClass, @PathParam("id") String id);
	
	/**
	 * 
	 * @param request
	 * @param response
	 */
	@PUT
	@Path("/update")
	void update(@Context HttpServletRequest request, @Context HttpServletResponse response);
	
	/**
	 * 
	 * @param entityClass
	 * @param filter
	 * @param request
	 * @param response
	 */
	@GET
	@Path("/show/{entityClass}/{filter}")
	void show(@PathParam("entityClass") String entityClass, @PathParam("filter") String filter,
			  @Context HttpServletRequest request, @Context HttpServletResponse response);
	
}
