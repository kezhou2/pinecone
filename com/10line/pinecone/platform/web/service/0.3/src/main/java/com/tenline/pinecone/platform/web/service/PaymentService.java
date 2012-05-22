/**
 * 
 */
package com.tenline.pinecone.platform.web.service;

import javax.servlet.http.HttpServletResponse;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.core.Context;

/**
 * @author wangyq
 * 
 */
@Path("/api/payment")
public interface PaymentService {

	/**
	 * 
	 * @param from
	 * @param to
	 * @param response
	 * @throws Exception
	 */
	@GET
	@Path("/generate/batch/{from}/@From/{to}/@To")
	public void generateBatch(@PathParam("from") String from, @PathParam("to") String to, 
			@Context HttpServletResponse response) throws Exception;

}
