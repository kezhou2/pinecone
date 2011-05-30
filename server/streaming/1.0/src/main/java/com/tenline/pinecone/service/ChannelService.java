/**
 * 
 */
package com.tenline.pinecone.service;

import javax.ws.rs.Consumes;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import com.tenline.pinecone.model.Event;

/**
 * @author Bill
 *
 */
@Path("/api/channel")
public interface ChannelService {
	
	/**
	 * 
	 * @param event
	 * @return
	 */
	@POST
	@Path("/subscribe")
	@Produces(MediaType.APPLICATION_JSON)
	@Consumes(MediaType.APPLICATION_JSON)
	public Event subscribe(Event event);
	
	/**
	 * 
	 * @param event
	 * @return
	 */
	@POST
	@Path("/publish")
	@Consumes(MediaType.APPLICATION_JSON)
	public Response publish(Event event);
	
}
