/**
 * 
 */
package com.tenline.pinecone.service;

import javax.ws.rs.DELETE;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.core.Response;

/**
 * @author Bill
 *
 */
public interface AbstractService {

	/**
	 * 
	 * @param id
	 * @return
	 */
	@DELETE
	@Path("/delete/{id}")
	Response delete(@PathParam("id") String id);
	
}
