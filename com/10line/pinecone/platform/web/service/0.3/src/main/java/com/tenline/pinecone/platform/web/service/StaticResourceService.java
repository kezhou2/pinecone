/**
 * 
 */
package com.tenline.pinecone.platform.web.service;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.Response;

/**
 * @author Bill
 *
 */
@Path("/api/static/resource")
public interface StaticResourceService {
	
	/**
	 * 
	 * @param id
	 * @param request
	 * @return
	 */
	@POST
	@Path("/upload/{id}/@Icon")
	Response uploadIcon(@PathParam("id") String id,
						@Context HttpServletRequest request);
	
	/**
	 * 
	 * @param id
	 * @param request
	 * @param response
	 */
	@GET
	@Path("/download/{id}/@Icon")
	public void downloadIcon(@PathParam("id") String id,
							 @Context HttpServletRequest request,
							 @Context HttpServletResponse response);

}
