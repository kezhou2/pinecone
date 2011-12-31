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

import com.tenline.pinecone.platform.model.Record;

/**
 * @author Bill
 *
 */
@Path("/api/record")
public interface RecordService extends AbstractService {

	/**
	 * 
	 * @param record
	 * @return
	 */
	@POST
	@Path("/create")
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	Record create(Record record);
	
	/**
	 * 
	 * @param record
	 * @return
	 */
	@PUT
	@Path("/update")
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	Record update(Record record);
	
	/**
	 * 
	 * @param filter
	 * @return
	 */
	@GET
	@Path("/show/{filter}")
	@Produces(MediaType.APPLICATION_JSON)
	Collection<Record> show(@PathParam("filter") String filter);
	
	/**
	 * 
	 * @param filter
	 * @return
	 */
	@GET
	@Path("/show/@Item/{filter}")
	@Produces(MediaType.APPLICATION_JSON)
	Collection<Record> showByItem(@PathParam("filter") String filter);
	
}
