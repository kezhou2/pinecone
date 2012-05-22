/**
 * 
 */
package com.tenline.pinecone.platform.web.service;

import java.text.ParseException;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

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
	 * @return
	 * @throws ParseException
	 */
	@GET
	@Path("/generate/batch/{from}/@From/{to}/@To")
	@Produces(MediaType.TEXT_PLAIN)
	public String generateBatch(@PathParam("from") String from, 
			@PathParam("to") String to) throws ParseException;

}
